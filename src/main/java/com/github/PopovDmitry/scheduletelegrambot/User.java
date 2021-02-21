package com.github.PopovDmitry.scheduletelegrambot;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private long chatID;
    private UserState state;
    private String group;
}
