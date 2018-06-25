package com.licenta.licenta.service.probabilities;

import com.licenta.licenta.repository.TitleFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleProbabilities {

    private final TitleFeaturesRepository titleFeaturesRepository;

    private Double probabilityForGreaterThanMeanMaxTrueAndTitleTrue;
    private Double probabilityForGreaterThanMeanMaxFalseAndTitleTrue;
    private Double probabilityForGreaterThanMeanMaxTrueAndTitleFalse;
    private Double probabilityForGreaterThanMeanMaxFalseAndTitleFalse;

    private Double probabilityForIsInitCapTrueAndTitleTrue;
    private Double probabilityForIsInitCapFalseAndTitleTrue;
    private Double probabilityForIsInitCapTrueAndTitleFalse;
    private Double probabilityForIsInitCapFalseAndTitleFalse;

    private Double probabilityForBoldTrueAndTitleTrue;
    private Double probabilityForBoldFalseAndTitleTrue;
    private Double probabilityForBoldTrueAndTitleFalse;
    private Double probabilityForBoldFalseAndTitleFalse;

    private Double probabilityForIsItalicTrueAndTitleTrue;
    private Double probabilityForIsItalicFalseAndTitleTrue;
    private Double probabilityForIsItalicTrueAndTitleFalse;
    private Double probabilityForIsItalicFalseAndTitleFalse;

    private Double probabilityForIsInFirst10RowsTrueAndTitleTrue;
    private Double probabilityForIsInFirst10RowsFalseAndTitleTrue;
    private Double probabilityForIsInFirst10RowsTrueAndTitleFalse;
    private Double probabilityForIsInFirst10RowsFalseAndTitleFalse;

    private Double probabilityForIsAlphaTrueAndTitleTrue;
    private Double probabilityForIsAlphaFalseAndTitleTrue;
    private Double probabilityForIsAlphaTrueAndTitleFalse;
    private Double probabilityForIsAlphaFalseAndTitleFalse;

    private Double probabilityForContainsQuotationMarkTrueAndTitleTrue;
    private Double probabilityForContainsQuotationMarkFalseAndTitleTrue;
    private Double probabilityForContainsQuotationMarkTrueAndTitleFalse;
    private Double probabilityForContainsQuotationMarkFalseAndTitleFalse;

    private Double probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue;
    private Double probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue;
    private Double probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse;
    private Double probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse;

    private Double probabilityForHasMoreThanTwoWordsTrueAndTitleTrue;
    private Double probabilityForHasMoreThanTwoWordsFalseAndTitleTrue;
    private Double probabilityForHasMoreThanTwoWordsTrueAndTitleFalse;
    private Double probabilityForHasMoreThanTwoWordsFalseAndTitleFalse;


    private Double probabilityForIsTitleTrue;
    private Double probabilityForIsTitleFalse;

    @Autowired
    public TitleProbabilities(TitleFeaturesRepository titleFeaturesRepository) {
        this.titleFeaturesRepository = titleFeaturesRepository;
    }

    public void calculateProbabilities(){
        this.probabilityForGreaterThanMeanMaxTrueAndTitleTrue = titleFeaturesRepository.findIsGreaterThanMeanMaxAndIsTitle
                (true,true);
        this.probabilityForGreaterThanMeanMaxFalseAndTitleTrue = titleFeaturesRepository.findIsGreaterThanMeanMaxAndIsTitle
                (false,true);
        this.probabilityForGreaterThanMeanMaxTrueAndTitleFalse = titleFeaturesRepository.findIsGreaterThanMeanMaxAndIsTitle
                (true,false);
        this.probabilityForGreaterThanMeanMaxFalseAndTitleFalse = titleFeaturesRepository.findIsGreaterThanMeanMaxAndIsTitle
                (false,false);

        this.probabilityForIsInitCapTrueAndTitleTrue = titleFeaturesRepository.findIsInitCapAndIsTitle(true, true);
        this.probabilityForIsInitCapFalseAndTitleTrue = titleFeaturesRepository.findIsInitCapAndIsTitle(false, true);
        this.probabilityForIsInitCapTrueAndTitleFalse = titleFeaturesRepository.findIsInitCapAndIsTitle(true, false);
        this.probabilityForIsInitCapFalseAndTitleFalse = titleFeaturesRepository.findIsInitCapAndIsTitle(false, false);

        this.probabilityForBoldTrueAndTitleTrue = titleFeaturesRepository.findIsBoldAndIsTitle(true, true);
        this.probabilityForBoldFalseAndTitleTrue = titleFeaturesRepository.findIsBoldAndIsTitle(false, true);
        this.probabilityForBoldTrueAndTitleFalse = titleFeaturesRepository.findIsBoldAndIsTitle(true, false);
        this.probabilityForBoldFalseAndTitleFalse = titleFeaturesRepository.findIsBoldAndIsTitle(false, false);

        this.probabilityForIsItalicTrueAndTitleTrue = titleFeaturesRepository.findIsItalicAndIsTitle(true, true);
        this.probabilityForIsItalicFalseAndTitleTrue = titleFeaturesRepository.findIsItalicAndIsTitle(false, true);
        this.probabilityForIsItalicTrueAndTitleFalse = titleFeaturesRepository.findIsItalicAndIsTitle(true, false);
        this.probabilityForIsItalicFalseAndTitleFalse = titleFeaturesRepository.findIsItalicAndIsTitle(false, false);

        this.probabilityForIsInFirst10RowsTrueAndTitleTrue = titleFeaturesRepository.
                findIsInFirst10RowsAndIsTitle(true, true);
        this.probabilityForIsInFirst10RowsFalseAndTitleTrue = titleFeaturesRepository.
                findIsInFirst10RowsAndIsTitle(false, true);
        this.probabilityForIsInFirst10RowsTrueAndTitleFalse = titleFeaturesRepository.
                findIsInFirst10RowsAndIsTitle(true, false);
        this.probabilityForIsInFirst10RowsFalseAndTitleFalse = titleFeaturesRepository.
                findIsInFirst10RowsAndIsTitle(false, false);

        this.probabilityForIsAlphaTrueAndTitleTrue = titleFeaturesRepository.findIsAlphaAndIsTitle(true, true);
        this.probabilityForIsAlphaFalseAndTitleTrue = titleFeaturesRepository.findIsAlphaAndIsTitle(false, true);
        this.probabilityForIsAlphaTrueAndTitleFalse = titleFeaturesRepository.findIsAlphaAndIsTitle(true, false);
        this.probabilityForIsAlphaFalseAndTitleFalse = titleFeaturesRepository.findIsAlphaAndIsTitle(false, false);

        this.probabilityForContainsQuotationMarkTrueAndTitleTrue = titleFeaturesRepository.
                findContainsQuotationMarkAndIsTitle(true, true);
        this.probabilityForContainsQuotationMarkFalseAndTitleTrue = titleFeaturesRepository.
                findContainsQuotationMarkAndIsTitle(false, true);
        this.probabilityForContainsQuotationMarkTrueAndTitleFalse = titleFeaturesRepository.
                findContainsQuotationMarkAndIsTitle(true, false);
        this.probabilityForContainsQuotationMarkFalseAndTitleFalse = titleFeaturesRepository.
                findContainsQuotationMarkAndIsTitle(false, false);

        this.probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue = titleFeaturesRepository
                .findIsLengthLowerThanLengthMeanAndIsTitle(true, true);
        this.probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue = titleFeaturesRepository
                .findIsLengthLowerThanLengthMeanAndIsTitle(false, true);
        this.probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse = titleFeaturesRepository
                .findIsLengthLowerThanLengthMeanAndIsTitle(true, false);
        this.probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse = titleFeaturesRepository
                .findIsLengthLowerThanLengthMeanAndIsTitle(false, false);

        this.probabilityForHasMoreThanTwoWordsTrueAndTitleTrue = titleFeaturesRepository.
                findHasMoreThanTwoWordsAndIsTitle(true, true);
        this.probabilityForHasMoreThanTwoWordsFalseAndTitleTrue = titleFeaturesRepository.
                findHasMoreThanTwoWordsAndIsTitle(false, true);
        this.probabilityForHasMoreThanTwoWordsTrueAndTitleFalse = titleFeaturesRepository.
                findHasMoreThanTwoWordsAndIsTitle(true, false);
        this.probabilityForHasMoreThanTwoWordsFalseAndTitleFalse = titleFeaturesRepository.
                findHasMoreThanTwoWordsAndIsTitle(false, false);

        this.probabilityForIsTitleTrue = titleFeaturesRepository.findIsTitle(true);
        this.probabilityForIsTitleFalse = titleFeaturesRepository.findIsTitle(false);
    }

    public TitleFeaturesRepository getTitleFeaturesRepository() {
        return titleFeaturesRepository;
    }

    public Double getProbabilityForGreaterThanMeanMaxTrueAndTitleTrue() {
        return probabilityForGreaterThanMeanMaxTrueAndTitleTrue;
    }

    public void setProbabilityForGreaterThanMeanMaxTrueAndTitleTrue(Double probabilityForGreaterThanMeanMaxTrueAndTitleTrue) {
        this.probabilityForGreaterThanMeanMaxTrueAndTitleTrue = probabilityForGreaterThanMeanMaxTrueAndTitleTrue;
    }

    public Double getProbabilityForGreaterThanMeanMaxFalseAndTitleTrue() {
        return probabilityForGreaterThanMeanMaxFalseAndTitleTrue;
    }

    public void setProbabilityForGreaterThanMeanMaxFalseAndTitleTrue(Double probabilityForGreaterThanMeanMaxFalseAndTitleTrue) {
        this.probabilityForGreaterThanMeanMaxFalseAndTitleTrue = probabilityForGreaterThanMeanMaxFalseAndTitleTrue;
    }

    public Double getProbabilityForGreaterThanMeanMaxTrueAndTitleFalse() {
        return probabilityForGreaterThanMeanMaxTrueAndTitleFalse;
    }

    public void setProbabilityForGreaterThanMeanMaxTrueAndTitleFalse(Double probabilityForGreaterThanMeanMaxTrueAndTitleFalse) {
        this.probabilityForGreaterThanMeanMaxTrueAndTitleFalse = probabilityForGreaterThanMeanMaxTrueAndTitleFalse;
    }

    public Double getProbabilityForGreaterThanMeanMaxFalseAndTitleFalse() {
        return probabilityForGreaterThanMeanMaxFalseAndTitleFalse;
    }

    public void setProbabilityForGreaterThanMeanMaxFalseAndTitleFalse(Double probabilityForGreaterThanMeanMaxFalseAndTitleFalse) {
        this.probabilityForGreaterThanMeanMaxFalseAndTitleFalse = probabilityForGreaterThanMeanMaxFalseAndTitleFalse;
    }

    public Double getProbabilityForIsInitCapTrueAndTitleTrue() {
        return probabilityForIsInitCapTrueAndTitleTrue;
    }

    public void setProbabilityForIsInitCapTrueAndTitleTrue(Double probabilityForIsInitCapTrueAndTitleTrue) {
        this.probabilityForIsInitCapTrueAndTitleTrue = probabilityForIsInitCapTrueAndTitleTrue;
    }

    public Double getProbabilityForIsInitCapFalseAndTitleTrue() {
        return probabilityForIsInitCapFalseAndTitleTrue;
    }

    public void setProbabilityForIsInitCapFalseAndTitleTrue(Double probabilityForIsInitCapFalseAndTitleTrue) {
        this.probabilityForIsInitCapFalseAndTitleTrue = probabilityForIsInitCapFalseAndTitleTrue;
    }

    public Double getProbabilityForIsInitCapTrueAndTitleFalse() {
        return probabilityForIsInitCapTrueAndTitleFalse;
    }

    public void setProbabilityForIsInitCapTrueAndTitleFalse(Double probabilityForIsInitCapTrueAndTitleFalse) {
        this.probabilityForIsInitCapTrueAndTitleFalse = probabilityForIsInitCapTrueAndTitleFalse;
    }

    public Double getProbabilityForIsInitCapFalseAndTitleFalse() {
        return probabilityForIsInitCapFalseAndTitleFalse;
    }

    public void setProbabilityForIsInitCapFalseAndTitleFalse(Double probabilityForIsInitCapFalseAndTitleFalse) {
        this.probabilityForIsInitCapFalseAndTitleFalse = probabilityForIsInitCapFalseAndTitleFalse;
    }

    public Double getProbabilityForBoldTrueAndTitleTrue() {
        return probabilityForBoldTrueAndTitleTrue;
    }

    public void setProbabilityForBoldTrueAndTitleTrue(Double probabilityForBoldTrueAndTitleTrue) {
        this.probabilityForBoldTrueAndTitleTrue = probabilityForBoldTrueAndTitleTrue;
    }

    public Double getProbabilityForBoldFalseAndTitleTrue() {
        return probabilityForBoldFalseAndTitleTrue;
    }

    public void setProbabilityForBoldFalseAndTitleTrue(Double probabilityForBoldFalseAndTitleTrue) {
        this.probabilityForBoldFalseAndTitleTrue = probabilityForBoldFalseAndTitleTrue;
    }

    public Double getProbabilityForBoldTrueAndTitleFalse() {
        return probabilityForBoldTrueAndTitleFalse;
    }

    public void setProbabilityForBoldTrueAndTitleFalse(Double probabilityForBoldTrueAndTitleFalse) {
        this.probabilityForBoldTrueAndTitleFalse = probabilityForBoldTrueAndTitleFalse;
    }

    public Double getProbabilityForBoldFalseAndTitleFalse() {
        return probabilityForBoldFalseAndTitleFalse;
    }

    public void setProbabilityForBoldFalseAndTitleFalse(Double probabilityForBoldFalseAndTitleFalse) {
        this.probabilityForBoldFalseAndTitleFalse = probabilityForBoldFalseAndTitleFalse;
    }

    public Double getProbabilityForIsItalicTrueAndTitleTrue() {
        return probabilityForIsItalicTrueAndTitleTrue;
    }

    public void setProbabilityForIsItalicTrueAndTitleTrue(Double probabilityForIsItalicTrueAndTitleTrue) {
        this.probabilityForIsItalicTrueAndTitleTrue = probabilityForIsItalicTrueAndTitleTrue;
    }

    public Double getProbabilityForIsItalicFalseAndTitleTrue() {
        return probabilityForIsItalicFalseAndTitleTrue;
    }

    public void setProbabilityForIsItalicFalseAndTitleTrue(Double probabilityForIsItalicFalseAndTitleTrue) {
        this.probabilityForIsItalicFalseAndTitleTrue = probabilityForIsItalicFalseAndTitleTrue;
    }

    public Double getProbabilityForIsItalicTrueAndTitleFalse() {
        return probabilityForIsItalicTrueAndTitleFalse;
    }

    public void setProbabilityForIsItalicTrueAndTitleFalse(Double probabilityForIsItalicTrueAndTitleFalse) {
        this.probabilityForIsItalicTrueAndTitleFalse = probabilityForIsItalicTrueAndTitleFalse;
    }

    public Double getProbabilityForIsItalicFalseAndTitleFalse() {
        return probabilityForIsItalicFalseAndTitleFalse;
    }

    public void setProbabilityForIsItalicFalseAndTitleFalse(Double probabilityForIsItalicFalseAndTitleFalse) {
        this.probabilityForIsItalicFalseAndTitleFalse = probabilityForIsItalicFalseAndTitleFalse;
    }

    public Double getProbabilityForIsInFirst10RowsTrueAndTitleTrue() {
        return probabilityForIsInFirst10RowsTrueAndTitleTrue;
    }

    public void setProbabilityForIsInFirst10RowsTrueAndTitleTrue(Double probabilityForIsInFirst10RowsTrueAndTitleTrue) {
        this.probabilityForIsInFirst10RowsTrueAndTitleTrue = probabilityForIsInFirst10RowsTrueAndTitleTrue;
    }

    public Double getProbabilityForIsInFirst10RowsFalseAndTitleTrue() {
        return probabilityForIsInFirst10RowsFalseAndTitleTrue;
    }

    public void setProbabilityForIsInFirst10RowsFalseAndTitleTrue(Double probabilityForIsInFirst10RowsFalseAndTitleTrue) {
        this.probabilityForIsInFirst10RowsFalseAndTitleTrue = probabilityForIsInFirst10RowsFalseAndTitleTrue;
    }

    public Double getProbabilityForIsInFirst10RowsTrueAndTitleFalse() {
        return probabilityForIsInFirst10RowsTrueAndTitleFalse;
    }

    public void setProbabilityForIsInFirst10RowsTrueAndTitleFalse(Double probabilityForIsInFirst10RowsTrueAndTitleFalse) {
        this.probabilityForIsInFirst10RowsTrueAndTitleFalse = probabilityForIsInFirst10RowsTrueAndTitleFalse;
    }

    public Double getProbabilityForIsInFirst10RowsFalseAndTitleFalse() {
        return probabilityForIsInFirst10RowsFalseAndTitleFalse;
    }

    public void setProbabilityForIsInFirst10RowsFalseAndTitleFalse(Double probabilityForIsInFirst10RowsFalseAndTitleFalse) {
        this.probabilityForIsInFirst10RowsFalseAndTitleFalse = probabilityForIsInFirst10RowsFalseAndTitleFalse;
    }

    public Double getProbabilityForIsAlphaTrueAndTitleTrue() {
        return probabilityForIsAlphaTrueAndTitleTrue;
    }

    public void setProbabilityForIsAlphaTrueAndTitleTrue(Double probabilityForIsAlphaTrueAndTitleTrue) {
        this.probabilityForIsAlphaTrueAndTitleTrue = probabilityForIsAlphaTrueAndTitleTrue;
    }

    public Double getProbabilityForIsAlphaFalseAndTitleTrue() {
        return probabilityForIsAlphaFalseAndTitleTrue;
    }

    public void setProbabilityForIsAlphaFalseAndTitleTrue(Double probabilityForIsAlphaFalseAndTitleTrue) {
        this.probabilityForIsAlphaFalseAndTitleTrue = probabilityForIsAlphaFalseAndTitleTrue;
    }

    public Double getProbabilityForIsAlphaTrueAndTitleFalse() {
        return probabilityForIsAlphaTrueAndTitleFalse;
    }

    public void setProbabilityForIsAlphaTrueAndTitleFalse(Double probabilityForIsAlphaTrueAndTitleFalse) {
        this.probabilityForIsAlphaTrueAndTitleFalse = probabilityForIsAlphaTrueAndTitleFalse;
    }

    public Double getProbabilityForIsAlphaFalseAndTitleFalse() {
        return probabilityForIsAlphaFalseAndTitleFalse;
    }

    public void setProbabilityForIsAlphaFalseAndTitleFalse(Double probabilityForIsAlphaFalseAndTitleFalse) {
        this.probabilityForIsAlphaFalseAndTitleFalse = probabilityForIsAlphaFalseAndTitleFalse;
    }

    public Double getProbabilityForContainsQuotationMarkTrueAndTitleTrue() {
        return probabilityForContainsQuotationMarkTrueAndTitleTrue;
    }

    public void setProbabilityForContainsQuotationMarkTrueAndTitleTrue(Double probabilityForContainsQuotationMarkTrueAndTitleTrue) {
        this.probabilityForContainsQuotationMarkTrueAndTitleTrue = probabilityForContainsQuotationMarkTrueAndTitleTrue;
    }

    public Double getProbabilityForContainsQuotationMarkFalseAndTitleTrue() {
        return probabilityForContainsQuotationMarkFalseAndTitleTrue;
    }

    public void setProbabilityForContainsQuotationMarkFalseAndTitleTrue(Double probabilityForContainsQuotationMarkFalseAndTitleTrue) {
        this.probabilityForContainsQuotationMarkFalseAndTitleTrue = probabilityForContainsQuotationMarkFalseAndTitleTrue;
    }

    public Double getProbabilityForContainsQuotationMarkTrueAndTitleFalse() {
        return probabilityForContainsQuotationMarkTrueAndTitleFalse;
    }

    public void setProbabilityForContainsQuotationMarkTrueAndTitleFalse(Double probabilityForContainsQuotationMarkTrueAndTitleFalse) {
        this.probabilityForContainsQuotationMarkTrueAndTitleFalse = probabilityForContainsQuotationMarkTrueAndTitleFalse;
    }

    public Double getProbabilityForContainsQuotationMarkFalseAndTitleFalse() {
        return probabilityForContainsQuotationMarkFalseAndTitleFalse;
    }

    public void setProbabilityForContainsQuotationMarkFalseAndTitleFalse(Double probabilityForContainsQuotationMarkFalseAndTitleFalse) {
        this.probabilityForContainsQuotationMarkFalseAndTitleFalse = probabilityForContainsQuotationMarkFalseAndTitleFalse;
    }

    public Double getProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue() {
        return probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue;
    }

    public void setProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue(Double probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue) {
        this.probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue = probabilityForIsLengthLowerThanLengthMeanTrueAndTitleTrue;
    }

    public Double getProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue() {
        return probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue;
    }

    public void setProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue(Double probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue) {
        this.probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue = probabilityForIsLengthLowerThanLengthMeanFalseAndTitleTrue;
    }

    public Double getProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse() {
        return probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse;
    }

    public void setProbabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse(Double probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse) {
        this.probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse = probabilityForIsLengthLowerThanLengthMeanTrueAndTitleFalse;
    }

    public Double getProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse() {
        return probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse;
    }

    public void setProbabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse(Double probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse) {
        this.probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse = probabilityForIsLengthLowerThanLengthMeanFalseAndTitleFalse;
    }

    public Double getProbabilityForIsTitleTrue() {
        return probabilityForIsTitleTrue;
    }

    public void setProbabilityForIsTitleTrue(Double probabilityForIsTitleTrue) {
        this.probabilityForIsTitleTrue = probabilityForIsTitleTrue;
    }

    public Double getProbabilityForIsTitleFalse() {
        return probabilityForIsTitleFalse;
    }

    public void setProbabilityForIsTitleFalse(Double probabilityForIsTitleFalse) {
        this.probabilityForIsTitleFalse = probabilityForIsTitleFalse;
    }

    public Double getProbabilityForHasMoreThanTwoWordsTrueAndTitleTrue() {
        return probabilityForHasMoreThanTwoWordsTrueAndTitleTrue;
    }

    public void setProbabilityForHasMoreThanTwoWordsTrueAndTitleTrue(Double probabilityForHasMoreThanTwoWordsTrueAndTitleTrue) {
        this.probabilityForHasMoreThanTwoWordsTrueAndTitleTrue = probabilityForHasMoreThanTwoWordsTrueAndTitleTrue;
    }

    public Double getProbabilityForHasMoreThanTwoWordsFalseAndTitleTrue() {
        return probabilityForHasMoreThanTwoWordsFalseAndTitleTrue;
    }

    public void setProbabilityForHasMoreThanTwoWordsFalseAndTitleTrue(Double probabilityForHasMoreThanTwoWordsFalseAndTitleTrue) {
        this.probabilityForHasMoreThanTwoWordsFalseAndTitleTrue = probabilityForHasMoreThanTwoWordsFalseAndTitleTrue;
    }

    public Double getProbabilityForHasMoreThanTwoWordsTrueAndTitleFalse() {
        return probabilityForHasMoreThanTwoWordsTrueAndTitleFalse;
    }

    public void setProbabilityForHasMoreThanTwoWordsTrueAndTitleFalse(Double probabilityForHasMoreThanTwoWordsTrueAndTitleFalse) {
        this.probabilityForHasMoreThanTwoWordsTrueAndTitleFalse = probabilityForHasMoreThanTwoWordsTrueAndTitleFalse;
    }

    public Double getProbabilityForHasMoreThanTwoWordsFalseAndTitleFalse() {
        return probabilityForHasMoreThanTwoWordsFalseAndTitleFalse;
    }

    public void setProbabilityForHasMoreThanTwoWordsFalseAndTitleFalse(Double probabilityForHasMoreThanTwoWordsFalseAndTitleFalse) {
        this.probabilityForHasMoreThanTwoWordsFalseAndTitleFalse = probabilityForHasMoreThanTwoWordsFalseAndTitleFalse;
    }
}
