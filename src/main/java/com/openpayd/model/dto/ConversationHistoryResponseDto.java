package com.openpayd.model.dto;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Jacksonized
public class ConversationHistoryResponseDto {

    private List<ConversationHistory> conversationHistories;

    @Data
    @Builder
    @Jacksonized
    public static class ConversationHistory {
        private String fromCurrencyCode;
        private String toCurrencyCode;
        private Double amount;
        private Double convertedAmount;
        private LocalDateTime convertedDate;
    }
}
