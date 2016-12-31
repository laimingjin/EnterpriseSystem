package dataStub_Ser;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dataservice.PaymentDataService;
import po.PaymentPO;
import po.ReceiptPO;
import po.SaleDocumentPO;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import enumClass.ResultMessage;

public class PaymentData_Ser extends UnicastRemoteObject implements
		PaymentDataService {
	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/payment.out";

	// 存放所有PO的类
	ArrayList<PaymentPO> paymentPOs;

	// //单例模式，Account只需要一个实例
	// private static final PaymentData_Ser PAYMENT_DATA_SER = new
	// PaymentData_Ser();
	// public static PaymentData_Ser getInstance() {
	// return PAYMENT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public PaymentData_Ser() throws RemoteException {
		paymentPOs = (ArrayList<PaymentPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (paymentPOs == null) {
			paymentPOs = new ArrayList<PaymentPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(paymentPOs, address);
	}

	// //////////////////////////////////////////////

	public int containsSameID(String ID) {
		if(paymentPOs.size()!=0){
			for (int j = 0; j < paymentPOs.size(); j++) {
			if (paymentPOs.get(j).getDocumentID().equals(ID)) {
				return j;
			}
		}
		}
		
		return -1;
	}

	public ResultMessage add(PaymentPO p) throws RemoteException {
		// TODO Auto-generated method stub
		paymentPOs.add(p);
		output();
		// show();
		return ResultMessage.SUCCESS;
	}

	// 总经理才update
	public ResultMessage update(PaymentPO p) throws RemoteException {
		// TODO Auto-generated method stub
		String ID = p.getDocumentID();
		paymentPOs.set(containsSameID(ID), p);
		output();
		// show();
		return ResultMessage.SUCCESS;
	}

	public PaymentPO findByDocumentID(String documentID) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(documentID);
		if (index == -1) {
			return null;
		} else {
			return paymentPOs.get(index);
		}
	}
	//专为红冲和红冲复制写的find方法
		public PaymentPO findByIDForWriteBack(String documentID) throws RemoteException {
			if(paymentPOs.size()==0){
				return null;
			}
			ArrayList<PaymentPO>pos=new ArrayList<PaymentPO>();
			for (int j = 0; j < paymentPOs.size(); j++) {
				if (paymentPOs.get(j).getDocumentID().equals(documentID)) {
					pos.add(paymentPOs.get(j));
				}
			}
			if(pos!=null){
				return pos.get(pos.size()-1);
			}
			return null;
		}
	public ArrayList<PaymentPO> findByCustomer(String customerName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(paymentPOs.size()==0){
			return null;
		}
		if(customerName.equals("")){
			return paymentPOs;
		}
		ArrayList<PaymentPO> al = new ArrayList<PaymentPO>();
		for (int i = 0; i < paymentPOs.size(); i++) {
			if (paymentPOs.get(i).getCustomerName().equals(customerName)) {
				al.add(paymentPOs.get(i));
			}
		}
		// show();
		return al;
	}

	public ArrayList<PaymentPO> findByTime(String time) throws RemoteException {
		// TODO Auto-generated method stub
		if(paymentPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return paymentPOs;
		}else{
			String[] times = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			ArrayList<PaymentPO> al = new ArrayList<PaymentPO>();
			for (int i = 0; i < paymentPOs.size(); i++) {
				long date = Long.parseLong(paymentPOs.get(i).getDate());
				if (date >= time1 && date <= time2) {
					al.add(paymentPOs.get(i));
				}
			}
			// show();
			//System.out.println();
			return al;
		}
	
	}

	public ArrayList<PaymentPO> findByUser(String userName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(paymentPOs.size()==0){
			return null;
		}
		if(userName.equals("")){
			return paymentPOs;
		}
		ArrayList<PaymentPO> al = new ArrayList<PaymentPO>();
		for (int i = 0; i < paymentPOs.size(); i++) {
			if (paymentPOs.get(i).getUserName().equals(userName)) {
				al.add(paymentPOs.get(i));
			}
		}
		// show();
		System.out.println();
		return al;
	}

	// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号
	public String getNewID() throws RemoteException {
		// TODO 单据逻辑，如果单据库为空
		if (paymentPOs.size()==0) {
		 		return "FKD" + "-" + getDate() + "-00001";
		}
		String formerID = paymentPOs.get(paymentPOs.size() - 1).getDocumentID();
		String sep[] = formerID.split("-");
		
		String date=getDate();
		
		if (!date.equals(sep[1])) {
			return "FKD-" + date + "-00001";
		} else {
			int time = Integer.parseInt(sep[2]);
			if (time == 99999) {
				System.out.println("error");
				return null;
			} else {
				time++;
			}
			String iss = String.format("%5d", time).replace(" ", "0");
			return "FKD-" + date + "-" + iss;
		}
	}
	private String getDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		return dateFormat.format( now ); 
	}
	public void show() {
		for (int i = 0; i < paymentPOs.size(); i++) {
			String list = paymentPOs.get(i).getDate() + " "
					+ paymentPOs.get(i).getDocumentID() + " "
					+ paymentPOs.get(i).getCustomerID() + " "
					+ paymentPOs.get(i).getCustomerName() + " "
					+ paymentPOs.get(i).getUserID() + " "
					+ paymentPOs.get(i).getUserName() + " ";
			ArrayList<TransferLineItem> t = paymentPOs.get(i).getTransferList()
					.getTheList();
			for (int j = 0; j < t.size(); j++) {
				list = list + t.get(j).getAccountName() + " "
						+ t.get(j).getTransferPrice() + " "
						+ t.get(j).getRemark() + " ";
			}
			list = list + paymentPOs.get(i).getTotalPrice() + " "
					+ paymentPOs.get(i).isSend() + " "
					+ paymentPOs.get(i).isPass() + " "
					+ paymentPOs.get(i).isDealed();
			System.out.println(list);
		}

	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public ArrayList<PaymentPO> displayAll() throws RemoteException {
		// TODO Auto-generated method stub
		return paymentPOs;
	}

	public ResultMessage init()  throws RemoteException{
		TransferList transferList1=new TransferList();
		transferList1.addItem(new TransferLineItem("赵一", 200, "nothing"));
		transferList1.addItem(new TransferLineItem("钱二", 4000, "good"));
		transferList1.addItem(new TransferLineItem("孙三", 800, "OK"));
		TransferList transferList2=new TransferList();
		transferList2.addItem(new TransferLineItem("孙五", 900, ""));
		transferList2.addItem(new TransferLineItem("李四", 4000, "no bad"));
			paymentPOs.add(new PaymentPO("20140110", "FKD-20140110-00222", 3, "张三", 4, "cw", transferList1, transferList1.getTotalPrice(),  true, true,  true));
			paymentPOs.add(new PaymentPO("20140110", "FKD-20140110-00222", 3, "张三", 4, "cw", transferList2, transferList2.getTotalPrice(), true, true, false));
			output();
			return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(PaymentPO p) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(p.getDocumentID());
		if (index == -1) {
			System.out.println("无该ID的PO存在");
			return ResultMessage.FAIL;
		} else {
			paymentPOs.remove(index);
			output();
			return ResultMessage.SUCCESS;
		}
	}
}
