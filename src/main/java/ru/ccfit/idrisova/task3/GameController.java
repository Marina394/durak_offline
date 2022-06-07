package ru.ccfit.idrisova.task3;

import ru.ccfit.idrisova.task3.Observer.Observer;

import javax.swing.*;
import java.awt.event.*;
import java.util.Random;

public class GameController  implements Observer {

    final private GameView gamePanel;
    final private GameModel gameModel;


    GameController(GameModel gameModel, GameView gamePanel) {
        this.gamePanel = gamePanel;
        gamePanel.reg(this);
        this.gameModel = gameModel;
        gameModel.reg(gamePanel);


        gamePanel.addKeyListener(gamePanel.actionOnKey);
    }



    /*public void actionPerformed(ActionEvent e) {
        if(gameModel.isGameOver())
            return;

        gameModel.action();
        gamePanel.repaint();
    }*/

    @Override
    public void update(int k) {
        gameModel.getGamerPanel().setIndexSelectedCard(k);
        gameModel.getGamerPanel().setSelection(true);
        gameModel.action();
    }
}
