import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;

public class FibonacciSubscriberVerification extends SubscriberBlackboxVerification<Long> {

    public FibonacciSubscriberVerification() {
        super(new TestEnvironment());
    }

    @Override
    public Subscriber<Long> createSubscriber() {
        return new FibonacciSubscriber();
    }

    @Override
    public Long createElement(int element) {
        return new Long(element);
    }
}
