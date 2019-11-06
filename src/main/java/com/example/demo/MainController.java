package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.UnknownHostException;

@RestController
public class MainController {

    @RequestMapping(value = "")
    public ResponseEntity<String> main(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws UnknownHostException {

        return new ResponseEntity<>("Deploy Test Success", HttpStatus.OK);
    }
}
