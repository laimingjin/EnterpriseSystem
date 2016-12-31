package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.CashListVO;
import enumClass.ResultMessage;

public interface CashListBLService {
	public CashListVO findByID(String id) throws RemoteException;
	public CashListVO findByIDForWriteBack(String id)throws RemoteException;
	public ResultMessage addCashList(CashListVO vo);
	public  ArrayList<CashListVO>displayAll();
	public ResultMessage updateCashList(String documentID,CashListVO vo);
	 public ResultMessage deleteCashList(String ID);
	public void endManagement();//结束账户管理
	public void getinit();// 初始化部分数据
	public void output_CashList(String fileName,CashListVO vo)throws RemoteException;
	public String getNewID();
	public ArrayList<CashListVO> findCashList(String timezone)throws RemoteException;
}
