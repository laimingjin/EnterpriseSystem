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

import presentation.CustomerUI;
import temp_business.CustomerBLService;
import temp_businessImp.CustomerBLImp;
import vo.CustomerVO;

/**
 * @author 小春 2014年12月7日下午8:17:11 EnterpriseSystem aboutTree CustomerTree.java
 *         文件名
 */
public class CustomerTree implements TreeSelectionListener {
	public final static String[] sortName = { "销售商", "供应商" };// 确定分类节点的名称
	public Node root = new Node("客户");
	public CustomerSortNode[] sortItem = null;
	private JTree myTree = null;
	public static Vector<String> columnNames = new Vector<String>();

	private CustomerSortNode selectNode = null;
	private CustomerUI callBackPanel = null;

	private JTable customer_table;// 表格
	private CustomerBLService customerBLService = new CustomerBLImp();

	public CustomerTree(CustomerUI myPanel) {
		this.callBackPanel = myPanel;
	}

	private void creatCustomerSortNode() {// 生成固定的分类节点，商品的节点应该是动态的
		sortItem = new CustomerSortNode[2];
		for (int i = 0; i < sortItem.length; i++) {
			sortItem[i] = new CustomerSortNode(sortName[i]);
			root.add(sortItem[i]);
		}
		selectNode = sortItem[0];
	}

	public CustomerSortNode getSelectNode() {
		return selectNode;
	}

	public void setSeleceNode(String sort) {// 记录当前选择的节点
		// selectNode = sortItem[index];
		if (sort.equals(sortName[0])) {
			selectNode = sortItem[0];
		}
		if (sort.equals(sortName[1])) {
			selectNode = sortItem[1];
		}
	}

	private void addCustomer() {// 这是初始化树的方法，要和BL连接

		ArrayList<CustomerVO> theList = new ArrayList<CustomerVO>();
		theList = customerBLService.disPlayAll();
		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).getCustomerType().equals(sortName[0])) {
				sortItem[0].addCustomer(new CustomerNode(theList.get(i)));
			} else {
				sortItem[1].addCustomer(new CustomerNode(theList.get(i)));
			}
		}
	}

	public JTree getCustomerTree() {
		if (myTree == null) {
			creatCustomerSortNode();
			myTree = new JTree(root);
			addCustomer();
			myTree.addTreeSelectionListener(this);
		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof CustomerSortNode) {
			selectNode = (CustomerSortNode) n;
			callBackPanel.showCustomerInfo();
		}
	}

	private void addColumnsName() {// 表头
		columnNames.add("编号");
		columnNames.add("类型");
		columnNames.add("级别");
		columnNames.add("姓名");
		columnNames.add("电话");
		columnNames.add("地址");
		columnNames.add("邮编");
		columnNames.add("电子邮箱");
		columnNames.add("应收额度");
		columnNames.add("应收金额");
		columnNames.add("应付金额");
		columnNames.add("常业务员");
	}

	public JTable creatCustomerTable() {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> customer_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CustomerVO customer;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			customer = ((CustomerNode) selectNode.getChildAt(i)).getCustomer();
			row_info.add(Integer.toString(customer.getCustomerID()));// 编号
			row_info.add(customer.getCustomerType());// 类型
			row_info.add(Integer.toString(customer.getCustomerRank()));// 级别
			row_info.add(customer.getCustomerName());// 姓名
			row_info.add(customer.getTelePhone());// 号码
			row_info.add(customer.getCustomerAddress());// 地址
			row_info.add(Integer.toString(customer.getCustomerPostCode()));// 邮编
			row_info.add(customer.geteMail());// 电子邮箱
			row_info.add(Double.toString(customer.getReceivableLimit()));// 应收额度
			row_info.add(Double.toString(customer.getReceivableAmount()));// 应收
			row_info.add(Double.toString(customer.getPayableAmount()));// 应付
			row_info.add(customer.getOperator());// 业务员

			customer_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(customer_info,
				columnNames) {
			//private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1 || column == 9 || column == 10) {// 控制不可
					return false;
				}
				return true;
			}
		};
		// new DefaultTableModel(customer_info,
		// columnNames)
		customer_table = new JTable(newTableModel);
		customer_table.setGridColor(Color.black);
		customer_table.setShowGrid(true);
		customer_table.setBackground(Color.WHITE);
		customer_table.setRowHeight(25);
		customer_table.setFont(new Font("微软雅黑", 0, 12));
		return customer_table;
	}

	public JTable creatCustomerTable(ArrayList<CustomerVO> theList) {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> customer_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CustomerVO customer;
		int childCount = theList.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			customer = theList.get(i);
			row_info.add(Integer.toString(customer.getCustomerID()));// 编号
			row_info.add(customer.getCustomerType());// 类型
			row_info.add(Integer.toString(customer.getCustomerRank()));// 级别
			row_info.add(customer.getCustomerName());// 姓名
			row_info.add(customer.getTelePhone());// 号码
			row_info.add(customer.getCustomerAddress());// 地址
			row_info.add(Integer.toString(customer.getCustomerPostCode()));// 邮编
			row_info.add(customer.geteMail());// 电子邮箱
			row_info.add(Double.toString(customer.getReceivableLimit()));// 应收额度
			row_info.add(Double.toString(customer.getReceivableAmount()));// 应收
			row_info.add(Double.toString(customer.getPayableAmount()));// 应付
			row_info.add(customer.getOperator());// 业务员

			customer_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(customer_info,
				columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1 || column == 9 || column == 10) {// 控制不可
					return false;
				}
				return true;
			}
		};
		// new DefaultTableModel(customer_info,
		// columnNames)
		customer_table = new JTable(newTableModel);
		customer_table.setGridColor(Color.black);
		customer_table.setShowGrid(true);
		customer_table.setBackground(Color.WHITE);
		customer_table.setRowHeight(25);
		customer_table.setFont(new Font("微软雅黑", 0, 12));
		return customer_table;
	}

	public void removeCustomer(int index) {
		selectNode.removeCustomer(index);
		myTree.updateUI();
	}
}
