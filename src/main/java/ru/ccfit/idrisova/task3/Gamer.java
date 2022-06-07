package ru.ccfit.idrisova.task3;

public interface Gamer {
    public boolean fightBack(int indexCard, Card card, CardSuit trump);
    public Card attack(int indexCard, CardSuit trump);
    public void updateCards();
    public void takeCard(Card card);
    public int getCountCards();
    public Card[] getCards();
}
