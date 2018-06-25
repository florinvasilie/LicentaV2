package com.licenta.licenta.service.probabilities;

import com.licenta.licenta.repository.AuthorFeaturesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorProbabilities {
    private final AuthorFeaturesRepository authorFeaturesRepository;

    private Double probabilityForGreaterThanMeanMaxTrueAndAuthorTrue;
    private Double probabilityForGreaterThanMeanMaxFalseAndAuthorTrue;
    private Double probabilityForGreaterThanMeanMaxTrueAndAuthorFalse;
    private Double probabilityForGreaterThanMeanMaxFalseAndAuthorFalse;

    private Double probabilityForIsInitCapTrueAndAuthorTrue;
    private Double probabilityForIsInitCapFalseAndAuthorTrue;
    private Double probabilityForIsInitCapTrueAndAuthorFalse;
    private Double probabilityForIsInitCapFalseAndAuthorFalse;

    private Double probabilityForBoldTrueAndAuthorTrue;
    private Double probabilityForBoldFalseAndAuthorTrue;
    private Double probabilityForBoldTrueAndAuthorFalse;
    private Double probabilityForBoldFalseAndAuthorFalse;

    private Double probabilityForIsInFirst10RowsTrueAndAuthorTrue;
    private Double probabilityForIsInFirst10RowsFalseAndAuthorTrue;
    private Double probabilityForIsInFirst10RowsTrueAndAuthorFalse;
    private Double probabilityForIsInFirst10RowsFalseAndAuthorFalse;

    private Double probabilityForIsAlphaTrueAndAuthorTrue;
    private Double probabilityForIsAlphaFalseAndAuthorTrue;
    private Double probabilityForIsAlphaTrueAndAuthorFalse;
    private Double probabilityForIsAlphaFalseAndAuthorFalse;

    private Double probabilityForIsFirstCharAlphaTrueAndAuthorTrue;
    private Double probabilityForIsFirstCharAlphaFalseAndAuthorTrue;
    private Double probabilityForIsFirstCharAlphaTrueAndAuthorFalse;
    private Double probabilityForIsFirstCharAlphaFalseAndAuthorFalse;

    private Double probabilityForIsLastCharAlphaTrueAndAuthorTrue;
    private Double probabilityForIsLastCharAlphaFalseAndAuthorTrue;
    private Double probabilityForIsLastCharAlphaTrueAndAuthorFalse;
    private Double probabilityForIsLastCharAlphaFalseAndAuthorFalse;

    private Double probabilityForIsAllWordsInitCapTrueAndAuthorTrue;
    private Double probabilityForIsAllWordsInitCapFalseAndAuthorTrue;
    private Double probabilityForIsAllWordsInitCapTrueAndAuthorFalse;
    private Double probabilityForIsAllWordsInitCapFalseAndAuthorFalse;

    private Double probabilityForContainsBetween2And3WordsTrueAndAuthorTrue;
    private Double probabilityForContainsBetween2And3WordsFalseAndAuthorTrue;
    private Double probabilityForContainsBetween2And3WordsTrueAndAuthorFalse;
    private Double probabilityForContainsBetween2And3WordsFalseAndAuthorFalse;

    private Double probabilityForIsAuthorTrue;
    private Double probabilityForIsAuthorFalse;

    @Autowired
    public AuthorProbabilities(AuthorFeaturesRepository authorFeaturesRepository) {
        this.authorFeaturesRepository = authorFeaturesRepository;
    }

    public void calculateProbabilities() {
        this.probabilityForGreaterThanMeanMaxTrueAndAuthorTrue = authorFeaturesRepository.findIsGreaterThanMeanMaxAndIsAuthor
                (true, true);
        this.probabilityForGreaterThanMeanMaxFalseAndAuthorTrue = authorFeaturesRepository.findIsGreaterThanMeanMaxAndIsAuthor
                (false, true);
        this.probabilityForGreaterThanMeanMaxTrueAndAuthorFalse = authorFeaturesRepository.findIsGreaterThanMeanMaxAndIsAuthor
                (true, false);
        this.probabilityForGreaterThanMeanMaxFalseAndAuthorFalse = authorFeaturesRepository.findIsGreaterThanMeanMaxAndIsAuthor
                (false, false);

        this.probabilityForIsInitCapTrueAndAuthorTrue = authorFeaturesRepository.findIsInitCapAndIsAuthor(true, true);
        this.probabilityForIsInitCapFalseAndAuthorTrue = authorFeaturesRepository.findIsInitCapAndIsAuthor(false, true);
        this.probabilityForIsInitCapTrueAndAuthorFalse = authorFeaturesRepository.findIsInitCapAndIsAuthor(true, false);
        this.probabilityForIsInitCapFalseAndAuthorFalse = authorFeaturesRepository.findIsInitCapAndIsAuthor(false, false);

        this.probabilityForBoldTrueAndAuthorTrue = authorFeaturesRepository.findIsBoldAndIsAuthor(true, true);
        this.probabilityForBoldFalseAndAuthorTrue = authorFeaturesRepository.findIsBoldAndIsAuthor(false, true);
        this.probabilityForBoldTrueAndAuthorFalse = authorFeaturesRepository.findIsBoldAndIsAuthor(true, false);
        this.probabilityForBoldFalseAndAuthorFalse = authorFeaturesRepository.findIsBoldAndIsAuthor(false, false);

        this.probabilityForIsInFirst10RowsTrueAndAuthorTrue = authorFeaturesRepository.
                findIsInFirst10RowsAndIsAuthor(true, true);
        this.probabilityForIsInFirst10RowsFalseAndAuthorTrue = authorFeaturesRepository.
                findIsInFirst10RowsAndIsAuthor(false, true);
        this.probabilityForIsInFirst10RowsTrueAndAuthorFalse = authorFeaturesRepository.
                findIsInFirst10RowsAndIsAuthor(true, false);
        this.probabilityForIsInFirst10RowsFalseAndAuthorFalse = authorFeaturesRepository.
                findIsInFirst10RowsAndIsAuthor(false, false);

        this.probabilityForIsAlphaTrueAndAuthorTrue = authorFeaturesRepository.findIsAlphaAndIsAuthor(true, true);
        this.probabilityForIsAlphaFalseAndAuthorTrue = authorFeaturesRepository.findIsAlphaAndIsAuthor(false, true);
        this.probabilityForIsAlphaTrueAndAuthorFalse = authorFeaturesRepository.findIsAlphaAndIsAuthor(true, false);
        this.probabilityForIsAlphaFalseAndAuthorFalse = authorFeaturesRepository.findIsAlphaAndIsAuthor(false, false);

        this.probabilityForIsFirstCharAlphaTrueAndAuthorTrue = authorFeaturesRepository.
                findIsFirstCharAlphaAndIsAuthor(true, true);
        this.probabilityForIsFirstCharAlphaFalseAndAuthorTrue = authorFeaturesRepository.
                findIsFirstCharAlphaAndIsAuthor(false, true);
        this.probabilityForIsFirstCharAlphaTrueAndAuthorFalse = authorFeaturesRepository.
                findIsFirstCharAlphaAndIsAuthor(true, false);
        this.probabilityForIsFirstCharAlphaFalseAndAuthorFalse = authorFeaturesRepository.
                findIsFirstCharAlphaAndIsAuthor(false, false);

        this.probabilityForIsLastCharAlphaTrueAndAuthorTrue = authorFeaturesRepository.
                findIsLastCharAlphaAndIsAuthor(true, true);
        this.probabilityForIsLastCharAlphaFalseAndAuthorTrue = authorFeaturesRepository.
                findIsLastCharAlphaAndIsAuthor(false, true);
        this.probabilityForIsLastCharAlphaTrueAndAuthorFalse = authorFeaturesRepository.
                findIsLastCharAlphaAndIsAuthor(true, false);
        this.probabilityForIsLastCharAlphaFalseAndAuthorFalse = authorFeaturesRepository.
                findIsLastCharAlphaAndIsAuthor(false, false);

        this.probabilityForIsAllWordsInitCapTrueAndAuthorTrue = authorFeaturesRepository.
                findIsAllWordsInitCapAndIsAuthor(true, true);
        this.probabilityForIsAllWordsInitCapFalseAndAuthorTrue = authorFeaturesRepository.
                findIsAllWordsInitCapAndIsAuthor(false, true);
        this.probabilityForIsAllWordsInitCapTrueAndAuthorFalse = authorFeaturesRepository.
                findIsAllWordsInitCapAndIsAuthor(true, false);
        this.probabilityForIsAllWordsInitCapFalseAndAuthorFalse = authorFeaturesRepository.
                findIsAllWordsInitCapAndIsAuthor(false, false);

        this.probabilityForContainsBetween2And3WordsTrueAndAuthorTrue = authorFeaturesRepository.
                findContainsBetweenTwoAndThreeWordsAndIsAuthor(true, true);
        this.probabilityForContainsBetween2And3WordsFalseAndAuthorTrue = authorFeaturesRepository.
                findContainsBetweenTwoAndThreeWordsAndIsAuthor(false, true);
        this.probabilityForContainsBetween2And3WordsTrueAndAuthorFalse = authorFeaturesRepository.
                findContainsBetweenTwoAndThreeWordsAndIsAuthor(true, false);
        this.probabilityForContainsBetween2And3WordsFalseAndAuthorFalse = authorFeaturesRepository.
                findContainsBetweenTwoAndThreeWordsAndIsAuthor(false, false);

        this.probabilityForIsAuthorTrue = authorFeaturesRepository.findIsAuthor(true);
        this.probabilityForIsAuthorFalse = authorFeaturesRepository.findIsAuthor(false);
    }

    public Double getProbabilityForGreaterThanMeanMaxTrueAndAuthorTrue() {
        return probabilityForGreaterThanMeanMaxTrueAndAuthorTrue;
    }

    public void setProbabilityForGreaterThanMeanMaxTrueAndAuthorTrue(Double probabilityForGreaterThanMeanMaxTrueAndAuthorTrue) {
        this.probabilityForGreaterThanMeanMaxTrueAndAuthorTrue = probabilityForGreaterThanMeanMaxTrueAndAuthorTrue;
    }

    public Double getProbabilityForGreaterThanMeanMaxFalseAndAuthorTrue() {
        return probabilityForGreaterThanMeanMaxFalseAndAuthorTrue;
    }

    public void setProbabilityForGreaterThanMeanMaxFalseAndAuthorTrue(Double probabilityForGreaterThanMeanMaxFalseAndAuthorTrue) {
        this.probabilityForGreaterThanMeanMaxFalseAndAuthorTrue = probabilityForGreaterThanMeanMaxFalseAndAuthorTrue;
    }

    public Double getProbabilityForGreaterThanMeanMaxTrueAndAuthorFalse() {
        return probabilityForGreaterThanMeanMaxTrueAndAuthorFalse;
    }

    public void setProbabilityForGreaterThanMeanMaxTrueAndAuthorFalse(Double probabilityForGreaterThanMeanMaxTrueAndAuthorFalse) {
        this.probabilityForGreaterThanMeanMaxTrueAndAuthorFalse = probabilityForGreaterThanMeanMaxTrueAndAuthorFalse;
    }

    public Double getProbabilityForGreaterThanMeanMaxFalseAndAuthorFalse() {
        return probabilityForGreaterThanMeanMaxFalseAndAuthorFalse;
    }

    public void setProbabilityForGreaterThanMeanMaxFalseAndAuthorFalse(Double probabilityForGreaterThanMeanMaxFalseAndAuthorFalse) {
        this.probabilityForGreaterThanMeanMaxFalseAndAuthorFalse = probabilityForGreaterThanMeanMaxFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsInitCapTrueAndAuthorTrue() {
        return probabilityForIsInitCapTrueAndAuthorTrue;
    }

    public void setProbabilityForIsInitCapTrueAndAuthorTrue(Double probabilityForIsInitCapTrueAndAuthorTrue) {
        this.probabilityForIsInitCapTrueAndAuthorTrue = probabilityForIsInitCapTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsInitCapFalseAndAuthorTrue() {
        return probabilityForIsInitCapFalseAndAuthorTrue;
    }

    public void setProbabilityForIsInitCapFalseAndAuthorTrue(Double probabilityForIsInitCapFalseAndAuthorTrue) {
        this.probabilityForIsInitCapFalseAndAuthorTrue = probabilityForIsInitCapFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsInitCapTrueAndAuthorFalse() {
        return probabilityForIsInitCapTrueAndAuthorFalse;
    }

    public void setProbabilityForIsInitCapTrueAndAuthorFalse(Double probabilityForIsInitCapTrueAndAuthorFalse) {
        this.probabilityForIsInitCapTrueAndAuthorFalse = probabilityForIsInitCapTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsInitCapFalseAndAuthorFalse() {
        return probabilityForIsInitCapFalseAndAuthorFalse;
    }

    public void setProbabilityForIsInitCapFalseAndAuthorFalse(Double probabilityForIsInitCapFalseAndAuthorFalse) {
        this.probabilityForIsInitCapFalseAndAuthorFalse = probabilityForIsInitCapFalseAndAuthorFalse;
    }

    public Double getProbabilityForBoldTrueAndAuthorTrue() {
        return probabilityForBoldTrueAndAuthorTrue;
    }

    public void setProbabilityForBoldTrueAndAuthorTrue(Double probabilityForBoldTrueAndAuthorTrue) {
        this.probabilityForBoldTrueAndAuthorTrue = probabilityForBoldTrueAndAuthorTrue;
    }

    public Double getProbabilityForBoldFalseAndAuthorTrue() {
        return probabilityForBoldFalseAndAuthorTrue;
    }

    public void setProbabilityForBoldFalseAndAuthorTrue(Double probabilityForBoldFalseAndAuthorTrue) {
        this.probabilityForBoldFalseAndAuthorTrue = probabilityForBoldFalseAndAuthorTrue;
    }

    public Double getProbabilityForBoldTrueAndAuthorFalse() {
        return probabilityForBoldTrueAndAuthorFalse;
    }

    public void setProbabilityForBoldTrueAndAuthorFalse(Double probabilityForBoldTrueAndAuthorFalse) {
        this.probabilityForBoldTrueAndAuthorFalse = probabilityForBoldTrueAndAuthorFalse;
    }

    public Double getProbabilityForBoldFalseAndAuthorFalse() {
        return probabilityForBoldFalseAndAuthorFalse;
    }

    public void setProbabilityForBoldFalseAndAuthorFalse(Double probabilityForBoldFalseAndAuthorFalse) {
        this.probabilityForBoldFalseAndAuthorFalse = probabilityForBoldFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsInFirst10RowsTrueAndAuthorTrue() {
        return probabilityForIsInFirst10RowsTrueAndAuthorTrue;
    }

    public void setProbabilityForIsInFirst10RowsTrueAndAuthorTrue(Double probabilityForIsInFirst10RowsTrueAndAuthorTrue) {
        this.probabilityForIsInFirst10RowsTrueAndAuthorTrue = probabilityForIsInFirst10RowsTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsInFirst10RowsFalseAndAuthorTrue() {
        return probabilityForIsInFirst10RowsFalseAndAuthorTrue;
    }

    public void setProbabilityForIsInFirst10RowsFalseAndAuthorTrue(Double probabilityForIsInFirst10RowsFalseAndAuthorTrue) {
        this.probabilityForIsInFirst10RowsFalseAndAuthorTrue = probabilityForIsInFirst10RowsFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsInFirst10RowsTrueAndAuthorFalse() {
        return probabilityForIsInFirst10RowsTrueAndAuthorFalse;
    }

    public void setProbabilityForIsInFirst10RowsTrueAndAuthorFalse(Double probabilityForIsInFirst10RowsTrueAndAuthorFalse) {
        this.probabilityForIsInFirst10RowsTrueAndAuthorFalse = probabilityForIsInFirst10RowsTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsInFirst10RowsFalseAndAuthorFalse() {
        return probabilityForIsInFirst10RowsFalseAndAuthorFalse;
    }

    public void setProbabilityForIsInFirst10RowsFalseAndAuthorFalse(Double probabilityForIsInFirst10RowsFalseAndAuthorFalse) {
        this.probabilityForIsInFirst10RowsFalseAndAuthorFalse = probabilityForIsInFirst10RowsFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsAlphaTrueAndAuthorTrue() {
        return probabilityForIsAlphaTrueAndAuthorTrue;
    }

    public void setProbabilityForIsAlphaTrueAndAuthorTrue(Double probabilityForIsAlphaTrueAndAuthorTrue) {
        this.probabilityForIsAlphaTrueAndAuthorTrue = probabilityForIsAlphaTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsAlphaFalseAndAuthorTrue() {
        return probabilityForIsAlphaFalseAndAuthorTrue;
    }

    public void setProbabilityForIsAlphaFalseAndAuthorTrue(Double probabilityForIsAlphaFalseAndAuthorTrue) {
        this.probabilityForIsAlphaFalseAndAuthorTrue = probabilityForIsAlphaFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsAlphaTrueAndAuthorFalse() {
        return probabilityForIsAlphaTrueAndAuthorFalse;
    }

    public void setProbabilityForIsAlphaTrueAndAuthorFalse(Double probabilityForIsAlphaTrueAndAuthorFalse) {
        this.probabilityForIsAlphaTrueAndAuthorFalse = probabilityForIsAlphaTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsAlphaFalseAndAuthorFalse() {
        return probabilityForIsAlphaFalseAndAuthorFalse;
    }

    public void setProbabilityForIsAlphaFalseAndAuthorFalse(Double probabilityForIsAlphaFalseAndAuthorFalse) {
        this.probabilityForIsAlphaFalseAndAuthorFalse = probabilityForIsAlphaFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsFirstCharAlphaTrueAndAuthorTrue() {
        return probabilityForIsFirstCharAlphaTrueAndAuthorTrue;
    }

    public void setProbabilityForIsFirstCharAlphaTrueAndAuthorTrue(Double probabilityForIsFirstCharAlphaTrueAndAuthorTrue) {
        this.probabilityForIsFirstCharAlphaTrueAndAuthorTrue = probabilityForIsFirstCharAlphaTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsFirstCharAlphaFalseAndAuthorTrue() {
        return probabilityForIsFirstCharAlphaFalseAndAuthorTrue;
    }

    public void setProbabilityForIsFirstCharAlphaFalseAndAuthorTrue(Double probabilityForIsFirstCharAlphaFalseAndAuthorTrue) {
        this.probabilityForIsFirstCharAlphaFalseAndAuthorTrue = probabilityForIsFirstCharAlphaFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsFirstCharAlphaTrueAndAuthorFalse() {
        return probabilityForIsFirstCharAlphaTrueAndAuthorFalse;
    }

    public void setProbabilityForIsFirstCharAlphaTrueAndAuthorFalse(Double probabilityForIsFirstCharAlphaTrueAndAuthorFalse) {
        this.probabilityForIsFirstCharAlphaTrueAndAuthorFalse = probabilityForIsFirstCharAlphaTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsFirstCharAlphaFalseAndAuthorFalse() {
        return probabilityForIsFirstCharAlphaFalseAndAuthorFalse;
    }

    public void setProbabilityForIsFirstCharAlphaFalseAndAuthorFalse(Double probabilityForIsFirstCharAlphaFalseAndAuthorFalse) {
        this.probabilityForIsFirstCharAlphaFalseAndAuthorFalse = probabilityForIsFirstCharAlphaFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsLastCharAlphaTrueAndAuthorTrue() {
        return probabilityForIsLastCharAlphaTrueAndAuthorTrue;
    }

    public void setProbabilityForIsLastCharAlphaTrueAndAuthorTrue(Double probabilityForIsLastCharAlphaTrueAndAuthorTrue) {
        this.probabilityForIsLastCharAlphaTrueAndAuthorTrue = probabilityForIsLastCharAlphaTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsLastCharAlphaFalseAndAuthorTrue() {
        return probabilityForIsLastCharAlphaFalseAndAuthorTrue;
    }

    public void setProbabilityForIsLastCharAlphaFalseAndAuthorTrue(Double probabilityForIsLastCharAlphaFalseAndAuthorTrue) {
        this.probabilityForIsLastCharAlphaFalseAndAuthorTrue = probabilityForIsLastCharAlphaFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsLastCharAlphaTrueAndAuthorFalse() {
        return probabilityForIsLastCharAlphaTrueAndAuthorFalse;
    }

    public void setProbabilityForIsLastCharAlphaTrueAndAuthorFalse(Double probabilityForIsLastCharAlphaTrueAndAuthorFalse) {
        this.probabilityForIsLastCharAlphaTrueAndAuthorFalse = probabilityForIsLastCharAlphaTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsLastCharAlphaFalseAndAuthorFalse() {
        return probabilityForIsLastCharAlphaFalseAndAuthorFalse;
    }

    public void setProbabilityForIsLastCharAlphaFalseAndAuthorFalse(Double probabilityForIsLastCharAlphaFalseAndAuthorFalse) {
        this.probabilityForIsLastCharAlphaFalseAndAuthorFalse = probabilityForIsLastCharAlphaFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsAllWordsInitCapTrueAndAuthorTrue() {
        return probabilityForIsAllWordsInitCapTrueAndAuthorTrue;
    }

    public void setProbabilityForIsAllWordsInitCapTrueAndAuthorTrue(Double probabilityForIsAllWordsInitCapTrueAndAuthorTrue) {
        this.probabilityForIsAllWordsInitCapTrueAndAuthorTrue = probabilityForIsAllWordsInitCapTrueAndAuthorTrue;
    }

    public Double getProbabilityForIsAllWordsInitCapFalseAndAuthorTrue() {
        return probabilityForIsAllWordsInitCapFalseAndAuthorTrue;
    }

    public void setProbabilityForIsAllWordsInitCapFalseAndAuthorTrue(Double probabilityForIsAllWordsInitCapFalseAndAuthorTrue) {
        this.probabilityForIsAllWordsInitCapFalseAndAuthorTrue = probabilityForIsAllWordsInitCapFalseAndAuthorTrue;
    }

    public Double getProbabilityForIsAllWordsInitCapTrueAndAuthorFalse() {
        return probabilityForIsAllWordsInitCapTrueAndAuthorFalse;
    }

    public void setProbabilityForIsAllWordsInitCapTrueAndAuthorFalse(Double probabilityForIsAllWordsInitCapTrueAndAuthorFalse) {
        this.probabilityForIsAllWordsInitCapTrueAndAuthorFalse = probabilityForIsAllWordsInitCapTrueAndAuthorFalse;
    }

    public Double getProbabilityForIsAllWordsInitCapFalseAndAuthorFalse() {
        return probabilityForIsAllWordsInitCapFalseAndAuthorFalse;
    }

    public void setProbabilityForIsAllWordsInitCapFalseAndAuthorFalse(Double probabilityForIsAllWordsInitCapFalseAndAuthorFalse) {
        this.probabilityForIsAllWordsInitCapFalseAndAuthorFalse = probabilityForIsAllWordsInitCapFalseAndAuthorFalse;
    }

    public Double getProbabilityForContainsBetween2And3WordsTrueAndAuthorTrue() {
        return probabilityForContainsBetween2And3WordsTrueAndAuthorTrue;
    }

    public void setProbabilityForContainsBetween2And3WordsTrueAndAuthorTrue(Double probabilityForContainsBetween2And3WordsTrueAndAuthorTrue) {
        this.probabilityForContainsBetween2And3WordsTrueAndAuthorTrue = probabilityForContainsBetween2And3WordsTrueAndAuthorTrue;
    }

    public Double getProbabilityForContainsBetween2And3WordsFalseAndAuthorTrue() {
        return probabilityForContainsBetween2And3WordsFalseAndAuthorTrue;
    }

    public void setProbabilityForContainsBetween2And3WordsFalseAndAuthorTrue(Double probabilityForContainsBetween2And3WordsFalseAndAuthorTrue) {
        this.probabilityForContainsBetween2And3WordsFalseAndAuthorTrue = probabilityForContainsBetween2And3WordsFalseAndAuthorTrue;
    }

    public Double getProbabilityForContainsBetween2And3WordsTrueAndAuthorFalse() {
        return probabilityForContainsBetween2And3WordsTrueAndAuthorFalse;
    }

    public void setProbabilityForContainsBetween2And3WordsTrueAndAuthorFalse(Double probabilityForContainsBetween2And3WordsTrueAndAuthorFalse) {
        this.probabilityForContainsBetween2And3WordsTrueAndAuthorFalse = probabilityForContainsBetween2And3WordsTrueAndAuthorFalse;
    }

    public Double getProbabilityForContainsBetween2And3WordsFalseAndAuthorFalse() {
        return probabilityForContainsBetween2And3WordsFalseAndAuthorFalse;
    }

    public void setProbabilityForContainsBetween2And3WordsFalseAndAuthorFalse(Double probabilityForContainsBetween2And3WordsFalseAndAuthorFalse) {
        this.probabilityForContainsBetween2And3WordsFalseAndAuthorFalse = probabilityForContainsBetween2And3WordsFalseAndAuthorFalse;
    }

    public Double getProbabilityForIsAuthorTrue() {
        return probabilityForIsAuthorTrue;
    }

    public void setProbabilityForIsAuthorTrue(Double probabilityForIsAuthorTrue) {
        this.probabilityForIsAuthorTrue = probabilityForIsAuthorTrue;
    }

    public Double getProbabilityForIsAuthorFalse() {
        return probabilityForIsAuthorFalse;
    }

    public void setProbabilityForIsAuthorFalse(Double probabilityForIsAuthorFalse) {
        this.probabilityForIsAuthorFalse = probabilityForIsAuthorFalse;
    }
}
