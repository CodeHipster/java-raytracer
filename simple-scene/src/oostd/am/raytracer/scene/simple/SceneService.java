package oostd.am.raytracer.scene.simple;

import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.scene.simple.factory.TetraHedron;

public class SceneService implements oostd.am.raytracer.api.scenery.SceneService {
    @Override
    public Scene getScene() {
        return new TetraHedron().getScene();
    }
}
