package com.licenta.licenta.representations.resp;

import java.util.List;

public class TitleResponse {
    private Integer page;
    private List<String> title;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<String> getTitle() {
        return title;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }
}
