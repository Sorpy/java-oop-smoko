package uni.coursework;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameRunner extends JFrame implements Runnable,KeyListener {

    //private GameBoard gameField;
    private int gameHeight;
    private int gameWidth;
    private SnakePart snakePart;
    private ArrayList<SnakePart> snake;
    private Food food;
    private int score = 0;
    //private Point snakePoint;
    private int x=20;
    private int y=20;
    private int ticks;

    private int size = 5;

    private Thread thread;

    private boolean isAlive;

    private String moveDirection;

    public GameRunner(){
        initBoard();
        //this.snakePoint = new Point();
        isAlive=true;
        ticks= 0;
        moveDirection="R";
        snake = new ArrayList<>();
        //snakePoint.setLocation(20,20);
        thread =new Thread(this);
        thread.start();

    }

    private void initBoard() {
        setSize(Constants.FIELD_WIDTH*Constants.BLOCK_SIZE,Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
        setFocusable(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        while (isAlive){
            gameTick();
            repaint();
        }
    }
    public void gameTick(){
        if (snake.size()==0){
            snakePart = new SnakePart(x,y);
            snake.add(snakePart);
        }
        ticks++;
        if (ticks>2500000){
            switch (moveDirection){
                case "R":
                    x++;
                    break;
                case "L":
                    x--;
                    break;
                case "U":
                    y--;
                    break;
                case "D" :
                    y++;
                    break;
            }

            ticks=0;
            snakePart = new SnakePart(x,y);
            snake.add(snakePart);

            if (snake.size()>size){
                snake.remove(0);
            }
        }
    }

    @Override
    public void paint(Graphics g){
        //g.clearRect(0, 0, Constants.FIELD_WIDTH*Constants.BLOCK_SIZE, Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,Constants.FIELD_WIDTH*Constants.BLOCK_SIZE, Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE);

        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).drawPart(g);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int pressedKey = e.getKeyCode();
        switch (pressedKey){
            case KeyEvent.VK_RIGHT:
                if (!moveDirection.equals("L")){
                    moveDirection = "R";
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!moveDirection.equals("R")){
                    moveDirection = "L";
                }
                break;
            case KeyEvent.VK_UP:
                if (!moveDirection.equals("D")){
                    moveDirection = "U";
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!moveDirection.equals("U")){
                    moveDirection = "D";
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


}
