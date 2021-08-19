package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import com.senderman.durkafeedbackbot.util.DialogState;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class TopicRequestCallback implements CallbackExecutor {

    private final DialogStateService dialogStateService;
    private final MainMenu mainMenu;

    public TopicRequestCallback(DialogStateService dialogStateService, MainMenu mainMenu) {
        this.dialogStateService = dialogStateService;
        this.mainMenu = mainMenu;
    }

    @Override
    public String command() {
        return "c_topic_request";
    }

    @Override
    public void accept(@NotNull CallbackQueryContext context) {
        dialogStateService.insert(new DialogState(context.user().getId(), DialogState.Stage.TOPIC_REQUEST));
        context.editMessage(
                        "Опишите тему, которую вы хотели бы увидеть среди постов на нашем канале." +
                        "(Одним сообщением без изображений)",
                        mainMenu.goToMainMenuMarkup())
                .callAsync(context.sender);
    }

}
