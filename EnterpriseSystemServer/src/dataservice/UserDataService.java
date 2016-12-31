package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.UserPO;
import enumClass.ResultMessage;

public interface UserDataService  extends Remote {
	public UserPO find(String UserName)  throws RemoteException;
	public ArrayList<UserPO> dispAll()  throws RemoteException;
    public ResultMessage add(UserPO po)  throws RemoteException;
    public ResultMessage delete(UserPO po)  throws RemoteException;
    public ResultMessage updata(UserPO oldPo,UserPO newPo) throws RemoteException;
	public ArrayList<UserPO> finds(String info)  throws RemoteException;
//    public ResultMessage updateByUser(UserPO po)  throws RemoteException;
//    public ResultMessage updateBySystem(UserPO po)  throws RemoteException;
//    public ResultMessage finish()  throws RemoteException;
	public void getinit() throws RemoteException;
	public int  getfinalID() throws RemoteException;
}
