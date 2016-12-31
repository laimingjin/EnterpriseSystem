package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.SetAccountPO;
import enumClass.ResultMessage;
public interface SetAccountDataService  extends Remote  {
	public ResultMessage init()  throws RemoteException;
	public ResultMessage add(SetAccountPO p)  throws RemoteException;
	public SetAccountPO findByID(int id)  throws RemoteException;
	public SetAccountPO findByExactTime(String time)throws RemoteException;
	public ArrayList<SetAccountPO> findByTimezone(String timezone)  throws RemoteException;
	public ArrayList<SetAccountPO> displayAll()  throws RemoteException;
	public int getFinalID()  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
}
