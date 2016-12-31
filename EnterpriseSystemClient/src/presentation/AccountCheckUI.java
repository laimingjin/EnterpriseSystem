package presentation;

/**
 * 财务人员的经营查看主界面
 * 包括：销售明细表，经营历程表，经营情况表和导出按钮
 * @author nancy
 * @version 1.0
 * 
 */
import inputUI.AccountBusinessListInputUI;
import inputUI.AccountBusinessProcessInputUI;
import inputUI.AccountCheckInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.StaticImage;
import vo.BusinessListVO;
import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.CommodityGiftVO;
import vo.DocumentVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import vo.SalesListVO;
import vo.WholeDocumentVO;
import DocumentPresentationUI.CashListDocumentUI;
import DocumentPresentationUI.GiftDocumentUI;
import DocumentPresentationUI.ImportDocumentUI;
import DocumentPresentationUI.PaymentDocumentUI;
import DocumentPresentationUI.ProbelmDocumentUI;
import DocumentPresentationUI.ReceiptDocumentUI;
import DocumentPresentationUI.SaleDocumentUI;
import aboutTree.BusinessListTable;
import aboutTree.BusinessProcessTable;
import aboutTree.SalesListsTable;

public class AccountCheckUI extends JPanel {
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private static int JBO_SALESLIST = 0; // 销售明细表
	private static int JBO_BUSINESSPROCESSLIST = 1; // 经营历程表
	private static int JBO_BUSINESSLIST = 2; // 经营情况表
	private static int JBO_ACCOUNTCHECK = 3; // 导出
	private static int JBO_TURNBACK = 4; // 返回上一级
	private static int JBO_EXIT = 5; // 退出登陆

	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[6];// 存放按钮的数组
	private JTable myTable;
	private JScrollPane myScrollPane;
	public WholeDocumentVO wholeDocumentVO;

	public SalesListsTable salesListsTable;
	public BusinessProcessTable businessProcessTable;
	public BusinessListTable businessListTable;

	public AccountCheckUI() {
		initialize();
		setLayout(null);
		// 表格commodityTree=new CommodityTree(this);
		salesListsTable = new SalesListsTable(this);
		businessProcessTable = new BusinessProcessTable(this);
		businessListTable = new BusinessListTable(this);
		myScrollPane = new JScrollPane();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton salesList = new MyButton(StaticImage.backOfSalesList, 31, 169,
				145, 57);
		this.add(salesList.jbutton);
		MyButton businessProcessList = new MyButton(
				StaticImage.backOfBusinessProcessList, 31, 268, 145, 57);
		this.add(businessProcessList.jbutton);
		MyButton businessList = new MyButton(StaticImage.backOfBusinessList,
				32, 363, 145, 57);
		this.add(businessList.jbutton);
		MyButton accountExport = new MyButton(StaticImage.backOfListDetail, 32,
				462, 145, 57);
		this.add(accountExport.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);

		jButtons[0] = salesList.jbutton;
		jButtons[1] = businessProcessList.jbutton;
		jButtons[2] = businessList.jbutton;
		jButtons[3] = accountExport.jbutton;
		jButtons[4] = turnBack.jbutton;
		jButtons[5] = exit.jbutton;

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}

	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private AccountCheckUI accountCheckUI;

		public ButtonsActionListener(int id, AccountCheckUI myPanel) {
			buttonID = id;
			// mytree = jTree;
			accountCheckUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == JBO_SALESLIST) {
				MyInputFrame accountinput1 = new MyInputFrame();
				AccountCheckInputUI newPanel = new AccountCheckInputUI(
						accountCheckUI, accountinput1);
				accountinput1.getIni(newPanel);

			}

			if (buttonID == JBO_BUSINESSPROCESSLIST) {
				MyInputFrame accountinput2 = new MyInputFrame();
				AccountBusinessProcessInputUI newPanel = new AccountBusinessProcessInputUI(
						accountCheckUI, accountinput2);
				accountinput2.getIni(newPanel);

			}

			if (buttonID == JBO_BUSINESSLIST) {
				MyInputFrame timeinput = new MyInputFrame();
				AccountBusinessListInputUI newpanel = new AccountBusinessListInputUI(
						accountCheckUI, timeinput);
				timeinput.getIni(newpanel);

			}

			if (buttonID == JBO_ACCOUNTCHECK) {
				showDetails(wholeDocumentVO);

			}

			if (buttonID == JBO_TURNBACK) {
				nextJpanel = new AccountWholeUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}

			if (buttonID == JBO_EXIT) {
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
		}
	}

	// 销售明细表格
	public void showSaleListSearchInfo(ArrayList<SalesListVO> arr) {

		myTable = salesListsTable.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	// 经营查询表格
	public void showBusinessProcessSearchInfo(ArrayList<DocumentVO> arr) {

		myTable = businessProcessTable.SaleListTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	public void showBusinessListSearchInfo(ArrayList<BusinessListVO> arr) {

		myTable = businessListTable.SaleListTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	public void showDetails(WholeDocumentVO wholeDocumentVO) {
		int deleteIndex = myTable.getSelectedRow();
		String documentTypeString = (String) myTable.getValueAt(deleteIndex, 0);
		String ID = (String) myTable.getValueAt(deleteIndex, 2);
		if (documentTypeString.equals("现金费用单")) {
			ArrayList<CashListVO> cashListVO = wholeDocumentVO.getCashListVOs();
			for (int i = 0; i < cashListVO.size(); i++) {
				if (cashListVO.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					CashListDocumentUI myCommodityDetilUI = new CashListDocumentUI(
							newFrame, cashListVO.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("赠送单")) {
			ArrayList<CommodityGiftVO> commodityGiftVOs = wholeDocumentVO
					.getCommodityGiftVOs();
			long IDlong = Long.parseLong(ID);
			for (int i = 0; i < commodityGiftVOs.size(); i++) {
				if (commodityGiftVOs.get(i).getDocumentID() == IDlong) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					GiftDocumentUI myCommodityDetilUI = new GiftDocumentUI(
							newFrame, commodityGiftVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("报损单")) {
			ArrayList<CommodityDocumentVO> commodityDocumentVOs = wholeDocumentVO
					.getCommodityDocumentVOs();
			int IDINT = Integer.parseInt(ID);
			for (int i = 0; i < commodityDocumentVOs.size(); i++) {
				if (commodityDocumentVOs.get(i).getDocumentID() == IDINT) {

					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ProbelmDocumentUI myCommodityDetilUI = new ProbelmDocumentUI(
							newFrame, commodityDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("报溢单")) {
			ArrayList<CommodityDocumentVO> commodityDocumentVOs = wholeDocumentVO
					.getCommodityDocumentVOs();
			int IDINT = Integer.parseInt(ID);
			for (int i = 0; i < commodityDocumentVOs.size(); i++) {
				if (commodityDocumentVOs.get(i).getDocumentID() == IDINT) {

					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ProbelmDocumentUI myCommodityDetilUI = new ProbelmDocumentUI(
							newFrame, commodityDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("报警单")) {
			ArrayList<CommodityDocumentVO> commodityDocumentVOs = wholeDocumentVO
					.getCommodityDocumentVOs();
			int IDINT = Integer.parseInt(ID);
			for (int i = 0; i < commodityDocumentVOs.size(); i++) {
				if (commodityDocumentVOs.get(i).getDocumentID() == IDINT) {

					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ProbelmDocumentUI myCommodityDetilUI = new ProbelmDocumentUI(
							newFrame, commodityDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}
		} else if (documentTypeString.equals("收款单")) {
			ArrayList<ReceiptVO> receiptVOs = wholeDocumentVO.getReceiptVOs();
			for (int i = 0; i < receiptVOs.size(); i++) {
				if (receiptVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ReceiptDocumentUI myCommodityDetilUI = new ReceiptDocumentUI(
							newFrame, receiptVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);
				}
			}

		} else if (documentTypeString.equals("付款单")) {
			ArrayList<PaymentVO> paymentVOs = wholeDocumentVO.getPaymentVOs();
			for (int i = 0; i < paymentVOs.size(); i++) {
				if (paymentVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					PaymentDocumentUI myCommodityDetilUI = new PaymentDocumentUI(
							newFrame, paymentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);
				}
			}
		} else if (documentTypeString.equals("进货单")) {
			ArrayList<ImportDocumentVO> importDocumentVOs = wholeDocumentVO
					.getImportDocumentVOs();

			for (int i = 0; i < importDocumentVOs.size(); i++) {
				if (importDocumentVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ImportDocumentUI myCommodityDetilUI = new ImportDocumentUI(
							newFrame, importDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("销售单")) {
			ArrayList<SaleDocumentVO> saleDocumentVOs = wholeDocumentVO
					.getSaleDocumentVOs();

			for (int i = 0; i < saleDocumentVOs.size(); i++) {
				if (saleDocumentVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					SaleDocumentUI myCommodityDetilUI = new SaleDocumentUI(
							newFrame, saleDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}
		} else if (documentTypeString.equals("进货退货单")) {
			ArrayList<ImportDocumentVO> importDocumentVOs = wholeDocumentVO
					.getImportDocumentVOs();

			for (int i = 0; i < importDocumentVOs.size(); i++) {
				if (importDocumentVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					ImportDocumentUI myCommodityDetilUI = new ImportDocumentUI(
							newFrame, importDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}

		} else if (documentTypeString.equals("销售退货单")) {
			ArrayList<SaleDocumentVO> saleDocumentVOs = wholeDocumentVO
					.getSaleDocumentVOs();

			for (int i = 0; i < saleDocumentVOs.size(); i++) {
				if (saleDocumentVOs.get(i).getDocumentID().equals(ID)) {
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					SaleDocumentUI myCommodityDetilUI = new SaleDocumentUI(
							newFrame, saleDocumentVOs.get(i));
					newFrame.getIni(myCommodityDetilUI);

				}
			}
		} else {

		}

	}

	public WholeDocumentVO getWholeDocumentVO() {
		return wholeDocumentVO;
	}

	public void setWholeDocumentVO(WholeDocumentVO wholeDocumentVO) {
		this.wholeDocumentVO = wholeDocumentVO;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountCheckUI.getImage(), 0, 0, null);
	}

}
