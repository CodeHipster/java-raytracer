import oostd.am.raytracer.api.RayTracerService;

module oostd.am.raytracer.api {
    exports oostd.am.raytracer.api.debug;
    exports oostd.am.raytracer.api.scenery;
    exports oostd.am.raytracer.api.camera;
    exports oostd.am.raytracer.api.geography;
    exports oostd.am.raytracer.api;
    uses RayTracerService;
}