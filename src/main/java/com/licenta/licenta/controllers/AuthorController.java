package com.licenta.licenta.controllers;


import com.licenta.licenta.representations.resp.AuthorResponse;
import com.licenta.licenta.service.BayesNaiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AuthorController {
    private final BayesNaiveService bayesNaiveService;

    @Autowired
    public AuthorController(BayesNaiveService bayesNaiveService) {
        this.bayesNaiveService = bayesNaiveService;
    }

    @RequestMapping(value = "/author", method = RequestMethod.POST)
    public ResponseEntity<List<AuthorResponse>> extractAuthor(@RequestPart(value = "file") MultipartFile pdfFile){
        List<AuthorResponse> authorResponseList = bayesNaiveService.authorNaiveBayes(pdfFile);
        return new ResponseEntity<>(authorResponseList, HttpStatus.OK);
    }
}
