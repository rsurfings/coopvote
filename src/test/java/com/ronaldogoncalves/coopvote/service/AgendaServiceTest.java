package com.ronaldogoncalves.coopvote.service;

import com.ronaldogoncalves.coopvote.dto.AgendaRequestDto;
import com.ronaldogoncalves.coopvote.dto.AgendaResponseDto;
import com.ronaldogoncalves.coopvote.entity.Agenda;
import com.ronaldogoncalves.coopvote.exception.NotFoundException;
import com.ronaldogoncalves.coopvote.repository.AgendaRepository;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class AgendaServiceTest {

    @Mock
    private AgendaRepository agendaRepository;

    @InjectMocks
    private AgendaServiceImpl agendaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("agendaListProvider")
    public void shouldReturnAllAgendas(List<Agenda> agendaList) {
        when(agendaRepository.findAll()).thenReturn(agendaList);

        List<AgendaResponseDto> response = agendaService.getAllAgendas();

        assertEquals(agendaList.size(), response.size());

        for (int i = 0; i < agendaList.size(); i++) {
            Agenda agenda = agendaList.get(i);
            AgendaResponseDto responseDto = response.get(i);

            assertEquals(agenda.getId().toHexString(), responseDto.getId());
            assertEquals(agenda.getName(), responseDto.getName());
        }
    }

    private static Stream<List<Agenda>> agendaListProvider() {
        List<Agenda> agendaList = new ArrayList<>();

        Agenda agenda1 = new Agenda("Agenda 1");
        agenda1.setId(new ObjectId());
        agendaList.add(agenda1);

        Agenda agenda2 = new Agenda("Agenda 2");
        agenda2.setId(new ObjectId());
        agendaList.add(agenda2);

        Agenda agenda3 = new Agenda("Agenda 3");
        agenda3.setId(new ObjectId());
        agendaList.add(agenda3);

        return Stream.of(agendaList);
    }

    @Test
    public void shouldReturnAgendaById() {
        ObjectId id = new ObjectId();
        Agenda agenda = new Agenda("test 1");
        agenda.setId(id);

        when(agendaRepository.findById(eq(id))).thenReturn(Optional.of(agenda));

        AgendaResponseDto response = agendaService.getAgendaById(id.toHexString());

        assertEquals(id.toHexString(), response.getId());
        assertEquals(agenda.getName(), response.getName());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenAgendaNotFoundById() {
        ObjectId id = new ObjectId();

        when(agendaRepository.findById(eq(id))).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> agendaService.getAgendaById(id.toHexString()));
    }

    @Test
    public void shouldCreateAgenda() {
        String agendaName = "Agenda 1";
        ObjectId id = new ObjectId();
        AgendaRequestDto requestDto = new AgendaRequestDto();
        requestDto.setName(agendaName);

        Agenda agenda = new Agenda(agendaName);
        agenda.setId(id);

        when(agendaRepository.insert(any(Agenda.class))).thenReturn(agenda);

        AgendaResponseDto response = agendaService.createAgenda(requestDto);

        assertEquals(id.toHexString(), response.getId());
        assertEquals(agendaName, response.getName());
    }
}
