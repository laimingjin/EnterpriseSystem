package businesslogic;

import java.io.Serializable;

public class CashList implements Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	String documentID;

	String entryName;
	String user;
	String accountName;
	double entryPrice;
	String remarks;
	double totalPrice;
	public CashList(String documentID,String user,String accountName,String entryName,double entryPrice,String remarks,double totalPrice){
		this.documentID=documentID;
		this.entryName=entryName;
		this.user=user;
		this.accountName=accountName;
		this.entryPrice=entryPrice;
		this.remarks=remarks;
		this.totalPrice=totalPrice;
	}
	public String getDocumentID() {
		return documentID;
	}
	public String getEntryName() {
		return entryName;
	}
	public String getUser() {
		return user;
	}
	public String getAccountName() {
		return accountName;
	}
	public double getEntryPrice() {
		return entryPrice;
	}
	public String getRemarks() {
		return remarks;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
}
