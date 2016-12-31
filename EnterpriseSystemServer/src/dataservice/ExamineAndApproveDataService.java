package dataservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import po.CashListPO;
import po.CommodityDocumentPO;
import po.CommodityGiftPO;
import po.ImportDocumentPO;
import po.PaymentPO;
import po.PromotionPO_Customer;
import po.PromotionPo_Package;
import po.PromotionPo_Price;
import po.ReceiptPO;
import po.SaleDocumentPO;
import enumClass.ResultMessage;

public interface ExamineAndApproveDataService extends Remote{
	public ResultMessage examineCommodityDocument(CommodityDocumentPO po) throws RemoteException ;
	public ResultMessage examineCommodityGift(CommodityGiftPO po) throws RemoteException ;
	public ResultMessage examineImportDocument(ImportDocumentPO po) throws RemoteException ;
	public ResultMessage examineSaleDocument(SaleDocumentPO po) throws RemoteException ;
	public ResultMessage examineReceipt(ReceiptPO po) throws RemoteException ;
	public ResultMessage examinePayment(PaymentPO po) throws RemoteException ;
	public ResultMessage examineCashList(CashListPO po) throws RemoteException ;
	public ResultMessage examinePromotion_Package(PromotionPo_Package po) throws RemoteException ;
	public ResultMessage examinePromotion_Price(PromotionPo_Price po) throws RemoteException ;
	public ResultMessage examinePromotion_Customer(PromotionPO_Customer po) throws RemoteException ;
	public ResultMessage  finish ()throws RemoteException;

}
