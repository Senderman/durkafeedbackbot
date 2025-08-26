package com.senderman.durkafeedbackbot.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("bot")
public record BotConfig(
        String username,
        String token,
        long durkaChatId,
        String durkaLink,
        long durkaChannelId
) {
}
