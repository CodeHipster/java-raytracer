package oostd.am.raytracer.streaming.pipeline;

import java.util.concurrent.Executors;
import java.util.concurrent.SubmissionPublisher;

public class PipelineSubmissionPublisher<T> extends SubmissionPublisher<T> {
    public PipelineSubmissionPublisher() {
        this(1_000_000);
    }

    public PipelineSubmissionPublisher(int limit) {
        super(Executors.newFixedThreadPool(3), limit);
    }
}
