package com.logistics.rms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SurCharge {
    private Integer surchargeRate;
    private String surchargeDescription;
}
