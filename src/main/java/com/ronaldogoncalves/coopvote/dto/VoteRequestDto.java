package com.ronaldogoncalves.coopvote.dto;

import com.ronaldogoncalves.coopvote.entity.Answer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VoteRequestDto {
    @NotBlank(message = "Voting ID cannot be blank")
    private String votingId;

    @NotBlank(message = "CPF cannot be blank")
    private String cpf;

    @NotNull(message = "Answer cannot be null")
    private Answer answer;

    public String getVotingId() {
        return votingId;
    }

    public void setVotingId(String votingId) {
        this.votingId = votingId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
