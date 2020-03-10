package uni.coursework.entity;

import uni.coursework.Constants;

import java.awt.*;

public class Food extends Block {
    private boolean isMovingFood;
    private int moveTick;

    public Food(boolean isMovingFood, int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
        this.isMovingFood = isMovingFood;
        this.moveTick =0;

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

    public int getMoveTick() {
        return moveTick;
    }

    public void setMoveTick(int moveTick) {
        this.moveTick = moveTick;
    }
}
