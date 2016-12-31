package vo;

import enumClass.PROBLEM;

public class QueryManagementVO {
	String timeZone;
	
	String commodityName;
	String customerName;
	String userName;
	String storehouse;//仓库
	PROBLEM kind;
	
	String time;
	String commodityModel;
	int retailQuantity; 			// 零售数量
	double latestRetailPrice; 		// 最近零售价
	double total;//总额
	
	
	public QueryManagementVO(){}
	
	public QueryManagementVO(String timeZone,String commodityName,String customerName,String userName,String storehouse,PROBLEM kind,String time,String commodityModel,int retailQuantity,double latestRetailPrice,double total){
	    this.timeZone=timeZone;
		this.commodityName=commodityName;
		this.customerName=customerName;
		this.userName=userName;
	    this.storehouse=storehouse;
		this.kind= kind;
		this.time=time;
		this.commodityModel=commodityModel;
		this.retailQuantity=retailQuantity;
		this.latestRetailPrice=latestRetailPrice;
		this.total=total;
	}
		
	//销售明细表
	public QueryManagementVO(String timeZone,String commodityName,String customerName,String userName,String storehouse){
	    this.timeZone=timeZone;
		this.commodityName=commodityName;
		this.customerName=customerName;
		this.userName=userName;
	    this.storehouse=storehouse;
		
	}
	public QueryManagementVO(String time,String commodityName,String commodityModel,int retailQuantity,double latestRetailPrice,double total){
		this.time=time;
		this.commodityName=commodityName;
		this.commodityModel=commodityModel;
		this.retailQuantity=retailQuantity;
		this.latestRetailPrice=latestRetailPrice;
		this.total=total;
	}
	//经营历程表
	public QueryManagementVO(String timeZone,PROBLEM kind,String customerName,String userName,String storehouse){
	    this.timeZone=timeZone;
	    this.kind= kind;
		this.customerName=customerName;
		this.userName=userName;
	    this.storehouse=storehouse;
		
	}
	//经营情况表
	public String getTimeZone() {
		return timeZone;
	}

	public String getCommodityName() {
		return commodityName;
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

	public String getTime() {
		return time;
	}

	public String getCommodityModel() {
		return commodityModel;
	}

	public int getRetailQuantity() {
		return retailQuantity;
	}

	public double getLatestRetailPrice() {
		return latestRetailPrice;
	}

	public double getTotal() {
		return total;
	}
	
}

