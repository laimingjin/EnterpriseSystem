package businesslogic;

import java.io.Serializable;

public class TransferLineItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String accountName;
	double transferPrice;

	String remark;

	public TransferLineItem(String accountName, double transferPrice,
			String remark) {
		this.accountName = accountName;
		this.transferPrice = transferPrice;
		this.remark = remark;

	}

	public String getAccountName() {
		return accountName;
	}

	public double getTransferPrice() {
		return transferPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setTransferPrice(double transferPrice) {
		this.transferPrice = transferPrice;
	}
}
