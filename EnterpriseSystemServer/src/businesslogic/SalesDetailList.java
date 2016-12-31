package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class SalesDetailList implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	ArrayList<SalesDetailLineItem> al = new ArrayList<SalesDetailLineItem>();

	public void addItem(SalesDetailLineItem item) {
		// ArrayList<SalesDetailLineItem> theList=new
		// ArrayList<SalesDetailLineItem>();
		al.add(item);
	}

	public ArrayList<SalesDetailLineItem> getList() {
		return al;
	}
}
