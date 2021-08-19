package com.senderman.durkafeedbackbot.callback;

import com.annimon.tgbotsmodule.commands.CallbackQueryCommand;
import com.annimon.tgbotsmodule.commands.authority.For;

import java.util.EnumSet;

public interface CallbackExecutor extends CallbackQueryCommand {

    @Override
    default EnumSet<For> authority() {
        return For.all();
    }
}
