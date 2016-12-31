package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.SaleDocumentVO;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public interface ExportBLService {
	/**
	 * 创建进货草稿单 YSH 2014年12月11日下午12:58:51
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage addExportDraft(SaleDocumentVO vo);// 创建进货单

	/**
	 * 
	 * YSH 2014年12月11日下午2:08:33
	 * 
	 * @param DocNumber
	 * @return
	 */
	public ResultMessage deleteExportDraft(SaleDocumentVO vo);

	/**
	 * 修改进货草稿单 YSH 2014年12月11日下午2:13:28
	 * 
	 * @param DocNumber
	 * @param vo
	 * @return
	 */
	public ResultMessage updateExportDraft(SaleDocumentVO vo);

	/**
	 * 创建进货退货草稿单 YSH 2014年12月11日下午12:59:21
	 * 
	 * @param vo
	 * @return
	 */
	public SaleDocumentVO returnExportBackDraft(SaleDocumentVO vo);// 创建退货单

	// /**
	// * 删除进货退货草稿单 YSH 2014年12月11日下午2:09:00
	// *
	// * @param DocNumber
	// * @return
	// */
	// public ResultMessage deleteExportBackDraft(String DocNumber);

	/**
	 * 提交 进货单 YSH 2014年12月11日下午2:10:07
	 * 
	 * @return
	 */
	public ResultMessage sendExport(String DocNumber);// 单据提交审批

	/**
	 * 
	 * YSH 2014年12月11日下午9:02:33
	 * 
	 * @return
	 */
	public ArrayList<SaleDocumentVO> displayAll();

	/**
	 * 返回所有已处理单据 YSH 2014年12月11日下午9:12:37
	 * 
	 * @return
	 */
	public ArrayList<SaleDocumentVO> displayAllDone();

	// /**
	// * 提交进货退货单
	// * YSH 2014年12月11日下午2:09:54
	// *
	// * @return
	// */
	// public ResultMessage sendExportBack(String DocNumber);

	// public boolean changeInfo();//修改单据相关的库存及客户信息
	// public void endOperation();//退出管理

	/**
	 * 
	 * YSH 2014年12月14日下午2:51:10
	 * 
	 * @return
	 */
	public String getNewId(String kind);

	/**
	 * 
	 * YSH 2014年12月13日下午8:46:47
	 * 
	 * @param kind
	 * @param stateOfDocument
	 * @return
	 */
	public ArrayList<SaleDocumentVO> displayExport(String kind,
			StateOfDocument stateOfDocument);

	public ArrayList<SaleDocumentVO> findByTimezone(String timezone)
			throws RemoteException;

	public ArrayList<SaleDocumentVO> findSalesList(String time1, String time2,
			String commodityName, String customerName, String executive,
			String warehouse) throws RemoteException;

	public void getinit();// 初始化部分数据

	public void output_SaleDocument(String fileName, SaleDocumentVO vo);

	public ArrayList<SaleDocumentVO> findForBusinessList(String timezone)
			throws RemoteException;

	public ArrayList<SaleDocumentVO> findSaleDocument(String timezone,
			String customer, String executive, String storehouse)
			throws RemoteException;

	public SaleDocumentVO findByDocumentID(String documentID)
			throws RemoteException;

	public SaleDocumentVO findByIDForWriteBack(String id)
			throws RemoteException;
}
