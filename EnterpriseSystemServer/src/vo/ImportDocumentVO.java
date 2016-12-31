package vo;

import java.util.ArrayList;



import dataStub_Ser.ImportData_Ser;
import po.ExamineAble;
import po.ImportDocumentPO;
import po.SaleDocumentPO;

import enumClass.POSITION;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;
import businesslogic.Commodity;
import businesslogic.Customer;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.User;

public class ImportDocumentVO implements ExamineAble, SendAble {
	private String theDate;
	private String documentID;// 单据编号
	private Customer theCustomer;// 客户
	private String warehouse;// 仓库
	private User theUser;// 操作员
	private ImportList theList;// 商品列表
	private String theMessage;// 备注
	private double totalPrice;// 总价
	private StateOfDocument stateOfDocument;
	private String kind;

	private final String headOfID_JHD = "JHD";
	private final String headOfID_JHTHD = "JHTHD";

	ArrayList<PromotionVO_Price> promotionVO_Prices = new ArrayList<PromotionVO_Price>();
	ArrayList<PromotionVO_Customer> promotionVO_Customers = new ArrayList<PromotionVO_Customer>();
	ArrayList<PromotionVO_Package> promotionVO_Packages = new ArrayList<PromotionVO_Package>();

	// boolean isPass;//是否通过审核
	// boolean isSend;//是否已提交审核
	// boolean isDealed;

	public ImportDocumentVO(String date, String id, Customer customer,
			String warehouse, User user, ImportList list, String message,
			double price, StateOfDocument stateOfDocument) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPrice = price;
		this.stateOfDocument = stateOfDocument;
		// this.isPass=isPass;
		// this.isSend=isSend;
		// this.isDealed=isDealed;
	}

	public ImportDocumentVO(String date, String id, Customer customer,
			String warehouse, User user, ImportList list, String message,
			double price, boolean pass, boolean send, boolean dealed) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPrice = price;

		stateOfDocument = StateOfDocument.DRAFT;
		if (send) {
			stateOfDocument = StateOfDocument.SENDED;
		} else if (pass) {
			stateOfDocument = StateOfDocument.EXAMINED;
		} else if (dealed) {
			stateOfDocument = StateOfDocument.DONE;
		}
	}

	public boolean isPass() {
		return stateOfDocument == StateOfDocument.EXAMINED;
	}

	public void setPass(boolean isPass) {
		if (isPass) {
			stateOfDocument = StateOfDocument.EXAMINED;
		}
	}

	public boolean isSend() {
		return stateOfDocument == StateOfDocument.SENDED;
	}

	public void setSend(boolean isSend) {
		if (isSend) {
			stateOfDocument = StateOfDocument.SENDED;
		}
	}

	public boolean isDealed() {
		return stateOfDocument == StateOfDocument.DONE;
	}

	public void setDealed(boolean isDealed) {
		if (isDealed) {
			stateOfDocument = stateOfDocument.DONE;
		}
	}

	public ImportDocumentVO(ImportDocumentPO po) {
		theDate = po.getTheDate();
		documentID = po.getDocumentID();
		theCustomer = po.getTheCustomer();
		warehouse = po.getWarehouse();
		theUser = po.getTheUser();
		theList = po.getTheList();
		theMessage = po.getTheMessage();
		totalPrice = po.getTotalPrice();
		stateOfDocument = po.getStateOfDocument();
	}

	public ImportDocumentVO(String newId, ImportDocumentVO vo) {
		theDate = vo.getTheDate();
		documentID = newId;
		theCustomer = vo.getTheCustomer();
		warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPrice = vo.getTotalPrice();
		stateOfDocument = vo.getStateOfDocument();
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

	public String toString() {
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
