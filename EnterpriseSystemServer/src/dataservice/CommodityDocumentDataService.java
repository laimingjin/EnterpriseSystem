package dataservice;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.CommodityDocumentPO;
import enumClass.ResultMessage;

public interface CommodityDocumentDataService extends Remote {
	public ResultMessage init()  throws RemoteException;
	public ResultMessage finish()  throws RemoteException;
	public ResultMessage add(CommodityDocumentPO p)  throws RemoteException;
	public ResultMessage delete(CommodityDocumentPO p)  throws RemoteException;
	public ResultMessage update(CommodityDocumentPO p)  throws RemoteException;
	public CommodityDocumentPO find(int id)  throws RemoteException;
	public ArrayList<CommodityDocumentPO> findByTime(String time)  throws RemoteException;
	public ArrayList<CommodityDocumentPO>displayAll()throws RemoteException;
	public  int getFinalID()  throws RemoteException;
	public CommodityDocumentPO findByIDForWriteBack(int documentID)  throws RemoteException;
}