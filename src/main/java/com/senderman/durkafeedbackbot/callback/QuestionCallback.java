package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import com.senderman.durkafeedbackbot.util.DialogState;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class QuestionCallback implements CallbackExecutor {

    private final DialogStateService dialogStateService;
    private final MainMenu mainMenu;

    public QuestionCallback(DialogStateService dialogStateService, MainMenu mainMenu) {
        this.dialogStateService = dialogStateService;
        this.mainMenu = mainMenu;
    }

    @Override
    public String command() {
        return "c_question";
    }

    @Override
    public void accept(@NotNull CallbackQueryContext context) {
        dialogStateService.insert(new DialogState(context.user().getId(), DialogState.Stage.QUESTION));
        context.editMessage(
                        "Введите свой вопрос." +
                        "При необходимости, укажите ник админа, которому хотите задать вопрос",
                        mainMenu.goToMainMenuMarkup())
                .callAsync(context.sender);

    }
}
