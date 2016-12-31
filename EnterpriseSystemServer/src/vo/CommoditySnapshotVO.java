package vo;

import java.util.ArrayList;

import po.CommoditySnapshotPO;
import businesslogic.Commodity;

public class CommoditySnapshotVO {
	
	private ArrayList<Commodity> commodity;  //各种商品的arrlist
	
	private	String lot; 				     // 批次(日期)
	private	int lotNumber; 					 // 批号（序号）

	//有大问题
	public CommoditySnapshotVO ( 	ArrayList<Commodity> ex,String l,int ln){
		commodity=ex;

	       lot=l; 				     // 批次(日期)
		 lotNumber=ln; 					 // 批号（序号）
				
		
	}
	public CommoditySnapshotVO(CommoditySnapshotPO commoditySnapshotPO){
		this.commodity=commoditySnapshotPO.getCommodity();
		this.lot=commoditySnapshotPO.getLot();
		this.lotNumber=commoditySnapshotPO.getLotNumber();
		
	}
	public ArrayList<Commodity> getCommodity() {
		return commodity;
	}
	public String getLot() {
		return lot;
	}
	public int getLotNumber() {
		return lotNumber;
	}
	
	
}
