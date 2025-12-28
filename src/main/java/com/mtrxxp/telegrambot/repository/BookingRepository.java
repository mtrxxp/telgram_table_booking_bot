package com.mtrxxp.telegrambot.repository;

import com.mtrxxp.telegrambot.model.BookingModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<BookingModel, Integer> {
    BookingModel findByChatId(Long chatId);

    boolean existsByChatId(Long chatId);
}
