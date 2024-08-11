package com.openpayd.client;

import com.openpayd.client.response.ConvertCurrencyResponse;
import com.openpayd.client.response.ExchangeRateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "currency-client", url = "https://api.currencylayer.com")
public interface CurrencyLayerClient {

    @GetMapping("/live")
    ExchangeRateResponse getExchangeRates(@RequestParam("access_key") String accessKey,
                                          @RequestParam("source") String source,
                                          @RequestParam("currencies") String currencies);

    @GetMapping("/convert")
    ConvertCurrencyResponse convert(@RequestParam("access_key") String accessKey,
                                    @RequestParam("from") String from,
                                    @RequestParam("to") String to,
                                    @RequestParam("amount") Double amount);
}