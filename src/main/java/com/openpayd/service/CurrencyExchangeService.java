package com.openpayd.service;


import com.openpayd.enums.FxRateServiceProvider;
import com.openpayd.mapper.ConversationHistory;
import com.openpayd.model.db.CurrencyConversation;
import com.openpayd.model.dto.ConversationHistoryResponseDto;
import com.openpayd.model.dto.ConvertCurrencyResponseDto;
import com.openpayd.model.dto.ExchangeRateResponseDto;
import com.openpayd.repository.CurrencyExchangeRepository;
import com.openpayd.strategy.ExchangeRateFactory;
import com.openpayd.strategy.ExchangeRateStrategy;
import com.openpayd.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CurrencyExchangeService {
    private final CurrencyExchangeRepository repository;

    private final ExchangeRateStrategy exchangeRateStrategy;

    public CurrencyExchangeService(final CurrencyExchangeRepository repository, final ExchangeRateFactory exchangeRateFactory) {
        this.repository = repository;
        this.exchangeRateStrategy = exchangeRateFactory.getStrategy(FxRateServiceProvider.CURRENCY_LAYER);
    }

    @Cacheable(value = "calculateExchangeRateCache")
    public ExchangeRateResponseDto calculateExchangeRate(String fromCurrencyCode, String toCurrencyCode) {
        var exchangeRate = exchangeRateStrategy.getExchangeRate(fromCurrencyCode, toCurrencyCode);

        return ExchangeRateResponseDto.builder()
                .quote(exchangeRate)
                .build();
    }

    public ConvertCurrencyResponseDto convert(String fromCurrencyCode, String toCurrencyCode, Double amount) {
        var response = exchangeRateStrategy.convert(fromCurrencyCode, toCurrencyCode, amount);

        var currencyConversation = CurrencyConversation.builder()
                .fromCurrencyCode(fromCurrencyCode)
                .toCurrencyCode(toCurrencyCode)
                .amount(amount)
                .convertedAmount(response.getConvertedAmount())
                .convertedDate(response.getConvertedDate())
                .build();

        repository.save(currencyConversation);

        log.info("Conversation history saved successfully");

        return response;
    }

    public ConversationHistoryResponseDto getAllConversations(String dateTime, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        var convertedDate = DateTimeUtil.convertStringToLocalDateTime(dateTime);
        var currencyConversationList = repository.findByConvertedDateAfter(convertedDate, paging);

        var data = ConversationHistory.INSTANCE.tasksToTaskDtoList(currencyConversationList);

        return ConversationHistoryResponseDto.builder()
                .conversationHistories(data)
                .build();
    }


}
