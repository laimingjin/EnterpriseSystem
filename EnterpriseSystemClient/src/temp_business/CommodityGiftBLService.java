package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.CommodityGiftVO;
import enumClass.ResultMessage;

public interface CommodityGiftBLService {
	public ResultMessage addCommodityGift(CommodityGiftVO vo)
			throws RemoteException;

	public ResultMessage updataMessage(CommodityGiftVO vo);

	public ArrayList<CommodityGiftVO> displayAll();

	public CommodityGiftVO getCommodityGiftByID(int ID);

	public void output_CommodityGift(String fileName, CommodityGiftVO vo);

	public ArrayList<CommodityGiftVO> findForBusinessList(String timezone)
			throws RemoteException;

	public ArrayList<CommodityGiftVO> findCommodityGift(String timezone)
			throws RemoteException;

	public CommodityGiftVO findByIDForWriteBack(long id) throws RemoteException;
}
