package com.github.PopovDmitry.scheduletelegrambot;

import com.github.PopovDmitry.scheduletelegrambot.config.BotConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ScheduleTelegramBotApplication{

	//@Autowired
	//private static ApplicationContextProvider applicationContextProvider;

	//@Autowired
	//private static ApplicationContext applicationContext;

	//@Autowired
	//private static ApplicationContextProvider applicationContextProvider;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ScheduleTelegramBotApplication.class, args);

		ScheduleParser scheduleParser = new ScheduleParser();
		scheduleParser.parseGroups();
		//System.out.println(scheduleParser.parseSchedule("АВТ-809", Day.THURSDAY));
		//System.out.println(scheduleParser.weekSchedule("АВТ-809"));

		//System.out.println(ApplicationContextProvider.getApplicationContext());

		BotInitializer botInitializer = new BotInitializer();
		ScheduleTelegramBot bot = botInitializer.getBot();

		//ScheduleTelegramBot bot = ApplicationContextProvider.getApplicationContext().getBean("scheduleTelegramBot",ScheduleTelegramBot.class);
		//ScheduleTelegramBot bot = applicationContextProvider.getApplicationContext().getBean(ScheduleTelegramBot.class);
		//ScheduleTelegramBot bot = new ScheduleTelegramBot();
//		WebHookController webHookController = new WebHookController(bot);
//		TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//		telegramBotsApi.registerBot(bot, new SetWebhook("https://4a6d6c562aca.ngrok.io"));
//		System.out.println(bot.getBotToken());
//		System.out.println(bot.getBotPath());
//		System.out.println(bot.getBotUsername());

	}

}