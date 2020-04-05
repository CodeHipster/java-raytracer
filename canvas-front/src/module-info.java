import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.api.scenery.SceneService;

module oostd.am.raytracer.visualize.desktop {
    requires java.desktop;
    requires oostd.am.raytracer.api;
    uses RayTracerService;
    uses SceneService;
}