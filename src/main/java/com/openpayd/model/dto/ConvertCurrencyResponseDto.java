package com.openpayd.model.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
@Data
public class ConvertCurrencyResponseDto {
    private Double convertedAmount;
    private Long timestamp;
}
