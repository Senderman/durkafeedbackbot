package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class MainMenuCallback implements CallbackExecutor {

    private final DialogStateService dialogStateService;
    private final MainMenu mainMenu;

    public MainMenuCallback(DialogStateService dialogStateService, MainMenu mainMenu) {
        this.dialogStateService = dialogStateService;
        this.mainMenu = mainMenu;
    }

    @Override
    public String command() {
        return "c_main_menu";
    }

    @Override
    public void accept(@NotNull CallbackQueryContext context) {
        dialogStateService.remove(context.user().getId());
        context.editMessage("Главное меню", mainMenu.mainMenuMarkup()).callAsync(context.sender);
    }
}
