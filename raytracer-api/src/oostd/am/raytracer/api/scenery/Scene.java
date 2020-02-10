package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.debug.DebugCamera;

import java.util.List;

public interface Scene {

    List<Triangle> getTriangles();

    List<PointLight> getPointLights();

    Camera getRenderCamera();

    List<DebugCamera> getDebugCameras();
}
