package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.commands.CommandRegistry;
import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.senderman.durkafeedbackbot.callback.CallbackExecutor;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.command.user.AnyTextCommand;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;

import java.util.Set;

@Singleton
public class CommandUpdateHandler extends CommandRegistry<For> {

    public CommandUpdateHandler(
            @Value("${username}") String username,
            Authority<For> authority,
            Set<CommandExecutor> commands,
            Set<CallbackExecutor> callbacks,
            AnyTextCommand anyTextCommand
    ) {
        super(username, authority);
        splitCallbackCommandByWhitespace();
        commands.forEach(this::register);
        callbacks.forEach(this::register);
        register(anyTextCommand);
    }

}
