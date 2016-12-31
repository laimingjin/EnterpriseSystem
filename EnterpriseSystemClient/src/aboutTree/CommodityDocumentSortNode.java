package aboutTree;

public class CommodityDocumentSortNode extends Node{
//	private static final long serialVersionUID = 1L;
    private String myName;
	public CommodityDocumentSortNode(String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addCommodityDocument(CommodityDocumentNode commodityDocumentNode){
		super.add(commodityDocumentNode);
	}
	public void removeCommodityDocument(int index){
		super.remove(index);
	}
	public String getName(){
	return myName;
	}
}
