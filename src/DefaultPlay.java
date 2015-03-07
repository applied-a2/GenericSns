import java.util.*;

public class DefaultPlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private ArrayList<Commodity> shares;
	private ArrayList<Card> cards;
	private Random randomCard; //random generator
	private String[] mainTypes = { "motors", "stores", "steels", "shippings" }; //just loop through this list if needed
																				//instead of declaring each type
	private ShareIndicator shareIndicator;
	
	public static void main(String[] args)
	{
		DefaultPlay defaultPlay = new DefaultPlay();
		defaultPlay.display();
	}

	public DefaultPlay() {
		players = new ArrayList<Player>();
		shares = new ArrayList<Commodity>();
		randomCard = new Random();
		cards = new ArrayList<Card>();
	}
	
	public void setUpPlayers()
	{
		System.out.println("How many players?");
		System.out.print("==>");
		int numPlayers = input.nextInt();
		while(players.size() < numPlayers)
		{
			players.add(new Player(players.size()+1, 80));
		}
	}

	/*
	 * 2 for loops generating the "up" and "down" cards, 
	 * values (2,3,4) will control these loop.
	 * Then add the 2 bonus cards
	 */
	public void setUpCards() {	
		for (String mainType : mainTypes)	
		{
			for (int upValue = 2; upValue <= 4; upValue++)		
			{
				cards.add(new Card(mainType, "up", upValue));
			}
			for (int downValue = 2; downValue <= 4; downValue++) 			
			{
				cards.add(new Card(mainType, "down", downValue));
			}
		}

		cards.add(new Card("bull", "up", 4));
		cards.add(new Card("bear", "down", 4));
		
//		for (String mainType : mainTypes) {
//			int count = 0;
//			for (Card card : cards) {
//				if (mainType.equals(card.getCardType())) {
//					count++;
//				}
//			}
//			System.out.println(count + " " + mainType + " cards");
//		}
//		System.out.println("1 bear card, 1 bull card");
	}

	public void setUpShares() 
	{
		for(String type: mainTypes)
		{
			for(int i = 0; i < 28; i++)
			{
				shares.add(new Commodity(type, 10));
			}	
		}
	}

	public int generateRandomCard()
    {
        return randomCard.nextInt(cards.size());
    }
	
	public void display() {
		setUpPlayers();
		System.out.println(players.size() + " players");
		setUpShares();
		shareIndicator = new ShareIndicator(shares);
		setUpCards();
		Collections.shuffle(cards);	//just mix the cards we just made		
		System.out.println("Number of shares: " + shares.size());
		System.out.println("Setup complete, On screen !");
		
		for(int round = 1; round <= 12; round++)//The main 
																		//loop - round controller - runs from 
																		//round 1 to round 12  
		{
			System.out.println("--------------------");
			if(round != 1)
			{
				Collections.reverse(players);
			}
			System.out.println("Round " + round);
			
			ArrayList<Card> dealtCardsInRound = new ArrayList<Card>(); //This is a list 
																		//of all cards being 
																		//dealt in this turn
			
			for(int i = 0; i < players.size(); i++)	
			{
				Player currentPlayer = players.get(i);		//get player from players list
				System.out.println("Player " + currentPlayer.getOrder());
				
				Card dealtCard = cards.get(generateRandomCard());	//3 steps involved here: get 
				dealtCardsInRound.add(dealtCard);					//an index from random generator,
																	//get card from cards list using that
																	//index, then add that card to the
																	//dealtCardsInRound
				
				currentPlayer.takeCard(dealtCard);			//give the dealt card to player
				System.out.println(dealtCard.toString());	//print out details of the card
				
				boolean playerTurn = true;				
				while(playerTurn)						//player will chose what to do in this loop
				{
					playerTurn = !playerChoice(currentPlayer);	//when player finished, a boolean "true"
																//will be returned, but we must make this
																//boolean "false" to escape the loop
				}
			}
			
			System.out.println("Okay, round " + round 		//After all players have done
					+ " finished, exposing cards ..."); 	//what they want, print out details															
			for(Card card: dealtCardsInRound)				//of all the cards having been dealt 
			{												//in this round
				System.out.println(card.toString());
			}
			
			updateEverything(dealtCardsInRound);
			
			System.out.println("~~~~~~Share Indicator Records~~~~~");
			System.out.println(shareIndicator.toString());	//print the share indicator
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			System.out.println("--------------------");
		}
	}
	
	/*
	 * This method asks player to choose an action (buy, sell or do nothing),
	 * when finished, return "true" (which means player has finished his turn)
	 */
	public boolean playerChoice(Player player)
	{
		boolean finished = true;
		System.out.println("--------------------");
		System.out.println("Choices: ");
		System.out.println("1) Buy");
		System.out.println("2) Sell");
		System.out.println("3) Do nothing");
		int choice = input.nextInt();
		
		switch(choice)
		{
			case 1: finished = giveSharesToPlayer(player);
					break;
			case 2: finished = takeSharesFromPlayer(player);
					break;
			case 3: finished = true;
					break;
			default: System.out.println("Don't have that choice");
		}
		
		System.out.println("Your current balance: " + player.getMoney());
		System.out.println("Total shares: " + shares.size());
		System.out.println("Your shares: " + player.getShareHolds().size());
		System.out.println("--------------------");
		return finished;
	}
	
	/*
	 * This method take a commodity 
	 * type entered by player, then
	 * check if main types list contains that choice
	 */
	public String getChosenCommodityTypeFromPlayer(int count)
	{
		String commodityTypeToBeBoughtOrSold = "";
		boolean flag = true;
		while(flag)
		{
			boolean hasThatType = false;
			System.out.print("Type "+ count +": ");
			input.nextLine();
			String typeChosenByPlayer = input.nextLine().toLowerCase();			
			for(String commodityType: mainTypes)
			{
				if(commodityType.equals(typeChosenByPlayer))
				{
					hasThatType = true;
				}
			}
			if(hasThatType)
			{
				commodityTypeToBeBoughtOrSold = typeChosenByPlayer;
				flag = false;
			}
			else
			{
				System.out.print("Don't have that, main types are: ");
				for(String type: mainTypes)
				{
					System.out.print(type + "\t");
				}
				System.out.println("Try again ...");
			}
		}
		return commodityTypeToBeBoughtOrSold;
	}
	
	
	/*
	 * When player chooses to buy share, this method is called
	 */
	public boolean giveSharesToPlayer(Player player)
	{
		System.out.println("How many types of share do you want to buy?");
		int numType = input.nextInt();
		int count = 1;
		
		ArrayList<Commodity> sharesToBeTakenAwayFromStack = new ArrayList<Commodity>();
		
		while(numType > 0)
		{
			String commodityTypeToBeBought = getChosenCommodityTypeFromPlayer(count);
			System.out.println("How many shares of " + commodityTypeToBeBought + " ?");
			int amount = input.nextInt();
			ArrayList<Commodity> sharesToBeGiven = player.buyShares(shares, commodityTypeToBeBought, amount);
			sharesToBeTakenAwayFromStack.addAll(sharesToBeGiven);
			
			if(sharesToBeGiven.isEmpty())
			{
				System.out.println("You don't have enough money, your current balance is " + player.getMoney() );
			}
			
			count++;
			numType--;
		}
		
		shares.removeAll(sharesToBeTakenAwayFromStack);
		
		System.out.println("Finished transaction ? (y/n)");
		char answer = input.next().charAt(0);
		if((answer == 'y')||(answer == 'Y'))
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	/*
	 * When player chooses to sell share, this method is called
	 */
	public boolean takeSharesFromPlayer(Player player)
	{
		System.out.println("How many types of share do you want to sell?");
		int numType = input.nextInt();
		int count = 1;
		while(numType > 0)
		{
			String commodityTypeToBeSold = getChosenCommodityTypeFromPlayer(count);
			System.out.println("How many share of " + commodityTypeToBeSold + " ?");
			int amount = input.nextInt();
			ArrayList<Commodity> sharesToBeAdded = player.sellShares(commodityTypeToBeSold, amount);
			shares.addAll(sharesToBeAdded);
			count++;
			numType--;
		}
		return true;
	}
	
	/*
	 * This method takes in a list of cards having been dealt during current round.
	 * Extract this list, update TWO THINGS: the big shares list and each player's shares list 
	 * basing on each card.
	 */
	public void updateEverything(ArrayList<Card> dealtCardsInRound)
	{
		for(Card dealtCard: dealtCardsInRound) 
		{
			String cardType = dealtCard.getCardType();
			String cardFunction = dealtCard.getCardFunction();
			int cardValue = dealtCard.getCardValue();
			
			if(cardFunction.equals("down")) {
				cardValue = -cardValue;
			}
			
			for(Commodity share: shares) {
				if(share.getCommodityType().equals(cardType)||
						cardType.equals("bull")||cardType.equals("bear"))
				{
					share.updateValue(cardValue);
				}
			}
			
			for(Player player: players) {
				for(Commodity shareOfPlayer: player.getShareHolds())
				{
					if(shareOfPlayer.getCommodityType().equals(cardType)||
							cardType.equals("bull")||cardType.equals("bear"))
					{
						//System.out.println("Old value: " + shareOfPlayer.getValue());
						shareOfPlayer.updateValue(cardValue);
						//System.out.println("New value: " + shareOfPlayer.getValue());
					}
				}
			}
			
//			for(Commodity shareRecord: shareIndicator.getshareRecords()){
//				if(shareRecord.getCommodityType().equals(cardType)||
//						cardType.equals("bull")||cardType.equals("bear"))
//				{
//					shareRecord.updateValue(cardValue);
//				}
//			}
		}
	}
}
