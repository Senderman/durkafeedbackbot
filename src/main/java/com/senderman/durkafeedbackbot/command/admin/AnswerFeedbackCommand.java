package com.senderman.durkafeedbackbot.command.admin;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Singleton
public class AnswerFeedbackCommand implements CommandExecutor {

    private final FeedbackService feedbackService;

    public AnswerFeedbackCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String command() {
        return "/fresp";
    }

    @Override
    public EnumSet<For> authority() {
        return EnumSet.of(For.ADMIN);
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        context.setArgumentsLimit(2);
        if (context.argumentsLength() < 2) {
            context.replyToMessage("Неверное количество аргументов!").callAsync(context.sender);
            return;
        }

        int feedbackId;
        try {
            feedbackId = Integer.parseInt(context.argument(0));
        } catch (NumberFormatException e) {
            context.replyToMessage("id фидбека - это число!").callAsync(context.sender);
            return;
        }

        var feedbackOptional = feedbackService.findById(feedbackId);
        if (feedbackOptional.isEmpty()) {
            context.replyToMessage("Фидбека с таким id не существует!").callAsync(context.sender);
            return;
        }
        // feedbackRepo.deleteById(feedbackId);
        var feedback = feedbackOptional.get();
        feedback.setReplied(true);
        feedbackService.update(feedback);

        var answer = context.argument(1);
        Methods.sendMessage()
                .setChatId(feedback.getChatId())
                .setText("\uD83D\uDD14 <b>Ответ админа</b>\n\n" + answer)
                .setReplyToMessageId(feedback.getMessageId())
                .callAsync(context.sender);
        context.replyToMessage("Ответ отправлен!").callAsync(context.sender);
    }
}
