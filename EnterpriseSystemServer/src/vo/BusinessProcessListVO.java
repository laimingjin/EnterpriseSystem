package vo;

import enumClass.PROBLEM;

public class BusinessProcessListVO {
	String timeZone;
	String customerName;
	String userName;
	String storehouse;//仓库
	PROBLEM kind;
	public BusinessProcessListVO(String timeZone,PROBLEM kind,String customerName,String userName,String storehouse){
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
