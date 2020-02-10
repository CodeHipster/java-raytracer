package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.api.scenery.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class LotsOCameras implements Scene {

    private List<DebugCamera> debugCameras;
    private Camera renderCamera;

    public LotsOCameras() {
        List<DebugCamera> debugCameras = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++){
            debugCameras.add(new DebugCamera(
                    new Positioning(new Vector(0,0,0), UnitVector.construct(new Vector(1,0,0)))
                    ,200
                    , 200
                    , new Resolution(300,300)
                    , null
            ));
        }
        this.debugCameras = debugCameras;
        this.renderCamera = new Camera(
                new Positioning(new Vector(0,0,0), UnitVector.construct(new Vector(1,0,0)))
                ,1
                , new Resolution(300,300)
                , null);;
    }

    @Override
    public List<Triangle> getTriangles() {
        return new ArrayList<>();
    }

    @Override
    public List<PointLight> getPointLights() {
        return new ArrayList<>();
    }

    @Override
    public Camera getRenderCamera() {
        return renderCamera;
    }

    @Override
    public List<DebugCamera> getDebugCameras() {
        return debugCameras;
    }

    /**
     * expect 5 outputs
     * first output is render, other 4 are debug
     * @param outputs
     */
    public void attachCameraOutput(List<Flow.Subscriber<Pixel>> outputs){
        this.renderCamera.outputConsumer = outputs.get(0);
        this.debugCameras.get(0).outputConsumer = outputs.get(1);
        this.debugCameras.get(1).outputConsumer = outputs.get(2);
        this.debugCameras.get(2).outputConsumer = outputs.get(3);
        this.debugCameras.get(3).outputConsumer = outputs.get(4);
    }
}
