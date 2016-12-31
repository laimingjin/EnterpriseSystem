package aboutTree;

import vo.CustomerVO;

public class CustomerNode extends Node {
	/**
	 * 这是客户节点
	 * 用于存放CustomerVO
	 */
	//private static final long serialVersionUID = 1L;
	private CustomerVO customer = null;

	private CustomerNode(String name) {
		super(name);
	}

	public CustomerNode(CustomerVO customer) {
		this(customer.getCustomerName());
		this.customer = customer;
	}
	public CustomerVO getCustomer(){
		return customer;
	}
}
