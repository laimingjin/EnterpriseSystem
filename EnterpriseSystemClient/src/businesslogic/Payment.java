package businesslogic;

import java.io.Serializable;

public class Payment implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String date;
	String documentID;
	int customerID;
	String customerName;
	int userID;
	String userName;
	TransferList transferList;
	double totalPrice;

	public Payment(String date, String documentID, int customerID,
			String customerName, int userID, String userName,
			TransferList transferList, double totalPrice) {
		this.date = date;
		this.documentID = documentID;
		this.customerID = customerID;
		this.customerName = customerName;
		this.userID = userID;
		this.userName = userName;
		this.transferList = transferList;
		// this.accountName=accountName;
		// this.transferPrice=transferPrice;
		// this.remarks=remarks;
		this.totalPrice = totalPrice;
	}

	public Payment(String documentID2, String customer, String user,
			String accountName, double transferPrice, String remarks,
			double totalPrice2) {
	}

	public String getDate() {
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

}
