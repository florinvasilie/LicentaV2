package com.licenta.licenta.representations.resp;

import java.util.List;

public class AuthorWithTitlesResp {
    private String author;
    private List<String> titles;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getTitles() {
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }
}
