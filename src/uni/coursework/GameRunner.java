package uni.coursework;

import uni.coursework.entity.Block;
import uni.coursework.entity.Food;
import uni.coursework.entity.Obstacle;
import uni.coursework.entity.SnakePart;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameRunner extends JFrame implements Runnable,KeyListener, ActionListener {

    private Block block;
    private ArrayList<SnakePart> snake;
    private ArrayList<Food> food;
    private ArrayList<Obstacle> obstacles;
    private int score = 0;
    //private Point snakePoint;
    private int xCoordinate;
    private int yCoordinate;
    private int foodMoveTick;
    private Random r;
    private Thread thread;

    private int size = 8;

    private boolean isAlive;

    private String moveDirection;

    private Button pauseButton =new Button("Pause");
    private Button restartButton = new Button("Restart");
    boolean isThreadActive;

    public GameRunner(){
        initBoard();
        //this.snakePoint = new Point();
        r = new Random();
        isAlive=true;
        foodMoveTick = 0;
        moveDirection="U";
        snake = new ArrayList<>();
        food= new ArrayList<>();
        obstacles = new ArrayList<>();
        //snakePoint.setLocation(20,20);
        xCoordinate=20;
        yCoordinate=20;
        addObstacle();
        isThreadActive = true;
        thread =new Thread(this);
        thread.start();
        pauseButton.setBounds(450,100,50,50);
        pauseButton.addActionListener(this);
        add(pauseButton);

        restartButton.setBounds(450,200,50,50);
        restartButton.addActionListener(this);
        add(restartButton);


    }

    private void addObstacle() {
        int firstWall = 10;
        int secondWall = 30;
        for (int j =0;j<20;j++){
            block = new Obstacle(firstWall,j);
            obstacles.add((Obstacle) block);
            block = new Obstacle(secondWall,40-j);
            obstacles.add((Obstacle) block);
        }

    }

    private void initBoard() {
        setSize(Constants.FIELD_WIDTH*Constants.BLOCK_SIZE +Constants.BLOCK_SIZE*20,
                Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE +Constants.BLOCK_SIZE*20);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(this);
        setVisible(true);
        setFocusable(true);
        setLocationRelativeTo(null);
        setLayout(null);
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
    private void gameTick(){
        //init snake
        if (snake.size()==0){
            block = new SnakePart(xCoordinate, yCoordinate);
            snake.add((SnakePart) block);
        }

        //create/consume food
        if (!(food.size()==r.nextInt(4))){
            eatFood();
        } else {
            createFood();
        }
        moveSnake();
        checkSnakeCollision();
        if (!food.isEmpty()) {
            foodMoveTick++;
            for (int i = 0; i < food.size(); i++) {
                if (food.get(i).isMovingFood()&& foodMoveTick>3) {
                    moveFood(checkFoodMovement(food.get(i)),food.get(i));
                    foodMoveTick=0;
                }
            }
        }
    }

    private void moveFood(ArrayList<Food> moveList,Food movedFood) {
        block = moveList.get(r.nextInt(moveList.size()));
        food.add((Food) block);

        food.remove(movedFood);
    }

    private ArrayList<Food> checkFoodMovement(Food food) {
        ArrayList<Food> possibleMoves = new ArrayList<>();

        for (Obstacle obstacle: obstacles) {

            possibleMoves.add(new Food(true,
                    food.getxCoordinate()-1,
                    food.getyCoordinate()));

            if (obstacle.getxCoordinate()==food.getxCoordinate()-1 &&
                food.getxCoordinate()-1 >0 &&
                obstacle.getyCoordinate()==food.getyCoordinate()){
                possibleMoves.remove(possibleMoves.size()-1);

            }
            possibleMoves.add(new Food(true,
                    food.getxCoordinate()+1,
                    food.getyCoordinate()));

            if (obstacle.getxCoordinate()==food.getxCoordinate()+1 &&
                food.getxCoordinate()+1 <39 &&
                obstacle.getyCoordinate()==food.getyCoordinate()){
                possibleMoves.remove(possibleMoves.size()-1);

            }
            possibleMoves.add(new Food(true,
                    food.getxCoordinate(),
                    food.getyCoordinate()-1));

            if (obstacle.getxCoordinate()==food.getxCoordinate() &&
                obstacle.getyCoordinate()==food.getyCoordinate()-1&&
                food.getyCoordinate()-1>0){
                possibleMoves.remove(possibleMoves.size()-1);

            }
            possibleMoves.add(new Food(true,
                    food.getxCoordinate(),
                    food.getyCoordinate()+1));
            if (obstacle.getxCoordinate()==food.getxCoordinate() &&
                obstacle.getyCoordinate()==food.getyCoordinate()+1 &&
                food.getyCoordinate()+1>39){
                possibleMoves.remove(possibleMoves.size()-1);

            }
        }
        return possibleMoves;
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
            xCoordinate=1;
        }
        if (yCoordinate>39){
            yCoordinate=1;
        }
        if (xCoordinate<1){
            xCoordinate=39;
        }
        if (yCoordinate<1){
            yCoordinate=39;
        }
        block = new SnakePart(xCoordinate, yCoordinate);
        snake.add((SnakePart) block);

        if (snake.size()>size){
            snake.remove(0);
        }

    }

    private void eatFood() {
        for (Iterator<Food> foodIterator = food.iterator(); foodIterator.hasNext();){
            Food currentFood = foodIterator.next();
            if (xCoordinate==currentFood.getxCoordinate() &&
                    yCoordinate== currentFood.getyCoordinate()){
                if (currentFood.isMovingFood()) score+=15;
                else score+=10;
                foodIterator.remove();
                size++;
            }
        }
    }

    private void createFood() {
        boolean isFoodValid = false;
        while(!isFoodValid){
            boolean isMovingFood = 0== r.nextInt(3);
            block = new Food(isMovingFood,r.nextInt(Constants.FIELD_HEIGHT),r.nextInt(Constants.FIELD_WIDTH));
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
                food.add((Food) block);
                repaint();
                isFoodValid=true;
            }
        }
    }

    @Override
    public void paint(Graphics g){
//
//        pauseButton.setLocation(450,100);
//        restartButton.setLocation(450,200);

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

        if (score>=30){
            gameOutcome(g,"WINNER WINNER!!");
        }

        if (!isAlive){
            gameOutcome(g,"YOU LOST!!!");
        }

        g.setColor(new Color(31, 224, 255));
        g.fillRect(0,400,400,100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("Score" + score,200,440);
    }

    private void gameOutcome(Graphics g,String string){
        thread.stop();
        g.setColor(Color.black);
        g.fillRect(0, 0,Constants.FIELD_WIDTH*Constants.BLOCK_SIZE, Constants.FIELD_HEIGHT*Constants.BLOCK_SIZE);
        g.setColor(Color.RED);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
        g.drawString(string,50,200);
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


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(pauseButton)){
            if (isThreadActive){
                thread.suspend();
                isThreadActive= false;
            }
            else {
                thread.resume();
                isThreadActive =true;
            }
        } else if (e.getSource().equals(restartButton)){
            getContentPane().removeAll();
            new GameRunner();
            getContentPane().validate();
            getContentPane().repaint();
        }
    }
}
