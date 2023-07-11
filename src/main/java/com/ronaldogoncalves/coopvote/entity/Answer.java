package com.ronaldogoncalves.coopvote.entity;

public enum Answer {
    NO("Não"),
    YES("Sim");

    private final String answer;

    Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }
}
