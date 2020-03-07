package uni.coursework;

import jdk.nashorn.internal.ir.Block;

import java.awt.*;

public class SnakePart {
//    private Point point;
//
//    public SnakePart(Point point) {
//        this.point = point;
//    }
//
//    public Point getPoint() {
//        return point;
//    }
//
//    public void setPoint(Point point) {
//        this.point = point;
//    }

    private int x;
    private int y;

    public SnakePart(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void drawPart(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(getX()*Constants.BLOCK_SIZE,
                getY()*Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE,
                Constants.BLOCK_SIZE);
    }
}
