package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CustomerPO;
import enumClass.ResultMessage;

public interface CustomerDataservice extends Remote {
	public CustomerPO findByID(int customerID)  throws RemoteException ;
	public CustomerPO findByName(String customerName)  throws RemoteException ;
    public ArrayList<CustomerPO> findVague(String info)  throws RemoteException ;
    public  ArrayList<CustomerPO>displayAll()throws RemoteException;
    public ResultMessage add(CustomerPO po)  throws RemoteException ;
    public ResultMessage delete(CustomerPO po)  throws RemoteException ;
    public ResultMessage updateBySaleManager(CustomerPO newpo)  throws RemoteException ;//或者这个可以删掉直接用同一个update方法就可
    public ResultMessage update(CustomerPO newpo)  throws RemoteException ;
   public int getFinalID()  throws RemoteException ;
   public void getinit() throws RemoteException;
    public ResultMessage finish()  throws RemoteException;
}
