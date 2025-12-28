package com.mtrxxp.telegrambot.bot.router;

import com.mtrxxp.telegrambot.bot.handler.BookingHandler;
import com.mtrxxp.telegrambot.bot.handler.DeleteHandler;
import com.mtrxxp.telegrambot.bot.handler.MenuHandler;
import com.mtrxxp.telegrambot.bot.handler.StatusHandler;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRouter {
    final BookingHandler bookingHandler;
    final MenuHandler menuHandler;
    private final StatusHandler statusHandler;
    private final DeleteHandler deleteHandler;

    public MessageRouter(BookingHandler bookingHandler, MenuHandler menuHandler, StatusHandler statusHandler, DeleteHandler deleteHandler) {
        this.bookingHandler = bookingHandler;
        this.menuHandler = menuHandler;
        this.statusHandler = statusHandler;
        this.deleteHandler = deleteHandler;
    }

    public SendMessage handle(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return null;
        }

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        return switch (text) {
            case "/start" -> menuHandler.mainMenu(chatId);
            case "Check Status", "/status" -> statusHandler.handleStatus(chatId);
            case "Cancel Booking", "/cancel" -> deleteHandler.deleteBooking(chatId);
            default ->
                    bookingHandler.handleBooking(chatId, text);
        };
    }
}
