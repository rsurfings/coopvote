package com.ronaldogoncalves.coopvote.dto;


public class AgendaResponseDto {
    private String id;
    private String name;

    public void setName(String name) {
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