import java.util.*;
import java.util.Random;
import java.util.HashMap;
import java.util.Iterator;


public class DefaultPlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Commodity> motorShares;
	private ArrayList<Commodity> shippingShares;
	private ArrayList<Commodity> steelShares;
	private ArrayList<Commodity> storeShares;
	private ArrayList<Card> cards;
	private Random randomCard;
	private ArrayList<Card> randomCards;
	private HashMap<Player, Card> HoldingCards;
	public static void main(String[] args)
	{
		DefaultPlay defaultPlay = new DefaultPlay();
		defaultPlay.display();
	}
	
	public DefaultPlay()
	{
		players = new ArrayList<Player>();
		motorShares = new ArrayList<Commodity>();
		shippingShares = new ArrayList<Commodity>();
		steelShares = new ArrayList<Commodity>();
		storeShares = new ArrayList<Commodity>();
		setUpShares();
		randomCards = new ArrayList<Card>();
		HoldingCards = new HashMap<Player, Card>();
		randomCard = new Random();
		cards = new ArrayList<Card>(); //(Thai): I forgot this line, sorry ^^
		//cards.addAll(getMotorCards());
		//cards.addAll(getShippingCards());
		//cards.addAll(getSteelCards());// changed to steel
		//cards.addAll(getStoreCards());
		setUpCards();
	}
	
	public void display()
	{
		System.out.println("Setup complete, On screen !");
		System.out.println("Number of shares: " + motorShares.size() + " " + motorShares.get(0).getCommodityType()
				+ "\n" + shippingShares.size() + " " + shippingShares.get(0).getCommodityType()
				+ "\n" + steelShares.size() + " " + steelShares.get(0).getCommodityType()
				+ "\n" + storeShares.size() + " " + storeShares.get(0).getCommodityType());
		String[] mainTypes = {"motors", "stores" ,"steels", "shippings"};
		for(String mainType: mainTypes)
		{
			int count = 0;
			for(Card card: cards)
			{
				if(mainType.equals(card.getCardType()))
				{
					count++;
				}
			}
			System.out.println(count + " " + mainType + " cards");
		}
	}
	
	public void setUpCards()
	{
		String[] mainTypes = {"motors", "stores" ,"steels", "shippings"}; // a primitive array list containing 4 main types of cards
		for(String mainType: mainTypes) //loop through the list, so we don't have to declare every type 
		{
			for(int upValue = 2; upValue <= 4; upValue++)	//generates the "up" cards, values (2,3,4) will control this loop
			{
				cards.add(new Card(mainType, "up", upValue));
			}
			for(int downValue = 2; downValue <= 4; downValue++) //same as above, generates the "down" cards
			{
				cards.add(new Card(mainType, "down", downValue));
			}
		}
		
		cards.add(new Card("bull", "up", 4));  //add the 2 bonus cards
		cards.add(new Card("bear", "down", 4));
	}
	
	public void generateRandomCard()
    {
        
        int index = randomCard.nextInt(cards.size());
        randomCards.add(cards.get(index));
    }
	
	 public void generateHoldingCard(HashMap<Player, Card> HoldingCards)
	    {
	        Iterator<Player> it = players.iterator();
	        while(it.hasNext()) {
	        	Player player = it.next();
	        	Card card = randomCards.get(0);
	            
	      }
	   }
	        
	   
	
	public void setUpShares()
	{
	 while (motorShares.size() < 28){
		Commodity motors = new Commodity("motors", 10);
		motorShares.add(motors);
	}
	while (shippingShares.size() < 28){
		Commodity shipping = new Commodity("shipping", 10);
		shippingShares.add(shipping);
	}
	while (steelShares.size() < 28){
		Commodity steel = new Commodity("steel", 10);
		steelShares.add(steel);
	}
	while (storeShares.size() < 28){
		Commodity store = new Commodity("store", 10);
		storeShares.add(store);
	}
	 
	}
	
	public void setUpPlayers()
	{
		System.out.println("How many players?");
		System.out.print("==>");
		int numPlayers = input.nextInt();
		//int money = 80;
		while(players.size() < numPlayers)
		{
			players.add(new Player(players.size() + 1, 80));
		}
		numPlayers++;
		for(Player player: players)
		{
			player.setTurn(numPlayers);
			numPlayers--;
		}
		
	}
	
	
}
