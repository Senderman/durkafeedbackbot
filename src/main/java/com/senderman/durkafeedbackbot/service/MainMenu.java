package com.senderman.durkafeedbackbot.service;

import com.senderman.durkafeedbackbot.config.BotConfig;
import com.senderman.durkafeedbackbot.util.callback.ButtonBuilder;
import com.senderman.durkafeedbackbot.util.callback.MarkupBuilder;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Singleton
public class MainMenu {

    private final String durkaLink;

    public MainMenu(BotConfig config) {
        this.durkaLink = config.durkaLink();
    }

    public InlineKeyboardButton goToMainMenuButton() {
        return InlineKeyboardButton.builder()
                .callbackData("c_main_menu")
                .text("Главное меню")
                .build();
    }

    public InlineKeyboardMarkup goToMainMenuMarkup() {
        return new MarkupBuilder()
                .addButton(goToMainMenuButton())
                .build();
    }

    public InlineKeyboardMarkup mainMenuMarkup() {
        return new MarkupBuilder()
                .addButton(ButtonBuilder.callbackButton("Задать вопрос")
                        .payload("c_question")
                )
                .newRow()
                .addButton(ButtonBuilder.urlButton("Чат канала")
                        .payload(durkaLink)
                )
                .newRow()
                .addButton(ButtonBuilder.callbackButton("Предложить тему для будущих постов")
                        .payload("c_topic_request")
                )
                .newRow()
                .addButton(ButtonBuilder.callbackButton("Рассказать свою историю")
                        .payload("c_true_story")
                )
                .build();
    }
}
