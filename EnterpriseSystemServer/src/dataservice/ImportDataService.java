package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.ImportDocumentPO;
import enumClass.ResultMessage;


public interface ImportDataService extends Remote {
	public ImportDocumentPO findByID(String documentID)  throws RemoteException;
	public ArrayList< ImportDocumentPO>findByTimezone(String times)  throws RemoteException;
	public ArrayList< ImportDocumentPO>findByCommodity(String commodityName)  throws RemoteException;
	public ArrayList< ImportDocumentPO> findByCustomer(String  customerName)  throws RemoteException;
	public ArrayList< ImportDocumentPO> findByStorehouse(String storehouse)  throws RemoteException;
	public ArrayList< ImportDocumentPO> dispAll()  throws RemoteException;
    public ResultMessage add(ImportDocumentPO po)  throws RemoteException;
    public ResultMessage update(ImportDocumentPO po)  throws RemoteException;
    public ResultMessage delete(ImportDocumentPO po)throws RemoteException;
    public String getNewID(String kind)  throws RemoteException;//同样kind是进货单和进货退货单的区别
    public ImportDocumentPO findByIDForWriteBack(String documentID)  throws RemoteException;
    public ResultMessage finish()  throws RemoteException;
    public void getInit() throws RemoteException;
}
