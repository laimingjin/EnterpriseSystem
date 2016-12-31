package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;
import temp_business.UserBLService;
import vo.UserVO;
import dataservice.UserDataService;
import enumClass.ResultMessage;

public class UserBLImp implements UserBLService {

	private UserDataService userdataService;

	public UserBLImp() {
		try {
			userdataService = (UserDataService) Naming
					.lookup(StaticInfo.URL_USER);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public ResultMessage addUser(UserVO vo) {
		UserPO po = new UserPO(vo);
		try {
			return userdataService.add(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}

	}

	public ResultMessage updUser(UserVO oldVo, UserVO newVO) {
		UserPO oldPo = new UserPO(oldVo);
		UserPO newPo = new UserPO(newVO);
		try {
			return userdataService.updata(oldPo, newPo);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ArrayList<UserVO> dispAll() {
		ArrayList<UserPO> userPOs = new ArrayList<UserPO>();
		;
		try {
			userPOs = userdataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<UserVO> userVOs = new ArrayList<UserVO>();

		for (int i = 0; i < userPOs.size(); i++) {
			userVOs.add(new UserVO(userPOs.get(i)));
		}
		return userVOs;
	}

	public ArrayList<UserVO> finds(String info) {
		ArrayList<UserPO> userPOs = new ArrayList<UserPO>();
		;
		try {
			userPOs = userdataService.finds(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<UserVO> userVOs = new ArrayList<UserVO>();

		for (int i = 0; i < userPOs.size(); i++) {
			userVOs.add(new UserVO(userPOs.get(i)));
		}
		return userVOs;
	}

	public ResultMessage delete(UserVO vo) {
		UserPO po = new UserPO(vo);
		try {
			return userdataService.delete(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public void getinit() {
		try {
			userdataService.getinit();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public int getNewID() {

		int finalID = 0;
		try {
			finalID = userdataService.getfinalID();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return (finalID + 1);
	}

	public UserVO find(String name) {
		try {
			return new UserVO(userdataService.find(name));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
