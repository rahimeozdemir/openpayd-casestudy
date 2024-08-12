package com.openpayd.strategy;

import com.openpayd.enums.FxRateServiceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeRateFactory {

    private final CurrencyLayerApiStrategy currencyLayerApiStrategy;

    public ExchangeRateStrategy getStrategy(FxRateServiceProvider apiType) {
        if (FxRateServiceProvider.CURRENCY_LAYER.equals(apiType)) {
            return currencyLayerApiStrategy;
        } else {
            throw new IllegalArgumentException("Invalid API type");
        }
    }
}