package com.openpayd.strategy;

import com.openpayd.client.CurrencyLayerClient;
import com.openpayd.client.response.ConvertCurrencyResponse;
import com.openpayd.exception.ExternalApiCallException;
import com.openpayd.model.dto.ConvertCurrencyResponseDto;
import com.openpayd.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CurrencyLayerApiStrategy implements ExchangeRateStrategy {

    private final CurrencyLayerClient client;

    @Value("${currency-client.api-key}")
    private String apiKey;

    @Override
    public Double getExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        var response = client.getExchangeRates(apiKey, fromCurrencyCode, toCurrencyCode);
        return response.getQuotes().get(fromCurrencyCode + toCurrencyCode);
    }

    @Override
    public ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        ConvertCurrencyResponse response;
        try {
            response = client.convert(apiKey, fromCurrencyCode, toCurrencyCode, amount);
        } catch (Exception e) {
            log.error("Error occurred while calling. Error:{}", e.getMessage());
            throw new ExternalApiCallException(e.getMessage(), e.getCause());
        }

        if (Objects.isNull(response) || Objects.isNull(response.getResult())) {
            log.error("Api returned empty response");
            throw new ExternalApiCallException("Api returned empty response");
        }

        var convertedDate = DateTimeUtil.convertTimestampToLocalDateTime(response.getInfo().getTimestamp());

        return ConvertCurrencyResponseDto.builder()
                .convertedAmount(response.getResult())
                .convertedDate(convertedDate)
                .build();
    }
}