import oostd.am.raytracer.api.RayTracerService;
import oostd.am.raytracer.random.RandomPixelService;

module oostd.am.raytracer.random {
    requires oostd.am.raytracer.api;
    provides RayTracerService with RandomPixelService;
}