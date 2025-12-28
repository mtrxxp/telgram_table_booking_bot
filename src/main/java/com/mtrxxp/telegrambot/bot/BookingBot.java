package com.mtrxxp.telegrambot.bot;

import com.mtrxxp.telegrambot.bot.router.MessageRouter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    String botName;
    @Value("${bot.token}")
    String botToken;

    final MessageRouter messageRouter;

    public BookingBot(MessageRouter messageRouter) {
        this.messageRouter = messageRouter;
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = messageRouter.handle(update);
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
