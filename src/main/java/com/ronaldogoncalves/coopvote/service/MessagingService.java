package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.VotingResultResponseDto;

public interface MessagingService {
    void send(VotingResultResponseDto votingResultResponseDto);
}