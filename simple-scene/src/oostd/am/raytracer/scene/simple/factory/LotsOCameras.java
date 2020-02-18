package oostd.am.raytracer.scene.simple.factory;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.Window;
import oostd.am.raytracer.api.geography.Dimension;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.Scene;

import java.util.ArrayList;

public class LotsOCameras extends BaseSceneFactory {
    public LotsOCameras() {

        for (int i = 0; i < 4; i++) {
            Resolution debugResolution = new Resolution(300, 300);
            debugWindows.add(new Window(
                    new Vector(0, i * 2, 0),
                    new UnitVector(new Vector(1, 0, 0)),
                    new UnitVector(new Vector(0, 1, 0)),
                    new Dimension(10, 10)
            ));
        }

        Camera renderCamera = new Camera(
                new Vector(0, 0, 0),
                new UnitVector(0, 0, 1),
                new UnitVector(0, 1, 0),
                1);

        this.scene = new Scene(new ArrayList<>(), new ArrayList<>(), renderCamera, debugWindows);
    }
}
