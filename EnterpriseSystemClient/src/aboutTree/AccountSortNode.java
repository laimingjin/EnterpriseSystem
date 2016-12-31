package aboutTree;

public class AccountSortNode extends Node{
	/**
	 * serialVersionUID
	 * 账户分类节点
	 * 保有很多客户节点
	 */
	//private static final long serialVersionUID = 1L;
    private String myName;
	public AccountSortNode(String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addAccount(AccountNode accountNode){
		super.add(accountNode);
	}
	public void removeAccount(int index){
		super.remove(index);
	}
	public String getName(){
	return myName;
	}
}
