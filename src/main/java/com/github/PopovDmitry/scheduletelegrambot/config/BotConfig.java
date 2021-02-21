package com.github.PopovDmitry.scheduletelegrambot.config;

import com.github.PopovDmitry.scheduletelegrambot.ScheduleTelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:bot.properties")
public class BotConfig {

    @Value("${bot.token}")
    private String botToken;

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.webHookPath}")
    private String webHookPath;

    @Bean
    public ScheduleTelegramBot scheduleTelegramBot() {
        ScheduleTelegramBot bot = new ScheduleTelegramBot();
        bot.setBotToken(botToken);
        bot.setBotUsername(botUsername);
        bot.setWebHookPath(webHookPath);
        return bot;
    }
}
