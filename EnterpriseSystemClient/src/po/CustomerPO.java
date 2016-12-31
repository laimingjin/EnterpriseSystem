package po;

import java.io.Serializable;

import vo.CustomerVO;

public class CustomerPO implements Serializable{
   
	
	private static final long serialVersionUID = 2127135450673540799L;
	int customerID;   //客户编号
    String customerType;//客户类型
    int customerRank;//客户级别
    String customerName;//客户姓名
    String telePhone;//客户电话号码
    String customerAddress;//客户地址
    int customerPostCode;//客户邮编
    String eMail;//客户电子邮箱
    double receivableLimit;//应收额度
    double receivableAmount;//应收账款
    double payableAmount;//应付账款
    String operator;// 常业务员
    
    public CustomerPO(int cid,String ctype,int crank,String cname,String ctele,String caddr,
    		int cpost,String cemai,double climit, double creve,double cpay,String Operator){
    	 customerID=cid;  
         customerType=ctype;
         customerRank=crank;
         customerName=cname;
         telePhone=ctele;
         customerAddress=caddr;
         customerPostCode=cpost;
         eMail=cemai;
         receivableLimit=climit;
         receivableAmount=creve;
         payableAmount=cpay;
         operator=Operator;
    }
    
    public CustomerPO(CustomerVO vo){
    	 customerID=vo.getCustomerID() ;  
         customerType=vo.getCustomerType();
         customerRank=vo.getCustomerRank();
         customerName=vo.getCustomerName();
         telePhone=vo.getTelePhone();
         customerAddress=vo.getCustomerAddress();
         customerPostCode=vo.getCustomerPostCode();
         eMail=vo.geteMail();
         receivableLimit=vo.getReceivableLimit();
         receivableAmount=vo.getReceivableAmount();
         payableAmount=vo.getPayableAmount();
         operator=vo.getOperator();
    }
    
    public int getCustomerID() {
		return customerID;
	}

	public String getCustomerType() {
		return customerType;
	}

	public int getCustomerRank() {
		return customerRank;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public int getCustomerPostCode() {
		return customerPostCode;
	}

	public String geteMail() {
		return eMail;
	}

	public double getReceivableLimit() {
		return receivableLimit;
	}

	public double getReceivableAmount() {
		return receivableAmount;
	}

	public double getPayableAmount() {
		return payableAmount;
	}

	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	public boolean hasReceivableOrPayable() {
		return receivableAmount!=0.0  ||  payableAmount!=0.0;
	}

	/**
	 * @param receivableAmount the receivableAmount to set
	 */
	public void setReceivableAmount(double receivableAmount) {
		this.receivableAmount = receivableAmount;
	}

	/**
	 * @param payableAmount the payableAmount to set
	 */
	public void setPayableAmount(double payableAmount) {
		this.payableAmount = payableAmount;
	}
    
}
