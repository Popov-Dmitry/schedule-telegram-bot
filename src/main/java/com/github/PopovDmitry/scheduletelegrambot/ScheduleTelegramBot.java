package com.github.PopovDmitry.scheduletelegrambot;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//@PropertySource("classpath:bot.properties")
public class ScheduleTelegramBot extends TelegramWebhookBot {

    //@Value("${bot.token}")
    @Setter
    private String botToken;

    //@Value("${bot.username}")
    @Setter
    private String botUsername;

    //@Value("${bot.webHookPath}")
    @Setter
    private String webHookPath;

    public ScheduleTelegramBot() {
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {

        String inputText = update.getMessage().getText();

        if(inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Hello from Java!");
            System.out.println("start");

            try {
                execute(message);
            }
            catch (TelegramApiException exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String getBotPath() {
        return webHookPath;
    }
}
