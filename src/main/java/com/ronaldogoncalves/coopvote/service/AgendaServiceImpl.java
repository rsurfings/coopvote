package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.AgendaRequestDto;
import com.ronaldogoncalves.coopvote.dto.AgendaResponseDto;
import com.ronaldogoncalves.coopvote.entity.Agenda;
import com.ronaldogoncalves.coopvote.exception.NotFoundException;
import com.ronaldogoncalves.coopvote.repository.AgendaRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository agendaRepository;

    @Autowired
    public AgendaServiceImpl(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    @Override
    public List<AgendaResponseDto> getAllAgendas() {
        List<Agenda> agendaList = agendaRepository.findAll();

        return agendaList.stream()
                .map(this::mapAgendaToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AgendaResponseDto getAgendaById(String id) {
        Agenda agenda = agendaRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new NotFoundException("Agenda not found."));

        return mapAgendaToResponseDto(agenda);
    }

    @Override
    public AgendaResponseDto createAgenda(AgendaRequestDto agendaRequestDto) {
        Agenda agenda = new Agenda(agendaRequestDto.getName());
        agenda = agendaRepository.insert(agenda);

        return mapAgendaToResponseDto(agenda);
    }

    private AgendaResponseDto mapAgendaToResponseDto(Agenda agenda) {
        AgendaResponseDto agendaResponseDto = new AgendaResponseDto();
        agendaResponseDto.setId(agenda.getId().toHexString());
        agendaResponseDto.setName(agenda.getName());

        return agendaResponseDto;
    }
}
