package po;

import java.io.Serializable;
import java.util.ArrayList;

import vo.CommoditySnapshotVO;
import businesslogic.Commodity;

public class CommoditySnapshotPO implements Serializable{
	
	private static final long serialVersionUID = -4059846903692909584L;

	private ArrayList<Commodity> commodity;  //各种商品的arrlist
	
	private	String lot; 				     // 批次(日期)
	private	int lotNumber; 					 // 批号（序号）

	//有大问题
	public CommoditySnapshotPO (  ArrayList<Commodity>ex, String l,int ln){
		commodity=ex;

	       lot=l; 				     // 批次(日期)
		 lotNumber=ln; 					 // 批号（序号）
				
	
	}
	
	public CommoditySnapshotPO(CommoditySnapshotVO commoditySnapshotvVO) {
		commodity=commoditySnapshotvVO.getCommodity();
		lot=commoditySnapshotvVO.getLot();
		lotNumber=commoditySnapshotvVO.getLotNumber();
	}

	public  ArrayList<Commodity> getCommodity() {
		return commodity;
	}
	public String getLot() {
		return lot;
	}
	public int getLotNumber() {
		return lotNumber;
	}

	}
