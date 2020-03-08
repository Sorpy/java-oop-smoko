package uni.coursework.entity;

import uni.coursework.Constants;

import java.awt.*;

public class Food extends Block {
    private boolean isMovingFood;

    public Food(boolean isMovingFood, int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
        this.isMovingFood = isMovingFood;

    }

    public boolean isMovingFood() {
        return isMovingFood;
    }

    public void setMovingFood(boolean movingFood) {
        isMovingFood = movingFood;
    }

    @Override
    public void drawPart(Graphics g){
        if (isMovingFood) {
            g.setColor(Color.GREEN);
            g.fillRect(getxCoordinate() * Constants.BLOCK_SIZE,
                    getyCoordinate() * Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE);
        }else {
            g.setColor(Color.ORANGE);
            g.fillRect(getxCoordinate() * Constants.BLOCK_SIZE,
                    getyCoordinate() * Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE,
                    Constants.BLOCK_SIZE);
        }
    }
}
