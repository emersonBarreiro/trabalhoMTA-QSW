package observer;
import events.Event;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class EventEmitter {

    private Map<String, HashSet<EventHandler>> handlers = new HashMap<String, HashSet<EventHandler>>();

    public void on(String eventName, EventHandler eventHandler) {
        if (! this.handlers.containsKey(eventName)) {
            this.handlers.put(eventName, new HashSet<>());
        }

        this.handlers.get(eventName).add(eventHandler);
    }

    public void dispatch(String eventName, Event event) {
        if (this.handlers.containsKey(eventName)) {
            HashSet<EventHandler> handlers = this.handlers.get(eventName);
            for (EventHandler handler : handlers) {
                handler.handle(event);
            }
        }
    }

}

