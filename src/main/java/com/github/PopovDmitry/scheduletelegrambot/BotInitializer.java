package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@PropertySource("classpath:bot.properties")
public class BotInitializer {

    public BotInitializer() {}

    public ScheduleTelegramBot getBot() throws TelegramApiException {
        ScheduleTelegramBot bot = ApplicationContextProvider.getApplicationContext().getBean(ScheduleTelegramBot.class);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot, new SetWebhook("${bot.webHookPath}"));
        return bot;
    }
}
