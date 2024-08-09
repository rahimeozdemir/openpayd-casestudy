package org.openpayd.com.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openpayd.com.model.dto.BaseResponseDto;
import org.openpayd.com.model.dto.ExchangeRateRequestDto;
import org.openpayd.com.model.dto.ExchangeRateResponseDto;
import org.openpayd.com.service.CurrencyExchangeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @PostMapping("/exchange-rate")
    public ResponseEntity<BaseResponseDto<ExchangeRateResponseDto>> calculateExchangeRate(
            @RequestBody ExchangeRateRequestDto request) {

        var exchangeRate = service.calculateExchangeRate(request.getFromCurrencyCode(), request.getToCurrencyCode());

        return ResponseEntity.ok(BaseResponseDto.<ExchangeRateResponseDto>builder()
                .data(exchangeRate)
                .build());
    }
}
