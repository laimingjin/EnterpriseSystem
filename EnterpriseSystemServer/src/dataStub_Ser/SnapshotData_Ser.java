package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.Commodity;
import po.CommodityDocumentPO;
import po.CommodityPO;
import po.CommoditySnapshotPO;
import dataservice.SnapshotDataService;
import enumClass.ResultMessage;

public class SnapshotData_Ser extends UnicastRemoteObject implements
		SnapshotDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/snapshot.out";

	// 存放所有PO的类
	ArrayList<CommoditySnapshotPO> snapshotPOs;

	// 得到商品
//	CommodityData_Ser commodityData_Ser =new  CommodityData_Ser();
//	ArrayList<CommodityPO> coms = commodityData_Ser.dispAll();

	// //单例模式，Account只需要一个实例
	// private static final SnapshotData_Ser SNAPSHOT_DATA_SER = new
	// SnapshotData_Ser();
	// public static SnapshotData_Ser getInstance() {
	// return SNAPSHOT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public SnapshotData_Ser() throws RemoteException {
		snapshotPOs = (ArrayList<CommoditySnapshotPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (snapshotPOs == null) {
			snapshotPOs = new ArrayList<CommoditySnapshotPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(snapshotPOs, address);
	}

	// //////////////////////////////////////////////

//	// 在建立今天的库存盘点之前要进行这步操作才能得到一个SnapshotPO
//	public CommoditySnapshotPO beforeAdd() throws RemoteException {
//	//	ArrayList<SnapshotLineItem> al = new ArrayList<SnapshotLineItem>();
//		Date now = new Date();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
//		String dataNow = dateFormat.format( now ); 
//ArrayList<Commodity >commodities=new ArrayList<Commodity>();
//
//		for (int i = 0; i < coms.size(); i++) {
//			commodities.add(new Commodity(coms.get(i)));
//		}
//		return new CommoditySnapshotPO(commodities, dataNow, dataNow);
//	}

	public ResultMessage add(CommoditySnapshotPO p)  throws RemoteException{
		snapshotPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public int getFinalID() throws RemoteException {
		// TODO Auto-generated method stub
		   int result=0;   
			if(snapshotPOs.size()==0){
	            return 0;
	            }else{
			 result=snapshotPOs.get(snapshotPOs.size() - 1).getLotNumber();
	            }
			return result;
	}

	public ResultMessage init() throws RemoteException {
		Commodity f1=new Commodity( "电灯泡", 005, "飞利浦",0001, "990e", 200, 100,  100,  0, 0, "20141206", 0,10);
		Commodity f2=new Commodity( "电灯泡", 005, "阳光", 0002,"991w", 0, 100,  100,  0, 0, "20141206", 0,10);
	ArrayList<Commodity>commodities=new ArrayList<Commodity>();
	commodities.add(f1);
	commodities.add(f2);
		CommoditySnapshotPO commoditySnapshotPO=new CommoditySnapshotPO(commodities, "20141215", 1);
		
		CommoditySnapshotPO commoditySnapshotPO1=new CommoditySnapshotPO(commodities, "20141216", 2);
		
		CommoditySnapshotPO commoditySnapshotPO2=new CommoditySnapshotPO(commodities, "20141217", 3);
		snapshotPOs=new ArrayList<CommoditySnapshotPO>();
		snapshotPOs.add(commoditySnapshotPO);
		snapshotPOs.add(commoditySnapshotPO1);
		snapshotPOs.add(commoditySnapshotPO2);
	
		
		output();
		
		return null;
	}

	public CommoditySnapshotPO findBylot(String date) throws RemoteException {
		
	if(snapshotPOs.size()==0){
		return null;
	}
		CommoditySnapshotPO commoditySnapshotPO=null;
		
	
		for (int i = 0; i < snapshotPOs.size(); i++) {
			System.out.println(snapshotPOs.size());
			String time=snapshotPOs.get(i).getLot();
			if (time .equals(date)) {
			
			commoditySnapshotPO=snapshotPOs.get(i);
			}
		}
		// show();
		// System.out.println();
		return commoditySnapshotPO;
	}

	
}
