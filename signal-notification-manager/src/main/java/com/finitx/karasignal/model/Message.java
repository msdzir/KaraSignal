package com.finitx.karasignal.model;

import lombok.Data;

@Data
public class Message {

    private String to;
    private String subject;
    private String text;
}
