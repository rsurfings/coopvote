package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.VotingResultResponseDto;
import com.ronaldogoncalves.coopvote.kafka.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingServiceImpl implements MessagingService {

    private final Producer producer;

    @Autowired
    public MessagingServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void send(VotingResultResponseDto votingResultResponseDto) {
        String message = createMessage(votingResultResponseDto);
        producer.send(message);
    }

    private String createMessage(VotingResultResponseDto votingResultResponseDto) {
        String agendaName = votingResultResponseDto.getAgenda().getName();
        long yesVotes = votingResultResponseDto.getVoteResult().getYes();
        long noVotes = votingResultResponseDto.getVoteResult().getNo();

        return formatMessage(agendaName, yesVotes, noVotes);
    }

    private String formatMessage(String agendaName, long yesVotes, long noVotes) {
        return String.format("Agenda '%s' closed! Votes: [Yes=%d] ~ [No=%d]", agendaName, yesVotes, noVotes);
    }
}
