package entrants.utils.logging;

import javax.swing.*;
import java.util.logging.*;

public final class GhostLogger {
    private static TextAreaHandler handler;
    private static SimpleFormatter formatter;

    public static void setup() {
        // suppress the logging output to the console
        Logger rootLogger = Logger.getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        if (handlers[0] instanceof ConsoleHandler) {
            rootLogger.removeHandler(handlers[0]);
        }

        rootLogger.setLevel(Level.INFO);

        // Create TextAreaHandler
        handler = new TextAreaHandler();

        // create a TXT formatter
        formatter = new SimpleFormatter();
        handler.setFormatter(formatter);

        // Add handler to the logger
        rootLogger.addHandler(handler);
    }

    public static void setTextArea(JTextArea area) {
        handler.setTextArea(area);
    }
}
