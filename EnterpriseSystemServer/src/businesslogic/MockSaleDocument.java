package businesslogic;

import java.io.Serializable;

public class MockSaleDocument extends SaleDocument  implements Serializable{
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	String theDate;// 单据时间
	String documentID;// 单据编号
	Customer theCustomer;// 客户
	String customerName;
	String executive;// 业务员
	String warehouse;// 仓库
	User theUser;// 操作员
	SaleList theList;// 商品列表
	String commodityName;
	String commodityModel;
	int Quantity;
	double retailPrice;
	double totalPrice;
	String theMessage;// 备注
	double totalPriceBefore;// 折让前总额
	double theRebate;// 折让金额
	double theVoucher;// 代金券金额
	double totalPriceAfter;// 折让后总额
	boolean isPass;// 是否通过审批
	boolean isSend;// 是否已经提交审批

	public MockSaleDocument(String theDate, String commodityName,
			String commodityModel, int Quantity, double retailPrice,
			double totalPrice) {
		// super(date,commodityName,commodityModel,Quantity,retailPrice,totalPrice);
		this.theDate = theDate;
		this.commodityName = commodityName;
		this.commodityModel = commodityModel;
		this.Quantity = Quantity;
		this.retailPrice = retailPrice;
		this.totalPrice = totalPrice;

	}

	public MockSaleDocument(String theDate, String commodityName,
			String commodityModel, String customerName, String executive,
			String warehouse, int Quantity, double retailPrice,
			double totalPrice) {
		// super(date,commodityName,commodityModel,Quantity,retailPrice,totalPrice);
		this.customerName = customerName;
		this.executive = executive;
		this.warehouse = warehouse;
		this.theDate = theDate;
		this.commodityName = commodityName;
		this.commodityModel = commodityModel;
		this.Quantity = Quantity;
		this.retailPrice = retailPrice;
		this.totalPrice = totalPrice;

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
