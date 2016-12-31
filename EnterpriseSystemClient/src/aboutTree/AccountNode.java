package aboutTree;

import vo.AccountVO;

public class AccountNode extends Node{

	//private static final long serialVersionUID = 1L;
	private AccountVO account = null;

	private AccountNode(String name) {
		super(name);
	}

	public AccountNode(AccountVO accountVO) {
		this(accountVO.getAccountName());
		this.account = accountVO;
	}
	public AccountVO getAccount(){
		return account;
	}
}
