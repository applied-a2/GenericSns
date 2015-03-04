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
