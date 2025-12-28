package com.mtrxxp.telegrambot.bot.handler;

import com.mtrxxp.telegrambot.service.BookingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeleteHandler {
    final BookingService bookingService;

    public DeleteHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public SendMessage deleteBooking(Long chatId){
        bookingService.deleteBooking(chatId);
        return new SendMessage(chatId.toString(), "Your booking has been canceled.");
    }

}
