package org.openpayd.com.service;


import lombok.RequiredArgsConstructor;
import org.openpayd.com.model.dto.ExchangeRateResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    public ExchangeRateResponseDto calculateExchangeRate(String toCurrencyCode, String fromCurrencyCode) {
        return null;
    }

}
