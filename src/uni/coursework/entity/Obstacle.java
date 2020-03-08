package uni.coursework.entity;

import uni.coursework.Constants;

import java.awt.*;

public class Obstacle extends Block {
    public Obstacle(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
    }

    @Override
    public void drawPart(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(getxCoordinate()* Constants.BLOCK_SIZE,
                getyCoordinate()*Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE);
    }
}
