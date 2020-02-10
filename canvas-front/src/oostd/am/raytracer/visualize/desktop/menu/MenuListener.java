package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.api.scenery.Scene;
import oostd.am.raytracer.visualize.desktop.RenderService;
import oostd.am.raytracer.visualize.desktop.render.ScreenManager;
import oostd.am.raytracer.visualize.desktop.scene.Simple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuListener implements ActionListener {

    private RenderService renderService;
    private ScreenManager screenManager;

    MenuListener(RenderService renderService, ScreenManager screenManager) {
        this.renderService = renderService;
        this.screenManager = screenManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startRendering".equals(e.getActionCommand())) {
//            Pyramid scene = new Pyramid();
//            List<Flow.Subscriber<Pixel>> debugCams = scene.getDebugCameras().stream()
//                    .map(c -> screenManager.createWindow(convert(c.resolution))).collect(Collectors.toList());
//            Flow.Subscriber<Pixel> renderOutput = screenManager.createWindow(convert(scene.getRenderCamera().resolution));
//            scene.attachPixelConsumers(renderOutput, debugCams.get(0));
//            renderService.startRender(scene);

//            LotsOCameras scene = new LotsOCameras();
//
//            List<Flow.Subscriber<Pixel>> debugCams = scene.getDebugCameras().stream()
//                    .map(c -> screenManager.createWindow(convert(c.resolution))).collect(Collectors.toList());
//            Flow.Subscriber<Pixel> renderOutput = screenManager.createWindow(convert(scene.getRenderCamera().resolution));
//            List<Flow.Subscriber<Pixel>> outputs = new ArrayList<>();
//            outputs.add(renderOutput);
//            outputs.addAll(debugCams);
//            scene.attachCameraOutput(outputs);
//            renderService.startRender(scene);

            Scene simple = new Simple(screenManager);
            renderService.startRender(simple);
        }
    }
}
