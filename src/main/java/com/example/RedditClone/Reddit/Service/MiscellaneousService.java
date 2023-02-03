package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.lowagie.text.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

@Service
public class MiscellaneousService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoOperations mongoOperations;

    //FIND USER NAME BY USER-ID
    public String findUserName(UUID userId) {
        try {
            return userRepo.findById(userId).get().getName();
        } catch (Exception e) {
            throw new ResourceAccessException("No user found ");
        }
    }

    //EXPORTING ALL USERS IN EXCEL
    public ByteArrayResource exportUsers() {
        List<User> userList = mongoOperations.findAll(User.class);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String[] COLUMNs = {"Created Date" , "Name" , "Date of Birth" , "Gender" , "Mobile NUmber" , "Address" , "Active "};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ){
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Users");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.BLUE.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        // Row for Header
        Row headerRow = sheet.createRow(0);

        // Header
        for (int col = 0; col < COLUMNs.length; col++) {
            sheet.setColumnWidth(col,20*256);
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(COLUMNs[col]);
            cell.setCellStyle(headerCellStyle);
        }

            int rowIdx = 1;
            //"Created Date" , "Name" , "Date of Birth" , "Gender" , "Mobile NUmber" , "Address" , "Active "};
            for (User user : userList){
                Row row = sheet.createRow(rowIdx++);
                //creating cells for the row
                row.createCell(0).setCellValue(sdf.format(user.getCreatedTimeStamp()));
                row.createCell(1).setCellValue(user.getName());
                row.createCell(2).setCellValue(sdf.format(user.getDateOfBirth()));
                row.createCell(3).setCellValue(user.getGender().toString());
                row.createCell(4).setCellValue(user.getContactInformation().getMobileNumber());
                String stringBuilder = user.getAddress().getLocality() + " , " + user.getAddress().getCity() + " , "
                        + user.getAddress().getState() + " , " + user.getAddress().getCountry();
                row.createCell(5).setCellValue(stringBuilder);
                row.createCell(6).setCellValue(user.isActive() ? "Yes" : "No");
            }

            workbook.write(out);
            return new ByteArrayResource(out.toByteArray());
        }
     catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public ResponseEntity<File> exportToPDF() throws FileNotFoundException {
//
//            List<User> userList = mongoOperations.findAll(User.class);
//            Document document = new Document(PageSize.A4);
//            File file = new File("users.pdf");
//            FileOutputStream outputStream = new FileOutputStream(file);
//            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//            document.open();
//            com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
//            font.setSize(10);
//            font.setColor(java.awt.Color.BLACK.darker());
//
//            Paragraph p = new Paragraph("Tax Invoice", font);
//            p.add(Chunk.NEWLINE);
//            p.add("Original For Recipient");
//            p.setAlignment(Paragraph.ALIGN_CENTER);
//            document.add(p);
//            for (User user : userList) {
//
//                PdfPTable table = new PdfPTable(11);
//                table.setWidthPercentage(100f);
//                //table.setWidths(new float[] {5.0f,5.0f, 3.5f, 2.0f, 3.0f, 3.0f,3.5f, 3.0f, 3.0f, 3.7f, 3.5f});
//                table.setSpacingBefore(5);
//                table.getDefaultCell().setBorder(0);
//
//                //creating headers of table
//                PdfPCell cell = new PdfPCell();
//                cell.setBackgroundColor(java.awt.Color.LIGHT_GRAY);
//                cell.setPadding(5);
//
//                font.setColor(java.awt.Color.black);
//                font.setSize(10);
//
//                cell.setPhrase(new Phrase("Users", font));
//                cell.setBorder(0);
//                table.addCell(cell);
//
//                Paragraph paragraph = new Paragraph("Order Id:  12345 " ,font);
//                paragraph.add(Chunk.NEWLINE);
//                paragraph.setLeading(20);
//
//                LineSeparator ls = new LineSeparator();
//                document.add(new Chunk(ls));
//
//
//                    Paragraph para = new Paragraph("Customer Name :" ,font);
//                    para.add(Chunk.NEWLINE);
//                    paragraph.setLeading(20);
//                    //para.add("Customer Mobile Number : "+(!TextUtils.isEmpty(vendorwiseOrders.getUserMobileNumber()) ? vendorwiseOrders.getUserMobileNumber() : "N/A"));
//                    para.add(Chunk.NEWLINE);
//                    //para.add("OrderType : "+((vendorwiseOrders.getOrderType() != null) ? vendorwiseOrders.getOrderType() : "N/A"));
//                    document.add(para);
//
//                PdfPCell dataCell = new PdfPCell();
//                dataCell.setBorder(Rectangle.NO_BORDER);
//                dataCell.setPaddingTop(8f);
//                dataCell.setPaddingBottom(8f);
//
//                dataCell.setPhrase(new Phrase("Raghav"));
//                table.addCell(dataCell);
//
//
//                Paragraph space = new Paragraph(Chunk.NEWLINE);
//                document.add(space);
//                //  document.newPage();
//            }
//
//            document.close();
//            ResponseEntity<File> response = new ResponseEntity<>(file,HttpStatus.CREATED);
//            return  response;
//        }



}
