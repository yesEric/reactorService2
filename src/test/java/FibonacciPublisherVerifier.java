import org.reactivestreams.Publisher;
import org.reactivestreams.tck.PublisherVerification;
import org.reactivestreams.tck.TestEnvironment;

public class FibonacciPublisherVerifier extends PublisherVerification {
    public FibonacciPublisherVerifier() {
        super(new TestEnvironment());
    }

    @Override
    public Publisher createPublisher(long elements) {
        return new FibonacciPublisher();
    }

    @Override
    public Publisher createFailedPublisher() {
        return null;
    }
}
