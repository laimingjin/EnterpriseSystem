package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.AccountPO;
import dataservice.AccountDataService;
import enumClass.ResultMessage;

public class AccountData_Ser extends UnicastRemoteObject implements
		AccountDataService {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/account.out";

	// 存放所有PO的类
	ArrayList<AccountPO> accountPOs;

	// //单例模式，Account只需要一个实例
	// private static final AccountData_Ser ACCOUNT_DATA_SER = new
	// AccountData_Ser();
	// public static AccountData_Ser getInstance() {
	// return ACCOUNT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	@SuppressWarnings("unchecked")
	public AccountData_Ser() throws RemoteException {
		accountPOs = (ArrayList<AccountPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (accountPOs == null) {
			accountPOs = new ArrayList<AccountPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(accountPOs, address);
	}

	// //////////////////////////////////////////////

	/**
	 * 判断accountName是否相同 2014年11月30日
	 * 
	 * @param po
	 * @return
	 */

	// 相同账户名的坐标
	private int IndexOfSameName(String name) {
		// int index=0;
		if (accountPOs.size() != 0) {
			for (int i = 0; i < accountPOs.size(); i++) {
				if (accountPOs.get(i).getAccountName().equals(name)) {
					return i;
				}
			}
		}

		return -1;
	}

	public ResultMessage add(AccountPO p) throws RemoteException {

		if (IndexOfSameName(p.getAccountName()) != -1) {
			return ResultMessage.FAIL;
		} else {
			accountPOs.add(p);
			output();
			return ResultMessage.SUCCESS;
		}

	}

	public ResultMessage delete(AccountPO p) throws RemoteException {
		int index = IndexOfSameName(p.getAccountName());
		if (index == -1) {
			return ResultMessage.FAIL;
		}
		accountPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}

	// 账户管理的修改，改的是账户名
	public ResultMessage update(AccountPO p, AccountPO newpo)
			throws RemoteException {
		// TODO Auto-generated method stub
		int index = IndexOfSameName(p.getAccountName());
		if (index == -1) {
			return ResultMessage.FAIL;
		}

		accountPOs.set(index, newpo);
		output();
		return ResultMessage.SUCCESS;
	}

	// 收款付款单的时候改变账户金额
	public ResultMessage update(AccountPO p) {
		// TODO Auto-generated method stub
		int index = IndexOfSameName(p.getAccountName());
		if (index == -1) {
			return ResultMessage.FAIL;
		}
		accountPOs.set(index, p);
		output();
		return ResultMessage.SUCCESS;
	}

	public AccountPO find(String accountName) throws RemoteException {
		// TODO Auto-generated method stub
		int index = IndexOfSameName(accountName);
		if (index == -1) {

			return null;
		} else {
			return accountPOs.get(index);
		}

	}

	// 模糊查找
	public ArrayList<AccountPO> finds(String info) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<AccountPO> resultSet = new ArrayList<AccountPO>();
		if (accountPOs.size() != 0) {
			for (int i = 0; i < accountPOs.size(); i++) {
				if (accountPOs.get(i).getAccountName().contains(info)) {
					resultSet.add(accountPOs.get(i));
				}
			}
		}
		return resultSet;
	}

	// 显示所有
	public ArrayList<AccountPO> displayAll() throws RemoteException {
		return accountPOs;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return ResultMessage.SUCCESSFULEND;
	}

	private void show() throws RemoteException {
		for (int i = 0; i < accountPOs.size(); i++) {
			System.out.println(accountPOs.get(i).getAccountName() + "  "
					+ accountPOs.get(i).getAccountPrice());
		}
	}

	public ResultMessage init() throws RemoteException {
		accountPOs.add(new AccountPO("赵一", 10000.0));
		accountPOs.add(new AccountPO("钱二", 45000.0));
		accountPOs.add(new AccountPO("孙三", 350.0));
		accountPOs.add(new AccountPO("李四", 660.0));
		accountPOs.add(new AccountPO("孙五", 330.0));
		accountPOs.add(new AccountPO("钱三", 540.0));
		output();
		return ResultMessage.SUCCESS;
	}
}
