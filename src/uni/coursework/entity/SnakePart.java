package uni.coursework.entity;

import uni.coursework.Constants;

import java.awt.*;

public class SnakePart extends Block {

    public SnakePart(int xCoordinate, int yCoordinate) {
        super(xCoordinate, yCoordinate);
    }

    @Override
    public void drawPart(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(getxCoordinate()* Constants.BLOCK_SIZE,
                getyCoordinate()*Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE);
    }
}
