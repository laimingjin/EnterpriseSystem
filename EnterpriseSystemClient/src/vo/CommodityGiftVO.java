package vo;

import po.CommodityGiftPO;
import businesslogic.Commodity;
import businesslogic.Customer;



public class CommodityGiftVO {
	private  String date;
	private long documentID;                 //单据编号
	private Commodity commodity;            //商品
	 private int sendQuantity;           //送出去的商品数量  
	
	private Customer customer; 
	
	private String docIdFromSale;//产生此赠送单的ID
	
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批
    boolean isDealed;
    public CommodityGiftVO(String d,long di,Commodity c,int sq,Customer cus,boolean isPass,
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
    public CommodityGiftVO( CommodityGiftPO commodityGiftPO){
    	documentID=commodityGiftPO.getDocumentID();
    	commodity=commodityGiftPO.getCommodity();
    	sendQuantity=commodityGiftPO.getSendQuantity();
    	customer=commodityGiftPO.getCustomer();
    	date=commodityGiftPO.getDate();
    	isPass=commodityGiftPO.isPass();
    	isSend=commodityGiftPO.isSend();
    	isDealed=commodityGiftPO.isDealed();
    	
    	docIdFromSale=commodityGiftPO.getDocIdFromSale();
    }
    public long getDocumentID() {
		return documentID;
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
	 public Customer getCustomer() {
			return customer;
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
	public String toString(){
		return Long.toString(documentID);
	}
	/**
	 * @return the docIdFromSale
	 */
	public String getDocIdFromSale() {
		return docIdFromSale;
	}
	/**
	 * @param docIdFromSale the docIdFromSale to set
	 */
	public void setDocIdFromSale(String docIdFromSale) {
		this.docIdFromSale = docIdFromSale;
	}
	
}
