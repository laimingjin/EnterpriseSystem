package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import dataservice.SetAccountDataService;
import enumClass.ResultMessage;
import po.SetAccountPO;
import businesslogic.Account;
import businesslogic.CommodityLineItem;
import businesslogic.Customer;

public class SetAccountData_Ser extends UnicastRemoteObject implements
		SetAccountDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/setAccount.out";

	// 存放所有PO的类
	ArrayList<SetAccountPO> setAccountPOs;

//	// 单例模式，Account只需要一个实例
//	private static final SetAccountData_Ser SETACCOUNT_DATA_SER = new SetAccountData_Ser();
//
//	public static SetAccountData_Ser getInstance() {
//		return SETACCOUNT_DATA_SER;
//	}

	/**
	 * 构造器：初始化Data
	 */
	public SetAccountData_Ser() throws RemoteException {
		setAccountPOs = (ArrayList<SetAccountPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (setAccountPOs == null) {
			setAccountPOs = new ArrayList<SetAccountPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(setAccountPOs, address);
	}

	// //////////////////////////////////////////////
	private int containsSameID(int ID) {
		if(setAccountPOs.size()!=0){
			for (int j = 0; j < setAccountPOs.size(); j++) {
			if (setAccountPOs.get(j).getSetAccountID() == (ID)) {
				return j;
			}
		}
		}
		
		return -1;
	}

	public ResultMessage add(SetAccountPO p)  throws RemoteException{
		// TODO Auto-generated method stub
		setAccountPOs.add(p);
		output();
		//show();
		return ResultMessage.SUCCESS;
	}

	public SetAccountPO findByID(int id) throws RemoteException {
		// TODO Auto-generated method stub
		int index = containsSameID(id);
		if (index == -1) {
			return null;
		} else {
			// show();

			return setAccountPOs.get(index);
		}
	}

	public SetAccountPO findByExactTime(String time)throws RemoteException{
		if(setAccountPOs.size()==0){
			return null;
		}
		
		for (int j = 0; j < setAccountPOs.size(); j++) {
			if (setAccountPOs.get(j).getDate() .equals(time)) {
				return setAccountPOs.get(j);
			}
		}
		return null;
	}
	public ArrayList<SetAccountPO> findByTimezone(String time)  throws RemoteException{
		// TODO Auto-generated method stub
		if(setAccountPOs.size()==0){
			return null;
		}
		
		String[] times = time.split(",");
		long time1 = Long.parseLong(times[0]);
		long time2 = Long.parseLong(times[1]);
		ArrayList<SetAccountPO> al = new ArrayList<SetAccountPO>();
		for (int i = 0; i < setAccountPOs.size(); i++) {
			long date = Long.parseLong(setAccountPOs.get(i).getDate());
			if (date >= time1 && date <= time2) {
				al.add(setAccountPOs.get(i));
			}
		}
		// show();
		System.out.println();
		return al;
	}

	public ArrayList<SetAccountPO> displayAll()  throws RemoteException{
		return setAccountPOs;
	}
	private void show() {
		for (int i = 0; i < setAccountPOs.size(); i++) {
			String list = setAccountPOs.get(i).getDate() + " "
					+ setAccountPOs.get(i).getSetAccountID() + " ";

			ArrayList<CommodityLineItem> commodities = setAccountPOs.get(i)
					.getCommodityList();
			ArrayList<Customer> customers = setAccountPOs.get(i)
					.getCustomerList();
			ArrayList<Account> accounts = setAccountPOs.get(i).getAccountList();
			for (int j = 0; j < commodities.size(); j++) {
				list = list + commodities.get(j).getCommoditySortID() + " "
						+ commodities.get(j).getCommoditySortName() + " "
						+ commodities.get(j).getCommodityID() + " "
						+ commodities.get(j).getCommodityName() + " "
						+ commodities.get(j).getCommodityModel() + " "
						+ commodities.get(j).getPurchasePrice() + " "
						+ commodities.get(j).getLatestRetailPrice() + " "
						+ commodities.get(j).getLatestPurchasePrice() + " "
						+ commodities.get(j).getLatestRetailPrice() + " ";
			}
			for (int k = 0; k < customers.size(); k++) {
				list = list + customers.get(k).getCustomerID() + " "
						+ customers.get(k).getCustomerType() + " "
						+ customers.get(k).getCustomerRank() + " "
						+ customers.get(k).getCustomerName() + " "
						+ customers.get(k).getTelePhone() + " "
						+ customers.get(k).getCustomerAddress() + " "
						+ customers.get(k).getCustomerPostCode() + " "
						+ customers.get(k).geteMail() + " "
						+ customers.get(k).getReceivableLimit() + " "
						+ customers.get(k).getReceivableAmount() + " "
						+ customers.get(k).getPayableAmount() + " ";
			}
			for (int l = 0; l < accounts.size(); l++) {
				list = list + accounts.get(l).getAccountName() + " "
						+ accounts.get(l).getAccountPrice() + " ";
			}
			System.out.println(list);
		}

	}

	public int getFinalID()  throws RemoteException{
		if (setAccountPOs.isEmpty()) {
			return 0;
		} else {
			return setAccountPOs.get(setAccountPOs.size() - 1)
					.getSetAccountID();
		}
	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

public ResultMessage init()  throws RemoteException{
ArrayList<CommodityLineItem>commodityList=new ArrayList<CommodityLineItem>();
commodityList.add(new CommodityLineItem(005,"电灯泡",  0001,"飞利浦", "990e", 100, 150, 80, 0));
ArrayList<Customer>customerList=new ArrayList<Customer>();
customerList.add(new Customer(1, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 16000, 0,"xs"));
customerList.add(new Customer(2, "供应商", 1, "张三", "18933445544", "wu", 210023, "qq@com", 100000, 0, 0,"xs"));
ArrayList<Account>accountList=new ArrayList<Account>();
	accountList.add(new Account("赵一", 10000.0));
	accountList.add(new Account("钱二", 45000.0));
setAccountPOs.add(new SetAccountPO("20131231", 1, commodityList, customerList, accountList));

output();
return ResultMessage.SUCCESS;	
}
}
