package oostd.am.raytracer.api.scenery;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.debug.Window;

import java.util.List;

/**
 * TODO: rethink the scene api
 should it be data driven, scene contains all just data and no functionality?
 how are cameras and debug windows connected?
 camera configuration and functional interface should be separated?
 configuration, e.g position, size, direction should be defined in the scene
 but non scene config, like resolution should be configured elsewhere,
      but should be part of the api, as it needs to be communicated to the engine doing the rendering
 And there should be a way to connect a subscriber(something that displays pixels) to a publisher(the engine that produces pixels and lines)

 So all classes in the scene should be concrete data classes. including this one.
 then the engine gets a scene with camera configuration to render
 it should also be provided with a factory to get subscribers for the windows
 for now add resolution to the scene? or does the subscriber factory decide the resolution?
 as it is up to the presentation component to decide what the resolution should be.

 this makes me think we should split up the scene from the api?
 a scene service provides a scene, this can be a hardcoded implementation or reading from a file
 for different formats even.

 raytracer-api
    no dependencies
 raytracer
    compile time dependency on raytracer-api
    implements rayTracerService
 scene
    compile time dependency on raytracer-api
    implements sceneService
 presentation component
    compile time dependency on raytracer-api
    runtime dependency on scene
    runtime dependency on rayTracer
 */

public final class Scene {

    public List<Triangle> triangles;
    public List<PointLight> pointLights;
    public Camera renderCamera;
    public List<Window> debugWindows;

    public Scene(List<Triangle> triangles, List<PointLight> pointLights, Camera renderCamera, List<Window> debugWindows) {
        this.triangles = triangles;
        this.pointLights = pointLights;
        this.renderCamera = renderCamera;
        this.debugWindows = debugWindows;
    }
}
