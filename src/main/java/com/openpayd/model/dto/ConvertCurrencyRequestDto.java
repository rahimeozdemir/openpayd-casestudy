package com.openpayd.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ConvertCurrencyRequestDto {
    @Schema(description =  "Source currency code", example = "USD")
    private String fromCurrencyCode;
    @Schema(description =  "Currency code to be converted", example = "TRY")
    private String toCurrencyCode;
    @Schema(description =  "An amount in the source currency", example = "10")
    @Min(value = 1, message = "Amount must be greater than or equal to 1")
    private Double amount;
}
