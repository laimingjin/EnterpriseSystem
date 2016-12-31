package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import enumClass.ResultMessage;

public interface SalesListDataService  extends Remote {
//	public ArrayList<SalesListPO>  find(String timezone,String commodityName,String customer,String executive,String storehouse )  throws RemoteException ;
	public ResultMessage init()  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
}
