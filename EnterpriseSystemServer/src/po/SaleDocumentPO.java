package po;

import java.io.Serializable;

import enumClass.StateOfDocument;
import vo.SaleDocumentVO;
import businesslogic.Customer;
import businesslogic.SaleList;
import businesslogic.User;



public class SaleDocumentPO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6143295218863856856L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String theDate;//单据时间
	String documentID;// 单据编号
	Customer theCustomer;// 客户
	String executive;// 业务员
	User theUser;// 操作员
	String warehouse;// 仓库
	SaleList theList;// 商品列表
	String theMessage;// 备注
	double totalPriceBefore;// 折让前总额
	double theRebate;// 折让金额
	double theVoucher;// 代金券金额
	double totalPriceAfter;// 折让后总额
	StateOfDocument stateOfDocument;
	
	
    
	public SaleDocumentPO(String date,String id, Customer customer, String executive,String warehouse,
			User user, SaleList list, String message, double priceBefore,
			double rebate, double voucher, double priceAfter, 	StateOfDocument stateOfDocument) {
		theDate=date;
		documentID = id;
		theCustomer = customer;
		this.executive=executive;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate=rebate;
		theVoucher=voucher;
		totalPriceAfter=priceAfter;
		this.stateOfDocument=stateOfDocument;
	}
	public SaleDocumentPO(String date, String id, Customer customer,
			String executive, String warehouse, User user, SaleList list,
			String message, double priceBefore, double rebate, double voucher,
			double priceAfter,  boolean pass,boolean send,boolean dealed) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.executive = executive;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate = rebate;
		theVoucher = voucher;
		totalPriceAfter = priceAfter;
		stateOfDocument = StateOfDocument.DRAFT;
		if (send) {
			stateOfDocument = StateOfDocument.SENDED;
		} else if (pass) {
			stateOfDocument = StateOfDocument.EXAMINED;
		} else if (dealed) {
			stateOfDocument = StateOfDocument.DONE;
		}
	}

	public SaleDocumentPO(SaleDocumentVO vo) {
		theDate=vo.getTheDate();
		documentID = vo.getDocumentID();
		theCustomer = vo.getTheCustomer();
		this.executive=vo.getExecutive();
		this.warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPriceBefore = vo.getTotalPriceBefore();
		theRebate=vo.getTheRebate();
		theVoucher=vo.getTheVoucher();
		totalPriceAfter=vo.getTotalPriceAfter();
		this.stateOfDocument=vo.getStateOfDocument();
		
		
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

	public String getExecutive() {
		return executive;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public User getTheUser() {
		return theUser;
	}

	public SaleList getTheList() {
		return theList;
	}

	public String getTheMessage() {
		return theMessage;
	}

	public double getTotalPriceBefore() {
		return totalPriceBefore;
	}

	public double getTheRebate() {
		return theRebate;
	}

	public double getTheVoucher() {
		return theVoucher;
	}

	public double getTotalPriceAfter() {
		return totalPriceAfter;
	}

	/**
	 * @return the stateOfDocument
	 */
	public StateOfDocument getStateOfDocument() {
		return stateOfDocument;
	}
	
}
