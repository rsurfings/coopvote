package com.ronaldogoncalves.coopvote.dto;

import com.ronaldogoncalves.coopvote.entity.VoteResult;

public class VotingResultResponseDto {

    private AgendaResponseDto agenda;
    private VoteResult voteResult;

    public AgendaResponseDto getAgenda() {
        return agenda;
    }

    public void setAgenda(AgendaResponseDto agenda) {
        this.agenda = agenda;
    }

    public VoteResult getVoteResult() {
        return voteResult;
    }

    public void setVoteResult(VoteResult voteResult) {
        this.voteResult = voteResult;
    }
}
