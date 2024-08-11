package com.openpayd.mapper;


import com.openpayd.model.db.CurrencyConversation;
import com.openpayd.model.dto.ConversationHistoryResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ConversationHistory.class})
public interface ConversationHistory {
    ConversationHistory INSTANCE = Mappers.getMapper(ConversationHistory.class);

    List<ConversationHistoryResponseDto.ConversationHistory> tasksToTaskDtoList(List<CurrencyConversation> conversationList);
}