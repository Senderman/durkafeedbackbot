package com.senderman.durkafeedbackbot.service;

import com.senderman.durkafeedbackbot.util.callback.ButtonBuilder;
import com.senderman.durkafeedbackbot.util.callback.MarkupBuilder;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

@Singleton
public class MainMenu {

    private final String durkaLink;

    public MainMenu(@Value("${durkaLink}") String durkaLink) {
        this.durkaLink = durkaLink;
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
                .addButton(ButtonBuilder.callbackButton()
                        .payload("c_question")
                        .text("Задать вопрос"))
                .newRow()
                .addButton(ButtonBuilder.urlButton()
                        .payload(durkaLink)
                        .text("Чат канала"))
                .newRow()
                .addButton(ButtonBuilder.callbackButton()
                        .payload("c_topic_request")
                        .text("Предложить тему для будущих постов"))
                .newRow()
                .addButton(ButtonBuilder.callbackButton()
                        .payload("c_true_story")
                        .text("Рассказать свою историю"))
                .build();
    }
}
