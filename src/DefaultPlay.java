import java.util.*;

public class DefaultPlay {

	private Scanner input = new Scanner(System.in);
	private ArrayList<Player> players;
	private Shares shares;
	private ArrayList<Card> cards;
	private String[] mainTypes = { "motors", "stores", "steels", "shippings" }; //just loop through this list if needed
	private boolean buyCompleted = false; //this boolean make sure each player only purchases once
	
	public static void main(String[] args)
	{
		DefaultPlay defaultPlay = new DefaultPlay();
		defaultPlay.display();
	}
	
	/*
	 * Constructor
	 */
	public DefaultPlay() 
	{
		players = new ArrayList<Player>();
		shares = new Shares(mainTypes, 28);
		cards = new ArrayList<Card>();
	}
	
	/*
	 * Set up the player objects basing on the number entered 
	 * by user, add them to the players array list
	 */
	public void setUpPlayers()
	{
		System.out.println("How many players?");
		System.out.print("==>");
		int numPlayers = input.nextInt();
		while(players.size() < numPlayers) {
			players.add(new Player(players.size()+1, 80));
		}
	}

	/*
	 * 2 for loops generating the "up" and "down" cards, 
	 * values (2,3,4) will control these loop.
	 * Then add the 2 bonus cards
	 */
	public void setUpCards() {	
		for (String mainType : mainTypes) {
			for (int upValue = 2; upValue <= 4; upValue++) {
				cards.add(new Card(mainType, "up", upValue));
			}
			for (int downValue = 2; downValue <= 4; downValue++) {
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

	/*
	 * Main driver of the game
	 */
	public void display() {
		setUpPlayers();
		System.out.println(players.size() + " players");
		setUpCards();
		System.out.println("Number of shares: " + shares.getShares().size());
		System.out.println("Setup complete, On screen !");
		
		for(int round = 1; round <= 12; round++)//The main loop-round controller (1 to 12)
		{
			roundDriver(round);
		}
		sellAll();
		
//		System.out.println("======> Result <======");
//		int highestMoneyAmount = 0;
//		String winner = "";
//		for(Player player: players) {
//			System.out.println("|| " + player.toString());
//			if(player.getMoney() >= highestMoneyAmount) {
//				winner = "Player " + player.getIdentity();
//			}
//		}
//		System.out.println("Winner: " + winner);
	}
	
	/*
	 * Run an individual round of the game
	 * @param round number
	 */
	public void roundDriver(int round) {
		Collections.shuffle(cards);									//mix the cards every round		
		System.out.println("--------------------");
		if(round != 1) {
			Collections.rotate(players,players.size()-1);			//player turn controller
		}
		System.out.println("Round " + round);
		
		int[] dealtCardIndexInRound = new int[players.size()]; 
		
		for(int i = 0; i < players.size(); i++)	{
			Player currentPlayer = players.get(i);		//get player from players list
			
			if(!currentPlayer.retired()) {
				System.out.println("Player " + currentPlayer.getIdentity());
				
				int randomCardIndex = RandomGenerator.randomInt(cards.size() - 1); //must be (-1) in case 
																				   //the random index is equal
																				   //to players' size (arraylist out of bound)
				dealtCardIndexInRound[i] = randomCardIndex;
				
				System.out.println(cards.get(randomCardIndex).toString());	//print out details of the card
				
				boolean playerTurn = true;	
				printPlayerShares(currentPlayer);		//print out shares of current player
				
				buyCompleted = false;					//reset buyCompleted boolean
				while(playerTurn)						//player will chose what to do in this loop
				{
					playerTurn = !playerChoice(currentPlayer);	//when player finished, a boolean "true"
																//will be returned, but we must make this
																//boolean "false" to escape the loop
				}
				
				if(currentPlayer.getMoney() == 0 						//if player has no money and no shares
						&& currentPlayer.getShareIds().size() == 0) {	//he is retired
					currentPlayer.setRetired();
				}
			}
		}
		
		System.out.println("Okay, round " + round 		//After all players have done
				+ " finished, exposing cards ..."); 	//what they want, print out details															
		for(int index: dealtCardIndexInRound)				//of all the cards having been dealt 
		{												//in this round
			System.out.println(cards.get(index).toString());
		}
		updateShares(dealtCardIndexInRound);
		printShareIndicator();	
	}
	
	
	/*
	 * This method asks player to choose an action (buy, sell or do nothing),
	 * @return boolean value indicating whether player has finished his turn
	 */
	public boolean playerChoice(Player player)
	{
		boolean finished = true;
		System.out.println("--------------------");
		System.out.println("Choices: ");
		System.out.println("1) Buy");
		System.out.println("2) Sell");
		System.out.println("3) Pass");
		int choice = input.nextInt();
		
		switch(choice) {
			case 1: if(!buyCompleted) {
						finished = buyShares(player);
					}
					else {
						System.out.println("You can buy only once");
						finished = false;
					}
					break;
			case 2: finished = sellShares(player);
					break;
			case 3: finished = true;
					break;
			default: System.out.println("Don't have that choice");
					break;
		}
		System.out.println("Your current balance: " + player.getMoney());
		printPlayerShares(player);
		System.out.println("--------------------");
		return finished;
	}
	
	
	/*
	 * Print out all the share types the game has 
	 */
	public void printMainTypes()
	{
		int countPrint = 0;
		System.out.println("Choose one type:");
		for(String commodityType: mainTypes) {
			System.out.println("<" + countPrint + "> " + commodityType.toString());
			countPrint++;
		}
	}
	
	/*
	 * Print the share indicator (records of shares' values)
	 */
	public void printShareIndicator() {
		System.out.println("~~~~~~Share Indicator Records~~~~~");
		System.out.print(shares.shareIndicator(mainTypes));
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		System.out.println("--------------------");
		System.out.println("Press enter to continue ...");
		input.nextLine();
		input.nextLine();
	}
	
	
	/*
	 * This prints out shares bought by player, 
	 * along with their current value
	 */
	public void printPlayerShares(Player player)
	{
		System.out.println("~~~~~~~~~Player " 
				+ player.getIdentity() + " shares~~~~~~~~~~");
		for(String commodityType: mainTypes) {
			System.out.println("| " + commodityType + "\t" 
					+ shares.getSoldShareIds(player.playerId(), commodityType).size()
					+ " (" + shares.getShareValueOnType(commodityType) + " pound each)");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	/*
	 * This method take a commodity type entered by player, then
	 * check if main types list contains that choice, ask 
	 * again if doesn't
	 * @return the valid commodity type chosen by user
	 */
	public String getChosenCommodityTypeFromPlayer()
	{
		String commodityTypeToBeBoughtOrSold = "";
		boolean flag = true;
		while(flag) {		
			System.out.print("===>");
			int typeNumber = input.nextInt();
			if((typeNumber < mainTypes.length)&&(typeNumber >= 0)) {
				commodityTypeToBeBoughtOrSold = mainTypes[typeNumber];
				flag = false;
			}
			else {
				System.out.println("Don't have that, try again ...");
			}
		}
		return commodityTypeToBeBoughtOrSold;
	}
	
	/*
	 * This method is called when player chooses to buy shares
	 * The transaction will stop immediately when an invalid event happens
	 * (eg. player tries to buy while he does not have enough money) 
	 * @param the current player
	 * @return 
	 */
	public boolean buyShares(Player currentPlayer)
	{
		printMainTypes();
		String commodityTypeToBeBought = getChosenCommodityTypeFromPlayer();
		System.out.println("How many " + commodityTypeToBeBought + "?");
		int shareNum = input.nextInt();
		int refinedShareNum = refineShareNum(shareNum);
		if(refinedShareNum <= 0) {
			System.out.println("Cannot buy negative or zero amount of share");
			return false;
		}
		
		ArrayList<Long> availableShareIds = shares.getAvailableShareIds(commodityTypeToBeBought);
		int moneyToPay = shares.getShareValueOnType(commodityTypeToBeBought) * refinedShareNum;
		
		if(currentPlayer.getMoney() < moneyToPay) {
			System.out.println("You don't have enough money");
			return false;
		}
		else if(availableShareIds.size() < refinedShareNum) {
			System.out.println("There are not enough shares for you");
			return false;
		}
		else {
			if(confirmFinished("Confirm puchase, do you want to change ?")) {
				return false;
			}
			
			for(int i = 0; i < refinedShareNum; i++) {
				currentPlayer.addShareId(availableShareIds.get(i));
				shares.giveShareToPlayer(availableShareIds.get(i), currentPlayer.playerId());
			}
			currentPlayer.setMoney(currentPlayer.getMoney() - moneyToPay);
			System.out.println("Purchase completed");
			buyCompleted = true;
		}
		
		return confirmFinished("Finished?");
	}
	
	/*
	 * Restrict the amount of share entered by player, make sure
	 * it's either 10, 15 or 20 if it's greater than 5. However, 
	 * if the range of 20 is crossed, the number will remain 
	 * unchanged, which will be handled by the former method
	 * @param number entered by player
	 * @return (restricted) number
	 */
	public int refineShareNum(int num)
	{
		//int refinedNum = 1;
		if (num <= 5)  {
			return num;
		}
		for (int i = 5; (i + 5) <= 20; i += 5) {
			if (num == (i+5)) {
				return num;
			}
			else if ((num > i)&&(num < i+5)) {
				num = i + 5;
				System.out.println("If more than 5 shares"
						+ ", \nyou can only buy 10, 15 or "
						+ "20 shares \n ---> amount changed to " + num);
			}
		}
		return num;
	}
	
	/*
	 * This method is called when player chooses to sell his share.
	 * The transaction will stop immediately when an invalid event happens
	 * (eg. player tries to sell what he has not bought) 
	 * @param the current player
	 * @return a boolean indicating whether the transaction is finish 
	 */
	public boolean sellShares(Player currentPlayer)
	{
		printMainTypes();
		String commodityTypeToBeSold = getChosenCommodityTypeFromPlayer();
		ArrayList<Long> shareIdsBoughtByPlayer 
		= shares.getSoldShareIds(currentPlayer.playerId(), commodityTypeToBeSold);
		
		if(shareIdsBoughtByPlayer.size() == 0) {
			System.out.println("You don't have any " + commodityTypeToBeSold + " share");
			return false;
		}
		
		System.out.println("How many " + commodityTypeToBeSold + "?");
		int shareNum = input.nextInt();
		
		if(shareNum <= 0) {
			System.out.println("Cannot sell zero or negative amount of share");
			return false;
		}
		
		if(shareIdsBoughtByPlayer.size() < shareNum) {
			System.out.println("You haven't bought that many " + commodityTypeToBeSold + " !");
			return false;
		}
		else {
			int moneyToReceive = 0;
			for(int i = 0; i < shareNum; i++) {
				currentPlayer.removeShareId(shareIdsBoughtByPlayer.get(i));
				shares.takeShareFromPlayer(shareIdsBoughtByPlayer.get(i));
				moneyToReceive += shares.getShareValueOnType(commodityTypeToBeSold);
			}
			currentPlayer.setMoney(currentPlayer.getMoney() + moneyToReceive);
			System.out.println("Sell completed");
		}
		
		return confirmFinished("Finished?");
	}
	
	/*
	 * Ask for player's confirmation
	 * @param message to print (detail of the confirmation)
	 * @return player's decision of type boolean
	 */
	public boolean confirmFinished(String message)
	{
		System.out.println(message + " (y/n)");
		System.out.print("===>");
		char answer = input.next().toLowerCase().charAt(0);
		return (answer == 'y');
	}
	
	/*
	 * Update the shares values using all the dealt card
	 * in round
	 */
	public void updateShares(int[] dealtCardIndexInRound)
	{
		for(int i = 0; i < dealtCardIndexInRound.length; i++) {
			Card card = cards.get(dealtCardIndexInRound[i]);
			String cardType = card.getCardType();
			String cardFunction = card.getCardFunction();
			int cardValue = card.getCardValue();
			
			if(cardFunction.equals("down")) {
				cardValue = -cardValue;
			}
			
			for(String shareType: mainTypes) {
				if(shareType.equals(cardType)
						||cardType.equals("bear")||cardType.equals("bull")) {
					shares.updateOneTypeOfShare(shareType,cardValue);
				}
			}
		}
	}
	
	/*
	 *This method is called after all rounds 
	 *have finished. All players' shares will be 
	 *sold basing on the final record of the share 
	 *indicator. The final money amount after this will
	 *determine the winner 
	 */
	public void sellAll()
	{
		System.out.println("======> Result <======");
		int highestMoneyAmount = 0;
		String winner = "";		
		for(Player player: players) {
			if(!player.retired()) {
				int moneyToReceive = 0;
				ArrayList<Long> shareIds = new ArrayList<Long>();
				shareIds.addAll(player.getShareIds());
				for(Long shareId: shareIds) {
					player.removeShareId(shareId);
					shares.takeShareFromPlayer(shareId);
					moneyToReceive += shares.getShareValueOnId(shareId);
				}
				player.setMoney(player.getMoney() + moneyToReceive);
				if(player.getMoney() >= highestMoneyAmount) {
					highestMoneyAmount = player.getMoney();
					winner = "Player " + player.getIdentity();
				}
				System.out.println("|| " + player.toString());
			}
			else {
				System.out.println("|| " + player.toString() + " (retired)");
			}
		}
		System.out.println("\n Winner: " + winner);
	}
}
