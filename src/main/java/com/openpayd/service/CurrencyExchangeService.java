package com.openpayd.service;


import com.openpayd.client.CurrencyLayerClient;
import com.openpayd.model.dto.ExchangeRateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    private final CurrencyLayerClient client;

    @Value("${currency-client.api-key}")
    private String apiKey;

    public ExchangeRateResponseDto calculateExchangeRate(String toCurrencyCode, String fromCurrencyCode) {
        var response = client.getExchangeRates(apiKey, fromCurrencyCode, toCurrencyCode);

        return ExchangeRateResponseDto.builder()
                .quote(response.getQuotes().get(fromCurrencyCode + toCurrencyCode))
                .build();
    }

}
