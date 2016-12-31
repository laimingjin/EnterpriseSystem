package businesslogic;

import java.io.Serializable;

public class MockImportDoc extends ImportDocument  implements Serializable{
     /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String theDate;
     ImportList theList;
     double totalPrice;
	public MockImportDoc(String date,ImportList list,double price) {
		theDate=date;
		theList=list;
		totalPrice=price;
	}
	public String getTheDate() {
		return theDate;
	}
	public ImportList getTheList() {
		return theList;
	}
	public double getTotalPrice() {
		return totalPrice;
	}

}
