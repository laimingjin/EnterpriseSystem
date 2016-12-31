package vo;

public class AccountVO {
	String accountName;//账号名称
	
	double accountPrice;//账号金额
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public AccountVO(String name,double price){
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
