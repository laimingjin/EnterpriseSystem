package vo;

import java.util.ArrayList;

import businesslogic.SalesDetailLineItem;


public class SalesListVO {
	/**
	 * 销售明细表
	 */
	
//	SalesDetailList salesDetailList;
ArrayList<SalesDetailLineItem>	theList;
	
	public SalesListVO(ArrayList<SalesDetailLineItem>	theList){
			this.theList=theList;
	}
	


public ArrayList<SalesDetailLineItem> getTheList(){
	return theList;
}


}
