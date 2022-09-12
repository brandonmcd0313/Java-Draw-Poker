import java.util.*;
public class Player
{
    //a player has two decks
    //a draw deck (hand)
    private Deck drawPile = new Deck();
    //a discard deck
    private Deck discPile = new Deck();
    //a player has a name
    private String name = "";
    //rank of hand
    private int hand;
    private String highcard;
    private int high;
    //hand stuffs
    //create a array for suits and ranks
    private int[] ranks = new int[5];
    private String[] suits = new String[5];
    //the name is assigned based on the players number
    public Player(int i)
    {
        name = "Player " + Integer.toString(i);
    }

    //player can get a card
    public void gainDraw(Card card)
    {
        drawPile.gain(card);
    }

    public void gainDisc(Card card)
    {
        discPile.gain(card);
    }

    //palyer draw
    public Card draw()
    {
        return drawPile.deal();
    }

    public Card draw(int i)
    {
        return drawPile.deal(i);
    }

    //player check
    public boolean check()
    {
        //palyer has no card
        if(drawPile.count() == 0)
        {
            if(discPile.count() > 0)
            {
                //shuffle discard
                discPile.shuffleDeck();
                //set it as hand
                int size = discPile.count();
                for(int i = 0; i < size; i++)
                {
                    drawPile.gain(discPile.deal());
                }
                return true;
            }
            else
            {
                System.out.println(name + " was ELIMINATED!\n");
                return false;
            }
        }

        return true;
    }

    //player get card
    public Card handCardAt(int i)
    {
        return drawPile.getCard(i);
    }
    //palyer getters
    public String getName()
    {
        return name;
    }

    public int handSize()
    {
        return drawPile.count();
    }

    public void printHand()
    {
        //to use method
        hand = checkHand()[0];
        high = checkHand()[1];
        highcard = toName(high);

        if(hand == 8)
        {
            System.out.println("Straight Flush (" + highcard + ") high");
        }
        else if(hand == 7)
        {
            System.out.println("Four of a kind (" + highcard + ")");
        }
        else if(hand == 6)
        {
            System.out.println("Full House (" + highcard + ") high");
        }
        else if(hand == 5)
        {
            System.out.println("Flush (" + highcard + ") high");
        }
        else if(hand == 4)
        {
            System.out.println("Straight (" + highcard + ") high");
        }
        else if(hand == 3)
        {
            System.out.println("Three of a Kind (" + highcard + ")");
        }
        else if(hand == 2)
        {
            System.out.println("Two Pair (" + highcard + ") high");
        }
        else if(hand == 1)
        {
            System.out.println("One Pair (" + highcard + ")");
        }
        else if(hand == 0)
        {
            System.out.println("High Card (" + highcard + ") high");
        }
    }

    public int getHand()
    {
        return this.hand;
    }

    public int getHigh()
    {
        return this.high;
    }

    private void setHnds()
    {

        //populate arrays
        for(int i = 0; i < 5; i++)
        {
            ranks[i]= drawPile.getCard(i).getRank();
            suits[i]= drawPile.getCard(i).getSuit();
        }

    }

    public int strat(Deck deck)
    {
        ArrayList<Integer> discs = new ArrayList<Integer>();

        setHnds();
        int holder = 0;
        //pick discards

        for (int i = 0; i < 5; i++)
        {
            if((amountIn(ranks, ranks[i]) < 2) && (ranks[i] < 12))
            {
                discs.add(i);
                holder++;
            }
        }

        discs.sort(Collections.reverseOrder());

        //remove from array
        for(int i = 0; i < discs.size()-1; i++)
        {
            gainDisc(draw(discs.get(i)));
        }

        for(int i = 0; i < discs.size()-1; i++)
        {
            gainDraw(deck.deal());
        }

        return holder;
    }

    public int[] checkHand()
    {
        setHnds();

        boolean cont = true;
        boolean sfStore = false;
        int starer = getSmallest(ranks);
        if(cont=true)
        {
            //straight flush
            //if all suits same cont
            //if not aint a sf
            //if consec rank return 8
            if(amountIn(suits,"Spades ♠") == 5)
                sfStore = true;
            else if(amountIn(suits,"Clubs ♣") == 5)
                sfStore = true;
            else if(amountIn(suits,"Hearts ♥") == 5)
                sfStore = true;
            else if(amountIn(suits,"Diamonds ♦") == 5)
                sfStore = true;

            if(sfStore)
            {
                for(int i = 0; i < 5; i++)
                {
                    if(amountIn(ranks,(starer + i)) == 1)
                    {
                        if(i == 4)
                            return new int[]{8, getMax(ranks)};
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        //four of a kind
        //if four cards are same return 7
        for(int i = 2; i <= 14; i++)
        {
            if(amountIn(ranks,i) == 4)
            {
                return new int[]{7, i};

            }
        }
        //full house
        //if 3 cards equal
        //and 2 cards equal
        //return 6
        for(int i = 2; i <= 14; i++)
        {
            if(amountIn(ranks,i) == 3)
            {
                for(int j = 2; j <= 14; j++)
                {
                    if(amountIn(ranks,j) == 2)
                    {
                        return new int[] {6, j};

                    }
                }
            }
        }

        //flush
        //if 5 cards have same suit
        //return 5
        if(amountIn(suits,"Spades ♠") == 5)
            return new int[]{5, getMax(ranks)};
        else if(amountIn(suits,"Clubs ♣") == 5)
            return new int[]{5, getMax(ranks)};

        else if(amountIn(suits,"Hearts ♥") == 5)
            return new int[]{5, getMax(ranks)};
        else if(amountIn(suits,"Diamonds ♦") == 5)
            return new int[]{5, getMax(ranks)};

        int storer = getSmallest(ranks);
        //straight
        //if 5 cards have consec rank
        //return 4
        for(int i = 0; i < 5; i++)
        {
            if(amountIn(ranks,(storer + i)) == 1)
            {
                if(i == 4)
                    return new int[]{4, getMax(ranks)};
            }
            else
            {
                break;
            }
        }

        //three of a kind
        //if 3 cards have same rank
        //return 3
        for(int i = 2; i <= 14; i++)
        {
            if(amountIn(ranks,i) == 3)
            {
                return new int[]{3, i};
            }
        }

        //two pair
        //if two cards have same rank
        //and two different cards have same rank
        //return 2
        for(int i = 2; i <= 14; i++)
        {
            if(amountIn(ranks,i) == 2)
            {
                int saver = i;
                for(int j = 2; j <= 14; j++)
                {
                    if(amountIn(ranks,j) == 2)
                    {
                        if(!(j == saver))
                        {
                            if(i > j)
                                return new int[]{2, i};
                            else
                                return new int[]{2, j};
                        }
                    }
                }
            }
        }

        //one pair
        //if two cards have the same rank
        //return 1
        for(int i = 2; i <= 14; i++)
        {
            if(amountIn(ranks,i) == 2)
            {
                return new int[]{1, i};
            }
        }

        //high card
        //the else
        return new int[]{0, getMax(ranks)};
    }

    public int amountIn(int[] array, int value)
    {
        int n = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i] == value)
                n++;
        }
        return n;
    }

    public int amountIn(String[] array, String value)
    {
        int n = 0;
        for(int i = 0; i < array.length; i++){
            if(array[i].equalsIgnoreCase(value))
                n++;
        }
        return n;
    }

    public int getSmallest(int[] z){
        int[] a = new int[5];
        for(int i = 0; i < 5; i++)
        {
            a[i] = z[i];
        }

        int temp;
        for (int i = 0; i < 5; i++) 
        {
            for (int j = i + 1; j < 5; j++) 
            {
                if (a[i] > a[j]) 
                {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[0];
    }

    public int getMax(int[] z){
        int[] a = new int[5];
        for(int i = 0; i < 5; i++)
        {
            a[i] = z[i];
        }

        int temp;
        for (int i = 0; i < 5; i++) 
        {
            for (int j = i + 1; j < 5; j++) 
            {
                if (a[i] > a[j]) 
                {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        return a[4];
    }

    public String toName(int rank)
    {
        if(rank == 2)
            return "Two";
        else if(rank == 3)
            return "Three";
        else if(rank == 4)
            return "Four";
        else if(rank == 5)
            return "Five";
        else if(rank == 6)
            return "Six";
        else if(rank == 7)
            return "Seven";
        else if(rank == 8)
            return "Eight";
        else if(rank == 9)
            return "Nine";
        else if(rank == 10)
            return "Ten";
        else if(rank == 11)
            return "Jack";
        else if(rank == 12)
            return "Queen";
        else if(rank == 13)
            return "King";
        else if(rank == 14)
            return "Ace";
            
          return "i am suffering";
    }
}
