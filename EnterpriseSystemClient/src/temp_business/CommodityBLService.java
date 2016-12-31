package temp_business;

import java.util.ArrayList;

import vo.CommodityVO;
import businesslogic.CommodityCheck;
import enumClass.ResultMessage;

public interface CommodityBLService {
	
	// 商品界面得到商品分类和商品信息,商品库存，库存快照
	// 点击某个叶节点分类后能显示该分类下的所有商品。商品的属性有：
//	编号、名称、型号、库存数量、进价、零售价、最近进价、最近零售价
	// public CommodityVO findAllCommodityByCommoditySort(String
	// commoditySortName);
	
	// 商品的查询可以通过输入关键字、商品编号等进行模糊查找
	public ArrayList<CommodityVO> getCommodityByKey(String key);
	public CommodityVO getCommodityByID(int ID);

//	public CommodityVO getCommoditySnapshot();// 获取当日库存快照

	public ArrayList<CommodityCheck> checkCommodityInventory(String time1, String time2);// 获取库存查看

	
	public ResultMessage addCommodity(CommodityVO commodityVO);// 增商品

	
	public ResultMessage deleteCommodity(String commodityName,String commodityModel);// 删商品
	
	public ResultMessage updateCommodity(int ID, String commodityName,String commodityModel);// 修改商品
	public ResultMessage updateCommodity(CommodityVO commodityVO);// 修改商品

	public int getNewCommodityID();
	
public ArrayList<CommodityVO> displayAll();

public CommodityVO find(String name,String model);
	public void getinit();// 初始化部分数据

}
