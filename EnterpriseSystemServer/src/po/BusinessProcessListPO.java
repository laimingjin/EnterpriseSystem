package po;

import java.io.Serializable;

import enumClass.PROBLEM;

public class BusinessProcessListPO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String timeZone;
	String customerName;
	String userName;
	String storehouse;//仓库
	PROBLEM kind;
	public BusinessProcessListPO(String timeZone,PROBLEM kind,String customerName,String userName,String storehouse){
	    this.timeZone=timeZone;
	    this.kind= kind;
		this.customerName=customerName;
		this.userName=userName;
	    this.storehouse=storehouse;
		
	}
	
	public String getTimeZone() {
		return timeZone;
	}
	public String getCustomerName() {
		return customerName;
	}
	public String getUserName() {
		return userName;
	}
	public String getStorehouse() {
		return storehouse;
	}
	public PROBLEM getKind() {
		return kind;
	}
}
