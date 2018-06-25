package com.licenta.licenta.service;

import com.licenta.licenta.model.AuthorFeatures;
import com.licenta.licenta.model.TitleFeatures;
import com.licenta.licenta.repository.AuthorFeaturesRepository;
import com.licenta.licenta.repository.TitleFeaturesRepository;
import com.licenta.licenta.representations.TextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TrainDataService {

    private final TitleFeaturesRepository titleFeaturesRepository;
    private final AuthorFeaturesRepository authorFeaturesRepository;
    private final FeaturesService featuresService;


    @Autowired
    public TrainDataService(TitleFeaturesRepository titleFeaturesRepository,
                            AuthorFeaturesRepository authorFeaturesRepository, FeaturesService featuresService) {
        this.titleFeaturesRepository = titleFeaturesRepository;
        this.authorFeaturesRepository = authorFeaturesRepository;
        this.featuresService = featuresService;
    }

    public String trainDataForTitle(MultipartFile pdfFile) {
        try {
//            PDDocument pdDocument = PDDocument.load(new File(
//                    "C:\\Users\\fvasilie\\Desktop\\Arhiva TIMPUL\\" +
//                            "Arhiva TIMPUL\\Timpul - 2016\\PDF Timpul august 2016_BT.pdf"));

//            PDDocument pdDocument = PDDocument.load(new File(
//                    "C:\\Users\\fvasilie\\Desktop\\Arhiva TIMPUL\\" +
//                            "Arhiva TIMPUL\\Timpul - 2016\\TIMPUL-aprilie-2016.pdf"));

            PDDocument pdDocument = PDDocument.load(pdfFile.getInputStream());
            PDFTextStripper stripper = new TextStripper();

            Map<Integer, List<String>> textPerPage = new HashMap<>();
            for (Integer i = 1; i < pdDocument.getNumberOfPages(); i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                textPerPage.put(i, featuresService.getLinesFrom(stripper.getText(pdDocument)));
            }

            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                System.out.print("Page = " + entry.getKey());
                System.out.print(" Max for this page is: " + featuresService.maxTextSize(entry.getValue()));
                Double meanMax = featuresService.meanMax(entry.getValue());
                Double meanLength = featuresService.meanLength(entry.getValue());
                System.out.print(" Mean max for this page is: " + meanMax);
                System.out.print(" Mean length for this page is: " + meanLength);
                System.out.println();
                entry.getValue().forEach(it -> {
                    TitleFeatures features = new TitleFeatures();
                    features.setDocumentName(pdfFile.getName());
                    features.setTextPerLine(featuresService.getText(it));
                    features.setPageNumber(entry.getKey());
                    features.setAlpha(featuresService.isAlpha(it));
                    features.setBold(featuresService.isBold(it));
                    features.setContainsQuotationMark(featuresService.containsQuotationMark(it));
                    features.setInFirst10Rows(featuresService.isInFirst10Rows(entry.getValue(), it));
                    features.setInitCap(featuresService.isInitCap(it));
                    features.setItalic(featuresService.isItalic(it));
                    features.setLengthLowerThanLengthMean(featuresService.isLengthLowerThanLengthMean(it, meanLength));
                    features.setGreaterThanMeanMax(featuresService.isGraterThanMeanMax(it, meanMax));
                    features.setHasMoreThanTwoWords(featuresService.hasMoreThanTwoWords(it));
                    titleFeaturesRepository.save(features);

                    System.out.println("\t isBold: " + featuresService.isBold(it) + " isItalic: " + featuresService.isItalic(it)
                            + " isGraterThanMeanMax: " + featuresService.isGraterThanMeanMax(it, meanMax)
                            + " containsQuotationMark: " + featuresService.containsQuotationMark(it)
                            + " isInitCap: " + featuresService.isInitCap(it) + " isAlpha: " + featuresService.isAlpha(it)
                            + " isLengthLowerThanLengthMean: " + featuresService.isLengthLowerThanLengthMean(it, meanLength)
                            + " isInFirst10Rows: " + featuresService.isInFirst10Rows(entry.getValue(), it)
                            + " hasMoreThanTwoWords: " + featuresService.hasMoreThanTwoWords(it));
                    System.out.println(" Text: " + it);
                });
            }
//
//            stripper.setStartPage(0);
//            stripper.setEndPage(pdDocument.getNumberOfPages());
//            System.out.println(stripper.getText(pdDocument));

//            int cont = 0;
//            ImageToPdf imageToPdf = new ImageToPdf();
//            for (PDPage page : pdDocument.getPages()){
//                cont++;
//                System.out.println("Processing page " + cont);
//                imageToPdf.processPage(page);
//            }
        pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String trainDataForAuthor() {
        try {
            PDDocument pdDocument = PDDocument.load(new File(
                    "C:\\Users\\fvasilie\\Desktop\\Arhiva TIMPUL\\" +
                            "Arhiva TIMPUL\\Timpul - 2016\\PDF Timpul august 2016_BT.pdf"));


            PDFTextStripper stripper = new TextStripper();

            Map<Integer, List<String>> textPerPage = new HashMap<>();
            for (Integer i = 1; i < pdDocument.getNumberOfPages(); i++) {
                stripper.setStartPage(i);
                stripper.setEndPage(i);
                textPerPage.put(i, featuresService.getLinesFrom(stripper.getText(pdDocument)));
            }

            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                System.out.print("Page = " + entry.getKey());
                System.out.print(" Max for this page is: " + featuresService.maxTextSize(entry.getValue()));
                Double meanMax = featuresService.meanMax(entry.getValue());
                Double meanLength = featuresService.meanLength(entry.getValue());
                System.out.print(" Mean max for this page is: " + meanMax);
                System.out.print(" Mean length for this page is: " + meanLength);
                System.out.println();
                entry.getValue().forEach(it -> {
                    AuthorFeatures features = new AuthorFeatures();
                    features.setDocumentName("PDF Timpul august 2016_BT.pdf");
                    features.setTextPerLine(featuresService.getText(it));
                    features.setPageNumber(entry.getKey());
                    features.setBold(featuresService.isBold(it));
                    features.setAlpha(featuresService.isAlpha(it));
                    features.setInFirst10Rows(featuresService.isInFirst10Rows(entry.getValue(), it));
                    features.setInitCap(featuresService.isInitCap(it));
                    features.setGreaterThanMeanMax(featuresService.isGraterThanMeanMax(it, meanMax));
                    features.setFirstCharAlpha(featuresService.isFirstCharAlpha(it));
                    features.setLastCharAlpha(featuresService.isLastCharAlpha(it));
                    features.setAllWordsInitCap(featuresService.isAllWordsInitCap(it));
                    features.setContainsBetweenTwoAndThreeWords(featuresService.containsBetweenTwoAndThreeWords(it));
                    authorFeaturesRepository.save(features);

//                    System.out.println("\t isBold: " + featuresService.isBold(it)
//                            + " isGraterThanMeanMax: " + featuresService.isGraterThanMeanMax(it, meanMax)
//                            + " isInitCap: " + featuresService.isInitCap(it) + " isAlpha: " + featuresService.isAlpha(it)
//                            + " isInFirst10Rows: " + featuresService.isInFirst10Rows(entry.getValue(), it)
//                            + " isFirstCharAlpha: " + featuresService.isFirstCharAlpha(it)
//                            + " isLastCharAlpha: " + featuresService.isLastCharAlpha(it)
//                            + " allWordsInitCap: " + featuresService.isAllWordsInitCap(it)
//                            + " containsBetween2And3Words: " + featuresService.containsBetweenTwoAndThreeWords(it));
//                    System.out.println(" Text: " + it);
                });
            }
//
//            stripper.setStartPage(0);
//            stripper.setEndPage(pdDocument.getNumberOfPages());
//            System.out.println(stripper.getText(pdDocument));

//            int cont = 0;
//            ImageToPdf imageToPdf = new ImageToPdf();
//            for (PDPage page : pdDocument.getPages()){
//                cont++;
//                System.out.println("Processing page " + cont);
//                imageToPdf.processPage(page);
//            }
            pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
