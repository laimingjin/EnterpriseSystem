package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CommodityGiftPO;
import po.CommodityPO;
import businesslogic.Commodity;
import businesslogic.Customer;
import dataservice.CommodityGiftDataService;
import enumClass.ResultMessage;

public class CommodityGiftData_Ser extends UnicastRemoteObject implements
		CommodityGiftDataService {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/commodityGift.out";

	// 存放所有PO的类
	ArrayList<CommodityGiftPO> commodityGiftPOs;

	// //单例模式，Account只需要一个实例
	// private static final CommodityGiftData_Ser COMMODITYGIFT_DATA_SER = new
	// CommodityGiftData_Ser();
	// public static CommodityGiftData_Ser getInstance() {
	// return COMMODITYGIFT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	@SuppressWarnings("unchecked")
	public CommodityGiftData_Ser() throws RemoteException {
		commodityGiftPOs = (ArrayList<CommodityGiftPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (commodityGiftPOs == null) {
			commodityGiftPOs = new ArrayList<CommodityGiftPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(commodityGiftPOs, address);
	}

	// //////////////////////////////////////////////
	public int containsSameID(long ID) {
		if(commodityGiftPOs.size()!=0){
			for (int j = 0; j < commodityGiftPOs.size(); j++) {
				if (commodityGiftPOs.get(j).getDocumentID() == ID) {
					return j;
				}
			}
		}
		
		return -1;
	}

	public ResultMessage add(CommodityGiftPO p) throws RemoteException {
		// TODO Auto-generated method stub
		commodityGiftPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CommodityGiftPO p) throws RemoteException {
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			return ResultMessage.FAIL;
		} else {
			commodityGiftPOs.remove(index);
			output();
			return ResultMessage.SUCCESS;
		}

	}

	public ResultMessage update(CommodityGiftPO p) {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			return ResultMessage.FAIL;
		} else {
			commodityGiftPOs.set(index, p);
			output();
			return ResultMessage.SUCCESS;
		}

	}

	public ArrayList<CommodityGiftPO>displayAll()throws RemoteException {
		return commodityGiftPOs;
	}
	public CommodityGiftPO find(long id) {
		// TODO Auto-generated method stub
		int index = containsSameID(id);
		if (index == -1) {
			return null;
		} else {
			return commodityGiftPOs.get(index);
		}

	}
	//专为红冲和红冲复制写的find方法
		public CommodityGiftPO findByIDForWriteBack(long documentID) throws RemoteException {
			if(commodityGiftPOs.size()==0){
				return null;
			}
			ArrayList<CommodityGiftPO>pos=new ArrayList<CommodityGiftPO>();
			for (int j = 0; j < commodityGiftPOs.size(); j++) {
				if (commodityGiftPOs.get(j).getDocumentID()==documentID) {
					pos.add(commodityGiftPOs.get(j));
				}
			}
			{
				return pos.get(pos.size()-1);
			}
		}
		
	public ArrayList<CommodityGiftPO> finds(String time)
			throws RemoteException{
		// TODO Auto-generated method stub
		if(commodityGiftPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return commodityGiftPOs;
		}else {
			ArrayList<CommodityGiftPO> al = new ArrayList<CommodityGiftPO>();
			String []times=time.split(",");
			long time11 = Long.parseLong(times[0]);
			long time22 = Long.parseLong(times[1]);
			for (int i = 0; i < commodityGiftPOs.size(); i++) {
				long date = Long.parseLong(commodityGiftPOs.get(i).getDate());
				if (date >= time11 && date <= time22) {
					al.add(commodityGiftPOs.get(i));
				}
			}
			return al;
		}
		
	}

	public ResultMessage init() throws RemoteException {
		// TODO Auto-generated method stub
		Commodity commodity1=new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 100, 100,  100,  150, 170, "20141206", 100,10);
		Customer customer1 =new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
		CommodityGiftPO po1=new CommodityGiftPO("20140301", 01, commodity1, 50, customer1, true, true, true);
		Commodity commodity2=new Commodity("电灯泡",003,  "阳谷",0003, "992r", 0, 100,  100,  130, 200, "20141206", 0,10);
		Customer customer2 =new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
		
		CommodityGiftPO po2=new CommodityGiftPO("20140412", 20333, commodity2, 50, customer2, true, true, false);
	//	CommodityGiftPO po2=new CommodityGiftPO("20140412", 02, commodity2, 50, customer2, true, true, false);
		commodityGiftPOs.add(po1);
		commodityGiftPOs.add(po2);
		output();
		return ResultMessage.SUCCESS;
		//commodityGiftPOs.add(po2);
		//output();
	//	return null;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
public long getFinalID() throws RemoteException{
	if(commodityGiftPOs.size()==0){
		return 0;
	}else{
		return commodityGiftPOs.get(commodityGiftPOs.size()-1).getDocumentID();
	}
	
	
}
}
