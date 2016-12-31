
package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.CommoditySort;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import po.AccountPO;
import po.ReceiptPO;
import po.SaleDocumentPO;
import dataservice.ReceiptDataService;
import enumClass.ResultMessage;

public class ReceiptData_Ser extends UnicastRemoteObject implements
		ReceiptDataService {

	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/receipt.out";

	// 存放所有PO的类
	ArrayList<ReceiptPO> receiptPOs;

	// //单例模式，Account只需要一个实例
	// private static final ReceiptData_Ser RECEIPT_DATA_SER = new
	// ReceiptData_Ser();
	// public static ReceiptData_Ser getInstance() {
	// return RECEIPT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public ReceiptData_Ser() throws RemoteException {
		receiptPOs = (ArrayList<ReceiptPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (receiptPOs == null) {
			receiptPOs = new ArrayList<ReceiptPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(receiptPOs, address);
	}


	private int containsSameID(String ID) throws RemoteException {
		if(receiptPOs.size()!=0){
			for (int j = 0; j < receiptPOs.size(); j++) {
				if (receiptPOs.get(j).getDocumentID().equals(ID)) {
					return j;
				}
			}
		}
		
		return -1;
	}

	public ArrayList<ReceiptPO> displayAll() throws RemoteException {
		return receiptPOs;
	}

	public ResultMessage add(ReceiptPO p) throws RemoteException {
		// TODO Auto-generated method stub
		receiptPOs.add(p);
		output();
		return ResultMessage.SUCCESS;
	}
	public ResultMessage delete(ReceiptPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			System.out.println("无该ID的PO存在");
			return ResultMessage.FAIL;
		} else {
			receiptPOs.remove(index);
			output();
			return ResultMessage.SUCCESS;
		}
	}
	public ResultMessage update(ReceiptPO p) throws RemoteException {
		// TODO Auto-generated method stub
		String ID = p.getDocumentID();
		receiptPOs.set(containsSameID(ID), p);
		output();
		return ResultMessage.SUCCESS;
	}

	public ReceiptPO findByDocumentID(String documentID) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(documentID);
		if (index == -1) {
			return null;
		} else {
			return receiptPOs.get(index);
		}
	}
	//专为红冲和红冲复制写的find方法
		public ReceiptPO findByIDForWriteBack(String documentID) throws RemoteException {
		if(receiptPOs.size()==0){
			return null;
		}
			ArrayList<ReceiptPO>pos=new ArrayList<ReceiptPO>();
			for (int j = 0; j < receiptPOs.size(); j++) {
				if (receiptPOs.get(j).getDocumentID().equals(documentID)) {
					pos.add(receiptPOs.get(j));
				}
			}
			if(pos!=null){
				return pos.get(pos.size()-1);
			}
			return null;
		}
	public ArrayList<ReceiptPO> findByCustomer(String customerName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(receiptPOs.size()==0){
			return null;
		}
		if(customerName.equals("")){
			return receiptPOs;
		}
		ArrayList<ReceiptPO> al = new ArrayList<ReceiptPO>();
		for (int i = 0; i < receiptPOs.size(); i++) {
			if (receiptPOs.get(i).getCustomerName().equals(customerName)) {
				al.add(receiptPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<ReceiptPO> findByTime(String time) throws RemoteException {
		// TODO Auto-generated method stub
		if(receiptPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return receiptPOs;
		}else{
			String[] times = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			ArrayList<ReceiptPO> al = new ArrayList<ReceiptPO>();
			for (int i = 0; i < receiptPOs.size(); i++) {
				long date = Long.parseLong(receiptPOs.get(i).getDate());
				if (date >= time1 && date <= time2) {
					al.add(receiptPOs.get(i));
				}
			}
			return al;
		}
		
	}

	public ArrayList<ReceiptPO> findByUser(String userName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(receiptPOs.size()==0){
			return null;
		}
		if(userName.equals("")){
			return receiptPOs;
		}
		ArrayList<ReceiptPO> al = new ArrayList<ReceiptPO>();
		for (int i = 0; i < receiptPOs.size(); i++) {
			if (receiptPOs.get(i).getUserName().equals(userName)) {
				al.add(receiptPOs.get(i));
			}
		}
		return al;
	}

	// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号
	public String getNewID() throws RemoteException {
		// TODO 单据逻辑，如果单据库为空
		if (receiptPOs.size()==0) {
		 		return "SKD" + "-" + getDate() + "-00001";
		}
		String formerID = receiptPOs.get(receiptPOs.size() - 1).getDocumentID();
		String sep[] = formerID.split("-");
		String date = getDate();
		if (!date.equals(sep[1])) {
			return "SKD-" + date + "-00001";
		} else {
			int time = Integer.parseInt(sep[2]);
			if (time == 99999) {
				System.out.println("error");
				System.err.println("error");
				return null;
			} else {
				time++;
			}
			String iss = String.format("%5d", time).replace(" ", "0");
			return "SKD-" + date + "-" + iss;
		}
	}

	private String getDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
		return dateFormat.format(now);
	}

	public void show() {
		for (int i = 0; i < receiptPOs.size(); i++) {
			String list = receiptPOs.get(i).getDate() + " "
					+ receiptPOs.get(i).getDocumentID() + " "
					+ receiptPOs.get(i).getCustomerID() + " "
					+ receiptPOs.get(i).getCustomerName() + " "
					+ receiptPOs.get(i).getUserID() + " "
					+ receiptPOs.get(i).getUserName() + " ";
			ArrayList<TransferLineItem> t = receiptPOs.get(i).getTransferList()
					.getTheList();
			for (int j = 0; j < t.size(); j++) {
				list = list + t.get(j).getAccountName() + " "
						+ t.get(j).getTransferPrice() + " "
						+ t.get(j).getRemark() + " ";
			}
			list = list + receiptPOs.get(i).getTotalPrice() + " "
					+ receiptPOs.get(i).isSend() + " "
					+ receiptPOs.get(i).isPass() + " "
					+ receiptPOs.get(i).isDealed();
			System.out.println(list);
		}

	}

	

public ResultMessage init()  throws RemoteException{
	TransferList transferList1=new TransferList();
	transferList1.addItem(new TransferLineItem("赵一", 200, "nothing"));
	transferList1.addItem(new TransferLineItem("钱二", 4000, "good"));
	transferList1.addItem(new TransferLineItem("孙三", 800, "OK"));
	TransferList transferList2=new TransferList();
	transferList2.addItem(new TransferLineItem("孙五", 900, ""));
	transferList2.addItem(new TransferLineItem("李四", 4000, "no bad"));
		receiptPOs.add(new ReceiptPO("20140110", "SKD-20140110-00222", 3, "张三", 4, "cw", transferList1, transferList1.getTotalPrice(), false, false, false));
		receiptPOs.add(new ReceiptPO("20140702", "SKD-20140702-00234", 2, "王二", 4, "cw", transferList2, transferList2.getTotalPrice(), false, false, false));
		output();
		return ResultMessage.SUCCESS;
	}


public ResultMessage finish() {
	// TODO Auto-generated method stub
	return null;
}
}
