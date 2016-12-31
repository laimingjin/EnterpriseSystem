package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class EntryList implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
	 * 
	 */
//	private static final long serialVersionUID = -8186459362931996629L;
ArrayList<EntryLineItem>al=new ArrayList<EntryLineItem>();
public void addItem(EntryLineItem eli){
	al.add(eli);
	
}
//计算总的转账金额
public double getTotalPrice(){
	double result=0;
	 for(int i=0;i<al.size();i++){
		 result=result+al.get(i).getEntryPrice();
	 }
	 return result;
	
} 
public ArrayList<EntryLineItem> getTheList(){
	return al;
}

}
