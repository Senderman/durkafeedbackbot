package com.senderman.durkafeedbackbot.command.admin;

import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import com.senderman.durkafeedbackbot.model.Feedback;
import com.senderman.durkafeedbackbot.util.callback.ButtonBuilder;
import com.senderman.durkafeedbackbot.util.callback.MarkupBuilder;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Singleton
public class ReadCommand implements CommandExecutor {

    private final FeedbackService feedbackService;

    public ReadCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String command() {
        return "/read";
    }

    @Override
    public EnumSet<For> authority() {
        return EnumSet.of(For.ADMIN);
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        if (context.argumentsLength() == 0) {
            context.replyToMessage("Неверное количество аргументов!").callAsync(context.sender);
            return;
        }
        int id;
        try {
            id = Integer.parseInt(context.argument(0));
        } catch (NumberFormatException e) {
            context.replyToMessage("Id фидбека - это число!");
            return;
        }
        var feedbackOptional = feedbackService.findById(id);
        if (feedbackOptional.isEmpty()) {
            context.replyToMessage("Фидбека с этим id не существует!");
            return;
        }
        var f = feedbackOptional.get();
        var text = """
                <b>ID</b>: %d
                <b>Автор:</b> %s
                <b>Тип:</b> %s
                <b>Отвечен:</b> %s
                
                %s
                """.formatted(id, f.getUserName(), f.getType(), f.isReplied() ? "✅" : "❌", f.getMessage());
        var m = context.replyToMessage(text);
        if (f.getType().equals(Feedback.TYPE_TRUE_STORY)) {
            m.setReplyMarkup(new MarkupBuilder().addButton(ButtonBuilder.callbackButton("Опубликовать")
                            .payload("c_publish " + id)
                    )
                    .build());
        }
        m.callAsync(context.sender);
    }
}
