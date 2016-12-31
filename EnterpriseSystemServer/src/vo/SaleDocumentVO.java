package vo;

import po.ExamineAble;
import po.SaleDocumentPO;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;
import businesslogic.Customer;
import businesslogic.SaleList;
import businesslogic.User;

/**
 * 单据编号（XSD-yyyyMMdd-xxxxx），客户（仅显示销售商），业务员（和这个客户打交道的公司员工，可以设置一个客户的默认业务员-problem
 * 【是否可修改？】），操作员（当前登录系统的用户），仓库，出货商品清单，折让前总额，折让【级别不同，情况不同】，使用代金卷金额，折让后总额，备注。
 * 出货商品清单中要显示商品的编号，名称（从商品选择界面选择），型号，数量（手工输入），单价（默认为商品信息里的销售价，可修改），金额（自动生成），商品备注。
 * 2014年12月10日下午5:16:12
 */
public class SaleDocumentVO implements ExamineAble ,SendAble {
	private final String headOfID_XSD="XSD";
	private final String headOfID_XSTHD="XSTHD";
	String theDate;// 单据时间
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

	public SaleDocumentVO(String date, String id, Customer customer,
			String executive, String warehouse, User user, SaleList list,
			String message, double priceBefore, double rebate, double voucher,
			double priceAfter, StateOfDocument stateOfDocument) {
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
		this.stateOfDocument = stateOfDocument;
	}
	
	public SaleDocumentVO(String date, String id, Customer customer,
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

	public SaleDocumentVO(String newID, SaleDocumentVO vo) {
		theDate = vo.getTheDate();
		documentID = newID;
		theCustomer = vo.getTheCustomer();
		this.executive = vo.getExecutive();
		this.warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPriceBefore = vo.getTotalPriceBefore();
		theRebate = vo.getTheRebate();
		theVoucher = vo.getTheVoucher();
		totalPriceAfter = vo.getTotalPriceAfter();
		this.stateOfDocument = vo.getStateOfDocument();
	}


	public SaleDocumentVO(SaleDocumentPO po) {
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
