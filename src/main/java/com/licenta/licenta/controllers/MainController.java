package com.licenta.licenta.controllers;

import com.licenta.licenta.service.BayesNaiveService;
import com.licenta.licenta.service.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MainController {

    private final TrainDataService trainDataService;
    private final BayesNaiveService bayesNaiveService;

    @Autowired
    public MainController(TrainDataService trainDataService, BayesNaiveService bayesNaiveService) {
        this.trainDataService = trainDataService;
        this.bayesNaiveService = bayesNaiveService;
    }

    @RequestMapping(value = "/trainData", method = RequestMethod.POST)
    public ResponseEntity<String> trainData(@RequestPart(value = "file") MultipartFile pdfFile){
        trainDataService.trainDataForTitle(pdfFile);
//        trainDataService.trainDataForAuthor();
        return new ResponseEntity<>("Data was trained", HttpStatus.OK);
    }


}
