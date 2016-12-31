package comChooseUI;

import inputUI.PromotionCustomerInputUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;

import temp_business.CommoditySortBLService;
import temp_businessImp.CommoditySortBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.CommoditySortVO;
import vo.CommodityVO;
import aboutTree.CommodityNode;
import aboutTree.CommoditySortNode;

public class ProComChooseUI extends JPanel {

//	private static final long serialVersionUID = 1L;
	private JTree commodityTree = null;// 数据树
	private JTable comodityTable;// 数据表格
	private JScrollPane myTreePanel = new JScrollPane();
	private JScrollPane myTablePanel = new JScrollPane();
	public PromotionCustomerInputUI callBackPanel;
	private Frame currentFrame;// 当前输入窗口
	private static int BUTTON_CANEL = 0;
	private static int BUTTON_SAVE = 1;
	// 下面直接有商品的子分类，用于点击
	public ArrayList<CommoditySortNode> sortItem = new ArrayList<CommoditySortNode>();
	public static Vector<String> columnNames = new Vector<String>();// 表头
	// 选中的商品分类名称
	private CommoditySortNode selectNode = null;
	CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();

	public ProComChooseUI(PromotionCustomerInputUI panel, Frame frame) {
		callBackPanel = panel;
		currentFrame = frame;
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		this.setLayout(null);

		myTreePanel.getViewport().add(getCommodityTree());
		myTreePanel.setBounds(20, 43, 190, 297);
		myTreePanel.setVisible(true);
		this.add(myTreePanel);

		getButtonInit();
	}

	// 初始化按钮
	private void getButtonInit() {
		MyButton saveButton = new MyButton(StaticImage.backOfjbu_save, 288,
				308, StaticImage.backOfjbu_save.getIconWidth(),
				StaticImage.backOfjbu_save.getIconHeight());
		MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
				308, StaticImage.backOfjbu_cancel.getIconWidth(),
				StaticImage.backOfjbu_cancel.getIconHeight());
		JButton[] myButtons = new JButton[] { cancelButton.jbutton,
				saveButton.jbutton };
		for (int i = 0; i < myButtons.length; i++) {
			myButtons[i].addActionListener(new ButtonListener(i));
			myButtons[i].setVisible(true);
			this.add(myButtons[i]);
		}
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

	private JTree getCommodityTree() {

		CommoditySortVO rootVO = commoditySortBLService.getRoot();
		CommoditySortNode root = new CommoditySortNode(rootVO);

		if (commodityTree == null) {
			creatCommoditySortNode(rootVO, root);
			commodityTree = new JTree(root);
			commodityTree.addTreeSelectionListener(new tree_selection_listener(
					this));
		}
		return commodityTree;
	}

	class tree_selection_listener implements TreeSelectionListener {
		ProComChooseUI currentPanel;

		public tree_selection_listener(ProComChooseUI panel) {
			currentPanel = panel;
		}

		public void valueChanged(TreeSelectionEvent e) {// 对树的监听

			Object n = e.getPath().getLastPathComponent();
			if (n instanceof CommoditySortNode) {

				selectNode = (CommoditySortNode) n;
				if (selectNode.getCommodity().getCommodityList() != null) {
					currentPanel.showCommodityInfo();
				} else {
				}
			}
		}
	}

	class ButtonListener implements ActionListener {
		int buttonID;

		public ButtonListener(int id) {
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				currentFrame.dispose();
			}
			if (buttonID == BUTTON_SAVE) {
				int index = comodityTable.getSelectedRow();
				if (selectNode.getCommodity().getCommodityList() != null) {
					CommodityVO newCommodityVO = selectNode.getCommodity()
							.getCommodityList().get(index);
					callBackPanel.addCommodityInfo(newCommodityVO);
				}
				currentFrame.dispose();
			}
		}
	}

	private void addColumnsName() {// 表头
		columnNames.add("商品分类");
		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("商品型号");
		columnNames.add("商品数量");
		columnNames.add("商品最近进价");
		columnNames.add("商品最近售价");
	}

	public void creatCommodityTable() {// 创造表格
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
			row_info.add(commodity.getCommoditySortName());// 分类
			row_info.add(Integer.toString(commodity.getCommodityID()));// 编号
			row_info.add(commodity.getCommodityName());// 姓名
			row_info.add(commodity.getCommodityModel());// 型号
			row_info.add(Integer.toString(commodity.getInventoryQuantity()));// 库存数量
			row_info.add(Double.toString(commodity.getLatestPurchasePrice()));// 最近进价
			row_info.add(Double.toString(commodity.getLatestRetailPrice()));// 最近售价

			commodity_info.add(row_info);
		}
		DefaultTableModel newTableModel = new DefaultTableModel(commodity_info,
				columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		comodityTable = new JTable(newTableModel);
		// JTable customer_table = new JTable(new
		// DefaultTableModel(commodity_info,columnNames));
		comodityTable.setGridColor(Color.black);
		comodityTable.setShowGrid(true);
		comodityTable.setBackground(Color.WHITE);
		comodityTable.setRowHeight(25);
		comodityTable.setFont(new Font("微软雅黑", 0, 12));
	}

	private void showCommodityInfo() {
		creatCommodityTable();
		myTablePanel.getViewport().add(comodityTable);
		myTablePanel.setBounds(210, 43, 480, 225);
		myTablePanel.setVisible(true);
		this.add(myTablePanel);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodity_choose.getImage(), 0, 0, null);
	}
}
