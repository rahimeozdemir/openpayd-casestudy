package com.openpayd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openpayd.model.dto.ConversationHistoryResponseDto;
import com.openpayd.model.dto.ConvertCurrencyResponseDto;
import com.openpayd.model.dto.ExchangeRateResponseDto;
import com.openpayd.service.CurrencyExchangeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CurrencyExchangeController.class)
class CurrencyExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SneakyThrows
    @Test
    void calculateExchangeRate() {
        // given:
        when(currencyExchangeService.calculateExchangeRate(any(), any()))
                .thenReturn(ExchangeRateResponseDto.builder()
                        .quote(1.0)
                        .build());

        // when:
        this.mockMvc.perform(get("/exchange-rate")
                        .param("fromCurrencyCode", "USD")
                        .param("toCurrencyCode", "TRY")
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }


    @Test
    @SneakyThrows
    void convertCurrency() {
        // given:
        var convertCurrencyResponseDto = ConvertCurrencyResponseDto.builder()
                .convertedAmount(10.0)
                .convertedDate(LocalDateTime.now())
                .build();

        when(currencyExchangeService.convert(anyString(), anyString(), anyDouble()))
                .thenReturn(convertCurrencyResponseDto);

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();

        String requestJson = mapper.writeValueAsString(convertCurrencyResponseDto);

        // when:
        this.mockMvc.perform(post("/convert")
                        .content(requestJson)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }

    @Test
    @SneakyThrows
    void getAllConversations() {
        // given:
        when(currencyExchangeService.getAllConversations(any(), anyInt(), anyInt()))
                .thenReturn(ConversationHistoryResponseDto.builder()
                        .conversationHistories(List.of(ConversationHistoryResponseDto.ConversationHistory.builder()
                                        .fromCurrencyCode("TRY")
                                        .toCurrencyCode("USD")
                                        .amount(10.0)
                                .build()))
                        .build());

        // when:
        this.mockMvc.perform(get("/conversation-history")
                        .param("convertedDate", "2024-08-12T11:04:03")
                        .param("page", "0")
                        .param("size", "3")
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then:
    }
}