package presentation;

import inputUI.UserInfoInput;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.UserBLService;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.UserVO;
import aboutTree.UserNode;
import aboutTree.UserTree;
import enumClass.ResultMessage;

/**
 * @author 小春 2014年12月8日上午10:24:44 EnterpriseSystem presentation
 *         UserManageUI.java
 */
public class UserManageUI extends JPanel {

//	private static final long serialVersionUID = 1L;
	/**
	 * 用户管理界面
	 */
	private JPanel nextPanel;// 即将跳转的界面
	private UserTree userTree = null;// 树
	private JTable userTable = null;// 表格
	private JScrollPane myTablePane = new JScrollPane();
	JScrollPane myJScrollPane = new JScrollPane();
	// 逻辑层接口
	UserBLService myuserBlService;
	// UserBLService userBLService=new UserBLImp();
	// 每个按钮对应的数字
	private static int BUTTON_ADD = 0;
	private static int BUTTON_DEL = 1;
	private static int BUTTON_UPD = 2;
	private static int BUTTON_FIN = 3;
	private static int BUTTON_SHO = 4;
	private static int BUTTON_TURNBACK = 5;
	private static int BUTTON_EXIT = 6;
	// 搜索框
	MyTextField search;

	ArrayList<Object> theList = new ArrayList<Object>();

	// 初始化按钮
	private static MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82,
			90, 35);
	private static MyButton delete = new MyButton(StaticImage.backOfDelete,
			358, 82, 90, 35);
	private static MyButton update = new MyButton(StaticImage.backOfUpdate,
			468, 82, 90, 35);
	private static MyButton show = new MyButton(StaticImage.backOfShow, 578,
			82, 90, 35);
	private static MyButton find = new MyButton(StaticImage.backOfFind, 688,
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
	public UserManageUI() {
		this.setLayout(null);
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		this.getButtonInit();
		myuserBlService = new UserBLImp();
	}

	private void getButtonInit() {
		// 树树树
		userTree = new UserTree(this);
		myJScrollPane = new JScrollPane();
		myJScrollPane.setBounds(5, 120, 215, 410);
		myJScrollPane.getViewport().add(userTree.getUserTree());
		myJScrollPane.setVisible(true);
		this.add(myJScrollPane);
		// 表格
		ArrayList<UserVO> myList = new ArrayList<UserVO>();
		userTable = userTree.creatUserTable(myList);
		myTablePane.add(userTable);
		myTablePane.setBounds(220, 120, 790, 410);
		myTablePane.setVisible(true);
		this.add(myTablePane);
		// 搜索框
		search = new MyTextField(785, 84, 200, 32);
		this.add(search.jtextfield);
		// 按钮
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
			buttons[i].addActionListener(new ButtonListener(i, this));
			this.add(buttons[i]);

		}
	}

	class ButtonListener implements ActionListener {
		private int buttonID;
		UserManageUI currentPanel;

		public ButtonListener(int id, UserManageUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_ADD) {
				MyInputFrame newFrame = new MyInputFrame();
				UserInfoInput newInput = new UserInfoInput(currentPanel,
						userTree.getSelectNode().getName(), newFrame);
				newFrame.getIni(newInput);
			}
			if (buttonID == BUTTON_DEL) {
				removeUser();
			}
			if (buttonID == BUTTON_UPD) {
				updateUser();
			}
			if (buttonID == BUTTON_FIN) {
				ArrayList<UserVO> tempList = myuserBlService
						.finds(search.jtextfield.getText());
				showUserSearchInfo(tempList);
			}
			if (buttonID == BUTTON_SHO) {
				ArrayList<UserVO> tempList = myuserBlService
					.dispAll();
				showUserSearchInfo(tempList);
			}
			if (buttonID == BUTTON_TURNBACK) {
				nextPanel = new SystemWholeUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
			if (buttonID == BUTTON_EXIT) {
				nextPanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
		}
	}

	/**
	 * 小春 2014年12月8日上午10:22:55 EnterpriseSystem presentation UserManageUI.java
	 * 
	 * @param user
	 *            用于添加用户
	 */
	public ResultMessage addUser(UserVO user) {
		ResultMessage result = myuserBlService.addUser(user);
		if (result == ResultMessage.SUCCESS) {// 添加成功
			String sortName = user.getTheJob();
			userTree.setSeleceNode(sortName);
			userTree.getSelectNode().add(new UserNode(user));
			userTree.getUserTree().updateUI();
			showUserInfo();
		}
		return result;
	}

	/**
	 * 
	 * 小春 2014年12月8日上午10:27:24 EnterpriseSystem presentation UserManageUI.java
	 * 在界面上显示所选的节点的所有子节点的信息
	 */
	public void showUserInfo() {
		userTable = userTree.creatUserTable();
		// myTablePane.removeAll();
		myTablePane.getViewport().add(userTable);
		myTablePane.updateUI();
	}

	/**
	 * 
	 * 小春 2014年12月8日上午10:30:09 EnterpriseSystem presentation UserManageUI.java
	 * 删除节点的子节点上的用户
	 */
	private void removeUser() {
		if (userTree.isAbleToChange()) {
			int deleIndex = userTable.getSelectedRow();
			if (deleIndex > -1) {
				String ID = (String) userTable.getValueAt(deleIndex, 0);
				int id = Integer.parseInt(ID);
				String Name = (String) userTable.getValueAt(deleIndex, 1);
				String Job = (String) userTable.getValueAt(deleIndex, 2);
				int Level = Integer.parseInt((String) userTable.getValueAt(
						deleIndex, 3));
				UserVO tempUser = new UserVO(id, Name, null, Job, Level);
				ResultMessage result = myuserBlService.delete(tempUser);
				if (result == ResultMessage.SUCCESS) {// 成功
					((DefaultTableModel) userTable.getModel())
							.removeRow(deleIndex);
					userTree.removeUser(deleIndex);
				} else {// 失败
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_CustomerDelete.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			}
		}else{
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame,
					StaticImage.backOfFail_Operation.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
	}

	public void showUserSearchInfo(ArrayList<UserVO> theList) {
		userTable = userTree.creatUserTable(theList);
		myTablePane.getViewport().add(userTable);
		myTablePane.setBounds(220, 120, 790, 410);
		myTablePane.setVisible(true);
		this.add(myTablePane);
	}

	private void updateUser() {// 修改
		if (userTree.isAbleToChange()) {
			int deleIndex = userTable.getSelectedRow();
			if (deleIndex > -1) {
				String ID = (String) userTable.getValueAt(deleIndex, 0);
				int id = Integer.parseInt(ID);
				String Name = (String) userTable.getValueAt(deleIndex, 1);
				String Job = (String) userTable.getValueAt(deleIndex, 2);
				int Level = Integer.parseInt((String) userTable.getValueAt(
						deleIndex, 3));
				UserVO oldUser = userTree.getUser(deleIndex);
				UserVO tempUser = new UserVO(id, Name, oldUser.getPassword(),
						Job, Level);
				ResultMessage result = myuserBlService.updUser(oldUser,
						tempUser);
				if (result == ResultMessage.SUCCESS) {// 成功
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSuccess.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					userTree = new UserTree(this);
					myJScrollPane.getViewport().add(userTree.getUserTree());
					myJScrollPane.updateUI();
				} else {// 失败
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFail_CustomerDelete.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			}
		}else{
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame,
					StaticImage.backOfFail_Operation.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfuserMagUI.getImage(), 0, 0, null);
	}
}
