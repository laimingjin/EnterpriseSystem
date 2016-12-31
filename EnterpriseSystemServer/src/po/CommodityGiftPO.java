package po;

import java.io.Serializable;

import vo.CommodityGiftVO;
import businesslogic.Commodity;
import businesslogic.Customer;

public class CommodityGiftPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4974561475857751418L;
	private  String date;
	private long documentID;                 //单据编号
	private Commodity commodity;            //商品
	 private int sendQuantity;           //送出去的商品数量  
	 private Customer customer;      
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批
boolean isDealed;
    public CommodityGiftPO(String d,long di,Commodity c,int sq,Customer cus,boolean isPass,
			boolean isSend,boolean isDealed){
    	documentID=di;                 //单据编号
    	commodity=c;            //出错的商品
    	 sendQuantity=sq;           //送出去的商品数量  
    	customer=cus;
    	 date=d;
    	 this.isPass = isPass;
 		this.isSend = isSend;
 		this.isDealed=isDealed;
    }
    public CommodityGiftPO(CommodityGiftVO commodityGiftVO){
    	documentID=commodityGiftVO.getDocumentID();
    	commodity=commodityGiftVO.getCommodity();
    	sendQuantity=commodityGiftVO.getSendQuantity();
    	customer=commodityGiftVO.getCustomer();
    	date=commodityGiftVO.getDate();
    	isPass =commodityGiftVO.isPass();
    	isSend=commodityGiftVO.isSend();
    	isDealed=commodityGiftVO.isDealed();
    }
	public long getDocumentID() {
		return documentID;
	}
	 public Customer getCustomer() {
			return customer;
		}
	public Commodity getCommodity() {
		return commodity;
	}
	public int getSendQuantity() {
		return sendQuantity;
	}
	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
public boolean isDealed(){
	return isDealed;
}
public void setDealed(boolean isDealed){
	this.isDealed=isDealed;
}
	public String getDate() {
		return date;
	}    
}
