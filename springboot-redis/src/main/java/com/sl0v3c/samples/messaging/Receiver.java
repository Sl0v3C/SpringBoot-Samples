package com.sl0v3c.samples.messaging;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receiver {

    public Receiver() {}

    public void receiveMessage(String message) {
        log.info("Received <{}>", message);
    }
}
