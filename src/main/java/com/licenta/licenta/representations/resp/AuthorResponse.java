package com.licenta.licenta.representations.resp;

import java.util.List;

public class AuthorResponse {
    private Integer page;
    private List<String> author;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<String> getAuthor() {
        return author;
    }

    public void setAuthor(List<String> author) {
        this.author = author;
    }
}
