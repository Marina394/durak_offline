package ru.ccfit.idrisova.task3;

import ru.ccfit.idrisova.task3.Observer.Observable;
import ru.ccfit.idrisova.task3.Observer.Observer;

import java.util.Random;

public class GameModel implements Observable {
    final private GamerBot gamerBot = new GamerBot();
    final private GamerPanel gamerPanel = new GamerPanel();
    private Gamer fightBackGamer;
    private Gamer attackingGamer;
    private CardSuit trump;
    private Card[] deckCards = new Card[36];
    private int countDeckCards = 36;
    private boolean isGameOver = false;
    private boolean isAttack = false;
    private boolean isFightBack = false;
    private Card cardAttack;
    private boolean cardIsTaken = false;

    private Observer observer;
    public GameModel(){
        initGame();
    }
    public GamerBot getGamerBot() {
        return gamerBot;
    }

    public GamerPanel getGamerPanel() {
        return gamerPanel;
    }

    public Card[] getDeckCards() {
        return deckCards;
    }

    public CardSuit getTrump() {
        return trump;
    }

    public int getCountDeckCards() {
        return countDeckCards;
    }

    public void setAttackingGamer(Gamer attackingGamer){
        this.attackingGamer = attackingGamer;
    }

    public void setTrump(CardSuit trump) {
        this.trump = trump;
    }

    public void setCountDeckCards(int countDeckCards) {
        this.countDeckCards = countDeckCards;
    }

    public Gamer getAttackingGamer(){
        return attackingGamer;
    }

    public void gameOver(){
        isGameOver = true;
    }

    public boolean isGameOver(){
        return isGameOver;
    }

    void initGame() {
        attackingGamer = gamerPanel;
        fightBackGamer = gamerBot;
        for (int i = 0; i < countDeckCards; ++i) {
            deckCards[i] = new Card();
        }

        fillDeckCards();
        setTrump();
        updateDeckCards(gamerBot);
        updateDeckCards(gamerPanel);
        isAttack = true;
    }

    private void fillDeckCards() {
        int index = 0;
        for (CardSuit suit : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                deckCards[index].setSuitAndValue(suit, value);
                index++;
            }
        }
    }
    private void setTrump() {
        int indexSuit = new Random().nextInt(CardSuit.values().length);
        trump = CardSuit.values()[indexSuit];
    }
    private void updateDeckCards(Gamer gamer) {
        gamer.updateCards();
        if(countDeckCards == 0)
            return;
        for(int i = gamer.getCountCards(); i < 6; ++i) {
            int index = new Random().nextInt(36);
            if (deckCards[index].getCardStatus() == CardStatus.inDeck) {
                gamer.takeCard(deckCards[index]);
                countDeckCards--;
            } else
                i--;
        }
        setCountDeckCards(countDeckCards);
    }
    private void attackPlay(){
        if(attackingGamer == gamerPanel){
            if(gamerPanel.isCardSelected()){
                gamerPanel.setSelection(false);
                cardAttack = attackingGamer.attack(gamerPanel.getIndexSelectedCard(), trump);
                isAttack = false;
                isFightBack = true;
            }
        }
        else{
            cardAttack = attackingGamer.attack(0, trump);
            isAttack = false;
            isFightBack = true;
        }
    }

    private void fightBackPlay(){
        if(fightBackGamer == gamerPanel){ //игрок пользователь
            if(gamerPanel.isFightBackCard(cardAttack, trump)){ //может отбиться
                if(gamerPanel.isCardSelected()){  //выбрал карту
                    if(gamerPanel.getIndexSelectedCard() == -39){ //хочет взять
                        cardIsTaken = true;
                        gamerPanel.setSelection(false);
                        isFightBack = false;
                        return;
                    }
                    if(fightBackGamer.fightBack(gamerPanel.getIndexSelectedCard(), cardAttack, trump)) { //карта верная
                        cardIsTaken = false;
                        gamerPanel.setSelection(false);
                        isFightBack = false;
                    }
                }
            }
            else {
                cardIsTaken = true;
                isFightBack = false;
            }
        }
        else{
            cardIsTaken = !fightBackGamer.fightBack(0, cardAttack, trump);
            isFightBack = false;
        }
    }
    private void updateGame(){
        updateDeckCards(attackingGamer);
        if(!cardIsTaken) {
            updateDeckCards(fightBackGamer);
            attackingGamer = fightBackGamer;
            fightBackGamer = (fightBackGamer == gamerBot) ? gamerPanel : gamerBot;
        }
        else{
            fightBackGamer.takeCard(cardAttack);
        }

        isAttack = true;
        if(attackingGamer.getCountCards() == 0 || fightBackGamer.getCountCards() == 0)
            gameOver();
    }

    public void action(){
        if(isAttack)
            attackPlay();
        else if(isFightBack)
            fightBackPlay();
        else
            updateGame();

        notifySubscribers(0);
    }

    @Override
    public void reg(Observer o) {
        observer = o;
    }

    @Override
    public void notifySubscribers(int k) {
        observer.update(k);
    }
}
