package question4;

public class Sample {

	public static void main(String[] args) {
        SomePublisher publisher = new SomePublisher();

        for (int i = 0; i < 10; i++) {
            @SuppressWarnings("unused")
			SomeSubscriber subscriber = new SomeSubscriber(publisher);
            subscriber = null;
        }
        publisher = null;
        
        System.gc();
        System.runFinalization();
        
        System.out. println("The answer is: " + SomeSubscriber.Count);
		
	}
}
