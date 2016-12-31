package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.ReceiptPO;
import enumClass.ResultMessage;

public interface ReceiptDataService  extends Remote {
	public ResultMessage init()  throws RemoteException;
	public ResultMessage add( ReceiptPO p)  throws RemoteException;
	public ResultMessage update( ReceiptPO p)  throws RemoteException;
	public ResultMessage delete(ReceiptPO p)  throws RemoteException;
	public ArrayList<ReceiptPO> displayAll() throws RemoteException;
	public ReceiptPO findByDocumentID(String documentID)  throws RemoteException;
	public ArrayList<ReceiptPO> findByCustomer(String customerName)  throws RemoteException;
	public ArrayList<ReceiptPO> findByTime(String time)  throws RemoteException;
	public ArrayList<ReceiptPO> findByUser(String userName)  throws RemoteException;
	public String getNewID() throws RemoteException;
	public ReceiptPO findByIDForWriteBack(String documentID)  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
}
