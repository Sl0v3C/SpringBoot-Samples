package com.sl0v3c.samples.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor  // if not add this annotation, the repositories cannot get the object
public class WarehouseInfo { //implements Serializable { if you do not setValueSerializer, you can implement it
    private String code;
    private double taxRate;

    public WarehouseInfo(String code, double taxRate) {
        this.code = code;
        this.taxRate = taxRate;
    }
}
