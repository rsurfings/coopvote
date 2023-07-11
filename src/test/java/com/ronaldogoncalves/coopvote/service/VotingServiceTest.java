package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.*;
import com.ronaldogoncalves.coopvote.entity.Agenda;
import com.ronaldogoncalves.coopvote.entity.Answer;
import com.ronaldogoncalves.coopvote.entity.Vote;
import com.ronaldogoncalves.coopvote.entity.Voting;
import com.ronaldogoncalves.coopvote.exception.BusinessException;
import com.ronaldogoncalves.coopvote.exception.NotFoundException;
import com.ronaldogoncalves.coopvote.integration.CpfService;
import com.ronaldogoncalves.coopvote.repository.AgendaRepository;
import com.ronaldogoncalves.coopvote.repository.VotingRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class VotingServiceTest {

    private static final String CPF = "1";
    private static final String AGENDA_NAME = "Test";
    private static final Integer MINUTES_TO_EXPIRATION = 10;

    @Mock
    private VotingRepository votingRepository;

    @Mock
    private AgendaRepository agendaRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private VotingServiceImpl votingService;

    @Mock
    private CpfService cpfService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldReturnZeroVotings() {
        // Arrange
        List<Voting> votings = new ArrayList<>();
        Mockito.when(votingRepository.findAll()).thenReturn(votings);

        // Act
        List<VotingResponseDto> resp = votingService.getAllVotings();

        // Assert
        assertEquals(0, resp.size());
    }

    @Test
    public void shouldReturnVotings() {
        // Arrange
        List<Voting> votings = new ArrayList<>();
        votings.add(new Voting());
        votings.add(new Voting());
        Mockito.when(votingRepository.findAll()).thenReturn(votings);

        // Act
        List<VotingResponseDto> resp = votingService.getAllVotings();

        // Assert
        assertEquals(2, resp.size());
    }

    @Test
    public void shouldNotCreateVotingWhenAgendaNotExists() {
        ObjectId id = new ObjectId();
        Agenda agenda = new Agenda(AGENDA_NAME);

        Voting voting = new Voting(agenda, 10);
        voting.setId(id);
        VotingRequestDto req = new VotingRequestDto();
        req.setAgendaId(voting.getId().toHexString());

        Mockito.when(agendaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            votingService.createVoting(req);
        });
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenGettingVotingById() {
        ObjectId id = new ObjectId();
        Mockito.when(votingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            votingService.getVotingById(id.toHexString());
        });
    }

    @Test
    public void shouldVote() {
        ObjectId id = new ObjectId();
        Agenda agenda = new Agenda(AGENDA_NAME);

        Voting voting = new Voting(agenda, MINUTES_TO_EXPIRATION);
        Mockito.when(votingRepository.findById(id)).thenReturn(Optional.of(voting));
        Mockito.when(cpfService.isAbleToVote(CPF)).thenReturn(true);

        Voting voting2 = new Voting(agenda, MINUTES_TO_EXPIRATION);
        voting2.setId(id);

        Vote vote = new Vote(CPF, Answer.NO);
        voting2.addVote(vote);

        Mockito.when(votingRepository.save(voting)).thenReturn(voting2);

        VoteRequestDto dto = new VoteRequestDto();
        dto.setCpf(CPF);
        dto.setVotingId(voting2.getId().toHexString());
        dto.setAnswer(Answer.NO);

        VoteResponseDto resp = votingService.createVote(dto);

        assertTrue(resp.isSuccess());
    }

    @Test
    public void shouldNotReturnResultWhenVotingIsOpen() {
        ObjectId id = new ObjectId();
        Agenda agenda = new Agenda(AGENDA_NAME);
        agenda.setId(id);

        Voting voting = new Voting(new Agenda(AGENDA_NAME), MINUTES_TO_EXPIRATION);
        voting.setClosed(false);
        voting.addVote(new Vote(CPF, Answer.NO));

        Mockito.when(votingRepository.findById(id)).thenReturn(Optional.of(voting));

        assertThrows(BusinessException.class, () -> {
            votingService.getVotingResult(id.toHexString());
        });
    }

    @ParameterizedTest
    @MethodSource("votingResultDataProvider")
    public void shouldReturnVotingResult(Voting voting, int expectedNoVotes, int expectedYesVotes) {
        // Arrange
        ObjectId id = new ObjectId();
        Mockito.when(votingRepository.findById(id)).thenReturn(Optional.of(voting));

        // Act
        VotingResultResponseDto resp = votingService.getVotingResult(id.toHexString());

        // Assert
        assertNotNull(resp);
        assertEquals(expectedNoVotes, resp.getVoteResult().getNo());
        assertEquals(expectedYesVotes, resp.getVoteResult().getYes());
        verify(votingRepository, times(1)).findById(id);
    }

    private static Stream<Arguments> votingResultDataProvider() {
        Voting voting = new Voting(new Agenda(AGENDA_NAME), 1);
        voting.addVote(new Vote("1", Answer.NO));
        voting.addVote(new Vote("2", Answer.NO));
        voting.addVote(new Vote("3", Answer.YES));
        voting.setExpirationDate(Instant.now().minusSeconds(MINUTES_TO_EXPIRATION));

        Voting voting2 = new Voting(new Agenda(AGENDA_NAME), 1);
        voting2.addVote(new Vote("1", Answer.YES));
        voting2.addVote(new Vote("2", Answer.YES));
        voting2.addVote(new Vote("3", Answer.YES));
        voting2.setExpirationDate(Instant.now().minusSeconds(MINUTES_TO_EXPIRATION));

        return Stream.of(
                Arguments.of(voting, 2, 1),
                Arguments.of(voting2, 0, 3)
        );
    }
}