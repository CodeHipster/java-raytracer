package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.debug.Window;

import java.util.List;

/**
 * Scene contains objects to define the scene geometry
 */
public final class Scene {

    public List<Triangle> triangles;
    public List<Sphere> spheres;
    public List<PointLight> pointLights;
    public Camera renderCamera;
    public List<Window> debugWindows;

    public Scene(List<Triangle> triangles, List<Sphere> spheres, List<PointLight> pointLights, Camera renderCamera, List<Window> debugWindows) {
        this.triangles = triangles;
        this.spheres = spheres;
        this.pointLights = pointLights;
        this.renderCamera = renderCamera;
        this.debugWindows = debugWindows;
    }
}
