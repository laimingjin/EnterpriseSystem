package presentation;

/**
 * 财务人员的单据管理的主界面
 * 左侧有一颗树可以进行选择付款单，收款单和现金费用单
 * 有增删改显示查找提交的功能
 * @author nancy
 * @version 1.0
 */
import inputUI.AccountCashDocumentInput;
import inputUI.AccountPayDocumentInput;
import inputUI.AccountReceiveDocumentInput;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.CashListBLService;
import temp_business.PaymentBLService;
import temp_business.ReceiptBLService;
import temp_businessImp.CashListBLImp;
import temp_businessImp.PaymentBLImp;
import temp_businessImp.ReceiptBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CashListVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import aboutTree.AccountCashDocumentNode;
import aboutTree.AccountDocumentTree;
import aboutTree.AccountPayDocumentNode;
import aboutTree.AccountReceiveDocumentNode;
import enumClass.ResultMessage;

public class AccountDocumentUI extends JPanel {
	//private static final long serialVersionUID = 1L;
	private static int JBO_ADD = 0; // 增加
	private static int JBO_DELETE = 1; // 删除
	private static int JBO_UPDATE = 2; // 修改
	private static int JBO_REFRESH = 3; //
	private static int JBO_SUBMIT = 4;
	private static int JBO_DEAL = 5; // 提交
	private static int JBO_TURNBACK = 6; // 返回上一级
	private static int JBO_EXIT = 7; // 退出登陆

	private JButton[] jButtons = new JButton[8];// 存放按钮的数组
	private JPanel nextJpanel;

	private JScrollPane myScrollPane;
	public String selectedSonSort;
	private JTable myTable;
	private AccountDocumentTree accountDocumentTree;
	PaymentBLService paymentBLService = new PaymentBLImp();
	ReceiptBLService receiptBLService = new ReceiptBLImp();
	CashListBLService cashListBLService = new CashListBLImp();
	// private DefaultTreeModel treeModel;
	JScrollPane pane = new JScrollPane();

	/**
	 * Create the application.
	 */
	public AccountDocumentUI() {
		initialize();
		setLayout(null);
		// 树
		accountDocumentTree = new AccountDocumentTree(this);
		pane.getViewport().add(accountDocumentTree.getAccountDocumentTree());
		pane.setBounds(10, 120, 210, 410);
		System.out.println(pane.getComponentCount());
		this.add(pane);
		// 表格
		myScrollPane = new JScrollPane();

	}

	/**
	 * Initialize the contents of the jpanel.
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82, 90, 35);
		this.add(add.jbutton);
		MyButton delete = new MyButton(StaticImage.backOfDelete, 358, 82, 90,
				35);
		this.add(delete.jbutton);
		MyButton update = new MyButton(StaticImage.backOfUpdate, 468, 82, 90,
				35);
		this.add(update.jbutton);
		MyButton refresh = new MyButton(StaticImage.backOfRefresh, 578, 80, 90,
				35);
		this.add(refresh.jbutton);
		MyButton submit = new MyButton(StaticImage.backOfjbu_submit, 698, 82,
				90, 35);
		this.add(submit.jbutton);
		MyButton deal = new MyButton(StaticImage.backOfDeal, 815, 81, 90, 35);
		this.add(deal.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		jButtons[0] = add.jbutton;
		jButtons[1] = delete.jbutton;
		jButtons[2] = update.jbutton;
		jButtons[3] = refresh.jbutton;
		jButtons[4] = submit.jbutton;
		jButtons[5] = deal.jbutton;
		jButtons[6] = turnBack.jbutton;
		jButtons[7] = exit.jbutton;

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}

	}

	/*
	 * 按钮监听内部类
	 */
	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private AccountDocumentUI accountDocumentUI;

		public ButtonsActionListener(int id, AccountDocumentUI myPanel) {
			buttonID = id;
			accountDocumentUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == JBO_ADD) {
				String type = accountDocumentTree.getSelectNode().getName();
				if (type == "未发送收款单") {
					MyDocumentInputFrame commodityInput = new MyDocumentInputFrame();
					AccountReceiveDocumentInput myInputUI = new AccountReceiveDocumentInput(
							accountDocumentUI, commodityInput);
					commodityInput.getIni(myInputUI);
				} else if (type == "未发送付款单") {

					MyDocumentInputFrame commodityInput = new MyDocumentInputFrame();
					AccountPayDocumentInput myInputUI = new AccountPayDocumentInput(
							accountDocumentUI, commodityInput);
					commodityInput.getIni(myInputUI);
				} else if (type == "未发送现金费用单") {
					MyDocumentInputFrame commodityInput = new MyDocumentInputFrame();
					AccountCashDocumentInput myInputUI = new AccountCashDocumentInput(
							accountDocumentUI, commodityInput);
					commodityInput.getIni(myInputUI);
				} else {
					// 需要跳出failToAddDocument
					// !!!!!!!!!!!!!!!!!!!!!!!!!!
				}

			}

			if (buttonID == JBO_DELETE) {
				removeAccountDocument();

			}

			if (buttonID == JBO_UPDATE) {
				// 调用BLUPDATE方法
				int deleteIndex = myTable.getSelectedRow();
				if (deleteIndex > -1) {
					String ID = (String) accountDocumentTree
							.getCustomer_table().getValueAt(deleteIndex, 2);
					if (ID.startsWith("FKD")) {
						try {
							PaymentVO paymentVO = paymentBLService.findByID(ID);
							int size = paymentVO.getTransferList().getTheList()
									.size();
							double total = 0;
							for (int i = 0; i < size; i++) {
								String priceString = (String) accountDocumentTree
										.getCustomer_table().getValueAt(
												deleteIndex + i, 8);
								double price = Double.parseDouble(priceString);
								total = total + price;
								paymentVO.getTransferList().getTheList().get(i)
										.setTransferPrice(price);

							}
							paymentVO.setTotalPrice(total);
							ResultMessage resultMessage = paymentBLService
									.updatePayment(ID, paymentVO);
							if (resultMessage.equals(ResultMessage.SUCCESS)) {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfSuccess.getImage());
								myTipsFrame.getIni(myFailTipsPanel);

							} else {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfFailToUpdate
												.getImage());
								myTipsFrame.getIni(myFailTipsPanel);
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (ID.startsWith("SKD")) {
						try {
							ReceiptVO receiptVO = receiptBLService.findByID(ID);
							int size = receiptVO.getTransferList().getTheList()
									.size();
							double total = 0;
							for (int i = 0; i < size; i++) {
								String priceString = (String) accountDocumentTree
										.getCustomer_table().getValueAt(
												deleteIndex + i, 8);
								double price = Double.parseDouble(priceString);
								total = total + price;
								receiptVO.getTransferList().getTheList().get(i)
										.setTransferPrice(price);

							}
							receiptVO.setTotalPrice(total);
							ResultMessage resultMessage = receiptBLService
									.updateReceipt(ID, receiptVO);
							if (resultMessage.equals(ResultMessage.SUCCESS)) {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfSuccess.getImage());
								myTipsFrame.getIni(myFailTipsPanel);

							} else {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfFailToUpdate
												.getImage());
								myTipsFrame.getIni(myFailTipsPanel);
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (ID.startsWith("XJ")) {
						try {
							CashListVO cashListVO = cashListBLService
									.findByID(ID);
							int size = cashListVO.getEntryList().getTheList()
									.size();

							double total = 0;
							for (int i = 0; i < size; i++) {
								String priceString = (String) accountDocumentTree
										.getCustomer_table().getValueAt(
												deleteIndex + i, 7);
								double price = Double.parseDouble(priceString);
								total = total + price;
								cashListVO.getEntryList().getTheList().get(i)
										.setEntryPrice(price);

							}
							cashListVO.setTotalPrice(total);

							ResultMessage resultMessage = cashListBLService
									.updateCashList(ID, cashListVO);
							if (resultMessage.equals(ResultMessage.SUCCESS)) {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfSuccess.getImage());
								myTipsFrame.getIni(myFailTipsPanel);

							} else {
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfFailToUpdate
												.getImage());
								myTipsFrame.getIni(myFailTipsPanel);
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {

					}
					// 得到了选中的行的编号
					// 通过IDy要去找到那个VO!!!!!!!!!!!!!!!!!!!!!!
					// 然后返回一个新的VOupdate
				}

			}

			if (buttonID == JBO_REFRESH) {
				accountDocumentTree = new AccountDocumentTree(accountDocumentUI);
				pane.getViewport().add(
						accountDocumentTree.getAccountDocumentTree());
				pane.updateUI();

			}
			if (buttonID == JBO_SUBMIT) {

				// 调用BLUPDATE方法，改为已发送
				// 然后重画
				int deleteIndex = myTable.getSelectedRow();
				if (deleteIndex > -1) {
					String ID = (String) myTable.getValueAt(deleteIndex, 2);
					if (ID.startsWith("SK")) {
						try {
							ReceiptVO receiptVO = receiptBLService.findByID(ID);
							receiptVO.setSend(true);
							receiptBLService.updateReceipt(ID, receiptVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (ID.startsWith("FK")) {
						try {
							PaymentVO paymentVO = paymentBLService.findByID(ID);
							paymentVO.setSend(true);
							paymentBLService.updatePayment(ID, paymentVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else if (ID.startsWith("XJFY")) {
						CashListVO cashListVO;
						try {
							cashListVO = cashListBLService.findByID(ID);
							cashListVO.setSend(true);
							cashListBLService.updateCashList(ID, cashListVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {

					}

					// 得到了选中的行的编号
					// 通过IDy要去找到那个VO!!!!!!!!!!!!!!!!!!!!!!
					// 然后返回一个新的VOupdate
				}

			}
			if (buttonID == JBO_DEAL) {
				int deleteIndex = myTable.getSelectedRow();
				if (deleteIndex > -1) {
					String ID = (String) myTable.getValueAt(deleteIndex, 2);
					if (ID.startsWith("SK")) {
						try {
							ReceiptVO receiptVO = receiptBLService.findByID(ID);
							receiptVO.setDealed(true);
							receiptBLService.updateReceipt(ID, receiptVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else if (ID.startsWith("FK")) {
						try {
							PaymentVO paymentVO = paymentBLService.findByID(ID);
							paymentVO.setDealed(true);
							paymentBLService.updatePayment(ID, paymentVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else if (ID.startsWith("XJFY")) {
						CashListVO cashListVO;
						try {
							cashListVO = cashListBLService.findByID(ID);
							cashListVO.setDealed(true);
							cashListBLService.updateCashList(ID, cashListVO);
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {

					}
				}

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

	public void addAccountReceiveDocument(ReceiptVO receiptVO) {
		String sortName = "未发送收款单";// 得到分类名称

		accountDocumentTree.setSeleceNode(sortName);
		accountDocumentTree.getSelectNode().add(
				new AccountReceiveDocumentNode(receiptVO));
		accountDocumentTree.getAccountDocumentTree().updateUI();
		showAccountDocumentInfo();
	}

	public void addAccountPayDocument(PaymentVO paymentVO) {
		String sortName = "未发送付款单";// 得到分类名称

		accountDocumentTree.setSeleceNode(sortName);
		accountDocumentTree.getSelectNode().add(
				new AccountPayDocumentNode(paymentVO));
		accountDocumentTree.getAccountDocumentTree().updateUI();
		showAccountDocumentInfo();
	}

	public void addAccountCashDocument(CashListVO cashListVO) {
		String sortName = "未发送现金费用单";// 得到分类名称

		accountDocumentTree.setSeleceNode(sortName);
		accountDocumentTree.getSelectNode().add(
				new AccountCashDocumentNode(cashListVO));
		accountDocumentTree.getAccountDocumentTree().updateUI();
		showAccountDocumentInfo();
	}

	public void showAccountDocumentInfo() {
		myTable = accountDocumentTree.creatAccountDocumentTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void removeAccountDocument() {// 删除客户

		int deleteIndex = myTable.getSelectedRow();

		if (deleteIndex > -1) {
			String ID = (String) myTable.getValueAt(deleteIndex, 2);
			System.out.println(ID);
			if (ID.startsWith("SK")) {
				ResultMessage resultMessage = receiptBLService
						.deleteReceipt(ID);
				if (resultMessage.equals(ResultMessage.SUCCESS)) {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSuccess.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					((DefaultTableModel) myTable.getModel())
							.removeRow(deleteIndex);
					accountDocumentTree.removeDocument(deleteIndex);
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_Delete.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			} else if (ID.startsWith("FK")) {

				ResultMessage resultMessage = paymentBLService
						.deletePayment(ID);
				if (resultMessage.equals(ResultMessage.SUCCESS)) {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSuccess.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					((DefaultTableModel) myTable.getModel())
							.removeRow(deleteIndex);
					accountDocumentTree.removeDocument(deleteIndex);
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_Delete.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			} else if (ID.startsWith("XJFY")) {
				ResultMessage resultMessage = cashListBLService
						.deleteCashList(ID);

				if (resultMessage.equals(ResultMessage.SUCCESS)) {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSuccess.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					((DefaultTableModel) myTable.getModel())
							.removeRow(deleteIndex);
					accountDocumentTree.removeDocument(deleteIndex);
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_Delete.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			} else {

			}

		}

		// ///调用BL删除
		// 22222222222222222222222222!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountDocumentUI.getImage(), 0, 0, null);
	}
}
