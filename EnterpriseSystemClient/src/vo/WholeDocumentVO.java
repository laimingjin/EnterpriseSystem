package vo;

import java.util.ArrayList;

public class WholeDocumentVO {
	
	ArrayList<SaleDocumentVO> saleDocumentVOs;
	ArrayList<ImportDocumentVO> importDocumentVOs;
	ArrayList<ReceiptVO> receiptVOs;
	ArrayList<PaymentVO>  paymentVOs;
	ArrayList<CashListVO> cashListVOs;
	ArrayList<CommodityDocumentVO> commodityDocumentVOs;
	ArrayList<CommodityGiftVO> commodityGiftVOs;
	ArrayList<DocumentVO> documentVOs;
	public WholeDocumentVO(ArrayList<SaleDocumentVO>  sVOs,ArrayList<ImportDocumentVO> iDocumentVOs,	ArrayList<ReceiptVO> rVOs,
			ArrayList<PaymentVO>  pVOs,	ArrayList<CashListVO>  cashVOs,ArrayList<CommodityDocumentVO>  cDocumentVOs,
			ArrayList<CommodityGiftVO>  cGiftVOs,			ArrayList<DocumentVO>  dVos){
		saleDocumentVOs=sVOs;
		importDocumentVOs=iDocumentVOs;
		receiptVOs=rVOs;
		paymentVOs=pVOs;
		cashListVOs=cashVOs;
		commodityDocumentVOs=cDocumentVOs;
		commodityGiftVOs=cGiftVOs;
		documentVOs=dVos;
		
	}
	public ArrayList<SaleDocumentVO> getSaleDocumentVOs() {
		return saleDocumentVOs;
	}
	public ArrayList<ImportDocumentVO> getImportDocumentVOs() {
		return importDocumentVOs;
	}
	public ArrayList<ReceiptVO> getReceiptVOs() {
		return receiptVOs;
	}
	public ArrayList<PaymentVO> getPaymentVOs() {
		return paymentVOs;
	}
	public ArrayList<CashListVO> getCashListVOs() {
		return cashListVOs;
	}
	public ArrayList<CommodityDocumentVO> getCommodityDocumentVOs() {
		return commodityDocumentVOs;
	}
	public ArrayList<CommodityGiftVO> getCommodityGiftVOs() {
		return commodityGiftVOs;
	}
	public ArrayList<DocumentVO> getDocumentVOs() {
		return documentVOs;
	}
}
