package uni.coursework;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        GameRunner gameRunner = new GameRunner();
        JFrame jFrame = new JFrame();
        jFrame.add(gameRunner);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.pack();

    }
}
