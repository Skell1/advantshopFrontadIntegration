package org.example.advantshopfrontadintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.chatId}")
    private String chatId;

    public void logInfoMessage(String message) {
        try {
            StringBuilder stringBuilder = new StringBuilder("#info" + "\n ");
            stringBuilder.append(message);
            this.sendMessage(message);
        } catch (Exception e) {
            log.error("logInfoMessage {}", e.getMessage());
        }
    }

    public void logErrorMessage(String message) {
        try {
        StringBuilder stringBuilder = new StringBuilder("#error" + "\n ");
        stringBuilder.append(message);
        this.sendMessage(message);
        } catch (Exception e) {
            log.error("logInfoMessage {}", e.getMessage());
        }
    }

    private void sendMessage(String text) {
        var sendMessage = new SendMessage(chatId, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }

    public TelegramBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    @Override
    public String getBotUsername() {
        return "AdvantshopIntegrationBot";
    }
}
