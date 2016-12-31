package po;

import java.io.Serializable;

public class AccountPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID =  -9126163066350620197L;
	String accountName;//账号名称
	double accountPrice;//账号金额

	public AccountPO(String name,double price){
		accountName=name;
		accountPrice=price;		
	}
	public String getAccountName(){
		return accountName;		
	}
	public double getAccountPrice(){
		return accountPrice;	
	}

	
	
}
