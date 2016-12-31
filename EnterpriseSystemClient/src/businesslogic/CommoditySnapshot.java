package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;


public class CommoditySnapshot implements Serializable {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	private ArrayList<Commodity> commodityArr;         //各种商品的arrlist
	
	private	String lot; 				     // 批次(日期)
	private	int lotNumber; 					 // 批号（序号）
	private	int lineNumber;                  //行号
	//有大问题
     public    CommoditySnapshot ( 	ArrayList<Commodity> commodityarr,double a,String l,int ln,String d,int linenumber){
		commodityArr=commodityarr;
	       lot=l; 				     // 批次(日期)
		 lotNumber=ln; 					 // 批号（序号）
		 lineNumber=linenumber;                  //行号
	}
    

 	public ArrayList<Commodity> getCommodityArr() {
 		return commodityArr;
 	}
 	public String getLot() {
 		return lot;
 	}
 	public int getLotNumber() {
 		return lotNumber;
 	}
 	public int getLineNumber() {
 		return lineNumber;
 	}
		
}
