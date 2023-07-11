package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.*;
import com.ronaldogoncalves.coopvote.entity.*;
import com.ronaldogoncalves.coopvote.exception.BusinessException;
import com.ronaldogoncalves.coopvote.exception.NotFoundException;
import com.ronaldogoncalves.coopvote.integration.CpfService;
import com.ronaldogoncalves.coopvote.repository.AgendaRepository;
import com.ronaldogoncalves.coopvote.repository.VotingRepository;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class VotingServiceImpl implements VotingService {

    private final String minutesToExpiration;
    private final VotingRepository votingRepository;
    private final AgendaRepository agendaRepository;
    private final ModelMapper modelMapper;
    private final CpfService cpfService;

    @Autowired
    public VotingServiceImpl(@Value("{$default.expiration.minutes}") String minutesToExpiration,
                             VotingRepository votingRepository, ModelMapper modelMapper,
                             AgendaRepository agendaRepository,
                             CpfService cpfService) {
        this.minutesToExpiration = minutesToExpiration;
        this.votingRepository = votingRepository;
        this.agendaRepository = agendaRepository;
        this.modelMapper = modelMapper;
        this.cpfService = cpfService;
    }

    @Override
    public List<VotingResponseDto> getAllVotings() {
        List<Voting> votingList = votingRepository.findAll();

        return votingList.stream()
                .map(this::mapVotingToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public VotingResponseDto getVotingById(String id) {
        Voting voting = findVoting(id);
        return mapVotingToResponseDto(voting);
    }

    @Override
    public VotingResponseDto createVoting(VotingRequestDto votingRequestDto) {
        Agenda agenda = findAgendaById(votingRequestDto.getAgendaId());
        int minutesToExpiration = getMinutesToExpiration(votingRequestDto);

        Voting voting = new Voting(agenda, minutesToExpiration);
        voting = votingRepository.save(voting);

        return mapVotingToResponseDto(voting);
    }

    private Agenda findAgendaById(String id) {
        return agendaRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Agenda not found."));
    }

    private int getMinutesToExpiration(VotingRequestDto votingRequestDto) {
        Integer minutesToExpiration = votingRequestDto.getMinutesToExpiration();
        return minutesToExpiration != null && minutesToExpiration > 0
                ? minutesToExpiration
                : Integer.parseInt(Objects.requireNonNull(this.minutesToExpiration));
    }

    @Override
    public VoteResponseDto createVote(VoteRequestDto voteRequestDto) {
        Voting voting = findVotingById(voteRequestDto.getVotingId());
        validateVote(voting, voteRequestDto);

        Vote vote = createVoteFromRequest(voteRequestDto);
        voting.addVote(vote);
        votingRepository.save(voting);

        return new VoteResponseDto(true);
    }

    private Vote createVoteFromRequest(VoteRequestDto voteRequestDto) {
        String cpf = voteRequestDto.getCpf();
        Answer answer = voteRequestDto.getAnswer();
        return new Vote(cpf, answer);
    }


    @Override
    public VotingResultResponseDto getVotingResult(String id) {
        Voting voting = findVotingById(id);

        if (!voting.isExpired()) {
            throw new BusinessException("The voting has not yet expired. It will be closed at " + voting.getExpirationDate().toString());
        }

        List<Vote> votes = voting.getVotes();
        long yesVotes = countVotesByAnswer(votes, Answer.YES);
        long noVotes = countVotesByAnswer(votes, Answer.NO);

        VoteResult voteResult = new VoteResult(yesVotes, noVotes);

        VotingResultResponseDto votingResultResponseDto = new VotingResultResponseDto();
        votingResultResponseDto.setAgenda(mapAgendaToResponseDto(voting.getAgenda()));
        votingResultResponseDto.setVoteResult(voteResult);

        return votingResultResponseDto;
    }

    private Voting findVoting(String id) {
        return votingRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Voting not found."));
    }

    private Voting findVotingById(String id) {
        return votingRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Voting not found."));
    }

    private void validateVote(Voting voting, VoteRequestDto voteRequestDto) {
        if (voting.isExpired()) {
            throw new BusinessException("The voting has expired.");
        }

        String cpf = voteRequestDto.getCpf();
        if (voting.hasVotedWithCpf(cpf)) {
            throw new BusinessException("The associate with CPF (" + cpf + ") has already voted.");
        }

        if (!cpfService.isAbleToVote(cpf)) {
            throw new BusinessException("CPF is not eligible to vote.");
        }
    }

    private long countVotesByAnswer(List<Vote> votes, Answer answer) {
        return votes.stream()
                .filter(vote -> vote.getAnswer() == answer)
                .count();
    }

    private VotingResponseDto mapVotingToResponseDto(Voting voting) {
        return modelMapper.map(voting, VotingResponseDto.class);
    }

    private AgendaResponseDto mapAgendaToResponseDto(Agenda agenda) {
        return modelMapper.map(agenda, AgendaResponseDto.class);
    }
}