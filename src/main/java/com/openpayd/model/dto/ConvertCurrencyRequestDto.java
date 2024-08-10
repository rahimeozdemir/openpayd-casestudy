package com.openpayd.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConvertCurrencyRequestDto {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private Double amount;
}
