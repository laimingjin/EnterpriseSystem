package vo;

import java.util.ArrayList;

import po.CustomerPO;
import enumClass.CustomerType;

public class CustomerVO {
	// 编号、分类（进货商、销售商）、级别（五级，一级普通用户，五级VIP客户）、姓名、
	// 电话、地址、邮编、电子邮箱、应收额度、应收、应付、默认业务员
	int customerID; // 客户编号0/1/9/10
	String customerType;// 客户类型
	int customerRank;// 客户级别
	String customerName;// 客户姓名
	String telePhone;// 客户电话号码
	String customerAddress;// 客户地址
	  int customerPostCode;//客户邮编
	String eMail;// 客户电子邮箱
	double receivableLimit;// 应收额度
	double receivableAmount;// 应收账款
	double payableAmount;// 应付账款
	String operator;// 常业务员


	
	public CustomerVO(int customerID, String customerType,
			int customerRank, String customerName, String telePhone,
			String customerAddress, int customerPostCode, String eMail,
			double receivableLimit, double receivableAmount,
			double payableAmount, String operator) {
		super();
		this.customerID = customerID;
		this.customerType = customerType;
		this.customerRank = customerRank;
		this.customerName = customerName;
		this.telePhone = telePhone;
		this.customerAddress = customerAddress;
		this.customerPostCode = customerPostCode;
		this.eMail = eMail;
		this.receivableLimit = receivableLimit;
		this.receivableAmount = receivableAmount;
		this.payableAmount = payableAmount;
		this.operator = operator;
	}


	public CustomerVO(CustomerPO po) {
		customerID=po.getCustomerID() ;  
        customerType=po.getCustomerType();
        customerRank=po.getCustomerRank();
        customerName=po.getCustomerName();
        telePhone=po.getTelePhone();
        customerAddress=po.getCustomerAddress();
        customerPostCode=po.getCustomerPostCode();
        eMail=po.geteMail();
        receivableLimit=po.getReceivableLimit();
        receivableAmount=po.getReceivableAmount();
        payableAmount=po.getPayableAmount();
        operator=po.getOperator();
	}


	/**
	 * @return the customerID
	 */
	public int getCustomerID() {
		return customerID;
	}


	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}


	/**
	 * @return the customerRank
	 */
	public int getCustomerRank() {
		return customerRank;
	}


	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}


	/**
	 * @return the telePhone
	 */
	public String getTelePhone() {
		return telePhone;
	}


	/**
	 * @return the customerAddress
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}


	/**
	 * @return the customerPostCode
	 */
	public int getCustomerPostCode() {
		return customerPostCode;
	}


	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}


	/**
	 * @return the receivableLimit
	 */
	public double getReceivableLimit() {
		return receivableLimit;
	}


	/**
	 * @return the receivableAmount
	 */
	public double getReceivableAmount() {
		return receivableAmount;
	}


	/**
	 * @return the payableAmount
	 */
	public double getPayableAmount() {
		return payableAmount;
	}


	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}


	public String toString() {
		return customerName;
	}

}
