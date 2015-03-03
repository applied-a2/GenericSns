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
	
	public void buyShares(ArrayList<Commodity> shares)
	{
		shareHolds = shares;
	}
	
	/*A method to sell share(s) from the shares array list
	 */
}
