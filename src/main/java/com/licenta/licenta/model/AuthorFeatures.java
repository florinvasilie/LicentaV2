package com.licenta.licenta.model;

import javax.persistence.*;

@Entity
@Table(name = "AUTHOR_FEATURES")
public class AuthorFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

    @Column(name = " PAGE_NUMBER")
    private Integer pageNumber;

    @Column(name = "TEXT_LINE", length = 800)
    private String textPerLine;

    @Column(name = "IS_GREATER_THAN_MEAN_MAX")
    private boolean isGreaterThanMeanMax;

    @Column(name = "IS_INIT_CAP")
    private boolean isInitCap;

    @Column(name = "IS_BOLD")
    private boolean isBold;

    @Column(name = "IS_IN_FIRST_10_ROWS")
    private boolean isInFirst10Rows;

    @Column(name = "IS_ALPHA")
    private boolean isAlpha;

    @Column(name = "IS_FIRST_CHAR_ALPHA")
    private boolean isFirstCharAlpha;

    @Column(name = "IS_LAST_CHAR_ALPHA")
    private boolean isLastCharAlpha;

    @Column(name = "IS_ALL_WORDS_INIT_CAP")
    private boolean isAllWordsInitCap;

    @Column(name = "CONTAINS_BETWEEN_2_AND_3_WORDS")
    private boolean containsBetweenTwoAndThreeWords;

    @Column(name = "IS_AUTHOR")
    private boolean isAuthor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getTextPerLine() {
        return textPerLine;
    }

    public void setTextPerLine(String textPerLine) {
        this.textPerLine = textPerLine;
    }

    public boolean isGreaterThanMeanMax() {
        return isGreaterThanMeanMax;
    }

    public void setGreaterThanMeanMax(boolean greaterThanMeanMax) {
        isGreaterThanMeanMax = greaterThanMeanMax;
    }

    public boolean isInitCap() {
        return isInitCap;
    }

    public void setInitCap(boolean initCap) {
        isInitCap = initCap;
    }

    public boolean isBold() {
        return isBold;
    }

    public void setBold(boolean bold) {
        isBold = bold;
    }

    public boolean isInFirst10Rows() {
        return isInFirst10Rows;
    }

    public void setInFirst10Rows(boolean inFirst10Rows) {
        isInFirst10Rows = inFirst10Rows;
    }

    public boolean isAlpha() {
        return isAlpha;
    }

    public void setAlpha(boolean alpha) {
        isAlpha = alpha;
    }

    public boolean isFirstCharAlpha() {
        return isFirstCharAlpha;
    }

    public void setFirstCharAlpha(boolean firstCharAlpha) {
        isFirstCharAlpha = firstCharAlpha;
    }

    public boolean isLastCharAlpha() {
        return isLastCharAlpha;
    }

    public void setLastCharAlpha(boolean lastCharAlpha) {
        isLastCharAlpha = lastCharAlpha;
    }

    public boolean isAllWordsInitCap() {
        return isAllWordsInitCap;
    }

    public void setAllWordsInitCap(boolean allWordsInitCap) {
        isAllWordsInitCap = allWordsInitCap;
    }

    public boolean isContainsBetweenTwoAndThreeWords() {
        return containsBetweenTwoAndThreeWords;
    }

    public void setContainsBetweenTwoAndThreeWords(boolean containsBetweenTwoAndThreeWords) {
        this.containsBetweenTwoAndThreeWords = containsBetweenTwoAndThreeWords;
    }

    public boolean isAuthor() {
        return isAuthor;
    }

    public void setAuthor(boolean author) {
        isAuthor = author;
    }
}
