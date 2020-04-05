package oostd.am.raytracer.engine;

import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Resolution;
import oostd.am.raytracer.api.geography.PixelPosition;
import oostd.am.raytracer.api.geography.UnitVector;
import oostd.am.raytracer.api.geography.Vector;
import oostd.am.raytracer.api.geography.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class RayInitializer {

    /**
     * Create rays from a camera.
     * The rays start from the camera position and are aimed at a 1 by 1 unit square at given offset.
     *
     * @param camera
     */
    static List<InverseRay> createRays(Camera camera, Resolution resolution) {
        List<InverseRay> inverseRays = new ArrayList<>(resolution.width*resolution.height);

        // stepSize in world space
        double xStep = camera.lens.dimension.width / resolution.width;
        double yStep = camera.lens.dimension.height / resolution.height;
        //start in the left bottom
        double xStart = (camera.lens.dimension.width / -2) + (xStep / 2); //+half a step to move to the center of the pixel
        double yStart = camera.lens.dimension.height / -2 + (yStep / 2);

        for (int y = 0; y < resolution.height; ++y) {
            double yLensPos = yStart + y * yStep;
            for (int x = 0; x < resolution.width; ++x) {
                double xLensPos = xStart + x * xStep;
                Vector lensPoint = camera.lens.positionOf(new Vector2D(xLensPos, yLensPos));
                Vector camToPoint = lensPoint.subtract(camera.position);
                UnitVector rayDirection = new UnitVector(camToPoint);
                InverseRay ray = new InverseRay(
                        1,
                        1,
                        rayDirection,
                        camera.position,
                        new PixelPosition(x, y),
                        null);
                inverseRays.add(ray);
            }
        }
        return inverseRays;
    }
}
