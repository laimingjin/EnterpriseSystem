package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CommodityPO;
import po.UserPO;
import dataservice.CommodityDataService;
import enumClass.POSITION;
import enumClass.ResultMessage;

public class CommodityData_Ser extends UnicastRemoteObject implements
		CommodityDataService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/commodity.out";

	// 存放所有PO的类
	 ArrayList<CommodityPO> commodityPOs;


	/**
	 * 构造器：初始化Data
	 */
	public  CommodityData_Ser() throws RemoteException {
		commodityPOs = (ArrayList<CommodityPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (commodityPOs == null) {
			commodityPOs = new ArrayList<CommodityPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(commodityPOs, address);
	}

	// //////////////////////////////////////////////
	private int containsSameID(int ID) {
		if(commodityPOs.size()!=0){
			for (int j = 0; j < commodityPOs.size(); j++) {
			if (commodityPOs.get(j).getCommodityID() == ID) {
				return j;
			}
		}
		}
		
		return -1;
	}

	public ResultMessage add(CommodityPO p) throws RemoteException {
		commodityPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CommodityPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getCommodityID());
		if (index == -1) {
			return ResultMessage.FAIL;
		}
		commodityPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(CommodityPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getCommodityID());
		if (index == -1) {
			return ResultMessage.FAIL;
		}
		commodityPOs.set(index, p);
		output();
		return ResultMessage.SUCCESS;
	}

	public CommodityPO find(int id) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(id);
		if (index == -1) {
			return null;
		}

		return commodityPOs.get(index);
	}

	// ////////期初建账时候改用所有商品都显示  这个通过时间查找就舍弃
	public ArrayList<CommodityPO> finds(String time1, String time2)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(commodityPOs.size()==0){
			return null;
		}
		ArrayList<CommodityPO> al = new ArrayList<CommodityPO>();
		long time11 = Long.parseLong(time1);
		long time22 = Long.parseLong(time2);
		for (int i = 0; i < commodityPOs.size(); i++) {
			long date = Long.parseLong(commodityPOs.get(i).getDate());
			if (date >= time11 && date <= time22) {
				al.add(commodityPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<CommodityPO> findVague(String key) throws RemoteException {
		// TODO Auto-generated method stub
		if(commodityPOs.size()==0){
			return null;
		}
		ArrayList<CommodityPO> al = new ArrayList<CommodityPO>();
		for (int i = 0; i < commodityPOs.size(); i++) {
			if (commodityPOs.get(i).getCommoditySortName().contains(key)
					|| commodityPOs.get(i).getCommodityName().contains(key)
					|| commodityPOs.get(i).getCommodityModel().contains(key)) {
				al.add(commodityPOs.get(i));

			}

		}

		return al;
	}

	public CommodityPO find(String name, String model) throws RemoteException {
		// TODO Auto-generated method stub
		if(commodityPOs.size()==0){
			return null;
		}
		for (int i = 0; i < commodityPOs.size(); i++) {
			if (commodityPOs.get(i).getCommodityName().equals(name)
					&& commodityPOs.get(i).getCommodityModel().equals(model)) {
				return commodityPOs.get(i);
			}
		}
		return null;
	}

	// public ArrayList<SnapshotPO> getSnapshot() {
	// // TODO Auto-generated method stub
	// ArrayList<SnapshotPO> al=new ArrayList<SnapshotPO>();
	// for(int i=0;i<commodityPOs.size();i++){
	// CommodityPO com=commodityPOs.get(i);
	// SnapshotPO po=new
	// SnapshotPO(com.getCommodityName(),com.getCommodityModel(),com.getInventoryQuantity(),com.getAveragePrice(),com.getDate())
	// }
	//
	//
	// return null;
	// }

	public ArrayList<CommodityPO> dispAll() {
	
		return commodityPOs;
	}

	public int getFinalID() {
		// TODO Auto-generated method stub
        int result=0;   
		if(commodityPOs.size()==0){
            return 0;
            }else{
	
            	result=commodityPOs.get(commodityPOs.size() - 1).getCommodityID();
		 
            }
		return result;
	}

	public ResultMessage init() {
		
		CommodityPO f1=new CommodityPO( 005,"电灯泡",  0001,"飞利浦", "990e", 150, 100,  150,  80, 150, "20141206", 150,10);
		CommodityPO f2=new CommodityPO( 005,"电灯泡",  0002,"阳光", "991w", 0, 100,  120,  90, 0, "20141206", 0,10);
		CommodityPO f3=new CommodityPO( 005,"电灯泡",  0003,"阳谷", "992r", 150, 100,  100,  0, 0, "20141206", 100,10);
		CommodityPO f4=new CommodityPO( 004,"电灯管",  0004,"飞利浦", "666", 0, 30,  30,  0, 0, "20141226", 0,20);
		CommodityPO f5=new CommodityPO( 004,"电灯管",  0005,"阳花牌", "999", 0, 50,  50,  0, 0, "20141226", 0,30);
		CommodityPO f6=new CommodityPO( 006,"遮光布",  0006,"春花牌", "33e", 100, 150,  200,  150, 200, "20141226", 150,10);
		CommodityPO f7=new CommodityPO( 006,"遮光布",  0007,"玖姿牌", "66w", 0, 100,  100,  0, 0, "20141226", 0,0);
		CommodityPO f8=new CommodityPO( 007,"贴花", 8,"春光牌", "5r", 0, 10,  10,  0, 0, "20141216", 0,5);
		CommodityPO f9=new CommodityPO( 007,"贴花",  9,"雅丽牌", "4s", 0, 10,  10,  0, 0, "20141216", 0,15);
		
		//	commodityPOs=new ArrayList<CommodityPO>();
		commodityPOs.add(f1);
		commodityPOs.add(f2);
		commodityPOs.add(f3);
		commodityPOs.add(f4);
		commodityPOs.add(f5);
		commodityPOs.add(f6);
		commodityPOs.add(f7);
		commodityPOs.add(f8);
		commodityPOs.add(f9);
		output();

		return null;
	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}
}
