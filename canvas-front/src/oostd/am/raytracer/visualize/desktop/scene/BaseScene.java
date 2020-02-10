package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.visualize.desktop.render.PixelSubscriberFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseScene implements Scene {

    protected PixelSubscriberFactory subscriberFactory;
    protected List<Triangle> triangles;
    protected List<PointLight> pointLights;
    protected Camera renderCamera;
    protected List<DebugCamera> debugCameras;

    public BaseScene(PixelSubscriberFactory subscriberFactory){
        this.subscriberFactory = subscriberFactory;
        this.triangles = new ArrayList<>();
        this.pointLights = new ArrayList<>();
        this.debugCameras = new ArrayList<>();
    }

    @Override
    public List<Triangle> getTriangles() {
        return triangles;
    }

    @Override
    public List<PointLight> getPointLights() {
        return pointLights;
    }

    @Override
    public Camera getRenderCamera() {
        return renderCamera;
    }

    @Override
    public List<DebugCamera> getDebugCameras() {
        return debugCameras;
    }
}
