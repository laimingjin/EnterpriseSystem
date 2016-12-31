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

import presentation.CommodityUI;
import temp_business.CommoditySortBLService;
import temp_businessImp.CommoditySortBLImp;
import vo.CommoditySortVO;
import vo.CommodityVO;

public class CommodityTree implements TreeSelectionListener {
	// 下面直接有商品的子分类，用于点击
	public ArrayList<CommoditySortNode> sortItem = new ArrayList<CommoditySortNode>();
	private JTree myTree = null;
	public static Vector<String> columnNames = new Vector<String>();
	// 选中的商品分类名称
	private CommoditySortNode selectNode = null;
	private CommodityUI callBackPanel = null;
	CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
	JTable customer_table = new JTable();

	public JTable gettable() {
		return customer_table;
	}

	/*
	 * 初始化返回界面
	 */
	public CommodityTree(CommodityUI myPanel) {
		this.callBackPanel = myPanel;
	}

	/*
	 * 通过商品分类”商品“最根的根节点吧整棵树画出来（初始化树）
	 */
	private void creatCommoditySortNode(CommoditySortVO c1,
			CommoditySortNode father) {
		// 如果有商品列表,就把他直接画在这个节点上
		if (c1.getCommodityList() != null) {
			sortItem.add(father);
			for (int i = 0; i < c1.getCommodityList().size(); i++) {
				CommodityNode c = new CommodityNode(c1.getCommodityList()
						.get(i));
				father.add(c);
			}
			// 如果有子分类列表,就把他们直接画在这个节点上,并且还是调用这个方法
		} else if (c1.getCommoditySortSonList() != null) {
			for (int i = 0; i < c1.getCommoditySortSonList().size(); i++) {
				CommoditySortNode e1 = new CommoditySortNode(c1
						.getCommoditySortSonList().get(i));
				father.add(e1);
				creatCommoditySortNode(c1.getCommoditySortSonList().get(i), e1);
			}
			// 其他情况不可能.........
		} else {

		}

		selectNode = father;
	}

	public CommoditySortNode getSelectNode() {
		return selectNode;
	}

	public void setSelecetNode(CommoditySortNode seleceNode) {
		this.selectNode = seleceNode;
	}

	/*
	 * 记录当前选择的节点
	 */
	public void setSelecetNode(String sort) {
		// 遍历是选择了哪一个
		for (int i = 0; i < sortItem.size(); i++) {
			if (sort.equals(sortItem.get(i).getCommodity()
					.getCommoditySortName()))
				selectNode = sortItem.get(i);
		}
	}

	private void addtreeaction() {// 这是初始化树的方法，要和BL连接
		myTree.addTreeSelectionListener(this);
	}

	public JTree getCommodityTree() {

		CommoditySortVO rootVO = commoditySortBLService.getRoot();
		CommoditySortNode root = new CommoditySortNode(rootVO);

		if (myTree == null) {
			creatCommoditySortNode(rootVO, root);
			myTree = new JTree(root);
			addtreeaction();

		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听

		Object n = e.getPath().getLastPathComponent();
		if (n instanceof CommoditySortNode) {

			selectNode = (CommoditySortNode) n;
			if (selectNode.getCommodity().getCommodityList() != null) {
				callBackPanel.showCommodityInfo();
			} else {
			}
		}
		myTree.addTreeSelectionListener(this);
	}

	private void addColumnsName() {// 表头
		columnNames.add("分类编号");
		columnNames.add("商品分类");
		columnNames.add("商品编号");
		columnNames.add("商品名称");

		columnNames.add("商品型号");
		columnNames.add("商品数量");
		columnNames.add("商品进价");
		columnNames.add("商品售价");
		columnNames.add("最近进价");
		columnNames.add("最近售价");
		columnNames.add("警戒数量");

	}

	public JTable creatCommodityTable() {// 创造表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommodityVO commodity;
		int childCount = selectNode.getChildCount();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			commodity = ((CommodityNode) selectNode.getChildAt(i))
					.getCommodity();
			row_info.add(Integer.toString(commodity.getCommoditySortID()));// 编号
			row_info.add(commodity.getCommoditySortName());// 类型
			row_info.add(Integer.toString(commodity.getCommodityID()));// 级别
			row_info.add(commodity.getCommodityName());// 姓名
			row_info.add(commodity.getCommodityModel());// 号码
			row_info.add(Integer.toString(commodity.getInventoryQuantity()));// 地址
			row_info.add(Double.toString(commodity.getPurchasePrice()));// 应收额度
			row_info.add(Double.toString(commodity.getRetailPrice()));// 应收
			row_info.add(Double.toString(commodity.getLatestPurchasePrice()));// 应付
			row_info.add(Double.toString(commodity.getLatestRetailPrice()));// 应付
			row_info.add(Integer.toString(commodity.getWarnQuantity()));
			commodity_info.add(row_info);
		}
		DefaultTableModel newTableModel = new DefaultTableModel(commodity_info,
				columnNames) {
			/**
			 * 
			 */
			//private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 1 || column == 2 || column == 5
						|| column == 6 || column == 7 || column == 8
						|| column == 9) {// 控制不可
					return false;
				}
				return true;
			}
		};

		customer_table = new JTable(newTableModel);
		// customer_table.getColumnModel().getColumn(0).setPreferredWidth(30);

		// JTable customer_table = new JTable(new
		// DefaultTableModel(commodity_info,columnNames));
		customer_table.setGridColor(Color.black);
		customer_table.setShowGrid(true);
		customer_table.setBackground(Color.WHITE);
		customer_table.setRowHeight(25);
		customer_table.setFont(new Font("微软雅黑", 0, 12));
		return customer_table;
	}

	public JTable creatCommoditySearchTable(ArrayList<CommodityVO> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommodityVO commodity;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			commodity = arr.get(i);
			row_info.add(Integer.toString(commodity.getCommoditySortID()));// 编号
			row_info.add(commodity.getCommoditySortName());// 类型
			row_info.add(Integer.toString(commodity.getCommodityID()));// 级别
			row_info.add(commodity.getCommodityName());// 姓名
			row_info.add(commodity.getCommodityModel());// 号码
			row_info.add(Integer.toString(commodity.getInventoryQuantity()));// 地址
			row_info.add(Double.toString(commodity.getPurchasePrice()));// 应收额度
			row_info.add(Double.toString(commodity.getRetailPrice()));// 应收
			row_info.add(Double.toString(commodity.getLatestPurchasePrice()));// 应付
			row_info.add(Double.toString(commodity.getLatestRetailPrice()));// 应付
			row_info.add(Integer.toString(commodity.getWarnQuantity()));
			commodity_info.add(row_info);
		}
		JTable commodity_table = new JTable(new DefaultTableModel(
				commodity_info, columnNames));
		commodity_table.setGridColor(Color.black);
		commodity_table.setShowGrid(true);
		commodity_table.setBackground(Color.WHITE);
		commodity_table.setRowHeight(25);
		commodity_table.setFont(new Font("微软雅黑", 0, 12));
		return commodity_table;
	}

	public void removeCommodity(int index) {
		selectNode.removeCommodity(index);
		myTree.updateUI();
	}

	public void removeCommoditySort() {
		selectNode.removeFromParent();
		myTree.updateUI();
	}

}
