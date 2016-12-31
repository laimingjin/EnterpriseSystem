package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.CashListPO;
import businesslogic.EntryLineItem;
import businesslogic.EntryList;
import dataservice.CashListDataService;
import enumClass.ResultMessage;

public class CashListData_Ser extends UnicastRemoteObject implements
		CashListDataService {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/cashList.out";

	// 存放所有PO的类
	ArrayList<CashListPO> cashListPOs;

	// 单例模式，Account只需要一个实例
	// private static final CashListData_Ser CASHLIST_DATA_SER = new
	// CashListData_Ser();
	// public static CashListData_Ser getInstance() {
	// return CASHLIST_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	@SuppressWarnings("unchecked")
	public CashListData_Ser() throws RemoteException {
		cashListPOs = (ArrayList<CashListPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (cashListPOs == null) {
			cashListPOs = new ArrayList<CashListPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(cashListPOs, address);
	}

	// //////////////////////////////////////////////
	public int containsSameID(String ID) {
		if(cashListPOs.size()!=0){
			for (int j = 0; j < cashListPOs.size(); j++) {
				if (cashListPOs.get(j).getDocumentID().equals(ID)) {
					return j;
				}
			}
		}
	
		return -1;
	}

	public ResultMessage add(CashListPO p) throws RemoteException {
		// TODO Auto-generated method stub
		cashListPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(CashListPO p) throws RemoteException {
		// TODO Auto-generated method stub
		String ID = p.getDocumentID();
		
		cashListPOs.set(containsSameID(ID), p);
		output();
		// show();
		return ResultMessage.SUCCESS;
	}

	public CashListPO findByDocumentID(String documentID)
			throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(documentID);
		if (index == -1) {
			return null;
		} else {
			// show();

			return cashListPOs.get(index);
		}
	}
	//专为红冲和红冲复制写的find方法
		public CashListPO findByIDForWriteBack(String documentID) throws RemoteException {
			ArrayList<CashListPO>pos=new ArrayList<CashListPO>();
			if(cashListPOs.size()!=0){
				for (int j = 0; j < cashListPOs.size(); j++) {
					if (cashListPOs.get(j).getDocumentID().equals(documentID)) {
						pos.add(cashListPOs.get(j));
					}
				}
			}
			
			if(pos!=null){
				return pos.get(pos.size()-1);
			}
			return null;
		}
		
	public ArrayList<CashListPO> findByTime(String time) throws RemoteException {
		// TODO Auto-generated method stub
		if(cashListPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return cashListPOs;
		}else{
			String[] times = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			ArrayList<CashListPO> al = new ArrayList<CashListPO>();
			for (int i = 0; i < cashListPOs.size(); i++) {
				long date = Long.parseLong(cashListPOs.get(i).getDate());
				if (date >= time1 && date <= time2) {
					al.add(cashListPOs.get(i));
				}
			}
			System.out.println();
			return al;
		}
		
	}

	public ArrayList<CashListPO> findByUser(String userName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(cashListPOs.size()==0){
			return null;
		}
		ArrayList<CashListPO> al = new ArrayList<CashListPO>();
		for (int i = 0; i < cashListPOs.size(); i++) {
			if (cashListPOs.get(i).getUserName().equals(userName)) {
				al.add(cashListPOs.get(i));
			}
		}
		// show();
		System.out.println();
		return al;
	}

	// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号
	public String getNewID() throws RemoteException {
		String date=getDate();
		if(cashListPOs.size()==0){
			return "XJFYD-" + date + "-" +"00001";
		}
		String formerID = cashListPOs.get(cashListPOs.size() - 1)
				.getDocumentID();
		String sep[] = formerID.split("-");
		
		if (!date.equals(sep[1])) {
			return "XJFYD-" + date + "-00001";
		} else {
			int time = Integer.parseInt(sep[2]);
			if (time == 99999) {
				System.out.println("error");
				return null;
			} else {
				time++;
			}
			String iss = String.format("%5d", time).replace(" ", "0");
			return "XJFYD-" + date + "-" + iss;
		}
	}

	private String getDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		return dateFormat.format( now ); 
	}

	public void show() {
		for (int i = 0; i < cashListPOs.size(); i++) {
			String list = cashListPOs.get(i).getDate() + " "
					+ cashListPOs.get(i).getDocumentID() + " " + " "
					+ cashListPOs.get(i).getUserID() + " "
					+ cashListPOs.get(i).getUserName() + " "
					+ cashListPOs.get(i).getAccountName() + " ";
			ArrayList<EntryLineItem> t = cashListPOs.get(i).getEntryList()
					.getTheList();
			for (int j = 0; j < t.size(); j++) {
				list = list + t.get(j).getEntryName() + " "
						+ t.get(j).getEntryPrice() + " " + t.get(j).getRemark()
						+ " ";
			}
			list = list + cashListPOs.get(i).getTotalPrice() + " "
					+ cashListPOs.get(i).isSend() + " "
					+ cashListPOs.get(i).isPass() + " "
					+ cashListPOs.get(i).isDealed();
			System.out.println(list);
		}

	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<CashListPO> displayAll() throws RemoteException {
		// TODO Auto-generated method stub
		return cashListPOs;
	}

	public ResultMessage init()  throws RemoteException{
		EntryList entryList1=new EntryList();
		entryList1.addItem(new EntryLineItem("阿伟", 3000, ""));
		entryList1.addItem(new EntryLineItem("阿龙", 1000, ""));
		entryList1.addItem(new EntryLineItem("阿鸣 ", 4000, ""));
		EntryList entryList2=new EntryList();
		entryList2.addItem(new EntryLineItem("阿兰", 800, ""));
		entryList2.addItem(new EntryLineItem("阿凤", 600, ""));
		entryList2.addItem(new EntryLineItem("阿珍 ", 2000, ""));
		cashListPOs.add(new CashListPO("20140301", "XJFYD-20140301-32299", 3, "cw", "钱二", entryList1,entryList1.getTotalPrice(), false, false, false));
		cashListPOs.add(new CashListPO("20140606", "XJFYD-20140606-49777", 3, "cw", "赵一", entryList2, entryList2.getTotalPrice(), false, false, false));
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CashListPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			System.out.println("无该ID的PO存在");
			return ResultMessage.FAIL;
		} else {
			cashListPOs.remove(index);
			output();
			return ResultMessage.SUCCESS;
		}
	}
}
