package question4;

import java.util.ArrayList;
import java.util.List;

public class SomePublisher {
    List<SomeAction> actions = new ArrayList<SomeAction>();

    public void subscribe(SomeSubscriber subscriber) {
        actions.add(subscriber.getAction());
    }
}