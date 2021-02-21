package com.github.PopovDmitry.scheduletelegrambot;

import lombok.Setter;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class ScheduleTelegramBot extends TelegramWebhookBot {

    @Setter
    private String botToken;

    @Setter
    private String botUsername;

    @Setter
    private String webHookPath;

    private final ScheduleParser scheduleParser = new ScheduleParser();
    private Hashtable<Long, User> users = new Hashtable<>();


    public ScheduleTelegramBot() {}

    @Override
    public String getBotUsername() { return botUsername; }

    @Override
    public String getBotToken() { return botToken; }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {

        String inputText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        System.out.println(inputText);

        if(inputText.startsWith("/start") || !users.containsKey(chatId)) {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Hello, enter group");
            User user = new User(chatId, UserState.ENTER_GROUP, null);
            users.put(chatId, user);

            /*
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow keyboardRow = new KeyboardRow();
            KeyboardButton keyboardButton = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            KeyboardButton keyboardButton3 = new KeyboardButton();

            keyboardButton.setText("1");
            keyboardButton2.setText("2");
            keyboardButton3.setText("3");

            keyboardRow.add(keyboardButton);
            keyboardRow.add(keyboardButton2);
            keyboardRow.add(keyboardButton3);

            keyboardRowList.add(keyboardRow);

            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            message.setReplyMarkup(replyKeyboardMarkup);

             */

            try {
                execute(message);
            }
            catch (TelegramApiException exception) {
                exception.printStackTrace();
            }
        }
        else if(users.get(chatId).getState() == UserState.ENTER_GROUP) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            if(scheduleParser.getGroups().containsKey(inputText)) {
                sendMessage.setText("Group found");
                users.get(chatId).setGroup(inputText);

                ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                replyKeyboardMarkup.setResizeKeyboard(true);
                replyKeyboardMarkup.setSelective(true);
                List<KeyboardRow> keyboardRowList = new ArrayList<>();
                KeyboardRow keyboardRow1 = new KeyboardRow();
                KeyboardRow keyboardRow2 = new KeyboardRow();
                KeyboardRow keyboardRow3 = new KeyboardRow();
                KeyboardButton keyboardButton1 = new KeyboardButton();
                KeyboardButton keyboardButton2 = new KeyboardButton();
                KeyboardButton keyboardButton3 = new KeyboardButton();
                KeyboardButton keyboardButton4 = new KeyboardButton();
                KeyboardButton keyboardButton5 = new KeyboardButton();
                KeyboardButton keyboardButton6 = new KeyboardButton();
                KeyboardButton keyboardButton7 = new KeyboardButton();

                keyboardButton1.setText(Day.ПОНЕДЕЛЬНИК.toString());
                keyboardButton2.setText(Day.ВТОРНИК.toString());
                keyboardButton3.setText(Day.СРЕДА.toString());
                keyboardButton4.setText(Day.ЧЕТВЕРГ.toString());
                keyboardButton5.setText(Day.ПЯТНИЦА.toString());
                keyboardButton6.setText(Day.СУББОТА.toString());
                keyboardButton7.setText("Неделя");

                keyboardRow1.add(keyboardButton1);
                keyboardRow1.add(keyboardButton2);
                keyboardRow1.add(keyboardButton3);
                keyboardRow2.add(keyboardButton4);
                keyboardRow2.add(keyboardButton5);
                keyboardRow2.add(keyboardButton6);
                keyboardRow3.add(keyboardButton7);

                keyboardRowList.add(keyboardRow1);
                keyboardRowList.add(keyboardRow2);
                keyboardRowList.add(keyboardRow3);

                replyKeyboardMarkup.setKeyboard(keyboardRowList);
                sendMessage.setReplyMarkup(replyKeyboardMarkup);


                users.get(chatId).setState(UserState.LOGGED_IN);
            }
            else {
                sendMessage.setText("Group not found");
            }

            try {
                execute(sendMessage);
            }
            catch (TelegramApiException exception) {
                exception.printStackTrace();
            }

        }
        else if(inputText.equals(Day.ПОНЕДЕЛЬНИК.toString()) ||
                inputText.equals(Day.ВТОРНИК.toString()) ||
                inputText.equals(Day.СРЕДА.toString()) ||
                inputText.equals(Day.ЧЕТВЕРГ.toString()) ||
                inputText.equals(Day.ПЯТНИЦА.toString()) ||
                inputText.equals(Day.СУББОТА.toString())) {
            users.get(chatId).setState(UserState.SHOW_SCHEDULE);
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(String.valueOf(chatId));
            try {
                sendMessage.setText(scheduleParser.parseSchedule(users.get(chatId).getGroup(), Day.valueOf(inputText)));
                execute(sendMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    public String getBotPath() { return webHookPath; }
}
