package com.ronaldogoncalves.coopvote.integration;

public class CpfServiceResponse {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAbleToVote() {
        return "ABLE_TO_VOTE".equals(status);
    }

}
