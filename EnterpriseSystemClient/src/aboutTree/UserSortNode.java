package aboutTree;

public class UserSortNode extends Node{


	/**
	 * serialVersionUID
	 * 客户分类节点
	 * 保有很多客户节点
	 */
	//private static final long serialVersionUID = 1L;
    private String myName;
    public UserSortNode(String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addUser(UserNode user){
		super.add(user);
	}
	public void removeUser(int index){
		super.remove(index);
	}
	public String getName(){
	return myName;
	}

}
