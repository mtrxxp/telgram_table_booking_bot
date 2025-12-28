package com.mtrxxp.telegrambot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.mtrxxp.telegrambot.bot.BookingBot;

@Configuration
public class BookingBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(BookingBot bookingBot) throws Exception {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(bookingBot);
        return botsApi;
    }
}
