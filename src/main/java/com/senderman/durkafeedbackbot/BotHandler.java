package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.BotModuleOptions;
import com.annimon.tgbotsmodule.analytics.UpdateHandler;
import com.annimon.tgbotsmodule.api.methods.Methods;
import com.senderman.durkafeedbackbot.config.BotConfig;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

@Singleton
public class BotHandler extends com.annimon.tgbotsmodule.BotHandler {

    private final BotConfig config;
    private final UpdateHandler updateHandler;


    public BotHandler(
            BotConfig config,
            UpdateHandler updateHandler,
            BotModuleOptions botOptions
    ) {
        super(botOptions);
        this.config = config;
        this.updateHandler = updateHandler;

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
            if (!message.isUserMessage() && (!chatId.equals(config.durkaChatId()) && !chatId.equals(config.durkaChannelId()))) {
                Methods.leaveChat(chatId).callAsync(this);
                return null;
            }

            // skip non-commands in admin chat
            if (chatId.equals(config.durkaChatId()) && !message.isCommand())
                return null;
        }

        updateHandler.handleUpdate(this, update);

        return null;
    }
}
