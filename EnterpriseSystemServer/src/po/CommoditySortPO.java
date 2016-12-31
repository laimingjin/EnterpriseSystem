package po;

import java.io.Serializable;
import java.util.ArrayList;

import vo.CommoditySortVO;
import vo.CommodityVO;
import businesslogic.Commodity;
import businesslogic.CommoditySort;
public class CommoditySortPO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6027907959374556352L;
	private String commoditySortName; 					// 商品分类名称
	private 	int commoditySortID; 						// 商品分类编号
	private 	String Father;
	private 	  ArrayList<CommoditySortPO> commoditySortSonList = new ArrayList<CommoditySortPO>();  //分类下所拥有的子分类
	private 	boolean hasCommodity=false;                       //有无商品
	private   ArrayList<CommodityPO> commodityList = new ArrayList<CommodityPO>();  //分类下所拥有的商品
	  public CommoditySortPO(String csn,int csi,String f, boolean hc,ArrayList<CommoditySortPO>csl,  ArrayList<CommodityPO>cl){
		  commoditySortName=csn;
          commoditySortID=csi;
          Father=f;
          hasCommodity=hc;
          commoditySortSonList=csl;
          commodityList=cl;
	  }
	  
		  
	  
		  
		  
	public CommoditySortPO(CommoditySortVO vo) {
		this.commoditySortName = vo.getCommoditySortName();
		this.commoditySortID = vo.getCommoditySortID();
		Father = vo.getFather();

		ArrayList<CommoditySortVO> commoditySortVOs=vo.getCommoditySortSonList();
		if(commoditySortVOs!=null){
		for (int i = 0; i < commoditySortVOs.size(); i++) {
			commoditySortSonList.add(new CommoditySortPO(commoditySortVOs.get(i)));
		}
		}else{
			commoditySortSonList=null;
		}
		this.hasCommodity = vo.isHasCommodity();
		ArrayList<CommodityVO> commodityVOs=vo.getCommodityList();
		if(commodityVOs!=null){
			
	
		for (int i = 0; i < commodityVOs.size(); i++) {
			commodityList.add(new CommodityPO(commodityVOs.get(i)));
		}
		}else{
			commodityList=null;
		}
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





	public void setCommoditySortSonList(
			ArrayList<CommoditySortPO> commoditySortSonList) {
		this.commoditySortSonList = commoditySortSonList;
	}





	public void setHasCommodity(boolean hasCommodity) {
		this.hasCommodity = hasCommodity;
	}





	public void setCommodityList(ArrayList<CommodityPO> commodityList) {
		this.commodityList = commodityList;
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
	public ArrayList<CommoditySortPO> getCommoditySortSonList() {
		return commoditySortSonList;
	}
	public boolean isHasCommodity() {
		return hasCommodity;
	}
	public ArrayList<CommodityPO> getCommodityList() {
		return commodityList;
	}
	
}
