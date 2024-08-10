package com.openpayd.client;

import com.openpayd.model.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-client", url = "https://api.currencylayer.com")
public interface CurrencyLayerClient {

    @GetMapping("/live")
    ExchangeRateResponse getExchangeRates(@RequestParam("access_key") String accessKey,
                                          @RequestParam("source") String source,
                                          @RequestParam("currencies") String currencies);
}