package businesslogic;

import java.io.Serializable;



public class CommodityGift implements Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private  String date;
	private int documentID;                 //单据编号

	private Commodity commodity;            //出错的商品
	  private int sendQuantity;           //送出去的商品数量  
	private boolean pass;
	
    public CommodityGift(String d,int di,Commodity c,int sq,boolean p){
    	documentID=di;                 //单据编号
    	commodity=c;            //出错的商品
    	 sendQuantity=sq;           //送出去的商品数量  
    	pass=p;
    	 date=d;
    }
	public int getDocumentID() {
		return documentID;
	}
	public Commodity getCommodity() {
		return commodity;
	}
	public int getSendQuantity() {
		return sendQuantity;
	}
	public boolean isPass() {
		return pass;
	}
	public String getDate() {
		return date;
	}
}
