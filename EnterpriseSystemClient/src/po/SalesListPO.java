package po;

import java.io.Serializable;
import java.util.ArrayList;

import businesslogic.SalesDetailLineItem;

public class SalesListPO implements Serializable{
	

//	private static final long serialVersionUID = 1L;
//	SalesDetailList salesDetailList;
ArrayList<SalesDetailLineItem>	theList;
	
	public SalesListPO(ArrayList<SalesDetailLineItem>	theList){
			this.theList=theList;
	}
	


public ArrayList<SalesDetailLineItem> getTheList(){
	return theList;
}

}
