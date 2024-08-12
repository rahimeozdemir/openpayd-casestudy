package com.openpayd.service;

import com.openpayd.client.CurrencyLayerClient;
import com.openpayd.client.response.ConvertCurrencyResponse;
import com.openpayd.client.response.ExchangeRateResponse;
import com.openpayd.enums.FxRateServiceProvider;
import com.openpayd.model.db.CurrencyConversation;
import com.openpayd.repository.CurrencyExchangeRepository;
import com.openpayd.strategy.CurrencyLayerApiStrategy;
import com.openpayd.strategy.ExchangeRateFactory;
import com.openpayd.strategy.ExchangeRateStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyExchangeServiceTest {

    private CurrencyExchangeService sut;

    @Mock
    private CurrencyLayerClient client;

    @Mock
    private ExchangeRateFactory exchangeRateFactory;

    @Mock
    private CurrencyExchangeRepository repository;

    private ExchangeRateStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new CurrencyLayerApiStrategy(client);
        when(exchangeRateFactory.getStrategy(FxRateServiceProvider.CURRENCY_LAYER)).thenReturn(strategy);

        sut = new CurrencyExchangeService(repository, exchangeRateFactory);
    }

    @Test
    void calculateExchangeRate() {
        // given:
        when(client.getExchangeRates(null, "source", "destination"))
                .thenReturn(ExchangeRateResponse.builder()
                        .quotes(Map.of("USD", 1.0))
                        .success(true)
                        .build());

        // when:
        sut.calculateExchangeRate("source", "destination");

        // then:
    }

    @Test
    void convert() {
        // given:
        when(client.convert(null, "source", "destination", 1.0))
                .thenReturn(ConvertCurrencyResponse.builder()
                        .result(1.0)
                        .success(true)
                        .info(ConvertCurrencyResponse.Info.builder()
                                .timestamp(1723481477L)
                                .build())
                        .build());

        // when:
        sut.convert("source", "destination", 1.0);


        // then:
        verify(repository).save(any());
    }

    @Test
    void getAllConversations() {
        // given:
        var date = "2024-08-12T11:04:03";
        when(repository.findByConvertedDateAfter(any(), any()))
                        .thenReturn(List.of(CurrencyConversation.builder().build()));

        // when:
        sut.getAllConversations(date, 0, 3);

        // then:
    }
}