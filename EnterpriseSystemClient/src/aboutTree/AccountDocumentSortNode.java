package aboutTree;


public class AccountDocumentSortNode extends Node {

	/**
	 * serialVersionUID
	 * 账户单据分类节点
	 * 保有很多单据节点
	 */
//	private static final long serialVersionUID = 1L;
    private String myName;
    
	public AccountDocumentSortNode (String sortName) {
		super(sortName);
		myName=sortName;
	}
	public void addAccountReceiveDocument(AccountReceiveDocumentNode accountReceiveDocumentNode){
		super.add(accountReceiveDocumentNode);
	}
	public void removeDocument(int index){
		super.remove(index);
	}
	public void addAccountPayDocument(AccountPayDocumentNode accountPayDocumentNode){
		super.add(accountPayDocumentNode);
	}
	public void addAccountCashDocument(AccountCashDocumentNode accountCashDocumentNode){
		super.add(accountCashDocumentNode);
	}
	
	public String getName(){
	return myName;
	}
}
