package com.openpayd.strategy;

import com.openpayd.enums.FxRateServiceProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ExchangeRateFactoryTest {

    @InjectMocks
    private ExchangeRateFactory sut;

    @Test
    void it_should_throw_an_exception() {
        // given:

        // when:
        assertThrows(IllegalArgumentException.class, () -> sut.getStrategy(FxRateServiceProvider.UNKNOWN));

        // then:
    }

    @Test
    void it_should_successfully() {
        // given:

        // when:
        assertDoesNotThrow(() -> sut.getStrategy(FxRateServiceProvider.CURRENCY_LAYER));

        // then:
    }
}