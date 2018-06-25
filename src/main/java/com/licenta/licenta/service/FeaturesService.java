package com.licenta.licenta.service;


import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FeaturesService {

    public List<String> getLinesFrom(String text) {
        String line = "";
        List<String> linesOfText = new ArrayList<>();
        for (Integer i = 0; i < text.length(); i++)
            if (text.charAt(i) != '\n') {
                line += text.charAt(i);
            } else {
                linesOfText.add(line);
                line = "";
            }
        return linesOfText;
    }

    public Double maxTextSize(List<String> linesOfText) {
        return linesOfText.stream()
                .map(this::getFontSize)
                .max(Double::compare)
                .orElse(0.0);
    }

    public Double meanMax(List<String> linesOfText) {
        return linesOfText.stream()
                .mapToDouble(this::getFontSize).sum() / linesOfText.size();
    }

    public Double meanLength(List<String> linesOfText) {
        return linesOfText.stream()
                .mapToDouble(it -> getText(it).length()).sum() / linesOfText.size();
    }

    public boolean isBold(String textLine) {
        return getFont(textLine).toLowerCase().contains("bold");
    }

    public boolean isItalic(String textLine) {
        return getFont(textLine).toLowerCase().contains("italic");
    }

    public boolean isInitCap(String textLine) {
        return getText(textLine).charAt(0) == Character.toUpperCase(getText(textLine).charAt(0));
    }

    public boolean isAlpha(String textLine) {
        return StringUtils.trimAllWhitespace(getText(textLine)).chars().allMatch(Character::isLetterOrDigit);
    }

    //font size
    public boolean isGraterThanMeanMax(String textLine, Double meanMax) {
        return getFontSize(textLine).compareTo(meanMax) > 0;
    }

    public Double secondMax(List<String> linesOfText, Double max) {
        linesOfText.removeIf(it -> Objects.equals(max, getFontSize(it)));
        return linesOfText.stream()
                .map(this::getFontSize)
                .max(Double::compare)
                .orElse(0.0);
    }

    public boolean isSecondMax(String textLine, Double secondMax) {
        Double localMax = getFontSize(textLine);
        return Objects.equals(localMax, secondMax);
    }

    public boolean isLengthLowerThanLengthMean(String textLine, Double lengthMean) {
        return ((double) getText(textLine).length() <= lengthMean);
    }

    public boolean isInFirst10Rows(List<String> linesOfText, String textLine) {
        return linesOfText
                .stream()
                .limit(10)
                .anyMatch(it -> it.toLowerCase().equals(textLine.toLowerCase()));
    }

    public boolean containsQuotationMark(String textLine) {
        return getText(textLine).toLowerCase().contains("\"") || getText(textLine).toLowerCase().contains("”")
                || getText(textLine).toLowerCase().contains("„") || getText(textLine).toLowerCase().contains(",,")
                || getText(textLine).toLowerCase().contains("''");
    }

    public boolean isFirstCharAlpha(String textLine) {
        return Character.isLetter(getText(textLine).charAt(0));
    }

    public boolean isLastCharAlpha(String textLine) {
        String text = getText(textLine);
        return Character.isLetter(text.charAt(text.length() -1));
    }

    public boolean isAllWordsInitCap(String textLine) {
        String initCap = WordUtils.capitalizeFully(getText(textLine));
        return initCap.equals(getText(textLine));
    }

    public boolean containsBetweenTwoAndThreeWords(String textLine) {
        String[] words = getText(textLine).split("\\s+");
        return words.length >= 2 && words.length <= 3;
    }

    public boolean hasMoreThanTwoWords(String textLine){
        String[] words = getText(textLine).split("\\s+");
        return words.length > 2;
    }

    public Double getFontSize(String textLine) {
        double fontSize = 0.0;
        String regex = "<Size>(.+?)</Size>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(textLine);
        while (matcher.find()) {
            fontSize = Double.parseDouble(matcher.group(1));
        }
        return fontSize;
    }

    public String getFont(String text) {
        String font = "";
        String regex = "<Font>(.+?)</Font>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            font = matcher.group(1);
        }
        return font;
    }

    public String getText(String text) {
        String finalText = "";
        String regex = "<Text>(.+?)</Text>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            finalText = matcher.group(1);
        }
        return finalText;
    }

    public Double getX(String text){
        Double X = 0.0;
        Pattern pattern = Pattern.compile("<X>(.+?)</X>");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            X = Double.parseDouble(matcher.group(1));
        }
        return X;
    }

    public Double getY(String text){
        Double Y = 0.0;
        String regex = "<Y>(.+?)</Y>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            Y = Double.parseDouble(matcher.group(1));
        }
        return Y;
    }

    public Double distanceBetweenOriginAndAPoint(String text){
        return Math.sqrt
                (Math.pow(getX(text), 2) + Math.pow(getY(text), 2));
    }

    public double distanceBetweenTwoPoints(String firstPoint, String secondPoint){
        return Math.sqrt(Math.pow((getX(firstPoint) - getX(secondPoint)), 2)
                + Math.pow((getY(firstPoint) - getY(secondPoint)), 2));
    }
}
