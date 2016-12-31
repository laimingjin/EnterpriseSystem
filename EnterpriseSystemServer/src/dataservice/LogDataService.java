package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.LogPO;
import enumClass.ResultMessage;

public interface LogDataService extends Remote{
	public ResultMessage add(LogPO po)  throws RemoteException;
	public LogPO find(long id)throws RemoteException;
	public ArrayList<LogPO> findByTime(String timezone)throws RemoteException;
	public ArrayList<LogPO>displayAll()throws RemoteException;
	public long getFinalID()  throws RemoteException;
	public ResultMessage  finish()throws RemoteException;
	public ResultMessage init()  throws RemoteException;
}
