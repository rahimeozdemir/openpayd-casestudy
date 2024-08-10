package com.openpayd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConvertCurrencyRequest {
    private String fromCurrencyCode;
    private String toCurrencyCode;
    private Double amount;
}
