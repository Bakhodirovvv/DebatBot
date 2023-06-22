package com.comapany.controller;

import com.comapany.container.Container;

import com.comapany.domain.User;
import com.comapany.enums.LanguageStatus;
import com.comapany.enums.UserStatus;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Video;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.ArrayList;
import java.util.List;

public class UserController {
    public static void handleMessage(Message message) {
        System.out.println(message.getChatId());
        if (message.hasText()) {
            handleText(message, message.getText());
        }
        if (message.hasVideo()) {
            handleVideo(message, message.getVideo());
        }
    }


    private static void handleText(Message message, String text) {
        String chatId = String.valueOf(message.getChatId());
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        //start
        if (text.equals("/start")) {
            Container.userStatusMap.put(chatId, UserStatus.SEND_FIO);
            Container.userMap.put(chatId, new User());

            sendMessage.setText("""
                    Murojaatingizni yo'llash uchun tilni tanlang.👇\n----------------------------------------------
                    Выберите язык для использования 👇
                    """);
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setSelective(true);
            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardButton keyboardButton1 = new KeyboardButton();
            KeyboardButton keyboardButton2 = new KeyboardButton();
            KeyboardRow keyboardRow1 = new KeyboardRow();
            keyboardButton1.setText("O`zbek tili");
            keyboardButton2.setText("Rus tili");
            keyboardRow1.add(keyboardButton1);
            keyboardRow1.add(keyboardButton2);
            keyboardRowList.add(keyboardRow1);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            Container.MyBot.sendMsg(sendMessage);
        } //uzbek tili uchun
        else if (text.equals("O`zbek tili")) {
            Container.languageStatusMap.put(chatId, LanguageStatus.LANGUAGE_UZ);
            sendMessage.setText("\uD83D\uDDF3 Ism, sharifingiz, yashash manzilingiz va bog'lanish uchun telefon raqamingizni qoldiring!");
            Container.MyBot.sendMsg(sendMessage);
        } //rus tili uchun
        else if (text.equals("Rus tili")) {
            Container.languageStatusMap.put(chatId, LanguageStatus.LANGUAGE_RU);
            sendMessage.setText("\uD83D\uDDF3 Оставьте  свое имя, фамилию, адрес проживания и номер телефона для связи!");
            Container.MyBot.sendMsg(sendMessage);
        } else if (text.equals("Matn") || text.equals("Текст")) {
            UserStatus userStatus = Container.userStatusMap.get(chatId);
            User user = Container.userMap.get(chatId);
            if (Container.userStatusMap.containsKey(chatId)) {
                if (userStatus.equals(UserStatus.SEND_TEXT_VIDEO)) {
                    LanguageStatus languageStatus = Container.languageStatusMap.get(chatId);
                    user.setDescription(text);

                    Container.userStatusMap.put(chatId, UserStatus.FINALLY);
                    if (languageStatus.equals(LanguageStatus.LANGUAGE_UZ)) {
                        sendMessage.setText(
                                """
                                                📝Sizning savolingiz aniq, lo‘nda, tushunarli bo‘lishi hamda matn mazmunida fikringiz to‘liq o‘z aksini topishi lozim.
                                                                 
                                        🎥Murojaatingizni @jurnalist0202 ga yuborishingiz mumkin!
                                        """);
                        Container.MyBot.sendMsg(sendMessage);
                    } else if (languageStatus.equals(LanguageStatus.LANGUAGE_RU)) {
                        sendMessage.setText(
                                """
                                                📝Ваш вопрос должен быть ясным, лаконичным и понятным, полностью отражать ваше мнение в содержании текста.
                                                                                
                                        🎥Вы можете направить свои обращения на @journalist0202!
                                        """);
                        Container.MyBot.sendMsg(sendMessage);
                    }
                }
            }



        } else if (text.equals("Video") || text.equals("Видео")) {
            if (Container.userStatusMap.containsKey(chatId)) {
                UserStatus userStatus = Container.userStatusMap.get(chatId);
                if (userStatus.equals(UserStatus.SEND_TEXT_VIDEO)) {
                    LanguageStatus languageStatus = Container.languageStatusMap.get(chatId);
                    Container.userStatusMap.put(chatId, UserStatus.FINALLY);
                    if (languageStatus.equals(LanguageStatus.LANGUAGE_UZ)) {
                        sendMessage.setText(
                                """
                                                👨🏻‍💼⁉️Qaysi partiya vakiliga murojaat yo'llamoqchisiz?\s
                                                                                
                                        Videomurojaat davomiyligi 1 daqiqadan ko’p bo’lmasligi hamda sifati 720/1024 full hd variantda bo’lishi lozim.
                                                                                
                                        🎥Videomurojaatni @jurnalist0202 ga yuborishingiz mumkin!
                                                                                
                                        """);
                        Container.MyBot.sendMsg(sendMessage);
                    } else if (languageStatus.equals(LanguageStatus.LANGUAGE_RU)) {
                        sendMessage.setText(
                                """
                                                👨🏻‍💼⁉️К представителю какой партии Вы хотите обратиться?
                                                                                
                                        🎥Продолжительность видеообращения не должна превышать 1 минуты, а качество должно быть в варианте 720/1024 Full HD.
                                                                                
                                        🎥Вы можете направить свои видеообращения на @journalist0202!
                                        """);
                        Container.MyBot.sendMsg(sendMessage);
                    }
                }
            }


        } else {
             if (Container.userStatusMap.containsKey(chatId)) {
                UserStatus userStatus = Container.userStatusMap.get(chatId);
                if (userStatus.equals(UserStatus.FINALLY)){
                    LanguageStatus languageStatus = Container.languageStatusMap.get(chatId);
                    if (languageStatus.equals(LanguageStatus.LANGUAGE_UZ)) {
                        sendMessage.setText("Murojaatingiz qabul qilindi. Fikringiz biz uchun muhim.\n \n " +
                                "Saylovda ovoz bering, kelajakka befarq bo‘lmang!");
                        Container.MyBot.sendMsg(sendMessage);
                    } else if (languageStatus.equals(LanguageStatus.LANGUAGE_RU)) {
                        sendMessage.setText("Ваше обращение  принято. Мнение каждого гражданина  важно для нас.\n \n" +
                                "Голосуйте на выборах, не будьте равнодушны к будущему!");
                        Container.MyBot.sendMsg(sendMessage);
                    }
                    User user = Container.userMap.get(chatId);
                    sendMessage.setText("Murojat :  " +
                            text + " " +
                            "\n FIO : " +
                            user.getFIO());
                    sendMessage.setChatId(Container.ADMIN_CHAT_ID);
                    Container.MyBot.sendMsg(sendMessage);
                }

               else if (userStatus.equals(UserStatus.SEND_FIO)) {
                    LanguageStatus languageStatus = Container.languageStatusMap.get(chatId);
                    User user = Container.userMap.get(chatId);
                    user.setFIO(text);
                    Container.userStatusMap.put(chatId, UserStatus.SEND_TEXT_VIDEO);
                    if (languageStatus.equals(LanguageStatus.LANGUAGE_UZ)) {
                        sendMessage.setText("Xabaringiz bor, 9-iyul kuni O'zbekiston Respublikasi Prezidenti saylovi bo'lib o'tadi. Shu sababli 24-iyun kuni siyosiy partiyalar o'rtasida debat tashkil etilmoqda. \n \n" +
                                "Prezidentlikka nomzodlarning saylovoldi dasturlari yuzasidan savol/murojaatingizni ushbu bot orqali matn yoki video ko‘rinishida yo‘llashingiz mumkin!\n \n" +
                                "\n \n" +
                                "Murojaat shaklini tanlash uchun “tanlov” tugmasini bosing!");
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);
                        List<KeyboardRow> keyboardRowList = new ArrayList<>();
                        KeyboardButton keyboardButton1 = new KeyboardButton();
                        KeyboardButton keyboardButton2 = new KeyboardButton();
                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        keyboardButton1.setText("Matn");
                        keyboardButton2.setText("Video");
                        keyboardRow1.add(keyboardButton1);
                        keyboardRow1.add(keyboardButton2);
                        keyboardRowList.add(keyboardRow1);
                        replyKeyboardMarkup.setKeyboard(keyboardRowList);
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        Container.MyBot.sendMsg(sendMessage);
                    } else if (languageStatus.equals(LanguageStatus.LANGUAGE_RU)) {
                        sendMessage.setText("9 июля состоятся выборы Президента Республики Узбекистан. По этой причине 24 июня проходят дебаты между политическими партиями. \n \n" +
                                "Свой вопрос/обращение по предвыборным программам кандидатов в президенты вы можете направить через этот бот в виде текста или видео!\n \n" +
                                "\n \n " +
                                "Нажмите кнопку “Выбрать”, чтобы выбрать форму заявки!");
                        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
                        replyKeyboardMarkup.setResizeKeyboard(true);
                        replyKeyboardMarkup.setSelective(true);
                        List<KeyboardRow> keyboardRowList = new ArrayList<>();
                        KeyboardButton keyboardButton1 = new KeyboardButton();
                        KeyboardButton keyboardButton2 = new KeyboardButton();
                        KeyboardRow keyboardRow1 = new KeyboardRow();
                        keyboardButton1.setText("Текст");
                        keyboardButton2.setText("Видео");
                        keyboardRow1.add(keyboardButton1);
                        keyboardRow1.add(keyboardButton2);
                        keyboardRowList.add(keyboardRow1);
                        replyKeyboardMarkup.setKeyboard(keyboardRowList);
                        sendMessage.setReplyMarkup(replyKeyboardMarkup);
                        Container.MyBot.sendMsg(sendMessage);
                    }

                }
            }

        }

    }

    private static void handleVideo(Message message, Video video) {
        if (message.getVideo() != null) {
            User user = Container.userMap.get(String.valueOf(message.getChatId()));
            String chatId = String.valueOf(message.getChatId());
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            SendVideo sendVideo = new SendVideo();
            sendVideo.setChatId(Container.ADMIN_CHAT_ID);
            sendVideo.setVideo(new InputFile(video.getFileId()));
            sendVideo.setCaption(user.getFIO());
            Container.MyBot.sendMsg(sendVideo);
            if (Container.userStatusMap.containsKey(chatId)) {
                UserStatus userStatus = Container.userStatusMap.get(chatId);
                if (userStatus.equals(UserStatus.FINALLY)) {
                    LanguageStatus languageStatus = Container.languageStatusMap.get(chatId);
                    if (languageStatus.equals(LanguageStatus.LANGUAGE_UZ)) {
                        sendMessage.setText("Murojaatingiz qabul qilindi. Fikringiz biz uchun muhim.\n \n " +
                                "Saylovda ovoz bering, kelajakka befarq bo‘lmang!");
                        Container.MyBot.sendMsg(sendMessage);
                    } else if (languageStatus.equals(LanguageStatus.LANGUAGE_RU)) {
                        sendMessage.setText("Ваше обращение  принято. Мнение каждого гражданина  важно для нас.\n \n" +
                                "Голосуйте на выборах, не будьте равнодушны к будущему!");
                        Container.MyBot.sendMsg(sendMessage);
                    }
                }
            }
        }
    }

}
