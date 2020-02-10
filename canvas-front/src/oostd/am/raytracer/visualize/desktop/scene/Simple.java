package oostd.am.raytracer.visualize.desktop.scene;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Color;
import oostd.am.raytracer.api.camera.Positioning;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.debug.DebugCamera;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.scenery.ColorFilter;
import oostd.am.raytracer.api.scenery.Material;
import oostd.am.raytracer.api.scenery.PointLight;
import oostd.am.raytracer.api.scenery.Triangle;
import oostd.am.raytracer.api.scenery.Vertex;
import oostd.am.raytracer.api.scenery.VolumeProperties;
import oostd.am.raytracer.visualize.desktop.render.PixelSubscriberFactory;

public class Simple extends BaseScene {

    public Simple(PixelSubscriberFactory factory) {
        super(factory);

        Material material = new Material(
                100,
                1,
                0,
                false,
                new ColorFilter(0.5f, 0.5f, 0.0f)
        );

        triangles.add(new Triangle(
                new Vertex[]{
                        new Vertex(-2.0, 0.0, -2.0),
                        new Vertex(0.0, 5.0, 0.0),
                        new Vertex(2.0, 0.0, 2.0)
                },
                material,
                new VolumeProperties(new ColorFilter(1, 1, 1), 1)
        ));

        pointLights.add(new PointLight(new Vertex(3, 2, 1), new Color(1, 1, 1)));

        Resolution renderResolution = new Resolution(1, 1);
        renderCamera = new Camera(
                new Positioning(
                        new Vector(0, 2, -10),
                        UnitVector.construct(0, 0, 1))
                ,1
                , renderResolution
                , this.subscriberFactory.createSubscriber(renderResolution)
        );

        Resolution debugResolution = new Resolution(300, 300);
        debugCameras.add(new DebugCamera(
                new Positioning(
                        new Vector(10, 2, 0),
                        UnitVector.construct(-1, 0, 0))
                ,10,10
                , debugResolution
                , this.subscriberFactory.createSubscriber(debugResolution)
        ));
    }
}
