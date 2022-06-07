package ru.ccfit.idrisova.task3;

public class GamerBot implements Gamer{
    private int countCards;
    private Card cards[];

    GamerBot(){
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

    private Card getSmallestCard(Card card1, Card card2, CardSuit trump){
        if(card1.getCardSuit() != trump){
            if(card2.getCardSuit() == trump)
                return card1;
            else
                return card2.getCardValue().ordinal() < card1.getCardValue().ordinal() ? card2 : card1;
        }
        else{
            if(card2.getCardSuit() == trump && card2.getCardValue().ordinal() > card1.getCardValue().ordinal())
                return card1;
            return card2;
        }
    }

    public boolean fightBack(int indexCard, Card card, CardSuit trump){
        Card cardFightBack = cards[0];
        boolean isFight = false;
        for(int i = 0; i < countCards; ++i){
            if(cards[i].getCardSuit() == card.getCardSuit()){
                if(cards[i].getCardValue().ordinal() > card.getCardValue().ordinal()){
                    cardFightBack = isFight ? getSmallestCard(cardFightBack, cards[i], trump) : cards[i];
                    isFight = true;
                }
            }
            else{
                if(cards[i].getCardSuit() == trump){
                    cardFightBack = isFight ? getSmallestCard(cardFightBack, cards[i], trump) : cards[i];
                    isFight = true;
                }
            }
        }
        if(isFight)
            cardFightBack.setCardStatus(CardStatus.fightBack);
        return isFight;
    }

    public Card attack(int indexCard, CardSuit trump){
        Card card = cards[0];
        for(int i = 1; i < countCards; ++i){
            card = getSmallestCard(card, cards[i], trump);
        }
        card.setCardStatus(CardStatus.attack);
        return card;
    }
}
