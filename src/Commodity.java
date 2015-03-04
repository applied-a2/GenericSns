
public class Commodity {

	private String commodityType;
	private int value;
	
	public Commodity(String commodityType, int value)
	{
		this.commodityType = commodityType;
		this.value = value;
	}
	
	public String getCommodityType()
	{
		return commodityType;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public void updateValue(int nvalue)
	{
		if(((value+nvalue) >=0)&&(((value+nvalue) <= 20)))
		{
			value += nvalue;
		}
	}
	
	public void updateCommodityType(String commodityType)
	{
		this.commodityType = commodityType;
	}
}
