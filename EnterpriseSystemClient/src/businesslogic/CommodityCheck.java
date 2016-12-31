package businesslogic;

import java.io.Serializable;

public class CommodityCheck  implements Serializable{
	private String timeString;
	
	private SaleList thesaleList;// 商品列表
	private ImportList theimportList;// 商品列表
	 public CommodityCheck(String tString,SaleList sList,ImportList importList){
		 this.timeString=tString;
	this.thesaleList=sList;
	this.theimportList=importList;
	 }
	 public String getTimeString() {
			return timeString;
		}
		public SaleList getThesaleList() {
			return thesaleList;
		}
		public ImportList getTheimportList() {
			return theimportList;
		}
}