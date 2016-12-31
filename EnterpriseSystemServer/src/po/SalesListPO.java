package po;

import java.io.Serializable;
import java.util.ArrayList;

import businesslogic.SalesDetailLineItem;
import businesslogic.SalesDetailList;

public class SalesListPO implements Serializable{
	
//	SalesDetailList salesDetailList;
ArrayList<SalesDetailLineItem>	theList;
	
	public SalesListPO(ArrayList<SalesDetailLineItem>	theList){
			this.theList=theList;
	}
	


public ArrayList<SalesDetailLineItem> getTheList(){
	return theList;
}

}
