package com.ronaldogoncalves.coopvote.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class VotingTest {

    @Test
    public void shouldReturnTrueIfExpired() {
        Voting voting = new Voting();
        voting.setExpirationDate(Instant.now().minusSeconds(30));

        assertTrue(voting.isExpired());
    }

    @Test
    public void shouldReturnFalseIfNotExpired() {
        Voting voting = new Voting();
        voting.setExpirationDate(Instant.now().plusSeconds(30));

        assertFalse(voting.isExpired());
    }

    @ParameterizedTest
    @MethodSource("votingData")
    public void shouldValidateCpfAlreadyVoted(String cpf1, Answer answer1, String cpf2, Answer answer2, String targetCpf, Answer targetAnswer, boolean expectedResult) {
        Voting voting = new Voting();

        voting.addVote(new Vote(cpf1, answer1));
        voting.addVote(new Vote(cpf2, answer2));

        assertEquals(expectedResult, voting.hasVotedWithCpf(targetCpf));
    }

    private static Stream<Arguments> votingData() {
        return Stream.of(
                Arguments.of("111", Answer.NO, "222", Answer.YES, "111", Answer.YES, true),
                Arguments.of("111", Answer.NO, "222", Answer.YES, "333", Answer.NO, false),
                Arguments.of("111", Answer.YES, "222", Answer.YES, "111", Answer.YES, true),
                Arguments.of("111", Answer.YES, "222", Answer.NO, "111", Answer.NO, true)
        );
    }


}
