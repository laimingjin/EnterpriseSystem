package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CommodityDocumentPO;
import businesslogic.Commodity;
import dataservice.CommodityDocumentDataService;
import enumClass.PROBLEM;
import enumClass.ResultMessage;

public class CommodityDocumentData_Ser extends UnicastRemoteObject implements
		CommodityDocumentDataService {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/commodityDocument.out";

	// 存放所有PO的类
	ArrayList<CommodityDocumentPO> commodityDocumentPOs;


	/**
	 * 构造器：初始化Data
	 */
	@SuppressWarnings("unchecked")
	public CommodityDocumentData_Ser() throws RemoteException {
		commodityDocumentPOs = (ArrayList<CommodityDocumentPO>) rw
				.read(address);

		// 若为第一次运行则初始化accountPOs
		if (commodityDocumentPOs == null) {
			commodityDocumentPOs = new ArrayList<CommodityDocumentPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(commodityDocumentPOs, address);
	}

	// //////////////////////////////////////////////
	public int containsSameID(int ID) {
		if(commodityDocumentPOs.size()!=0){
			for (int j = 0; j < commodityDocumentPOs.size(); j++) {
			if (commodityDocumentPOs.get(j).getDocumentID() == (ID)) {
				return j;
			}
		}
		}
		
		return -1;
	}

	public ResultMessage add(CommodityDocumentPO p) throws RemoteException {
		commodityDocumentPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CommodityDocumentPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			System.out.println("无该ID的PO存在");
			return ResultMessage.FAIL;
		} else {
			commodityDocumentPOs.remove(index);
			output();
			return ResultMessage.SUCCESS;
		}
	}

	public ResultMessage update(CommodityDocumentPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		commodityDocumentPOs.set(index, p);
		output();

		return ResultMessage.SUCCESS;
	}

	public ArrayList<CommodityDocumentPO>displayAll()throws RemoteException{
		return commodityDocumentPOs;
	}
	public CommodityDocumentPO find(int id) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(id);
		if (index == -1) {
			return null;
		} else {
			return commodityDocumentPOs.get(index);
		}
	}
	//专为红冲和红冲复制写的find方法
		public CommodityDocumentPO findByIDForWriteBack(int documentID) throws RemoteException {
			if(commodityDocumentPOs.size()==0){
				return null;
			}
			ArrayList<CommodityDocumentPO>pos=new ArrayList<CommodityDocumentPO>();
			for (int j = 0; j < commodityDocumentPOs.size(); j++) {
				if (commodityDocumentPOs.get(j).getDocumentID()==documentID) {
					pos.add(commodityDocumentPOs.get(j));
				}
			}
			return pos.get(pos.size()-1);
		}
	public ArrayList<CommodityDocumentPO> findByTime(String time)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(commodityDocumentPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return commodityDocumentPOs;
		}else{
			ArrayList<CommodityDocumentPO> al = new ArrayList<CommodityDocumentPO>();
			String times[] = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			for (int i = 0; i < commodityDocumentPOs.size(); i++) {
				long date = Long.parseLong(commodityDocumentPOs.get(i).getDate());
				if (date >= time1 && date <= time2) {
					al.add(commodityDocumentPOs.get(i));
				}
			}
			return al;
		}
	}

	public ResultMessage init() throws RemoteException {
		// TODO Auto-generated method stub
		Commodity f1=new Commodity( "电灯泡", 005, "飞利浦",0001, "990e", 200, 100,  100,  0, 0, "20141206", 0,10);
		Commodity f2=new Commodity( "电灯泡", 005, "阳光", 0002,"991w", 0, 100,  100,  0, 0, "20141206", 0,10);
		Commodity f3=new Commodity( "电灯泡",005,  "阳谷",0003, "992r", 0, 100,  100,  0, 0, "20141206", 0,10);
		CommodityDocumentPO c1=new CommodityDocumentPO("20141212", 01, f1, PROBLEM.LOSS, 150,  true, true,true);
		CommodityDocumentPO c2=new CommodityDocumentPO("20141216", 02, f2, PROBLEM.OVERFLOW, 200, true, true, false);
		
		CommodityDocumentPO c3=new CommodityDocumentPO("20141222", 03, f3, PROBLEM.OVERFLOW, 100, false, false, false);
		
		commodityDocumentPOs.add(c1);
		commodityDocumentPOs.add(c2);

		commodityDocumentPOs.add(c3);
		
		output();
		

		return null;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public int getFinalID() throws RemoteException {
		// TODO Auto-generated method stub
		  int result=0;   
			if(commodityDocumentPOs.size()==0){
	            return 0;
	            }else{
			 result=commodityDocumentPOs.get(commodityDocumentPOs.size() - 1).getDocumentID();
	
	            }
			return result;
		
	}
}
