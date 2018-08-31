package com.sl0v3c.samples.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class WarehouseInfo implements Serializable {
    private String code;
    private double taxRate;

    public WarehouseInfo(String code, double taxRate) {
        this.code = code;
        this.taxRate = taxRate;
    }
}
