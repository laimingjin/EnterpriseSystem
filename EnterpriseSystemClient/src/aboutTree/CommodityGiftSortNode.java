package aboutTree;

public class CommodityGiftSortNode extends Node{
	//private static final long serialVersionUID = 1L;
    private String myName;
	public CommodityGiftSortNode (String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addCommodityGiftDocument(CommodityGiftNode commodityGiftNode){
		super.add(commodityGiftNode);
	}
	public void removeCommodityGiftDocument(int index){
		super.remove(index);
	}
	public String getName(){
	return myName;
	}
}

