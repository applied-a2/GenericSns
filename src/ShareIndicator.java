import java.util.*;

public class ShareIndicator {
	
	private	ArrayList<Commodity> shareRecords;
	private int round;
	 
	public ShareIndicator(ArrayList<Commodity> shares)
	{
		shareRecords = new ArrayList<Commodity>(); 
		String type = "";
		for(Commodity share: shares)
		{
			if(!share.getCommodityType().equals(type))
			{
				type = share.getCommodityType();
				shareRecords.add(share);
			}
		}
		this.shareRecords = shareRecords;
		this.round= 1;
	}
	
	public void updateshareRecords(String commodityType, int newValue)
	{
		for(Commodity shareRecord: shareRecords)
		{
			if(shareRecord.getCommodityType().equals(commodityType))
			{
				if((shareRecord.getValue() + newValue >= 0)&&(shareRecord.getValue() + newValue <= 20))
				{
					shareRecord.updateValue(newValue);
				}
			}
		}
	}
	
	public ArrayList<Commodity> getshareRecords()
	{
		return shareRecords;
	}
	
	public int getRound()
	{
		return round;
	}
	
	public void nextRound()
	{
		if(round < 12)
		{
			round++;
		}
	}
	
	public String toString()
	{
		String record = "";
		for(Commodity shareRecord: shareRecords)
		{
			record += "| " + shareRecord.toString() + "\n";
		}
		return record;
	}
}
