package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.api.RayTracer;
import oostd.am.raytracer.api.camera.Camera;
import oostd.am.raytracer.api.camera.Pixel;
import oostd.am.raytracer.api.scenery.Scenery;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MenuListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("render".equals(e.getActionCommand())) {
            List<RayTracer> instances = RayTracer.getInstances();
            if(instances.isEmpty()) System.out.println("No implementations found...");
            for(RayTracer tracer : instances){
                Stream<Pixel> pixelStream = tracer.render(new Scenery(), new Camera());
                List<Pixel> pixelz = pixelStream.limit(100).peek(pixel -> System.out.println("xpos: " + pixel.position.x)).collect(Collectors.toList());

            }
        }
    }
}
