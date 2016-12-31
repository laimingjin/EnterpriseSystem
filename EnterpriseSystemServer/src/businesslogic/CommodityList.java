package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class CommodityList implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ArrayList<CommodityLineItem> commodityLineItem =new ArrayList<CommodityLineItem>();
	
	public void addCommodityLineItem(CommodityLineItem cli){
		commodityLineItem.add(cli);
	     	
	}
//	public double calAverageinventoryPrice(){
//		int size=commodityLineItem .size();
//		double total=0;
//		for(int i=0;i<size;i++){
//			total=total+commodityLineItem.get(i).getInventoryPrice()*commodityLineItem.get(i).getInventoryQuantity();
//		}
//		
//		return total/size;
//		
//	}
	public ArrayList<CommodityLineItem> getCommodityList(){
		return commodityLineItem;
	}
}
