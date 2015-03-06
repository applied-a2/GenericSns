
public class ShareIndicator {
	private String commodityType;
	private int value;
	private int roundinPlay;
	 
	public ShareIndicator(String commodityType, int value, int roundinPlay)
	{
		this.commodityType = commodityType;
		this.value = value;
		this.roundinPlay = roundinPlay;
	}
	public String getCommodityType()
	{
		return commodityType;
	}
	public int getValue()
	{
		return value;
	}
	public int RoundInPlay()
	{
		return roundinPlay;
	}

	public void updateValue(int nvalue)
   {
		if(((value+nvalue) >=0)&&((value+nvalue <= 20)))
	  {
			value += nvalue;
	  }
   }
	
    public void updateRoundInPlay(int nroundinPlay) 
    {
    	if(((roundinPlay+nroundinPlay) >=1)&&((roundinPlay+nroundinPlay <= 12)))
    	{
    		 this.roundinPlay = nroundinPlay;
    		 roundinPlay++;
    	}
    }
    public void updatecommodityType(String commodityType)
    {
    	this.commodityType = commodityType;
    }
}
