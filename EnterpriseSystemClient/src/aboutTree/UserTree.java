package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;

import presentation.UserManageUI;
import temp_business.UserBLService;
import temp_businessImp.UserBLImp;
import vo.UserVO;

/**
 * @author 小春 2014年12月7日下午11:10:00 EnterpriseSystem aboutTree UserTree.java
 */
public class UserTree implements TreeSelectionListener {
	// 逻辑层接口
	UserBLService myuserBlService;
	public final static String[] sortName = { "库存管理人员", "进货销售人员", "财务人员",
			"总经理", "系统管理人员" ,"销售经理"};// 确定分类节点的名称
	public Node root = new Node("系统用户");
	public UserSortNode[] sortItem = null;
	private JTree myTree = null;
	public static Vector<String> columnNames = new Vector<String>();

	private UserSortNode selectNode = null;
	private UserManageUI callBackPanel = null;
	private boolean isAbleToChange = false;
	ArrayList<UserVO> theList;

	public UserTree(UserManageUI myPanel) {
		this.callBackPanel = myPanel;
		myuserBlService = new UserBLImp();
	}

	private void creatUserSortNode() {// 生成固定的分类节点，商品的节点应该是动态的
		sortItem = new UserSortNode[6];
		for (int i = 0; i < sortItem.length; i++) {
			sortItem[i] = new UserSortNode(sortName[i]);
			root.add(sortItem[i]);
		}
		selectNode = sortItem[0];
	}

	public UserSortNode getSelectNode() {
		return selectNode;
	}

	public void setSeleceNode(UserSortNode seleceNode) {
		this.selectNode = seleceNode;
	}

	public void setSeleceNode(String sort) {// 记录当前选择的节点
		// selectNode = sortItem[index];
		if (sort.equals(sortName[0])) {
			selectNode = sortItem[0];
		}
		if (sort.equals(sortName[1])) {
			selectNode = sortItem[1];
		}
		if (sort.equals(sortName[2])) {
			selectNode = sortItem[2];
		}
		if (sort.equals(sortName[3])) {
			selectNode = sortItem[3];
		}
		if (sort.equals(sortName[4])) {
			selectNode = sortItem[4];
		}
		if (sort.equals(sortName[5])) {
			selectNode = sortItem[5];
		}
	}

	private void addUser() {// 这是初始化树的方法，要和BL连接

		theList = myuserBlService.dispAll();// 取得所有用户
		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).getTheJob().equals(sortName[0])) {
				sortItem[0].addUser(new UserNode(theList.get(i)));
			}
			if (theList.get(i).getTheJob().equals(sortName[1])) {
				sortItem[1].addUser(new UserNode(theList.get(i)));
			}
			if (theList.get(i).getTheJob().equals(sortName[2])) {
				sortItem[2].addUser(new UserNode(theList.get(i)));
			}
			if (theList.get(i).getTheJob().equals(sortName[3])) {
				sortItem[3].addUser(new UserNode(theList.get(i)));
			}
			if (theList.get(i).getTheJob().equals(sortName[4])) {
				sortItem[4].addUser(new UserNode(theList.get(i)));
			}
			if (theList.get(i).getTheJob().equals(sortName[5])) {
				sortItem[5].addUser(new UserNode(theList.get(i)));
			}
		}
		myTree.addTreeSelectionListener(this);
	}

	public JTree getUserTree() {
		if (myTree == null) {
			creatUserSortNode();
			myTree = new JTree(root);
			addUser();
		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof UserSortNode) {
			selectNode = (UserSortNode) n;
			callBackPanel.showUserInfo();
		}
	}

	private void addColumnsName() {// 表头
		columnNames.add("编号");
		columnNames.add("姓名");
		columnNames.add("职位");
		columnNames.add("级别");
	}

	public JTable creatUserTable() {// 创造表格
		isAbleToChange = true;
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> user_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		UserVO user;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			user = ((UserNode) selectNode.getChildAt(i)).getuserVO();
			row_info.add(Integer.toString(user.getUserID()));// 编号
			row_info.add(user.getUserName());// 姓名
			row_info.add(user.getTheJob());// 职位
			row_info.add(Integer.toString(user.getPowerLevel()));// 级别

			user_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(user_info,
				columnNames) {
			//private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1) {// 控制不可编辑
					return false;
				}
				return true;
			}
		};
		// new DefaultTableModel(customer_info,
		// columnNames)
		JTable user_table = new JTable(newTableModel);
		user_table.setGridColor(Color.black);
		user_table.setShowGrid(true);
		user_table.setBackground(Color.WHITE);
		user_table.setRowHeight(25);
		user_table.setFont(new Font("微软雅黑", 0, 12));
		return user_table;
	}

	public JTable creatUserTable(ArrayList<UserVO> theList) {// 创造表格
		isAbleToChange = false;// 修改
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> user_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		UserVO user;
		int childCount = theList.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			user = theList.get(i);
			row_info.add(Integer.toString(user.getUserID()));// 编号
			row_info.add(user.getUserName());// 姓名
			row_info.add(user.getTheJob());// 职位
			row_info.add(Integer.toString(user.getPowerLevel()));// 级别

			user_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(user_info,
				columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1) {// 控制不可编辑
					return false;
				}
				return true;
			}
		};
		// new DefaultTableModel(customer_info,
		// columnNames)
		JTable user_table = new JTable(newTableModel);
		user_table.setGridColor(Color.black);
		user_table.setShowGrid(true);
		user_table.setBackground(Color.WHITE);
		user_table.setRowHeight(25);
		user_table.setFont(new Font("微软雅黑", 0, 12));
		return user_table;
	}

	public void removeUser(int index) {
		theList.remove(index);
		selectNode.removeUser(index);
		myTree.updateUI();
	}

	public UserVO getUser(int index) {
		return theList.get(index);
	}

	public boolean isAbleToChange() {
		return isAbleToChange;
	}

}
