package com.trnvinh.bookingroom.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import org.apache.coyote.BadRequestException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource; 
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trnvinh.bookingroom.model.BookingRoom;
import com.trnvinh.bookingroom.service.BookingRoomService;

@RestController
@RequestMapping("/api/v1/bookingroom")
public class BookingRoomController {
	@Autowired
	private BookingRoomService service;

	@GetMapping("/check")
	public ResponseEntity<Map<String, Object>> checkBookingConflict(
	        @RequestParam Long roomId,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
	        @RequestParam Long exceptedId) {
	    Map<String, Object> response = new HashMap<>();

	    boolean conflict = service.isBookingConflict(roomId, startTime, endTime, exceptedId);
	    response.put("conflict", conflict);
	    return ResponseEntity.ok(response);
	}
	@GetMapping
	public List<BookingRoom> getAllBookingRooms() {
		return service.gettAllBookingRooms();
	}

	@GetMapping("/{id}")
	public BookingRoom getBookingRoomByUserRoom(@PathVariable Long id) {
		return service.getBookingRoomByUserRoom(id);
	}

	@PostMapping
	public void insertBookingRoom(@RequestBody BookingRoom bookingRoom) throws BadRequestException {
		if (bookingRoom.getEndTime().isBefore(bookingRoom.getStartTime())) {
            throw new BadRequestException("End time must be later than start time");
        }

        boolean conflict = service.isBookingConflict(bookingRoom.getRoomId(), bookingRoom.getStartTime(), bookingRoom.getEndTime(), null);
        if (conflict) {
            throw new BadRequestException("The room is already booked for this time period.");
        }
		service.insertBookingRoom(bookingRoom);
	}

	@PutMapping("/{id}")
	public void updateBookingRoom(@PathVariable Long id,
			@RequestBody BookingRoom bookingRoom) throws BadRequestException{
		bookingRoom.setId(id);
		boolean conflict = service.isBookingConflict(bookingRoom.getRoomId(), bookingRoom.getStartTime(), bookingRoom.getEndTime(), id);
        if (conflict) {
            throw new BadRequestException("The room is already booked for this time period.");
        }
		service.updateBookingRoom(bookingRoom);
	}
	
	@DeleteMapping("/{id}")
	public void deleteBookingRoom(@PathVariable Long id) {
		service.deleteBookingRoom(id);
	}
	@GetMapping("/date")
    public List<BookingRoom> getBookingsByDate(@RequestParam String date) {
        return service.findByDate(date);
    }
	@GetMapping("/export")
	public ResponseEntity<InputStreamResource> exportBookingRooms() throws IOException {
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	    Workbook workbook = null;
	    try {
	        workbook = createWorkbook(service.gettAllBookingRooms());
	        workbook.write(outputStream);
	    } finally {
	        if (workbook != null) {
	            workbook.close();
	        }
	    }

	    // Set headers for the response
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=booking_rooms.xlsx");
	    headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

	    // Return response entity
	    return ResponseEntity.ok()
	            .headers(headers)
	            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	            .body(new InputStreamResource(new ByteArrayInputStream(outputStream.toByteArray())));
	}


    private Workbook createWorkbook(List<BookingRoom> bookingRooms) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Booking Rooms");

        // Header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Booking ID");
        headerRow.createCell(1).setCellValue("User Id");
        headerRow.createCell(2).setCellValue("Room Id");
        headerRow.createCell(3).setCellValue("Start Time");
        headerRow.createCell(4).setCellValue("End Time");
        headerRow.createCell(5).setCellValue("Description");

        // Data rows
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int rowNum = 1;
        for (BookingRoom bookingRoom : bookingRooms) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(bookingRoom.getId());
            row.createCell(1).setCellValue(bookingRoom.getUserId());
            row.createCell(2).setCellValue(bookingRoom.getRoomId());
            row.createCell(3).setCellValue(formatter.format(bookingRoom.getStartTime()));
            row.createCell(4).setCellValue(formatter.format(bookingRoom.getEndTime()));
            row.createCell(5).setCellValue(bookingRoom.getDescription());
        }

        return workbook;
    }
    @GetMapping("/export/pdf")
    public ResponseEntity<byte[]> generatePdfFile() throws DocumentException, IOException {
        List<BookingRoom> listOfBookingRooms = service.gettAllBookingRooms();
        byte[] pdfBytes = generatePdf(listOfBookingRooms);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "booking_rooms.pdf");
        
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    public byte[] generatePdf(List<BookingRoom> bookingRoomList) throws DocumentException, IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();
        
        // Title
        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);
        Paragraph paragraph = new Paragraph("List of Booking Rooms", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);
        
        // Table
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3, 3, 3, 3});
        table.setSpacingBefore(10);
        
        // Table Header
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontHeader.setColor(CMYKColor.WHITE);
        
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(5);
        
        String[] headers = {"ID", "User ID", "Room ID", "Start Time", "End Time", "Description"};
        for (String header : headers) {
            cell.setPhrase(new Phrase(header, fontHeader));
            table.addCell(cell);
        }
        
        // Table Data
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (BookingRoom bookingRoom : bookingRoomList) {
            table.addCell(String.valueOf(bookingRoom.getId()));
            table.addCell(String.valueOf(bookingRoom.getUserId()));
            table.addCell(String.valueOf(bookingRoom.getRoomId()));
            table.addCell(formatter.format(bookingRoom.getStartTime()));
            table.addCell(formatter.format(bookingRoom.getEndTime()));
            table.addCell(bookingRoom.getDescription());
        }
        
        // Adding table to document
        document.add(table);
        document.close();
        
        return outputStream.toByteArray();
    }



}
