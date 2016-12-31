package presentation;

/**
 * 库存管理人员的商品管理界面
 * @author nancy
 * @version 1.0
 */
import inputUI.CommodityInputUI;
import inputUI.CommoditySortUpdateInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.CommodityBLService;
import temp_business.CommodityDocumentBLService;
import temp_business.CommoditySortBLService;
import temp_businessImp.CommodityBLImp;
import temp_businessImp.CommodityDocumentBLImp;
import temp_businessImp.CommoditySortBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityDocumentVO;
import vo.CommoditySortVO;
import vo.CommodityVO;
import aboutTree.CommodityNode;
import aboutTree.CommoditySortNode;
import aboutTree.CommodityTree;
import businesslogic.Commodity;
import enumClass.PROBLEM;

public class CommodityUI extends JPanel {
	/**
	 * 
	 */
	private int realRow;

	public int getRow() {
		return realRow;
	}

//	private static final long serialVersionUID = 1L;
	// private static int JBO_ADD=0; //增加
	// private static int JBO_DELETE= 1; //删除
	// private static int JBO_UPDATE=2; //修改
	// private static int JBO_REFRESH=3; //显示
	// private static int JBO_FIND=4; //查找
	// private static int JBO_TURNBACK=5; //返回上一级
	// private static int JBO_EXIT = 6; //退出登陆、
	private JButton[] jButtons = new JButton[7];// 存放按钮的数组
	private JPanel nextJpanel;
	private JTable myTable;
	private JScrollPane myScrollPane;
	private MyTextField search;
	public CommodityTree commodityTree;
	public ArrayList<CommodityVO> commodityVOs = new ArrayList<CommodityVO>();
	JScrollPane pane = new JScrollPane();
	CommodityDocumentBLService commodityDocumentBLService = new CommodityDocumentBLImp();
	CommodityBLService commodityBLService = new CommodityBLImp();
	CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();

	/**
	 * Create the application.
	 */
	public CommodityUI() {
		initialize();
		setLayout(null);
		// 树
		commodityTree = new CommodityTree(this);

		pane.getViewport().add(commodityTree.getCommodityTree());
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

		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
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

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private CommodityUI commodityUI;

		public ButtonsActionListener(int id, CommodityUI myPanel) {
			buttonID = id;
			// mytree = jTree;
			commodityUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				MyInputFrame commodityInput = new MyInputFrame();
				CommodityInputUI myInputUI = new CommodityInputUI(commodityUI,
						commodityTree.getSelectNode().getCommodity(),
						commodityInput);
				commodityInput.getIni(myInputUI);
				break;

			case 1:
				removeCommodity();
				break;

			case 2:
				if (myTable != null) {

					int deleteIndex = myTable.getSelectedRow();
					if (deleteIndex > -1) {
						// String sortID=(String)
						// commodityTree.gettable().getValueAt(deleteIndex, 0);
						String ID = (String) commodityTree.gettable()
								.getValueAt(deleteIndex, 2);
						String commodityName = (String) commodityTree
								.gettable().getValueAt(deleteIndex, 3);
						String commodityModel = (String) commodityTree
								.gettable().getValueAt(deleteIndex, 4);
						int commodityID = Integer.parseInt(ID);
						commodityBLService.updateCommodity(commodityID,
								commodityName, commodityModel);

					} else {
						// 改分类名字

						MyTipsFrame myTipsFrame = new MyTipsFrame();
						CommoditySortUpdateInputUI myFailTipsPanel = new CommoditySortUpdateInputUI(
								commodityUI, commodityTree.getSelectNode()
										.getCommodity(), myTipsFrame,
								StaticImage.backOfSmallInput.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} else {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					CommoditySortUpdateInputUI myFailTipsPanel = new CommoditySortUpdateInputUI(
							commodityUI, commodityTree.getSelectNode()
									.getCommodity(), myTipsFrame,
							StaticImage.backOfSmallInput.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					// }
				}
				break;

			case 3:
				// 刷新就是重画
				commodityTree = new CommodityTree(commodityUI);
				pane.getViewport().add(commodityTree.getCommodityTree());
				pane.updateUI();
				boolean hasWarn = false;
				ArrayList<CommodityVO> commodityVOs = commodityBLService
						.displayAll();
				for (int i = 0; i < commodityVOs.size(); i++) {

					if ((commodityVOs.get(i).getInventoryQuantity() < commodityVOs
							.get(i).getWarnQuantity())
							&& (commodityVOs.get(i).getLatestPurchasePrice() != 0)) {
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyyMMdd");// 可以方便地修改日期格式
						String dataNow = dateFormat.format(now);

						CommodityDocumentVO commodityDocumentVO = new CommodityDocumentVO(
								dataNow,
								commodityDocumentBLService
										.getNewCommodityDocumentID(),
								new Commodity(commodityVOs.get(i)),
								PROBLEM.WARN, commodityVOs.get(i)
										.getInventoryQuantity(), false, false,
								false);
						commodityDocumentBLService
								.addCommodityDocument(commodityDocumentVO);
						hasWarn = true;

					}
				}
				if (hasWarn) {
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfSmallWarn.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {

				}
				break;
			case 4:
				// 规定模糊查找只能看不能增删改
				// commodityBLService.getCommodityByKey(search.jtextfield.getText());
				// showCommoditySearchInfo(getAllCommodity(search.jtextfield.getText(),commodityTree.getSelectNode().getCommodity()));
				showCommoditySearchInfo(commodityBLService
						.getCommodityByKey(search.jtextfield.getText()));
				break;
			case 5:
				nextJpanel = new CommodityWholeUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			case 6:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			}
		}
	}

	public void addCommodity(CommodityVO commodity) {
		// 调用BL看能不能增加
		String sortName = commodity.getCommoditySortName();
		commodityTree.setSelecetNode(sortName);
		commodityTree.getSelectNode().add(new CommodityNode(commodity));
		commodityTree.getCommodityTree().updateUI();
		showCommodityInfo();
	}

	public void addCommoditySort(CommoditySortVO commoditysort) {
		// 调用BL看能不能增加
		String sortName = commoditysort.getFather();
		commodityTree.setSelecetNode(sortName);
		commodityTree.getSelectNode().add(new CommoditySortNode(commoditysort));
		commodityTree.getCommodityTree().updateUI();

	}

	public void showCommodityInfo() {

		myTable = commodityTree.creatCommodityTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	// 用于显示模糊查找的表格
	public void showCommoditySearchInfo(ArrayList<CommodityVO> arr) {
		myTable = commodityTree.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	private void removeCommodity() {
		// 调用BL的看能不能删除
		CommoditySortNode commoditySortNode = commodityTree.getSelectNode();

		if (commoditySortNode.getChildCount() == 0) {
			commodityTree.removeCommoditySort();

			commoditySortBLService.deleteCommoditySort(commoditySortNode
					.getCommodity().getCommoditySortName());

		} else {
			// MyTipsFrame myTipsFrame=new MyTipsFrame();
			// MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame,
			// StaticImage.backOfFailToDeleteSort.getImage());
			// myTipsFrame.getIni(myFailTipsPanel);
		}

		// 调BL的把他们删除掉
		if (myTable != null) {
			int deleteIndex = myTable.getSelectedRow();
			if (deleteIndex > -1) {
				// 调BL的把他们删除掉
				commodityBLService.deleteCommodity(
						(String) myTable.getValueAt(deleteIndex, 3),
						(String) myTable.getValueAt(deleteIndex, 4));
				((DefaultTableModel) myTable.getModel()).removeRow(deleteIndex);
				commodityTree.removeCommodity(deleteIndex);

			

			}
		}
	}

	// 用于通过点击的数节点和关键字得到满足条件的arraylist
	@SuppressWarnings("unused")
	private ArrayList<CommodityVO> getAllCommodity(String key,
			CommoditySortVO commoditySortVO) {

		if (commoditySortVO.getCommodityList() != null) {
			for (int i = 0; i < commoditySortVO.getCommodityList().size(); i++) {
				if (commoditySortVO.getCommodityList().get(i)
						.getCommodityName().contains(key)) {
					commodityVOs.add(commoditySortVO.getCommodityList().get(i));
				}

			}
		} else if (commoditySortVO.getCommoditySortSonList() != null) {
			for (int i = 0; i < commoditySortVO.getCommoditySortSonList()
					.size(); i++) {
				getAllCommodity(key, commoditySortVO.getCommoditySortSonList()
						.get(i));
			}
		} else {

		}
		return commodityVOs;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodityUI.getImage(), 0, 0, null);
	}

}
