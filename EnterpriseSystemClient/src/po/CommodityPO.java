package po;

import java.io.Serializable;

import vo.CommodityVO;

public class CommodityPO implements Serializable{
	
	private static final long serialVersionUID = -6399144509181130459L;

	private int commoditySortID; 			// 商品分类编号
	
	private String commoditySortName; 		// 商品分类名称
	private int commodityID; 				// 商品编号
	private String commodityName; 			// 商品名称
	private String commodityModel; 			// 商品型号
	private int inventoryQuantity; 			// 库存数量
	private double purchasePrice; 			// 进价
	private double retailPrice; 			// 零售价
	
	private double latestPurchasePrice; 	// 最近进价
	private double latestRetailPrice; 		// 最近零售价	
	private String date;                   //出厂日期
	private double averagePrice;              //库存均价
	private int warnQuantity;                 




	public CommodityPO(int csi,String csn,int ci,String cn,String cm,int iq,double pp,double rp,double lpp,double lrp,String d,double ap,int warnQuantity){
		commoditySortName=csn;
		commoditySortID=csi;
		commodityName=cn;
		commodityID=ci;
		commodityModel=cm;
		inventoryQuantity=iq;
		purchasePrice=pp;
		retailPrice=rp;
		latestPurchasePrice=lpp;
		latestRetailPrice=lrp;
		date=d;
		averagePrice=ap;
		this.warnQuantity=warnQuantity;
	}

		
	
	public CommodityPO(CommodityVO commodityVO) {

		commoditySortID=commodityVO.getCommoditySortID();
		commoditySortName=commodityVO.getCommoditySortName();
		commodityID=commodityVO.getCommodityID();
		commodityName=commodityVO.getCommodityName();
		commodityModel=commodityVO.getCommodityModel();
		inventoryQuantity=commodityVO.getInventoryQuantity();
		purchasePrice=commodityVO.getPurchasePrice();

		retailPrice=commodityVO.getRetailPrice();

		latestPurchasePrice=commodityVO.getLatestPurchasePrice();
		latestRetailPrice=commodityVO.getLatestRetailPrice();
		date=commodityVO.getDate();
		averagePrice=commodityVO.getAveragePrice();
		warnQuantity=commodityVO.getWarnQuantity();
	       
		
	}



	public int getWarnQuantity() {
		return warnQuantity;
	}




	public void setWarnQuantity(int warnQuantity) {
		this.warnQuantity = warnQuantity;
	}

	
	public String getCommoditySortName() {
		return commoditySortName;
	}
	public int getCommoditySortID() {
		return commoditySortID;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public int getCommodityID() {
		return commodityID;
	}
	public String getCommodityModel() {
		return commodityModel;
	}
	public int getInventoryQuantity() {
		return inventoryQuantity;
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
	  public String getDate() {
			return date;
		}

		public double getAveragePrice() {
			return averagePrice;
		}
		public void setCommoditySortName(String commoditySortName) {
			this.commoditySortName = commoditySortName;
		}



		public void setCommodityName(String commodityName) {
			this.commodityName = commodityName;
		}



		public void setCommodityModel(String commodityModel) {
			this.commodityModel = commodityModel;
		}


}
