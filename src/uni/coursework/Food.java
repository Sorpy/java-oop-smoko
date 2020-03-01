package uni.coursework;

public class Food extends GameBlock {
    private boolean isMovingFood;
    private GameField gameField;

    public boolean isMovingFood() {
        return isMovingFood;
    }

    public void setMovingFood(boolean movingFood) {
        isMovingFood = movingFood;
    }
}
