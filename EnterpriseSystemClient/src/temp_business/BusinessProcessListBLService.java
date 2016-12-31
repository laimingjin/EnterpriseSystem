package temp_business;

import java.rmi.RemoteException;

import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.CommodityGiftVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import vo.WholeDocumentVO;
import enumClass.KindOfDocuments;
import enumClass.ResultMessage;

public interface BusinessProcessListBLService {
	public  WholeDocumentVO showBusinessProcessList(String timezone,String  kind,String customer,String executive,String storehouse) throws RemoteException;//经营历程表
	public ResultMessage writeBack (KindOfDocuments kind,String documentID) throws RemoteException;//红冲
	public SaleDocumentVO writeBackCopy_SaleDocument (String documentID)throws RemoteException;//红冲并复制
	public ImportDocumentVO writeBackCopy_ImportDocument (String documentID)throws RemoteException;//红冲并复制
	public CommodityDocumentVO writeBackCopy_CommodityDocument (String documentID)throws RemoteException;//红冲并复制
	public CommodityGiftVO  writeBackCopy_CommodityGift (String documentID)throws RemoteException;//红冲并复制
	public ReceiptVO writeBackCopy_Receipt (String documentID)throws RemoteException;//红冲并复制
	public PaymentVO writeBackCopy_Payment (String documentID)throws RemoteException;//红冲并复制
	public CashListVO writeBackCopy_CashList (String documentID)throws RemoteException;//红冲并复制
}
