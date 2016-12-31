package aboutTree;

import vo.CommodityGiftVO;

public class CommodityGiftNode  extends Node {
//	private static final long serialVersionUID = 1L;
	private CommodityGiftVO commodityGiftVO= null;

	private CommodityGiftNode(String name) {
		super(name);
	}

	public CommodityGiftNode(CommodityGiftVO commodityGiftVO) {
		this(commodityGiftVO.getCommodity().getCommodityName()+" "+commodityGiftVO.getCommodity().getCommodityModel());
		this.commodityGiftVO = commodityGiftVO;
	}
	public CommodityGiftVO getcommodityGiftVO(){
		return commodityGiftVO;
	}
}
