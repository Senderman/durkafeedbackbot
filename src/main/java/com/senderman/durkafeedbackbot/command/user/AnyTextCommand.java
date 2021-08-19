package com.senderman.durkafeedbackbot.command.user;

import com.annimon.tgbotsmodule.api.methods.Methods;
import com.annimon.tgbotsmodule.commands.RegexCommand;
import com.annimon.tgbotsmodule.commands.authority.For;
import com.annimon.tgbotsmodule.commands.context.RegexMessageContext;
import com.senderman.durkafeedbackbot.dbservice.FeedbackService;
import com.senderman.durkafeedbackbot.model.Feedback;
import com.senderman.durkafeedbackbot.service.DialogStateService;
import com.senderman.durkafeedbackbot.service.MainMenu;
import com.senderman.durkafeedbackbot.util.Html;
import com.senderman.durkafeedbackbot.util.callback.ButtonBuilder;
import com.senderman.durkafeedbackbot.util.callback.MarkupBuilder;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
import java.util.regex.Pattern;

@Component
public class AnyTextCommand implements RegexCommand {

    private final MainMenu mainMenu;
    private final FeedbackService feedbackService;
    private final DialogStateService dialogStateService;
    private final long durkaChatId;

    public AnyTextCommand(
            MainMenu mainMenu,
            FeedbackService feedbackService,
            DialogStateService dialogStateService,
            @Value("${durkaChatId}") long durkaChatId
    ) {
        this.mainMenu = mainMenu;
        this.feedbackService = feedbackService;
        this.dialogStateService = dialogStateService;
        this.durkaChatId = durkaChatId;
    }

    @Override
    public Pattern pattern() {
        return Pattern.compile(".*");
    }

    @Override
    public EnumSet<For> authority() {
        return For.all();
    }

    @Override
    public void accept(@NotNull RegexMessageContext context) {
        var user = context.user();
        var stage = dialogStateService.get(user.getId()).getStage();
        String feedbackType;
        switch (stage) {
            case TOPIC_REQUEST -> feedbackType = Feedback.TYPE_TOPIC_REQUEST;
            case TRUE_STORY -> feedbackType = Feedback.TYPE_TRUE_STORY;
            case QUESTION -> feedbackType = Feedback.TYPE_QUESTION;
            case EMPTY -> {
                context.reply("Главное меню")
                        .setReplyMarkup(mainMenu.mainMenuMarkup())
                        .callAsync(context.sender);
                return;
            }
            default -> feedbackType = "NO TYPE";
        }
        var message = context.message();
        String feedbackText = Html.htmlSafe(message.getText());
        if (feedbackText.length() > 2000) {
            context.replyToMessage("Максимальная длина текста - 200 символов!").callAsync(context.sender);
            return;
        }

        var feedback = new Feedback(
                feedbackText,
                feedbackType,
                user.getId(),
                user.getFirstName(),
                message.getChatId(),
                message.getMessageId());
        feedback = feedbackService.insert(feedback);
        dialogStateService.remove(user.getId());

        var feedbackId = feedback.getId();
        var userLink = Html.getUserLink(user);
        var textForAdmins = """
                🔔 <b>Фидбек #%d</b>
                                
                Тип: <b>%s</b>
                От: %s
                                
                %s
                                
                Для ответа, введите /fresp %d &lt;ваш ответ&gt;
                Для удаления - /fdel %d
                """
                .formatted(feedbackId, feedbackType, userLink, feedbackText, feedbackId, feedbackId);
        var m = Methods.sendMessage(durkaChatId, textForAdmins);
        if (feedbackType.equals(Feedback.TYPE_TRUE_STORY)) {
            m.setReplyMarkup(new MarkupBuilder().addButton(ButtonBuilder.callbackButton()
                            .payload("c_publish " + feedbackId)
                            .text("Опубликовать"))
                    .build());
        }
        m.callAsync(context.sender);
        context.replyToMessage("✅ Сообщение отправлено админам!").callAsync(context.sender);
    }
}
