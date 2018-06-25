package com.licenta.licenta.controllers;

import com.licenta.licenta.representations.resp.TitleResponse;
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
public class TitleController {

    private final BayesNaiveService bayesNaiveService;
    @Autowired
    public TitleController(BayesNaiveService bayesNaiveService) {
        this.bayesNaiveService = bayesNaiveService;
    }

    @RequestMapping(value = "/title", method = RequestMethod.POST)
    public ResponseEntity<List<TitleResponse>> extractTitle(@RequestPart(value = "file") MultipartFile pdfFile){
        List<TitleResponse> titleResponseList = bayesNaiveService.titleNaiveBayes(pdfFile);
        return new ResponseEntity<>(titleResponseList, HttpStatus.OK);
    }
}
