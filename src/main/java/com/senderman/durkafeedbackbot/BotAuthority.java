package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.commands.authority.Authority;
import com.annimon.tgbotsmodule.commands.authority.For;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.EnumSet;

@Component
public class BotAuthority implements Authority<For> {

    private final long durkaChatId;

    public BotAuthority(@Value("${durkaChatId}") long durkaChatId) {
        this.durkaChatId = durkaChatId;
    }

    @Override
    public boolean hasRights(Update update, User user, EnumSet<For> fors) {
        if (fors.contains(For.USER))
            return true;

        // All admins live in durka
        return update.getMessage().getChatId().equals(durkaChatId);
    }
}
