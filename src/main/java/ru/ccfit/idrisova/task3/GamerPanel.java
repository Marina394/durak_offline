package ru.ccfit.idrisova.task3;

public class GamerPanel implements Gamer {
    private int countCards;
    private Card cards[];

    private boolean isCardSelected = false;
    private int indexSelectedCard;

    GamerPanel(){
        countCards = 0;
        cards = new Card[36];
    }

    public void updateCards(){
        for(int i = 0; i < countCards; ++i){
            if(cards[i].getCardStatus() == CardStatus.attack || cards[i].getCardStatus() == CardStatus.fightBack){
                cards[i].setCardStatus(CardStatus.remove);
                for(int j = i; j < countCards; ++j)
                    cards[j] = cards[j + 1];
                countCards--;
                break;
            }
        }
    }

    public void takeCard(Card card){
        card.setCardStatus(CardStatus.inGamerDeck);
        cards[countCards] = card;
        countCards++;
    }

    public int getCountCards() {
        return countCards;
    }

    public Card[] getCards(){
        return cards;
    }

    public boolean isFightBackCard(Card card, CardSuit trump){
        for(int i = 0; i < countCards; ++i){
            if(fightBack(i, card, trump)){
                cards[i].setCardStatus(CardStatus.inGamerDeck);
                return true;
            }
        }
        return false;
    }

    public boolean fightBack(int indexCard, Card card, CardSuit trump){
        if(card.getCardSuit() == cards[indexCard].getCardSuit()){
            if(card.getCardValue().ordinal() < cards[indexCard].getCardValue().ordinal()){
                cards[indexCard].setCardStatus(CardStatus.fightBack);
                return true;
            }
            return false;
        }
        if(cards[indexCard].getCardSuit() == trump){
            cards[indexCard].setCardStatus(CardStatus.fightBack);
            return true;
        }
        return false;
    }

    public Card attack(int indexCard, CardSuit trump){
        cards[indexCard].setCardStatus(CardStatus.attack);
        return cards[indexCard];
    }

    public boolean isCardSelected(){
        return isCardSelected;
    }

    public void setIndexSelectedCard(int indexSelectedCard){
        this.indexSelectedCard = indexSelectedCard;
    }

    public int getIndexSelectedCard(){
        return indexSelectedCard;
    }

    public void setSelection(boolean isCardSelected){
        this.isCardSelected = isCardSelected;
    }
}
