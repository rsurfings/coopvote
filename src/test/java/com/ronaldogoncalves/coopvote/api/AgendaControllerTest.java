package com.ronaldogoncalves.coopvote.api;

import com.ronaldogoncalves.coopvote.dto.AgendaRequestDto;
import com.ronaldogoncalves.coopvote.dto.AgendaResponseDto;
import com.ronaldogoncalves.coopvote.service.AgendaService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AgendaControllerTest {

    private static final String AGENDA_NAME = "test";

    private AgendaController agendaController;

    @Mock
    private AgendaService agendaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        agendaController = new AgendaController(agendaService);
    }

    @Test
    public void shouldReturnZeroAgendas() {
        // Arrange
        ResponseEntity<List<AgendaResponseDto>> expectedResponse = ResponseEntity.ok(new ArrayList<>());

        // Act
        ResponseEntity<List<AgendaResponseDto>> response = agendaController.getAllAgendas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
    }

    @Test
    public void shouldReturnAgendas() {
        // Arrange
        List<AgendaResponseDto> agendaList = new ArrayList<>();
        AgendaResponseDto agendaResponseDto = new AgendaResponseDto();
        agendaResponseDto.setId("1");
        agendaResponseDto.setName(AGENDA_NAME);
        agendaList.add(agendaResponseDto);
        ResponseEntity<List<AgendaResponseDto>> expectedResponse = ResponseEntity.ok(agendaList);
        when(agendaService.getAllAgendas()).thenReturn(agendaList);

        // Act
        ResponseEntity<List<AgendaResponseDto>> response = agendaController.getAllAgendas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse.getBody().size(), response.getBody().size());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }


    @Test
    public void shouldCreateAgenda() throws URISyntaxException {
        // Arrange
        String agendaName = AGENDA_NAME;
        AgendaRequestDto requestDto = new AgendaRequestDto(agendaName);
        AgendaResponseDto responseDto = new AgendaResponseDto();
        responseDto.setId(new ObjectId().toHexString());
        responseDto.setName(agendaName);
        ResponseEntity<AgendaResponseDto> expectedResponse = ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
        when(agendaService.createAgenda(requestDto)).thenReturn(responseDto);

        // Act
        ResponseEntity<AgendaResponseDto> response = agendaController.createAgenda(requestDto);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedResponse.getBody(), response.getBody());
    }
}
