package com.openpayd.service;


import com.openpayd.client.CurrencyLayerClient;
import com.openpayd.mapper.ConversationHistory;
import com.openpayd.model.db.CurrencyConversation;
import com.openpayd.model.dto.ConversationHistoryResponseDto;
import com.openpayd.model.dto.ConvertCurrencyResponseDto;
import com.openpayd.model.dto.ExchangeRateResponseDto;
import com.openpayd.repository.CurrencyExchangeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyExchangeService {

    private final CurrencyLayerClient client;
    private final CurrencyExchangeRepository repository;

    @Value("${currency-client.api-key}")
    private String apiKey;

    public ExchangeRateResponseDto calculateExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        var response = client.getExchangeRates(apiKey, fromCurrencyCode, toCurrencyCode);

        return ExchangeRateResponseDto.builder()
                .quote(response.getQuotes().get(fromCurrencyCode + toCurrencyCode))
                .build();
    }

    public ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        var response = client.convert(apiKey, fromCurrencyCode, toCurrencyCode, amount);

        var convertedDate = convertTimestampToLocalDateTime(response.getInfo().getTimestamp());

        var currencyConversation = CurrencyConversation.builder()
                .fromCurrencyCode(fromCurrencyCode)
                .toCurrencyCode(toCurrencyCode)
                .amount(amount)
                .convertedAmount(response.getResult())
                .convertedDate(convertedDate)
                .build();

        repository.save(currencyConversation);

        log.info("Conversation history saved successfully");


        return ConvertCurrencyResponseDto.builder()
                .convertedAmount(response.getResult())
                .dateTime(convertedDate)
                .build();
    }

    public ConversationHistoryResponseDto getAllConversations(String timestamp, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        var convertedDate = convertStringToLocalDateTime(timestamp);
        var currencyConversationList = repository.findByConvertedDateAfter(convertedDate, paging);

        var data = ConversationHistory.INSTANCE.tasksToTaskDtoList(currencyConversationList);

        return ConversationHistoryResponseDto.builder()
                .conversationHistories(data)
                .build();
    }

    private LocalDateTime convertTimestampToLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    private LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString);
    }
}
