package com.openpayd.strategy;

import com.openpayd.client.CurrencyLayerClient;
import com.openpayd.client.response.ConvertCurrencyResponse;
import com.openpayd.client.response.ExchangeRateResponse;
import com.openpayd.exception.ExternalApiCallException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CurrencyLayerApiStrategyTest {

    @InjectMocks
    private CurrencyLayerApiStrategy sut;

    @Mock
    private CurrencyLayerClient client;

    @Test
    void it_should_throw_an_exception_when_calling_getExchangeRate_throws_an_exception() {
        // given:
        when(client.getExchangeRates("accessKey", "source", "destination"))
                .thenThrow(FeignException.FeignClientException.class);

        // when:
        assertThrows(ExternalApiCallException.class, () -> sut.getExchangeRate("source", "destination"));

        // then:
    }

    @Test
    void it_should_throw_an_exception_when_calling_getExchangeRate_returned_failed_response() {
        // given:
        when(client.getExchangeRates("accessKey", "source", "destination"))
                .thenReturn(ExchangeRateResponse.builder()
                        .success(false)
                        .build());

        // when:
        assertThrows(ExternalApiCallException.class, () -> sut.getExchangeRate("source", "destination"));

        // then:
    }

    @Test
    void it_should_throw_an_exception_when_calling_getExchangeRate_returned_empty_response() {
        // given:
        when(client.getExchangeRates("accessKey", "source", "destination"))
                .thenReturn(null);

        // when:
        assertThrows(ExternalApiCallException.class, () -> sut.getExchangeRate("source", "destination"));

        // then:
    }

    @Test
    void it_should_call_getExchangeRate_successfully() {
        // given:
        when(client.getExchangeRates(any(), any(), any()))
                .thenReturn(ExchangeRateResponse.builder()
                        .source("TRY")
                        .quotes(Map.of("USD", 30.4))
                        .success(true)
                        .build());

        // when:
        assertDoesNotThrow(() -> sut.getExchangeRate("source", "destination"));

        // then:
    }


    @Test
    void it_should_throw_an_exception_when_calling_convert_throws_an_exception() {
        // given:
        when(client.convert(any(), any(), any(), any()))
                .thenThrow(FeignException.FeignClientException.class);

        // when:
        assertThrows(ExternalApiCallException.class,
                () -> sut.convert("source", "destination", 1.0));

        // then:
    }

    @Test
    void it_should_throw_an_exception_when_calling_convert_returned_unsuccessful_response() {
        // given:
        when(client.convert(any(), any(), any(), any()))
                .thenReturn(ConvertCurrencyResponse.builder()
                        .success(false)
                        .build());

        // when:
        assertThrows(ExternalApiCallException.class,
                () -> sut.convert("source", "destination", 1.0));

        // then:
    }

    @Test
    void it_should_throw_an_exception_when_calling_convert_returned_empty_response() {
        // given:
        when(client.convert(any(), any(), any(), any()))
                .thenReturn(null);

        // when:
        assertThrows(ExternalApiCallException.class,
                () -> sut.convert("source", "destination", 1.0));

        // then:
    }

    @Test
    void it_should_returned_successfully_when_calling_convert() {
        // given:
        when(client.convert(any(), any(), any(), any()))
                .thenReturn(ConvertCurrencyResponse.builder()
                        .success(true)
                        .result(10.0)
                        .info(ConvertCurrencyResponse.Info.builder()
                                .timestamp(1723481477L)
                                .build())
                        .build());

        // when:
        assertDoesNotThrow(() -> sut.convert("source", "destination", 1.0));

        // then:
    }
}