package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.CommoditySnapshotPO;
import enumClass.ResultMessage;

public interface SnapshotDataService  extends Remote {
//public CommoditySnapshotPO beforeAdd(String today)  throws RemoteException;// 在建立今天的库存盘点之前要进行这步操作才能得到一个SnapshotPO
	public ResultMessage add(CommoditySnapshotPO p)  throws RemoteException;
	public CommoditySnapshotPO findBylot(String date)  throws RemoteException;//查询某一天的库存盘点
	public int getFinalID()  throws RemoteException;//得到最后一个ID
	public ResultMessage init()  throws RemoteException;
}
