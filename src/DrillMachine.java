import javafx.scene.image.Image;

/**
 * Represents a drill machine with various directional images.
 * The class provides methods to access different drill images for specific directions.
 */
public class DrillMachine {
    private float fuelAmount = 10000;
    private int haulAmount = 0;
    private int moneyAmount = 0;
    private Image leftDrillImage;
    private Image rightDrillImage;
    private Image downDrillImage;
    private Image flyDrillImage;
    public DrillMachine() {
        this.leftDrillImage = new Image("file:src/assets/drill/drill_01.png");
        this.rightDrillImage = new Image("file:src/assets/drill/drill_58.png");
        this.downDrillImage = new Image("file:src/assets/drill/drill_40.png");
        this.flyDrillImage = new Image("file:src/assets/drill/drill_25.png");
    }
    /**
     * Returns the image representing the left-facing drill.
     *
     * @return The left-facing drill image.
     */
    public Image getLeftDrillImage() {
        return leftDrillImage;
    }

    /**
     * Returns the image representing the right-facing drill.
     *
     * @return The right-facing drill image.
     */
    public Image getRightDrillImage() {
        return rightDrillImage;
    }

    /**
     * Returns the image representing the downward-facing drill.
     *
     * @return The downward-facing drill image.
     */
    public Image getDownDrillImage() {
        return downDrillImage;
    }

    /**
     * Returns the image representing the flying (upward) drill.
     *
     * @return The flying (upward) drill image.
     */
    public Image getFlyDrillImage() {
        return flyDrillImage;
    }

    public float getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(float fuelAmount) {
        this.fuelAmount = fuelAmount;
    }

    public int getHaulAmount() {
        return haulAmount;
    }

    public void setHaulAmount(int haulAmount) {
        this.haulAmount = haulAmount;
    }

    public int getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(int moneyAmount) {
        this.moneyAmount = moneyAmount;
    }
}
