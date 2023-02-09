package com.example.RedditClone.Reddit.Service;

import com.example.RedditClone.Reddit.Model.User;
import com.example.RedditClone.Reddit.Model.UserLoginDetails;
import com.example.RedditClone.Reddit.Repository.UserLoginDetailsRepo;
import com.example.RedditClone.Reddit.Repository.UserRepo;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
public class MiscellaneousService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MongoOperations mongoOperations;
    @Autowired
    private UserLoginDetailsRepo loginDetailsRepo;
    @Autowired
    private JavaMailSender mailSender;

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

        String[] COLUMNs = {"Created Date", "Name", "Date of Birth", "Gender", "Mobile NUmber", "Address", "Active "};
        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
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
                sheet.setColumnWidth(col, 20 * 256);
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;

            for (User user : userList) {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //GENERATING RANDOM PASSWORD
    public String generateUsernameAndPassword() {
        CharacterRule LC = new CharacterRule(EnglishCharacterData.LowerCase);
        LC.setNumberOfCharacters(4);
        CharacterRule UC = new CharacterRule(EnglishCharacterData.UpperCase);
        UC.setNumberOfCharacters(2);
        CharacterRule SC = new CharacterRule(EnglishCharacterData.Special);
        SC.setNumberOfCharacters(2);
        CharacterRule D = new CharacterRule(EnglishCharacterData.Digit);
        D.setNumberOfCharacters(2);
        PasswordGenerator passGen = new PasswordGenerator();
        return passGen.generatePassword(10, LC, UC, SC, D);
    }

    //CHECK THE ENTERED USERNAME AND PASSWORD IS CORRECT OR NOT
    public boolean checkUsernamePassword(UUID userId, String username, String password) {
        Optional<UserLoginDetails> loginDetails = loginDetailsRepo.findById(userId);
        if (loginDetails.isPresent()) {
            if (loginDetails.get().getUserName().equals(username) && loginDetails.get().getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new ResourceAccessException("Login Details not found");
        }
    }

    public String resetPassword(UUID userId, String username) {
        Optional<UserLoginDetails> loginDetails = null;

        if (userId != null) {
            loginDetails = loginDetailsRepo.findById(userId);
        } else if (username != null && userId == null) {
            loginDetails = loginDetailsRepo.findByUserName(username);
        }

        if (loginDetails != null) {
            String newPassword = generateUsernameAndPassword();
            loginDetails.get().setPassword(newPassword);
            loginDetailsRepo.save(loginDetails.get());

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(loginDetails.get().getEmailId());
            message.setSubject("Hi User ! Here is your new password");
            message.setText("Password : " + newPassword);

            mailSender.send(message);
            return "New Password successfully sent to the user";

        } else {
            throw new ResourceAccessException("User Not Found");
        }

    }
}
