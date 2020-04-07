package oostd.am.performance;

import java.util.Random;
import java.util.stream.Stream;

public class StreamTest {
    public static void main(String[] args) {
        Random random = new Random();

        long start = System.nanoTime();
        Stream.generate(random::nextInt)
                .parallel()
                .limit(100000000)
                .map(integer -> integer + random.nextInt())
                .map(integer -> integer / 2)
                .map(d -> Math.pow(d, 2))
                .forEach(aDouble -> {});

        long duration = System.nanoTime() - start;

        System.out.println("Duration in ms: " + duration/1000000);
    }
}
