package com.mtrxxp.telegrambot.bot.handler;

import com.mtrxxp.telegrambot.bot.state.BookingStep;
import com.mtrxxp.telegrambot.model.BookingModel;
import com.mtrxxp.telegrambot.service.BookingService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingHandler {
    final BookingService bookingService;

    private final Map<Long, BookingStep> userSteps = new HashMap<>();
    private final Map<Long, BookingModel> userBookings = new HashMap<>();

    public BookingHandler(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    public SendMessage handleBooking(Long chatId, String text) {
        boolean exist = bookingService.bookingExists(chatId);
        if (!userSteps.containsKey(chatId) && !exist) {
            userSteps.put(chatId, BookingStep.ASK_NAME);

            BookingModel booking = new BookingModel();
            booking.setChatId(chatId);
            userBookings.put(chatId, booking);

            return new SendMessage(chatId.toString(), "Enter your name:");
        } else{
            if (exist) {
                return new SendMessage(chatId.toString(),
                        "You already have a booking. To make a new one, please cancel the existing booking first.");
            }
        }

        BookingStep step = userSteps.get(chatId);
        BookingModel booking = userBookings.get(chatId);

        switch (step) {

            case ASK_NAME:
                booking.setName(text);
                userSteps.put(chatId, BookingStep.ASK_DATE);
                return new SendMessage(chatId.toString(),
                        "Enter date of booking (for example, 2025-05-02):");

            case ASK_DATE:
                booking.setBookingDate(text);
                userSteps.put(chatId, BookingStep.ASK_PEOPLE);
                return new SendMessage(chatId.toString(), "How many people?");

            case ASK_PEOPLE:
                try {
                    booking.setNumberOfPeople(Integer.parseInt(text));
                } catch (NumberFormatException e) {
                    return new SendMessage(chatId.toString(), "Write a number!");
                }
                userSteps.put(chatId, BookingStep.ASK_REQUESTS);
                return new SendMessage(chatId.toString(),
                        "Additional requests? (or write 'no')");

            case ASK_REQUESTS:
                booking.setAdditionalRequests(text);

                booking.setId(UUID.randomUUID().hashCode());
                bookingService.addBooking(booking);

                userSteps.remove(chatId);
                userBookings.remove(chatId);

                return new SendMessage(chatId.toString(),
                        "Your booking saved! Thank you!");
        }
        return null;
    }
}
