package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CommodityGiftPO;
import enumClass.ResultMessage;

public interface CommodityGiftDataService extends Remote{
	public ResultMessage init()  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
	public ResultMessage add(CommodityGiftPO p)  throws RemoteException ;
	public ResultMessage delete(CommodityGiftPO p)  throws RemoteException ;
	public ResultMessage update(CommodityGiftPO p)  throws RemoteException ;
	public CommodityGiftPO find(long id)  throws RemoteException ;
	public ArrayList<CommodityGiftPO> finds(String time)  throws RemoteException ;
	public ArrayList<CommodityGiftPO>displayAll()throws RemoteException ;
	public CommodityGiftPO findByIDForWriteBack(long documentID)  throws RemoteException;
	public long getFinalID() throws RemoteException;
}
