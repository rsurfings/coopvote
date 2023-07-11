package com.ronaldogoncalves.coopvote.api;

import com.ronaldogoncalves.coopvote.dto.AgendaRequestDto;
import com.ronaldogoncalves.coopvote.dto.AgendaResponseDto;
import com.ronaldogoncalves.coopvote.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/agenda", produces = "application/json")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    @GetMapping()
    public ResponseEntity<List<AgendaResponseDto>> getAllAgendas() {
        List<AgendaResponseDto> agendas = agendaService.getAllAgendas();
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Agenda by ID")
    public ResponseEntity<AgendaResponseDto> getAgendaById(@PathVariable String id) {
        AgendaResponseDto agenda = agendaService.getAgendaById(id);
        return ResponseEntity.ok(agenda);
    }

    @PostMapping()
    @Operation(summary = "Create agenda")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AgendaResponseDto> createAgenda(@RequestBody AgendaRequestDto agenda) throws URISyntaxException {
        AgendaResponseDto response = agendaService.createAgenda(agenda);
        URI location = new URI(response.getId());
        return ResponseEntity.created(location).body(response);
    }
}
