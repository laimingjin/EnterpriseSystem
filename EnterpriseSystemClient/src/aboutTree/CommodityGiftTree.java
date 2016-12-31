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

import presentation.CommodityGiftDealUI;
import temp_business.CommodityGiftBLService;
import temp_businessImp.CommodityGiftBLImp;
import vo.CommodityGiftVO;

public class CommodityGiftTree implements TreeSelectionListener {
	public final static String[] sortName = { "未处理赠送单", "已处理赠送单" };// 确定分类节点的名称
	public Node root = new Node("库存赠送单");
	public CommodityGiftSortNode[] sortItem = null;
	private JTree myTree = null;
	private JTable customer_table = new JTable();
	CommodityGiftBLService commodityGiftBLService = new CommodityGiftBLImp();

	public JTable gettable() {
		return customer_table;
	}

	public static Vector<String> columnNames = new Vector<String>();

	private CommodityGiftSortNode selectNode = null;
	private CommodityGiftDealUI callBackPanel = null;

	public CommodityGiftTree(CommodityGiftDealUI myPanel) {
		this.callBackPanel = myPanel;
	}

	private void creatCommodityGiftSortNode() {// 生成固定的分类节点，商品的节点应该是动态的

		sortItem = new CommodityGiftSortNode[2];
		for (int i = 0; i < 2; i++) {
			sortItem[i] = new CommodityGiftSortNode(sortName[i]);
			root.add(sortItem[i]);
		}
		selectNode = sortItem[0];
	}

	public CommodityGiftSortNode getSelectNode() {
		return selectNode;
	}

	public void setSeleceNode(CommodityGiftSortNode seleceNode) {
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

	}

	private void addCommodityDocument() {// 这是初始化树的方法，要和BL连接
	// Commodity c=new Commodity("卡西欧", 0011, "CASIOA", 11, "990e", 0, 100, 100,
	// 0, 0, "2014/12/6", 0,10);
	// Customer theCustomerVO1 = new Customer();
	// CommodityGiftVO commodityGiftVO=new CommodityGiftVO("123", 12, c, 123,
	// theCustomerVO1, true, true, true);
	// CommodityGiftVO commodityGiftVO2=new CommodityGiftVO("12", 13, c, 123,
	// theCustomerVO1, true, true, false);
	// CommodityGiftVO commodityGiftVO1=new CommodityGiftVO("13", 1, c, 123,
	// theCustomerVO1, true, true, false);
	// //到时候需要需要需要通过名称和编号得到Commodity.
	// theList.add(commodityGiftVO);
	// theList.add(commodityGiftVO1);
	// theList.add(commodityGiftVO2);
		// .............................
		ArrayList<CommodityGiftVO> theList = commodityGiftBLService
				.displayAll();

		for (int i = 0; i < theList.size(); i++) {

			if ((theList.get(i).isDealed() == true)
					&& (theList.get(i).isPass() == true)
					&& (theList.get(i).isSend() == true)) {
				// root1.getgetChildAt(0)).addAccountReceiveDocument(new
				// AccountReceiveDocumentNode(thereceiveList.get(i)));
				sortItem[1].addCommodityGiftDocument(new CommodityGiftNode(
						theList.get(i)));
			} else if ((theList.get(i).isPass() == true)
					&& (theList.get(i).isSend() == true)
					&& (theList.get(i).isDealed() == false)) {
				sortItem[0].addCommodityGiftDocument(new CommodityGiftNode(
						theList.get(i)));
			} else {

			}

		}

		myTree.addTreeSelectionListener(this);
	}

	public JTree getCustomerTree() {
		if (myTree == null) {
			creatCommodityGiftSortNode();
			myTree = new JTree(root);
			addCommodityDocument();
		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof CommodityGiftSortNode) {
			selectNode = (CommodityGiftSortNode) n;
			callBackPanel.showCommodityDocumentInfo();
		}
	}

	private void addColumnsName() {// 表头

		columnNames.add("单据类型");
		columnNames.add("时间");
		columnNames.add("单据编号");
		columnNames.add("赠品名称");
		columnNames.add("赠品型号");
		columnNames.add("系统数量");
		columnNames.add("赠送数量");
		columnNames.add("收赠品客户ID");
		columnNames.add("收赠品客户姓名");
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
		CommodityGiftVO commodityGiftVO;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			commodityGiftVO = ((CommodityGiftNode) selectNode.getChildAt(i))
					.getcommodityGiftVO();
			row_info.add("赠送单");// 问题类型
			row_info.add(commodityGiftVO.getDate());// 时间
			row_info.add(Long.toString(commodityGiftVO.getDocumentID()));// 单据编号
			row_info.add(commodityGiftVO.getCommodity().getCommodityName());// 名称
			row_info.add(commodityGiftVO.getCommodity().getCommodityModel());// 型号

			row_info.add(Integer.toString(commodityGiftVO.getCommodity()
					.getInventoryQuantity()));// / 系统数量
			row_info.add(Integer.toString(commodityGiftVO.getSendQuantity()));
			row_info.add(Integer.toString(commodityGiftVO.getCustomer()
					.getCustomerID()));
			row_info.add(commodityGiftVO.getCustomer().getCustomerName());
			// row_info.add(String.valueOf(commodityGiftVO.isSend()));// 发送
			// row_info.add(String.valueOf(commodityGiftVO.isPass()));// 审批
			// row_info.add(String.valueOf(commodityGiftVO.isDealed()));// 处理

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
				for (int i = 0; i < 10; i++) {
					if (column == i) {// 控制不可
						return false;
					}
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

	/*
	 * public void removeCommodityGift(int index) {
	 * selectNode.removeCommodityGiftDocument(index); myTree.updateUI(); }
	 */
}
