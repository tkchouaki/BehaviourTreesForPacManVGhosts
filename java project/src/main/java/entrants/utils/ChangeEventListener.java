package entrants.utils;

import javax.swing.event.ChangeEvent;
import java.util.EventListener;

public interface ChangeEventListener extends EventListener {
    void changed(ChangeEvent evt);
}
