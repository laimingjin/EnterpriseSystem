package vo;

import po.ExamineAble;
import temp_businessImp.CustomerBLImp;
import businesslogic.TransferList;
import enumClass.ResultMessage;

public class ReceiptVO implements ExamineAble {
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

	public ReceiptVO(String date, String documentID, int customerID,
			String customerName, int userID, String userName,
			TransferList transferList, double totalPrice, boolean isPass,
			boolean isSend, boolean isDealed) {

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
		this.isPass = isPass;
		this.isSend = isSend;
		this.isDealed = isDealed;
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

	public boolean isDealed() {
		return isDealed;
	}

	public void setDealed(boolean isDealed) {
		this.isDealed = isDealed;
	}

	// TODO  收款单执行失败条件有否
	public ResultMessage examined() {
		ResultMessage resultMessage = ResultMessage.SUCCESS;
		CustomerBLImp customerBLImp = new CustomerBLImp();

		// resultMessage=receiptDataService.add(po);
		if (resultMessage.equals(ResultMessage.SUCCESS)) {// 要修改客户的应付
			CustomerVO v = customerBLImp.findCustomer(this.getCustomerID());
			double payableAmount = v.getPayableAmount() + this.getTotalPrice();
			CustomerVO newVo = new CustomerVO(v.getCustomerID(),
					v.getCustomerType(), v.getCustomerRank(),
					v.getCustomerName(), v.getTelePhone(),
					v.getCustomerAddress(), v.getCustomerPostCode(),
					v.geteMail(), v.getReceivableLimit(),
					v.getReceivableAmount(), payableAmount, v.getOperator());
			customerBLImp.updateCustomer(newVo);
		}
		return resultMessage;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

}
