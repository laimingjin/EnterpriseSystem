package aboutTree;

import vo.UserVO;

public class UserNode extends Node{

	/**
	 * 用户节点
	 */
	//private static final long serialVersionUID = 1L;
    private UserVO user=null;
   
    public UserNode(String name){
    	super(name);
    }
    public UserNode(UserVO user){
    	this(user.getUserName());
    	this.user=user;
    }
    public UserVO getuserVO(){
    	return user;
    }
}
