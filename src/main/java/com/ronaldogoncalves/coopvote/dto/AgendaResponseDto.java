package com.ronaldogoncalves.coopvote.dto;


public class AgendaResponseDto {
    private String id;
    private final String name;

    public AgendaResponseDto(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}