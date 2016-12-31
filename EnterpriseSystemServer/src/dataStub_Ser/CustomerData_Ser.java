package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.CustomerPO;
import dataservice.CustomerDataservice;
import enumClass.ResultMessage;

public class CustomerData_Ser extends UnicastRemoteObject implements
		CustomerDataservice {
//	private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/customer.out";

	// 存放所有PO的类
	ArrayList<CustomerPO> customerPOs;

	// //单例模式，Account只需要一个实例
	// private static final CustomerData_Ser CUSTOMER_DATA_SER = new
	// CustomerData_Ser();
	// public static CustomerData_Ser getInstance() {
	// return CUSTOMER_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	@SuppressWarnings("unchecked")
	public CustomerData_Ser() throws RemoteException {
		customerPOs = (ArrayList<CustomerPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (customerPOs == null) {
			customerPOs = new ArrayList<CustomerPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(customerPOs, address);
	}

	// //////////////////////////////////////////////
	private int cotainsSameID(CustomerPO po) {
		if(customerPOs.size()!=0){
			for (int i = 0; i < customerPOs.size(); i++) {
				if (customerPOs.get(i).getCustomerID() == (po.getCustomerID())) {
					return i;
				}
			}
		}
		
		return -1;
	}
	private int cotainsSameName(CustomerPO po) {
		if(customerPOs.size()!=0){
			for (int i = 0; i < customerPOs.size(); i++) {
				if (customerPOs.get(i).getCustomerName() .equals(po.getCustomerName())) {
					return i;
				}
			}
		}
		
		return -1;
	}

	public CustomerPO findByID(int customerID) throws RemoteException {
		if(customerPOs.size()!=0){
			for (int i = 0; i < customerPOs.size(); i++) {
				if (customerPOs.get(i).getCustomerID() == customerID) {
					// show();
					return customerPOs.get(i);
				}
			}
		}
	

		return null;
	}

	public CustomerPO findByName(String customerName) throws RemoteException {
		if(customerPOs.size()!=0){
			for (int i = 0; i < customerPOs.size(); i++) {
				if (customerPOs.get(i).getCustomerName().equals(customerName)) {
					// show();
					return customerPOs.get(i);
				}
			}
		}
		

		return null;
	}

	public ArrayList<CustomerPO> findVague(String info) throws RemoteException {
		ArrayList<CustomerPO> al = new ArrayList<CustomerPO>();
		if(customerPOs.size()!=0){
			for (int i = 0; i < customerPOs.size(); i++) {
				if (hasInfo(customerPOs.get(i), info)) {
					al.add(customerPOs.get(i));
				}
			}
		}
		System.out.println();
		return al;
	}
//模糊查找，中文的才行
	private boolean hasInfo(CustomerPO customerPO, String info) {
		return customerPO.getCustomerName().contains(info)
				|| customerPO.getOperator().contains(info)
				|| customerPO.getCustomerAddress().contains(info)
				|| customerPO.geteMail().contains(info);
	}

	public ArrayList<CustomerPO> displayAll() throws RemoteException {
		return customerPOs;
	}

	public ResultMessage add(CustomerPO po) throws RemoteException {
		if (cotainsSameName(po)!=-1) {
			// TODO syso
			System.out.println("已存在客户");
			
			return ResultMessage.Exist;
		}
		customerPOs.add(po);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(CustomerPO po) throws RemoteException {
		int index= cotainsSameName(po);
		if (index  == -1) {
			// TODO syso
			System.out.println("不存在该客户");
			return ResultMessage.NotExist;
		}
		customerPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage updateBySaleManager(CustomerPO newpo)
			throws RemoteException {
		int index = cotainsSameName(newpo);
		if (index == -1) {
			System.out.println("不存在该客户");
			return ResultMessage.NotExist;
		}
		customerPOs.set(index, newpo);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(CustomerPO newpo) throws RemoteException {
		int index = cotainsSameName(newpo);
		if (index == -1) {
			System.out.println("不存在该客户");
			return ResultMessage.NotExist; 
		}
		customerPOs.set(index, newpo);
		output();
		return ResultMessage.SUCCESS;
	}

	public int getFinalID() throws RemoteException {
		if(customerPOs.size()==0){
			return 0;
		}
		int finalID = customerPOs.get(customerPOs.size() - 1).getCustomerID();
		return finalID;
	}

	@SuppressWarnings("unused")
	private void show() {
		for (int i = 0; i < customerPOs.size(); i++) {
			String list = customerPOs.get(i).getCustomerID() + " "
					+ customerPOs.get(i).getCustomerType() + " "
					+ customerPOs.get(i).getCustomerRank() + " "
					+ customerPOs.get(i).getCustomerName() + "   "
					+ customerPOs.get(i).getTelePhone() + " "
					+ customerPOs.get(i).getCustomerAddress() + " "
					+ customerPOs.get(i).getCustomerPostCode() + " "
					+ customerPOs.get(i).geteMail() + " "
					+ customerPOs.get(i).getReceivableLimit() + " "
					+ customerPOs.get(i).getReceivableAmount() + " "
					+ customerPOs.get(i).getPayableAmount() + " ";
			System.out.println(list);
		}

	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getinit() throws RemoteException {
		
		CustomerPO kh1=new CustomerPO(1, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 16000, 0,"xs");
		CustomerPO kh2=new CustomerPO(2, "供应商", 1, "张三", "18933445544", "wu", 210023, "qq@com", 100000, 0, 0,"xs");
		CustomerPO kh3=new CustomerPO(3, "供应商", 2, "林琳", "18944445555", "wu", 210046, "qq@com", 100000, 15000, 0,"xs");
		CustomerPO kh4=new CustomerPO(4, "销售商", 3, "张琪", "18933445453", "wu", 210046, "qq@com", 100000, 0, 30000,"xs");
		CustomerPO kh5=new CustomerPO(5, "销售商", 3, "李四", "18933448995", "wu", 210023, "qq@com", 100000, 0, 0,"xs");
		customerPOs=new ArrayList<CustomerPO>();
		customerPOs.add(kh1);
		customerPOs.add(kh2);
		customerPOs.add(kh3);
		customerPOs.add(kh4);
		customerPOs.add(kh5);
		
		output();
	}

}
