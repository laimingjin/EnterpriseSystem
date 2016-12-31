package presentation;

/**
 * 财务人员账户管理的主界面
 * 左侧有一颗树可以选择账户
 * 有增删改显示查看的功能
 * @author nancy
 * @version 1.0
 */
import inputUI.AccountManagementInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import temp_business.AccountBLService;
import temp_businessImp.AccountBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.AccountVO;
import aboutTree.AccountNode;
import aboutTree.AccountTree;
import enumClass.ResultMessage;

public class AccountManagementUI extends JPanel {
	// static final long serialVersionUID = 1L;
	// private static int JBO_ADD=0; //增加
	// private static int JBO_DELETE= 1; //删除
	// private static int JBO_UPDATE=2; //修改
	// private static int JBO_SHOW=3; //显示
	// private static int JBO_FIND=4; //查找
	// private static int JBO_TURNBACK=5; //返回上一级
	// private static int JBO_EXIT = 6; //退出登陆、
	private JButton[] jButtons = new JButton[7];// 存放按钮的数组
	private JPanel nextJpanel;
	AccountBLService accountBLService = new AccountBLImp();
	private JScrollPane myScrollPane;
	public String selectedSonSort;
	private JTable myTable;
	private AccountTree accountTree;
	JScrollPane pane = new JScrollPane();
	MyTextField search;

	// private DefaultTreeModel treeModel;

	/**
	 * Create the application.
	 */
	public AccountManagementUI() {
		initialize();
		setLayout(null);
		// 树
		accountTree = new AccountTree(this);

		pane.getViewport().add(accountTree.getAccountTree());
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

		MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82, 90, 35);
		this.add(add.jbutton);
		MyButton delete = new MyButton(StaticImage.backOfDelete, 358, 82, 90,
				35);
		this.add(delete.jbutton);
		MyButton update = new MyButton(StaticImage.backOfUpdate, 468, 82, 90,
				35);
		this.add(update.jbutton);
		MyButton refresh = new MyButton(StaticImage.backOfRefresh, 578, 82, 90,
				35);
		this.add(refresh.jbutton);
		MyButton find = new MyButton(StaticImage.backOfFind, 688, 82, 90, 35);
		this.add(find.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		search = new MyTextField(785, 84, 200, 32);
		this.add(search.jtextfield);
		jButtons[0] = add.jbutton;
		jButtons[1] = delete.jbutton;
		jButtons[2] = update.jbutton;
		jButtons[3] = refresh.jbutton;
		jButtons[4] = find.jbutton;
		jButtons[5] = turnBack.jbutton;
		jButtons[6] = exit.jbutton;
		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}

	}

	/*
	 * 按钮监听内部类
	 */
	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private AccountManagementUI accountManagementUI;

		public ButtonsActionListener(int id, AccountManagementUI myPanel) {
			buttonID = id;
			// mytree = jTree;
			accountManagementUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				MyInputFrame commodityInput = new MyInputFrame();
				AccountManagementInputUI myInputUI = new AccountManagementInputUI(
						accountManagementUI, commodityInput);
				commodityInput.getIni(myInputUI);
				break;

			case 1:

				removeAccount();
				break;

			case 2:
				DefaultMutableTreeNode oldName = (DefaultMutableTreeNode) accountTree
						.getAccountTree().getLastSelectedPathComponent();
				// int deleteIndex=myTable.getSelectedRow();
				String oldNameString = oldName.toString();

				AccountVO accountVO = accountBLService
						.findAccount(oldNameString);
				// 根据这个可以调用啊、、、、、、、、、
				if (myTable != null) {
					int deleteIndex = myTable.getSelectedRow();
					if (deleteIndex > -1) {
						String newName = (String) myTable.getValueAt(
								deleteIndex, 0);
						// 先set然后调调BL的..........................
						accountVO.setAccountName(newName);
						ResultMessage resultMessage = accountBLService
								.updateAccount(oldNameString, accountVO);
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
				}

				break;

			case 3:
				// 刷新就是重画
				accountTree = new AccountTree(accountManagementUI);

				pane.getViewport().add(accountTree.getAccountTree());
				pane.updateUI();

				break;
			case 4:
				ArrayList<AccountVO> accountVOs = accountBLService
						.findsAccount(search.jtextfield.getText());
				showAccountSearchInfo(accountVOs);
				break;
			case 5:
				nextJpanel = new AccountWholeUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;

			case 6:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			}
		}
	}

	public void addAccount(AccountVO accountVO) {
		accountTree.getSelectNode().add(new AccountNode(accountVO));
		accountTree.getAccountTree().updateUI();
		showAccountInfo();
	}

	public void showAccountInfo() {
		myTable = accountTree.creatCustomerTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	public void showAccountSearchInfo(ArrayList<AccountVO> arr) {
		myTable = accountTree.creatSearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void removeAccount() {// 删除客户
		int deleteIndex = myTable.getSelectedRow();
		if (deleteIndex > -1) {
			accountBLService.deleteAccount((String) myTable.getValueAt(
					deleteIndex, 0));
			((DefaultTableModel) myTable.getModel()).removeRow(deleteIndex);
			accountTree.removeAccount(deleteIndex);

		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodityUI.getImage(), 0, 0, null);
	}
}
