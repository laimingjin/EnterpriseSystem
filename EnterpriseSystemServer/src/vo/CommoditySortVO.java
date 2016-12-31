package vo;

import java.util.ArrayList;

import po.CommodityPO;
import po.CommoditySortPO;
import businesslogic.Commodity;
import businesslogic.CommoditySort;

public class CommoditySortVO {

	private String commoditySortName; 					// 商品分类名称
	



	private 	int commoditySortID; 						// 商品分类编号
	private 	String Father;
	private 	  ArrayList<CommoditySortVO> commoditySortSonList = new ArrayList<CommoditySortVO>();  //分类下所拥有的子分类
	
	private 	boolean hasCommodity=false;                       //有无商品
	private   ArrayList<CommodityVO> commodityList = new ArrayList<CommodityVO>();  //分类下所拥有的商品
	  public CommoditySortVO(String csn,int csi,String f, boolean hc,ArrayList<CommoditySortVO>csl,  ArrayList<CommodityVO>cl){
		  commoditySortName=csn;
          commoditySortID=csi;
          Father=f;
          hasCommodity=hc;
          commoditySortSonList=csl;
          commodityList=cl;
	  }
	  public CommoditySortVO(CommoditySortPO commoditySortPO){
		  this.commoditySortName = commoditySortPO.getCommoditySortName();
			this.commoditySortID = commoditySortPO.getCommoditySortID();
			Father = commoditySortPO.getFather();

			ArrayList<CommoditySortPO> commoditySortPOs=commoditySortPO.getCommoditySortSonList();
		if(commoditySortPOs!=null){
				for (int i = 0; i < commoditySortPOs.size(); i++) {
				       CommoditySortVO cVo=new CommoditySortVO(commoditySortPOs.get(i));
					   this.commoditySortSonList.add(cVo);
			
				}
			}else{
				commoditySortSonList=null;
			}
			
			this.hasCommodity = commoditySortPO.isHasCommodity();
			ArrayList<CommodityPO> commodityPOs=commoditySortPO.getCommodityList();
			if(commodityPOs!=null){
			for (int i = 0; i <commodityPOs.size(); i++) {
				this.commodityList.add(new CommodityVO(commodityPOs.get(i)));
			}
			}else{
				commodityList=null;
			}
	  }
		  
		  
		  
	public String getCommoditySortName() {
		return commoditySortName;
	}
	public int getCommoditySortID() {
		return commoditySortID;
	}
	 public String getFather() {
			return Father;
		}
	public ArrayList<CommoditySortVO> getCommoditySortSonList() {
		return commoditySortSonList;
	}
	public boolean isHasCommodity() {
		return hasCommodity;
	}
	public ArrayList<CommodityVO> getCommodityList() {
		return commodityList;
	}
	public String toString(){
		return commoditySortName;
	}
	public void setCommoditySortSonList(
			ArrayList<CommoditySortVO> commoditySortSonList) {
		this.commoditySortSonList = commoditySortSonList;
	}




	public void setHasCommodity(boolean hasCommodity) {
		this.hasCommodity = hasCommodity;
	}




	public void setCommodityList(ArrayList<CommodityVO> commodityList) {
		this.commodityList = commodityList;
	}
	public void setCommoditySortName(String commoditySortName) {
		this.commoditySortName = commoditySortName;
	}




	public void setCommoditySortID(int commoditySortID) {
		this.commoditySortID = commoditySortID;
	}




	public void setFather(String father) {
		Father = father;
	}

}
