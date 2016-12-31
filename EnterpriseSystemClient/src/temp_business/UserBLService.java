package temp_business;

import java.util.ArrayList;

import vo.UserVO;
import enumClass.ResultMessage;

/**
 * 以 《 用户名 》 为唯一标识，admin不可能重名
 * 
 * @author YSH 2014年12月10日下午1:51:22
 */
public interface UserBLService {
	/**
	 * 新增用户 YSH 2014年12月8日上午11:15:28
	 * 
	 * @param vo
	 */
	public ResultMessage addUser(UserVO vo);// 新建用户

	/**
	 * 更新用户 YSH 2014年12月8日上午11:15:42
	 * 
	 * @param oldVo
	 * @param newUserVO
	 * @return
	 */
	public ResultMessage updUser(UserVO oldVo, UserVO newUserVO);// 修改用户密码等信息

	/**
	 * 提供所有用户的数据 YSH 2014年12月8日上午11:15:53
	 * 
	 * @return
	 */
	public ArrayList<UserVO> dispAll();

	/**
	 * 模糊查找用户 YSH 2014年12月10日下午1:11:45
	 * 
	 * @param info
	 *            模糊查找关键词
	 * @return
	 */
	public ArrayList<UserVO> finds(String info);

	/**
	 * 
	 * YSH 2014年12月14日下午4:49:51
	 * 
	 * @param name
	 * @return
	 */
	public UserVO find(String name);

	/**
	 * 删除某个用户 YSH 2014年12月8日上午11:16:10
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage delete(UserVO vo);

	/**
	 * 
	 * YSH 2014年12月14日上午12:05:05
	 * 
	 * @return
	 */
	public int getNewID();

	/**
	 * 初始化数据，这个主要是方便测试，UI基本用不上 YSH 2014年12月8日上午11:16:49
	 */
	public void getinit();// 初始化部分数据

}
