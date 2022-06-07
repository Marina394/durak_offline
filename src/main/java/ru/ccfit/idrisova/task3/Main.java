package ru.ccfit.idrisova.task3;

import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    public static void main(String[] args){
        Main main = new Main();
        main.setTitle("Durak");
        main.setSize(new Dimension(800, 600));
        main.setBackground(Color.gray);
        main.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GameModel gameModel = new GameModel();
        GameView gamePanel = new GameView(gameModel);

        main.getContentPane().add(gamePanel);
        GameController gameProcess = new GameController(gameModel, gamePanel);


        main.setVisible(true);
    }
}
