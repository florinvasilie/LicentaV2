package com.licenta.licenta.representations.resp;

import java.util.List;

public class AuthorAndTitle {
    private Integer page;
    private List<String> title;
    private List<String> author;

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
