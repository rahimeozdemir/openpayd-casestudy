package org.openpayd.com.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateRequestDto {
    private String toCurrencyCode;
    private String fromCurrencyCode;
}
