package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ScheduleTelegramBotApplication{

	public static void main(String[] args) throws Exception {
		SpringApplication.run(ScheduleTelegramBotApplication.class, args);

		ScheduleParser scheduleParser = new ScheduleParser();
		//scheduleParser.parseGroups();
		//System.out.println(scheduleParser.parseSchedule("АВТ-809", Day.THURSDAY));
		//System.out.println(scheduleParser.weekSchedule("АВТ-809"));

		BotInitializer botInitializer = new BotInitializer();
		ScheduleTelegramBot bot = botInitializer.getBot();
	}
}