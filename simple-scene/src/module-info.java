import oostd.am.raytracer.api.scenery.SceneService;

module oostd.am.raytracer.scene.simple {
    requires oostd.am.raytracer.api;
    provides SceneService with oostd.am.raytracer.scene.simple.SceneService;
}