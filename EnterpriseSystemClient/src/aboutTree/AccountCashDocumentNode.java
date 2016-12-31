package aboutTree;

import vo.CashListVO;

public class AccountCashDocumentNode extends Node{
//	private static final long serialVersionUID = 1L;
	private  CashListVO cashListVO= null;

	private AccountCashDocumentNode(String name) {
		super(name);
	}

	public AccountCashDocumentNode( CashListVO cashListVO) {
		this(cashListVO.getDocumentID()+" send: "+cashListVO.isSend()+" pass: "+cashListVO.isPass()+" deal: "+cashListVO.isDealed());
		this.cashListVO=cashListVO;
	}
	public CashListVO  getAccountCashDocumentVO(){
		return cashListVO;
	}
}
