package po;

import java.io.Serializable;

import vo.ImportDocumentVO;
import enumClass.StateOfDocument;
import businesslogic.Customer;
import businesslogic.ImportList;
import businesslogic.User;

public class ImportDocumentPO implements Serializable{
	
	private static final long serialVersionUID = -3630114153115528093L;
	private String theDate;
	private String documentID;// 单据编号
	private Customer theCustomer;// 客户
	private String warehouse;// 仓库
	private User theUser;// 操作员
	private 	ImportList theList;// 商品列表
	private String theMessage;// 备注
	private double totalPrice;// 总价
	private StateOfDocument stateOfDocument;
	
	public ImportDocumentPO(String date, String id, Customer customer,
			String warehouse, User user, ImportList list, String message,
			double price,StateOfDocument stateOfDocument) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPrice = price;
		this.stateOfDocument=stateOfDocument;
	}

//	public boolean isPass() {
//		return isPass;
//	}
//
//	public void setPass(boolean isPass) {
//		this.isPass = isPass;
//	}
//
//	public boolean isSend() {
//		return isSend;
//	}
//
//	public void setSend(boolean isSend) {
//		this.isSend = isSend;
//	}
//
//	public boolean isDealed() {
//		return isDealed;
//	}
//
//	public void setDealed(boolean isDealed) {
//		this.isDealed = isDealed;
//	}

	public ImportDocumentPO(ImportDocumentVO vo) {
		theDate = vo.getTheDate();
		documentID = vo.getDocumentID();
		theCustomer =vo.getTheCustomer() ;
		this.warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPrice = vo.getTotalPrice();
		stateOfDocument=vo.getStateOfDocument();
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

	public String getTheMessage() {
		return theMessage;
	}

	/**
	 * @return the stateOfDocument
	 */
	public StateOfDocument getStateOfDocument() {
		return stateOfDocument;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param stateOfDocument the stateOfDocument to set
	 */
	public void setStateOfDocument(StateOfDocument stateOfDocument) {
		this.stateOfDocument = stateOfDocument;
	}

}
