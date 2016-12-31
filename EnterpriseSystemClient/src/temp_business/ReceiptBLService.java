package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.ReceiptVO;
import enumClass.ResultMessage;

public interface ReceiptBLService {
	public void getinit();// 初始化部分数据

	public ReceiptVO findByID(String id) throws RemoteException;

	public ReceiptVO findByIDForWriteBack(String id) throws RemoteException;

	public ArrayList<ReceiptVO> findReceipt(String timezone, String customer)
			throws RemoteException;

	public ArrayList<ReceiptVO> displayAll();

	public ResultMessage addReceipt(ReceiptVO vo);

	public ResultMessage deleteReceipt(String ID);

	public ResultMessage updateReceipt(String documentID, ReceiptVO vo);

	public String getNewID();

	public void output_Receipt(String fileName, ReceiptVO vo)
			throws RemoteException;

	public void endManagement();// 结束账户管理

}
