package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.commands.context.CallbackQueryContext;
import com.senderman.durkafeedbackbot.service.StoryPublisherService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class PublishCallback implements CallbackExecutor {

    private final StoryPublisherService storyPublisherService;

    public PublishCallback(StoryPublisherService storyPublisherService) {
        this.storyPublisherService = storyPublisherService;
    }

    @Override
    public String command() {
        return "c_publish";
    }

    @Override
    public void accept(@NotNull CallbackQueryContext context) {
        int feedbackId = Integer.parseInt(context.argument(0));
        try {
            storyPublisherService.publish(feedbackId, context.message().getChatId(), context.sender);
            Methods.sendMessage(context.message().getChatId(), "✅ История #" + feedbackId + " опубликована!")
                    .callAsync(context.sender);
            context.answerAsAlert("✅ История #" + feedbackId + " опубликована!")
                    .callAsync(context.sender);
        } catch (StoryPublisherService.StoryNotFoundException e) {
            context.answerAsAlert("История #" + feedbackId + " удалена либо уже опубликована!").callAsync(context.sender);
        } catch (StoryPublisherService.NotAStoryException e) {
            context.answerAsAlert("Фидбек #" + feedbackId + " - не история!").callAsync(context.sender);
        }
    }

}
