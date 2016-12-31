package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CashListPO;
import enumClass.ResultMessage;


public interface CashListDataService extends Remote{
	public ResultMessage init()  throws RemoteException;
	public ResultMessage add( CashListPO p)  throws RemoteException;
	public ResultMessage update( CashListPO p)  throws RemoteException;
	public ArrayList<CashListPO>displayAll()throws RemoteException;
	public CashListPO findByDocumentID(String documentID)  throws RemoteException;
	public ArrayList<CashListPO> findByTime(String time)  throws RemoteException;
	public ArrayList<CashListPO> findByUser(String userName)  throws RemoteException;
	public String getNewID() throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
	public ResultMessage delete(CashListPO p)  throws RemoteException;
	public CashListPO findByIDForWriteBack(String documentID)  throws RemoteException;
}
