package com.comapany.bot;

import com.comapany.container.Container;
import com.comapany.controller.AdminController;
import com.comapany.controller.UserController;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return Container.BotUserNAME="http://t.me/debat2023birnima_bot";
    }

    @Override
    public String getBotToken() {
        return Container.TOKEN="6079936421:AAGI8Fsa0gxr1WPTaonsfwS_qo-KVlfeGYo";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
           Message message=update.getMessage();
            User user=message.getFrom();
            String chatId = String.valueOf(message.getChatId());
            if (chatId.equals(Container.ADMIN_CHAT_ID)){
                AdminController.handleMessage(message);
            }else {
                UserController.handleMessage(message);
            }

        }
//        else if (update.hasCallbackQuery()) {
//            UserController.handleCallBackQuery(update.getCallbackQuery().getMessage(),
//                    update.getCallbackQuery().getData());
//        }
    }

    public Message sendMsg(Object obj) {
        try {
            if (obj instanceof SendMessage) {
                execute((SendMessage) obj);
            } else if (obj instanceof DeleteMessage) {
                execute((DeleteMessage) obj);
            }else if (obj instanceof SendDocument) {
                execute((SendDocument) obj);
            } else if (obj instanceof SendVideo) {
                execute((SendVideo) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return null;
    }
}
