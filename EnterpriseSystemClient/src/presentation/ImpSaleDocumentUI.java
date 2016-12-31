package presentation;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import temp_businessImp.ExportBLImp;
import temp_businessImp.ImportDocumentImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.ImportDocumentVO;
import vo.SaleDocumentVO;
import aboutTree.ImpDocSortNode;
import aboutTree.ImpSaleDocTree;
import aboutTree.SaleDocSortNode;
import docDetailUI.CommodityDetilUI;
import enumClass.ResultMessage;
import enumClass.StateOfDocNode;
import enumClass.StateOfDocument;

/**
 * @author 小春 2014年12月13日下午8:39:06 EnterpriseSystem presentation
 *         ImpSaleDocumentUI.java 进货销售单管理界面
 */
public class ImpSaleDocumentUI extends JPanel {
	//private static final long serialVersionUID = 1L;
	private JPanel nextPanel;// 即将跳转的界面
	// 每个按钮对应的数字
	private static int BUTTON_SEND = 0;
	private static int BUTTON_ALLSEND = 1;
	private static int BUTTON_DEL = 2;
	private static int BUTTON_DETIL = 3;
	private static int BUTTON_TURNBACK = 4;
	private static int BUTTON_EXIT = 5;
	private static int BUTTON_DEAL = 6;
	private JScrollPane treePanel = new JScrollPane();// 存放树
	private JScrollPane tablePanel = new JScrollPane();// 存放表格
	private JTable docTabel;// 表格
	private ImpSaleDocTree docTree;// 树
	// 逻辑接口
	private ImportDocumentBLService importDocumentBLService;
	private ExportBLService exportBLService;

	// 按钮
	private MyButton send;
	private MyButton allSend;
	private MyButton delete;
	private MyButton turnBack;
	private MyButton docDetil;
	private MyButton exit;
	private MyButton deal;
	private JButton[] buttons;

	public ImpSaleDocumentUI() {
		this.setLayout(null);
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		importDocumentBLService = new ImportDocumentImp();
		exportBLService = new ExportBLImp();
		getButtonInit();
		getTreeInit();
	}

	private void getButtonInit() {
		send = new MyButton(StaticImage.backOfSend, 787, 79,
				StaticImage.backOfSend.getIconWidth(),
				StaticImage.backOfSend.getIconHeight());
		allSend = new MyButton(StaticImage.backOfjbu_AllSend, 659, 79,
				StaticImage.backOfjbu_AllSend.getIconWidth(),
				StaticImage.backOfjbu_AllSend.getIconHeight());
		delete = new MyButton(StaticImage.backOfDelete, 527, 79,
				StaticImage.backOfDelete.getIconWidth(),
				StaticImage.backOfDelete.getIconHeight());
		docDetil = new MyButton(StaticImage.backOfjbu_docDetil, 903, 79,
				StaticImage.backOfjbu_docDetil.getIconWidth(),
				StaticImage.backOfjbu_docDetil.getIconHeight());
		turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63, 50, 13);
		exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		deal= new MyButton(StaticImage.backOfDeal, 387,82, 90, 35);
		buttons = new JButton[] { send.jbutton, allSend.jbutton,
				delete.jbutton, docDetil.jbutton, turnBack.jbutton,
				exit.jbutton ,deal.jbutton};
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
			buttons[i].addActionListener(new ButtonListener(i, this));
			this.add(buttons[i]);
		}
	}

	private void getTreeInit() {
		docTree = new ImpSaleDocTree(this);
		treePanel = new JScrollPane();
		treePanel.getViewport().add(docTree.getDocumentTree());
		treePanel.setBounds(10, 120, 210, 410);
		this.add(treePanel);
	}

	// 鼠标监听
	class ButtonListener implements ActionListener {
		private int buttonID;
		@SuppressWarnings("unused")
		private ImpSaleDocumentUI currentPanel;

		public ButtonListener(int id, ImpSaleDocumentUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_SEND) {
				sendDoc();
			}
			if (buttonID == BUTTON_ALLSEND) {
				allSendDoc();
			}
			if (buttonID == BUTTON_DEL) {
				delDoc();
			}
			if (buttonID == BUTTON_DETIL) {
				showDocDetil();
			}
			if (buttonID == BUTTON_TURNBACK) {
				nextPanel = new ImpSalerMainUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
			if (buttonID == BUTTON_EXIT) {
				nextPanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
			if (buttonID == BUTTON_DEAL) {
				int index = docTabel.getSelectedRow();
				String ID=(String) docTabel.getValueAt(index, 0);
				if(ID.startsWith("JH")){
					ImportDocumentVO newImportDocumentVO;
					try {
						newImportDocumentVO = importDocumentBLService.findByID(ID);
						if(newImportDocumentVO.getStateOfDocument().equals(StateOfDocument.EXAMINED)){
							newImportDocumentVO.setStateOfDocument(StateOfDocument.DONE);
							importDocumentBLService.updateImportDraft(newImportDocumentVO);
						}else{}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}else if(ID.startsWith("XS")){
					try {
						SaleDocumentVO newSaleDocumentVO=exportBLService.findByDocumentID(ID);
					if(newSaleDocumentVO.getStateOfDocument().equals(StateOfDocument.EXAMINED)){
						newSaleDocumentVO.setStateOfDocument(StateOfDocument.DONE);
						exportBLService.updateExportDraft(newSaleDocumentVO);
					}else{}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
				}
			}
		}
	}

	private void sendDoc() {// 发送单据
		int index = docTabel.getSelectedRow();// 选择的行
		ImpDocSortNode tempImpNode = docTree.getSelectImpNode();
		SaleDocSortNode tempSaleNode = docTree.getSeleceSaleNode();

		if (index > -1) {
			if (tempImpNode == null) {// 选择了销售单的情况
				if ((tempSaleNode.getNodeState() == StateOfDocNode.SaleDraft)
						|| (tempSaleNode.getNodeState() == StateOfDocNode.SaleReturnDraft)) {// 选择了未发送单据
					ResultMessage result = exportBLService
							.sendExport(tempSaleNode.getDocumentVO(index)
									.getDocumentID());
					if (result == ResultMessage.SUCCESS) {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSuccess.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFail_Operation.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfSelect_Draft.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}

			}
			if (tempSaleNode == null) {// 选择进货单
				if ((tempImpNode.getNodeState() == StateOfDocNode.ImpDraft)
						|| (tempImpNode.getNodeState() == StateOfDocNode.ImpReturnDraft)) {// 选择了未发送单据
					System.out.println(tempImpNode.getDocumentVO(index));
					ResultMessage result = importDocumentBLService
							.sendImport(tempImpNode.getDocumentVO(index)
									.getDocumentID());
					if (result == ResultMessage.SUCCESS) {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSuccess.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFail_Operation.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfSelect_Draft.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			}
			docTree = new ImpSaleDocTree(this);
			treePanel.getViewport().add(docTree.getDocumentTree());
			treePanel.updateUI();
		}
	}

	private void delDoc() {// 删除单据
		int index = docTabel.getSelectedRow();// 选择的行
		ImpDocSortNode tempImpNode = docTree.getSelectImpNode();
		SaleDocSortNode tempSaleNode = docTree.getSeleceSaleNode();

		if (index > -1) {
			if (tempImpNode == null) {// 选择了销售单的情况
				if ((tempSaleNode.getNodeState() == StateOfDocNode.SaleDraft)
						|| (tempSaleNode.getNodeState() == StateOfDocNode.SaleReturnDraft)) {// 选择了未发送单据
					ResultMessage result = exportBLService
							.deleteExportDraft(tempSaleNode
									.getDocumentVO(index));
					if (result == ResultMessage.SUCCESS) {
						docTabel.remove(index);
						tempSaleNode.remove(index);
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSuccess.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFail_Operation.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfSelect_Draft.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}

			}
			if (tempSaleNode == null) {// 选择进货单
				if ((tempImpNode.getNodeState() == StateOfDocNode.ImpDraft)
						|| (tempImpNode.getNodeState() == StateOfDocNode.ImpReturnDraft)) {// 选择了未发送单据
					ResultMessage result = importDocumentBLService
							.deleteImportDraft(tempImpNode.getDocumentVO(index));
					if (result == ResultMessage.SUCCESS) {
						docTabel.remove(index);
						tempImpNode.remove(index);
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSuccess.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFail_Operation.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfSelect_Draft.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			}
		}
	}

	private void allSendDoc() {// 批量发送
		ImpDocSortNode tempImpNode = docTree.getSelectImpNode();
		SaleDocSortNode tempSaleNode = docTree.getSeleceSaleNode();
		if (tempImpNode == null) {// 选择了销售单的情况
			if ((tempSaleNode.getNodeState() == StateOfDocNode.SaleDraft)
					|| (tempSaleNode.getNodeState() == StateOfDocNode.SaleReturnDraft)) {// 选择了未发送单据
				ArrayList<SaleDocumentVO> tempList = tempSaleNode
						.getSaleDocList();
				for (int i = 0; i < tempList.size(); i++) {
					exportBLService.sendExport(tempList.get(i).getDocumentID());
				}
				// 刷新
				docTree = new ImpSaleDocTree(this);
				treePanel.getViewport().add(docTree.getDocumentTree());
				treePanel.updateUI();
			} else {
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame, StaticImage.backOfSelect_Draft.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
		if (tempSaleNode == null) {// 选择进货单
			if ((tempImpNode.getNodeState() == StateOfDocNode.ImpDraft)
					|| (tempImpNode.getNodeState() == StateOfDocNode.ImpReturnDraft)) {// 选择了未发送单据
				ArrayList<ImportDocumentVO> tempList = tempImpNode
						.getImportDocList();
				for (int i = 0; i < tempList.size(); i++) {
					importDocumentBLService.sendImport(tempList.get(i)
							.getDocumentID());
				}
				// 刷新
				docTree = new ImpSaleDocTree(this);
				treePanel.getViewport().add(docTree.getDocumentTree());
				treePanel.updateUI();
			} else {
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame, StaticImage.backOfSelect_Draft.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
	}

	private void showDocDetil() {// 显示信息
		int index = docTabel.getSelectedRow();
		ImpDocSortNode tempImpNode = docTree.getSelectImpNode();
		SaleDocSortNode tempSaleNode = docTree.getSeleceSaleNode();
		if (index > -1) {// 选择了表格
			if (tempImpNode == null) {// 选择了销售单的情况
				SaleDocumentVO tempDocumentVO = tempSaleNode
						.getDocumentVO(index);
				MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
				CommodityDetilUI myCommodityDetilUI = new CommodityDetilUI(
						newFrame, tempDocumentVO.getTheList());
				newFrame.getIni(myCommodityDetilUI);
			}
			if (tempSaleNode == null) {// 选择了进货单
				ImportDocumentVO tempDocumentVO = tempImpNode
						.getDocumentVO(index);
				MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
				CommodityDetilUI myCommodityDetilUI = new CommodityDetilUI(
						newFrame, tempDocumentVO.getTheList());
				newFrame.getIni(myCommodityDetilUI);
			}

		}
	}

	// 显示表格
	public void showDocumentInfo() {
		docTabel = docTree.creatDocTable();
		tablePanel.getViewport().add(docTabel);
		tablePanel.setBounds(220, 120, 790, 410);
		tablePanel.setVisible(true);
		this.add(tablePanel);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImpSaleDocUI.getImage(), 0, 0, null);
	}
}
