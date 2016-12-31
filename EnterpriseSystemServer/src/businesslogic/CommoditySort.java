package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class CommoditySort  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private 	int commoditySortID; 						// 商品分类编号
	private String commoditySortName; 					// 商品分类名称
	
	private 	String Father;
	private 	  ArrayList<CommoditySort> commoditySortSonList = new ArrayList<CommoditySort>();  //分类下所拥有的子分类
	private 	boolean hasCommodity=false;                       //有无商品
	private   ArrayList<Commodity> commodityList = new ArrayList<Commodity>();  //分类下所拥有的商品
	  public CommoditySort(int csi,String csn,String f, boolean hc,ArrayList<CommoditySort>csl,  ArrayList<Commodity>cl){
		  commoditySortName=csn;
          commoditySortID=csi;
          Father=f;
          hasCommodity=hc;
          commoditySortSonList=csl;
          commodityList=cl;
	  }
	  

		public String getCommoditySortName() {
			return commoditySortName;
		}
		public int getCommoditySortID() {
			return commoditySortID;
		}
		public String getFather() {
			return Father;
		}
		public ArrayList<CommoditySort> getCommoditySortSonList() {
			return commoditySortSonList;
		}
		public boolean isHasCommodity() {
			return hasCommodity;
		}
		public ArrayList<Commodity> getCommodityList() {
			return commodityList;
		}



}
