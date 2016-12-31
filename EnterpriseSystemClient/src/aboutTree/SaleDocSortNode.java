package aboutTree;

import java.util.ArrayList;

import vo.SaleDocumentVO;
import enumClass.StateOfDocNode;

/**
 * @author 小春
 *2014年12月17日下午3:19:54
 *EnterpriseSystem  
 *aboutTree 
 *SaleDocSortNode.java 
 *销售单节点
 */
public class SaleDocSortNode extends Node{


//	private static final long serialVersionUID = 1L;
	private ArrayList<SaleDocumentVO> saleDocList=new ArrayList<SaleDocumentVO>();
	private StateOfDocNode nodeState;
	public SaleDocSortNode(String nodeName,StateOfDocNode state,ArrayList<SaleDocumentVO> list){
		super(nodeName);
		saleDocList=list;
		nodeState=state;
	}
	public void addNode(ImpSaleDocNode docNode){
		super.add(docNode);
	}
	public void remove(int index){
		saleDocList.remove(index);
	}
	
	public ArrayList<SaleDocumentVO> getSaleDocList() {
		return saleDocList;
	}
	public StateOfDocNode getNodeState() {
		return nodeState;
	}
	
	public SaleDocumentVO getDocumentVO(int index){
		return saleDocList.get(index);
	}

}
