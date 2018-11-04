package entrants.utils.logging;

import javax.swing.*;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class TextAreaHandler extends StreamHandler {
    private JTextArea textArea;

    public void setTextArea(JTextArea area) {
        textArea = area;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        flush();

        if (textArea != null) {
            textArea.append(getFormatter().format(record));
        }
    }
}
