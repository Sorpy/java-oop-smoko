package uni.coursework;

import uni.coursework.entity.Block;
import uni.coursework.entity.Food;
import uni.coursework.entity.Obstacle;
import uni.coursework.entity.SnakePart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class GameRunner extends JFrame implements Runnable,KeyListener {

    private Block block;
    private ArrayList<Block> snake;
    private ArrayList<Block> food;
    private ArrayList<Block> obstacles;
    private int score = 0;
    //private Point snakePoint;
    private int xCoordinate;
    private int yCoordinate;
    private int ticks;
    private Random r;

    private int size = 8;

    private Thread thread;

    private boolean isAlive;

    private String moveDirection;

    public GameRunner(){
        initBoard();
        //this.snakePoint = new Point();
        r = new Random();
        isAlive=true;
        ticks= 0;
        moveDirection="U";
        snake = new ArrayList<>();
        food= new ArrayList<>();
        obstacles = new ArrayList<>();
        //snakePoint.setLocation(20,20);
        xCoordinate=20;
        yCoordinate=20;
        addObstacle();
        thread =new Thread(this);
        thread.start();


    }

    private void addObstacle() {
        int firstWall = 10;
        int secondWall = 30;
        for (int j =0;j<20;j++){
            block = new Obstacle(firstWall,j);
            obstacles.add(block);
            block = new Obstacle(secondWall,40-j);
            obstacles.add(block);
        }

    }

    private void initBoard() {
        setSize(Constants.FIELD_WIDTH*Constants.BLOCK_SIZE +Constants.BLOCK_SIZE,
                Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE +Constants.BLOCK_SIZE);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        //getRootPane().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    @Override
    public void run() {
        while (isAlive){
            gameTick();
            repaint();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void gameTick(){
        //init snake
        if (snake.size()==0){
            block = new SnakePart(xCoordinate, yCoordinate);
            snake.add(block);
        }

        //create/consume food
        if (food.size()==0){
            createFood();
        } else {
            eatFood();
        }
        moveSnake();
        checkSnakeCollision();
    }

    private void checkSnakeCollision() {
        for (Block obstacle :obstacles) {
            if (snake.get(snake.size()-1).getxCoordinate()==obstacle.getxCoordinate()&&
                    snake.get(snake.size()-1).getyCoordinate()==obstacle.getyCoordinate()){
                isAlive=false;

            }
        }
        for (int snakePart = 0; snakePart < snake.size() - 1; snakePart++) {
            if (snake.get(snakePart).getxCoordinate()==snake.get(snake.size()-1).getxCoordinate()&&
            snake.get(snakePart).getyCoordinate()==snake.get(snake.size()-1).getyCoordinate()){
                isAlive =false;
            }
        }
    }

    private void moveSnake() {
        switch (moveDirection){
            case "R":
                xCoordinate++;
                break;
            case "L":
                xCoordinate--;
                break;
            case "U":
                yCoordinate--;
                break;
            case "D" :
                yCoordinate++;
                break;
        }
        if (xCoordinate>39){
            xCoordinate=0;
        }
        if (yCoordinate>39){
            yCoordinate=0;
        }
        if (xCoordinate<0){
            xCoordinate=39;
        }
        if (yCoordinate<0){
            yCoordinate=39;
        }
        block = new SnakePart(xCoordinate, yCoordinate);
        snake.add(block);

        if (snake.size()>size){
            snake.remove(0);
        }

    }

    private void eatFood() {
        if (xCoordinate==food.get(0).getxCoordinate() &&
                yCoordinate== food.get(0).getyCoordinate()){
            food.remove(0);
            size++;
        }
    }

    private void createFood() {
        boolean isFoodValid = false;
        while(!isFoodValid){
            block = new Food(true,r.nextInt(Constants.FIELD_HEIGHT),r.nextInt(Constants.FIELD_WIDTH));
            boolean addFood = true;
            for (Block snakePart :snake) {
                if (block.getxCoordinate()==snakePart.getxCoordinate()&&
                block.getyCoordinate()==snakePart.getyCoordinate()){
                    addFood = false;
                }
            }
            for (Block obstacle :obstacles) {
                if (block.getxCoordinate()==obstacle.getxCoordinate()&&
                        block.getyCoordinate()==obstacle.getyCoordinate()){
                    addFood = false;
                }
            }
            if (addFood) {
                food.add(block);
                repaint();
                isFoodValid=true;
            }
        }
    }

    @Override
    public void paint(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0, 0,Constants.FIELD_WIDTH*Constants.BLOCK_SIZE, Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE);

        for (int i = 0; i < snake.size(); i++) {
            snake.get(i).drawPart(g);
        }
        for(int i = 0; i < food.size(); i++) {
            food.get(i).drawPart(g);
        }
        for(int i = 0; i < obstacles.size(); i++) {
            obstacles.get(i).drawPart(g);
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
