package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.SystemPO;
import enumClass.ResultMessage;

public interface SystermDataService  extends Remote {
	public SystemPO find(String time)  throws RemoteException;
    public ResultMessage add(SystemPO po)  throws RemoteException;
    
    public ResultMessage finish()  throws RemoteException;
}
