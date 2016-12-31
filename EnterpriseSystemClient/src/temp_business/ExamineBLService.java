package temp_business;

import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import enumClass.ResultMessage;

public interface ExamineBLService {

	public ResultMessage process(SaleDocumentVO saleDocumentVO);

	public ResultMessage process(ImportDocumentVO importDocumentVO);

	public ResultMessage process(ReceiptVO receiptVO);

	public ResultMessage process(PaymentVO paymentVO);

	public ResultMessage process(CashListVO cashListVO);

	public ResultMessage process(CommodityDocumentVO commodityDocumentVO);

}
