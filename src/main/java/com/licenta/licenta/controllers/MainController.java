package com.licenta.licenta.controllers;

import com.licenta.licenta.service.BayesNaiveTitleService;
import com.licenta.licenta.service.TrainDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final TrainDataService trainDataService;
    private final BayesNaiveTitleService bayesNaiveTitleService;

    @Autowired
    public MainController(TrainDataService trainDataService, BayesNaiveTitleService bayesNaiveTitleService) {
        this.trainDataService = trainDataService;
        this.bayesNaiveTitleService = bayesNaiveTitleService;
    }

    @RequestMapping(value = "/trainData", method = RequestMethod.GET)
    public ResponseEntity<String> hello(){
        trainDataService.test();
       // trainDataService.extractTextFromPDF();
        return new ResponseEntity<>("Data was trained", HttpStatus.OK);
    }

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public ResponseEntity<String> hi(){
        bayesNaiveTitleService.testNaiveBayse();
        return new ResponseEntity<>("Bayes Naive", HttpStatus.OK);
    }
}
