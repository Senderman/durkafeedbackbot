package com.senderman.durkafeedbackbot.command.user;

import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements CommandExecutor {

    private final DialogStateService dialogStateService;
    private final MainMenu mainMenu;

    public StartCommand(DialogStateService dialogStateService, MainMenu mainMenu) {
        this.dialogStateService = dialogStateService;
        this.mainMenu = mainMenu;
    }

    @Override
    public String command() {
        return "/start";
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        dialogStateService.remove(context.user().getId());
        context.reply("Главное меню")
                .setReplyMarkup(mainMenu.mainMenuMarkup())
                .callAsync(context.sender);
    }
}
