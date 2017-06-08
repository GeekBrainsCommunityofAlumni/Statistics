package com.gb.statistics.webservice.messages;


public class RestResponse {
    private String status;
    private String description;

    public RestResponse(String status, String description) {
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }
}
