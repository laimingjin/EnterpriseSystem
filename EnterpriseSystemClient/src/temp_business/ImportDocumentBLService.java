package temp_business;

import java.rmi.RemoteException;
import java.util.ArrayList;

import vo.ImportDocumentVO;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

/**
 * @author YSH 2014年12月11日下午12:58:48
 */
public interface ImportDocumentBLService {
	/**
	 * 创建进货草稿单 YSH 2014年12月11日下午12:58:51
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage addImportDraft(ImportDocumentVO vo);// 创建进货单

	/**
	 * 
	 * YSH 2014年12月11日下午2:08:33
	 * 
	 * @param DocNumber
	 * @return
	 */
	public ResultMessage deleteImportDraft(ImportDocumentVO vo);

	/**
	 * 修改进货草稿单 YSH 2014年12月11日下午2:03:10
	 * 
	 * @param vo
	 * @return
	 */
	public ResultMessage updateImportDraft(ImportDocumentVO vo);

	/**
	 * 创建进货退货草稿单 YSH 2014年12月11日下午12:59:21
	 * 
	 * @param vo
	 * @return
	 */
	public ImportDocumentVO returnImportBackDraft(ImportDocumentVO vo);// 创建退货单

	// /**
	// * 删除进货退货草稿单 YSH 2014年12月11日下午2:09:00
	// *
	// * @param DocNumber
	// * @return
	// */
	// public ResultMessage deleteImportBackDraft(String DocNumber);

	/**
	 * 提交 进货单 YSH 2014年12月11日下午2:10:07
	 * 
	 * @return
	 */
	public ResultMessage sendImport(String DocNumber);// 单据提交审批

	// /**
	// * 提交进货退货单
	// * YSH 2014年12月11日下午2:09:54
	// *
	// * @return
	// */
	// public ResultMessage sendImportBack(String DocNumber);

	// public boolean changeInfo();//修改单据相关的库存及客户信息
	// public void endOperation();//退出管理

	/**
	 * 
	 * YSH 2014年12月11日下午9:02:33
	 * 
	 * @return
	 */
	public ArrayList<ImportDocumentVO> displayAll();

	/**
	 * 
	 * YSH 2014年12月14日下午2:51:10
	 * 
	 * @return
	 */
	public String getNewId(String kind);

	/**
	 * 
	 * YSH 2014年12月13日下午8:45:14
	 * 
	 * @param kind
	 *            代表单据种类 ： “JHD”“JHTHD”
	 * @param stateOfDocument
	 *            四态
	 * @return
	 */
	public ArrayList<ImportDocumentVO> displayImport(String kind,
			StateOfDocument stateOfDocument);

	public ArrayList<ImportDocumentVO> findByTimezone(String timezone)
			throws RemoteException;

	public void output_ImportDocument(String fileName, ImportDocumentVO vo);

	public ArrayList<ImportDocumentVO> findForBusinessList(String timezone)
			throws RemoteException;

	public ArrayList<ImportDocumentVO> findImportDocument(String timezone,
			String customer, String storehouse) throws RemoteException;

	public ImportDocumentVO findByID(String id) throws RemoteException;

	public ImportDocumentVO findByIDForWriteBack(String id)
			throws RemoteException;
}
