package businesslogic;

import java.io.Serializable;

public class Account implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
	//private static final long serialVersionUID = 8644557871075726850L;
String accountName;
double accountPrice;

public Account(String name,double price){
	accountName=name;
	accountPrice=price;
}


public String getAccountName() {
	return accountName;
}

public double getAccountPrice() {
	return accountPrice;
}

}
