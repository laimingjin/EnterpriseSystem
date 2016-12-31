package businesslogic;

import java.io.Serializable;

public class SaleLineItem implements Serializable{
	private static final long serialVersionUID = 1L;
	Commodity theCommodity;//商品
	
	int theNumber;//数量
	  String theMessage;//备注
	  public SaleLineItem(Commodity commodity,int number,String message){
		  theCommodity=commodity;
		  theNumber=number;
		  theMessage=message;
	  }
	  public double getTotalPurchasePrice(){
		  double purchasePrice=theCommodity.getLatestPurchasePrice();
		  return theNumber*purchasePrice;
	  }
	  public double getTotal(){
		  double price=theCommodity.getLatestRetailPrice();
		  return theNumber*price;
	  }
	  public double getPrice(){
		  return theCommodity.getLatestRetailPrice();
	  }

	public String getTheMessage() {
		return theMessage;
	}

	public Commodity getTheCommodity() {
		return theCommodity;
	}

	public int getTheNumber() {
		return theNumber;
	}
	  public void setTheNumber(int theNumber) {
			this.theNumber = theNumber;
		}

	  
}
