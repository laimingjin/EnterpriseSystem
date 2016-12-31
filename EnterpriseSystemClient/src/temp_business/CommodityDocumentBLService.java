package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.CommodityDocumentVO;
import enumClass.ResultMessage;

public interface CommodityDocumentBLService {
	// 用于得到所有的问题单据的VO
	public ArrayList<CommodityDocumentVO> diaplay();

	// 创建库存问题单a
	public ResultMessage addCommodityDocument(
			CommodityDocumentVO commodityDocumentVO);

	// 删除
	public ResultMessage updateCommodityDocument(
			CommodityDocumentVO commodityDocumentVO);

	// 获得新的ID
	public int getNewCommodityDocumentID();

	public ResultMessage deleteCommodityDocument(int ID);// 删商品

	public ArrayList<CommodityDocumentVO> findCommodityDocument(String time)
			throws RemoteException;

	public CommodityDocumentVO getCommodityDocumentByID(int ID);

	public void output_CommodityDocument(String fileName, CommodityDocumentVO vo);

	public ArrayList<CommodityDocumentVO> findForBusinessList(String timezone)
			throws RemoteException;

	public CommodityDocumentVO findByIDForWriteBack(int id)
			throws RemoteException;
}
