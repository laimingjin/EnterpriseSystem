package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.UserPO;
import dataservice.UserDataService;
import enumClass.POSITION;
import enumClass.ResultMessage;

public class UserData_Ser extends UnicastRemoteObject implements
		UserDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/user.out";

	// 存放所有PO的类
	ArrayList<UserPO> userPOs;

	private int index_hasNoSameName = -1;

	// //单例模式，Account只需要一个实例
	// private static final UserData_Ser USER_DATA_SER = new UserData_Ser();
	// public static UserData_Ser getInstance() {
	// return USER_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public UserData_Ser() throws RemoteException {
		userPOs = (ArrayList<UserPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (userPOs == null) {
			userPOs = new ArrayList<UserPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(userPOs, address);
	}

	// //////////////////////////////////////////////

	// /**
	// * 判断ID是否相同 2014年11月30日
	// *
	// * @param po
	// * @return
	// */
	// public int containsSameID(int ID) throws RemoteException {
	// for (int j = 0; j < userPOs.size(); j++) {
	// if (userPOs.get(j).getUserID() == (ID)) {
	// return j;
	// }
	// }
	// return -1;
	// }
	//
	// public UserPO find(int userID) throws RemoteException {
	// int index = containsSameID(userID);
	// if (index == -1) {
	// return null;
	// }
	// sysoShow();
	// return userPOs.get(index);
	// }

	/**
	 * 
	 * YSH 2014年12月8日上午11:00:08
	 * 
	 * @param po
	 * @return
	 * @throws RemoteException
	 */
	public ResultMessage add(UserPO po) throws RemoteException {
		if (getIndexOfTheSameName(po) != index_hasNoSameName) {
			System.out.println("已有该用户名");
			return ResultMessage.Exist;
		}
		userPOs.add(po);
		output();

		sysoShow();

		return ResultMessage.SUCCESS;
	}

	/**
	 * 
	 * YSH 2014年12月8日上午11:00:03
	 * 
	 * @param po
	 * @return
	 */
	private int getIndexOfTheSameName(UserPO po) {
		if (userPOs != null) {
			for (int i = 0; i < userPOs.size(); i++) {
				if (po.getUserName().equals(userPOs.get(i).getUserName())) {// 若用户名相同
					return i;
				}
			}
		}

		return index_hasNoSameName;
	}

	/**
	 * 
	 * YSH 2014年12月8日上午11:00:06
	 * 
	 * @param po
	 * @return
	 * @throws RemoteException
	 */
	public ResultMessage delete(UserPO po) throws RemoteException {
		int index = getIndexOfTheSameName(po);
		if (index == index_hasNoSameName) {
			//
			System.out.println("该用户不存在");
			//
			return ResultMessage.NotExist;
		} else {
			userPOs.remove(index);
			output();
			// sysoShow();
			return ResultMessage.SUCCESS;
		}
	}

	// public ResultMessage updateByUser(UserPO po) throws RemoteException {
	// int userID = po.getUserID();
	// userPOs.set(containsSameID(userID), po);
	// output();
	// show();
	// return ResultMessage.SUCCESS;
	// }
	//
	// public ResultMessage updateBySystem(UserPO po) throws RemoteException {
	// int userID = po.getUserID();
	// userPOs.set(containsSameID(userID), po);
	// output();
	// show();
	// return ResultMessage.SUCCESS;
	// }

	// // 得到最后一个客户的ID，从而下一个客户的ID为此ID+1
	// public int getFinalID() {
	// if (userPOs.isEmpty()) {
	// return 0;
	// } else {
	// return userPOs.get(userPOs.size() - 1).getUserID();
	// }
	// }

	/**
	 * 
	 * YSH 2014年12月8日上午11:00:00
	 * 
	 * @throws RemoteException
	 */
	private void sysoShow() throws RemoteException {
		for (int i = 0; i < userPOs.size(); i++) {
			String list =
			// userPOs.get(i).getUserID() + " "+
			userPOs.get(i).getUserName() + " " + userPOs.get(i).getPassword()
					+ " " + userPOs.get(i).getTheJob() + " "
					+ userPOs.get(i).getPowerLevel();
			System.out.println(list);
		}
	}

	// public ResultMessage finish() throws RemoteException {
	// return null;
	// }

	/**
	 * 
	 * YSH 2014年12月8日上午10:59:58
	 * 
	 * @param oldPo
	 * @param newPo
	 * @return
	 * @throws RemoteException
	 */
	public ResultMessage updata(UserPO oldPo, UserPO newPo)
			throws RemoteException {

		int index = getIndexOfTheSameName(oldPo);
		if (index == index_hasNoSameName) {
			//
			System.out.println("该用户不存在");
			//
			return ResultMessage.FAIL;
		} else {
			// 删去oldPo，替换为newPo
			userPOs.remove(index);
			userPOs.add(index, newPo);
			output();
			//
			sysoShow();
			//
			return ResultMessage.SUCCESS;
		}

	}

	/**
	 * 
	 * YSH 2014年12月8日上午10:59:56
	 * 
	 * @param UserName
	 * @return
	 * @throws RemoteException
	 */
	public UserPO find(String UserName) throws RemoteException {
		if (userPOs != null) {
			for (int i = 0; i < userPOs.size(); i++) {
				if (UserName.equals(userPOs.get(i).getUserName())) {// 若用户名相同
					return userPOs.get(i);
				}
			}
		}

		return null;
	}

	// //////////////////////////////////////////////

	// /**
	// * 判断ID是否相同 2014年11月30日
	// *
	// * @param po
	// * @return
	// */
	// public int containsSameID(int ID) throws RemoteException {
	// for (int j = 0; j < userPOs.size(); j++) {
	// if (userPOs.get(j).getUserID() == (ID)) {
	// return j;
	// }
	// }
	// return -1;
	// }
	//
	// public UserPO find(int userID) throws RemoteException {
	// int index = containsSameID(userID);
	// if (index == -1) {
	// return null;
	// }
	// sysoShow();
	// return userPOs.get(index);
	// }

	/**
	 * 
	 * YSH 2014年12月8日上午10:59:43
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public ArrayList<UserPO> dispAll() throws RemoteException { // TODO 最好进行深拷贝

		return userPOs;
	}

	public ArrayList<UserPO> finds(String info) throws RemoteException {
		if (userPOs == null) {
			return null;
		}
		ArrayList<UserPO> al = new ArrayList<UserPO>();
		for (int i = 0; i < userPOs.size(); i++) {
			if (userPOs.get(i).getUserName().contains(info)) {
				al.add(userPOs.get(i));
			}
		}
		return al;
	}

	public void getinit() throws RemoteException {
		UserPO kc = new UserPO(2, "kc", "kc", POSITION.COMMODITY_MANAGER, 1);// 库存
		UserPO xs = new UserPO(3, "xs", "xs", POSITION.SALE_MANAGER, 1);// 销售
		UserPO cw = new UserPO(4, "cw", "cw", POSITION.FINANCIAL_MANAGER, 1);// 财务
		UserPO zjl = new UserPO(5, "zjl", "zjl", POSITION.GENERAL_MANAGER, 1);// 总经理
		UserPO xt = new UserPO(1, "admin", "admin", POSITION.SYSTEM_MANAGER, 3);// 系统管理

		// userPOs=new ArrayList<UserPO>();
		userPOs.add(xt);
		userPOs.add(kc);
		userPOs.add(xs);
		userPOs.add(cw);
		userPOs.add(zjl);

		output();
	}

	public int getfinalID() throws RemoteException {
		int result = 0;
		if (userPOs.size() == 0) {

		} else {

			result = userPOs.get(userPOs.size() - 1).getUserID();

		}
		return result;
	}

}
