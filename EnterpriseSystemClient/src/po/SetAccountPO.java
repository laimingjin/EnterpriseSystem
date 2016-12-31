package po;

import java.io.Serializable;
import java.util.ArrayList;

import businesslogic.Account;
import businesslogic.CommodityLineItem;
import businesslogic.Customer;

public class SetAccountPO implements Serializable{

	
	private static final long serialVersionUID = 5264894547432203755L;
	String date;// 日期，当前
	int setAccountID;// 期初建账的ID
	ArrayList<CommodityLineItem> commodityList;
	ArrayList<Customer> customerList;
	ArrayList<Account> accountList;

	public SetAccountPO(String date, int setAccountID,
			ArrayList<CommodityLineItem> commodityList,
			ArrayList<Customer> customerList, ArrayList<Account> accountList) {
		this.date = date;
		this.setAccountID = setAccountID;
		this.commodityList = commodityList;
		this.customerList = customerList;
		this.accountList = accountList;

	}

	public ArrayList<CommodityLineItem> getCommodityList() {
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
