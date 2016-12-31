package aboutTree;

import vo.CommodityDocumentVO;

public class CommodityDocumentNode extends Node {
	//private static final long serialVersionUID = 1L;
	private CommodityDocumentVO commodityDocumentVO = null;

	private CommodityDocumentNode(String name) {
		super(name);
	}

	public CommodityDocumentNode(CommodityDocumentVO commodityDocumentVO) {
		this(commodityDocumentVO.getCommodity().getCommodityName() + " "
				+ commodityDocumentVO.getCommodity().getCommodityModel());
		this.commodityDocumentVO = commodityDocumentVO;
	}

	public CommodityDocumentVO getcommodityDocumentVO() {
		return commodityDocumentVO;
	}
}
