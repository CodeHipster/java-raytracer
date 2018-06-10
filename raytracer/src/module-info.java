import oostd.am.raytracer.api.RayTracerService;

module oostd.am.raytracer {
    requires oostd.am.raytracer.api;
    provides RayTracerService with oostd.am.raytracer.RayTracerService;
}