package com.licenta.licenta.representations.resp;

import java.util.List;

public class AuthorTitleResp {
    private Integer page;
    private String textPerPage;
    private List<AuthorWithTitlesResp> authorWithTitlesRespList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getTextPerPage() {
        return textPerPage;
    }

    public void setTextPerPage(String textPerPage) {
        this.textPerPage = textPerPage;
    }

    public List<AuthorWithTitlesResp> getAuthorWithTitlesRespList() {
        return authorWithTitlesRespList;
    }

    public void setAuthorWithTitlesRespList(List<AuthorWithTitlesResp> authorWithTitlesRespList) {
        this.authorWithTitlesRespList = authorWithTitlesRespList;
    }
}
