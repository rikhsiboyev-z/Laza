package com.example.laza.telegram.bot;


import com.example.laza.user.UserService;
import com.example.laza.user.dto.UserCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LazaBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String USERNAME_BOT;
    @Value("${telegram.bot.token}")
    private String TOKEN_BOT;
    private State state = State.START;
    private final UserService userService;
    private final UserCreateDto userCreateDto = new UserCreateDto();

    @Override
    public void onUpdateReceived(Update update) {

        String chatId = null;
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            chatId = callbackQuery.getMessage().getChatId().toString();
            if (data.equals("Sign in")) {

                state = State.SIGN_IN;
            }
            if (data.equals("Sign up")) {
                state = State.SIGN_UP;
            }
        }

        switch (state) {
            case START -> {
                if (update.hasMessage()) {
                    Message message = update.getMessage();
                    Long chatId1 = message.getChatId();
                    sendInlineButton(chatId1, getInlineKeyboardMarkup());
                }

            }
            case SIGN_IN -> {
                sendMessage(Long.parseLong(chatId), """ 
                        Sign in qilish : \n
                        name : \n
                        email : \n
                        password :
                         """);
                state = State.NAME;
            }
            case NAME, EMAIL, PASSWORD -> {
                Message message = update.getMessage();
                Long id = message.getChatId();
                String value = message.getText();



                switch (state) {
                    case NAME -> {
                        sendMessage(id, "name tastiqlandi");
                        userCreateDto.setFirstname(value);
                        state = State.EMAIL;
                    }
                    case EMAIL -> {
                        sendMessage(id, "email tastiqlandi");
                        userCreateDto.setEmail(value);
                        state = State.PASSWORD;
                    }
                    case PASSWORD -> {
                        sendMessage(id, "password tastiqlandi \n Royxatan otingiz");
                        userCreateDto.setPassword(value);
                        userService.signUp(userCreateDto);

                    }

                }


            }
            case SIGN_UP -> {
                sendMessage(Long.parseLong(chatId), "Sign up qilish");
            }


        }


    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendInlineButton(Long chatId, InlineKeyboardMarkup inlineKeyboardMarkup) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Assalomu Alaykum!");

        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.getCause();
        }
    }


    private static InlineKeyboardMarkup getInlineKeyboardMarkup() {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        String[] regions = {
                "Sign in",
                "Sign up"
        };

        for (String region : regions) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            InlineKeyboardButton button = new InlineKeyboardButton(region);
            button.setCallbackData(region);
            row.add(button);
            keyboard.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(keyboard);

        return inlineKeyboardMarkup;

    }

    @Override
    public String getBotUsername() {
        return USERNAME_BOT;
    }

    @Override
    public String getBotToken() {
        return TOKEN_BOT;
    }
}
