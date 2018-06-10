import oostd.am.raytracer.api.RayTracer;

module oostd.am.raytracer {
    requires oostd.am.raytracer.api;
    provides RayTracer with oostd.am.raytracer.RayTracer;
}