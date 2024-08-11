package com.openpayd.controller;

import com.openpayd.model.dto.*;
import com.openpayd.model.dto.common.BaseResponseDto;
import com.openpayd.service.CurrencyExchangeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @GetMapping("/exchange-rate")
    public ResponseEntity<BaseResponseDto<ExchangeRateResponseDto>> calculateExchangeRate(
            @RequestParam String fromCurrencyCode,
            @RequestParam String toCurrencyCode
            ) {

        var exchangeRate = service.calculateExchangeRate(fromCurrencyCode, toCurrencyCode);

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

    @GetMapping("/conversation-histories")
    public ResponseEntity<BaseResponseDto<ConversationHistoryResponseDto>> getAllConversations(
            @RequestParam String convertedDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {

        var data = service.getAllConversations(convertedDate, page, size);

        return ResponseEntity.ok(BaseResponseDto.<ConversationHistoryResponseDto>builder()
                .data(data)
                .build());
    }
}
