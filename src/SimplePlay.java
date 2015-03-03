import java.util.*;

public class SimplePlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Card> cards;
	private ArrayList<Commodity> shares;
	
	public static void main(String[] args)
	{
		SimplePlay play = new SimplePlay();
		play.display();
	}
	
	public SimplePlay()
	{
		players = new ArrayList<Player>();
		shares = new ArrayList<Commodity>();
		cards = new ArrayList<Card>();
	}
	
	public void display()
	{
		setUpPlayers();
		setUpShares();
		setUpCards();
		System.out.println("Done");
		
	}
	
	public void setUpPlayers()
	{
		System.out.println("How many players?");
		System.out.print("==>");
		int numPlayers = input.nextInt();
		System.out.print("Initial amount of money for each player: ");
		int money = input.nextInt();
		while(players.size() < numPlayers)
		{
			players.add(new Player(players.size(), money));
		}
	}
	
	public void setUpShares()
	{
		System.out.println("How many type of commodity ?");
		int numTypes = input.nextInt();
		System.out.println("How many share for each stock?");
		int numShareEachCommoType = input.nextInt();
		System.out.println("Value for each share ?");
		int value = input.nextInt();
		int typeCount = 1;
		while(numTypes > 0)
		{
			System.out.print("Type " + typeCount + " : ");
			input.nextLine();
			String commodityType = input.nextLine();
			for(int i = 0; i < numShareEachCommoType; i++)
			{
				shares.add(new Commodity(commodityType, value));
			}	
			numTypes--;
			typeCount++;
		}
	}
	
	public void setUpCards()
	{
		int numValue = 0;
		int numCardEachShareType = 0;
		boolean asking = true; 
		while(asking)
		{	
			System.out.println("How many values for a type of card");
			numValue = input.nextInt();
			System.out.println("How many cards for each share type?");
			numCardEachShareType = input.nextInt();
			if(numCardEachShareType%numValue == 0)
			{
				asking = false;
			}
			else
			{
				System.out.println("Number of values must be "
						+ "divisible by number of cards for each type");
			}
		}
		
		
		ArrayList<String> shareTypes = getShareTypes();
		int numMainCardType = shareTypes.size();
		while(numMainCardType > 0)
		{
			for(int i = 0; i < numCardEachShareType; i++)
			{
				
			}
			numMainCardType--;
		}
		
		for(int i = 0; i < numValue; i++)
		{
			System.out.print("Value " + (i+1) + " : ");
			int value = input.nextInt();
		}
	}
	
	public ArrayList<String> getShareTypes()
	{
		ArrayList<String> shareTypes = new ArrayList<String>();
		int numShareType = 0;
		String shareType = "";
		for(Commodity commodity: shares)
		{
			if(!commodity.getCommodityType().equals(shareType))
			{
				shareTypes.add(commodity.getCommodityType());
				numShareType++;
			}
		}
		return shareTypes;
	}
}
