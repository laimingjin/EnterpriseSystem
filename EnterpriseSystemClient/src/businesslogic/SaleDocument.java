package businesslogic;

import java.io.Serializable;

public class SaleDocument implements Serializable {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	String theDate;// 单据时间
	String documentID;// 单据编号
	Customer theCustomer;// 客户
	String executive;// 业务员
	String warehouse;// 仓库
	User theUser;// 操作员
	SaleList theList;// 商品列表
	String theMessage;// 备注
	double totalPriceBefore;// 折让前总额
	double theRebate;// 折让金额
	double theVoucher;// 代金券金额
	double totalPriceAfter;// 折让后总额
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批

	public SaleDocument() {
	}

	public SaleDocument(String date, String id, Customer customer,
			String warehouse, User user, SaleList list, String message,
			double priceBefore, double rebate, double voucher,
			double priceAfter, boolean isPass, boolean isSend) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate = rebate;
		theVoucher = voucher;
		totalPriceAfter = priceAfter;
		this.isPass = isPass;
		this.isSend = isSend;
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

	public SaleList getTheList() {
		return theList;
	}

	public String getMessage() {
		return theMessage;
	}

	public String getExecutive() {
		return executive;
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
