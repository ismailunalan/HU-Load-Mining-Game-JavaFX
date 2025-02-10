import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that contains methods for creating the playing field
 */
public class WorldEditor{
    static int blockNumber;
    private final Image grassImage = new Image("file:src/assets/underground/top_01.png");
    private final  Image lavaImage = new Image("file:src/assets/underground/lava_01.png");
    private final  Image soilImage = new Image("file:src/assets/underground/soil_01.png");
    private final  Image bronziumImage = new Image("file:src/assets/underground/valuable_bronzium.png");
    private final  Image diamondImage = new Image("file:src/assets/underground/valuable_diamond.png");
    private final  Image emeraldImage = new Image("file:src/assets/underground/valuable_emerald.png");
    private final  Image borderImage = new Image("file:src/assets/underground/obstacle_01.png");
    Random random = new Random();
    public List<Integer> soils = new ArrayList<>();
    public List<Integer> lavas = new ArrayList<>();
    public  List<Integer> emeralds = new ArrayList<>();
    public List<Integer> bronziums = new ArrayList<>();
    public List<Integer> borders = new ArrayList<>();
    public List<Integer> diamonds = new ArrayList<>();

    /**
     * Creates the playing field
     *
     * @param gridPane The GridPane containing the game elements.
     */
    public void worldCreator(GridPane gridPane){
        backGroundDirtCreator(gridPane);
        borderWorldCreator(gridPane);
        randomUndergroundCreator(gridPane);
    }

    /**
     * Places blocks in the background underground
     *
     * @param gridPane The GridPane containing the game elements.
     */
    public void backGroundDirtCreator(GridPane gridPane){
        for(int row = 3; row < 15; row++) {
            for (int col = 0; col < 16; col++) {
                Rectangle dirtRectangle = new Rectangle(50, 50, Color.CHOCOLATE);
                gridPane.add(dirtRectangle, col, row);
                if (row == 3)
                    dirtRectangle.setTranslateY(3);
            }
        }
    }

    /**
     * Creates the game's borders and sky
     *
     * @param gridPane The GridPane containing the game elements.
     */
    public void borderWorldCreator(GridPane gridPane){
        //Loops for cell placements
        for (int col = 0; col < 16; col++) {
            Rectangle skyRectangle1 = new Rectangle(50, 50, Color.DARKBLUE);
            gridPane.add(skyRectangle1, col, 0);
            Rectangle skyRectangle2 = new Rectangle(50, 50, Color.DARKBLUE);
            gridPane.add(skyRectangle2, col, 1);
            Rectangle skyRectangle3 = new Rectangle(50, 52, Color.DARKBLUE);
            Rectangle sky = new Rectangle(50,5,Color.DARKBLUE);
            StackPane skyStack = new StackPane(sky);
            skyStack.setTranslateY(-25);
            gridPane.add(skyStack, col, 3);
            gridPane.add(skyRectangle3, col, 2);
            ImageView borderView = new ImageView(borderImage);
            gridPane.add(borderView, col, 15);
            blockNumber = 240 + col;
            borders.add(blockNumber);
            ImageView grassView = new ImageView(grassImage);
            grassView.setFitHeight(50);
            gridPane.add(grassView,col,3);
        }
        for (int row = 4; row < 16; row++) {
            ImageView border1 = new ImageView(borderImage);
            ImageView border2 = new ImageView(borderImage);
            gridPane.add(border1, 0, row);
            blockNumber = 16 * row;
            borders.add(blockNumber);
            gridPane.add(border2, 15, row);
            blockNumber = 16 * row + 15;
            borders.add(blockNumber);
        }
    }

    /**
     * Makes underground distribution random
     *
     * @param gridPane The GridPane containing the game elements.
     */
    public void randomUndergroundCreator(GridPane gridPane){
        for (int row = 4; row < 15; row++) {
            for (int col = 1; col < 15; col++) {
                int randomImageIndex, randomNumber;

                //It selects up to 100 random numbers and selects blocks according to a certain weight.
                randomNumber = random.nextInt(100);
                if (randomNumber < 80) {
                    randomImageIndex = 0;
                } else if (randomNumber < 88) {
                    randomImageIndex = 1;
                } else if (randomNumber < 91) {
                    randomImageIndex = 2;
                } else if (randomNumber < 93) {
                    randomImageIndex = 3;
                }else if (randomNumber < 97){
                    randomImageIndex = 4;
                }else {
                    randomImageIndex = 5;
                }
                blockNumber = 16 * row + col;
                //Places the selected block on the playing field
                Image randomImage = getRandomImage(randomImageIndex,blockNumber);
                ImageView imageView = new ImageView(randomImage);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);

                gridPane.add(imageView, col, row);
            }
        }
    }

    /**
     *
     * @param index Refers to the block that the selected number corresponds to
     * @param blockNumber Cell number to be placed
     * @return Selected image type
     */
    private Image getRandomImage(int index,int blockNumber) {
        switch (index) {
            case 0:
                soils.add(blockNumber);
                return soilImage;
            case 1:
                bronziums.add(blockNumber);
                return bronziumImage;
            case 2:
                emeralds.add(blockNumber);
                return emeraldImage;
            case 3:
                diamonds.add(blockNumber);
                return diamondImage;
            case 4:
                lavas.add(blockNumber);
                return lavaImage;
            case 5:
                borders.add(blockNumber);
                return borderImage;
            default:
                soils.add(blockNumber);
                return soilImage;
        }
    }
}
