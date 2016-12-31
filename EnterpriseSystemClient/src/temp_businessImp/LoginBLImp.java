package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import dataservice.UserDataService;
import po.UserPO;
import temp_business.LoginBLService;
import vo.LoginVo;
import vo.UserVO;
import enumClass.POSITION;

public class LoginBLImp implements LoginBLService{


	private  UserDataService userdataService;   

	private static UserPO currentUser;
	
	public LoginBLImp() {
		try {
			userdataService = (UserDataService) Naming.lookup(StaticInfo.URL_USER);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public POSITION login(LoginVo vo) {
	        POSITION  resultPosition = null;
				
				ArrayList<UserPO> userPOs = null;
				
				try {
					userPOs = userdataService.dispAll();
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			
				for (int i = 0; i < userPOs.size(); i++) {
					
					resultPosition=checkPassword(userPOs.get(i), vo.getAdmin(),  vo.getPassword());
					if (resultPosition!=POSITION.ADMIN_MISTAKE) {//如果用户名不对，继续匹配下一个
						
						break;
					}
				}
				
				

		return resultPosition;
	}
	
	private POSITION checkPassword(UserPO userPO, String admin, String password) {
	    @SuppressWarnings("unused")
		POSITION  resultPosition = null;
		if (!userPO.getUserName().equals(admin) ) {//用户名不对
			return POSITION.ADMIN_MISTAKE;
		}
		if (!userPO.getPassword().equals(password) ) {//密码不对
			return POSITION.PASSWORD_MISTAKE;
		}
		
		//用户名和密码都匹配
		currentUser=userPO;
		return userPO.getTheJob();
		
	}

	
	
	public String getUser() {
		return currentUser.getUserName();
	}

	public UserVO getUserVO() {
		return new UserVO(currentUser);
	}
}
