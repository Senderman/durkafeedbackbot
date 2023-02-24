package com.senderman.durkafeedbackbot.command.admin;

import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Singleton
public class DeleteFeedbackCommand implements CommandExecutor {

    private final FeedbackService feedbackService;

    public DeleteFeedbackCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String command() {
        return "/fdel";
    }

    @Override
    public EnumSet<For> authority() {
        return EnumSet.of(For.ADMIN);
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        if (context.argumentsLength() < 1) {
            context.replyToMessage("Неверное количество аргументов!").callAsync(context.sender);
            return;
        }
        var arg = context.argument(0);
        if (arg.matches("\\d+")) {
            deleteSingleFeedback(context, Integer.parseInt(arg));
        } else if (arg.matches("\\d+-\\d+")) {
            var args = arg.split("-");
            int from = Integer.parseInt(args[0]);
            int to = Integer.parseInt(args[1]);
            deleteFeedbackInRange(context, from, to);
        } else {
            context.replyToMessage("id фидбека - это число либо интервал!").callAsync(context.sender);
        }
    }

    private void deleteSingleFeedback(MessageContext ctx, int feedbackId) {
        if (!feedbackService.existsById(feedbackId)) {
            notifyNoFeedbacksFound(ctx);
            return;
        }

        feedbackService.deleteById(feedbackId);
        notifySuccess(ctx, "Фидбек #" + feedbackId + " удален");
    }

    private void deleteFeedbackInRange(MessageContext ctx, int from, int to) {
        long result = feedbackService.deleteByIdBetween(from, to);
        if (result == 0) {
            notifyNoFeedbacksFound(ctx);
            return;
        }
        notifySuccess(ctx, "Удалено " + result + " фидбеков (с " + from + " по " + to + ")");
    }

    private void notifySuccess(MessageContext ctx, String text) {
        ctx.replyToMessage(text).callAsync(ctx.sender);
    }

    private void notifyNoFeedbacksFound(MessageContext ctx) {
        ctx.replyToMessage("Ни одного фидбека не найдено").callAsync(ctx.sender);
    }
}
