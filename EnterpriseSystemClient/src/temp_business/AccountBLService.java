package temp_business;

import java.util.ArrayList;

import vo.AccountVO;
import enumClass.ResultMessage;

/**
 * 以 《 账户名 》 为唯一标识
 * 
 * @author YSH 2014年12月10日下午2:01:17
 */
public interface AccountBLService {
	/**
	 * 
	 * YSH 2014年12月10日下午2:04:06
	 * 
	 * @return
	 */
	public ArrayList<AccountVO> displayAll();

	/**
	 * 
	 * YSH 2014年12月10日下午2:02:20
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage addAccount(AccountVO vo);

	/**
	 * 
	 * YSH 2014年12月10日下午2:02:33
	 * 
	 * @param name
	 * @return
	 */
	public ResultMessage deleteAccount(String name);

	/**
	 * 
	 * YSH 2014年12月10日下午2:02:35
	 * 
	 * @param name
	 * @param vo
	 * @return
	 */
	public ResultMessage updateAccount(String name, AccountVO vo);

	// public void endManagement();//结束账户管理

	// 账户界面得到账号信息
	/**
	 * 
	 * YSH 2014年12月10日下午2:02:40
	 * 
	 * @param name
	 * @return
	 */
	public AccountVO findAccount(String name);

	/**
	 * 
	 * YSH 2014年12月10日下午2:03:27
	 * 
	 * @return
	 */
	public ArrayList<AccountVO> findsAccount(String info);

	public void getinit();// 初始化部分数据

}
