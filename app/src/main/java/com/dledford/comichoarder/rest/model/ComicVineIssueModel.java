package com.dledford.comichoarder.rest.model;

/**
 * Created by phesto on 11/18/2016.
 */

public class ComicVineIssueModel extends ComicVineModel {

    private ComicVineModel volume;
    private Integer issue_number;

    public Integer getIssue_number() {
        return issue_number;
    }

    public void setIssue_number(Integer issue_number) {
        this.issue_number = issue_number;
    }

    public ComicVineModel getVolume() {
        return volume;
    }

    public void setVolume(ComicVineModel volume) {
        this.volume = volume;
    }
}
