package com.licenta.licenta.controllers;

import com.licenta.licenta.representations.resp.AuthorAndTitle;
import com.licenta.licenta.representations.resp.AuthorTitleResp;
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
public class TitleAuthorController {

    private final BayesNaiveService bayesNaiveService;

    @Autowired
    public TitleAuthorController(BayesNaiveService bayesNaiveService) {
        this.bayesNaiveService = bayesNaiveService;
    }

    @RequestMapping(value = "authorTitleAndText", method = RequestMethod.POST)
    public ResponseEntity<List<AuthorTitleResp>> extractAuthorTitleAndText(@RequestPart(value = "file") MultipartFile pdfFile) {
        List<AuthorTitleResp> authorAndTitleList = bayesNaiveService.naiveBayesCorola(pdfFile);
        return new ResponseEntity<>(authorAndTitleList, HttpStatus.OK);
    }

    @RequestMapping(value = "authorAndTitle", method = RequestMethod.POST)
    public ResponseEntity<List<AuthorAndTitle>> extractAuthorAndTitle(@RequestPart(value = "file") MultipartFile pdfFile){
        List<AuthorAndTitle> authorAndTitleList = bayesNaiveService.naiveBayes(pdfFile);
        return new ResponseEntity<>(authorAndTitleList, HttpStatus.OK);
    }
}
