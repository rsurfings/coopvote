package com.ronaldogoncalves.coopvote.api;

import com.ronaldogoncalves.coopvote.dto.*;
import com.ronaldogoncalves.coopvote.service.VotingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/voting", produces = "application/json")
public class VotingController {

    private final VotingService votingService;

    @Autowired
    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @Operation(summary = "Get all votes")
    @GetMapping()
    public ResponseEntity<List<VotingResponseDto>> getAllVotings() {
        return ResponseEntity.ok(votingService.getAllVotings());
    }

    @Operation(summary = "Get vote by ID")
    @GetMapping("/{id}")
    public ResponseEntity<VotingResponseDto> getVotingById(@PathVariable String id) {
        return ResponseEntity.ok(votingService.getVotingById(id));
    }

    @Operation(summary = "Create voting")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ResponseEntity<VotingResponseDto> createVoting(@RequestBody VotingRequestDto votingRequestDto) throws URISyntaxException {
        VotingResponseDto response = votingService.createVoting(votingRequestDto);
        URI location = new URI(response.getId());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Create vote")
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/vote")
    public ResponseEntity<VoteResponseDto> createVote(@RequestBody VoteRequestDto voteRequestDto) throws URISyntaxException {
        VoteResponseDto response = votingService.createVote(voteRequestDto);
        URI location = new URI(response.toString());
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Get voting result by ID")
    @GetMapping("/result/{id}")
    public ResponseEntity<VotingResultResponseDto> getVotingResult(@PathVariable String id) {
        return ResponseEntity.ok(votingService.getVotingResult(id));
    }
}
