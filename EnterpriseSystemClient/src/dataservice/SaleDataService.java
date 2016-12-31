package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.SaleDocumentPO;
import enumClass.ResultMessage;

public interface SaleDataService  extends Remote {
	public ResultMessage  getInit()throws RemoteException;
	public SaleDocumentPO findByID(String documentID)  throws RemoteException;
	public ArrayList< SaleDocumentPO>findByTimezone(String times)  throws RemoteException;
	public ArrayList< SaleDocumentPO>findByCommodity(String commodityName)  throws RemoteException;
	public ArrayList< SaleDocumentPO> findByExecutive(String executive)  throws RemoteException;
	public ArrayList< SaleDocumentPO> findByCustomer(String  customerName)  throws RemoteException;
	public ArrayList< SaleDocumentPO> findByStorehouse(String storehouse)  throws RemoteException;
	public ArrayList< SaleDocumentPO> displayAll()  throws RemoteException;
    public ResultMessage add(SaleDocumentPO po)  throws RemoteException;
    public ResultMessage delete(SaleDocumentPO po)  throws RemoteException;
    public ResultMessage update(SaleDocumentPO po)  throws RemoteException;
    public String getNewID(String kind) throws RemoteException;// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号;kind是种类，销售单和销售退货单的区别
    public ArrayList< SaleDocumentPO>  findSalesList(String timezone, String commodityName,
			String customer, String executive, String storehouse)  throws RemoteException;//销售明细表的
    public ResultMessage finish()  throws RemoteException;
    public SaleDocumentPO findByIDForWriteBack(String documentID)  throws RemoteException;
}
