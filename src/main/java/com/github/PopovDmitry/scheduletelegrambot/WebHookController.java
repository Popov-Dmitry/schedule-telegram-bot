package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
public class WebHookController {
    private final ScheduleTelegramBot bot;

    @Autowired
    public WebHookController(ScheduleTelegramBot bot) { this.bot = bot; }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        System.out.println("Controller");
        return bot.onWebhookUpdateReceived(update);
    }
}