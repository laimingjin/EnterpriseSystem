package temp_business;

import vo.LoginVo;
import vo.UserVO;
import enumClass.POSITION;

public interface LoginBLService {

	/**
	 * 登录 YSH 2014年12月10日下午1:13:52
	 * 
	 * @param admin
	 * @param Password
	 * @return
	 */
	public POSITION login(LoginVo vo);

	public String getUser();

	public UserVO getUserVO();

}
