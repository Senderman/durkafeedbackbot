package com.senderman.durkafeedbackbot.util.callback;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public abstract class ButtonBuilder {

    protected final InlineKeyboardButton button;

    protected ButtonBuilder(String text) {
        this.button = new InlineKeyboardButton(text);
    }

    public static ButtonBuilder callbackButton(String text) {
        return new CallbackButtonBuilder(text);
    }

    public static ButtonBuilder urlButton(String text) {
        return new UrlButtonBuilder(text);
    }

    public static ButtonBuilder switchInlineQueryButton(String text) {
        return new SwitchInlineQueryButtonBuilder(text);
    }

    public static ButtonBuilder switchInlineQueryCurrentChatButton(String text) {
        return new SwitchInlineQueryCurrentChatButtonBuilder(text);
    }

    public ButtonBuilder text(String text) {
        button.setText(text);
        return this;
    }

    public abstract ButtonBuilder payload(String payload);

    public InlineKeyboardButton create() {
        return button;
    }

    private static class CallbackButtonBuilder extends ButtonBuilder {

        protected CallbackButtonBuilder(String text) {
            super(text);
        }

        @Override
        public ButtonBuilder payload(String payload) {
            button.setCallbackData(payload);
            return this;
        }
    }

    private static class UrlButtonBuilder extends ButtonBuilder {

        protected UrlButtonBuilder(String text) {
            super(text);
        }

        @Override
        public ButtonBuilder payload(String payload) {
            button.setUrl(payload);
            return this;
        }
    }

    private static class SwitchInlineQueryButtonBuilder extends ButtonBuilder {


        protected SwitchInlineQueryButtonBuilder(String text) {
            super(text);
        }

        @Override
        public ButtonBuilder payload(String payload) {
            button.setSwitchInlineQuery(payload);
            return this;
        }
    }

    private static class SwitchInlineQueryCurrentChatButtonBuilder extends ButtonBuilder {

        protected SwitchInlineQueryCurrentChatButtonBuilder(String text) {
            super(text);
        }

        @Override
        public ButtonBuilder payload(String payload) {
            button.setSwitchInlineQueryCurrentChat(payload);
            return this;
        }
    }

}
