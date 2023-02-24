package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.runtime.Micronaut;
import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class DurkaFeedbackBot implements BotModule, EmbeddedApplication<DurkaFeedbackBot> {

    private final BotHandler botHandler;
    private final ApplicationContext context;
    private final ApplicationConfiguration configuration;

    public DurkaFeedbackBot(BotHandler botHandler, ApplicationContext context, ApplicationConfiguration configuration) {
        this.botHandler = botHandler;
        this.context = context;
        this.configuration = configuration;
    }

    public static void main(String[] args) {
        Micronaut.run(DurkaFeedbackBot.class, args);
    }

    @Override
    public @NotNull BotHandler botHandler(@NotNull Config config) {
        return botHandler;
    }

    @Scheduled(initialDelay = "1s")
    public void runBot() {
        new Thread(() -> Runner.run(List.of(this))).start();
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public ApplicationConfiguration getApplicationConfiguration() {
        return configuration;
    }

    @Override
    public boolean isRunning() {
        return true;
    }

    @Override
    public boolean isServer() {
        return true;
    }
}
