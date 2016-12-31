package po;

import java.io.Serializable;

import businesslogic.EntryList;

public class CashListPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3066709916434986967L;
	String date;
	String documentID;
	int userID;
	String userName;
	String accountName;
EntryList entryList;
	double totalPrice;
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批
	boolean isDealed;
	public CashListPO(String date,String documentID,int userID,String userName,String accountName,EntryList entryList,double totalPrice,boolean isPass,boolean isSend,boolean isDealed){
		this.date=date;
		this.documentID=documentID;
		this.userID=userID;
		this.userName=userName;
		this.accountName=accountName;
	this.entryList=entryList;
		this.totalPrice=totalPrice;
		this.isPass=isPass;
		this.isSend=isSend;
		this.isDealed=isDealed;
	}
	public String getDate(){
		return date;
	}
	public String getDocumentID() {
		return documentID;
	}
	public int getUserID() {
		return userID;
	}
	public String getUserName() {
		return userName;
	}
	public String getAccountName(){
		return accountName;
	}
	public EntryList getEntryList(){
		return entryList;
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
public boolean isDealed(){
	return isDealed;
}
public void setDealed(boolean isDealed){
	this.isDealed=isDealed;
}
	
}
