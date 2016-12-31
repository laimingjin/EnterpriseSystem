package aboutTree;

import vo.ReceiptVO;

public class AccountReceiveDocumentNode extends Node {
	//private static final long serialVersionUID = 1L;
	private ReceiptVO receiptVO = null;

	private AccountReceiveDocumentNode(String name) {
		super(name);
	}

	public AccountReceiveDocumentNode(ReceiptVO receiptVO) {
		this(receiptVO.getDocumentID() + " send: " + receiptVO.isSend()
				+ " pass: " + receiptVO.isPass() + " deal: "
				+ receiptVO.isDealed());
		this.receiptVO = receiptVO;
	}

	public ReceiptVO getAccountReceiveDocumentVO() {
		return receiptVO;
	}
}
