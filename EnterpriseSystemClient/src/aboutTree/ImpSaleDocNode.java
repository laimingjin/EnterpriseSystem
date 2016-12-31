package aboutTree;

import vo.ImportDocumentVO;
import vo.SaleDocumentVO;

/**
 * @author 小春
 *2014年12月17日上午11:55:53
 *EnterpriseSystem  
 *aboutTree 
 *ImpSaleDocNode.java 
 *进货销售单节点
 */
public class ImpSaleDocNode extends Node{
//private static final long serialVersionUID = 1L;

public ImpSaleDocNode(ImportDocumentVO importDocumentVO){
	super(importDocumentVO.getDocumentID());
}
public ImpSaleDocNode(SaleDocumentVO saleDocumentVO){
	super(saleDocumentVO.getDocumentID());
}
}
