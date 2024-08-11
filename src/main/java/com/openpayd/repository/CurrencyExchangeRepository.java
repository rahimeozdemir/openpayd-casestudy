package com.openpayd.repository;

import com.openpayd.model.db.CurrencyConversation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CurrencyExchangeRepository extends JpaRepository<CurrencyConversation, Long> {

    List<CurrencyConversation> findByConvertedDateAfter(LocalDateTime convertedDate, Pageable pageable);
}
