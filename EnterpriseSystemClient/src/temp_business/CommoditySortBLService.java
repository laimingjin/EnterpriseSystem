package temp_business;

import vo.CommoditySortVO;
import enumClass.ResultMessage;

public interface CommoditySortBLService {
	// 点击某个叶节点分类后能显示该分类下的所有商品。商品的属性有：编号、名称、型号、库存数量、进价、零售价、最近进价、最近零售价

	public CommoditySortVO getRoot();

	public CommoditySortVO getCommoditySortbyID(int ID);

	public CommoditySortVO getCommoditySortbyName(String sortName);

	public ResultMessage addCommoditySort(CommoditySortVO commoditySortVO);// 增商品分类

	public ResultMessage deleteCommoditySort(String commoditySortName);// 删商品分类

	public ResultMessage updateCommoditySort(int sortID,
			String commoditySortName);// 修改商品分类

	public ResultMessage updateCommoditySort(CommoditySortVO cSortVO);// 修改商品分类

	public int getNewSortID();
}
