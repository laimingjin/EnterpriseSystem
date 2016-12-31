package presentation;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.CashListBLService;
import temp_business.CommodityDocumentBLService;
import temp_business.ExamineBLService;
import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import temp_business.PaymentBLService;
import temp_business.ReceiptBLService;
import temp_businessImp.CashListBLImp;
import temp_businessImp.CommodityDocumentBLImp;
import temp_businessImp.ExamineBLImp;
import temp_businessImp.ExportBLImp;
import temp_businessImp.ImportDocumentImp;
import temp_businessImp.PaymentBLImp;
import temp_businessImp.ReceiptBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import aboutTree.DocProTree;
import docDetailUI.CashDocDetailUI;
import docDetailUI.ImpDocDetailUI;
import docDetailUI.PayDocDetailUI;
import docDetailUI.RecDocDetailUI;
import docDetailUI.SaleDocDetailUI;
import enumClass.KindOfDocuments;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

/**
 * @author 小春 2014年12月10日下午11:10:38 EnterpriseSystem presentation
 *         DocumentProcessUI.java 单据审批
 */
public class DocumentProcessUI extends JPanel {

	// static final long serialVersionUID = 1L;
	// 选择单据类型
	private static KindOfDocuments kindOfDocuments;
	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[4];// 存放按钮的数组
	// 逻辑层接口们
	private ImportDocumentBLService importDocumentBLService = new ImportDocumentImp();
	private ExportBLService exportBLService = new ExportBLImp();
	private CommodityDocumentBLService commodityDocumentBLService = new CommodityDocumentBLImp();
	private PaymentBLService paymentBLService = new PaymentBLImp();
	private ReceiptBLService receiptBLService = new ReceiptBLImp();
	private CashListBLService cashListBLService = new CashListBLImp();
	private ExamineBLService examineBLService = new ExamineBLImp();
	// 各单据容器
	private ArrayList<ImportDocumentVO> importDocumentVOs = null;
	private ArrayList<SaleDocumentVO> saleDocumentVOs = null;
	private ArrayList<PaymentVO> paymentVOs = null;
	private ArrayList<ReceiptVO> receiptVOs = null;
	private ArrayList<CommodityDocumentVO> commodityDocumentVOs = null;
	private ArrayList<CashListVO> cashListVOs = null;

	private Vector<String> columnName = new Vector<String>();
	private JTable myDocTable = null;
	private DefaultTableModel newTabelModel = null;
	private DocProTree docProTree;

	private JScrollPane tabelPanel = new JScrollPane();
	private JScrollPane treePanel = new JScrollPane();
	private Vector<Vector<String>> document_info = new Vector<Vector<String>>();
	// 各按钮的编号
	private static int BUTTON_UPDATE = 0;
	private static int BUTTON_DETIL = 1;
	private static int BUTTON_ALLPASS = 2;
	private static int BUTTON_PASS = 3;
	private static int BUTTON_TURNBACK = 4;
	private static int BUTTON_EXIT = 5;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public DocumentProcessUI() {
		setLayout(null);
		initialize();
		getTreeInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton update = new MyButton(StaticImage.backOfManagerUpdate, 517,
				78, StaticImage.backOfManagerUpdate.getIconWidth(),
				StaticImage.backOfManagerUpdate.getIconHeight());
		this.add(update.jbutton);
		MyButton detil = new MyButton(StaticImage.backOfjbu_docDetil, 656, 78,
				StaticImage.backOfjbu_docDetil.getIconWidth(),
				StaticImage.backOfjbu_docDetil.getIconHeight());
		this.add(detil.jbutton);
		MyButton allPass = new MyButton(StaticImage.backOfAllPass, 787, 78, 90,
				36);
		this.add(allPass.jbutton);
		MyButton pass = new MyButton(StaticImage.backOfManagerPass, 908, 79,
				90, 35);
		this.add(pass.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);

		jButtons = new JButton[] { update.jbutton, detil.jbutton,
				allPass.jbutton, pass.jbutton, turnBack.jbutton, exit.jbutton };
		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}
	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;
		DocumentProcessUI currentPanel;

		public ButtonsActionListener(int id, DocumentProcessUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {

			if (buttonID == BUTTON_UPDATE) {
				updateDoc();
			}
			if (buttonID == BUTTON_DETIL) {
				showDetil();
			}
			if (buttonID == BUTTON_ALLPASS) {
				allPassDoc();
			}
			if (buttonID == BUTTON_PASS) {
				passDoc();
			}
			if (buttonID == BUTTON_TURNBACK) {
				nextJpanel = new ManagerWholeUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}

			if (buttonID == BUTTON_EXIT) {
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
		}
	}

	private void getTreeInit() {
		docProTree = new DocProTree(this);
		treePanel.getViewport().add(docProTree.getDocProTree());
		treePanel.setBounds(10, 120, 210, 410);
		this.add(treePanel);
	}

	private void addColumnName(KindOfDocuments kinds) {
		switch (kinds) {
		case SALEDOCUMENT:
		case SALERETURNDOC:
		case IMPORTDOCUMENT:
		case IMPRETURNDOC:
			columnName = new Vector<String>();
			columnName.add("单据类型");
			columnName.add("单据编号");
			columnName.add("客户");
			columnName.add("仓库");
			columnName.add("操作员");
			columnName.add("总价");
			columnName.add("备注");
			break;
		case COMMODITYDOCUMENT:
			columnName = new Vector<String>();
			columnName.add("单据类型");
			columnName.add("单据编号");
			columnName.add("问题类型");
			columnName.add("商品名称");
			columnName.add("商品型号");
			columnName.add("库存差量");
			break;
		case PAYMENT:
		case RECEIPT:
			columnName = new Vector<String>();
			columnName.add("单据类型");
			columnName.add("单据编号");
			columnName.add("客戶");
			columnName.add("总金额");
			columnName.add("操作员");
			break;
		case CASHLIST:
			columnName = new Vector<String>();
			columnName.add("单据类型");
			columnName.add("单据编号");
			columnName.add("账户名称");
			columnName.add("总金额");
			columnName.add("操作员");
			break;
		default:
			System.out.println("Error!");
			break;
		}
	}

	public void creatTabel(KindOfDocuments kind) {
		// TODO
		kindOfDocuments = kind;
		addColumnName(kind);// 添加表头
		if (kind == KindOfDocuments.IMPORTDOCUMENT) {
			creatImpTable();
		} else if (kind == KindOfDocuments.IMPRETURNDOC) {
			creatImpReturnTable();
		} else if (kind == KindOfDocuments.SALEDOCUMENT) {
			creatSaleTable();
		} else if (kind == KindOfDocuments.SALERETURNDOC) {
			creatSaleReturnTable();
		} else if (kind == KindOfDocuments.PAYMENT) {
			creatPayTable();
		} else if (kind == KindOfDocuments.RECEIPT) {
			creatRecTable();
		} else if (kind == KindOfDocuments.CASHLIST) {
			creatCashTable();
		} else if (kind == KindOfDocuments.COMMODITYDOCUMENT) {
			creatComTable();
		}
		myDocTable = new JTable(newTabelModel);
		tabelPanel.getViewport().add(myDocTable);
		tabelPanel.setBounds(220, 120, 790, 410);
		tabelPanel.setVisible(true);
		this.add(tabelPanel);
	}

	/**
	 * 
	 * 小春 2014年12月20日上午11:13:41 EnterpriseSystem presentation
	 * DocumentProcessUI.java 创造表格
	 */
	private void creatImpTable() {// 进货单
		importDocumentVOs = importDocumentBLService.displayImport("JHD",
				StateOfDocument.SENDED);
		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < importDocumentVOs.size(); i++) {// 添加信息
			Vector<String> row_info = new Vector<String>();

			ImportDocumentVO document = importDocumentVOs.get(i);
			row_info = new Vector<String>();
			row_info.add("进货单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getTheCustomer().getCustomerName());// 客户
			row_info.add(document.getWarehouse());// 仓库
			row_info.add(document.getTheUser().getUserName());// 操作员
			row_info.add(Double.toString(document.getTotalPrice()));// 总价
			row_info.add(document.getTheMessage());// 备注

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				}
				return false;
			}
		};
	}

	private void creatImpReturnTable() {// 进货退货单
		importDocumentVOs = importDocumentBLService.displayImport("JHTHD",
				StateOfDocument.SENDED);
		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < importDocumentVOs.size(); i++) {// 添加信息
			Vector<String> row_info = new Vector<String>();

			ImportDocumentVO document = importDocumentVOs.get(i);
			row_info = new Vector<String>();
			row_info.add("进货退货单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getTheCustomer().getCustomerName());// 客户
			row_info.add(document.getWarehouse());// 仓库
			row_info.add(document.getTheUser().getUserName());// 操作员
			row_info.add(Double.toString(document.getTotalPrice()));// 总价
			row_info.add(document.getTheMessage());// 备注

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				}
				return false;
			}
		};

	}

	private void creatSaleTable() {// 销售单
		saleDocumentVOs = exportBLService.displayExport("XSD",
				StateOfDocument.SENDED);
		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < saleDocumentVOs.size(); i++) {
			SaleDocumentVO document = saleDocumentVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("销售单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getTheCustomer().getCustomerName());// 客户
			row_info.add(document.getWarehouse());// 仓库
			row_info.add(document.getTheUser().getUserName());// 操作员
			row_info.add(Double.toString(document.getTotalPriceAfter()));// 总价
			row_info.add(document.getTheMessage());// 备注

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				}
				return false;
			}
		};
	}

	private void creatSaleReturnTable() {// 销售退货单
		saleDocumentVOs = exportBLService.displayExport("XSTHD",
				StateOfDocument.SENDED);
		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < saleDocumentVOs.size(); i++) {
			SaleDocumentVO document = saleDocumentVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("销售退货单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getTheCustomer().getCustomerName());// 客户
			row_info.add(document.getWarehouse());// 仓库
			row_info.add(document.getTheUser().getUserName());// 操作员
			row_info.add(Double.toString(document.getTotalPriceAfter()));// 总价
			row_info.add(document.getTheMessage());// 备注

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				}
				return false;
			}
		};

	}

	private void creatPayTable() {// 付款单
		ArrayList<PaymentVO> temPaymentVOs = paymentBLService.displayAll();
		paymentVOs = new ArrayList<PaymentVO>();
		for (int i = 0; i < temPaymentVOs.size(); i++) {// 取得待审批单据
			PaymentVO temPaymentVO = temPaymentVOs.get(i);
			if ((temPaymentVO.isSend() == true)
					&& (temPaymentVO.isPass() == false)) {
				paymentVOs.add(temPaymentVO);
			}
		}

		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < paymentVOs.size(); i++) {
			PaymentVO document = paymentVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("付款单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getCustomerName());// 客户
			row_info.add(Double.toString(document.getTotalPrice()));// 总价
			row_info.add(document.getUserName());// 操作员

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
	}

	private void creatRecTable() {// 收款单
		ArrayList<ReceiptVO> tempReceiptVOs = receiptBLService.displayAll();
		receiptVOs = new ArrayList<ReceiptVO>();
		for (int i = 0; i < tempReceiptVOs.size(); i++) {// 取得待审批单据
			ReceiptVO tempReceiptVO = tempReceiptVOs.get(i);
			if ((tempReceiptVO.isSend() == true)
					&& (tempReceiptVO.isPass() == false)) {
				receiptVOs.add(tempReceiptVO);
			}
		}

		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < receiptVOs.size(); i++) {
			ReceiptVO document = receiptVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("收款单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getCustomerName());// 客户
			row_info.add(Double.toString(document.getTotalPrice()));// 总价
			row_info.add(document.getUserName());// 操作员

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

	}

	private void creatCashTable() {// 现金费用单
		ArrayList<CashListVO> tempCash = cashListBLService.displayAll();
		cashListVOs = new ArrayList<CashListVO>();
		for (int i = 0; i < tempCash.size(); i++) {// 取得待审批单据
			CashListVO tempCashVO = tempCash.get(i);
			if ((tempCashVO.isSend() == true) && (tempCashVO.isPass() == false)) {
				cashListVOs.add(tempCashVO);
			}
		}

		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < cashListVOs.size(); i++) {
			CashListVO document = cashListVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("现金费用单");
			row_info.add(document.getDocumentID());// 编号
			row_info.add(document.getAccountName());// 账户
			row_info.add(Double.toString(document.getTotalPrice()));// 总价
			row_info.add(document.getUserName());// 操作员

			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 2) {
					return true;
				}
				return false;
			}
		};
	}

	private void creatComTable() {// 问题单据
		ArrayList<CommodityDocumentVO> tempComDoc = commodityDocumentBLService
				.diaplay();
		commodityDocumentVOs = new ArrayList<CommodityDocumentVO>();
		for (int i = 0; i < tempComDoc.size(); i++) {// 取得待审批单据
			CommodityDocumentVO tempComDocVO = tempComDoc.get(i);
			if ((tempComDocVO.isSend() == true)
					&& (tempComDocVO.isPass() == false)) {
				commodityDocumentVOs.add(tempComDocVO);
			}
		}

		document_info = new Vector<Vector<String>>();
		for (int i = 0; i < commodityDocumentVOs.size(); i++) {
			CommodityDocumentVO document = commodityDocumentVOs.get(i);
			Vector<String> row_info = new Vector<String>();
			row_info.add("库存问题单据");
			row_info.add(Integer.toString(document.getDocumentID()));// 编号
			row_info.add(document.getDocType());// 问题类型
			row_info.add(document.getCommodity().getCommodityName());// 商品名
			row_info.add(document.getCommodity().getCommodityModel());// 商品型号
			row_info.add(Double.toString(document.getRealQuantity()));// 库存差量
			document_info.add(row_info);
		}
		newTabelModel = new DefaultTableModel(document_info, columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

	}

	private void passDoc() {// 单单据审批
		int index = myDocTable.getSelectedRow();
		if (index > -1) {
			switch (kindOfDocuments) {
			case SALEDOCUMENT:
			case SALERETURNDOC:
				PassSaleDoc(index);
				break;
			case IMPORTDOCUMENT:
			case IMPRETURNDOC:
				PassImpDoc(index);
				break;
			case COMMODITYDOCUMENT:
				commodityDocumentVOs.get(index).setPass(true);
				commodityDocumentBLService
						.updateCommodityDocument(commodityDocumentVOs
								.get(index));
				examineBLService.process(commodityDocumentVOs.get(index));
				commodityDocumentVOs.remove(index);
				myDocTable.remove(index);
				MyTipsFrame myTipsFrame2 = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel2 = new MyFailTipsPanel(
						myTipsFrame2, StaticImage.backOfSuccess.getImage());
				myTipsFrame2.getIni(myFailTipsPanel2);
				break;
			case PAYMENT:
				paymentVOs.get(index).setPass(true);
				paymentBLService.updatePayment(paymentVOs.get(index)
						.getDocumentID(), paymentVOs.get(index));
				examineBLService.process(paymentVOs.get(index));
				paymentVOs.remove(index);
				myDocTable.remove(index);
				MyTipsFrame myTipsFrame3 = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel3 = new MyFailTipsPanel(
						myTipsFrame3, StaticImage.backOfSuccess.getImage());
				myTipsFrame3.getIni(myFailTipsPanel3);
				break;
			case RECEIPT:
				receiptVOs.get(index).setPass(true);
				receiptBLService.updateReceipt(receiptVOs.get(index)
						.getDocumentID(), receiptVOs.get(index));
				examineBLService.process(receiptVOs.get(index));
				receiptVOs.remove(index);
				myDocTable.remove(index);
				MyTipsFrame myTipsFrame4 = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel4 = new MyFailTipsPanel(
						myTipsFrame4, StaticImage.backOfSuccess.getImage());
				myTipsFrame4.getIni(myFailTipsPanel4);
				break;
			case CASHLIST:
				cashListVOs.get(index).setPass(true);
				cashListBLService.updateCashList(cashListVOs.get(index)
						.getDocumentID(), cashListVOs.get(index));
				examineBLService.process(cashListVOs.get(index));
				cashListVOs.remove(index);
				myDocTable.remove(index);
				MyTipsFrame myTipsFrame5 = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel5 = new MyFailTipsPanel(
						myTipsFrame5, StaticImage.backOfSuccess.getImage());
				myTipsFrame5.getIni(myFailTipsPanel5);
				break;
			default:
				System.out.println("Error!");
				break;
			}
		}
	}

	private void PassSaleDoc(int index){
		ResultMessage resutl=examineBLService.process(saleDocumentVOs.get(index));
		if (resutl==ResultMessage.SUCCESS) {
			saleDocumentVOs.remove(index);
			//myDocTable.remove(index);
			MyTipsFrame myTipsFrame = new MyTipsFrame(); 
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame, StaticImage.backOfSuccess.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}else if (resutl==ResultMessage.Commodity_NotExist) {//有商品已不存在
			MyTipsFrame myTipsFrame = new MyTipsFrame(); 
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame, StaticImage.backOfComNotExti.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}else if (resutl==ResultMessage.CommodityAmount_NotEnough) {//部分商品库存不足
			MyTipsFrame myTipsFrame = new MyTipsFrame(); 
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame, StaticImage.backOfComAmoNotEno.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}else if (resutl==ResultMessage.Customer_ReceivableLimit_Break) {//客户额度受限
			MyTipsFrame myTipsFrame = new MyTipsFrame(); 
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame, StaticImage.backofReceLinit.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}else if (resutl==ResultMessage.Customer_NotExist) {//客户已不存在
			MyTipsFrame myTipsFrame = new MyTipsFrame(); 
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame, StaticImage.backofCusNotExit.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
	}
	private void PassImpDoc(int index){
		ResultMessage result= examineBLService.process(importDocumentVOs.get(index));
	   if (result==ResultMessage.SUCCESS) {
		   importDocumentVOs.remove(index);
		// int ie=myDocTable.getSelectedRow();
		 //  myDocTable.remove(ie);
		   MyTipsFrame myTipsFrame1 = new MyTipsFrame();
		   MyFailTipsPanel myFailTipsPanel1 = new MyFailTipsPanel(
				   myTipsFrame1, StaticImage.backOfSuccess.getImage());
		   myTipsFrame1.getIni(myFailTipsPanel1);
	}else if (result==ResultMessage.Commodity_NotExist) {//有商品已不存在
		MyTipsFrame myTipsFrame = new MyTipsFrame(); 
		MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
				myTipsFrame, StaticImage.backOfComNotExti.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	}else if (result==ResultMessage.Customer_NotExist) {//客户已不存在
		MyTipsFrame myTipsFrame = new MyTipsFrame(); 
		MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
				myTipsFrame, StaticImage.backofCusNotExit.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	}
	}
	
 	private void allPassDoc() {// 批量审批
		switch (kindOfDocuments) {
		case SALEDOCUMENT:
		case SALERETURNDOC:
			allPassSaleDoc();
			break;
		case IMPORTDOCUMENT:
		case IMPRETURNDOC:
			allPassImpDoc();
			break;
		case COMMODITYDOCUMENT:
			allPassComDoc();
			break;
		case PAYMENT:
			allPassPayDoc();
			break;
		case RECEIPT:
			allPassRecDoc();
			break;
		case CASHLIST:
			allPassCashDoc();
			break;
		default:
			System.out.println("Error!");
			break;
		}
	}

	private void showDetil() {// 显示具体信息
		int index = myDocTable.getSelectedRow();
		MyDocumentInputFrame myDocumentInputFrame = new MyDocumentInputFrame();
		if (index > -1) {
			if ((kindOfDocuments == KindOfDocuments.IMPORTDOCUMENT)
					|| (kindOfDocuments == KindOfDocuments.IMPRETURNDOC)) {
				ImpDocDetailUI impDocDetailUI = new ImpDocDetailUI(
						myDocumentInputFrame, importDocumentVOs.get(index),
						this);
				myDocumentInputFrame.getIni(impDocDetailUI);
			} else if (kindOfDocuments == KindOfDocuments.SALEDOCUMENT
					|| kindOfDocuments == KindOfDocuments.SALERETURNDOC) {
				SaleDocDetailUI saleDocDetailUI = new SaleDocDetailUI(
						myDocumentInputFrame, saleDocumentVOs.get(index), this);
				myDocumentInputFrame.getIni(saleDocDetailUI);
			} else if (kindOfDocuments == KindOfDocuments.PAYMENT) {
				PayDocDetailUI payDocDetailUI = new PayDocDetailUI(
						myDocumentInputFrame, this, paymentVOs.get(index));
				myDocumentInputFrame.getIni(payDocDetailUI);
			} else if (kindOfDocuments == KindOfDocuments.RECEIPT) {
				RecDocDetailUI recDocDetailUI = new RecDocDetailUI(
						myDocumentInputFrame, this, receiptVOs.get(index));
				myDocumentInputFrame.getIni(recDocDetailUI);
			} else if (kindOfDocuments == KindOfDocuments.CASHLIST) {
				CashDocDetailUI cashDocDetailUI = new CashDocDetailUI(
						myDocumentInputFrame, this, cashListVOs.get(index));
				myDocumentInputFrame.getIni(cashDocDetailUI);
			} else {
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame, StaticImage.backOfIllegal.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
	}

	private void allPassSaleDoc() {
		for (int j = 0; j < saleDocumentVOs.size(); j++) {
			saleDocumentVOs.get(j).setPass(true);
			exportBLService.updateExportDraft(saleDocumentVOs.get(j));
			examineBLService.process(saleDocumentVOs.get(j));
		}
		saleDocumentVOs = new ArrayList<SaleDocumentVO>();// 清空
		MyTipsFrame myTipsFrame = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	}

	private void allPassImpDoc() {
		for (int i = 0; i < importDocumentVOs.size(); i++) {
			importDocumentVOs.get(i).setPass(true);
			importDocumentBLService.updateImportDraft(importDocumentVOs.get(i));
			examineBLService.process(importDocumentVOs.get(i));
		}
		importDocumentVOs = new ArrayList<ImportDocumentVO>();
		// myDocTable.remove(index);
		MyTipsFrame myTipsFrame1 = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel1 = new MyFailTipsPanel(myTipsFrame1,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame1.getIni(myFailTipsPanel1);
	}

	private void allPassPayDoc() {
		for (int index = 0; index < paymentVOs.size(); index++) {
			paymentVOs.get(index).setPass(true);
			paymentBLService.updatePayment(paymentVOs.get(index)
					.getDocumentID(), paymentVOs.get(index));
			examineBLService.process(paymentVOs.get(index));
		}
		paymentVOs = new ArrayList<PaymentVO>();
		// myDocTable.remove(index);
		MyTipsFrame myTipsFrame3 = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel3 = new MyFailTipsPanel(myTipsFrame3,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame3.getIni(myFailTipsPanel3);
	}

	private void allPassRecDoc() {
		for (int index = 0; index < receiptVOs.size(); index++) {
			receiptVOs.get(index).setPass(true);
			receiptBLService.updateReceipt(receiptVOs.get(index)
					.getDocumentID(), receiptVOs.get(index));
			examineBLService.process(receiptVOs.get(index));
		}
		receiptVOs = new ArrayList<ReceiptVO>();
		// myDocTable.remove(index);
		MyTipsFrame myTipsFrame4 = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel4 = new MyFailTipsPanel(myTipsFrame4,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame4.getIni(myFailTipsPanel4);
	}

	private void allPassCashDoc() {
		for (int index = 0; index < cashListVOs.size(); index++) {
			cashListVOs.get(index).setPass(true);
			cashListBLService.updateCashList(cashListVOs.get(index)
					.getDocumentID(), cashListVOs.get(index));
			examineBLService.process(cashListVOs.get(index));
		}
		cashListVOs = new ArrayList<CashListVO>();
		// myDocTable.remove(index);
		MyTipsFrame myTipsFrame5 = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel5 = new MyFailTipsPanel(myTipsFrame5,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame5.getIni(myFailTipsPanel5);
	}

	private void allPassComDoc() {
		for (int index = 0; index < commodityDocumentVOs.size(); index++) {
			commodityDocumentVOs.get(index).setPass(true);
			commodityDocumentBLService
					.updateCommodityDocument(commodityDocumentVOs.get(index));
			examineBLService.process(commodityDocumentVOs.get(index));
		}
		commodityDocumentVOs = new ArrayList<CommodityDocumentVO>();
		// myDocTable.remove(index);
		MyTipsFrame myTipsFrame2 = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel2 = new MyFailTipsPanel(myTipsFrame2,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame2.getIni(myFailTipsPanel2);
	}

	/**
	 * 更新数据用的
	 */
	private void updateDoc() {
		int index = myDocTable.getSelectedRow();
		if (index > -1) {
			if ((kindOfDocuments == KindOfDocuments.IMPORTDOCUMENT)
					|| (kindOfDocuments == KindOfDocuments.IMPRETURNDOC)) {
				ImportDocumentVO tempImp = importDocumentVOs.get(index);
				ImportDocumentVO newImp = new ImportDocumentVO(
						tempImp.getTheDate(), tempImp.getDocumentID(),
						tempImp.getTheCustomer(), tempImp.getWarehouse(),
						tempImp.getTheUser(), tempImp.getTheList(),
						(String) myDocTable.getValueAt(index, 6),
						tempImp.getTotalPrice(), tempImp.getStateOfDocument());
				UpadteDoc(newImp);

			} else if (kindOfDocuments == KindOfDocuments.SALEDOCUMENT
					|| kindOfDocuments == KindOfDocuments.SALERETURNDOC) {
				SaleDocumentVO tempImp = saleDocumentVOs.get(index);
				SaleDocumentVO newSale = new SaleDocumentVO(
						tempImp.getTheDate(), tempImp.getDocumentID(),
						tempImp.getTheCustomer(), tempImp.getExecutive(),
						tempImp.getWarehouse(), tempImp.getTheUser(),
						tempImp.getTheList(), (String) myDocTable.getValueAt(
								index, 6), tempImp.getTotalPriceBefore(),
						tempImp.getTheRebate(), tempImp.getTheVoucher(),
						tempImp.getTotalPriceAfter(), tempImp.isPass(),
						tempImp.isSend(), tempImp.isDealed());
				UpadteDoc(newSale);
			} else if (kindOfDocuments == KindOfDocuments.CASHLIST) {
				CashListVO tempCash = cashListVOs.get(index);
				CashListVO newCash = new CashListVO(tempCash.getDate(),
						tempCash.getDocumentID(), tempCash.getUserID(),
						tempCash.getAccountName(),
						(String) myDocTable.getValueAt(index, 2),
						tempCash.getEntryList(), tempCash.getTotalPrice(),
						tempCash.isPass(), tempCash.isSend(),
						tempCash.isDealed());
				UpadteDoc(newCash);
			}
		}
	}

	public void UpadteDoc(ImportDocumentVO importDocumentVO) {
		int index = myDocTable.getSelectedRow();
		importDocumentVOs.set(index, importDocumentVO);
		importDocumentBLService.updateImportDraft(importDocumentVO);
	}

	public void UpadteDoc(SaleDocumentVO saleDocumentVO) {
		int index = myDocTable.getSelectedRow();
		saleDocumentVOs.set(index, saleDocumentVO);
		exportBLService.updateExportDraft(saleDocumentVO);
	}

	public void UpadteDoc(PaymentVO paymentVO) {
		int index = myDocTable.getSelectedRow();
		paymentVOs.set(index, paymentVO);
		paymentBLService.updatePayment(paymentVO.getDocumentID(), paymentVO);
	}

	public void UpadteDoc(ReceiptVO receiptVO) {
		int index = myDocTable.getSelectedRow();
		receiptVOs.set(index, receiptVO);
		receiptBLService.updateReceipt(receiptVO.getDocumentID(), receiptVO);
	}

	public void UpadteDoc(CashListVO cashListVO) {
		int index = myDocTable.getSelectedRow();
		cashListVOs.set(index, cashListVO);
		cashListBLService
				.updateCashList(cashListVO.getDocumentID(), cashListVO);
	}

	public void UpadteDoc(CommodityDocumentVO commodityDocumentVO) {
		int index = myDocTable.getSelectedRow();
		commodityDocumentVOs.set(index, commodityDocumentVO);
		commodityDocumentBLService.updateCommodityDocument(commodityDocumentVO);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfDocumentProcessUI.getImage(), 0, 0, null);
	}
}
