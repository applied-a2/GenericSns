import java.util.*;
import java.util.Random;



public class DefaultPlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Commodity> shares;
	private ArrayList<Card> cards;
	private Random randomCard;
	
	
	
	
	public static void main(String[] args)
	{
		DefaultPlay defaultPlay = new DefaultPlay();
		defaultPlay.display();
	}

	public DefaultPlay() {
		players = new ArrayList<Player>();
		shares = new ArrayList<Commodity>();
		setUpShares();
		cards = new ArrayList<Card>(); // (Thai): I forgot this line, sorry ^^
		// cards.addAll(getMotorCards());
		// cards.addAll(getShippingCards());
		// cards.addAll(getSteelCards());// changed to steel
		// cards.addAll(getStoreCards());
		randomCard = new Random();
		cards = new ArrayList<Card>(); //(Thai): I forgot this line, sorry ^^
		//cards.addAll(getMotorCards());
		//cards.addAll(getShippingCards());
		//cards.addAll(getSteelCards());// changed to steel
		//cards.addAll(getStoreCards());
		setUpCards();
	}

	public void display() {
		System.out.println("Setup complete, On screen !");
		System.out.println("Number of shares: " + shares.size() );
		String[] mainTypes = { "motors", "stores", "steels", "shippings" };
		for (String mainType : mainTypes) {
			int count = 0;
			for (Card card : cards) {
				if (mainType.equals(card.getCardType())) {
					count++;
				}
			}
			System.out.println(count + " " + mainType + " cards");
		}
	}

	public void setUpCards() {
		String[] mainTypes = { "motors", "stores", "steels", "shippings" }; // a
																			// primitive
																			// array
																			// list
																			// containing
																			// 4
																			// main
																			// types
																			// of
																			// cards
		for (String mainType : mainTypes) // loop through the list, so we don't
											// have to declare every type
		{
			for (int upValue = 2; upValue <= 4; upValue++) // generates the "up"
															// cards, values
															// (2,3,4) will
															// control this loop
			{
				cards.add(new Card(mainType, "up", upValue));
			}
			for (int downValue = 2; downValue <= 4; downValue++) // same as
																	// above,
																	// generates
																	// the
																	// "down"
																	// cards
			{
				cards.add(new Card(mainType, "down", downValue));
			}
		}

		cards.add(new Card("bull", "up", 4)); // add the 2 bonus cards
		cards.add(new Card("bear", "down", 4));
	}

	public void setUpShares() {
		while (shares.size() < 27) {
			Commodity motors = new Commodity("motors", 10);
			shares.add(motors);
		}
	}

	

	public int generateRandomCard()
    {
        
        int index = randomCard.nextInt(cards.size());
        
        return index;
    }
}
