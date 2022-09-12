
public class Card
{
    private String name, suit;
    private int rank;

    //constructor
    public Card(String suit, int rank)
    {
        this.suit = suit;
        this.rank = rank;
        toName(rank); //set name
    }

    //method to turn rank into name
    public void toName(int rank)
    {
        if(rank == 2)
            this.name = "Two";
        else if(rank == 3)
            this.name = "Three";
        else if(rank == 4)
            this.name = "Four";
        else if(rank == 5)
            this.name = "Five";
        else if(rank == 6)
            this.name = "Six";
        else if(rank == 7)
            this.name = "Seven";
        else if(rank == 8)
            this.name = "Eight";
        else if(rank == 9)
            this.name = "Nine";
        else if(rank == 10)
            this.name = "Ten";
        else if(rank == 11)
            this.name = "Jack";
        else if(rank == 12)
            this.name = "Queen";
        else if(rank == 13)
            this.name = "King";
        else if(rank == 14)
            this.name = "Ace";
    }

    public void print()
    {
        System.out.println("The " + this.name + " of " + this.suit + ".");
    }

    //getter methods?
    public String getSuit()
    {
        return this.suit;
    }

    public int getRank()
    {
        return this.rank;
    }
}
