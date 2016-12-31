package aboutTree;

public class CustomerSortNode extends Node {

	/**
	 * serialVersionUID
	 * 客户分类节点
	 * 保有很多客户节点
	 */
	//private static final long serialVersionUID = 1L;
    private String myName;
	public CustomerSortNode(String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addCustomer(CustomerNode customer){
		super.add(customer);
	}
	public void removeCustomer(int index){
		super.remove(index);
	}
	public String getName(){
	return myName;
	}
	
}
