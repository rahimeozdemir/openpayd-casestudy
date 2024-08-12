package com.openpayd.controller;

import com.openpayd.model.dto.ConversationHistoryResponseDto;
import com.openpayd.model.dto.ConvertCurrencyRequestDto;
import com.openpayd.model.dto.ConvertCurrencyResponseDto;
import com.openpayd.model.dto.ExchangeRateResponseDto;
import com.openpayd.model.dto.common.BaseResponseDto;
import com.openpayd.service.CurrencyExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {

    private final CurrencyExchangeService service;

    @Operation(summary = "Calculate exchange rates",
            description = "Calculates the exchange rate between two given currencies")
    @GetMapping("/exchange-rate")
    public ResponseEntity<BaseResponseDto<ExchangeRateResponseDto>> calculateExchangeRate(
            @Parameter(description =  "Source currency code", example = "USD", required = true)
            @RequestParam String fromCurrencyCode,
            @Parameter(description =  "Currency code to be converted", example = "TRY", required = true)
            @RequestParam String toCurrencyCode) {

        var exchangeRate = service.calculateExchangeRate(fromCurrencyCode, toCurrencyCode);

        return ResponseEntity.ok(BaseResponseDto.<ExchangeRateResponseDto>builder()
                .data(exchangeRate)
                .message("Exchange rate calculated successfully")
                .build());
    }

    @Operation(summary = "Convert currency",
            description = "To convert a specific amount from one currency to another")
    @PostMapping("/convert")
    public ResponseEntity<BaseResponseDto<ConvertCurrencyResponseDto>> convertCurrency(
            @RequestBody @Valid ConvertCurrencyRequestDto request) {

        var exchangeRate = service.convert(
                request.getFromCurrencyCode(),
                request.getToCurrencyCode(),
                request.getAmount()
        );

        return ResponseEntity.ok(BaseResponseDto.<ConvertCurrencyResponseDto>builder()
                .data(exchangeRate)
                .message("Currency conversion was done successfully")
                .build());
    }

    @Operation(summary = "List converted currency history",
            description = "To query the history of currency conversions")
    @GetMapping("/conversation-history")
    public ResponseEntity<BaseResponseDto<ConversationHistoryResponseDto>> getAllConversations(
            @Parameter(description =  "It will retrieve conversion operations performed after this date", example = "2024-08-12T11:04:03", required = true)
            @RequestParam String convertedDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size
    ) {
        var data = service.getAllConversations(convertedDate, page, size);

        return ResponseEntity.ok(BaseResponseDto.<ConversationHistoryResponseDto>builder()
                .data(data)
                .message("Converted currency history listed successfully")
                .build());
    }
}
