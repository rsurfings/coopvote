package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.AgendaRequestDto;
import com.ronaldogoncalves.coopvote.dto.AgendaResponseDto;

import java.util.List;

public interface AgendaService {
    List<AgendaResponseDto> getAllAgendas();
    AgendaResponseDto getAgendaById(String id);
    AgendaResponseDto createAgenda(AgendaRequestDto dto);
}
