package businesslogic;

import java.io.Serializable;

import enumClass.PROBLEM;

public class CommodityDocument implements Serializable{
/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
private  String date;
	private int documentID;                 //单据编号
	private Commodity commodity;            //出错的商品
	private PROBLEM problem;				// 出错类型
	private int realQuantity;				// 实际库存
	private boolean pass;
	
	
	public  CommodityDocument(String d,int di,Commodity c,PROBLEM p,int rq,boolean isPass){
		documentID=di;              //单据编号
		commodity =c;
		pass=isPass;
	    problem=p;
	    date=d;
		realQuantity=rq;			//实际库存
		
	}
	public int getDocumentID() {
		return documentID;
	}
	public Commodity getCommodity() {
		return commodity;
	}
	public PROBLEM getProblem() {
		return problem;
	}
	public boolean isPass() {
		return pass;
	}
	public String getDate() {
		return date;
	}
	public int getRealQuantity() {
		return realQuantity;
	}

}
