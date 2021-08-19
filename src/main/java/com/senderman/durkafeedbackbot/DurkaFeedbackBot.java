package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;

import java.util.List;

public class DurkaFeedbackBot implements BotModule {

    public static void main(String[] args) {
        Runner.run("", List.of(new DurkaFeedbackBot()));
    }

    @Override
    public @NotNull BotHandler botHandler(@NotNull Config config) {
        return SpringApplication
                .run(BotHandler.class)
                .getBean(BotHandler.class);
    }
}
