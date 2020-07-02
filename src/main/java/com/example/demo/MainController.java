package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@RestController
public class MainController {

    private final String nowDateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    @RequestMapping(value = "")
    public ResponseEntity<String> main(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws UnknownHostException {

        return new ResponseEntity<>(String.format("Deploy Test Success : %s", nowDateStr), HttpStatus.OK);
    }
}
