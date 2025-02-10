import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Creates objects and invokes methods to start the game
     *
     * @param primaryStage the primary stage for this application, onto which the
     *                     application scene can be set.
     */
    @Override
    public void start(Stage primaryStage){
        DrillMachine drillMachine = new DrillMachine();
        GridPane gridPane = new GridPane();
        WorldEditor worldEditor = new WorldEditor();
        DrillMachineEditor drillMachineEditor = new DrillMachineEditor();
        worldEditor.worldCreator(gridPane);
        drillMachineEditor.drillMachineStarter(primaryStage,gridPane,drillMachine,worldEditor);
        drillMachineEditor.setCounters(gridPane,drillMachine);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
