package oostd.am.raytracer.streaming.pipeline;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

/**
 * A Submission publisher with a default limit of 1.000.000 and a fixed threadpool of 3,
 * @param <T>
 */
public class PipelineSubmissionPublisher<T> extends SubmissionPublisher<T> {
    public PipelineSubmissionPublisher() {
        this(1_000_000);
    }

    public PipelineSubmissionPublisher(int limit) {
        super(Executors.newFixedThreadPool(3), limit);
    }
}
