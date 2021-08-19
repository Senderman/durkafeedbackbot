package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.analytics.UpdateHandler;
import com.annimon.tgbotsmodule.api.methods.Methods;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@SpringBootApplication
public class BotHandler extends com.annimon.tgbotsmodule.BotHandler {

    private final UpdateHandler updateHandler;
    private final String token;
    private final String username;
    private final long durkaChannelId;
    private final long durkaChatId;

    public BotHandler(
            @Lazy UpdateHandler updateHandler,
            @Value("${token}") String token,
            @Value("${username}") String username,
            @Value("${durkaChannelId}") long durkaChannelId,
            @Value("${durkaChatId}") long durkaChatId) {
        this.updateHandler = updateHandler;
        this.token = token;
        this.username = username;
        this.durkaChannelId = durkaChannelId;
        this.durkaChatId = durkaChatId;

        addMethodPreprocessor(EditMessageText.class, m -> {
            m.enableHtml(true);
            m.disableWebPagePreview();
        });

        addMethodPreprocessor(SendMessage.class, m -> {
            m.enableHtml(true);
            m.disableWebPagePreview();
        });
    }

    @Override
    protected BotApiMethod<?> onUpdate(@NotNull Update update) {
        if (update.hasMessage()) {
            var message = update.getMessage();
            // do not process old messages
            if (message.getDate() + 120 < System.currentTimeMillis() / 1000)
                return null;

            var chatId = message.getChatId();
            // leave from foreign chats
            if (!message.isUserMessage() && (!chatId.equals(durkaChatId) || !chatId.equals(durkaChannelId))) {
                Methods.leaveChat(chatId).callAsync(this);
                return null;
            }

            // skip non-commands in admin chat
            if (chatId.equals(durkaChatId) && !message.isCommand())
                return null;
        }

        updateHandler.handleUpdate(update);

        return null;
    }

    @Override
    public String getBotUsername() {
        return this.username;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }
}
