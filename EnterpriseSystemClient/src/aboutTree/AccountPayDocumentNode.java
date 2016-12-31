package aboutTree;

import vo.PaymentVO;

public class AccountPayDocumentNode extends Node {
	/**
	 * 这是付款单节点 用于存放PaymentVO
	 */
//	private static final long serialVersionUID = 1L;
	private PaymentVO paymentVO = null;

	private AccountPayDocumentNode(String name) {
		super(name);
	}

	public AccountPayDocumentNode(PaymentVO paymentVO) {
		this(paymentVO.getDocumentID() + " send: " + paymentVO.isSend()
				+ " pass: " + paymentVO.isPass() + " deal: "
				+ paymentVO.isDealed());
		this.paymentVO = paymentVO;
	}

	public PaymentVO getAccountPayDocumentVO() {
		return paymentVO;
	}
}
