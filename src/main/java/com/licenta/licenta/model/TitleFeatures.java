package com.licenta.licenta.model;

import javax.persistence.*;

@Entity
@Table(name = "TITLE_FEATURES")
public class TitleFeatures {

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

    @Column(name = "IS_ITALIC")
    private boolean isItalic;

    @Column(name = "IS_IN_FIRST_10_ROWS")
    private boolean isInFirst10Rows;

    @Column(name = "IS_ALPHA")
    private boolean isAlpha;

    @Column(name = "CONTAINS_QUOTATION_MARK")
    private boolean containsQuotationMark;

    @Column(name = "IS_LENGTH_LOWER_THAN_LENGTH_MEAN")
    private boolean isLengthLowerThanLengthMean;

    @Column(name = "IS_TITLE")
    private boolean isTitle;

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

    public boolean isItalic() {
        return isItalic;
    }

    public void setItalic(boolean italic) {
        isItalic = italic;
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

    public boolean isContainsQuotationMark() {
        return containsQuotationMark;
    }

    public void setContainsQuotationMark(boolean containsQuotationMark) {
        this.containsQuotationMark = containsQuotationMark;
    }

    public boolean isLengthLowerThanLengthMean() {
        return isLengthLowerThanLengthMean;
    }

    public void setLengthLowerThanLengthMean(boolean lengthLowerThanLengthMean) {
        isLengthLowerThanLengthMean = lengthLowerThanLengthMean;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setTitle(boolean title) {
        isTitle = title;
    }
}
