package com.senderman.durkafeedbackbot.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.senderman.durkafeedbackbot.util.DialogState;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class DialogStateService {

    private final Cache<Long, DialogState> cache;

    public DialogStateService() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterAccess(Duration.ofHours(2))
                .build();
    }

    public void insert(DialogState dialogState) {
        cache.put(dialogState.getUserId(), dialogState);
    }

    public void remove(long id) {
        cache.invalidate(id);
    }

    @NotNull
    public DialogState get(long id) {
        var result = cache.getIfPresent(id);
        return result != null ? result : new DialogState(id, DialogState.Stage.EMPTY);
    }
}
