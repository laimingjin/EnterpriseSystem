package businesslogic;

import java.io.Serializable;

public class MockReceipt extends Receipt implements Serializable{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	String documentID;

	String customer;
	String user;
	String accountName;
	double transferPrice;
	String remarks;
	double totalPrice;
	public MockReceipt(String documentID,String customer,String user,String accountName,double transferPrice,String remarks,double totalPrice){
	super(documentID, customer, user, accountName, transferPrice, remarks, totalPrice);
	}
	public String getDocumentID() {
		return documentID;
	}
	public String getCustomer() {
		return customer;
	}
	public String getUser() {
		return user;
	}
	public String getAccountName() {
		return accountName;
	}
	public double getTransferPrice() {
		return transferPrice;
	}
	public String getRemarks() {
		return remarks;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
}
