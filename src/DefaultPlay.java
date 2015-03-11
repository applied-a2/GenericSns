import java.util.*;

public class DefaultPlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private Shares shares;
	private ArrayList<Card> cards;
	private String[] mainTypes = { "motors", "stores", "steels", "shippings" }; //just loop through this list if needed
	private int turn = 0;
	public static void main(String[] args)
	{
		DefaultPlay defaultPlay = new DefaultPlay();
		defaultPlay.display();
	}

	public DefaultPlay() {
		players = new ArrayList<Player>();
		shares = new Shares(mainTypes, 28);
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

	public void display() {
		setUpPlayers();
		System.out.println(players.size() + " players");
		
		
		//shareIndicator = new ShareIndicator(shares);
		setUpCards();
		Collections.shuffle(cards);	//just mix the cards we just made		
		System.out.println("Number of shares: " + shares.getShares().size());
		System.out.println("Setup complete, On screen !");
		
		for(int round = 1; round <= 12; round++)//The main 
																		//loop - round controller - runs from 
																		//round 1 to round 12  
		{
			System.out.println("--------------------");
			if(round != 1)
			{
				int turn = players.size();			
				Collections.rotate(players,turn-1);
			}
			System.out.println("Round " + round);
			
			int[] dealtCardIndexInRound = new int[players.size()]; 
			
			for(int i = 0; i < players.size(); i++)	
			{
				Player currentPlayer = players.get(i);		//get player from players list
				System.out.println("Player " + currentPlayer.getIdentity());
				
				int randomCardIndex = RandomGenerator.randomInt(cards.size());
				dealtCardIndexInRound[i] = randomCardIndex;
				
				System.out.println(cards.get(randomCardIndex).toString());	//print out details of the card
				
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
			for(int index: dealtCardIndexInRound)				//of all the cards having been dealt 
			{												//in this round
				System.out.println(cards.get(index).toString());
			}
			
			updateShares(dealtCardIndexInRound);
			
			System.out.println("~~~~~~Share Indicator Records~~~~~");
			System.out.println(shares.shareIndicator(mainTypes));	//print the share indicator
			System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
			
			System.out.println("--------------------");
		}
		
		sellAll();
		
		System.out.println("======> Result <======");
		int highestMoneyAmount = 0;
		String winner = "";
		for(Player player: players)
		{
			System.out.println("|| " + player.toString());
			if(player.getMoney() >= highestMoneyAmount)
			{
				winner = "Player " + player.getIdentity();
			}
		}
		System.out.println("Winner: " + winner);
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
			case 1: finished = buyShares(player);
					break;
			case 2: finished = sellShares(player);
					break;
			case 3: finished = true;
					break;
			default: System.out.println("Don't have that choice");
		}
		
		System.out.println("Your current balance: " + player.getMoney());
		System.out.println("Total shares: " + shares.getShares().size());
		System.out.println("Your shares: " + player.getShareIds().size());
		System.out.println("--------------------");
		return finished;
	}
	
	public void printMainTypes()
	{
		int countPrint = 0;
		for(String commodityType: mainTypes)
		{
			System.out.println("<" + countPrint + "> " + commodityType.toString());
			countPrint++;
		}
	}
	
	/*
	 * This method take a commodity 
	 * type entered by player, then
	 * check if main types list contains that choice
	 */
	public String getChosenCommodityTypeFromPlayer()
	{
		String commodityTypeToBeBoughtOrSold = "";
		boolean flag = true;
		while(flag)
		{
			//boolean hasThatType = false;			
			System.out.print("===>");
			int typeNumber = input.nextInt();
			
			if((typeNumber < mainTypes.length)&&(typeNumber >= 0))		
			{
				commodityTypeToBeBoughtOrSold = mainTypes[typeNumber];
				//hasThatType= true; 
				flag = false;
			}
			else
			{
				System.out.println("Don't have that, try again ...");
			}
		}
		return commodityTypeToBeBoughtOrSold;
	}
	
	public boolean buyShares(Player currentPlayer)
	{
		printMainTypes();
		String commodityTypeToBeBought = getChosenCommodityTypeFromPlayer();
		System.out.println("How many " + commodityTypeToBeBought + "?");
		int shareNum = input.nextInt();
		ArrayList<Long> availableShareIds = shares.getAvailableShareIds(commodityTypeToBeBought);
		int moneyToPay = shares.getShareValueOnType(commodityTypeToBeBought) * shareNum;
		
		if(currentPlayer.getMoney() < moneyToPay)
		{
			System.out.println("You don't have enough money");
			return false;
		}
		else if(availableShareIds.size() < shareNum)
		{
			System.out.println("There are not enough shares for you");
			return false;
		}
		else
		{
			for(int i = 0; i < shareNum; i++)
			{
				currentPlayer.addShareId(availableShareIds.get(i));
				shares.giveShareToPlayer(availableShareIds.get(i), currentPlayer.playerId());
			}
			currentPlayer.setMoney(currentPlayer.getMoney() - moneyToPay);
			System.out.println("Purchase completed");
		}
		
		boolean playerConfirm = confirmFinished();
		return playerConfirm;
	}
	
	public boolean sellShares(Player currentPlayer)
	{
		printMainTypes();
		String commodityTypeToBeSold = getChosenCommodityTypeFromPlayer();
		System.out.println("How many " + commodityTypeToBeSold + "?");
		int shareNum = input.nextInt();
		
		ArrayList<Long> shareIdsBoughtByPlayer 
				= shares.getSoldShareIds(currentPlayer.playerId(), commodityTypeToBeSold);
		
		if(shareIdsBoughtByPlayer.size() < shareNum)
		{
			System.out.println("You haven't bought that many " + commodityTypeToBeSold + " !");
			return false;
		}
		else
		{
			int moneyToReceive = 0;
			for(int i = 0; i < shareNum; i++)
			{
				currentPlayer.removeShareId(shareIdsBoughtByPlayer.get(i));
				shares.takeShareFromPlayer(shareIdsBoughtByPlayer.get(i));
				moneyToReceive += shares.getShareValueOnType(commodityTypeToBeSold);
			}
			currentPlayer.setMoney(currentPlayer.getMoney() + moneyToReceive);
			System.out.println("Sell completed");
		}
		
		boolean playerConfirm = confirmFinished();
		return playerConfirm;
	}
	
	public boolean confirmFinished()
	{
		System.out.println("Finished ? (y/n)");
		System.out.print("===>");
		char answer = input.next().toLowerCase().charAt(0);
		if(answer == 'y')
		{
			return true;
		}
		return false;
	}
	
	public void updateShares(int[] dealtCardIndexInRound)
	{
		for(int i = 0; i < dealtCardIndexInRound.length; i++)
		{
			Card card = cards.get(dealtCardIndexInRound[i]);
			String cardType = card.getCardType();
			String cardFunction = card.getCardFunction();
			int cardValue = card.getCardValue();
			
			if(cardFunction.equals("down"))
			{
				cardValue = -cardValue;
			}
			
			for(String shareType: mainTypes)
			{
				if(shareType.equals(cardType)
						||cardType.equals("bear")||cardType.equals("bull"))
				{
					shares.updateOneTypeOfShare(shareType,cardValue);
				}
			}
		}
	}
	
	public void sellAll()
	{
		for(Player player: players)
		{
			int moneyToReceive = 0;
			for(Long shareId: player.getShareIds())
			{
				shares.takeShareFromPlayer(shareId);
				moneyToReceive += shares.getShareValueOnId(shareId);
			}
			player.setMoney(player.getMoney() + moneyToReceive);
		}
	}
}
