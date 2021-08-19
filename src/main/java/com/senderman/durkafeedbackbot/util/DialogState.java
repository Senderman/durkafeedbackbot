package com.senderman.durkafeedbackbot.util;

public class DialogState {

    private final long userId;
    private Stage stage;

    public DialogState(long userId, Stage stage) {
        this.userId = userId;
        this.stage = stage;
    }

    public long getUserId() {
        return userId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public enum Stage {
        EMPTY,
        TOPIC_REQUEST,
        TRUE_STORY,
        QUESTION
    }
}
