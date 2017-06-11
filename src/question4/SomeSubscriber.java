package question4;

public class SomeSubscriber {
    public static int Count;

    public SomeSubscriber(SomePublisher publisher) {
        publisher.subscribe(this);
    }

    public SomeAction getAction() {
        final SomeSubscriber me = this;
        class Action implements SomeAction {

            @Override
            public void doAction() {
               me.doSomething();
            }
        }

        return new Action();
    }

    @Override
    protected void finalize() throws Throwable {
    	System.out.println("Some subscriber finalization was run");
        SomeSubscriber.Count++;
    }

    private void doSomething() {
        // TODO: something
    }
}