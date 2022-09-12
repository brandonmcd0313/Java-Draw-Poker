// Brandon McDonald
// Date: 3-2-22
// Purpose: Create a deck of cards and shuffle it.

import java.util.*; //import all util classes

public class Tester //class header
{
    //For user input Java, we need to create a SCANNER object
    static Scanner sc = new Scanner(System.in);
    static int playerCount;
    public static void main(String[] args) throws InterruptedException //method header
    {
        boolean yez = true;
        while(yez)
        {
            Deck deck = new Deck();
            deck.createDeck();
            deck.shuffleDeck();

            Deck discard = new Deck();

            boolean cont = true; int playerCount = 0;
            while(cont)
            {
                //allow the user to choose a number of players
                System.out.print("How many players are playing?: ");
                playerCount = Integer.parseInt(sc.nextLine());
                if(playerCount >= 2 && playerCount <= 5)
                {
                    break;
                }
                else
                {
                    System.out.println("try again.");
                }
            }

            //create a waring players array list for later]
            ArrayList <Player> wars = new ArrayList <Player>();

            //create the players and their arraylist
            ArrayList <Player> players = new ArrayList <Player>();

            for(int i = 1; i <= playerCount; i++)
            {
                players.add(new Player(i));
            }

            //deal the cards to the players
            int playerCounter = 0;
            for(int i = 0; i < 5; i++) //cards
            {
                for(int j = 0; j < players.size(); j++)
                {
                    players.get(playerCounter).gainDraw(deck.deal());
                    playerCounter++;
                }
                playerCounter = 0;
            }

            //player 0 is the user
            System.out.println(players.get(0).getName() + " cards: ");
            for(int i = 0; i < players.get(0).handSize(); i++)
            {
                System.out.print((i+1) + ") ");
                players.get(0).handCardAt(i).print();
            }

            players.get(0).printHand();
            //get disc amount
            int disc;
            while(true)
            {
                System.out.println("how many cards will you discard?");
                disc = Integer.parseInt(sc.nextLine());
                if(disc >= 0 && disc <= 5)
                    break;
                else
                    System.out.println("try again.");
            }

            System.out.println("Select " + disc + " cards to discard");
            ArrayList<Integer> discs = new ArrayList<Integer>(disc);
            for(int i = 0; i < disc; i++)
            {
                int holder = Integer.parseInt(sc.nextLine());
                if(holder <= 5 &&  holder >= 1 && !inArray(holder,discs))
                {
                    discs.add(holder);
                }
                else
                {
                    System.out.println("try again.");
                    i--;
                }

            }

            //sort greatest to least
            discs.sort(Collections.reverseOrder());

            //remove from array
            for(int i = 0, store = discs.size(); i < disc; i++)
            {
                discard.gain(players.get(0).draw(discs.get(i)-1));
            }

            for(int i = 0; i < disc; i++)
            {
                players.get(0).gainDraw(deck.deal());
            }

            //ai will happen here
            for(Player player: players)
            {
                if(!(player.getName().equalsIgnoreCase("Player 1")))
                {
                    System.out.println(player.getName() + " drew " + player.strat(deck) + " cards!");
                }
            }
            System.out.println();
            //get da ranks
            for(Player player : players)
            {
                System.out.println(player.getName() + " cards: ");
                for(int i = 0; i < player.handSize(); i++)
                {
                    System.out.print((i+1) + ") ");
                    player.handCardAt(i).print();
                }
                player.printHand();
                System.out.println();
            }

            ArrayList <Integer> hands = new ArrayList <Integer>();
            ArrayList <Integer> highcards = new ArrayList <Integer>();
            for(int i = 0; i < playerCount; i++)
            {
                //create array of hand ranks
                hands.add(players.get(i).getHand());
                //create array of high cards
                highcards.add(players.get(i).getHigh());
            }

            //find highest hand
            int startMax = getMax(hands,playerCount);
            int maxIndex = hands.indexOf(startMax);
            //remove it
            hands.remove(maxIndex);
            //if another hand of that still exists
            if(hands.indexOf(startMax)!= -1)
            {
                //compare high cards
                if(players.get(maxIndex).getHigh() < players.get(hands.indexOf(startMax)+1).getHigh())
                {
                    System.out.println("Results: " + players.get(hands.indexOf(startMax)+1).getName() + " wins the hand!"); 
                }
                else
                {
                    System.out.println("Results: " + players.get(maxIndex).getName() + " wins the hand!");
                }
            }
            //if not equal they win!
            else
            {
                System.out.println("Results: " + players.get(maxIndex).getName() + " wins the hand!");
            }
            //play again stuff
            System.out.println("Would you like to play again? (y/n)");
            String choice = sc.nextLine();
            while(true)
            {
                if(choice.equalsIgnoreCase("y"))
                {
                    yez = true;
                    break;
                    }
                else if(choice.equalsIgnoreCase("n"))
                {
                    yez = false;
                    break;
                }
                else
                {
                    System.out.println("try again.");
                    choice = sc.nextLine();
                }
            }
        }
    }

    static boolean inArray(int toCheckValue, ArrayList<Integer> array)
    {for (int i : array) {
            if (i == toCheckValue) {

                return true;
            }
        }
        return false;
    }

    static int getMax(ArrayList <Integer> z, int playerCount){
        int[] b = new int[playerCount];
        for(int i = 0; i < playerCount; i++)
        {
            b[i] = z.get(i);
        }

        int temp;
        for (int i = 0; i < playerCount; i++) 
        {
            for (int j = i + 1; j < playerCount; j++) 
            {
                if (b[i] > b[j]) 
                {
                    temp = b[i];
                    b[i] = b[j];
                    b[j] = temp;
                }
            }
        }

        return b[(playerCount-1)];
    }
} //end of class header

