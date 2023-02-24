package com.senderman.durkafeedbackbot.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;

import java.util.Objects;

@MappedEntity("feedback")
public class Feedback {

    public static final String TYPE_QUESTION = "Вопрос";
    public static final String TYPE_TRUE_STORY = "История";
    public static final String TYPE_TOPIC_REQUEST = "Предложение темы";

    @Id
    private int id;
    private final String message;
    private final String type;
    private final long userId;
    private final String userName;
    private final long chatId;
    private final int messageId;
    private boolean isReplied;

    @Creator
    public Feedback(String message, String type, long userId, String userName, long chatId, int messageId) {
        this.message = message;
        this.type = type;
        this.userId = userId;
        this.userName = userName;
        this.chatId = chatId;
        this.messageId = messageId;
        this.isReplied = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public long getChatId() {
        return chatId;
    }

    public int getMessageId() {
        return messageId;
    }

    public boolean isReplied() {
        return isReplied;
    }

    public void setReplied(boolean replied) {
        isReplied = replied;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return id == feedback.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
