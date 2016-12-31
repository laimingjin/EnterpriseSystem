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

import presentation.AccountManagementUI;
import temp_business.AccountBLService;
import temp_businessImp.AccountBLImp;
import vo.AccountVO;

public class AccountTree implements TreeSelectionListener {
	// public final static String[] sortName = { "销售商", "供应商" };// 确定分类节点的名称
	public AccountSortNode root = new AccountSortNode("账户");
	public AccountSortNode[] sortItem = null;
	private JTree myTree = null;
	public static Vector<String> columnNames = new Vector<String>();
	private AccountSortNode selectNode = root;
	AccountBLService accountBLService = new AccountBLImp();

	public JTable getAccount_table() {
		return account_table;
	}

	JTable account_table = new JTable();

	private AccountManagementUI callBackPanel = null;

	public AccountTree(AccountManagementUI myPanel) {
		this.callBackPanel = myPanel;
	}

	public AccountSortNode getSelectNode() {
		return selectNode;
	}

	public void creatAccountNode(ArrayList<AccountVO> theList) {
		for (int i = 0; i < theList.size(); i++) {
			root.addAccount(new AccountNode(theList.get(i)));
		}

	}

	public JTree getAccountTree() {
		if (myTree == null) {
			ArrayList<AccountVO> theList = accountBLService.displayAll();

			creatAccountNode(theList);
			myTree = new JTree(root);
		}
		myTree.addTreeSelectionListener(this);
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof AccountSortNode) {
			selectNode = (AccountSortNode) n;
			callBackPanel.showAccountInfo();
		}
	}

	private void addColumnsName() {// 表头
		columnNames.add("账户名称");
		columnNames.add("账户金额");

	}

	public JTable creatCustomerTable() {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> account_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		AccountVO accountVO;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			accountVO = ((AccountNode) selectNode.getChildAt(i)).getAccount();

			row_info.add(accountVO.getAccountName());// 账户名称

			row_info.add(Double.toString(accountVO.getAccountPrice()));// 账户金额

			account_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(account_info,
				columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {// 控制不可
					return false;
				}
				return true;
			}
		};

		account_table = new JTable(newTableModel);

		account_table.setGridColor(Color.black);
		account_table.setShowGrid(true);
		account_table.setBackground(Color.WHITE);
		account_table.setRowHeight(25);
		account_table.setFont(new Font("微软雅黑", 0, 12));
		return account_table;
	}

	public JTable creatSearchTable(ArrayList<AccountVO> arr) {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> account_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		AccountVO accountVO;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			accountVO = arr.get(i);

			row_info.add(accountVO.getAccountName());// 账户名称

			row_info.add(Double.toString(accountVO.getAccountPrice()));// 账户金额

			account_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(account_info,
				columnNames) {
			/**
			 * 
			 */
		//	private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {// 控制不可
					return false;
				}
				return true;
			}
		};

		account_table = new JTable(newTableModel);
		account_table.setGridColor(Color.black);
		account_table.setShowGrid(true);
		account_table.setBackground(Color.WHITE);
		account_table.setRowHeight(25);
		account_table.setFont(new Font("微软雅黑", 0, 12));
		return account_table;
	}

	public void removeAccount(int index) {
		selectNode.removeAccount(index);
		myTree.updateUI();
	}
}
