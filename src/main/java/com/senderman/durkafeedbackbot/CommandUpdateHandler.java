package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.senderman.durkafeedbackbot.callback.CallbackExecutor;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.command.user.AnyTextCommand;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CommandUpdateHandler extends CommandRegistry<For> {

    public CommandUpdateHandler(
            BotHandler handler,
            Authority<For> authority,
            Set<CommandExecutor> commands,
            Set<CallbackExecutor> callbacks,
            AnyTextCommand anyTextCommand
    ) {
        super(handler, authority);
        splitCallbackCommandByWhitespace();
        commands.forEach(this::register);
        callbacks.forEach(this::register);
        register(anyTextCommand);
    }

}
