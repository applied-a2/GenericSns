import java.util.*;

public class Player {

	private int order;
	private int money;
	private Card holdingCard;
	private ArrayList<Commodity> shareHolds;
	
	public Player(int order, int money)
	{
		this.order = order;
		this.money = money;
	}
	
	public void takeCard(Card card)
	{
		holdingCard = card;
	}
	
	public Card exposeCard()
	{
		return holdingCard;
	}
	
	public ArrayList<Commodity> buyShares(String commodityTypeToBeBought, int shareNum)
	{ 
		int moneyToPay = 0;
		ArrayList<Commodity> sharesToBeBought = new ArrayList<Commodity>();
		for(Commodity commodity: shareHolds)
		{
			if(commodity.getCommodityType().equals(commodityTypeToBeBought)&&(shareNum>0))
			{
				moneyToPay += commodity.getValue();
				shareHolds.add(commodity);//Adds commodity to the players stack
				sharesToBeBought.add(commodity);
				shareNum--;//For loop will run until numShares reaches 0
			}
		}
		money = money - moneyToPay;//Player pays money
		return sharesToBeBought;//Being returned so it will be taken away from the array as the array will be minus cards
	}
	
	/*A method to sell share(s) from the shares array list
	 */
	public ArrayList<Commodity> sellShares(String commodityTypeToBeSold, int numShares)//The commodity type and the number of shares the player wants to sell
	{
		int moneyToReceive= 0;
		ArrayList<Commodity> sharesToBeSold = new ArrayList<Commodity>();
		for(Commodity commodity: shareHolds)
		{
			if(commodity.getCommodityType().equals(commodityTypeToBeSold)&&(numShares>0))
			{
				moneyToReceive += commodity.getValue();//For each time the loop runs, the value of that commodity will be added to moneyToPay
				shareHolds.remove(commodity);//Takes commodity from the players stack
				sharesToBeSold.add(commodity);
				numShares--;//For loop will run until numShares reaches 0
			}
		}
		money = money + moneyToReceive;//player receives money
		return sharesToBeSold;//Being returned as it will be called upon by the DefaultPlay Class and added to the Array
	}
}
