package com.senderman.durkafeedbackbot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;

import java.util.Objects;

@TypeAlias("feedback")
public class Feedback {

    public static final String TYPE_QUESTION = "Вопрос";
    public static final String TYPE_TRUE_STORY = "История";
    public static final String TYPE_TOPIC_REQUEST = "Предложение темы";

    @Id
    private int id;
    private String message;
    private String type;
    private long userId;
    private String userName;
    private long chatId;
    private int messageId;
    private boolean isReplied;

    public Feedback() {
    }

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

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
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

    public void setType(String type) {
        this.type = type;
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
