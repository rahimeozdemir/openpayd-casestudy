package com.openpayd.model.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;

@Jacksonized
@Builder
@Data
public class ConvertCurrencyResponseDto {
    private Double convertedAmount;
    private LocalDateTime dateTime;
}
