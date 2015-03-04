import java.util.*;

public class defaultPlay {

	//private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Commodity> motorShares;
	private ArrayList<Commodity> shippingShares;
	private ArrayList<Commodity> steelShares;
	private ArrayList<Commodity> storeShares;
	private ArrayList<Card> cards;
	
	public defaultPlay()
	{
		players = new ArrayList<Player>();
		motorShares = new ArrayList<Commodity>();
		shippingShares = new ArrayList<Commodity>();
		steelShares = new ArrayList<Commodity>();
		storeShares = new ArrayList<Commodity>();
		cards.addAll(getMotorCards());
		cards.addAll(getShippingCards());
		cards.addAll(getStoreCards());
		cards.addAll(getStoreCards());
	}
	
	public ArrayList<Card> getMotorCards()
	{
		ArrayList<Card> motorCards = new ArrayList<Card>();
		for(int upValue = 2; upValue <= 4; upValue++)
		{
			motorCards.add(new Card("motors", "up", upValue));
		}
		for(int downValue = 2; downValue <= 4; downValue++)
		{
			motorCards.add(new Card("motors", "down", downValue));
		}
		return motorCards;
	}
	
	public ArrayList<Card> getShippingCards()
	{
		ArrayList<Card> shippingCards = new ArrayList<Card>();
		for(int upValue = 2; upValue <= 4; upValue++)
		{
			shippingCards.add(new Card("shippings", "up", upValue));
		}
		for(int downValue = 2; downValue <= 4; downValue++)
		{
			shippingCards.add(new Card("shippings", "down", downValue));
		}
		return shippingCards;
	}
	
	public ArrayList<Card> getStoreCards()
	{
		ArrayList<Card> storeCards = new ArrayList<Card>();
		for(int upValue = 2; upValue <= 4; upValue++)
		{
			storeCards.add(new Card("stores", "up", upValue));
		}
		for(int downValue = 2; downValue <= 4; downValue++)
		{
			storeCards.add(new Card("stores", "down", downValue));
		}
		return storeCards;
	}
	
	public ArrayList<Card> getSteelCards()
	{
		ArrayList<Card> steelCards = new ArrayList<Card>();
		for(int upValue = 2; upValue <= 4; upValue++)
		{
			steelCards.add(new Card("steels", "up", upValue));
		}
		for(int downValue = 2; downValue <= 4; downValue++)
		{
			steelCards.add(new Card("steels", "down", downValue));
		}
		return steelCards;
	}
	
	public void setUpShares()
	{
	while (motorShares.size() < 27){
		Commodity motors = new Commodity("motors", 10);
		motorShares.add(motors);
	}
	while (shippingShares.size() < 27){
		Commodity shipping = new Commodity("shipping", 10);
		shippingShares.add(shipping);
	}
	while (steelShares.size() < 27){
		Commodity steel = new Commodity("steel", 10);
		steelShares.add(steel);
	}
	while (storeShares.size() < 27){
		Commodity store = new Commodity("store", 10);
		storeShares.add(store);
	}
	
	}
}
