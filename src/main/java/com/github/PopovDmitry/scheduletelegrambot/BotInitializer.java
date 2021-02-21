package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class BotInitializer {

    public BotInitializer() {

    }

    public ScheduleTelegramBot getBot() throws TelegramApiException {
        ScheduleTelegramBot bot = ApplicationContextProvider.getApplicationContext().getBean(ScheduleTelegramBot.class);
        WebHookController webHookController = new WebHookController(bot);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot, new SetWebhook("https://ba6f5315c0fc.ngrok.io"));
        return bot;
    }
}
