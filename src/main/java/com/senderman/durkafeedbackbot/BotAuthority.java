package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.services.CommonAbsSender;
import io.micronaut.context.annotation.Value;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.EnumSet;

@Singleton
public class BotAuthority implements Authority<For> {

    private final long durkaChatId;

    public BotAuthority(@Value("${durkaChatId}") long durkaChatId) {
        this.durkaChatId = durkaChatId;
    }

    @Override
    public boolean hasRights(@NotNull CommonAbsSender sender, @NotNull Update update, @NotNull User user, @NotNull EnumSet<For> fors) {
        if (fors.contains(For.USER))
            return true;
        // All admins live in durka
        return update.getMessage().getChatId().equals(durkaChatId);
    }
}
