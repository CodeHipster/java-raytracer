package oostd.am.raytracer.scene.simple.factory;

import oostd.am.raytracer.api.scenery.Scene;

public abstract class BaseSceneFactory {
    protected Scene scene;

    public Scene getScene() {
        return scene;
    }
}
