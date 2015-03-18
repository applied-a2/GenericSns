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
			players.add(new Player(players.size() + 1, money));
		}
	}
	
	public void setUpShares()
	{
		System.out.println("How many type of  commodity ?");
		int numTypes = input.nextInt();
		System.out.println("How many share for each stock?");
		int numShareEachCommoType = input.nextInt();
		System.out.println("Value for each share ?");
		int value = input.nextInt();
		if ((value <= 5) || (value >= 15))
		{
		 		value = 10;               //This method sets the share value to default if it passes the limit 
		 		System.out.print("intial value for each share");
		}
		int typeCount = 1;
		while(numTypes > 0)
		{
			System.out.print("Type " + typeCount + " : ");
			input.nextLine();
			String commodityType = input.nextLine();
			for(int i = 0; i < numShareEachCommoType; i++)
			{
				//shares.add(new Commodity(commodityType, value));
			}	
			numTypes--;
			typeCount++;
		}
	}
	
	public void setUpCards()
	{
		int numValue = 0;
		int numCardEachCommodityType = 0;
		boolean asking = true; 
		while(asking)
		{	
			System.out.println("How many values for one type of card");
			numValue = input.nextInt();
			System.out.println("How many cards for each type?");
			numCardEachCommodityType = input.nextInt();
			if((numCardEachCommodityType%numValue == 0)&&(numCardEachCommodityType%2 == 0))
			{
				asking = false;
			}
			else
			{
				System.out.println("Number of values must be "
						+ "divisible by number of cards for each type");
			}
		}
		int[] mainValues = new int[numValue];
		for(int i = 0; i < numValue; i++)
		{
			System.out.print("Value " + (i+1) + " : ");
			int value = input.nextInt();
			mainValues[i] = value;
		}		
		ArrayList<String> shareTypes = getShareTypes();
		for(String shareType: shareTypes)
		{
			int firstValue = mainValues[0]; //avoid infinite loops below
			for(int upValue = mainValues[0]; upValue < (firstValue + numValue); upValue++)
			{
				cards.add(new Card(shareType, "up", upValue));
			}
			for(int downValue = mainValues[0]; downValue < (firstValue + numValue); downValue++)
			{
				cards.add(new Card(shareType, "down", downValue));
			}
		}
	}
	
	public ArrayList<String> getShareTypes()
	{
		ArrayList<String> shareTypes = new ArrayList<String>();
		//int numShareType = 0;
		String shareType = "";
		for(Commodity commodity: shares)
		{
			if(!commodity.getCommodityType().equals(shareType))
			{
				shareType = commodity.getCommodityType();
				shareTypes.add(commodity.getCommodityType());
				//numShareType++;
			}
		}
		return shareTypes;
	}
}
