package oostd.am.raytracer.visualize.desktop.menu;

import oostd.am.raytracer.api.scenery.SceneService;
import oostd.am.raytracer.visualize.desktop.RenderService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuListener implements ActionListener {

    private RenderService renderService;

    //TODO: selection window for user to select what scene to use.
    private SceneService sceneService;

    MenuListener(RenderService renderService) {
        this.renderService = renderService;

        List<SceneService> sceneServices = SceneService.getInstances();
        if(sceneServices.isEmpty()) throw new RuntimeException("No scene services found.");
        if(sceneServices.size() > 1){
            System.out.println("Found multiple instances of the scene service. Using the first one.");
            for(SceneService service : sceneServices){
                System.out.println(service.toString());
            }
        }
        sceneService = sceneServices.get(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("startRendering".equals(e.getActionCommand())) {
            renderService.startRender(sceneService.getScene());
        }
    }
}
