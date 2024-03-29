package com.senderman.durkafeedbackbot.command.admin;

import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.MessageContext;
import com.senderman.durkafeedbackbot.command.CommandExecutor;
import com.senderman.durkafeedbackbot.service.StoryPublisherService;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

@Singleton
public class PublishCommand implements CommandExecutor {

    private final StoryPublisherService storyPublisherService;

    public PublishCommand(StoryPublisherService storyPublisherService) {
        this.storyPublisherService = storyPublisherService;
    }

    @Override
    public String command() {
        return "/publish";
    }

    @Override
    public EnumSet<For> authority() {
        return EnumSet.of(For.ADMIN);
    }

    @Override
    public void accept(@NotNull MessageContext context) {
        int feedbackId = Integer.parseInt(context.argument(0));
        try {
            storyPublisherService.publish(feedbackId, context.message().getChatId(), context.sender);
        } catch (StoryPublisherService.StoryNotFoundException e) {
            context.replyToMessage("История #" + feedbackId + " удалена либо уже опубликована!")
                    .callAsync(context.sender);
        } catch (StoryPublisherService.NotAStoryException e) {
            context.replyToMessage("Фидбек #" + feedbackId + " - не история!")
                    .callAsync(context.sender);
        }
    }
}
