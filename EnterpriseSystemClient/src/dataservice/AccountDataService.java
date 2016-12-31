package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import enumClass.ResultMessage;
import po.AccountPO;

public interface AccountDataService extends Remote{
	public ResultMessage init()  throws RemoteException;
	public ResultMessage add(AccountPO p) throws RemoteException;
	public ResultMessage delete(AccountPO p) throws RemoteException;
	public ResultMessage update(AccountPO p,AccountPO newpo) throws RemoteException;
	public ResultMessage update(AccountPO p) throws RemoteException;
	public AccountPO find(String accountName) throws RemoteException;
	public ArrayList<AccountPO> finds(String info) throws RemoteException;//模糊查找
	public ArrayList<AccountPO> displayAll() throws RemoteException;//显示所有
	public ResultMessage finish() throws RemoteException;
}
