package oostd.am.raytracer.scene.simple.factory;

import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Sphere;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSceneFactory {
    protected Scene scene;

    protected List<Triangle> triangles = new ArrayList<>();
    protected List<Sphere> spheres = new ArrayList<>();
    protected List<PointLight> pointLights = new ArrayList<>();
    protected List<Window> debugWindows = new ArrayList<>();

    public Scene getScene() {
        return scene;
    }
}

