import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrillMachineEditor{
    Random random = new Random();
    private int drillXCoordinate;
    private int drillYCoordinate;
    private ImageView drillerView;
    private Image drillFace;
    private Text fuelText;
    private Text haulText;
    private Text moneyText;
    static int  blockNumber;
    private static final int SCENE_WIDTH = 800;
    private static final int SCENE_HEIGHT = 802;
    List<Integer> emptyBlocks = new ArrayList<>();
    /**
     * Displays the current fuel amount within the specified GridPane.
     *
     * @param gridPane The GridPane where the fuel information will be displayed.
     */
    public void fuelViewer(GridPane gridPane,DrillMachine drillMachine){
        fuelText = new Text(" fuel: " + drillMachine.getFuelAmount());
        fuelText.setFont(Font.font("Montserrat", 25));
        fuelText.setFill(Color.WHITE);
        GridPane.setConstraints(fuelText, 0,0, 2, 1);
        gridPane.add(fuelText,0,0);
    }
    /**
     * Displays the current haul amount within the specified GridPane.
     *
     * @param gridPane The GridPane where the haul information will be displayed.
     */
    public void haulViewer(GridPane gridPane,DrillMachine drillMachine){
        haulText = new Text(" haul: " + drillMachine.getHaulAmount());
        haulText.setFont(Font.font("Montserrat", 25));
        haulText.setFill(Color.WHITE);
        GridPane.setConstraints(haulText, 0,1, 2, 1);
        gridPane.add(haulText,0,1);
    }
    /**
     * Displays the current money amount within the specified GridPane.
     *
     * @param gridPane The GridPane where the money information will be displayed.
     */
    public void moneyViewer(GridPane gridPane,DrillMachine drillMachine){
        moneyText = new Text(" money: " + drillMachine.getMoneyAmount());
        moneyText.setFont(Font.font("Montserrat", 25));
        moneyText.setFill(Color.WHITE);
        GridPane.setConstraints(moneyText, 0,2, 2, 1);
        gridPane.add(moneyText,0,2);
    }
    /**
     * Sets up counters to display fuel, haul, and money amounts within the specified GridPane.
     *
     * @param gridPane The GridPane where the counters will be displayed.
     */
    public void setCounters(GridPane gridPane,DrillMachine drillMachine){
        fuelViewer(gridPane,drillMachine);
        haulViewer(gridPane,drillMachine);
        moneyViewer(gridPane,drillMachine);
    }
    public void decreaseFuelAmount(boolean isMoving,Stage primaryStage,DrillMachine drillMachine) {
        double decreaseAmount = 0.007;
        if (isMoving)
            decreaseAmount = 100;
        drillMachine.setFuelAmount(drillMachine.getFuelAmount() - (float) decreaseAmount);
        fuelText.setText(" Fuel: " + drillMachine.getFuelAmount());
        if (drillMachine.getFuelAmount() <= 0) {
            gameOver(primaryStage,false,drillMachine);
        }
    }
    public void fuelTimer(Stage primaryStage,DrillMachine drillMachine){
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.01), e -> decreaseFuelAmount(false,primaryStage,drillMachine))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    /**
     * Initializes and starts the drill machine game within the specified stage and grid pane.
     * This method sets up the drill machine's initial position, gravity effect, fuel consumption,
     * and player controls for movement.
     *
     * @param primaryStage The primary stage where the game scene will be displayed.
     * @param gridPane The GridPane used to display the game elements.
     * @param drillMachine The DrillMachine instance representing the player's drill machine.
     * @param worldEditor The WorldEditor instance responsible for world editing and interactions.
     */
    public void drillMachineStarter(Stage primaryStage, GridPane gridPane, DrillMachine drillMachine, WorldEditor worldEditor) {
        // Set initial drill position
        drillXCoordinate = random.nextInt(16);
        drillYCoordinate = 2;
        drillerViewSetter(drillXCoordinate, drillYCoordinate, drillMachine.getLeftDrillImage(), gridPane);

        // Create and set up the game scene
        Scene scene = new Scene(gridPane, SCENE_WIDTH, SCENE_HEIGHT);

        // Start decreasing fuel over time
        fuelTimer(primaryStage,drillMachine);

        // Set up animation timer to handle gravity working
        AnimationTimer animationTimer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                long interval = 600_000_000; // Interval for gravity effect
                if (now - lastTime >= interval) {
                    gravityWorking(gridPane, drillYCoordinate, drillXCoordinate);
                    lastTime = now;
                }
            }
        };

        // Handle player controls
        scene.setOnKeyPressed(event -> {
            boolean isMoved = true;
            int xChange = 0;
            int yChange = 0;
            KeyCode keyCode = event.getCode();

            // Determine movement based on key pressed
            switch (keyCode) {
                case UP:
                    drillFace = drillMachine.getFlyDrillImage();
                    flyDriller(drillYCoordinate - 1, drillXCoordinate, gridPane);
                    break;
                case DOWN:
                    drillFace = drillMachine.getDownDrillImage();
                    yChange = 1;
                    break;
                case LEFT:
                    drillFace = drillMachine.getLeftDrillImage();
                    xChange = -1;
                    break;
                case RIGHT:
                    drillFace = drillMachine.getRightDrillImage();
                    xChange = 1;
                    break;
                default:
                    isMoved = false;
                    break;
            }
            // Decrease fuel based on movement
            if (isMoved){
                decreaseFuelAmount(true, primaryStage,drillMachine);
            }

            // Move the drill machine
            moveDriller(drillXCoordinate, drillYCoordinate, drillXCoordinate + xChange, drillYCoordinate + yChange, gridPane, worldEditor, primaryStage,drillMachine);

            // Start animation timer if not already running
            animationTimer.start();
        });

        // Set up primary stage for the game
        primaryStage.setTitle("HU-Load");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     *
     * @param currentColumn Refers to the current column of the drillMachine
     * @param currentRow Refers to the current row of the drillMachine
     * @param newColumn Refers to the new column of the drillMachine
     * @param newRow Refers to the new row of the drillMachine
     * @param gridPane The GridPane used to display the game elements.
     * @param worldEditor The WorldEditor instance responsible for world editing and interactions.
     */
    private void moveDriller(int currentColumn, int currentRow, int newColumn, int newRow, GridPane gridPane,WorldEditor worldEditor,Stage primaryStage,DrillMachine drillMachine) {
        // Block numbers of cells
        blockNumber = 16 * newRow + newColumn;
        // Provides control the game limits
        if (newRow > 16 || newRow < 0 || newColumn > 15 || newColumn < 0){
            return;
        }
        ImageView imageViewToMove = null;
        // Finds the location of the machine
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == currentRow &&
                    GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == currentColumn &&
                    node instanceof ImageView) {
                ImageView imageView = (ImageView) node;
                if (imageViewToMove == null || gridPane.getChildren().indexOf(imageView) > gridPane.getChildren().indexOf(imageViewToMove)) {
                    imageViewToMove = imageView;
                }
            }
        }
        // Deletes the machine from its old location and moves it to its new location. If there is a cell to drill, it will drill
        if (imageViewToMove != null) {
            if (newColumn + drillerView.getFitWidth() <= drillerView.getScene().getWidth() && newRow + drillerView.getFitHeight() <= drillerView.getScene().getHeight()){
                if(drilledWorldRemover(newColumn,newRow,gridPane,worldEditor,blockNumber,primaryStage,drillMachine)){
                    gridPane.getChildren().remove(imageViewToMove);
                    drillYCoordinate = newRow;
                    drillXCoordinate = newColumn;
                    drillerViewSetter(newColumn, newRow, drillFace, gridPane);
                }
            }
        }
    }

    /**
     * Movement method customized for flying
     * @param newColumn Refers to the new column of the drillMachine
     * @param newRow Refers to the new row of the drillMachine
     * @param gridPane The GridPane used to display the game elements.
     */
    public void flyDriller(int newRow, int newColumn, GridPane gridPane) {
        // Provides control the game limits
        if (newRow > 16 || newRow < 0 || newColumn > 15 || newColumn < 0){
            return;
        }

        boolean imageViewExists = false;
        //Checks whether the location to be flown is empty or not
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == newRow && GridPane.getColumnIndex(node) == newColumn && node instanceof ImageView) {
                imageViewExists = true;
                break;
            }
        }
        //If the location is empty, the drill machine performs the move
        if (!imageViewExists) {
            Node node1 = null;
            for (Node node : gridPane.getChildren()) {
                if (GridPane.getRowIndex(node) == newRow + 1 && GridPane.getColumnIndex(node) == newColumn && node instanceof ImageView) {
                    node1 = node;
                    drillYCoordinate = newRow;
                    drillXCoordinate = newColumn;
                }
            }
            if (node1 != null){
                gridPane.getChildren().remove(node1);
                drillerViewSetter(newColumn, newRow, drillFace, gridPane);
            }
        }
    }

    /**
     * Sets up and displays the drill machine's view at the specified position within a GridPane.
     * @param imageX X position of drill machine
     * @param imageY Y position of drill machine
     * @param drillFace Picture containing the location to which the drill machine is directed
     * @param gridPane The GridPane used to display the game elements.
     */
    public void drillerViewSetter(int imageX, int imageY, Image drillFace, GridPane gridPane){
        drillerView = new ImageView(drillFace);
        drillerView.setFitWidth(75);
        drillerView.setFitHeight(75);
        drillerView.setPreserveRatio(false);
        GridPane.setConstraints(drillerView, imageX,imageY, 2, 2);
        GridPane.setMargin(drillerView, new Insets(10,50,50,-20));
        gridPane.add(drillerView,imageX,imageY);
        drillerView.setX(imageX);
        drillerView.setY(imageY);
    }

    /**
     * If the game ends by touching lava or running out of fuel, gameover is printed.
     *
     * @param primaryStage The primary stage where the game over screen will be displayed.
     * @param isLava Indicates whether the game over is due to falling into lava (true) or other reasons (false).
     */
    public void gameOver(Stage primaryStage, boolean isLava,DrillMachine drillMachine) {
        // Create a StackPane for the game over screen
        StackPane gameOverPane = new StackPane();

        // Create and configure the game over label
        Label gameOverLabel = new Label("GAME OVER");
        gameOverLabel.setFont(new Font(70));
        gameOverLabel.setTextFill(Color.WHITE);
        gameOverLabel.setTranslateY(-50);

        // Determines whether it ends by touching lava or running out of fuel.
        if (isLava) {
            gameOverPane.setStyle("-fx-background-color: #8a0404;"); // Red background for lava game over
            // Restart button
            Button restartButton = new Button("Restart The Game");
            restartButton.setFont(new Font(20));
            restartButton.setTextFill(Color.BLUE);
            restartButton.setTranslateY(160);
            restartButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Main main = new Main();
                    try {
                        main.start(primaryStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            gameOverPane.getChildren().add(restartButton);
        } else {
            gameOverPane.setStyle("-fx-background-color: #097b03;"); // Green background for running out of fuel game over
            // Display collected money amount for non-lava game over
            Label moneyLabel = new Label("Collected Money: " + drillMachine.getMoneyAmount());
            moneyLabel.setFont(new Font(50));
            moneyLabel.setTextFill(Color.WHITE);
            moneyLabel.setTranslateY(50);
            gameOverPane.getChildren().add(moneyLabel); // Add money label to game over screen
        }
        // Add game over label to game over screen
        gameOverPane.getChildren().add(gameOverLabel);

        // Set the game over screen as the scene for the primary stage
        primaryStage.setScene(new Scene(gameOverPane, SCENE_WIDTH, SCENE_HEIGHT));
    }
    /**
     * Updates haul amount and money amount based on the value and weight of collected valuables.
     *
     * @param worth The worth or value of the collected valuables.
     * @param weight The weight of the collected valuables.
     */
    public void valuablesOperations(int worth, int weight, DrillMachine drillMachine){
        // Update haul amount by adding the weight of the collected valuables
        drillMachine.setHaulAmount(drillMachine.getHaulAmount() + weight);
        // Update money amount by adding the worth of the collected valuables
        drillMachine.setMoneyAmount(drillMachine.getMoneyAmount() + worth);
        // Update the displayed haul amount text
        haulText.setText(" haul: " + drillMachine.getHaulAmount());
        // Update the displayed money amount text
        moneyText.setText(" money: " + drillMachine.getMoneyAmount());
    }
    /**
     * Simulates gravity by moving an ImageView down within a GridPane if the block below is empty.
     *
     * @param gridPane The GridPane containing the game elements.
     * @param currentRow The current row of the DrillMachine.
     * @param currentColumn The current column of the DrillMachine.
     */
    public void gravityWorking(GridPane gridPane,int currentRow, int currentColumn){
        // Calculate block number of the block directly below the DrillMachine
        int downBlockNumber = 16 * (currentRow + 1) + currentColumn;
        ImageView imageViewToMove = null;
        for (Node node: gridPane.getChildren())
            if (GridPane.getRowIndex(node) == currentRow && GridPane.getColumnIndex(node) == currentColumn && node instanceof ImageView)
                imageViewToMove = (ImageView) node;
        if (emptyBlocks.contains(downBlockNumber)) {
            gridPane.getChildren().remove(imageViewToMove);
            drillYCoordinate = currentRow + 1;
            drillXCoordinate = currentColumn;
            drillerViewSetter(drillXCoordinate, drillYCoordinate, drillFace, gridPane);
        }
    }

    /**
     *
     * @param newColumn The column where the DrillMachine is located
     * @param newRow The row where the DrillMachine is located
     * @param gridPane The GridPane containing the game elements.
     * @param worldEditor The WorldEditor instance responsible for world editing and interactions.
     * @param blockNumber Number of the block to be deleted
     * @param primaryStage The primary stage where the game over screen will be displayed.
     * @return Checks whether the encountered block can be deleted or not.
     */
    public boolean drilledWorldRemover(int newColumn, int newRow, GridPane gridPane,WorldEditor worldEditor,int blockNumber,Stage primaryStage,DrillMachine drillMachine){
        for (int i = 0; i < 48 ; i++)
            emptyBlocks.add(i);
        //Adds to the deleted list of deleted blocks
        if (!worldEditor.borders.contains(blockNumber))
            emptyBlocks.add(blockNumber);
        ImageView nodeToRemove = null;
        //It determines the type of block it encounters and performs operations appropriate to the block type.
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == newColumn &&
                    GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == newRow &&
                    node instanceof ImageView) {
                nodeToRemove = (ImageView) node;
                //It checks whether the encountered block is a border block or not.
                if (worldEditor.borders.contains(Integer.valueOf(blockNumber))) {
                    return false;
                } else if (worldEditor.lavas.contains(Integer.valueOf(blockNumber))){
                    gameOver(primaryStage,true,drillMachine);
                } else if (worldEditor.diamonds.contains(Integer.valueOf(blockNumber))) {
                    valuablesOperations(100000,100,drillMachine);
                    worldEditor.diamonds.remove(Integer.valueOf(blockNumber));
                } else if (worldEditor.emeralds.contains(Integer.valueOf(blockNumber))) {
                    valuablesOperations(5000,60,drillMachine);
                    worldEditor.emeralds.remove(Integer.valueOf(blockNumber));
                } else if (worldEditor.bronziums.contains(Integer.valueOf(blockNumber))) {
                    valuablesOperations(60,10,drillMachine);
                    worldEditor.bronziums.remove(Integer.valueOf(blockNumber));
                }
                break;
            }
        }
        if (nodeToRemove != null) {
            gridPane.getChildren().remove(nodeToRemove);
            return true;
        }
        return true;
    }
}
