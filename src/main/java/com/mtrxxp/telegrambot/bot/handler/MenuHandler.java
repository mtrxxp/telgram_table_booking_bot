package com.mtrxxp.telegrambot.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

@Component
public class MenuHandler {
    public SendMessage mainMenu(Long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Choose action:");

        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);

        KeyboardRow row = new KeyboardRow();
        row.add("Reservation");
        row.add("Check Status");
        row.add("Cancel Booking");

        keyboard.setKeyboard(List.of(row));
        message.setReplyMarkup(keyboard);

        return message;
    }
}
