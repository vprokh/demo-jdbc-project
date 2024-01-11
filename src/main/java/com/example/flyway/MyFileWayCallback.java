package com.example.flyway;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;
import org.flywaydb.core.api.logging.Log;
import org.flywaydb.core.api.logging.LogFactory;

public class MyFileWayCallback implements Callback {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public boolean supports(Event event, Context context) {
        return event == Event.AFTER_EACH_MIGRATE;
    }

    @Override
    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    @Override
    public void handle(Event event, Context context) {
        if (event == Event.AFTER_EACH_MIGRATE) {
            log.info("> TEST afterEachMigrate");
        }
    }

    @Override
    public String getCallbackName() {
        return MyFileWayCallback.class.getSimpleName();
    }
}
