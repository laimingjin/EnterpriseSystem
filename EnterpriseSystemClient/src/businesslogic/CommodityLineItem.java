package businesslogic;

import java.io.Serializable;

public class CommodityLineItem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int commoditySortID; 			// 商品分类编号
	private String commoditySortName; 		// 商品分类名称
	private int commodityID; 				// 商品编号
	private String commodityName; 			// 商品名称	
	private String commodityModel; 			// 商品型号
	private double purchasePrice; 			// 进价
	private double retailPrice; 			// 零售价
	
	private double latestPurchasePrice; 	// 最近进价
	private double latestRetailPrice; 		// 最近零售价	

	public CommodityLineItem(int commoditySortID,String commoditySortName,int commodityID,String commodityName,
			String commodityModel,double purchasePrice,double retailPrice,double latestPurchasePrice,double latestRetailPrice){
		this.commoditySortID= commoditySortID; 			// 商品分类编号
	this.commoditySortName=	commoditySortName; 		// 商品分类名称
	this.commodityID=	commodityID; 				// 商品编号
		this.commodityName=commodityName; 			// 商品名称	
		this.commodityModel=commodityModel; 			// 商品型号
		this.purchasePrice=purchasePrice; 			// 进价
	this.retailPrice=	retailPrice; 			// 零售价
		
		this.latestPurchasePrice=latestPurchasePrice; 	// 最近进价
		 this.latestRetailPrice=latestRetailPrice; 		// 最近零售价	
	}
	public int getCommoditySortID() {
		return commoditySortID;
	}
	public String getCommoditySortName() {
		return commoditySortName;
	}
	public int getCommodityID() {
		return commodityID;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public String getCommodityModel() {
		return commodityModel;
	}
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public double getRetailPrice() {
		return retailPrice;
	}
	public double getLatestPurchasePrice() {
		return latestPurchasePrice;
	}
	public double getLatestRetailPrice() {
		return latestRetailPrice;
	}
}
