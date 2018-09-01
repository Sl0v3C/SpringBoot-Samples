package com.sl0v3c.samples.utils.keyPrefix;

import lombok.Getter;

@Getter
public class KeyPrefix {
    private String key;

    public KeyPrefix(String part, Object object) {
        this.key =  object.getClass().getName() + ": " + part;
    }

}
