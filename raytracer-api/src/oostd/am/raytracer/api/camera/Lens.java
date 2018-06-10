package oostd.am.raytracer.api.camera;


public class Lens {
    public int width, height, offset;

    /**
     * rays are cast from the camera through each "pixel" of the lens.
     * @param width
     * @param height
     * @param offset of lens from the camera in units used in the scenery.
     */
    public Lens(int width, int height, int offset){
        this.width = width;
        this.height = height;
        this.offset = offset;
    }
}
