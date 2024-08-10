package com.openpayd.controller;

import com.openpayd.model.dto.*;
import com.openpayd.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @GetMapping("/exchange-rate")
    public ResponseEntity<BaseResponseDto<ExchangeRateResponseDto>> calculateExchangeRate(
            @RequestBody ExchangeRateRequestDto request) {

        var exchangeRate = service.calculateExchangeRate(request.getFromCurrencyCode(), request.getToCurrencyCode());

        return ResponseEntity.ok(BaseResponseDto.<ExchangeRateResponseDto>builder()
                .data(exchangeRate)
                .build());
    }

    @PostMapping("/convert-currency")
    public ResponseEntity<BaseResponseDto<ConvertCurrencyResponseDto>> convertCurrency(
            @RequestBody ConvertCurrencyRequestDto request) {

        var exchangeRate = service.convert(
                request.getFromCurrencyCode(),
                request.getToCurrencyCode(),
                request.getAmount()
        );

        return ResponseEntity.ok(BaseResponseDto.<ConvertCurrencyResponseDto>builder()
                .data(exchangeRate)
                .build());
    }
}
