package aboutTree;

import vo.CommodityVO;

public class CommodityNode extends Node {
//	private static final long serialVersionUID = 1L;
	private CommodityVO commodity= null;

	private CommodityNode(String name) {
		super(name);
	}
          //显示在节点的模样
	public CommodityNode(CommodityVO commodity) {
		this(commodity.getCommodityName()+" "+commodity.getCommodityModel());
		this.commodity = commodity;
	}
	public CommodityVO getCommodity(){
		return commodity;
	}
}
