package docDetailUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.SaleLineItem;
import businesslogic.SaleList;

public class DocumentDetailUI extends SuperInputUI {
	private SaleList mySaleList = null;// 销售单的商品列表
//	private static final long serialVersionUID = 1L;
	private ImportList myImportList = null;// 进货单的商品列表
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;

	public DocumentDetailUI(JFrame frame, ImportList myList) {
		super(frame);
		myImportList = myList;
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	public DocumentDetailUI(JFrame frame, SaleList myList) {
		super(frame);
		mySaleList = myList;
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatSaleTable();
		getButtonInit();
	}

	private void getButtonInit() {// 按钮初始化
		MyButton jbu_verify = new MyButton(StaticImage.backOfJbu_Verify, 288,
				307, StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_verify.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {
						closeFrame();
					}
				});
		jbu_verify.jbutton.setVisible(true);
		this.add(jbu_verify.jbutton);
	}

	private void creatImportTabel() {// 进货商品表格
		if (columnName.isEmpty()) {
			addColumnName();
		}
		tabelInfo = new Vector<Vector<String>>();
		ArrayList<ImportLineItem> tempItemList = myImportList
				.getImportLineList();
		for (int i = 0; i < tempItemList.size(); i++) {
			Vector<String> row_info = new Vector<String>();
			ImportLineItem tempItem = tempItemList.get(i);
			row_info.add(Integer.toString(tempItem.getTheCommodity()
					.getCommodityID()));// 编号
			row_info.add(tempItem.getTheCommodity().getCommodityName());// 名称
			row_info.add(tempItem.getTheCommodity().getCommodityModel());// 型号
			row_info.add(Integer.toString(tempItem.getNumber()));// 数量
			row_info.add(Double.toString(tempItem.getPrice()));// 单价
			row_info.add(Double.toString(tempItem.getTotal()));// 总价
			row_info.add(tempItem.getTheMessage());// 备注

			tabelInfo.add(row_info);
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		commodityTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(commodityTable);
		myTabelPane.setBounds(18, 45, 665, 230);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void creatSaleTable() {// 销售商品表格
		if (columnName.isEmpty()) {
			addColumnName();
		}
		tabelInfo = new Vector<Vector<String>>();
		ArrayList<SaleLineItem> tempItemList = mySaleList.getTheList();
		for (int i = 0; i < tempItemList.size(); i++) {
			Vector<String> row_info = new Vector<String>();
			SaleLineItem tempItem = tempItemList.get(i);
			row_info.add(Integer.toString(tempItem.getTheCommodity()
					.getCommodityID()));// 编号
			row_info.add(tempItem.getTheCommodity().getCommodityName());// 名称
			row_info.add(tempItem.getTheCommodity().getCommodityModel());// 型号
			row_info.add(Integer.toString(tempItem.getTheNumber()));// 数量
			row_info.add(Double.toString(tempItem.getPrice()));// 单价
			row_info.add(Double.toString(tempItem.getTotal()));// 总价
			row_info.add(tempItem.getTheMessage());// 备注

			tabelInfo.add(row_info);
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		commodityTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(commodityTable);
		myTabelPane.setBounds(18, 45, 665, 230);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void addColumnName() {
		columnName.add("商品编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品数量");
		columnName.add("商品单价");
		columnName.add("商品总价");
		columnName.add("商品备注");
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfCommodityDetil.getImage(), 0, 0, null);
	}
}
