package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.visualize.desktop.render.PixelSubscriberFactory;

import java.util.ArrayList;
import java.util.List;

public class LotsOCameras extends BaseScene {

    public LotsOCameras(PixelSubscriberFactory factory) {
        super(factory);
        List<DebugCamera> debugCameras = new ArrayList<>();
        for(int i = 0 ; i < 4 ; i++){
            Resolution debugResolution = new Resolution(300, 300);
            debugCameras.add(new DebugCamera(
                    new Positioning(new Vector(0,0,0), UnitVector.construct(new Vector(1,0,0)))
                    ,200
                    , 200
                    , debugResolution
                    , this.subscriberFactory.createSubscriber(debugResolution)
            ));
        }
        this.debugCameras = debugCameras;
        Resolution renderResolution = new Resolution(300, 300);
        this.renderCamera = new Camera(
                new Positioning(new Vector(0,0,0), UnitVector.construct(new Vector(1,0,0)))
                ,1
                , renderResolution
                , this.subscriberFactory.createSubscriber(renderResolution));
    }
}
