package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.PaymentVO;
import enumClass.ResultMessage;

public interface PaymentBLService {
	public PaymentVO findByID(String id) throws RemoteException;

	public ResultMessage addPayment(PaymentVO vo);

	public ArrayList<PaymentVO> displayAll();

	public ResultMessage updatePayment(String documentID, PaymentVO vo);

	public ResultMessage deletePayment(String ID);

	public void endManagement();// 结束账户管理

	public void getinit();// 初始化部分数据

	public void output_Payment(String fileName, PaymentVO vo)
			throws RemoteException;

	public String getNewID();

	public ArrayList<PaymentVO> findPayment(String timezone, String customer)
			throws RemoteException;

	public PaymentVO findByIDForWriteBack(String id) throws RemoteException;
}
