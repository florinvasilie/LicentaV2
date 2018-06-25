package com.licenta.licenta.representations;

import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class TextStripper extends PDFTextStripper {

    public TextStripper() throws IOException {
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        String prevBaseFont = "";
        StringBuilder builder = new StringBuilder();
        for (TextPosition position : textPositions){
            String baseFont = position.getFont().getName();
            float fontSize = position.getFontSizeInPt();
            float X = position.getX();
            float Y = position.getY();

            if (baseFont != null && !baseFont.equals(prevBaseFont)) {
                builder.append("<Font>").append(baseFont).append("</Font>").append("<Size>")
                        .append(Float.toString(fontSize)).append("</Size>");
                builder.append("<X>").append(X).append("</X>").append("<Y>").append(Y).append("</Y>");
                builder.append("<Text>").append(text).append("</Text>");
                break;
            }
        }
        this.writeString(builder.toString());
    }
}
