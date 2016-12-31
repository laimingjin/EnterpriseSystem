package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.SetAccountVO;
import enumClass.ResultMessage;

public interface SetAccountBLService {
	public ResultMessage addSetAccount(String today) throws RemoteException;//输入时间区段，然后BL调用得到商品账户等等的信息，生成期初建账
	public SetAccountVO  findSetAccount(String exactTime) throws RemoteException;//查找某一个具体时间的期初建账，因建账的时间是确定的
	public ArrayList<SetAccountVO>finds(String timezone) throws RemoteException;//查找一个时间区段的信息
	public ArrayList<SetAccountVO> displayAll() throws RemoteException;
	public int getFinalID()  throws RemoteException;
	public void getinit();// 初始化部分数据
	public  void endManagement();//结束账户管理
}
