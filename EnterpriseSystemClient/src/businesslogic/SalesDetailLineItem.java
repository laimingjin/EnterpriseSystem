package businesslogic;

import java.io.Serializable;

public class SalesDetailLineItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String date;
	String commodityName;
	String commodityModel;
	int retailQuantity; // 零售数量
	double latestRetailPrice; // 最近零售价
	double total;// 总额

	public SalesDetailLineItem(String date, String commodityName,
			String commodityModel, int retailQuantity,
			double latestRetailPrice, double total) {
		this.date = date;
		this.commodityName = commodityName;
		this.commodityModel = commodityModel;
		this.retailQuantity = retailQuantity;
		this.latestRetailPrice = latestRetailPrice;
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public String getCommodityName() {
		return commodityName;
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
