package com.github.PopovDmitry.scheduletelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.IOException;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class ScheduleTelegramBotApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ScheduleTelegramBotApplication.class, args);

		ScheduleParser scheduleParser = new ScheduleParser();

	}

}