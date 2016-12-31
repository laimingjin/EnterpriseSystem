package vo;

import po.CommodityPO;

/**
 * 编号、名称、型号、库存数量、进价、零售价、最近进价、最近零售价
 * 
 * @author YSH 2014年12月10日下午2:22:45
 */
public class CommodityVO {
	
private int commoditySortID; // 商品分类编
	private String commoditySortName; // 商品分类名称
	

		private int commodityID; // 商品编号
	private String commodityName; // 商品名称
	private String commodityModel; // 商品型号
	private int inventoryQuantity; // 库存数量
	

	private double purchasePrice; // 进价
	private double retailPrice; // 零售价
	private double latestPurchasePrice; // 最近进价
	private double latestRetailPrice; // 最近零售价
	private String date; // 出厂日期
	private double averagePrice; // 库存均价
	private int warnQuantity;

	public CommodityVO(String commoditySortName,	 int commoditySortID,String commodityName,int commodityID, 
		 String commodityModel,
			int inventoryQuantity, double purchasePrice, 
			double retailPrice,  double latestPurchasePrice,
			double latestRetailPrice, String date, double averagePrice,int warnQuantity ) {
		super();
		this.commoditySortName = commoditySortName;
		this.commoditySortID = commoditySortID;
		this.commodityID = commodityID;
		this.commodityName = commodityName;
		this.commodityModel = commodityModel;
		this.inventoryQuantity = inventoryQuantity;
		this.purchasePrice = purchasePrice;
		this.retailPrice = retailPrice;
		this.latestPurchasePrice = latestPurchasePrice;
		this.latestRetailPrice = latestRetailPrice;
		this.date = date;
		this.averagePrice = averagePrice;
		this.warnQuantity=warnQuantity;
	}

	public CommodityVO(CommodityPO commodityPO) {
		this.commoditySortName = commodityPO.getCommoditySortName();
		this.commoditySortID = commodityPO.getCommoditySortID();
		this.commodityID = commodityPO.getCommodityID();
		this.commodityName = commodityPO.getCommodityName();
		this.commodityModel = commodityPO.getCommodityModel();
		this.inventoryQuantity = commodityPO.getInventoryQuantity();
		this.purchasePrice =commodityPO.getPurchasePrice();
		this.retailPrice = commodityPO.getRetailPrice();
		this.latestPurchasePrice = commodityPO.getLatestPurchasePrice();
		this.latestRetailPrice =commodityPO.getLatestRetailPrice();
		this.date = commodityPO.getDate();
		this.averagePrice = commodityPO.getAveragePrice();
		this.warnQuantity=commodityPO.getWarnQuantity();
		
		// TODO Auto-generated constructor stub
	}

	public int getWarnQuantity() {
		return warnQuantity;
	}

	public void setWarnQuantity(int warnQuantity) {
		this.warnQuantity = warnQuantity;
	}

	/**
	 * @return the commoditySortName
	 */
	public String getCommoditySortName() {
		return commoditySortName;
	}

	/**
	 * @return the commoditySortID
	 */
	public int getCommoditySortID() {
		return commoditySortID;
	}

	/**
	 * @return the commodityID
	 */
	public int getCommodityID() {
		return commodityID;
	}

	/**
	 * @return the commodityName
	 */
	public String getCommodityName() {
		return commodityName;
	}

	/**
	 * @return the commodityModel
	 */
	public String getCommodityModel() {
		return commodityModel;
	}

	/**
	 * @return the inventoryQuantity
	 */
	public int getInventoryQuantity() {
		return inventoryQuantity;
	}

	/**
	 * @return the purchasePrice
	 */
	public double getPurchasePrice() {
		return purchasePrice;
	}
	public void setCommoditySortName(String commoditySortName) {
		this.commoditySortName = commoditySortName;
	}

	/**
	 * @return the retailPrice
	 */
	public double getRetailPrice() {
		return retailPrice;
	}



	/**
	 * @return the latestPurchasePrice
	 */
	public double getLatestPurchasePrice() {
		return latestPurchasePrice;
	}

	/**
	 * @return the latestRetailPrice
	 */
	public double getLatestRetailPrice() {
		return latestRetailPrice;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the averagePrice
	 */
	public double getAveragePrice() {
		return averagePrice;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public void setCommodityModel(String commodityModel) {
		this.commodityModel = commodityModel;
	}

	public String toString() {
		return commodityName;
	}
	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

}
