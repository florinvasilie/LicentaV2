package com.licenta.licenta.service;

import com.licenta.licenta.repository.AuthorFeaturesRepository;
import com.licenta.licenta.repository.TitleFeaturesRepository;
import com.licenta.licenta.representations.TextStripper;
import com.licenta.licenta.representations.resp.*;
import com.licenta.licenta.service.probabilities.AuthorProbabilities;
import com.licenta.licenta.service.probabilities.TitleProbabilities;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class BayesNaiveService {
    private final TitleFeaturesRepository titleFeaturesRepository;
    private final AuthorFeaturesRepository authorFeaturesRepository;
    private final FeaturesService featuresService;
    private final TitleProbabilities titleProbabilities;
    private final AuthorProbabilities authorProbabilities;

    @Autowired
    public BayesNaiveService(TitleFeaturesRepository titleFeaturesRepository, AuthorFeaturesRepository authorFeaturesRepository, FeaturesService featuresService,
                             TitleProbabilities titleProbabilities, AuthorProbabilities authorProbabilities) {
        this.titleFeaturesRepository = titleFeaturesRepository;
        this.authorFeaturesRepository = authorFeaturesRepository;
        this.featuresService = featuresService;
        this.titleProbabilities = titleProbabilities;
        this.authorProbabilities = authorProbabilities;
    }

    public List<AuthorAndTitle> naiveBayes(MultipartFile pdfFile) {
        List<AuthorAndTitle> authorAndTitleList = new ArrayList<>();
        try {
            PDDocument pdDocument = PDDocument.load(pdfFile.getInputStream());
            titleProbabilities.calculateProbabilities();
            authorProbabilities.calculateProbabilities();
            Map<Integer, List<String>> textPerPage = loadPdf(pdDocument);
            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                Double meanMax = featuresService.meanMax(entry.getValue());
                Double meanLength = featuresService.meanLength(entry.getValue());
                AuthorAndTitle authorAndTitle = new AuthorAndTitle();
                authorAndTitle.setPage(entry.getKey());
                List<String> authorList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();
                entry.getValue().parallelStream().forEach(it -> {
                    Pair<Boolean, Double> naiveBayesTitle = doNaiveBayesForTitle(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it), featuresService.isItalic(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it),
                            featuresService.isAlpha(it), featuresService.containsQuotationMark(it),
                            featuresService.isLengthLowerThanLengthMean(it, meanLength));

                    Pair<Boolean, Double> naiveBayesAuthor = doNaiveBayesForAuthor(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it), featuresService.isAlpha(it),
                            featuresService.isFirstCharAlpha(it), featuresService.isLastCharAlpha(it),
                            featuresService.isAllWordsInitCap(it), featuresService.containsBetweenTwoAndThreeWords(it));

                    if (naiveBayesTitle.getLeft() && !naiveBayesAuthor.getLeft()) {
                        if (!featuresService.getText(it).contains("www.") &&
                                !StringUtils.trimAllWhitespace(featuresService.getText(it)).chars().allMatch(Character::isDigit)
                                && !featuresService.getText(it).contains("Nr.") && !featuresService.getText(it).equals("Fondat la 15 martie 1876")
                                && !featuresService.getText(it).contains("‑")) {
                            titleList.add(it);
                        }
                        if (featuresService.getText(it).contains("‑")) {
                            authorList.add(featuresService.getText(it));
                        }

                    }

                    if (naiveBayesAuthor.getLeft()) {
                        authorList.add(featuresService.getText(it));
                    }
                });
                titleList.sort(Comparator.comparing(featuresService::distanceBetweenOriginAndAPoint));
                Map<Integer, List<String>> integerSetHashMap = new HashMap<>();
                Set<String> stringSet = new LinkedHashSet<>();
                for (int i = 0; i < titleList.size(); i++) {
                    double minDistance = Double.MAX_VALUE;
                    String text = "";
                    for (int j = i + 1; j < titleList.size(); j++) {
                        double distance = Math.sqrt(Math.pow((featuresService.getX(titleList.get(i)) - featuresService.getX(titleList.get(j))), 2)
                                + Math.pow((featuresService.getY(titleList.get(i)) - featuresService.getY(titleList.get(j))), 2));
                        if (minDistance > distance) {
                            minDistance = distance;
                            text = (titleList.get(j));
                        }
                    }
                    if (minDistance < featuresService.getFontSize(titleList.get(i)) + featuresService.getFontSize(text)) {
                        stringSet.add(featuresService.getText(titleList.get(i)));
                        stringSet.add(featuresService.getText(text));
                    } else {
                        if (!stringSet.isEmpty()) {
                            List<String> test = new ArrayList<>();
                            test.addAll(stringSet);
                            String test1 = test.get(test.size() - 1);
                            if (test1.charAt(test1.length() - 1) != ' ')
                                test1 = " " + test1;
                            test.remove(test.size() - 1);
                            test.add(test1);
                            integerSetHashMap.put(i, test);
                            stringSet.clear();
                            System.out.println();
                        } else {
                            stringSet.add(featuresService.getText(titleList.get(i)));
                            List<String> test = new ArrayList<>();
                            test.addAll(stringSet);
                            integerSetHashMap.put(i, test);
                            stringSet.clear();
                            System.out.println();
                        }
                    }
                }

                List<String> finalTitleList = new ArrayList<>();

                for (Map.Entry<Integer, List<String>> entry1 : integerSetHashMap.entrySet()) {
                    String multiple = "";
                    for (String it : entry1.getValue()) {
                        multiple += it;
                    }
                    finalTitleList.add(multiple);
                }

                authorAndTitle.setAuthor(authorList);
                authorAndTitle.setTitle(finalTitleList);
                authorAndTitleList.add(authorAndTitle);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return authorAndTitleList;
    }

    public List<AuthorTitleResp> naiveBayesCorola(MultipartFile pdfFile) {
        List<AuthorTitleResp> authorAndTitleList = new ArrayList<>();
        try {
            titleProbabilities.calculateProbabilities();
            authorProbabilities.calculateProbabilities();
            PDDocument pdDocument = PDDocument.load(pdfFile.getInputStream());
            Map<Integer, List<String>> textPerPage = loadPdf(pdDocument);
            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                Double meanMax = featuresService.meanMax(entry.getValue());
                Double meanLength = featuresService.meanLength(entry.getValue());
                AuthorTitleResp authorTitleResp = new AuthorTitleResp();
                authorTitleResp.setPage(entry.getKey());
                authorTitleResp.setTextPerPage(getTextForPage(entry.getKey(), pdDocument));
                List<String> authorList = new ArrayList<>();
                List<String> titleList = new ArrayList<>();
                entry.getValue().parallelStream().forEach(it -> {
                    Pair<Boolean, Double> naiveBayesTitle = doNaiveBayesForTitle(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it), featuresService.isItalic(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it),
                            featuresService.isAlpha(it), featuresService.containsQuotationMark(it),
                            featuresService.isLengthLowerThanLengthMean(it, meanLength));

                    Pair<Boolean, Double> naiveBayesAuthor = doNaiveBayesForAuthor(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it), featuresService.isAlpha(it),
                            featuresService.isFirstCharAlpha(it), featuresService.isLastCharAlpha(it),
                            featuresService.isAllWordsInitCap(it), featuresService.containsBetweenTwoAndThreeWords(it));

                    if (naiveBayesTitle.getLeft() && !naiveBayesAuthor.getLeft()) {
                        if (!featuresService.getText(it).contains("www.") &&
                                !StringUtils.trimAllWhitespace(featuresService.getText(it)).chars().allMatch(Character::isDigit)
                                && !featuresService.getText(it).contains("Nr.") && !featuresService.getText(it).equals("Fondat la 15 martie 1876")
                                && !featuresService.getText(it).contains("‑")) {
                            titleList.add(it);
                        }
                        System.out.println();
                        if (featuresService.getText(it).contains("‑")) {
                            authorList.add(it);
                        }
                    }

                    if (naiveBayesAuthor.getLeft()) {
                        if (featuresService.getText(it).contains(" "))
                            authorList.add(it);
                    }
                });
//                titleList.sort(Comparator.comparing(featuresService::getX));

                titleList.sort(Comparator.comparing(featuresService::distanceBetweenOriginAndAPoint));
                authorList.sort(Comparator.comparing(featuresService::distanceBetweenOriginAndAPoint));

//                titleList.forEach(System.out::println);


                Map<Integer, List<String>> integerSetHashMap = new HashMap<>();
                Set<String> stringSet = new LinkedHashSet<>();
                for (int i = 0; i < titleList.size(); i++) {
                    double minDistance = Double.MAX_VALUE;
                    String text = "";
                    for (int j = i + 1; j < titleList.size(); j++) {
                        double distance = featuresService.distanceBetweenTwoPoints(titleList.get(i), titleList.get(j));
//                        double distance = Math.sqrt(Math.pow((featuresService.getX(titleList.get(i)) - featuresService.getX(titleList.get(j))), 2)
//                                + Math.pow((featuresService.getY(titleList.get(i)) - featuresService.getY(titleList.get(j))), 2));
                        if (minDistance > distance) {
                            minDistance = distance;
                            text = (titleList.get(j));
                        }
                    }
//                    System.out.println(featuresService.getText(titleList.get(i)) + " vs " + featuresService.getText(text) + " min: " + minDistance);

                    if (minDistance < featuresService.getFontSize(titleList.get(i)) + featuresService.getFontSize(text)) {
                        stringSet.add(featuresService.getText(titleList.get(i)));
                        stringSet.add(featuresService.getText(text));
                    } else {
                        if (!stringSet.isEmpty()) {
                            List<String> test = new ArrayList<>();
                            test.addAll(stringSet);
                            String test1 = test.get(test.size() - 1);
                            if (test1.charAt(test1.length() - 1) != ' ')
                                test1 = " " + test1;
                            test.remove(test.size() - 1);
                            test.add(test1);
                            integerSetHashMap.put(i, test);
                            stringSet.clear();
                        } else {
                            stringSet.add(featuresService.getText(titleList.get(i)));
                            List<String> test = new ArrayList<>();
                            test.addAll(stringSet);
                            integerSetHashMap.put(i, test);
                            stringSet.clear();
                        }
                    }
                }

                List<String> finalTitleList = new ArrayList<>();
                List<String> finalAuthorList = new ArrayList<>();

                for (Map.Entry<Integer, List<String>> entry1 : integerSetHashMap.entrySet()) {
                    String multiple = "";
                    for (String it : entry1.getValue()) {
                        multiple += it;
                    }
                    finalTitleList.add(multiple);
                }

                Map<String, String> testMap = new HashMap<>();
                List<AuthorWithTitlesResp> authorWithTitlesRespList = new ArrayList<>();
                if (authorList.size() > 1) {
                    for (int i = 0; i < authorList.size(); i++) {
                        double minDistance = Double.MAX_VALUE;
                        String text = "";
                        for (int j = 0; j < titleList.size(); j++) {
                            double distance = featuresService.distanceBetweenTwoPoints(authorList.get(i), titleList.get(j));
                            if (minDistance > distance) {
                                minDistance = distance;
                                text = featuresService.getText(titleList.get(j));
                            }
                        }
                        boolean distinct = true;
                        System.out.println(featuresService.getText(authorList.get(i)) + " vs " + text);
                        for (int k = 0; k < finalTitleList.size() && distinct; k++) {
                            if (finalTitleList.get(k).contains(text)) {
                                testMap.put(finalTitleList.get(k), featuresService.getText(authorList.get(i)));
                                distinct = false;
                                finalTitleList.remove(k);

                            } else {
                                testMap.putIfAbsent(finalTitleList.get(k), "");
                            }
                        }
                    }
                } else {
                    if (authorList.size() != 0) {
                        AuthorWithTitlesResp authorWithTitlesResp = new AuthorWithTitlesResp();
                        authorWithTitlesResp.setAuthor(featuresService.getText(authorList.get(0)));
                        authorWithTitlesResp.setTitles(finalTitleList);
                        authorWithTitlesRespList.add(authorWithTitlesResp);
                    } else {
                        AuthorWithTitlesResp authorWithTitlesResp = new AuthorWithTitlesResp();
                        authorWithTitlesResp.setAuthor("");
                        authorWithTitlesResp.setTitles(finalTitleList);
                        authorWithTitlesRespList.add(authorWithTitlesResp);
                    }
                }
                for (Map.Entry<String, String> entry2 : testMap.entrySet()) {
                    AuthorWithTitlesResp authorWithTitlesResp = new AuthorWithTitlesResp();
                    authorWithTitlesResp.setAuthor(entry2.getValue());
                    authorWithTitlesResp.setTitles(Arrays.asList(entry2.getKey()));
                    authorWithTitlesRespList.add(authorWithTitlesResp);
//                    System.out.println("title = " + entry2.getKey());
//                    System.out.println("author = " + entry2.getValue());
                }
                authorTitleResp.setAuthorWithTitlesRespList(authorWithTitlesRespList);
                authorAndTitleList.add(authorTitleResp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return authorAndTitleList;
    }

    public List<AuthorResponse> authorNaiveBayes(MultipartFile pdfFile) {
        List<AuthorResponse> authorResponseList = new ArrayList<>();
        try {
            PDDocument pdDocument = PDDocument.load(pdfFile.getInputStream());
            Map<Integer, List<String>> textPerPage = loadPdf(pdDocument);
            authorProbabilities.calculateProbabilities();
            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                AuthorResponse authorResponse = new AuthorResponse();
                authorResponse.setPage(entry.getKey());
                List<String> authorList = new ArrayList<>();
                Double meanMax = featuresService.meanMax(entry.getValue());
                entry.getValue().parallelStream().forEach(it -> {
                    Pair<Boolean, Double> naiveBayesAuthor = doNaiveBayesForAuthor(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it), featuresService.isAlpha(it),
                            featuresService.isFirstCharAlpha(it), featuresService.isLastCharAlpha(it),
                            featuresService.isAllWordsInitCap(it), featuresService.containsBetweenTwoAndThreeWords(it));

                    if (naiveBayesAuthor.getLeft()) {
                        authorList.add(featuresService.getText(it));
                    }
                });
                authorResponse.setAuthor(authorList);
                authorResponseList.add(authorResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return authorResponseList;
    }

    public List<TitleResponse> titleNaiveBayes(MultipartFile pdfFile) {
        List<TitleResponse> titleResponseList = new ArrayList<>();
        try {
            PDDocument pdDocument = PDDocument.load(pdfFile.getInputStream());
            Map<Integer, List<String>> textPerPage = loadPdf(pdDocument);
            titleProbabilities.calculateProbabilities();
            for (Map.Entry<Integer, List<String>> entry : textPerPage.entrySet()) {
                TitleResponse titleResponse = new TitleResponse();
                titleResponse.setPage(entry.getKey());
                List<String> titleList = new ArrayList<>();
                Double meanMax = featuresService.meanMax(entry.getValue());
                Double meanLength = featuresService.meanLength(entry.getValue());
                entry.getValue().parallelStream().forEach(it -> {
                    Pair<Boolean, Double> naiveBayesTitle = doNaiveBayesForTitle(featuresService.isGraterThanMeanMax(it, meanMax),
                            featuresService.isInitCap(it), featuresService.isBold(it), featuresService.isItalic(it),
                            featuresService.isInFirst10Rows(entry.getValue(), it),
                            featuresService.isAlpha(it), featuresService.containsQuotationMark(it),
                            featuresService.isLengthLowerThanLengthMean(it, meanLength));

                    if (naiveBayesTitle.getLeft()) {
                        titleList.add(featuresService.getText(it));
                    }
                });
                titleResponse.setTitle(titleList);
                titleResponseList.add(titleResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return titleResponseList;
    }


    private Map<Integer, List<String>> loadPdf(PDDocument pdDocument) throws IOException {
        PDFTextStripper stripper = new TextStripper();
        Map<Integer, List<String>> textPerPage = new HashMap<>();
        for (Integer i = 1; i < pdDocument.getNumberOfPages() + 1; i++) {
            stripper.setStartPage(i);
            stripper.setEndPage(i);
            textPerPage.put(i, featuresService.getLinesFrom(stripper.getText(pdDocument)));
        }
        return textPerPage;
    }


    private String getTextForPage(Integer page, PDDocument pdDocument) throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        return stripper.getText(pdDocument);
    }

    private Pair<Boolean, Double> doNaiveBayesForTitle(boolean isGreaterThanMeanMax, boolean isInitCap, boolean isBold, boolean isItalic,
                                                       boolean isInFirst10Rows, boolean isAlpha, boolean containsQuotationMark,
                                                       boolean isLengthLowerThanLengthMean) {
        //for title = false

        //P(isGreaterThanMeanMax = ? | isTitle = false) * P(isInitCap = ? | isTitle = false) * P(isBold = ? | isTitle = false) *
        //P(isItalic = ? | isTitle = false) * P(isInFirst10Rows = ? | isTitle = false) * P(isAlpha = ? | isTitle = false) *
        //P(containsQuotationMark = ? | isTitle = false) * P(isLengthLowerThanThanLengthMean = ? | isTitle = false) * P(isTitle = false)

        double titleFalse = probabilityForGreaterThanMeanMaxTitleFalse(isGreaterThanMeanMax)
                * probabilityForIsInitCapTitleFalse(isInitCap)
                * probabilityForBoldTitleFalse(isBold)
                * probabilityForIsItalicTitleFalse(isItalic)
                * probabilityForIsInFirst10RowsTitleFalse(isInFirst10Rows)
                * probabilityForIsAlphaTitleFalse(isAlpha)
                * probabilityForContainsQuotationMarkTitleFalse(containsQuotationMark)
                * probabilityForIsLengthLowerThanLengthMeanTitleFalse(isLengthLowerThanLengthMean)
                * probabilityTitleFalse();


//        for title = true
//
//        P(isGreaterThanMeanMax = ? | isTitle = true) * P(isInitCap = ? | isTitle = true) * P(isBold = ? | isTitle = true) *
//        P(isItalic = ? | isTitle = true) * P(isInFirst10Rows = ? | isTitle = true) * P(isAlpha = ? | isTitle = true) *
//        P(containsQuotationMark = ? | isTitle = true) * P(isLengthLowerThanThanLengthMean = ? | isTitle = true) * P(isTitle = true)

        double titleTrue = probabilityForGreaterThanMeanMaxTitleTrue(isGreaterThanMeanMax)
                * probabilityForIsInitCapTitleTrue(isInitCap)
                * probabilityForBoldTitleTrue(isBold)
                * probabilityForIsItalicTitleTrue(isItalic)
                * probabilityForIsInFirst10RowsTitleTrue(isInFirst10Rows)
                * probabilityForIsAlphaTitleTrue(isAlpha)
                * probabilityForContainsQuotationMarkTitleTrue(containsQuotationMark)
                * probabilityForIsLengthLowerThanLengthMeanTitleTrue(isLengthLowerThanLengthMean)
                * probabilityTitleTrue();

        return Pair.of(titleTrue > titleFalse, titleTrue);
    }

    private Pair<Boolean, Double> doNaiveBayesForAuthor(boolean isGreaterThanMeanMax, boolean isInitCap, boolean isBold,
                                                        boolean isInFirst10Rows, boolean isAlpha, boolean isFirstCharAlpha,
                                                        boolean isLastCharAlpha, boolean isAllWordsInitCap,
                                                        boolean containsBetweenTwoAndThreeWords) {
        //for author = false

        //P(isGreaterThanMeanMax = ? | author = false) * P(isInitCap = ? | author = false) * P(isBold = ? | author = false) *
        //P(isInFirst10Rows = ? | author = false) * P(isAlpha = ? | author = false) *
        //P(isFirstCharAlpha = ? | author = false) * P(isLastCharAlpha = ? | author = false) * P(isAllWordsInitCap = + author = false) *
        //P(containsBetweenTwoAndThreeWords = ? | author = false) * P(author = false)

        double authorFalse =
//                probabilityForGreaterThanMeanMaxAuthorFalse(isGreaterThanMeanMax)
//                * probabilityForIsInitCapAuthorFalse(isInitCap) *
                probabilityForBoldAuthorFalse(isBold)
                        * probabilityForIsInFirst10RowsAuthorFalse(isInFirst10Rows) * probabilityForIsAlphaAuthorFalse(isAlpha)
                        * probabilityForIsFirstCharAlphaAuthorFalse(isFirstCharAlpha) * probabilityForIsLastCharAlphaAuthorFalse(isLastCharAlpha)
                        * probabilityForIsAllWordsInitCapAuthorFalse(isAllWordsInitCap)
                        * probabilityForContainsBetween2And3WordsAuthorFalse(containsBetweenTwoAndThreeWords)
                        * probabilityAuthorFalse();

//        System.out.println("probabilityForGreaterThanMeanMaxAuthorFalse: " +
//                probabilityForGreaterThanMeanMaxAuthorFalse(isGreaterThanMeanMax));
//        System.out.println("probabilityForIsInitCapAuthorFalse: " +
//                probabilityForIsInitCapAuthorFalse(isInitCap));
//        System.out.println("probabilityForBoldAuthorFalse(isBold): " +
//                probabilityForBoldAuthorFalse(isBold));
//        System.out.println("probabilityForIsInFirst10RowsAuthorFalse(isInFirst10Rows): " +
//                probabilityForIsInFirst10RowsAuthorFalse(isInFirst10Rows));
//        System.out.println("probabilityForIsAlphaAuthorFalse(isAlpha): " +
//                probabilityForIsAlphaAuthorFalse(isAlpha));
//        System.out.println("probabilityForIsFirstCharAlphaAuthorFalse(isFirstCharAlpha): " +
//                probabilityForIsFirstCharAlphaAuthorFalse(isFirstCharAlpha));
//        System.out.println("probabilityForIsLastCharAlphaAuthorFalse(isLastCharAlpha): " +
//                probabilityForIsLastCharAlphaAuthorFalse(isLastCharAlpha));
//        System.out.println("probabilityForIsAllWordsInitCapAuthorFalse(isAllWordsInitCap): " +
//                probabilityForIsAllWordsInitCapAuthorFalse(isAllWordsInitCap));
//        System.out.println("probabilityForContainsBetween2And3WordsAuthorFalse(containsBetweenTwoAndThreeWords): " +
//                probabilityForContainsBetween2And3WordsAuthorFalse(containsBetweenTwoAndThreeWords));
//        System.out.println("probabilityAuthorFalse(): " +
//                probabilityAuthorFalse());
//
//        System.out.println("authorFalse: " + authorFalse);


        //for author = true

        //P(isGreaterThanMeanMax = ? | author = true) * P(isInitCap = ? | author = true) * P(isBold = ? | author = true) *
        //P(isInFirst10Rows = ? | author = true) * P(isAlpha = ? | author = true) *
        //P(isFirstCharAlpha = ? | author = true) * P(isLastCharAlpha = ? | author = true) * P(isAllWordsInitCap = ? author = true) *
        //P(containsBetweenTwoAndThreeWords = ? | author = true) * P(author = true)

        double authorTrue =
//                probabilityForGreaterThanMeanMaxAuthorTrue(isGreaterThanMeanMax)
//                * probabilityForIsInitCapAuthorTrue(isInitCap) *
                probabilityForBoldAuthorTrue(isBold)
                        * probabilityForIsInFirst10RowsAuthorTrue(isInFirst10Rows) * probabilityForIsAlphaAuthorTrue(isAlpha)
                        * probabilityForIsFirstCharAlphaAuthorTrue(isFirstCharAlpha) * probabilityForIsLastCharAlphaAuthorTrue(isLastCharAlpha)
                        * probabilityForIsAllWordsInitCapAuthorTrue(isAllWordsInitCap)
                        * probabilityForContainsBetween2And3WordsAuthorTrue(containsBetweenTwoAndThreeWords)
                        * probabilityAuthorTrue();


//        System.out.println("probabilityForGreaterThanMeanMaxAuthorTrue: " +
//                probabilityForGreaterThanMeanMaxAuthorTrue(isGreaterThanMeanMax));
//        System.out.println("probabilityForIsInitCapAuthorTrue: " +
//                probabilityForIsInitCapAuthorTrue(isInitCap));
//        System.out.println("probabilityForBoldAuthorTrue(isBold): " +
//                probabilityForBoldAuthorTrue(isBold));
//        System.out.println("probabilityForIsInFirst10RowsAuthorTrue(isInFirst10Rows): " +
//                probabilityForIsInFirst10RowsAuthorTrue(isInFirst10Rows));
//        System.out.println("probabilityForIsAlphaAuthorTrue(isAlpha): " +
//                probabilityForIsAlphaAuthorTrue(isAlpha));
//        System.out.println("probabilityForIsFirstCharAlphaAuthorTrue(isFirstCharAlpha): " +
//                probabilityForIsFirstCharAlphaAuthorTrue(isFirstCharAlpha));
//        System.out.println("probabilityForIsLastCharAlphaAuthorTrue(isLastCharAlpha): " +
//                probabilityForIsLastCharAlphaAuthorTrue(isLastCharAlpha));
//        System.out.println("probabilityForIsAllWordsInitCapAuthorTrue(isAllWordsInitCap): " +
//                probabilityForIsAllWordsInitCapAuthorTrue(isAllWordsInitCap));
//        System.out.println("probabilityForContainsBetween2And3WordsAuthorTrue(containsBetweenTwoAndThreeWords): " +
//                probabilityForContainsBetween2And3WordsAuthorTrue(containsBetweenTwoAndThreeWords));
//        System.out.println("probabilityAuthorTrue(): " +
//                probabilityAuthorTrue());


        return Pair.of(authorTrue > authorFalse, authorTrue);
    }

    //---------------------------------- for title ---------------------------------------------------------

    //P(isTitle = false)
    private Double probabilityTitleFalse() {
        Double countByIsTitleFalse = titleProbabilities.getProbabilityForIsTitleFalse();
        if (countByIsTitleFalse == 0.0)
            return 0.0009;
        else
            return (countByIsTitleFalse / titleFeaturesRepository.count());
    }

    //P(isTitle = true)
    private Double probabilityTitleTrue() {
        Double countByIsTitleTrue = titleProbabilities.getProbabilityForIsTitleTrue();
        if (countByIsTitleTrue == 0.0)
            return 0.0009;
        else
            return (countByIsTitleTrue / titleFeaturesRepository.count());
    }

    //P(isGreaterThanMeanMax = ? | isTitle = false)
    private Double probabilityForGreaterThanMeanMaxTitleFalse(boolean isGreaterThanMeanMax) {
        Double isGreaterThanMeanMaxAndIsTitle;
        if (isGreaterThanMeanMax) {
            isGreaterThanMeanMaxAndIsTitle = titleProbabilities.getProbabilityForGreaterThanMeanMaxTrueAndTitleFalse();
        } else {
            isGreaterThanMeanMaxAndIsTitle = titleProbabilities.getProbabilityForGreaterThanMeanMaxFalseAndTitleFalse();
        }
        if (isGreaterThanMeanMaxAndIsTitle == 0.0)
            return 0.0009;
        else
            return isGreaterThanMeanMaxAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isGreaterThanMeanMax = ? | isTitle = true)
    private Double probabilityForGreaterThanMeanMaxTitleTrue(boolean isGreaterThanMeanMax) {
        Double isGreaterThanMeanMaxAndIsTitle;
        if (isGreaterThanMeanMax) {
            isGreaterThanMeanMaxAndIsTitle = titleProbabilities.getProbabilityForGreaterThanMeanMaxTrueAndTitleTrue();
        } else {
            isGreaterThanMeanMaxAndIsTitle = titleProbabilities.getProbabilityForGreaterThanMeanMaxFalseAndTitleTrue();
        }
        if (isGreaterThanMeanMaxAndIsTitle == 0.0)
            return 0.0009;
        else
            return isGreaterThanMeanMaxAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isInitCap = ? | isTitle = false)
    private Double probabilityForIsInitCapTitleFalse(boolean isInitCap) {
        Double isInitCapAndIsTitle;
        if (isInitCap) {
            isInitCapAndIsTitle = titleProbabilities.getProbabilityForIsInitCapTrueAndTitleFalse();
        } else {
            isInitCapAndIsTitle = titleProbabilities.getProbabilityForIsInitCapFalseAndTitleFalse();
        }
        if (isInitCapAndIsTitle == 0.0)
            return 0.0009;
        else
            return isInitCapAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isInitCap = ? | isTitle = true)
    private Double probabilityForIsInitCapTitleTrue(boolean isInitCap) {
        Double isInitCapAndIsTitle;
        if (isInitCap) {
            isInitCapAndIsTitle = titleProbabilities.getProbabilityForIsInitCapTrueAndTitleTrue();
        } else {
            isInitCapAndIsTitle = titleProbabilities.getProbabilityForIsInitCapFalseAndTitleTrue();
        }
        if (isInitCapAndIsTitle == 0.0)
            return 0.0009;
        else
            return isInitCapAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isBold = ? | isTitle = false)
    private Double probabilityForBoldTitleFalse(boolean isBold) {
        Double isBoldAndIsTitle;
        if (isBold) {
            isBoldAndIsTitle = titleProbabilities.getProbabilityForBoldTrueAndTitleFalse();
        } else {
            isBoldAndIsTitle = titleProbabilities.getProbabilityForBoldFalseAndTitleFalse();
        }
        if (isBoldAndIsTitle == 0.0)
            return 0.0009;
        else
            return isBoldAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isBold = ? | isTitle = true)
    private Double probabilityForBoldTitleTrue(boolean isBold) {
        Double isBoldAndIsTitle;
        if (isBold) {
            isBoldAndIsTitle = titleProbabilities.getProbabilityForBoldTrueAndTitleTrue();
        } else {
            isBoldAndIsTitle = titleProbabilities.getProbabilityForBoldFalseAndTitleTrue();
        }
        if (isBoldAndIsTitle == 0.0)
            return 0.0009;
        else
            return isBoldAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isItalic = ? | isTitle = false)
    private Double probabilityForIsItalicTitleFalse(boolean isItalic) {
        Double isItalicAndIsTitle;
        if (isItalic) {
            isItalicAndIsTitle = titleProbabilities.getProbabilityForIsItalicTrueAndTitleFalse();
        } else {
            isItalicAndIsTitle = titleProbabilities.getProbabilityForIsItalicFalseAndTitleFalse();
        }
        if (isItalicAndIsTitle == 0.0)
            return 0.0009;
        else
            return isItalicAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isItalic = ? | isTitle = true)
    private Double probabilityForIsItalicTitleTrue(boolean isItalic) {
        Double isItalicAndIsTitle;
        if (isItalic) {
            isItalicAndIsTitle = titleProbabilities.getProbabilityForIsItalicTrueAndTitleTrue();
        } else {
            isItalicAndIsTitle = titleProbabilities.getProbabilityForIsItalicFalseAndTitleTrue();
        }
        if (isItalicAndIsTitle == 0.0)
            return 0.0009;
        else
            return isItalicAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isInFirst10Rows = ? | isTitle = false)
    private Double probabilityForIsInFirst10RowsTitleFalse(boolean isInFirst10Rows) {
        Double isInFirst10RowsAndIsTitle;
        if (isInFirst10Rows) {
            isInFirst10RowsAndIsTitle = titleProbabilities.getProbabilityForIsInFirst10RowsTrueAndTitleFalse();
        } else {
            isInFirst10RowsAndIsTitle = titleProbabilities.getProbabilityForIsInFirst10RowsFalseAndTitleFalse();
        }
        if (isInFirst10RowsAndIsTitle == 0.0)
            return 0.0009;
        else
            return isInFirst10RowsAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isInFirst10Rows = ? | isTitle = true)
    private Double probabilityForIsInFirst10RowsTitleTrue(boolean isInFirst10Rows) {
        Double isInFirst10RowsAndIsTitle;
        if (isInFirst10Rows) {
            isInFirst10RowsAndIsTitle = titleProbabilities.getProbabilityForIsInFirst10RowsTrueAndTitleTrue();
        } else {
            isInFirst10RowsAndIsTitle = titleProbabilities.getProbabilityForIsInFirst10RowsFalseAndTitleTrue();
        }
        if (isInFirst10RowsAndIsTitle == 0.0)
            return 0.0009;
        else
            return isInFirst10RowsAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isAlpha = ? | isTitle = false)
    private Double probabilityForIsAlphaTitleFalse(boolean isAlpha) {
        Double isAlphaAndIsTitle;
        if (isAlpha) {
            isAlphaAndIsTitle = titleProbabilities.getProbabilityForIsAlphaTrueAndTitleFalse();
        } else {
            isAlphaAndIsTitle = titleProbabilities.getProbabilityForIsAlphaFalseAndTitleFalse();
        }
        if (isAlphaAndIsTitle == 0.0)
            return 0.0009;
        else
            return isAlphaAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isAlpha = ? | isTitle = true)
    private Double probabilityForIsAlphaTitleTrue(boolean isAlpha) {
        Double isAlphaAndIsTitle;
        if (isAlpha) {
            isAlphaAndIsTitle = titleProbabilities.getProbabilityForIsAlphaTrueAndTitleTrue();
        } else {
            isAlphaAndIsTitle = titleProbabilities.getProbabilityForIsAlphaFalseAndTitleTrue();
        }
        if (isAlphaAndIsTitle == 0.0)
            return 0.0009;
        else
            return isAlphaAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(containsQuotationMark = ? | isTitle = false)
    private Double probabilityForContainsQuotationMarkTitleFalse(boolean containsQuotationMark) {
        Double containsQuotationMarkAndIsTitle;
        if (containsQuotationMark) {
            containsQuotationMarkAndIsTitle = titleProbabilities.getProbabilityForContainsQuotationMarkTrueAndTitleFalse();
        } else {
            containsQuotationMarkAndIsTitle = titleProbabilities.getProbabilityForContainsQuotationMarkFalseAndTitleFalse();
        }
        if (containsQuotationMarkAndIsTitle == 0.0)
            return 0.0009;
        else
            return containsQuotationMarkAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(containsQuotationMark = ? | isTitle = true)
    private Double probabilityForContainsQuotationMarkTitleTrue(boolean containsQuotationMark) {
        Double containsQuotationMarkAndIsTitle;
        if (containsQuotationMark) {
            containsQuotationMarkAndIsTitle = titleProbabilities.getProbabilityForContainsQuotationMarkTrueAndTitleTrue();
        } else {
            containsQuotationMarkAndIsTitle = titleProbabilities.getProbabilityForContainsQuotationMarkFalseAndTitleTrue();
        }
        if (containsQuotationMarkAndIsTitle == 0.0)
            return 0.0009;
        else
            return containsQuotationMarkAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //P(isLengthLowerThanLengthMean = ? | isTitle = false)
    private Double probabilityForIsLengthLowerThanLengthMeanTitleFalse(boolean isLengthLowerThan15) {
        Double isLengthLowerThanLengthMeanAndIsTitle;
        if (isLengthLowerThan15) {
            isLengthLowerThanLengthMeanAndIsTitle = titleProbabilities.getProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse();
        } else {
            isLengthLowerThanLengthMeanAndIsTitle = titleProbabilities.getProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse();

        }
        if (isLengthLowerThanLengthMeanAndIsTitle == 0.0)
            return 0.0009;
        else
            return isLengthLowerThanLengthMeanAndIsTitle / titleProbabilities.getProbabilityForIsTitleFalse();
    }

    //P(isLengthLowerThanLengthMean = ? | isTitle = true)
    private Double probabilityForIsLengthLowerThanLengthMeanTitleTrue(boolean isLengthLowerThan15) {
        Double isLengthLowerThanLengthMeanAndIsTitle;
        if (isLengthLowerThan15) {
            isLengthLowerThanLengthMeanAndIsTitle = titleProbabilities.getProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue();
        } else {
            isLengthLowerThanLengthMeanAndIsTitle = titleProbabilities.getProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue();

        }
        if (isLengthLowerThanLengthMeanAndIsTitle == 0.0)
            return 0.0009;
        else
            return isLengthLowerThanLengthMeanAndIsTitle / titleProbabilities.getProbabilityForIsTitleTrue();
    }

    //---------------------------------- for author ---------------------------------------------------------

    //P(isAuthor = false)
    private Double probabilityAuthorFalse() {
        Double countByIsAuthorFalse = authorProbabilities.getProbabilityForIsAuthorFalse();
        if (countByIsAuthorFalse == 0.0)
            return 0.0009;
        else
            return (countByIsAuthorFalse / authorFeaturesRepository.count());
    }

    //P(isAuthor = true)
    private Double probabilityAuthorTrue() {
        Double countByIsAuthorTrue = authorProbabilities.getProbabilityForIsAuthorTrue();
        if (countByIsAuthorTrue == 0.0)
            return 0.0009;
        else
            return (countByIsAuthorTrue / authorFeaturesRepository.count());
    }

    //P(isGreaterThanMeanMax = ? | isAuthor = false)
    private Double probabilityForGreaterThanMeanMaxAuthorFalse(boolean isGreaterThanMeanMax) {
        Double isGreaterThanMeanMaxAndIsAuthor;
        if (isGreaterThanMeanMax) {
            isGreaterThanMeanMaxAndIsAuthor = authorProbabilities.getProbabilityForGreaterThanMeanMaxTrueAndAuthorFalse();
        } else {
            isGreaterThanMeanMaxAndIsAuthor = authorProbabilities.getProbabilityForGreaterThanMeanMaxFalseAndAuthorFalse();
        }
        if (isGreaterThanMeanMaxAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isGreaterThanMeanMaxAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isGreaterThanMeanMax = ? | isAuthor = true)
    private Double probabilityForGreaterThanMeanMaxAuthorTrue(boolean isGreaterThanMeanMax) {
        Double isGreaterThanMeanMaxAndIsAuthor;
        if (isGreaterThanMeanMax) {
            isGreaterThanMeanMaxAndIsAuthor = authorProbabilities.getProbabilityForGreaterThanMeanMaxTrueAndAuthorTrue();
        } else {
            isGreaterThanMeanMaxAndIsAuthor = authorProbabilities.getProbabilityForGreaterThanMeanMaxFalseAndAuthorTrue();
        }
        if (isGreaterThanMeanMaxAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isGreaterThanMeanMaxAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isInitCap = ? | isAuthor = false)
    private Double probabilityForIsInitCapAuthorFalse(boolean isInitCap) {
        Double isInitCapAndIsAuthor;
        if (isInitCap) {
            isInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsInitCapTrueAndAuthorFalse();
        } else {
            isInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsInitCapFalseAndAuthorFalse();
        }
        if (isInitCapAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isInitCapAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isInitCap = ? | isAuthor = true)
    private Double probabilityForIsInitCapAuthorTrue(boolean isInitCap) {
        Double isInitCapAndIsAuthor;
        if (isInitCap) {
            isInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsInitCapTrueAndAuthorTrue();
        } else {
            isInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsInitCapFalseAndAuthorTrue();
        }
        if (isInitCapAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isInitCapAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isBold = ? | isAuthor = false)
    private Double probabilityForBoldAuthorFalse(boolean isBold) {
        Double isBoldAndIsAuthor;
        if (isBold) {
            isBoldAndIsAuthor = authorProbabilities.getProbabilityForBoldTrueAndAuthorFalse();
        } else {
            isBoldAndIsAuthor = authorProbabilities.getProbabilityForBoldFalseAndAuthorFalse();
        }
        if (isBoldAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isBoldAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isBold = ? | isAuthor = true)
    private Double probabilityForBoldAuthorTrue(boolean isBold) {
        Double isBoldAndIsAuthor;
        if (isBold) {
            isBoldAndIsAuthor = authorProbabilities.getProbabilityForBoldTrueAndAuthorTrue();
        } else {
            isBoldAndIsAuthor = authorProbabilities.getProbabilityForBoldFalseAndAuthorTrue();
        }
        if (isBoldAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isBoldAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isInFirst10Rows = ? | isTitle = false)
    private Double probabilityForIsInFirst10RowsAuthorFalse(boolean isInFirst10Rows) {
        Double isInFirst10RowsAndIsAuthor;
        if (isInFirst10Rows) {
            isInFirst10RowsAndIsAuthor = authorProbabilities.getProbabilityForIsInFirst10RowsTrueAndAuthorFalse();
        } else {
            isInFirst10RowsAndIsAuthor = authorProbabilities.getProbabilityForIsInFirst10RowsFalseAndAuthorFalse();
        }
        if (isInFirst10RowsAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isInFirst10RowsAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isInFirst10Rows = ? | isAuthor = true)
    private Double probabilityForIsInFirst10RowsAuthorTrue(boolean isInFirst10Rows) {
        Double isInFirst10RowsAndIsAuthor;
        if (isInFirst10Rows) {
            isInFirst10RowsAndIsAuthor = authorProbabilities.getProbabilityForIsInFirst10RowsTrueAndAuthorTrue();
        } else {
            isInFirst10RowsAndIsAuthor = authorProbabilities.getProbabilityForIsInFirst10RowsFalseAndAuthorTrue();
        }
        if (isInFirst10RowsAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isInFirst10RowsAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isAlpha = ? | isAuthor = false)
    private Double probabilityForIsAlphaAuthorFalse(boolean isAlpha) {
        Double isAlphaAndIsAuthor;
        if (isAlpha) {
            isAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsAlphaTrueAndAuthorFalse();
        } else {
            isAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsAlphaFalseAndAuthorFalse();
        }
        if (isAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isAlpha = ? | isAuthor = true)
    private Double probabilityForIsAlphaAuthorTrue(boolean isAlpha) {
        Double isAlphaAndIsAuthor;
        if (isAlpha) {
            isAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsAlphaTrueAndAuthorTrue();
        } else {
            isAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsAlphaFalseAndAuthorTrue();
        }
        if (isAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isFirstCharAlpha = ? | isAuthor = false)
    private Double probabilityForIsFirstCharAlphaAuthorFalse(boolean isFirstCharAlpha) {
        Double isFirstCharAlphaAndIsAuthor;
        if (isFirstCharAlpha) {
            isFirstCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsFirstCharAlphaTrueAndAuthorFalse();
        } else {
            isFirstCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsFirstCharAlphaFalseAndAuthorFalse();
        }
        if (isFirstCharAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isFirstCharAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isFirstCharAlpha = ? | isAuthor = true)
    private Double probabilityForIsFirstCharAlphaAuthorTrue(boolean isFirstCharAlpha) {
        Double isFirstCharAlphaAndIsAuthor;
        if (isFirstCharAlpha) {
            isFirstCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsFirstCharAlphaTrueAndAuthorTrue();
        } else {
            isFirstCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsFirstCharAlphaFalseAndAuthorTrue();
        }
        if (isFirstCharAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isFirstCharAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isLastCharAlpha = ? | isAuthor = false)
    private Double probabilityForIsLastCharAlphaAuthorFalse(boolean isLastCharAlpha) {
        Double isLastCharAlphaAndIsAuthor;
        if (isLastCharAlpha) {
            isLastCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsLastCharAlphaTrueAndAuthorFalse();
        } else {
            isLastCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsLastCharAlphaFalseAndAuthorFalse();
        }
        if (isLastCharAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isLastCharAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isLastCharAlpha = ? | isAuthor = true)
    private Double probabilityForIsLastCharAlphaAuthorTrue(boolean isLastCharAlpha) {
        Double isLastCharAlphaAndIsAuthor;
        if (isLastCharAlpha) {
            isLastCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsLastCharAlphaTrueAndAuthorTrue();
        } else {
            isLastCharAlphaAndIsAuthor = authorProbabilities.getProbabilityForIsLastCharAlphaFalseAndAuthorTrue();
        }
        if (isLastCharAlphaAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isLastCharAlphaAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(isAllWordsInitCap = ? | isAuthor = false)
    private Double probabilityForIsAllWordsInitCapAuthorFalse(boolean isAllWordsInitCap) {
        Double isAllWordsInitCapAndIsAuthor;
        if (isAllWordsInitCap) {
            isAllWordsInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsAllWordsInitCapTrueAndAuthorFalse();
        } else {
            isAllWordsInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsAllWordsInitCapFalseAndAuthorFalse();
        }
        if (isAllWordsInitCapAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isAllWordsInitCapAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(isAllWordsInitCap = ? | isAuthor = true)
    private Double probabilityForIsAllWordsInitCapAuthorTrue(boolean isAllWordsInitCap) {
        Double isAllWordsInitCapAndIsAuthor;
        if (isAllWordsInitCap) {
            isAllWordsInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsAllWordsInitCapTrueAndAuthorTrue();
        } else {
            isAllWordsInitCapAndIsAuthor = authorProbabilities.getProbabilityForIsAllWordsInitCapFalseAndAuthorTrue();
        }
        if (isAllWordsInitCapAndIsAuthor == 0.0)
            return 0.0009;
        else
            return isAllWordsInitCapAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
    }

    //P(containsBetweenTwoAndThreeWords = ? | isAuthor = false)
    private Double probabilityForContainsBetween2And3WordsAuthorFalse(boolean containsBetweenTwoAndThreeWords) {
        Double containsBetweenTwoAndThreeWordsAndIsAuthor;
        if (containsBetweenTwoAndThreeWords) {
            containsBetweenTwoAndThreeWordsAndIsAuthor = authorProbabilities.getProbabilityForContainsBetween2And3WordsTrueAndAuthorFalse();
        } else {
            containsBetweenTwoAndThreeWordsAndIsAuthor = authorProbabilities.getProbabilityForContainsBetween2And3WordsFalseAndAuthorFalse();
        }
        if (containsBetweenTwoAndThreeWordsAndIsAuthor == 0.0)
            return 0.0009;
        else
            return containsBetweenTwoAndThreeWordsAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorFalse();
    }

    //P(containsBetweenTwoAndThreeWords = ? | isAuthor = true)
    private Double probabilityForContainsBetween2And3WordsAuthorTrue(boolean containsBetweenTwoAndThreeWords) {
        Double containsBetweenTwoAndThreeWordsAndIsAuthor;
        if (containsBetweenTwoAndThreeWords) {
            containsBetweenTwoAndThreeWordsAndIsAuthor = authorProbabilities.getProbabilityForContainsBetween2And3WordsTrueAndAuthorTrue();
        } else {
            containsBetweenTwoAndThreeWordsAndIsAuthor = authorProbabilities.getProbabilityForContainsBetween2And3WordsFalseAndAuthorTrue();
        }
        if (containsBetweenTwoAndThreeWordsAndIsAuthor == 0.0)
            return 0.0009;
        else
            return containsBetweenTwoAndThreeWordsAndIsAuthor / authorProbabilities.getProbabilityForIsAuthorTrue();
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