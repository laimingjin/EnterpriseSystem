package temp_business;

import java.util.ArrayList;

import vo.CustomerVO;
import enumClass.ResultMessage;

/**
 * 
 * 以 《 ID 》 为唯一标识，用户可能重名
 * 
 * @author YSH 2014年12月10日下午1:48:00
 */

public interface CustomerBLService {

	/**
	 * 增 YSH 2014年12月10日下午1:53:19
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage addCustomer(CustomerVO vo);// 添加

	// 编号、分类（进货商、销售商）、级别（五级，一级普通用户，五级VIP客户）、姓名、电话、地址、
	// 邮编、电子邮箱、应收额度、应收、应付、默认业务员

	// 对客户信息进行具体操作

	/**
	 * 删 YSH 2014年12月10日下午1:53:15
	 * 
	 * @param id
	 * @return
	 */
	public ResultMessage deleteCustomer(CustomerVO vo);// 删除

	// 对客户信息进行具体操作

	/**
	 * 改,新增的Customer的id不变 YSH 2014年12月10日下午1:53:21
	 * 
	 * @param id
	 * @param newVo
	 * @return
	 */
	public ResultMessage updateCustomer(CustomerVO newVo);// 修改

	/**
	 * 取得客户具体信息 YSH 2014年12月10日下午1:47:46
	 * 
	 * @param id
	 * @return
	 */
	public CustomerVO findCustomer(int id);

	/**
	 * 
	 * YSH 2014年12月14日下午4:49:51
	 * 
	 * @param name
	 * @return
	 */
	public CustomerVO find(String name);

	/**
	 * 模糊查找，此方法对 姓名，地址，电子邮箱，默认业务员 电话，邮编无反应 YSH 2014年12月10日下午1:48:04
	 * 
	 * @param info
	 * @return
	 */
	public ArrayList<CustomerVO> findsCustomer(String info);

	// 对客户信息进行具体操作

	/**
	 * 直接返回所有客户信息 YSH 2014年12月10日下午1:58:26
	 * 
	 * @return
	 */
	public ArrayList<CustomerVO> disPlayAll();

	/**
	 * 
	 * YSH 2014年12月11日下午8:04:11
	 * 
	 * @return
	 */
	public int getNewId();

	/**
	 * 初始化数据，这个主要是方便测试，UI基本用不上 YSH 2014年12月10日下午1:53:45
	 */
	public void getInit();

}
