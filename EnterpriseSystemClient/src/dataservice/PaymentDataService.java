package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.PaymentPO;
import enumClass.ResultMessage;


public interface PaymentDataService  extends Remote {
	public ResultMessage init()  throws RemoteException;
	public ResultMessage add( PaymentPO p)  throws RemoteException;
	public ResultMessage update( PaymentPO p)  throws RemoteException ;
	public ResultMessage delete(PaymentPO p)  throws RemoteException;
	public ArrayList<PaymentPO>displayAll()throws RemoteException;
	public PaymentPO findByDocumentID(String documentID)  throws RemoteException;
	public ArrayList<PaymentPO> findByCustomer(String customerName)  throws RemoteException;
	public ArrayList<PaymentPO> findByTime(String time)  throws RemoteException;
	public ArrayList<PaymentPO> findByUser(String userName)  throws RemoteException;
	public String getNewID() throws RemoteException;
	public PaymentPO findByIDForWriteBack(String documentID)  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
}
