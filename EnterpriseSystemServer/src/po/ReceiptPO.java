package po;

import java.io.Serializable;

import vo.ReceiptVO;
import businesslogic.TransferList;

public class ReceiptPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5635392196272429437L;
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
	public ReceiptPO(String date,String documentID,int customerID,String customerName,int userID,String userName, TransferList transferList,double totalPrice,boolean isPass,boolean isSend,boolean isDealed){
	
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
	public ReceiptPO(ReceiptVO vo) {
		this.date=vo.getDate();
		this.documentID=vo.getDocumentID();
		this.customerID=vo.getCustomerID();
		this.customerName=vo.getCustomerName();
		this.userID=vo.getUserID();
		this.userName=vo.getUserName();
		this.transferList=vo.getTransferList();
//		this.accountName=accountName;
//		this.transferPrice=transferPrice;
//		this.remarks=remarks;
		this.totalPrice=vo.getTotalPrice();
		this.isPass=vo.isPass();
		this.isSend=vo.isSend();
		this.isDealed=vo.isDealed();
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
}
