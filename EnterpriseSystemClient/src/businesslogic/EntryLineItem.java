package businesslogic;

import java.io.Serializable;

public class EntryLineItem implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
String entryName;
double entryPrice;

String remark;

public EntryLineItem(String entryName,double entryPrice,String remark){
	this.entryName=entryName;
	this.entryPrice=entryPrice;
	this.remark=remark;
	
}
public void setEntryPrice(double entryPrice) {
	this.entryPrice = entryPrice;
}
public String getEntryName() {
	return entryName;
}
public double getEntryPrice() {
	return entryPrice;
}
public String getRemark() {
	return remark;
}


}
