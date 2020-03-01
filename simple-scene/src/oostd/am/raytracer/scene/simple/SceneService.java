package oostd.am.raytracer.scene.simple;

import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.scene.simple.factory.Refraction;

public class SceneService implements oostd.am.raytracer.api.scenery.SceneService {
    @Override
    public Scene getScene() {
        return new Refraction().getScene();
    }
}
