package businesslogic;

import java.io.Serializable;

public class ImportLineItem implements Serializable{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

Commodity theCommodity;
 
int theNumber;
  String theMessage;
 
public ImportLineItem(Commodity Commodity,int number,String message){
	  theCommodity=Commodity;
	  theNumber=number;
	  theMessage=message;
  }
    public double getTotal(){
    	double result;
    	result=theNumber*(theCommodity.getLatestPurchasePrice());
    	return result;
    }
    
    public int getNumber(){
    	return theNumber;
    }
     public double getPrice(){
    	 return theCommodity.getLatestPurchasePrice();
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
