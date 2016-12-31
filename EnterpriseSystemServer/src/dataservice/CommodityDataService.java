package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CommodityPO;
import enumClass.ResultMessage;


public interface CommodityDataService extends Remote {
public ResultMessage init()  throws RemoteException;
public ResultMessage finish()  throws RemoteException;
public ResultMessage add(CommodityPO p)  throws RemoteException;
public ResultMessage delete(CommodityPO p)  throws RemoteException;
public ResultMessage update(CommodityPO p)  throws RemoteException;
public CommodityPO find(int id)  throws RemoteException;
public ArrayList<CommodityPO> finds(String time1,String time2)  throws RemoteException;
public ArrayList<CommodityPO> findVague(String key)  throws RemoteException;
public CommodityPO find(String name ,String model)  throws RemoteException;
public ArrayList<CommodityPO> dispAll()  throws RemoteException;
public  int getFinalID()  throws RemoteException;
}
