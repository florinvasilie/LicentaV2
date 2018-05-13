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
public class BayesNaiveTitleService {
    private final TitleFeaturesRepository titleFeaturesRepository;
    private final FeaturesService featuresService;

    @Autowired
    public BayesNaiveTitleService(TitleFeaturesRepository titleFeaturesRepository, FeaturesService featuresService) {
        this.titleFeaturesRepository = titleFeaturesRepository;
        this.featuresService = featuresService;
    }

    public String testNaiveBayse() {
        try {
            PDDocument pdDocument = PDDocument.load(new File(
                    "C:\\Users\\fvasilie\\Desktop\\Arhiva TIMPUL\\" +
                            "Arhiva TIMPUL\\Timpul - 2016\\TIMPUL-aprilie-2016.pdf"));

            PDFTextStripper stripper = new TextStripper();

            Map<Integer, List<String>> textPerPage = new HashMap<>();
            for (Integer i = 1; i < 2; i++) {
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

                    boolean naiveBayes = doBayesNaive(featuresService.isGraterThanMeanMax(it, meanMax), featuresService.isInitCap(it),
                            featuresService.isBold(it), featuresService.isItalic(it), featuresService.isInFirst10Rows(entry.getValue(), it),
                            featuresService.isAlpha(it), featuresService.containsQuotationMark(it),
                            featuresService.isLengthLowerThanLengthMean(it, meanLength));

                    if  (naiveBayes){
                        System.out.println("Title: " +  featuresService.getText(it));
                    }
//                    else {
//                        System.out.println("Nu este titlu: " + it);
//                    }
                });
            }
            pdDocument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Boolean doBayesNaive(boolean isGreaterThanMeanMax, boolean isInitCap, boolean isBold, boolean isItalic,
                                 boolean isInFirst10Rows, boolean isAlpha, boolean containsQuotationMark, boolean isLengthLowerThanLengthMean) {
        //for title = false

        //P(isGreaterThanMeanMax = ? | isTitle = false) * P(isInitCap = ? | isTitle = false) * P(isBold = ? | isTitle = false) *
        //P(isItalic = ? | isTitle = false) * P(isInFirst10Rows = ? | isTitle = false) * P(isAlpha = ? | isTitle = false) *
        //P(containsQuotationMark = ? | isTitle = false) * P(isLengthLowerThanThanLengthMean = ? | isTitle = false) * P(isTitle = false)

        double titleFalse = probabilityForGreaterThanMeanMax(isGreaterThanMeanMax, false)
                * probabilityForIsInitCap(isInitCap, false) * probabilityForBold(isBold, false)
                * probabilityForIsItalic(isItalic, false) * probabilityForIsInFirst10Rows(isInFirst10Rows, false)
                * probabilityForIsAlpha(isAlpha, false) * probabilityForContainsQuotationMark(containsQuotationMark, false)
                * probabilityForIsLengthLowerThanLengthMean(isLengthLowerThanLengthMean, false)
                * probabilityTitleFalse();


        //for title = true

        //P(isGreaterThanMeanMax = ? | isTitle = true) * P(isInitCap = ? | isTitle = true) * P(isBold = ? | isTitle = true) *
        //P(isItalic = ? | isTitle = true) * P(isInFirst10Rows = ? | isTitle = true) * P(isAlpha = ? | isTitle = true) *
        //P(containsQuotationMark = ? | isTitle = true) * P(isLengthLowerThanThanLengthMean = ? | isTitle = true) * P(isTitle = true)

        double titleTrue = probabilityForGreaterThanMeanMax(isGreaterThanMeanMax, true)
                * probabilityForIsInitCap(isInitCap, true) * probabilityForBold(isBold, true)
                * probabilityForIsItalic(isItalic, true) * probabilityForIsInFirst10Rows(isInFirst10Rows, true)
                * probabilityForIsAlpha(isAlpha, true) * probabilityForContainsQuotationMark(containsQuotationMark, true)
                * probabilityForIsLengthLowerThanLengthMean(isLengthLowerThanLengthMean, true)
                * probabilityTitleTrue();
        return titleTrue > titleFalse;
    }

    //P(isTitle = false)
    private Double probabilityTitleFalse() {
        Double countByIsTitleFalse = titleFeaturesRepository.findIsTitle(false);
        if (countByIsTitleFalse == 0.0)
            return 1.0;
        else
            return (countByIsTitleFalse / titleFeaturesRepository.count());
    }

    //P(isTitle = true)
    private Double probabilityTitleTrue() {
        Double countByIsTitleFalse = titleFeaturesRepository.findIsTitle(true);
        if (countByIsTitleFalse == 0.0)
            return 1.0;
        else
            return (countByIsTitleFalse / titleFeaturesRepository.count());
    }

    //P(isGreaterThanMeanMax = ? | isTitle = ?)
    private Double probabilityForGreaterThanMeanMax(boolean isGreaterThanMeanMax, boolean isTitle) {
        Double isGreaterThanMeanMaxAndIsTitle = titleFeaturesRepository.findIsGreaterThanMeanMaxAndIsTitle(isGreaterThanMeanMax, isTitle);
        if (isGreaterThanMeanMaxAndIsTitle == 0.0)
            return 1.0;
        else
            return isGreaterThanMeanMaxAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isInitCap = ? | isTitle = ?)
    private Double probabilityForIsInitCap(boolean isInitCap, boolean isTitle) {
        Double isInitCapAndIsTitle = titleFeaturesRepository.findIsInitCapAndIsTitle(isInitCap, isTitle);
        if (isInitCapAndIsTitle == 0.0)
            return 1.0;
        else
            return isInitCapAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isBold = ? | isTitle = ?)
    private Double probabilityForBold(boolean isBold, boolean isTitle) {
        Double isBoldAndIsTitle = titleFeaturesRepository.findIsBoldAndIsTitle(isBold, isTitle);
        if (isBoldAndIsTitle == 0.0)
            return 1.0;
        else
            return isBoldAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isItalic = ? | isTitle = ?)
    private Double probabilityForIsItalic(boolean isItalic, boolean isTitle) {
        Double isItalicAndIsTitle = titleFeaturesRepository.findIsItalicAndIsTitle(isItalic, isTitle);
        if (isItalicAndIsTitle == 0.0)
            return 1.0;
        else
            return isItalicAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isInFirst10Rows = ? | isTitle = ?)
    private Double probabilityForIsInFirst10Rows(boolean isInFirst10Rows, boolean isTitle) {
        Double isInFirst10RowsAndIsTitle = titleFeaturesRepository.findIsInFirst10RowsAndIsTitle(isInFirst10Rows, isTitle);
        if (isInFirst10RowsAndIsTitle == 0.0)
            return 1.0;
        else
            return isInFirst10RowsAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isAlpha = ? | isTitle = ?)
    private Double probabilityForIsAlpha(boolean isAlpha, boolean isTitle) {
        Double isAlphaAndIsTitle = titleFeaturesRepository.findIsAlphaAndIsTitle(isAlpha, isTitle);
        if (isAlphaAndIsTitle == 0.0)
            return 1.0;
        else
            return isAlphaAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(containsQuotationMark = ? | isTitle = ?)
    private Double probabilityForContainsQuotationMark(boolean containsQuotationMark, boolean isTitle) {
        Double containsQuotationMarkAndIsTitle = titleFeaturesRepository.findContainsQuotationMarkAndIsTitle(containsQuotationMark, isTitle);
        if (containsQuotationMarkAndIsTitle == 0.0)
            return 1.0;
        else
            return containsQuotationMarkAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }

    //P(isLengthLowerThanLengthMean = ? | isTitle = ?)
    private Double probabilityForIsLengthLowerThanLengthMean(boolean isLengthLowerThan15, boolean isTitle) {
        Double isLengthLowerThanLengthMeanAndIsTitle = titleFeaturesRepository.findIsLengthLowerThanLengthMeanAndIsTitle(isLengthLowerThan15, isTitle);
        if (isLengthLowerThanLengthMeanAndIsTitle == 0.0)
            return 1.0;
        else
            return isLengthLowerThanLengthMeanAndIsTitle / titleFeaturesRepository.findIsTitle(isTitle);
    }
}
//
//    //for title = false
//
//    //P(isGreaterThanMeanMax = ? | isTitle = false) * P(isInitCap = ? | isTitle = false) * P(isBold = ? | isTitle = false) *
//    //P(isItalic = ? | isTitle = false) * P(isInFirst10Rows = ? | isTitle = false) * P(isAlpha = ? | isTitle = false) *
//    //P(containsQuotationMark = ? | isTitle = false) * P(isLengthLowerThanThanLengthMean = ? | isTitle = false) * P(isTitle = false)
//
//    double titleFalse = probabilityForGreaterThanMeanMax(true, false)
//            * probabilityForIsInitCap(true, false) * probabilityForBold(true, false)
//            * probabilityForIsItalic(true, false) * probabilityForIsInFirst10Rows(true, false)
//            * probabilityForIsAlpha(true, false) * probabilityForContainsQuotationMark(true, false)
//            * probabilityForIsLengthLowerThanLengthMean(true, false)
//            * probabilityTitleFalse();
//
//        System.out.println("isTitle: false: "+titleFalse);
//
//
////for title = true
//
////P(isGreaterThanMeanMax = ? | isTitle = true) * P(isInitCap = ? | isTitle = true) * P(isBold = ? | isTitle = true) *
////P(isItalic = ? | isTitle = true) * P(isInFirst10Rows = ? | isTitle = true) * P(isAlpha = ? | isTitle = true) *
////P(containsQuotationMark = ? | isTitle = true) * P(isLengthLowerThanThanLengthMean = ? | isTitle = true) * P(isTitle = true)
//                double titleTrue=probabilityForGreaterThanMeanMax(true,true)
//                *probabilityForIsInitCap(true,true)*probabilityForBold(true,true)
//                *probabilityForIsItalic(true,true)*probabilityForIsInFirst10Rows(true,true)
//                *probabilityForIsAlpha(true,true)*probabilityForContainsQuotationMark(true,true)
//                *probabilityForIsLengthLowerThanLengthMean(true,true)
//                *probabilityTitleTrue();
//
//                System.out.println("titleTrue: true: "+titleTrue);
//                System.out.println(" probabilityTitleFalse: "+probabilityTitleFalse());
//                System.out.println(" probabilityTitleTrue: "+probabilityTitleTrue());
//                System.out.println(" probabilityForGreaterThanMeanMax: "+probabilityForGreaterThanMeanMax(true,false));
//                System.out.println(" probabilityForIsInitCap: "+probabilityForIsInitCap(true,false));
//                System.out.println(" probabilityForBold: "+probabilityForBold(true,false));
//                System.out.println(" probabilityForIsItalic: "+probabilityForIsItalic(true,false));
//                System.out.println(" probabilityForIsInFirst10Rows: "+probabilityForIsInFirst10Rows(true,false));
//                System.out.println(" probabilityForIsAlpha: "+probabilityForIsAlpha(true,false));
//                System.out.println(" probabilityForContainsQuotationMark: "+probabilityForContainsQuotationMark(true,false));
//                System.out.println(" probabilityForIsLengthLowerThanLengthMean: "+probabilityForIsLengthLowerThanLengthMean(true,false));