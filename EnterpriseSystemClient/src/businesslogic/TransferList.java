package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class TransferList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	//private static final long serialVersionUID = -5525577400278000121L;
	ArrayList<TransferLineItem> al = new ArrayList<TransferLineItem>();

	public void addItem(TransferLineItem tli) {
		al.add(tli);

	}

	// 计算总的转账金额
	public double getTotalPrice() {
		double result = 0;
		for (int i = 0; i < al.size(); i++) {
			result = result + al.get(i).getTransferPrice();
		}
		return result;

	}

	public ArrayList<TransferLineItem> getTheList() {
		return al;
	}

}
