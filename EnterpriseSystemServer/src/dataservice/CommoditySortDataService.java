package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import po.CommoditySortPO;
import enumClass.ResultMessage;


public interface CommoditySortDataService extends Remote{
	public ResultMessage init() throws RemoteException ;
	public ResultMessage finish() throws RemoteException ;
	
	public ResultMessage add(CommoditySortPO p) throws RemoteException ;
	public ResultMessage delete(CommoditySortPO p) throws RemoteException ;
	public ResultMessage update(CommoditySortPO p) throws RemoteException ;
	public CommoditySortPO find(int id)  throws RemoteException;
	
	public CommoditySortPO find(String name)throws  RemoteException,ClassNotFoundException, SQLException ;
	
	public int getFinalID()throws RemoteException ;
}
