package businesslogic;

import java.io.Serializable;

import vo.CustomerVO;

//import po.CustomerPO;
//import dataStub.CustomerDataServiceMyaqlImpl_stub;

public class Customer implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 8875792395117986807L;
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
    
    public Customer(){}
    
    public Customer(int cid,String ctype,int crank,String cname,String ctele,String caddr,
    		int cpost,String cemai,double climit, double creve,double cpay,String operator){
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
         this.operator = operator;
    }
    
//    public Customer getCustomerByName(String name){
//    	CustomerDataServiceMyaqlImpl_stub  customerData=new CustomerDataServiceMyaqlImpl_stub();
//    	CustomerPO theCustomerPO=customerData.find(name);
//    	Customer theCustomer=null;
//    	if(theCustomerPO==null){
//    		System.out.println("The customer isn't existed!");
//    	}else{
//    		
//    	}
//    }
    public Customer(CustomerVO myCustomerVO){
    	 customerID=myCustomerVO.getCustomerID();  
         customerType=myCustomerVO.getCustomerType();
         customerRank=myCustomerVO.getCustomerRank();
         customerName=myCustomerVO.getCustomerName();
         telePhone=myCustomerVO.getTelePhone();
         customerAddress=myCustomerVO.getCustomerAddress();
         customerPostCode=myCustomerVO.getCustomerPostCode();
         eMail=myCustomerVO.geteMail();
         receivableLimit=myCustomerVO.getReceivableLimit();
         receivableAmount=myCustomerVO.getReceivableAmount();
         payableAmount=myCustomerVO.getPayableAmount();
    }
    
    /**
     * 得到各数据的方法
     * @return
     */

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

	public String getOperator() {
		return operator;
	}
    
    

}
