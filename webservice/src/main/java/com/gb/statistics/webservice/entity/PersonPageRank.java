package com.gb.statistics.webservice.entity;


public class PersonPageRank {
    private int pageId;
    private int personId;
    private int rank;

    public PersonPageRank(int pageId, int personId, int rank) {
        this.pageId = pageId;
        this.personId = personId;
        this.rank = rank;
    }

    public PersonPageRank(){}

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
