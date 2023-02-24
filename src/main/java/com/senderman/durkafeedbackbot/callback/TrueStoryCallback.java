package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import com.senderman.durkafeedbackbot.util.DialogState;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class TrueStoryCallback implements CallbackExecutor {

    private final DialogStateService dialogStateService;
    private final MainMenu mainMenu;

    public TrueStoryCallback(DialogStateService dialogStateService, MainMenu mainMenu) {
        this.dialogStateService = dialogStateService;
        this.mainMenu = mainMenu;
    }

    @Override
    public String command() {
        return "c_true_story";
    }

    @Override
    public void accept(@NotNull CallbackQueryContext context) {
        dialogStateService.insert(new DialogState(context.user().getId(), DialogState.Stage.TRUE_STORY));
        context.editMessage(
                        "Расскажите свою историю, прикрепите изображение/файл, если требуется. \n" +
                        "Напоминаем: ваши сообщения будут публиковаться с сохранением авторских ошибок." +
                        "Если по какой-то причине мы не будем публиковать вашу историю - мы вам сообщим.",
                        mainMenu.goToMainMenuMarkup())
                .callAsync(context.sender);
    }

}
