package oostd.am.performance;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * Actually faster than the StreamTest.
 * Uses all cores, but not as intense as the StreamTest (makes me think cpu's are busy waiting)
 */
public class PubSubTest {

    public static void main(String[] args) {
        Generator generator = new Generator();
        CalcSubscriber subscriber = new CalcSubscriber();
        generator.subscribe(subscriber);


        long start = System.nanoTime();
        generator.start();
        long duration = System.nanoTime() - start;

        System.out.println("Duration in ms: " + duration/1000000);
    }

    static class Generator extends SubmissionPublisher<Integer> {

        public Generator(){
            super(Executors.newFixedThreadPool(3), 1000000);
        }

        Random random = new Random();
        int i = 0;
        public void start(){
            while(i < 100000000){
                submit(random.nextInt());
                i ++;
            }
        }
    }

    static class CalcSubscriber implements Flow.Subscriber<Integer> {

        Random random = new Random();

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override
        public void onNext(Integer item) {
            item = item + random.nextInt();
            item = item / 2;
            Math.pow(item, 2);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {
            System.out.println("done");
        }
    }
}
