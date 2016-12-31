package docDetailUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.DocumentProcessUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.ImportDocumentVO;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;

public class ImpDocDetailUI extends SuperInputUI {
	//private static final long serialVersionUID = 1L;
	private ImportList myImportList = null;// 进货单的商品列表
	ImportDocumentVO importDocumentVO;
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;
	DocumentProcessUI callBackPanel;

	private static int BUTTON_UPDATE = 0;
	private static int BUTTON_CANEL = 1;

	public ImpDocDetailUI(JFrame frame, ImportDocumentVO tempDocumentVO,
			DocumentProcessUI panel) {
		super(frame);
		callBackPanel = panel;
		importDocumentVO = tempDocumentVO;
		myImportList = tempDocumentVO.getTheList();
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		getButtonInit();
		creatTable();
	}

	private void getButtonInit() {// 初始化按钮

		MyButton updateButton = new MyButton(StaticImage.backOfjbu_update, 288,
				308, StaticImage.backOfjbu_update.getIconWidth(),
				StaticImage.backOfjbu_update.getIconHeight());
		MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
				308, StaticImage.backOfjbu_cancel.getIconWidth(),
				StaticImage.backOfjbu_cancel.getIconHeight());
		JButton[] myButtons = new JButton[] { updateButton.jbutton , cancelButton.jbutton};
		for (int i = 0; i < myButtons.length; i++) {
			myButtons[i].addActionListener(new ButtonListener(i, this));
			myButtons[i].setVisible(true);
			this.add(myButtons[i]);
		}
	}

	class ButtonListener implements ActionListener {
		int buttonID;
		ImpDocDetailUI currentPanel;

		public ButtonListener(int id, ImpDocDetailUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_UPDATE) {
				updateDoc();
			}
			closeFrame();
		}
	}

	private void creatTable() {// 进货商品表格
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
				if (column == 3) {
					return true;
				}
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

	private void updateDoc() {
		int index = commodityTable.getSelectedRow();
		if (index > -1) {
			int number = Integer.parseInt((String) commodityTable.getValueAt(
					index, 3));
			ImportLineItem tempItem = myImportList.getImportLineList().get(
					index);
			ImportLineItem newItem = new ImportLineItem(
					tempItem.getTheCommodity(), number,
					tempItem.getTheMessage());
			myImportList.getImportLineList().set(index, newItem);
			ImportDocumentVO newImportDocumentVO = new ImportDocumentVO(
					importDocumentVO.getTheDate(),
					importDocumentVO.getDocumentID(),
					importDocumentVO.getTheCustomer(),
					importDocumentVO.getWarehouse(),
					importDocumentVO.getTheUser(), myImportList,
					importDocumentVO.getTheMessage(), myImportList.getTotal(),
					importDocumentVO.isPass(), importDocumentVO.isSend(),
					importDocumentVO.isDealed());

			callBackPanel.UpadteDoc(newImportDocumentVO);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfDocProDetail.getImage(), 0, 0, null);
	}
}
