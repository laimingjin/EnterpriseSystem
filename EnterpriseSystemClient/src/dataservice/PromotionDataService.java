package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.PromotionPO_Customer;
import po.PromotionPo_Package;
import po.PromotionPo_Price;
import enumClass.ResultMessage;

public interface PromotionDataService  extends Remote {

	public ResultMessage add(PromotionPo_Package po)  throws RemoteException ;
	public ResultMessage add(PromotionPo_Price po)  throws RemoteException;
	public ResultMessage add(PromotionPO_Customer po)  throws RemoteException;
	
	public ResultMessage update(PromotionPo_Package po)  throws RemoteException;//日期和id唯一确定一个策略
	public ResultMessage update(PromotionPo_Price po)  throws RemoteException;
	public ResultMessage update(PromotionPO_Customer po)  throws RemoteException;
	
	public ResultMessage delete(PromotionPo_Package po) throws RemoteException;
	public ResultMessage delete(PromotionPo_Price po) throws RemoteException;
	public ResultMessage delete(PromotionPO_Customer po) throws RemoteException;
	
	public ArrayList<PromotionPo_Package> findPackages(String date)  throws RemoteException;// 查找某时间制定的所以促销策略，这是查看有效的促销策略，所以在date之后没有下一次的促销策略
	public ArrayList<PromotionPo_Price> findPrices(String date)  throws RemoteException;
	public ArrayList<PromotionPO_Customer> findCustomers(String date)  throws RemoteException ;
	public ArrayList<PromotionPo_Package> dispAllPromotionPackage()  throws RemoteException;// 从begin位置处开始展示所有的促销策略
	public ArrayList<PromotionPo_Price> dispAllPromotionPrice()  throws RemoteException ;
	public ArrayList<PromotionPO_Customer> dispAllPromotionCustomer()  throws RemoteException;
	
	public int getFinalID_CustemerProm()throws RemoteException;
	public int getFinalID_PackageProm()throws RemoteException;
	public int getFinalID_PriceProm()throws RemoteException;
	
	
	
	public ResultMessage  finish ()  throws RemoteException ;
	
	public void getInit ()  throws RemoteException ;
}
