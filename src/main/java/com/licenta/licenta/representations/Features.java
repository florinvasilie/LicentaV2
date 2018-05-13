package com.licenta.licenta.representations;

public class Features {
    private Integer page;
    private String pdfName;
    private String textLine;
    private boolean isMax;
    private boolean isInitCap;
    private boolean isBold;
    private boolean isItalic;
    private boolean isInFirst10Rows;
    private boolean isAlpha;
    private boolean containsQuotationMark;
    private boolean isLengthGreaterThan15;
    private boolean isLengthLowerThan50;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getTextLine() {
        return textLine;
    }

    public void setTextLine(String textLine) {
        this.textLine = textLine;
    }

    public boolean isMax() {
        return isMax;
    }

    public void setMax(boolean max) {
        isMax = max;
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
        this.isAlpha = alpha;
    }

    public boolean isContainsQuotationMark() {
        return containsQuotationMark;
    }

    public void setContainsQuotationMark(boolean containsQuotationMark) {
        this.containsQuotationMark = containsQuotationMark;
    }

    public boolean isLengthGreaterThan15() {
        return isLengthGreaterThan15;
    }

    public void setLengthGreaterThan15(boolean lengthGreaterThan15) {
        isLengthGreaterThan15 = lengthGreaterThan15;
    }

    public boolean isLengthLowerThan50() {
        return isLengthLowerThan50;
    }

    public void setLengthLowerThan50(boolean lengthLowerThan50) {
        isLengthLowerThan50 = lengthLowerThan50;
    }
}
