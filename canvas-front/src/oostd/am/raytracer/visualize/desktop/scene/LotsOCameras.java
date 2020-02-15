package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.DebugWindow;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.visualize.desktop.render.PixelSubscriberFactory;

import java.util.ArrayList;
import java.util.List;

public class LotsOCameras extends BaseScene {

    public LotsOCameras(PixelSubscriberFactory factory) {
        super(factory);
        List<DebugWindow> debugWindows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Resolution debugResolution = new Resolution(300, 300);
            debugWindows.add(new DebugWindow(
                    new Vector(0, i*2, 0),
                    UnitVector.construct(new Vector(1, 0, 0)),
                    UnitVector.construct(new Vector(0, 1, 0)),
                    new Dimension(10,10),
                    debugResolution
            ));
        }
        this.debugWindows = debugWindows;
        Resolution renderResolution = new Resolution(300, 300);
        this.renderCamera = new Camera(
                new Positioning(new Vector(0, 0, 0), UnitVector.construct(new Vector(1, 0, 0)))
                , 1
                , renderResolution
                , this.subscriberFactory.createSubscriber(renderResolution));
    }
}
