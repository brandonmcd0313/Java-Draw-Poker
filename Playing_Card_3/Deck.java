
import java.util.*; //import all util classes

public class Deck
{
    //create a deck array list
    private ArrayList<Card> deck = new ArrayList<Card>();
    
    public void createDeck()
    {
        //nested for loop populating ArrayList (creating deck)
        for(int i = 0; i < 4; i++) //suit
        {
            //create local variables that change throughout the loops
            String suit = ""; int rank = 0;
            //set appropriate suit in the local String
            {
                if(i == 0)
                    suit = "Spades ♠";
                else if(i == 1)
                    suit = "Hearts ♥";
                else if(i == 2)
                    suit = "Clubs ♣";
                else if(i == 3)
                    suit = "Diamonds ♦";
            }

            for(int j = 2; j < 15; j++)
                deck.add(new Card(suit, j));

        }
    }
    
    public void shuffleDeck()
    {
        //the ultimate shuffle machine
        Collections.shuffle(deck, new Random((int)(Math.random()*(10000000-500+1)+500)));
    }
    
    public Card deal()
    {
        Card toDeal = deck.get(0);
        deck.remove(0);
        return toDeal;
    }
    
    public Card deal(int i)
    {
        Card toDeal = deck.get(i);
        deck.remove(i);
        return toDeal;
    }
    
    public Card getCard(int i)
    {
        Card toDeal = deck.get(i);
        return toDeal;
    }
    
    public int count()
    {
        return deck.size();
    }
    
    public void gain(Card card)
    {
        deck.add(card);
    }
    
    public Card getCard()
    {
        return deck.get(deck.size()-1);
    }
    
}
