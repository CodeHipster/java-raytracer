package oostd.am.raytracer.streaming.tracer;

public class Tracer {

    private RayInitializer rayInitializer;

    public Tracer(RayInitializer rayInitializer){
        this.rayInitializer = rayInitializer;
    }

    public void start(){
        rayInitializer.initialize();
    }
}
