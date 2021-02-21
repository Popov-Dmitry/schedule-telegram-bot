package com.github.PopovDmitry.scheduletelegrambot;

import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class ScheduleTelegramBot extends TelegramWebhookBot {

    @Setter
    private String botToken;

    @Setter
    private String botUsername;

    @Setter
    private String webHookPath;

    public ScheduleTelegramBot() {}

    @Override
    public String getBotUsername() { return botUsername; }

    @Override
    public String getBotToken() { return botToken; }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {

        String inputText = update.getMessage().getText();

        if(inputText.startsWith("/start")) {
            SendMessage message = new SendMessage();
            message.setChatId(update.getMessage().getChatId().toString());
            message.setText("Hello from Java!");
            //message.setParseMode("html");
            System.out.println("start");

            try {
                execute(message);
                System.out.println("try");
            }
            catch (TelegramApiException exception) {
                System.out.println("catch");
                exception.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public String getBotPath() { return webHookPath; }
}
