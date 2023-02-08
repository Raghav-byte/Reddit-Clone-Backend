package com.example.RedditClone.Reddit.Controller;

import com.example.RedditClone.Reddit.Repository.UserRepo;
import com.example.RedditClone.Reddit.Service.MiscellaneousService;
import com.lowagie.text.DocumentException;
import io.swagger.annotations.ApiOperation;
import org.ocpsoft.prettytime.PrettyTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@RestController
public class MiscellaneousController {

    @Autowired
    private UserRepo userRepo ;
    @Autowired
    private MiscellaneousService miscellaneousService;

    @ApiOperation("Find user name by id")
    @GetMapping(path = "/find/user")
    public String findUserName(@RequestParam UUID userId){
        return miscellaneousService.findUserName(userId);
    }

    @ApiOperation("Export all users in excel")
    @GetMapping(path = "/export/users/excel")
    public ResponseEntity<ByteArrayResource> exportUsers(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Users_Export");
        ByteArrayResource in = miscellaneousService.exportUsers();
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(in);
    }

    @ApiOperation("Get relative time")
    @GetMapping("/get/relativeTime")
    public String findRelativeTime(Date createdTimeStamp){
        PrettyTime prettyTime = new PrettyTime();
        return prettyTime.format(createdTimeStamp);
   }

}
