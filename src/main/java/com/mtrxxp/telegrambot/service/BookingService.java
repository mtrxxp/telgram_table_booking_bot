package com.mtrxxp.telegrambot.service;

import com.mtrxxp.telegrambot.model.BookingModel;
import com.mtrxxp.telegrambot.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    @Autowired
    public BookingRepository bookingRepository;

    public void addBooking(BookingModel booking) {
        bookingRepository.save(booking);
    }

    public BookingModel getBooking(Long chatId) {
        return bookingRepository.findByChatId(chatId);
    }

    public boolean bookingExists(Long chatId) {
        return bookingRepository.existsByChatId(chatId);
    }

    public void deleteBooking(Long chatId) {
        BookingModel booking = bookingRepository.findByChatId(chatId);
        if (booking != null) {
            bookingRepository.delete(booking);
        }
    }
}
