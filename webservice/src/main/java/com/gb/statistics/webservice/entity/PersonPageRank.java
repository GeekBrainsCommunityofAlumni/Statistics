package com.gb.statistics.webservice.entity;


public class PersonPageRank {
    private Long pageId;
    private Long personId;
    private int rank;

    public PersonPageRank(Long pageId, Long personId, int rank) {
        this.pageId = pageId;
        this.personId = personId;
        this.rank = rank;
    }

    public PersonPageRank(){}

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
