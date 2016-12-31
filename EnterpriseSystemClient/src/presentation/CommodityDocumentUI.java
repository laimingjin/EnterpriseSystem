package presentation;

/**
 * 库存管理人员的单据管理界面
 * @author nancy
 * @version 1.0
 */
import inputUI.ProblemDocumentInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.CommodityBLService;
import temp_business.CommodityDocumentBLService;
import temp_businessImp.CommodityBLImp;
import temp_businessImp.CommodityDocumentBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityDocumentVO;
import aboutTree.CommodityDocumentNode;
import aboutTree.CommodityDocumentTree;
import enumClass.PROBLEM;
import enumClass.ResultMessage;

public class CommodityDocumentUI extends JPanel {
//	private static final long serialVersionUID = 1L;
	// private static int JBO_ADD=0; //增加
	// private static int JBO_DELETE= 1; //删除
	// private static int JBO_UPDATE=2; //修改
	// private static int JBO_REFRESH=3; //刷新
	// private static int JBO_SUBMIT=4; //提交
	// private static int JBO_DEAL=5; //
	// private static int JBO_TURNBACK=6; //返回上一级
	// private static int JBO_EXIT = 7; //退出登陆

	private JButton[] jButtons = new JButton[8];// 存放按钮的数组
	private JPanel nextJpanel;
	private JScrollPane myScrollPane;
	public String selectedSonSort;
	private JTable myTable;
	private CommodityDocumentTree commodityDocumentTree;
	JScrollPane pane = new JScrollPane();
	CommodityDocumentBLService commodityDocumentBLService = new CommodityDocumentBLImp();
	CommodityBLService commodityBLService = new CommodityBLImp();

	// private DefaultTreeModel treeModel;
	/**
	 * Create the application.
	 */
	public CommodityDocumentUI() {
		initialize();
		setLayout(null);
		// 树
		commodityDocumentTree = new CommodityDocumentTree(this);

		pane.getViewport().add(commodityDocumentTree.getCustomerTree());
		pane.setBounds(10, 120, 210, 410);
		this.add(pane);
		// 表格
		myScrollPane = new JScrollPane();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
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
		MyTextField search = new MyTextField(785, 84, 200, 32);
		this.add(search.jtextfield);
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

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private CommodityDocumentUI commodityDocumentUI;

		public ButtonsActionListener(int id, CommodityDocumentUI myPanel) {
			buttonID = id;
			commodityDocumentUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				if (commodityDocumentTree.getSelectNode().getName()
						.equals("未发送报溢单")
						|| commodityDocumentTree.getSelectNode().getName()
								.equals("未发送报损单")
						|| commodityDocumentTree.getSelectNode().getName()
								.equals("未发送报警单")) {
					MyDocumentInputFrame customerInput = new MyDocumentInputFrame();
					ProblemDocumentInputUI myInputUI = new ProblemDocumentInputUI(
							commodityDocumentUI, commodityDocumentTree
									.getSelectNode().getName(), customerInput);
					customerInput.getIni(myInputUI);

				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfcannotAddCommodityDocumentHere
									.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}

				break;

			case 1:
				removeCommodityDocument();

				break;

			case 2:
				int deleteIndex = myTable.getSelectedRow();
				if (deleteIndex > -1) {
					String ID = (String) commodityDocumentTree.gettable()
							.getValueAt(deleteIndex, 2);
					String actualNumberString = (String) commodityDocumentTree
							.gettable().getValueAt(deleteIndex, 5);
					CommodityDocumentVO commodityDocumentVO = commodityDocumentBLService
							.getCommodityDocumentByID(Integer.parseInt(ID));

					commodityDocumentVO.setRealQuantity(Integer
							.parseInt(actualNumberString));

					ResultMessage resultMessage = commodityDocumentBLService
							.updateCommodityDocument(commodityDocumentVO);
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
								StaticImage.backOfFailToUpdate.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
					// 得到了选中的行的编号
					// 通过IDy要去找到那个VO!!!!!!!!!!!!!!!!!!!!!!
					// 然后返回一个新的VOupdate
				}

				break;

			case 3:
				commodityDocumentTree = new CommodityDocumentTree(
						commodityDocumentUI);
				pane.getViewport().add(commodityDocumentTree.getCustomerTree());
				pane.updateUI();

				break;
			case 4:
				int deleteIndex2 = myTable.getSelectedRow();
				if (deleteIndex2 > -1) {
					String ID = (String) commodityDocumentTree.gettable()
							.getValueAt(deleteIndex2, 2);
					int documentID = Integer.parseInt(ID);
					CommodityDocumentVO commodityDocumentVO = commodityDocumentBLService
							.getCommodityDocumentByID(documentID);
					commodityDocumentVO.setSend(true);
					ResultMessage resultMessage = commodityDocumentBLService
							.updateCommodityDocument(commodityDocumentVO);
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
								StaticImage.backOfFailToUpdate.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
					// 得到了选中的行的编号
					// 通过IDy要去找到那个VO!!!!!!!!!!!!!!!!!!!!!!
					// 然后返回一个新的VOupdate
				}

				break;
			case 5:
				// 以后做搜索
				int deleteIndex3 = myTable.getSelectedRow();
				if (deleteIndex3 > -1) {
					String ID = (String) commodityDocumentTree.gettable()
							.getValueAt(deleteIndex3, 2);
					int documentID = Integer.parseInt(ID);
					CommodityDocumentVO commodityDocumentVO = commodityDocumentBLService
							.getCommodityDocumentByID(documentID);
					commodityDocumentVO.setDealed(true);
					ResultMessage resultMessage = commodityDocumentBLService
							.updateCommodityDocument(commodityDocumentVO);
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
								StaticImage.backOfFailToUpdate.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}

				}

				break;

			case 6:
				nextJpanel = new CommodityWholeUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;

			case 7:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			}
		}
	}

	public void addCommodityDocument(CommodityDocumentVO commodityDocumentVO) {
		String sortNameString = null;
		if (commodityDocumentVO.getProblem().equals(PROBLEM.OVERFLOW)) {
			sortNameString = "报溢单";
		} else if (commodityDocumentVO.getProblem().equals(PROBLEM.LOSS)) {
			sortNameString = "报损单";
		} else if (commodityDocumentVO.getProblem().equals(PROBLEM.WARN)) {
			sortNameString = "报警单";
		} else {

		}

		commodityDocumentTree.setSeleceNode(sortNameString);
		commodityDocumentTree.getSelectNode().add(
				new CommodityDocumentNode(commodityDocumentVO));
		commodityDocumentTree.getCustomerTree().updateUI();
		showCommodityDocumentInfo();
	}

	public void showCommodityDocumentInfo() {
		myTable = commodityDocumentTree.creatCommodityDocumentTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void removeCommodityDocument() {// 删除客户
		int deleteIndex = myTable.getSelectedRow();
		if (deleteIndex > -1) {
			String id = ((String) myTable.getValueAt(0, 2));
			commodityDocumentBLService.deleteCommodityDocument(Integer
					.parseInt(id));
			((DefaultTableModel) myTable.getModel()).removeRow(deleteIndex);
			commodityDocumentTree.removeCommodityDocument(deleteIndex);

		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountDocumentUI.getImage(), 0, 0, null);
	}
}
