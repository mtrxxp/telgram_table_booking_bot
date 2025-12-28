package com.mtrxxp.telegrambot.bot.handler;

import com.mtrxxp.telegrambot.model.BookingModel;
import com.mtrxxp.telegrambot.service.BookingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StatusHandler {
    final BookingService bookingService;

    public StatusHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public SendMessage handleStatus(Long chatId){
        BookingModel booking = bookingService.getBooking(chatId);
        if (booking == null) {
            return new SendMessage(chatId.toString(), "No booking found.");
        } else {
            return new SendMessage(chatId.toString(), "Your booking details:\n" +
                    "Name: " + booking.getName() + "\n" +
                    "Date: " + booking.getBookingDate() + "\n" +
                    "Number of People: " + booking.getNumberOfPeople() + "\n" +
                    "Additional Requests: " + booking.getAdditionalRequests());
        }
    }
}
