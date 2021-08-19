package com.senderman.durkafeedbackbot.service;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.services.CommonAbsSender;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StoryPublisherService {

    private final FeedbackService feedbackService;
    private final long durkaChannelId;

    public StoryPublisherService(FeedbackService feedbackService, @Value("${durkaChannelId}") long durkaChannelId) {
        this.feedbackService = feedbackService;
        this.durkaChannelId = durkaChannelId;
    }

    public void publish(int id, long issueChatId, CommonAbsSender sender) throws StoryNotFoundException {
        var feedbackOptional = feedbackService.findById(id);
        if (feedbackOptional.isEmpty())
            throw new StoryNotFoundException("Not found feedback with id " + id);

        var feedback = feedbackOptional.get();
        var text = "<b>История от подписчика</b>\n\n" + feedback.getMessage();
        feedbackService.deleteById(id);

        Methods.sendMessage(durkaChannelId, text).callAsync(sender);
        Methods.sendMessage(issueChatId, "✅ История #" + id + " опубликована!").callAsync(sender);
        Methods.sendMessage(feedback.getChatId(), "✅ Ваша история опубликована!")
                .setReplyToMessageId(feedback.getMessageId())
                .callAsync(sender);
    }

    public static class StoryNotFoundException extends Exception {
        public StoryNotFoundException(String message) {
            super(message);
        }
    }
}
