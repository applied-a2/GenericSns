import java.util.*;

public class SimplePlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private ArrayList<Share> shares;
	
	public static void main(String[] args)
	{
		SimplePlay play = new SimplePlay();
		play.display();
	}
	
	public SimplePlay()
	{
		players = new ArrayList<Player>();
		shares = new ArrayList<Share>();
		cards = new ArrayList<Card>();
	}
	
	public void display()
	{
		int numPlayers = getPlayers();
		setUpShares();
		setUpCards();
		
	}
	
	public int getPlayers()
	{
		System.out.println("How many players?");
		System.out.print("==>");
		int numPlayers = input.nextInt();
		return numPlayers;
	}
	
	public void setUpShares()
	{
		System.out.println("How many type of commodity ?");
		int numTypes = input.nextInt();
		System.out.println("How many share for each ?");
		int numSharesEachCommoType = input.nextInt();
		System.out.println("Value for each share ?");
		int value = input.nextInt();
		int typeCount = 1;
		while(numTypes > 0)
		{
			System.out.print("Type " + typeCount + " : ");
			input.nextLine();
			String commodityType = input.nextLine();
			String shareName = commodityType + "Share";
			shares.add(new Share(shareName, commodityType, value, numSharesEachCommoType));
			numTypes--;
			typeCount++;
		}
	}
	
	public void setUpCards()
	{
		System.out.println("How many type of cards?");
		int numCardTypes = input.nextInt();
		int typeCount = 1;
		while (numCardTypes > 0)
		{
			System.out.println("Type " + typeCount + " : ");
			input.nextLine();
			String cardType = input.nextLine();
			System.out.println("Amount for " + cardType + " : ");
			int amount = input.nextInt();
			System.out.println("Value for each card: ");
			int value = input.nextInt();
			while(cards.size() < (cards.size() + amount))
			{
				cards.add(new Card(cardType, value));
			}
			numCardTypes--;
			typeCount++;
		}
	}
}
