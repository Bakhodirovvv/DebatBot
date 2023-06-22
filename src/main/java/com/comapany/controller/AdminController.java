package com.comapany.controller;

import com.comapany.container.Container;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Video;


public class AdminController {
    public static void handleMessage(Message message) {
        System.out.println(message.getChatId());
        if (message.hasText()) {
            handleText(message, message.getText());
        } else if (message.hasVideo()) {
           handleVideo(message,message.getVideo());

        }
    }



    private static void handleText(Message message, String text) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        if (text.equals("/start")) {
            sendMessage.setText("Salom admin xush kelibsiz");
            sendMessage.setChatId(chatId);
            Container.MyBot.sendMsg(sendMessage);
        }
    }
    private static void handleVideo(Message message, Video video) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("text");
        Container.MyBot.sendMsg(sendMessage);
        message.setReplyToMessage(message);

    }

}
