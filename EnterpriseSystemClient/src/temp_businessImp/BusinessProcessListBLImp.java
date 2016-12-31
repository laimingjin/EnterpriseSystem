package temp_businessImp;

import java.rmi.RemoteException;
import java.util.ArrayList;

import temp_business.BusinessProcessListBLService;
import temp_business.CashListBLService;
import temp_business.CommodityDocumentBLService;
import temp_business.CommodityGiftBLService;
import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import temp_business.PaymentBLService;
import temp_business.ReceiptBLService;
import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.CommodityGiftVO;
import vo.DocumentVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import vo.WholeDocumentVO;
import businesslogic.EntryLineItem;
import businesslogic.EntryList;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.SaleLineItem;
import businesslogic.SaleList;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import enumClass.KindOfDocuments;
import enumClass.PROBLEM;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class BusinessProcessListBLImp implements BusinessProcessListBLService {

	public WholeDocumentVO showBusinessProcessList(String timezone,
			String kind, String customer, String executive, String storehouse)
			throws RemoteException {
		// TODO Auto-generated method stub
		ImportDocumentBLService importDocumentImp = new ImportDocumentImp();
		ExportBLService exportBLImp = new ExportBLImp();
		CommodityDocumentBLService commodityDocumentBLImp = new CommodityDocumentBLImp();
		CommodityGiftBLService commodityGiftBLImp = new CommodityGiftBLImp();
		ReceiptBLService receiptBLImp = new ReceiptBLImp();
		PaymentBLService paymentBLImp = new PaymentBLImp();
		CashListBLService cashListBLImp = new CashListBLImp();

		ArrayList<ImportDocumentVO> importDocumentVOs = importDocumentImp
				.findImportDocument(timezone, customer, storehouse);
		ArrayList<SaleDocumentVO> saleDocumentVOs = exportBLImp
				.findSaleDocument(timezone, customer, executive, storehouse);
		ArrayList<CommodityDocumentVO> commodityDocumentVOs = commodityDocumentBLImp
				.findCommodityDocument(timezone);
		ArrayList<CommodityGiftVO> commodityGiftVOs = commodityGiftBLImp
				.findCommodityGift(timezone);
		ArrayList<ReceiptVO> receiptVOs = receiptBLImp.findReceipt(timezone,
				customer);
		ArrayList<PaymentVO> paymentVOs = paymentBLImp.findPayment(timezone,
				customer);
		ArrayList<CashListVO> cashListVOs = cashListBLImp
				.findCashList(timezone);
		ArrayList<DocumentVO> documentVOs = new ArrayList<DocumentVO>();

		if (kind.equals("")) {
			if (importDocumentVOs != null) {
				for (int i = 0; i < importDocumentVOs.size(); i++) {
					ImportDocumentVO vo = importDocumentVOs.get(i);
					boolean isPass = false;
					boolean isSend = false;
					boolean isDealed = false;
					if (vo.getStateOfDocument() == StateOfDocument.DRAFT) {
						isDealed = false;
						isPass = false;
						isSend = false;
					} else if (vo.getStateOfDocument() == StateOfDocument.EXAMINED) {
						isDealed = false;
						isPass = true;
						isSend = true;
					} else if (vo.getStateOfDocument() == StateOfDocument.SENDED) {
						isDealed = false;
						isPass = false;
						isSend = true;
					} else {
						isDealed = true;
						isPass = true;
						isSend = true;
					}
					if (vo.getDocumentID().startsWith("JHD")) {
						documentVOs.add(new DocumentVO("进货单", vo.getTheDate(),
								vo.getDocumentID(), isPass, isSend, isDealed));
					} else {
						documentVOs.add(new DocumentVO("进货退货单",
								vo.getTheDate(), vo.getDocumentID(), isPass,
								isSend, isDealed));
					}
				}

			}
			if (saleDocumentVOs != null) {
				for (int i = 0; i < saleDocumentVOs.size(); i++) {
					SaleDocumentVO vo = saleDocumentVOs.get(i);
					boolean isPass = false;
					boolean isSend = false;
					boolean isDealed = false;
					if (vo.getStateOfDocument() == StateOfDocument.DRAFT) {
						isDealed = false;
						isPass = false;
						isSend = false;
					} else if (vo.getStateOfDocument() == StateOfDocument.EXAMINED) {
						isDealed = false;
						isPass = true;
						isSend = true;
					} else if (vo.getStateOfDocument() == StateOfDocument.SENDED) {
						isDealed = false;
						isPass = false;
						isSend = true;
					} else {
						isDealed = true;
						isPass = true;
						isSend = true;
					}
					if (vo.getDocumentID().startsWith("XSD")) {
						documentVOs.add(new DocumentVO("销售单", vo.getTheDate(),
								vo.getDocumentID(), isPass, isSend, isDealed));
					} else {
						documentVOs.add(new DocumentVO("销售退货单",
								vo.getTheDate(), vo.getDocumentID(), isPass,
								isSend, isDealed));
					}
				}
			}
			if (commodityDocumentVOs != null) {
				for (int i = 0; i < commodityDocumentVOs.size(); i++) {
					CommodityDocumentVO vo = commodityDocumentVOs.get(i);
					if (vo.getProblem().equals(PROBLEM.LOSS)) {
						documentVOs.add(new DocumentVO("报损单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					} else if (vo.getProblem().equals(PROBLEM.OVERFLOW)) {
						documentVOs.add(new DocumentVO("报溢单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					} else {
						documentVOs.add(new DocumentVO("报警单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					}
				}
			}
			if (commodityGiftVOs != null) {
				for (int i = 0; i < commodityGiftVOs.size(); i++) {
					CommodityGiftVO vo = commodityGiftVOs.get(i);
					documentVOs.add(new DocumentVO("赠送单", vo.getDate(), vo
							.getDocumentID() + "", vo.isPass(), vo.isSend(), vo
							.isDealed()));

				}
			}
			if (receiptVOs != null) {
				for (int i = 0; i < receiptVOs.size(); i++) {
					ReceiptVO vo = receiptVOs.get(i);
					documentVOs.add(new DocumentVO("收款单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
			if (paymentVOs != null) {
				for (int i = 0; i < paymentVOs.size(); i++) {
					PaymentVO vo = paymentVOs.get(i);
					documentVOs.add(new DocumentVO("付款单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
			if (cashListVOs != null) {
				for (int i = 0; i < cashListVOs.size(); i++) {
					CashListVO vo = cashListVOs.get(i);
					documentVOs.add(new DocumentVO("现金费用单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
			for (int j = 0; j < importDocumentVOs.size(); j++) {
				System.out.println(documentVOs.get(j).getType());
			}

		} else if (kind.equals("销售类单据")) {
			if (saleDocumentVOs != null) {
				for (int i = 0; i < saleDocumentVOs.size(); i++) {
					SaleDocumentVO vo = saleDocumentVOs.get(i);
					if (vo.getDocumentID().startsWith("XSD")) {
						documentVOs.add(new DocumentVO("销售单", vo.getTheDate(),
								vo.getDocumentID(), vo.isPass(), vo.isSend(),
								vo.isDealed()));
					} else {
						documentVOs.add(new DocumentVO("销售退货单",
								vo.getTheDate(), vo.getDocumentID(), vo
										.isPass(), vo.isSend(), vo.isDealed()));
					}
				}
			}
		} else if (kind.equals("进货类单据")) {
			if (importDocumentVOs != null) {
				for (int i = 0; i < importDocumentVOs.size(); i++) {
					ImportDocumentVO vo = importDocumentVOs.get(i);
					if (vo.getDocumentID().startsWith("JHD")) {
						documentVOs.add(new DocumentVO("进货单", vo.getTheDate(),
								vo.getDocumentID(), vo.isPass(), vo.isSend(),
								vo.isDealed()));
					} else {
						documentVOs.add(new DocumentVO("进货退货单",
								vo.getTheDate(), vo.getDocumentID(), vo
										.isPass(), vo.isSend(), vo.isDealed()));
					}
				}
			}
		} else if (kind.equals("财务类单据")) {
			if (receiptVOs != null) {
				for (int i = 0; i < receiptVOs.size(); i++) {
					ReceiptVO vo = receiptVOs.get(i);
					documentVOs.add(new DocumentVO("收款单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
			if (paymentVOs != null) {
				for (int i = 0; i < paymentVOs.size(); i++) {
					PaymentVO vo = paymentVOs.get(i);
					documentVOs.add(new DocumentVO("付款单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
			if (cashListVOs != null) {
				for (int i = 0; i < cashListVOs.size(); i++) {
					CashListVO vo = cashListVOs.get(i);
					documentVOs.add(new DocumentVO("现金费用单", vo.getDate(), vo
							.getDocumentID(), vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
		} else if (kind.equals("库存类单据")) {
			if (commodityDocumentVOs != null) {
				for (int i = 0; i < commodityDocumentVOs.size(); i++) {
					CommodityDocumentVO vo = commodityDocumentVOs.get(i);
					if (vo.getProblem().equals(PROBLEM.LOSS)) {
						documentVOs.add(new DocumentVO("报损单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					} else if (vo.getProblem().equals(PROBLEM.OVERFLOW)) {
						documentVOs.add(new DocumentVO("报溢单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					} else {
						documentVOs.add(new DocumentVO("报警单", vo.getDate(), vo
								.getDocumentID() + "", vo.isPass(),
								vo.isSend(), vo.isDealed()));
					}
				}
			}
			if (commodityGiftVOs != null) {
				for (int i = 0; i < commodityGiftVOs.size(); i++) {
					CommodityGiftVO vo = commodityGiftVOs.get(i);
					documentVOs.add(new DocumentVO("赠送单", vo.getDate(), vo
							.getDocumentID() + "", vo.isPass(), vo.isSend(), vo
							.isDealed()));
				}
			}
		}

		return new WholeDocumentVO(saleDocumentVOs, importDocumentVOs,
				receiptVOs, paymentVOs, cashListVOs, commodityDocumentVOs,
				commodityGiftVOs, documentVOs);
	}

	public ResultMessage writeBack(KindOfDocuments kind, String documentID)
			throws RemoteException {
		// TODO Auto-generated method stub

		if (kind == KindOfDocuments.SALEDOCUMENT) {

			return writeBack_SaleDocument(documentID);

		} else if (kind == KindOfDocuments.IMPORTDOCUMENT) {
			return writeBack_ImportDocument(documentID);
		} else if (kind == KindOfDocuments.COMMODITYDOCUMENT) {
			return writeBack_CommodityDocument(documentID);
		} else if (kind == KindOfDocuments.COMMODITYGIFT) {
			return writeBack_CommodityGift(documentID);
		} else if (kind == KindOfDocuments.RECEIPT) {
			return writeBack_Receipt(documentID);
		} else if (kind == KindOfDocuments.PAYMENT) {
			return writeBack_Payment(documentID);

		} else {
			return writeBack_CashList(documentID);
		}
	}

	// 对销售单红冲
	public ResultMessage writeBack_SaleDocument(String documentID)
			throws RemoteException {
		ExportBLImp exportBLImp = new ExportBLImp();
		SaleDocumentVO vo = exportBLImp.findByIDForWriteBack(documentID);
		// System.out.println(vo.getDocumentID());
		SaleList saleList1 = new SaleList();
		SaleList saleList = new SaleList();
		saleList = vo.getTheList();
		// System.out.println(saleList.getTheList());
		for (int i = 0; i < saleList.getTheList().size(); i++) {
			saleList1.addLineItem(new SaleLineItem(saleList.getTheList().get(i)
					.getTheCommodity(), 0 - saleList.getTheList().get(i)
					.getTheNumber(), saleList.getTheList().get(i)
					.getTheMessage()));
		}
		SaleDocumentVO newVo = new SaleDocumentVO(vo.getTheDate(),
				vo.getDocumentID(), vo.getTheCustomer(), vo.getExecutive(),
				vo.getWarehouse(), vo.getTheUser(), saleList1,
				vo.getTheMessage(), 0 - vo.getTotalPriceBefore(),
				0 - vo.getTheRebate(), 0 - vo.getTheVoucher(),
				0 - vo.getTotalPriceAfter(), StateOfDocument.EXAMINED);
		return exportBLImp.addExportDraft(newVo);
	}

	// 对进货单红冲
	public ResultMessage writeBack_ImportDocument(String documentID)
			throws RemoteException {
		ImportDocumentImp importDocumentImp = new ImportDocumentImp();
		// ImportDocumentVO vo=importDocumentImp.findByID(documentID);
		ImportDocumentVO vo = importDocumentImp
				.findByIDForWriteBack(documentID);
		ImportList importList = vo.getTheList();
		ImportList importList1 = new ImportList();
		for (int i = 0; i < importList.getImportLineList().size(); i++) {
			importList1.addImportLineItem(new ImportLineItem(importList
					.getImportLineList().get(i).getTheCommodity(),
					0 - importList.getImportLineList().get(i).getNumber(),
					importList.getImportLineList().get(i).getTheMessage()));
		}
		ImportDocumentVO newVo = new ImportDocumentVO(vo.getTheDate(),
				vo.getDocumentID(), vo.getTheCustomer(), vo.getWarehouse(),
				vo.getTheUser(), importList1, vo.getTheMessage(),
				0 - vo.getTotalPrice(), true, true, false);
		return importDocumentImp.addImportDraft(newVo);
	}

	// 对库存单据红冲
	public ResultMessage writeBack_CommodityDocument(String documentID)
			throws RemoteException {
		CommodityDocumentBLImp commodityDocumentBLImp = new CommodityDocumentBLImp();
		// CommodityDocumentVO
		// vo=commodityDocumentBLImp.getCommodityDocumentByID(Integer.parseInt(documentID));
		CommodityDocumentVO vo = commodityDocumentBLImp
				.findByIDForWriteBack(Integer.parseInt(documentID));
		CommodityDocumentVO newVo = new CommodityDocumentVO(vo.getDate(),
				vo.getDocumentID(), vo.getCommodity(), vo.getProblem(),
				0 - vo.getRealQuantity(), true, true, false);
		return commodityDocumentBLImp.addCommodityDocument(newVo);
	}

	// 对赠送单红冲
	public ResultMessage writeBack_CommodityGift(String documentID)
			throws RemoteException {
		CommodityGiftBLImp commodityGiftBLImp = new CommodityGiftBLImp();
		// CommodityGiftVO
		// vo=commodityGiftBLImp.getCommodityGiftByID(Integer.parseInt(documentID));
		CommodityGiftVO vo = commodityGiftBLImp.findByIDForWriteBack(Long
				.parseLong(documentID));
		CommodityGiftVO newVo = new CommodityGiftVO(vo.getDate(),
				vo.getDocumentID(), vo.getCommodity(),
				0 - vo.getSendQuantity(), vo.getCustomer(), true, true, false);
		return commodityGiftBLImp.addCommodityGift(newVo);
	}

	// 对收款单红冲
	public ResultMessage writeBack_Receipt(String documentID)
			throws RemoteException {
		ReceiptBLImp receiptBLImp = new ReceiptBLImp();
		// ReceiptVO vo=receiptBLImp.findByID(documentID);
		ReceiptVO vo = receiptBLImp.findByIDForWriteBack(documentID);
		TransferList transferList = vo.getTransferList();
		TransferList transferList1 = new TransferList();
		for (int i = 0; i < transferList.getTheList().size(); i++) {
			transferList1.addItem(new TransferLineItem(transferList
					.getTheList().get(i).getAccountName(), 0 - transferList
					.getTheList().get(i).getTransferPrice(), transferList
					.getTheList().get(i).getRemark()));
		}
		ReceiptVO newVo = new ReceiptVO(vo.getDate(), documentID,
				vo.getCustomerID(), vo.getCustomerName(), vo.getUserID(),
				vo.getUserName(), transferList1, 0 - vo.getTotalPrice(), true,
				true, false);

		return receiptBLImp.addReceipt(newVo);
	}

	// 对付款单红冲
	public ResultMessage writeBack_Payment(String documentID)
			throws RemoteException {
		PaymentBLImp paymentBLImp = new PaymentBLImp();
		// PaymentVO vo=paymentBLImp.findByID(documentID);
		PaymentVO vo = paymentBLImp.findByIDForWriteBack(documentID);
		TransferList transferList = vo.getTransferList();
		TransferList transferList1 = new TransferList();
		for (int i = 0; i < transferList.getTheList().size(); i++) {
			transferList1.addItem(new TransferLineItem(transferList
					.getTheList().get(i).getAccountName(), 0 - transferList
					.getTheList().get(i).getTransferPrice(), transferList
					.getTheList().get(i).getRemark()));
		}
		PaymentVO newVo = new PaymentVO(vo.getDate(), documentID,
				vo.getCustomerID(), vo.getCustomerName(), vo.getUserID(),
				vo.getUserName(), transferList1, 0 - vo.getTotalPrice(), true,
				true, false);

		return paymentBLImp.addPayment(newVo);
	}

	// 对现金费用单红冲
	public ResultMessage writeBack_CashList(String documentID)
			throws RemoteException {
		CashListBLImp cashListBLImp = new CashListBLImp();
		// CashListVO vo=cashListBLImp.findByID(documentID);
		CashListVO vo = cashListBLImp.findByIDForWriteBack(documentID);
		EntryList entryList = vo.getEntryList();
		EntryList entryList1 = new EntryList();
		for (int i = 0; i < entryList.getTheList().size(); i++) {
			entryList1
					.addItem(new EntryLineItem(entryList.getTheList().get(i)
							.getEntryName(), 0 - entryList.getTheList().get(i)
							.getEntryPrice(), entryList.getTheList().get(i)
							.getRemark()));
		}
		CashListVO newVo = new CashListVO(vo.getDate(), documentID,
				vo.getUserID(), vo.getUserName(), vo.getAccountName(),
				entryList1, 0 - vo.getTotalPrice(), true, true, false);

		return cashListBLImp.addCashList(newVo);

	}

	// 对销售单红冲复制
	public SaleDocumentVO writeBackCopy_SaleDocument(String documentID)
			throws RemoteException {
		// TODO Auto-generated method stub
		writeBack_SaleDocument(documentID);// 先红冲生成数量取负单据
		ExportBLImp exportBLImp = new ExportBLImp();
		SaleDocumentVO vo = exportBLImp.findByDocumentID(documentID);

		SaleList saleList1 = new SaleList();
		SaleList saleList = vo.getTheList();
		for (int i = 0; i < saleList.getTheList().size(); i++) {
			saleList1.addLineItem(new SaleLineItem(saleList.getTheList().get(i)
					.getTheCommodity(), 0, saleList.getTheList().get(i)
					.getTheMessage()));
		}
		SaleDocumentVO newVo = new SaleDocumentVO(vo.getTheDate(),
				vo.getDocumentID(), vo.getTheCustomer(), vo.getExecutive(),
				vo.getWarehouse(), vo.getTheUser(), saleList1,
				vo.getTheMessage(), 0, 0, 0, 0, StateOfDocument.DRAFT);
		return newVo;

	}

	// 对进货单红冲复制
	public ImportDocumentVO writeBackCopy_ImportDocument(String documentID)
			throws RemoteException {
		ImportDocumentImp importDocumentImp = new ImportDocumentImp();
		ImportDocumentVO vo = importDocumentImp.findByID(documentID);
		ImportList importList = vo.getTheList();
		ImportList importList1 = new ImportList();
		for (int i = 0; i < importList.getImportLineList().size(); i++) {
			importList1.addImportLineItem(new ImportLineItem(importList
					.getImportLineList().get(i).getTheCommodity(), 0,
					importList.getImportLineList().get(i).getTheMessage()));
		}
		ImportDocumentVO newVo = new ImportDocumentVO(vo.getTheDate(),
				vo.getDocumentID(), vo.getTheCustomer(), vo.getWarehouse(),
				vo.getTheUser(), importList1, vo.getTheMessage(), 0, false,
				false, false);
		return newVo;
	}

	// 对库存单据红冲复制
	public CommodityDocumentVO writeBackCopy_CommodityDocument(String documentID)
			throws RemoteException {
		CommodityDocumentBLImp commodityDocumentBLImp = new CommodityDocumentBLImp();
		CommodityDocumentVO vo = commodityDocumentBLImp
				.getCommodityDocumentByID(Integer.parseInt(documentID));
		CommodityDocumentVO newVo = new CommodityDocumentVO(vo.getDate(),
				vo.getDocumentID(), vo.getCommodity(), vo.getProblem(), 0,
				false, false, false);
		return newVo;
	}

	// 对赠送单红冲复制
	public CommodityGiftVO writeBackCopy_CommodityGift(String documentID)
			throws RemoteException {
		writeBack_CommodityGift(documentID);
		CommodityGiftBLImp commodityGiftBLImp = new CommodityGiftBLImp();
		CommodityGiftVO vo = commodityGiftBLImp.getCommodityGiftByID(Integer
				.parseInt(documentID));
		CommodityGiftVO newVo = new CommodityGiftVO(vo.getDate(),
				vo.getDocumentID(), vo.getCommodity(), 0, vo.getCustomer(),
				false, false, false);
		return newVo;
	}

	// 对收款单红冲复制
	public ReceiptVO writeBackCopy_Receipt(String documentID)
			throws RemoteException {
		writeBack_Receipt(documentID);
		ReceiptBLImp receiptBLImp = new ReceiptBLImp();
		ReceiptVO vo = receiptBLImp.findByID(documentID);
		TransferList transferList = vo.getTransferList();
		TransferList transferList1 = new TransferList();
		for (int i = 0; i < transferList.getTheList().size(); i++) {
			transferList1.addItem(new TransferLineItem(transferList
					.getTheList().get(i).getAccountName(), 0, transferList
					.getTheList().get(i).getRemark()));
		}
		ReceiptVO newVo = new ReceiptVO(vo.getDate(), documentID,
				vo.getCustomerID(), vo.getCustomerName(), vo.getUserID(),
				vo.getUserName(), transferList1, 0, false, false, false);

		return newVo;
	}

	// 对付款单红冲复制
	public PaymentVO writeBackCopy_Payment(String documentID)
			throws RemoteException {
		writeBack_Payment(documentID);
		PaymentBLImp paymentBLImp = new PaymentBLImp();
		PaymentVO vo = paymentBLImp.findByID(documentID);
		TransferList transferList = vo.getTransferList();
		TransferList transferList1 = new TransferList();
		for (int i = 0; i < transferList.getTheList().size(); i++) {
			transferList1.addItem(new TransferLineItem(transferList
					.getTheList().get(i).getAccountName(), 0, transferList
					.getTheList().get(i).getRemark()));
		}
		PaymentVO newVo = new PaymentVO(vo.getDate(), documentID,
				vo.getCustomerID(), vo.getCustomerName(), vo.getUserID(),
				vo.getUserName(), transferList1, 0, false, false, false);

		return newVo;
	}

	// 对现金费用单红冲复制
	public CashListVO writeBackCopy_CashList(String documentID)
			throws RemoteException {
		writeBack_CashList(documentID);
		CashListBLImp cashListBLImp = new CashListBLImp();
		CashListVO vo = cashListBLImp.findByID(documentID);
		EntryList entryList = vo.getEntryList();
		EntryList entryList1 = new EntryList();
		for (int i = 0; i < entryList.getTheList().size(); i++) {
			entryList1.addItem(new EntryLineItem(entryList.getTheList().get(i)
					.getEntryName(), 0, entryList.getTheList().get(i)
					.getRemark()));
		}
		CashListVO newVo = new CashListVO(vo.getDate(), documentID,
				vo.getUserID(), vo.getUserName(), vo.getAccountName(),
				entryList1, 0, false, false, false);

		return newVo;

	}

}
