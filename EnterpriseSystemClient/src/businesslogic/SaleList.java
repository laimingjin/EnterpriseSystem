package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class SaleList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<SaleLineItem> theList = new ArrayList<SaleLineItem>();

	public void addLineItem(SaleLineItem theItem) {
		theList.add(theItem);
	}

	public double getDocumentTotalPrice() {//销售单
		double result = 0;
		for (int i = 0; i < theList.size(); i++) {
			result = result + theList.get(i).getTotal();
		}
		return result;
	}

	public double getTotalPurchasePrice() {
		double result = 0;
		for (int j = 0; j < theList.size(); j++) {
			result = result + theList.get(j).getTotalPurchasePrice();
		}
		return result;
	}

	public ArrayList<SaleLineItem> getTheList() {
		return theList;
	}

	public void removeLineItem(int index) {
		theList.remove(index);
	}
}
