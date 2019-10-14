package observer;
import events.Event;

public interface EventHandler<T extends Event> {

    void handle(T event);

}
