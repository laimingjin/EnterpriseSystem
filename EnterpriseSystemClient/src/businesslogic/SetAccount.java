package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class SetAccount implements Serializable {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String date;// 日期，当前
	int setAccountID;// 期初建账的ID
	ArrayList<Commodity> commodityList;
	ArrayList<Customer> customerList;
	ArrayList<Account> accountList;

	public SetAccount(String date, int setAccountID,
			ArrayList<Commodity> commodityList,
			ArrayList<Customer> customerList, ArrayList<Account> accountList) {
		this.date = date;
		this.setAccountID = setAccountID;
		this.commodityList = commodityList;
		this.customerList = customerList;
		this.accountList = accountList;

	}

	public ArrayList<Commodity> getCommodityList() {
		return commodityList;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public ArrayList<Account> getAccountList() {
		return accountList;
	}

	public String getDate() {

		return date;
	}

	public int getSetAccountID() {
		return setAccountID;
	}

}
