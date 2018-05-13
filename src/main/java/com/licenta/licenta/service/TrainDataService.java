package com.licenta.licenta.service;

import com.licenta.licenta.model.TitleFeatures;
import com.licenta.licenta.repository.TitleFeaturesRepository;
import com.licenta.licenta.representations.TextStripper;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TrainDataService {

    private final TitleFeaturesRepository titleFeaturesRepository;
    private final FeaturesService featuresService;

    @Autowired
    public TrainDataService(TitleFeaturesRepository titleFeaturesRepository, FeaturesService featuresService) {
        this.titleFeaturesRepository = titleFeaturesRepository;
        this.featuresService = featuresService;
    }

    public String extractTextFromPDF() {
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
                    TitleFeatures features = new TitleFeatures();
                    features.setDocumentName("PDF Timpul august 2016_BT.pdf");
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
                    titleFeaturesRepository.save(features);

                    System.out.println("\t isBold: " + featuresService.isBold(it) + " isItalic: " + featuresService.isItalic(it)
                            + " isGraterThanMeanMax: " + featuresService.isGraterThanMeanMax(it, meanMax)
                            + " containsQuotationMark: " + featuresService.containsQuotationMark(it)
                            + " isInitCap: " + featuresService.isInitCap(it) + " isAlpha: " + featuresService.isAlpha(it)
                            + " isLengthLowerThanLengthMean: " + featuresService.isLengthLowerThanLengthMean(it, meanLength)
                            + " isInFirst10Rows: " + featuresService.isInFirst10Rows(entry.getValue(), it));
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

    public void test(){
        featuresService.containsBetweenTwoAndThreeWords("Ana are mere si pere");
        featuresService.isAllWordsInitCap("ana are mere");
    }
}
