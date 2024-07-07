package com.senderman.durkafeedbackbot.command.user;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

@Singleton
public class PrivacyPolicyCommand implements CommandExecutor {

    private final String privacyPolicyLink;

    public PrivacyPolicyCommand(@Value("${privacyPolicyLink}") String privacyPolicyLink) {
        this.privacyPolicyLink = privacyPolicyLink;
    }

    @Override
    public String command() {
        return "/privacy";
    }

    @Override
    public void accept(@NotNull MessageContext ctx) {
        ctx.replyToMessage(privacyPolicyLink).callAsync(ctx.sender);
    }
}
