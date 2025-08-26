package com.senderman.durkafeedbackbot;

import com.annimon.tgbotsmodule.BotModule;
import com.annimon.tgbotsmodule.Runner;
import com.annimon.tgbotsmodule.beans.Config;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Singleton
public class DurkaFeedbackBot implements BotModule {

    private final BotHandler botHandler;

    public DurkaFeedbackBot(BotHandler botHandler) {
        this.botHandler = botHandler;
    }

    public static void main(String[] args) {
        Micronaut.build(args)
                .classes(DurkaFeedbackBot.class)
                .banner(!System.getProperties().containsKey("disableBanner"))
                .start();
    }

    @Override
    public @NotNull BotHandler botHandler(@NotNull Config config) {
        return botHandler;
    }

    @EventListener
    public void runBot(StartupEvent event) {
        Runner.run(List.of(this));
    }
}
