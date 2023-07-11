package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.*;

import java.util.List;

public interface VotingService {
    List<VotingResponseDto> getAllVotings();

    VotingResponseDto getVotingById(String id);

    VotingResponseDto createVoting(VotingRequestDto votingRequestDto);

    VoteResponseDto createVote(VoteRequestDto voteRequestDto);

    VotingResultResponseDto getVotingResult(String id);
}
