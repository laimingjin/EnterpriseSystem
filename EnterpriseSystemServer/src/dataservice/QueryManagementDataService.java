package dataservice;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import po.QueryManagementPO;
import enumClass.ResultMessage;
public interface QueryManagementDataService  extends Remote {

	public QueryManagementPO  find(String id )  throws RemoteException;
	public Vector<QueryManagementPO> findVague (String info)  throws RemoteException;
	
	public ResultMessage  add (QueryManagementPO po)  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
}
