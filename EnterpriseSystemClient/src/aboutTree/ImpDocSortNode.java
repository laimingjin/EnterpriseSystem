package aboutTree;

import java.util.ArrayList;

import enumClass.StateOfDocNode;
import vo.ImportDocumentVO;

/**
 * @author 小春
 *2014年12月17日下午2:53:51
 *EnterpriseSystem  
 *aboutTree 
 *ImpDocSortNode.java 
 *进货单节点
 */
public class ImpDocSortNode extends Node{

	//private static final long serialVersionUID = 1L;
	private ArrayList<ImportDocumentVO> importDocList=new ArrayList<ImportDocumentVO>();
	private StateOfDocNode nodeState;
	public ImpDocSortNode(String nodeName,StateOfDocNode state,ArrayList<ImportDocumentVO> list){
		super(nodeName);
		importDocList=list;
		nodeState=state;
	}
	public void addNode(ImpSaleDocNode docNode){
		super.add(docNode);
	}
	public void remove(int index){
		importDocList.remove(index);
	}
	public ArrayList<ImportDocumentVO> getImportDocList() {
		return importDocList;
	}
	public StateOfDocNode getNodeState() {
		return nodeState;
	}
	
	public ImportDocumentVO getDocumentVO(int index){
		return importDocList.get(index);
	}
}
