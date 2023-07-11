package com.ronaldogoncalves.coopvote.entity;

public class VoteResult {
    private Long yes;
    private Long no;

    public VoteResult(Long yes, Long no) {
        this.yes = yes;
        this.no = no;
    }

    public Long getYes() {
        return yes;
    }

    public Long getNo() {
        return no;
    }
}
