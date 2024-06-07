package com.senderman.durkafeedbackbot.model;

import io.micronaut.core.annotation.Creator;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

import java.util.Objects;

@MappedEntity("feedback")
public class Feedback {

    public static final String TYPE_QUESTION = "Вопрос";
    public static final String TYPE_TRUE_STORY = "История";
    public static final String TYPE_TOPIC_REQUEST = "Предложение темы";

    @MappedProperty("message")
    private final String message;
    @MappedProperty("type")
    private final String type;
    @MappedProperty("user_id")
    private final long userId;
    @MappedProperty("user_name")
    private final String userName;
    @MappedProperty("chat_id")
    private final long chatId;
    @MappedProperty("message_id")
    private final int messageId;
    @Id
    @MappedProperty("id")
    private int id;
    @MappedProperty("replied")
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
