package presentation;

import inputUI.CustomerInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.CustomerBLService;
import temp_businessImp.CustomerBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CustomerVO;
import aboutTree.CustomerNode;
import aboutTree.CustomerTree;
import enumClass.POSITION;
import enumClass.ResultMessage;

/**
 * 那个啥，看到报错别慌， 我把VO改了一部分，你觉得不用改的话跟我说 我把它改回来
 * 
 * 2014年12月10日下午3:03:02
 */
public class CustomerUI extends JPanel {

	//private static final long serialVersionUID = 1L;
	private JPanel nextPanel;// 即将跳转的界面
	// 每个按钮对应的数字
	private static int BUTTON_ADD = 0;
	private static int BUTTON_DEL = 1;
	private static int BUTTON_UPD = 2;
	private static int BUTTON_FIN = 3;
	private static int BUTTON_SHO = 4;
	private static int BUTTON_TURNBACK = 5;
	private static int BUTTON_EXIT = 6;
	// 是否为显示状态
	boolean isShow = false;
	private JScrollPane myScrollPane;
	public String selectedSonSort;
	private JTable myTable;
	private CustomerTree customerTree;
	private CustomerBLService customerBLService;
	private MyTextField search;
	/**
	 * 初始化测试用的~
	 */

	// 初始化按钮
	private static MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82,
			90, 35);
	private static MyButton delete = new MyButton(StaticImage.backOfDelete,
			358, 82, 90, 35);
	private static MyButton update = new MyButton(StaticImage.backOfUpdate,
			468, 82, 90, 35);
	private static MyButton find = new MyButton(StaticImage.backOfFind, 688,
			82, 90, 35);
	private static MyButton show = new MyButton(StaticImage.backOfShow, 578,
			82, 90, 35);
	private static MyButton turnBack = new MyButton(StaticImage.backOfTurnBack,
			907, 63, 50, 13);
	private static MyButton exit = new MyButton(StaticImage.backOfExit, 965,
			63, 50, 13);

	private static JButton[] buttons;
	static {
		buttons = new JButton[] { add.jbutton, delete.jbutton, update.jbutton,
				find.jbutton, show.jbutton, turnBack.jbutton, exit.jbutton };
	}

	// 构造器
	public CustomerUI() {
		// mycustomerBl=new CustomerBL();
		this.setLayout(null);
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		this.getButtonInit();
		customerBLService = new CustomerBLImp();
		// addTree();
		// 给你加一个搜索框啦by nancy
		search = new MyTextField(785, 84, 200, 32);
		this.add(search.jtextfield);

		// 树
		customerTree = new CustomerTree(this);
		JScrollPane pane = new JScrollPane();
		pane.getViewport().add(customerTree.getCustomerTree());
		pane.setBounds(10, 120, 210, 410);
		this.add(pane);
		// 表格
		myScrollPane = new JScrollPane();
		myTable = customerTree.creatCustomerTable(new ArrayList<CustomerVO>());
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void getButtonInit() {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
			buttons[i].addActionListener(new ButtonListener(i, this));
			this.add(buttons[i]);
		}
	}

	// 鼠标监听事件
	class ButtonListener implements ActionListener {
		private int buttonID;
		private CustomerUI customerUI;

		public ButtonListener(int id, CustomerUI myPanel) {
			buttonID = id;
			customerUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_ADD) {
				if (isShow) {// 显示时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {
					int newID = customerBLService.getNewId();
					MyDocumentInputFrame customerInput = new MyDocumentInputFrame();
					CustomerInputUI myInputUI = new CustomerInputUI(customerUI,
							customerTree.getSelectNode().getName(), newID,
							customerInput);
					customerInput.getIni(myInputUI);
				}
			}
			if (buttonID == BUTTON_DEL) {
				if (isShow) {// 显示时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {
					removeCustomer();
				}
			}
			if (buttonID == BUTTON_UPD) {
				if (isShow) {// 显示时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {
					updateCustomer();
				}
			}
			if (buttonID == BUTTON_FIN) {
				isShow = true;
				String info = search.jtextfield.getText();
				ArrayList<CustomerVO> tempList = customerBLService
						.findsCustomer(info);
				customerTree.creatCustomerTable(tempList);
				myScrollPane.getViewport().add(
						customerTree.creatCustomerTable(tempList));
				myScrollPane.updateUI();
			}
			if (buttonID == BUTTON_SHO) {
				isShow = true;
				creatTable();
			}
			if (buttonID == BUTTON_TURNBACK) {
				nextPanel = new ImpSalerMainUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
			if (buttonID == BUTTON_EXIT) {
				nextPanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
		}

	}

	public ResultMessage addCustomer(CustomerVO customer) {// 添加客户
		ResultMessage addResult = customerBLService.addCustomer(customer);
		if (addResult == ResultMessage.SUCCESS) {// 成功就重画树和表格
			String sortName = customer.getCustomerType();// 得到分类名称
			customerTree.setSeleceNode(sortName);
			customerTree.getSelectNode().add(new CustomerNode(customer));
			customerTree.getCustomerTree().updateUI();
			showCustomerInfo();
		}
		return addResult;
	}

	public void showCustomerInfo() {
		isShow = false;
		myTable = customerTree.creatCustomerTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void removeCustomer() {// 删除客户
		int deleteIndex = myTable.getSelectedRow();
		if (deleteIndex > -1) {
			int customerID = Integer.parseInt((String) myTable.getValueAt(
					deleteIndex, 0));
			String customerType = (String) myTable.getValueAt(deleteIndex, 1);
			int customerRank = Integer.parseInt((String) myTable.getValueAt(
					deleteIndex, 2));
			String customerName = (String) myTable.getValueAt(deleteIndex, 3);
			String telePhone = (String) myTable.getValueAt(deleteIndex, 4);
			String customerAddress = (String) myTable
					.getValueAt(deleteIndex, 5);
			int customerPostCode = Integer.parseInt((String) myTable
					.getValueAt(deleteIndex, 6));
			String eMail = (String) myTable.getValueAt(deleteIndex, 7);
			double receivableLimit = Double.parseDouble((String) myTable
					.getValueAt(deleteIndex, 8));
			double receivableAmount = Double.parseDouble((String) myTable
					.getValueAt(deleteIndex, 9));
			double payableAmount = Double.parseDouble((String) myTable
					.getValueAt(deleteIndex, 10));
			String operator = (String) myTable.getValueAt(deleteIndex, 11);
			CustomerVO newCustomer = new CustomerVO(customerID, customerType,
					customerRank, customerName, telePhone, customerAddress,
					customerPostCode, eMail, receivableLimit, receivableAmount,
					payableAmount, operator);
			ResultMessage deleteResult = customerBLService
					.deleteCustomer(newCustomer);
			if (deleteResult == ResultMessage.SUCCESS) {
				((DefaultTableModel) myTable.getModel()).removeRow(deleteIndex);
				customerTree.removeCustomer(deleteIndex);
			} else {// 提示失败
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame,
						StaticImage.backOfFail_CustomerDelete.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		} else {// 未选择的情况
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
					StaticImage.backOfCustomer_Select.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
	}

	private void updateCustomer() {// 修改客户
		int updateIndex = myTable.getSelectedRow();
		int updateColumn = myTable.getSelectedColumn();
		if ((updateColumn == 8)
				&& (MyFrame.getTheUser().getPostion() != POSITION.SUPER_SALE_MANAGER)) {// 无权限修改
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
					StaticImage.backOfFail_Level.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		} else {
			if (updateIndex > -1) {

				int customerID = Integer.parseInt((String) myTable.getValueAt(
						updateIndex, 0));
				String customerType = (String) myTable.getValueAt(updateIndex,
						1);
				int customerRank = Integer.parseInt((String) myTable
						.getValueAt(updateIndex, 2));
				String customerName = (String) myTable.getValueAt(updateIndex,
						3);
				// String customerName="dddddd";
				String telePhone = (String) myTable.getValueAt(updateIndex, 4);
				String customerAddress = (String) myTable.getValueAt(
						updateIndex, 5);
				int customerPostCode = Integer.parseInt((String) myTable
						.getValueAt(updateIndex, 6));
				String eMail = (String) myTable.getValueAt(updateIndex, 7);
				double receivableLimit = Double.parseDouble((String) myTable
						.getValueAt(updateIndex, 8));
				double receivableAmount = Double.parseDouble((String) myTable
						.getValueAt(updateIndex, 9));
				double payableAmount = Double.parseDouble((String) myTable
						.getValueAt(updateIndex, 10));
				String operator = (String) myTable.getValueAt(updateIndex, 11);
				CustomerVO newCustomer = new CustomerVO(customerID,
						customerType, customerRank, customerName, telePhone,
						customerAddress, customerPostCode, eMail,
						receivableLimit, receivableAmount, payableAmount,
						operator);
				ResultMessage result = customerBLService
						.updateCustomer(newCustomer);
				if (result == ResultMessage.SUCCESS) {// 修改成功
					customerTree.getSelectNode().removeCustomer(updateIndex);// 删除原有节点
					customerTree.getSelectNode().addCustomer(
							new CustomerNode(newCustomer));// 新增修改后节点
					customerTree.getCustomerTree().updateUI();
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSuccess.getImage());
					myTipsFrame.getIni(myFailTipsPanel);

				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_Operation.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			} else {// 未选择的情况
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame,
						StaticImage.backOfCustomer_Select.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
	}

	private void creatTable() {// 显示所有客户信息
		ArrayList<CustomerVO> tempList = customerBLService.disPlayAll();// 取得所有客户信息
		customerTree.creatCustomerTable(tempList);
		myScrollPane.getViewport().add(
				customerTree.creatCustomerTable(tempList));
		myScrollPane.updateUI();

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfCustomer.getImage(), 0, 0, null);
	}
}