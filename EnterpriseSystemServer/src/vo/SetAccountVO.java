package vo;

import java.util.ArrayList;

import businesslogic.Account;
import businesslogic.CommodityLineItem;
import businesslogic.Customer;

public class SetAccountVO {

	String date;// 日期，当前
	int setAccountID;// 期初建账的ID
	ArrayList<CommodityLineItem> commodityList;
	ArrayList<Customer> customerList;
	ArrayList<Account> accountList;

	public SetAccountVO(String date, int setAccountID,
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
