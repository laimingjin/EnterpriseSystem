package businesslogic;

import java.io.Serializable;

public class ImportDocument implements Serializable{
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String theDate;//单据时间
	String documentID;//单据编号
	Customer theCustomer;//客户   //这里的客户和操作员是只要ID坏人名字就好还是要一个类  考虑UI的实现
	String warehouse;//仓库
	User theUser;//操作员
	ImportList theList;//商品列表
	String theMessage;//备注
	double totalPrice;//总价
	boolean isPass;//是否通过审核
	boolean isSend;//是否已提交审核
 
	 public ImportDocument(){}
	public ImportDocument(String date,String id, Customer customer, String warehouse,
			User user, ImportList list, String message, double price,boolean isPass,boolean isSend) {
		theDate=date;
		documentID = id;
		theCustomer = customer;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPrice = price;
		this.isPass=isPass;
		this.isSend=isSend;
	}
	
	public String getTheDate() {
		return theDate;
	}

	public String getDocumentID() {
		return documentID;
	}

	public Customer getTheCustomer() {
		return theCustomer;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public User getTheUser() {
		return theUser;
	}

	public ImportList getTheList() {
		return theList;
	}

	public String getMessage() {
		return theMessage;
	}

	public double getTotalPrice() {
		return totalPrice;
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
}
