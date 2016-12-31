package vo;

import po.ExamineAble;
import po.PaymentPO;

import businesslogic.TransferList;
import enumClass.ResultMessage;

public class PaymentVO implements ExamineAble,SendAble{
	String date;
	String documentID;
	int customerID;
	String customerName;
	int userID;
	String userName;
	TransferList transferList;
	double totalPrice;
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批
	boolean isDealed;
	public PaymentVO(String date,String documentID,int customerID,String customerName,int userID,String userName, TransferList transferList,double totalPrice,boolean isPass,boolean isSend,boolean isDealed){
		this.date=date;
		this.documentID=documentID;
		this.customerID=customerID;
		this.customerName=customerName;
		this.userID=userID;
		this.userName=userName;
		this.transferList=transferList;
//		this.accountName=accountName;
//		this.transferPrice=transferPrice;
//		this.remarks=remarks;
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
	public double getTotalPrice() {
		return totalPrice;
	}
	  public int getCustomerID() {
			return customerID;
		}
		public String getCustomerName() {
			return customerName;
		}
		public int getUserID() {
			return userID;
		}
		public String getUserName() {
			return userName;
		}
		public TransferList getTransferList() {
			return transferList;
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
	public String toString(){
		return documentID;
	}
	
	public ResultMessage send(SendAble sendAble) {
		// TODO Auto-generated method stub
		return null;
	}
	public ResultMessage examined() {
		// TODO Auto-generated method stub
		return null;
	}
}
