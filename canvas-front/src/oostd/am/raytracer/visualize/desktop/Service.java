package oostd.am.raytracer.visualize.desktop;

import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.visualize.desktop.render.RenderFrame;
import oostd.am.raytracer.visualize.desktop.render.RenderPixelSubscriber;

import java.util.List;

public class Service {

    private RayTracerService rayTracerService;

    public Service(){
        List<RayTracerService> instances = RayTracerService.getInstances();
        if(instances.isEmpty()) throw new RuntimeException("No raytracer service found.");
        rayTracerService = instances.get(0);
    }
    public void startRender(Scene scene, Camera camera){
        RenderFrame renderFrame = new RenderFrame(camera.lens.width, camera.lens.height);
        RenderPixelSubscriber renderPixelSubscriber = new RenderPixelSubscriber(renderFrame);
        rayTracerService.startRendering(renderPixelSubscriber, scene, camera);
    }
}
