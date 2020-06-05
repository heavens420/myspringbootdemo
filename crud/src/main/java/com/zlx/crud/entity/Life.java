package com.zlx.crud.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Life {
    private Integer id;
    private Double rest;
    private Double used;
    private String quality;
    private Integer value;
    private Date modifiedTime;
}
