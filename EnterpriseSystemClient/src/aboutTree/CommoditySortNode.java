package aboutTree;

import vo.CommoditySortVO;

public class CommoditySortNode extends Node{
//	private static final long serialVersionUID = 1L;
	private CommoditySortVO commoditySort= null;

	private CommoditySortNode(String name) {
		super(name);
	}
   /*
   * 商品分类节点持有一个CommoditySortVO
   */
	public CommoditySortNode(CommoditySortVO commoditySort) {
		this(commoditySort.getCommoditySortName());
		this.commoditySort = commoditySort;
	}
	//获得这个节点的 CommoditySortVO 
	public CommoditySortVO getCommodity(){
		return commoditySort;
	}
	/*
	 * 在这个商品分类节点下增加商品
	 */
	public void addCommodity(CommodityNode commodity){
		super.add(commodity);
	}
	/*
	 * 在这个商品分类节点下移除商品
	 */
	public void removeCommodity(int index){
		super.remove(index);
	}
	/*
	 * 在这个商品分类节点下增加商品子分类
	 */
	public void addSort(CommoditySortNode sonSort){
		super.add(sonSort);
	}
	/*
	 * 在这个商品分类节点下移除商品子分类
	 */
	public void removeSort(int index){
		super.remove(index);
	}

}
