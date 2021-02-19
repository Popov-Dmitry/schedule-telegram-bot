package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ScheduleTelegramBotApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ScheduleTelegramBotApplication.class, args);

		ScheduleParser scheduleParser = new ScheduleParser();
		scheduleParser.parseGroups();
		//System.out.println(scheduleParser.parseSchedule("АВТ-809", Day.THURSDAY));
		//System.out.println(scheduleParser.weekSchedule("АВТ-809"));

		ScheduleTelegramBot bot = new ScheduleTelegramBot();
		WebHookController webHookController = new WebHookController(bot);
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
		telegramBotsApi.registerBot(bot, new SetWebhook("https://ff556738ca48.ngrok.io"));

	}

}