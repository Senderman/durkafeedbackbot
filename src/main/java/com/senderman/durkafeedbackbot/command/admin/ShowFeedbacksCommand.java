package com.senderman.durkafeedbackbot.command.admin;

import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import com.senderman.durkafeedbackbot.model.Feedback;
import com.senderman.durkafeedbackbot.util.Html;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.senderman.durkafeedbackbot.model.Feedback.*;

@Component
public class ShowFeedbacksCommand implements CommandExecutor {

    private static final String feedbackSeparator = "\n\n<code>====================================</code>\n\n";
    private final FeedbackService feedbackService;

    public ShowFeedbacksCommand(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Override
    public String command() {
        return "/show";
    }

    @Override
    public EnumSet<For> authority() {
        return EnumSet.of(For.ADMIN);
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        var args = optimizeArguments(context.arguments().length != 0 ? context.arguments() : new String[]{"a"});
        context.reply("Собираем фидбеки...").callAsync(context.sender);
        var feedbacks = collectFeedbacks(args);
        if (feedbacks.size() == 0) {
            context.replyToMessage("Фидбеков по данным фильтрам не найдено!").callAsync(context.sender);
            return;
        }
        var text = new StringBuilder("<b>Фидбеки от подписчиков</b>");
        for (var f : feedbacks) {
            String formattedFeedback = formatFeedback(f);
            if (text.length() + feedbackSeparator.length() + formattedFeedback.length() >= 4096) {
                context.reply(text.toString()).callAsync(context.sender);
                text.setLength(0);
            }
            text.append(feedbackSeparator).append(formattedFeedback);
        }
        if (text.length() != 0) {
            context.replyToMessage(text.toString()).callAsync(context.sender);
        }
    }

    private List<String> optimizeArguments(String[] args) {
        Set<String> result = new HashSet<>();
        for (var arg : args) {
            if (arg.equals("a"))
                return List.of("a");

            if (arg.matches("[tsq\\d+]"))
                result.add(arg);
        }
        return result.stream().toList();
    }

    private List<Feedback> collectFeedbacks(List<String> args) {
        if (args.get(0).equals("a")) {
            var dbResult = feedbackService.findAll();
            var result = new ArrayList<Feedback>();
            dbResult.forEach(result::add);
            return result;
        }
        var types = new ArrayList<String>();
        var ids = new ArrayList<Integer>();
        for (var arg : args) {
            switch (arg) {
                case "q" -> types.add(TYPE_QUESTION);
                case "s" -> types.add(TYPE_TRUE_STORY);
                case "t" -> types.add(TYPE_TOPIC_REQUEST);
                default -> ids.add(Integer.parseInt(arg));
            }
        }
        return feedbackService.findByTypeInOrIdIn(types, ids);
    }

    private String formatFeedback(Feedback feedback) {
        var text = feedback.getMessage();
        if (feedback.getType().equals(TYPE_TRUE_STORY))
            text = text.substring(0, Math.min(text.length(), 97)) + "...";
        return """
                <code>#%d</code>
                <b>От:</b> %s (id <code>%d</code>)
                <b>Тип:</b> %s
                <b>Отвечен:</b> %s

                %s"""
                .formatted(
                        feedback.getId(),
                        feedback.getUserName(),
                        feedback.getUserId(),
                        feedback.getType(),
                        feedback.isReplied() ? "✅" : "❌",
                        Html.htmlSafe(text)
                );
    }

}
