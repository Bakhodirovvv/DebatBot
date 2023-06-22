package com.comapany.container;


import com.comapany.bot.MyBot;
import com.comapany.domain.User;
import com.comapany.enums.LanguageStatus;
import com.comapany.enums.UserStatus;

import java.util.HashMap;
import java.util.Map;

public class Container {

    public static String TOKEN = "5909611520:AAGZYs-7FmX1LYWMykKs1N2MB-CgcLP0kTw";
    public static String BotUserNAME = "https://t.me/debat2023birnima_bot";
    public static MyBot MyBot=null;
    public static String ADMIN_CHAT_ID="1339810632";


    public static Map<String, User> userMap = new HashMap<>();

    public static Map<String, LanguageStatus> languageStatusMap = new HashMap<>();

    public static Map<String, UserStatus> userStatusMap = new HashMap<>();

}
