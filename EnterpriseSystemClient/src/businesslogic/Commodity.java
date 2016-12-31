package businesslogic;

import java.io.Serializable;

import po.CommodityPO;
import vo.CommodityVO;

public class Commodity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = 8878683562005068663L;
	private String commoditySortName; 		// 商品分类名称
	private int commoditySortID; 			// 商品分类编号
	private String commodityName; 			// 商品名称
	private int commodityID; 				// 商品编号
	private String commodityModel; 			// 商品型号
	private int inventoryQuantity; 			// 库存数量
	private double purchasePrice; 			// 进价

	private double retailPrice; 			// 零售价

	private double latestPurchasePriceForChengBenTiaoJiao;//成本调价用上一次最近进价
	private double latestPurchasePrice; 	// 最近进价，在import中为当前进价
	private double latestRetailPrice; 		// 最近零售价	，在export中为当前shou价
	private String date;                   //出厂日期
	private double averagePrice;              //库存均价
	private int warnQuantity;

	

	public Commodity(String csn,int csi,String cn,int ci,String cm,int iq,double pp,double rp,double lpp,double lrp,String d,double ap,int w){
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
		warnQuantity=w;
	}
public Commodity(CommodityVO commodityVO){
	commoditySortName=commodityVO.getCommoditySortName();
	commoditySortID=commodityVO.getCommoditySortID();
	commodityName=commodityVO.getCommodityName();
	commodityID=commodityVO.getCommodityID();
	commodityModel=commodityVO.getCommodityModel();
	inventoryQuantity=commodityVO.getInventoryQuantity();
	purchasePrice=commodityVO.getPurchasePrice();

	retailPrice=commodityVO.getRetailPrice();
	
	latestPurchasePrice=commodityVO.getLatestPurchasePrice();
	latestRetailPrice=commodityVO.getLatestRetailPrice();
	date=commodityVO.getDate();
	averagePrice=commodityVO.getAveragePrice();
}
public Commodity(CommodityPO commodityPO){
	commoditySortName=commodityPO.getCommoditySortName();
	commoditySortID=commodityPO.getCommoditySortID();
	commodityName=commodityPO.getCommodityName();
	commodityID=commodityPO.getCommodityID();
	commodityModel=commodityPO.getCommodityModel();
	inventoryQuantity=commodityPO.getInventoryQuantity();
	purchasePrice=commodityPO.getPurchasePrice();

	retailPrice=commodityPO.getRetailPrice();
	
	latestPurchasePrice=commodityPO.getLatestPurchasePrice();
	latestRetailPrice=commodityPO.getLatestRetailPrice();
	date=commodityPO.getDate();
	averagePrice=commodityPO.getAveragePrice();
}
	//让别人通过名字和型号得到COMMODITY
	/*public Commodity getCommodityByName(String name,String model) throws RemoteException, ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		CommodityData_Ser   	commodityData= 	CommodityData_Ser.getInstance();
	
		CommodityPO commoditypo=commodityData.find(name,model);
		Commodity commodity=null;
	if(commoditypo==null){
		System.out.println("不存在");
	}else{
		String commoditySortName=commoditypo.getCommoditySortName(); 		// 商品分类名称
		 int commoditySortID=commoditypo.getCommoditySortID(); 			// 商品分类编号
		 String commodityName=commoditypo.getCommodityName(); 			// 商品名称
		int commodityID=commoditypo.getCommodityID(); 				// 商品编号
		String commodityModel=commoditypo.getCommodityModel(); 			// 商品型号
		int inventoryQuantity=commoditypo.getInventoryQuantity(); 			// 库存数量
		double purchasePrice=commoditypo.getPurchasePrice(); 			// 进价
	 
		double retailPrice=commoditypo.getRetailPrice(); 			// 零售价
		int retailQuantity=commoditypo.getInventoryQuantity(); 			// 零售数量
		
		double latestPurchasePric=commoditypo.getLatestPurchasePrice(); 	// 最近进价
		double latestRetailPrice=commoditypo.getLatestRetailPrice(); 		// 最近零售价
		String date=commoditypo.getDate();
		double averagePrice=commoditypo.getAveragePrice();              //库存均价
		int warn=commoditypo.getWarnQuantity();
		commodity=new Commodity(commoditySortName, commoditySortID, commodityName, commodityID, commodityModel, inventoryQuantity, purchasePrice, retailPrice,  latestPurchasePric, latestRetailPrice,date,averagePrice,warn);
		   //PO转成VO还要专门写
	
	}
	
		return commodity;
	}

	//进货还是进货的退货,商品类，进价，进价数量
	   public ResultMessage importUpadte (UpdateType type,Commodity commodity,double purchasePrice,int purchaseQuantity){
	         
		   
		   return null;
		
		   
	   }
	   //销售还是销售退货，商品类，零售价，零售数量，出厂日期（销售的那天）
      public ResultMessage saleUpdate(UpdateType type,Commodity commodity,double retailPrice  ,int  retailQuantity, String date){
		
    	  
    	  return null;
    	  
      }*/
  	public String getDate() {
		return date;
	}
	public int getWarnQuantity() {
		return warnQuantity;
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
	public double getAveragePrice() {
		return averagePrice;
	}
	public void setLatestPurchasePrice(double latestPurchasePrice) {
		this.latestPurchasePrice = latestPurchasePrice;
	}
	public void setLatestRetailPrice(double latestRetailPrice) {
		this.latestRetailPrice = latestRetailPrice;
	}
	/**
	 * @return the latestPurchasePriceForChengBenTiaoJiao
	 */
	public double getLatestPurchasePriceForChengBenTiaoJiao() {
		return latestPurchasePriceForChengBenTiaoJiao;
	}
	/**
	 * @param latestPurchasePriceForChengBenTiaoJiao the latestPurchasePriceForChengBenTiaoJiao to set
	 */
	public void setLatestPurchasePriceForChengBenTiaoJiao(
			double latestPurchasePriceForChengBenTiaoJiao) {
		this.latestPurchasePriceForChengBenTiaoJiao = latestPurchasePriceForChengBenTiaoJiao;
	}
	public boolean sameCommodityKind(Commodity commodity) {
		
		return commodity.getCommodityName().equals(commodityName) && commodity.getCommodityModel().equals(commodityModel);
	//商品名相同且型号相同，则属于同一种商品	
	}
	/**
	 * @param inventoryQuantity the inventoryQuantity to set
	 */
	public void setInventoryQuantity(int inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}
	
}
