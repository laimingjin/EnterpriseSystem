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

import presentation.CommodityDocumentUI;
import temp_business.CommodityDocumentBLService;
import temp_businessImp.CommodityDocumentBLImp;
import vo.CommodityDocumentVO;
import enumClass.PROBLEM;

public class CommodityDocumentTree implements TreeSelectionListener {
	public final static String[] sortName = { "未发送报溢单", "未发送报损单", "未发送报警单",
			"已发送报溢单", "已发送报损单", "已发送报警单", "已通过报溢单", "已通过报损单", "已通过报警单",
			"已处理报溢单", "已处理报损单", "已处理报警单" };// 确定分类节点的名称
	public Node root = new Node("问题单据");
	public Node root0 = new Node("未发送");
	public Node root1 = new Node("已发送");
	public Node root2 = new Node("已通过");
	public Node root3 = new Node("已处理");
	public CommodityDocumentSortNode[] sortItem = null;
	private JTree myTree = null;
	private JTable customer_table = new JTable();
	CommodityDocumentBLService commodityDocumentBLService = new CommodityDocumentBLImp();

	public JTable gettable() {
		return customer_table;
	}

	public static Vector<String> columnNames = new Vector<String>();

	private CommodityDocumentSortNode selectNode = null;
	private CommodityDocumentUI callBackPanel = null;

	public CommodityDocumentTree(CommodityDocumentUI myPanel) {
		this.callBackPanel = myPanel;
	}

	private void creatCommodityDocumentSortNode() {// 生成固定的分类节点，商品的节点应该是动态的
		root.add(root0);
		root.add(root1);
		root.add(root2);
		root.add(root3);
		sortItem = new CommodityDocumentSortNode[12];
		for (int i = 0; i < 3; i++) {
			sortItem[i] = new CommodityDocumentSortNode(sortName[i]);
			root0.add(sortItem[i]);
		}
		for (int i = 3; i < 6; i++) {
			sortItem[i] = new CommodityDocumentSortNode(sortName[i]);
			root1.add(sortItem[i]);
		}
		for (int i = 6; i < 9; i++) {
			sortItem[i] = new CommodityDocumentSortNode(sortName[i]);
			root2.add(sortItem[i]);
		}
		for (int i = 9; i < 12; i++) {
			sortItem[i] = new CommodityDocumentSortNode(sortName[i]);
			root3.add(sortItem[i]);
		}

		selectNode = sortItem[0];
	}

	public CommodityDocumentSortNode getSelectNode() {
		return selectNode;
	}

	public void setSeleceNode(CommodityDocumentSortNode seleceNode) {
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
		if (sort.equals(sortName[6])) {
			selectNode = sortItem[6];
		}
		if (sort.equals(sortName[7])) {
			selectNode = sortItem[7];
		}
		if (sort.equals(sortName[8])) {
			selectNode = sortItem[8];
		}
		if (sort.equals(sortName[9])) {
			selectNode = sortItem[9];
		}
		if (sort.equals(sortName[10])) {
			selectNode = sortItem[10];
		}
		if (sort.equals(sortName[11])) {
			selectNode = sortItem[11];
		}
	}

	private void addCommodityDocument() {// 这是初始化树的方法，要和BL连接
	// Commodity c=new Commodity("卡西欧", 0011, "CASIOA", 11, "990e", 0, 100, 100,
	// 0, 0, "2014/12/6", 0,10);
	// CommodityDocumentVO c1=new CommodityDocumentVO("2014/12/12", 11, c,
	// PROBLEM.LOSS, 100, false, false, false);
	// CommodityDocumentVO c2=new CommodityDocumentVO("2014/12/12", 11, c,
	// PROBLEM.OVERFLOW, 100, false, true, false);
	// theList.add(c1);
		// theList.add(c2);
		// 到时候需要需要需要通过名称和编号得到Commodity..............
		// .............................
		ArrayList<CommodityDocumentVO> theList = commodityDocumentBLService
				.diaplay();

		for (int i = 0; i < theList.size(); i++) {
			if (theList.get(i).getProblem().equals(PROBLEM.OVERFLOW)) {
				if ((theList.get(i).isDealed() == true)
						&& (theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)) {
					// root1.getgetChildAt(0)).addAccountReceiveDocument(new
					// AccountReceiveDocumentNode(thereceiveList.get(i)));
					sortItem[9].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else if ((theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)
						&& (theList.get(i).isDealed() == false)) {
					sortItem[6].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else if ((theList.get(i).isSend() == true)
						&& (theList.get(i).isPass() == false)
						&& (theList.get(i).isDealed() == false)) {
					sortItem[3].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));

				} else if ((theList.get(i).isSend() == false)
						&& (theList.get(i).isPass() == false)
						&& (theList.get(i).isDealed() == false)) {
					sortItem[0].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else {

				}

			} else if (theList.get(i).getProblem().equals(PROBLEM.LOSS)) {
				if ((theList.get(i).isDealed() == true)
						&& (theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)) {
					sortItem[10]
							.addCommodityDocument(new CommodityDocumentNode(
									theList.get(i)));
				} else if ((theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)) {
					sortItem[7].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else if (theList.get(i).isSend() == true) {
					sortItem[4].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else {
					sortItem[1].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				}

			} else {
				if ((theList.get(i).isDealed() == true)
						&& (theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)) {
					sortItem[11]
							.addCommodityDocument(new CommodityDocumentNode(
									theList.get(i)));

				} else if ((theList.get(i).isPass() == true)
						&& (theList.get(i).isSend() == true)) {
					sortItem[8].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else if (theList.get(i).isSend() == true) {
					sortItem[5].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				} else {
					sortItem[2].addCommodityDocument(new CommodityDocumentNode(
							theList.get(i)));
				}

			}
		}
		myTree.addTreeSelectionListener(this);
	}

	public JTree getCustomerTree() {
		// / ArrayList<CommodityDocumentVO> theList
		// =commodityDocumentBLService.diaplay();

		if (myTree == null) {
			creatCommodityDocumentSortNode();
			myTree = new JTree(root);
			addCommodityDocument();
		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof CommodityDocumentSortNode) {
			selectNode = (CommodityDocumentSortNode) n;
			callBackPanel.showCommodityDocumentInfo();
		}
	}

	private void addColumnsName() {// 表头
		columnNames.add("问题类型");
		columnNames.add("时间");
		columnNames.add("编号");
		columnNames.add("名称");
		columnNames.add("型号");
		columnNames.add("实际数量");
		columnNames.add("系统数量");
		// columnNames.add("已发送");
		// columnNames.add("已通过");
		// columnNames.add("已处理");
	}

	public JTable creatCommodityDocumentTable() {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		Vector<Vector<String>> customer_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommodityDocumentVO commodityDocumentVO;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			commodityDocumentVO = ((CommodityDocumentNode) selectNode
					.getChildAt(i)).getcommodityDocumentVO();
			row_info.add(String.valueOf(commodityDocumentVO.getProblem()));// 问题类型
			row_info.add(commodityDocumentVO.getDate());// 时间
			row_info.add(Integer.toString(commodityDocumentVO.getDocumentID()));// 编号
			row_info.add(commodityDocumentVO.getCommodity().getCommodityName());// 名称
			row_info.add(commodityDocumentVO.getCommodity().getCommodityModel());// 型号
			row_info.add(Integer.toString(commodityDocumentVO.getRealQuantity()));// 实际数量
			row_info.add(Integer.toString(commodityDocumentVO.getCommodity()
					.getInventoryQuantity()));// / 系统数量
			// row_info.add(String.valueOf(commodityDocumentVO.isSend()));// 发送
			// row_info.add(String.valueOf(commodityDocumentVO.isPass()));// 审批
			// row_info.add(String.valueOf(commodityDocumentVO.isDealed()));//
			// 处理

			customer_info.add(row_info);
		}

		DefaultTableModel newTableModel = new DefaultTableModel(customer_info,
				columnNames) {
			/**
			 * 
			 */
		//	private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {

				if (column == 5) {// 控制不可
					return true;
				} else {

					return false;
				}
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

	public void removeCommodityDocument(int index) {
		selectNode.removeCommodityDocument(index);
		myTree.updateUI();
	}
}
