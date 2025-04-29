package com.osq.websockets.chat.models;

public enum MessageType {
    join ("join"),
    leave ("leave"),
    message ("message"),
    writing ("writing");

    public String value;

    private MessageType(String value) {
        this.value = value;
    }
}
