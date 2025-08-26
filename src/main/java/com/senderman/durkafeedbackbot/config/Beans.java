package com.senderman.durkafeedbackbot.config;

import com.annimon.tgbotsmodule.BotModuleOptions;
import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;

import java.util.List;

@Factory
public class Beans {

    @Singleton
    public BotModuleOptions botOptions(BotConfig config) {
        return BotModuleOptions.
                create(config.token())
                .telegramUrlSupplierDefault()
                .getUpdatesGeneratorDefaultWithAllowedUpdates(List.of("message", "callback_query"))
                .build();
    }

}
