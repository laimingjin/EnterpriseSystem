package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

import po.CommodityPO;
import po.CommoditySortPO;
//import vo.CommodityPO;
import dataservice.CommoditySortDataService;
import enumClass.ResultMessage;

public class CommoditySortData_Ser extends UnicastRemoteObject  implements CommoditySortDataService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		// //////////////////////////////////////////////
		// 初始化读写器
		ReadAndWrite rw = new ReadAndWrite();

		// TODO 默认地址，应该写入配置
		String address = "file/commoditySort.out";

		// 存放所有PO的类
		ArrayList<CommoditySortPO> commoditySortPOs;

		/**
		 * 构造器：初始化Data
		 */
		@SuppressWarnings("unchecked")
		public  CommoditySortData_Ser() throws RemoteException {
		commoditySortPOs = (ArrayList<CommoditySortPO>) rw.read(address);

			// 若为第一次运行则初始化
			if (commoditySortPOs == null) {
				commoditySortPOs = new ArrayList<CommoditySortPO>();
				output();
			}
		}

		/**
		 * 更新数据 2014年11月30日
		 */
		private void output() {
			rw.write(commoditySortPOs, address);
		}

		// //////////////////////////////////////////////

	public ResultMessage init() throws RemoteException {
		CommodityPO f1=new CommodityPO( 005,"电灯泡",  0001,"飞利浦", "990e", 0, 100,  100,  0, 0, "20141206", 0,10);
		CommodityPO f2=new CommodityPO( 005,"电灯泡",  0002,"阳光", "991w", 0, 100,  100,  0, 0, "20141206", 0,10);
		CommodityPO f3=new CommodityPO( 005,"电灯泡",  0003,"阳谷", "992r", 0, 100,  100,  0, 0, "20141206", 0,10);
		ArrayList<CommodityPO> commodityPOs=new ArrayList<CommodityPO>();
		commodityPOs.add(f1);commodityPOs.add(f2);commodityPOs.add(f3);
		
		CommodityPO f4=new CommodityPO( 004,"电灯管",  0004,"飞利浦", "666", 0, 30,  30,  0, 0, "20141226", 0,20);
		CommodityPO f5=new CommodityPO( 004,"电灯管",  0005,"阳花牌", "999", 0, 50,  50,  0, 0, "20141226", 0,30);
		ArrayList<CommodityPO> commodityPOs2=new ArrayList<CommodityPO>();
		commodityPOs2.add(f4);commodityPOs2.add(f5);
		CommodityPO f6=new CommodityPO( 006,"遮光布",  0006,"春花牌", "33e", 0, 200,  200,  0, 0, "20141226", 0,10);
		CommodityPO f7=new CommodityPO( 006,"遮光布",  0007,"玖姿牌", "66w", 0, 100,  100,  0, 0, "20141226", 0,0);
		ArrayList<CommodityPO> commodityPOs3=new ArrayList<CommodityPO>();
	commodityPOs3.add(f6);commodityPOs3.add(f7);
		CommodityPO f8=new CommodityPO( 007,"贴花", 8,"春光牌", "5r", 0, 10,  10,  0, 0, "20141216", 0,5);
		CommodityPO f9=new CommodityPO( 007,"贴花",  9,"雅丽牌", "4s", 0, 10,  10,  0, 0, "20141216", 0,15);
		ArrayList<CommodityPO> commodityPOs4=new ArrayList<CommodityPO>();
		commodityPOs4.add(f8);commodityPOs4.add(f9);
		
	

		CommoditySortPO e3=new 	CommoditySortPO ("贴花", 007, "装饰品", true, null, commodityPOs4);
		   CommoditySortPO e4=new 	CommoditySortPO ("遮光布", 006, "装饰品", true, null, commodityPOs3);
		   ArrayList<CommoditySortPO> a3=new ArrayList<CommoditySortPO> ();
		   a3.add(e3);a3.add(e4);
		
		CommoditySortPO e1=new 	CommoditySortPO ("电灯泡", 005, "电灯配件", true, null, commodityPOs);
	   CommoditySortPO e2=new 	CommoditySortPO ("电灯管", 004, "电灯配件", true, null, commodityPOs2);
	   ArrayList<CommoditySortPO> a2=new ArrayList<CommoditySortPO> ();
	   a2.add(e1);a2.add(e2);
 
	   
	   CommoditySortPO d2=new 	CommoditySortPO ("装饰品", 001, "商品", false, a3, null);
	   CommoditySortPO d3=new 	CommoditySortPO ("电灯", 002, "商品", false, null, null);
	   CommoditySortPO d4=new 	CommoditySortPO ("电灯配件", 003, "商品", false, a2, null);
	   ArrayList<CommoditySortPO> a1=new ArrayList<CommoditySortPO> ();
	   a1.add(d2);a1.add(d3);a1.add(d4);
	
		CommoditySortPO c1=new 	CommoditySortPO ("商品", 000, "商品根", true, a1, null);
commoditySortPOs.add(c1);
commoditySortPOs.add(d2);
commoditySortPOs.add(d3);
commoditySortPOs.add(d4);

commoditySortPOs.add(e2);
commoditySortPOs.add(e1);

commoditySortPOs.add(e4);
commoditySortPOs.add(e3);
output();
		
		return null;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	private int containsSameID(int ID) {
		if(commoditySortPOs.size()!=0){
			for (int j = 0; j < commoditySortPOs.size(); j++) {
			if (commoditySortPOs.get(j).getCommoditySortID()== ID) {
				return j;
			}
		}
		}
		
		return -1;
	}
	
	private int containsSameName(String name) {
		if(commoditySortPOs.size()!=0){
			for (int j = 0; j < commoditySortPOs.size(); j++) {
			if (commoditySortPOs.get(j).getCommoditySortName() .equals( name)) {
				return j;
			}
		}
		}
		
		return -1;
	}
	public ResultMessage add(CommoditySortPO p) throws RemoteException {
		if (containsSameName(p.getCommoditySortName())!=-1) {
			return ResultMessage.Exist;
		}
		commoditySortPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CommoditySortPO p) throws RemoteException {
		int index = containsSameID(p.getCommoditySortID());
		if (index == -1) {
			return ResultMessage.NotExist;
		}
		commoditySortPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(CommoditySortPO p) throws RemoteException {
		int index = containsSameID(p.getCommoditySortID());
		if (index == -1) {
			return ResultMessage.NotExist;
		}
		commoditySortPOs.set(index, p);
		output();
		return ResultMessage.SUCCESS;
	}

	public CommoditySortPO find(String name) throws RemoteException,
			ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		int index = containsSameName(name);
		if (index == -1) {
			System.out.println(2222);
			return null;
		}
System.out.println(commoditySortPOs.get(index).getCommoditySortName());
		return commoditySortPOs.get(index);
	}

	public int getFinalID() throws RemoteException {
		// TODO Auto-generated method stub
        int result=0;   
		if(commoditySortPOs.size()==0){
            return 0;
            }else{
		 result=commoditySortPOs.get(commoditySortPOs.size() - 1).getCommoditySortID();
            }
		return result;
	}

	public CommoditySortPO find(int id) throws RemoteException {
		int index = containsSameID(id);
		if (index == -1) {
			return null;
		}

		return commoditySortPOs.get(index);
	}

}
