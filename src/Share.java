import java.util.*;

public class Share {
	
	private String shareName;
	private String commodityType;
	private ArrayList<Commodity> commodities;
	
	public Share(String shareName, String commodityType, int value, int amount)
	{
		this.shareName = shareName;
		this.commodityType = commodityType;
		commodities = new ArrayList<Commodity>();
		while(commodities.size() < amount)
		{
			commodities.add(new Commodity(commodityType, value));
		}
	}
	
	public String getType()
	{
		return commodityType;
	}
	
	public ArrayList<Commodity> getShares()
	{
		return commodities;
	}
	
	public void updateCommodityStatus(int value)
	{
		for(Commodity commodity: commodities)
		{
			commodity.updateValue(value);
		}
	}
}
