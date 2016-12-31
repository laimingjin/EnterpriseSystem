package presentation;

import inputUI.ExpDocChooseUI;
import inputUI.SaleInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import comChooseUI.SaleCuChooseUI;
import comChooseUI.SaleOpeChooseUI;
import temp_business.CustomerBLService;
import temp_business.ExportBLService;
import temp_businessImp.CustomerBLImp;
import temp_businessImp.ExportBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CustomerVO;
import vo.SaleDocumentVO;
import vo.UserVO;
import businesslogic.Customer;
import businesslogic.SaleLineItem;
import businesslogic.SaleList;
import businesslogic.User;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class SaleUI extends JPanel {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	/**
	 * 小春 2014年12月9日下午10:25:59 EnterpriseSystem presentation SaleUI.java
	 */
	// 即将跳转的按钮
	JPanel nextPanel;
	// 存放商品的表格
	JTable commodityTable;
	Vector<String> columnName = new Vector<String>();
	JScrollPane myTablePanel = new JScrollPane();
	// 信息输入框
	MyTextField[] myTextFields;
	// 数据表
	SaleList myList = new SaleList();
	// 逻辑层
	CustomerBLService myCustomerBLService = new CustomerBLImp();
	ExportBLService mysaleBl = new ExportBLImp();
	// 单据
	SaleDocumentVO mySaleDocumentVO;
	//客户
	CustomerVO customerVO;
	// 用于判断是销售还是退货
	public boolean isSale = true;
	// 每个按钮对应的数字
	private static int BUTTON_ADD = 0;
	private static int BUTTON_DEL = 1;
	private static int BUTTON_UPD = 2;
	private static int BUTTON_SALE = 3;
	private static int BUTTON_RETURNIMP = 4;
	private static int BUTTON_SUBMIT = 5;
	private static int BUTTON_CUSTCHOOSE = 6;
	private static int BUTTON_OPERCHOOSE = 7;
	private static int BUTTON_TURNBACK = 8;
	private static int BUTTON_EXIT = 9;
	// 初始化按钮
	private static MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82,
			90, 35);
	private static MyButton delete = new MyButton(StaticImage.backOfDelete,
			358, 82, 90, 35);
	private static MyButton update = new MyButton(StaticImage.backOfUpdate,
			468, 82, 90, 35);
	private static MyButton sale = new MyButton(StaticImage.backOfjbu_sale,
			688, 82, 90, 35);
	private static MyButton returnSale = new MyButton(
			StaticImage.backOfjbu_returnImp, 578, 82, 90, 35);
	private static MyButton submit = new MyButton(StaticImage.backOfjbu_submit,
			799, 82, 90, 35);
	private static MyButton cuChoose = new MyButton(
			StaticImage.backOfBigChoose, 194, 204,
			StaticImage.backOfBigChoose.getIconWidth(),
			StaticImage.backOfBigChoose.getIconHeight());
	private static MyButton opChoose = new MyButton(
			StaticImage.backOfBigChoose, 194, 246,
			StaticImage.backOfBigChoose.getIconWidth(),
			StaticImage.backOfBigChoose.getIconHeight());
	private static MyButton turnBack = new MyButton(StaticImage.backOfTurnBack,
			907, 63, 50, 13);
	private static MyButton exit = new MyButton(StaticImage.backOfExit, 965,
			63, 50, 13);

	private static JButton[] buttons;

	// 输入框的数据
	private static int X_TEXTFIELD = 106;
	private static int[] Y_TEXTFIELD;
	private static int WIDTH_FIELD = 129;
	private static int HEIGHT_FIELD = 23;

	static {
		Y_TEXTFIELD = new int[] { 155, 204, 246, 294, 344, 392, 440, 487, 535 };
	}

	public SaleUI() {
		this.setLayout(null);
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		getButtonInit();
		getTextFiledInit();
	}

	private void getButtonInit() {
		buttons = new JButton[] { add.jbutton, delete.jbutton, update.jbutton,
				sale.jbutton, returnSale.jbutton, submit.jbutton,cuChoose.jbutton,opChoose.jbutton,
				turnBack.jbutton, exit.jbutton };
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
			buttons[i].addActionListener(new ButtonListener(i, this));
			this.add(buttons[i]);
		}
	}

	private void getTextFiledInit() {
		myTextFields = new MyTextField[9];

		// System.out.println( mysaleBl.getNewId("XSD"));
		// System.out.println(myTextFields[0]);
		// System.out.println(myTextFields[0].jtextfield);

		for (int i = 0; i < myTextFields.length; i++) {
			myTextFields[i] = new MyTextField(X_TEXTFIELD, Y_TEXTFIELD[i],
					WIDTH_FIELD, HEIGHT_FIELD);
			myTextFields[i].jtextfield.setVisible(true);
			this.add(myTextFields[i].jtextfield);
		}
		myTextFields[0].jtextfield.setText(mysaleBl.getNewId("XSD"));
		myTextFields[1].jtextfield.setEditable(false);
		myTextFields[2].jtextfield.setEditable(false);
	}

	// 初始化表格
	private void getTableInit() {
		if (columnName.isEmpty()) {
			addColumn();
		}
		Vector<Vector<String>> rowInfo = new Vector<Vector<String>>();
		DefaultTableModel newTableModel = new DefaultTableModel(rowInfo,
				columnName) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				// if (column == 3 || column == 4 || column == 6) {// 控制不可编辑
				// return true;
				// }
				return false;
			}
		};
		commodityTable = new JTable(newTableModel);
		myTablePanel.getViewport().add(commodityTable);
		myTablePanel.setBounds(240, 120, 770, 410);
		myTablePanel.setVisible(true);
		this.add(myTablePanel);
	}

	// 表头
	private void addColumn() {
		columnName.add("商品编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品数量");
		columnName.add("商品单价");
		columnName.add("金额");
		columnName.add("商品备注");
	}

	// 鼠标监听
	class ButtonListener implements ActionListener {
		private int buttonID;
		SaleUI currentPanel;

		public ButtonListener(int id, SaleUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_ADD) {
				if (isSale) {
					MyDocumentInputFrame myNewFrame = new MyDocumentInputFrame();
					SaleInputUI myInputUI = new SaleInputUI(currentPanel,
							myNewFrame);
					myNewFrame.getIni(myInputUI);
				} else {// 退货时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					;
				}
			}
			if (buttonID == BUTTON_DEL) {
				if (isSale) {
					int deletiIndex = commodityTable.getSelectedRow();
					myList.removeLineItem(deletiIndex);
					commodityTable.remove(deletiIndex);
					myTextFields[4].jtextfield.setText(Double.toString(myList
							.getTotalPurchasePrice()));
					myTextFields[4].jtextfield.updateUI();
				} else {// 退货时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					;
				}
			}
			if (buttonID == BUTTON_UPD) {
				// TODO 此按钮已废
			}
			if (buttonID == BUTTON_SALE) {
				isSale = true;
				myList = new SaleList();
				getTableInit();
				myTextFields[0].jtextfield.setText(mysaleBl.getNewId("XSD"));
			}
			if (buttonID == BUTTON_RETURNIMP) {
				isSale = false;
				myList = new SaleList();
				MyDocumentInputFrame tempFrame = new MyDocumentInputFrame();
				ExpDocChooseUI myChooseUI = new ExpDocChooseUI(currentPanel,
						tempFrame);
				tempFrame.getIni(myChooseUI);
			}
			if (buttonID == BUTTON_SUBMIT) {
				submitDocu();
			}
			if (buttonID==BUTTON_CUSTCHOOSE) {
				MyDocumentInputFrame myDocumentInputFrame=new MyDocumentInputFrame();
				SaleCuChooseUI saleCuChooseUI=new SaleCuChooseUI(currentPanel, myDocumentInputFrame);
				myDocumentInputFrame.getIni(saleCuChooseUI);
			}
			if (buttonID==BUTTON_OPERCHOOSE) {
				MyDocumentInputFrame myDocumentInputFrame=new MyDocumentInputFrame();
				SaleOpeChooseUI saleOpeChooseUI=new SaleOpeChooseUI(currentPanel, myDocumentInputFrame);
				myDocumentInputFrame.getIni(saleOpeChooseUI);
			}
			if (buttonID == BUTTON_TURNBACK) {
				nextPanel = new ImpSalerMainUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
			if (buttonID == BUTTON_EXIT) {
				nextPanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextPanel);
			}
		}
	}

	// 新增一行商品
	public void addCommodityRow(SaleLineItem myiItem) {
		myList.addLineItem(myiItem);// 保存商品数据
		Vector<String> rowInfo = new Vector<String>();
		// 更新表格
		rowInfo.add(Integer
				.toString(myiItem.getTheCommodity().getCommodityID()));
		rowInfo.add(myiItem.getTheCommodity().getCommodityName());
		rowInfo.add(myiItem.getTheCommodity().getCommodityModel());
		rowInfo.add(Integer.toString(myiItem.getTheNumber()));
		rowInfo.add(Double.toString(myiItem.getTheCommodity()
				.getLatestPurchasePrice()));
		rowInfo.add(Double.toString(myiItem.getTotal()));
		rowInfo.add(myiItem.getTheMessage());
		DefaultTableModel dtm = (DefaultTableModel) commodityTable.getModel();
		dtm.addRow(rowInfo);
		commodityTable.updateUI();

		myTextFields[4].jtextfield.setText(Double.toString(myList
				.getTotalPurchasePrice()));
		myTextFields[4].jtextfield.updateUI();
	}

	private void submitDocu() {
		if (isSale) {
			String date = myTextFields[0].jtextfield.getText().split("-")[1];// 日期
			String id = myTextFields[0].jtextfield.getText();
			Customer customer = new Customer(customerVO);
			String operator = myTextFields[2].jtextfield.getText();
			String wareHouse = myTextFields[3].jtextfield.getText();
			double priceBefor = Double.parseDouble(myTextFields[4].jtextfield
					.getText());
			double zherang = Double.parseDouble(myTextFields[5].jtextfield
					.getText());
			double daijinquan = Double.parseDouble(myTextFields[6].jtextfield
					.getText());
			double priceAfter = priceBefor - zherang - daijinquan;
			String remark = myTextFields[8].jtextfield.getText();

			mySaleDocumentVO = new SaleDocumentVO(date, id, customer, operator,
					wareHouse, new User(MyFrame.getTheUser()), myList, remark,
					priceBefor, zherang, daijinquan, priceAfter,
					StateOfDocument.DRAFT);
			mysaleBl.addExportDraft(mySaleDocumentVO);
		} else {
			mysaleBl.addExportDraft(mySaleDocumentVO);
		}
		MyTipsFrame myTipsFrame = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	}

	public ResultMessage creatBackDocument(SaleDocumentVO mydocument) {
		SaleDocumentVO returnDocumentVO = mysaleBl
				.returnExportBackDraft(mydocument);
		if (returnDocumentVO.equals(null)) {
			return ResultMessage.FAIL;
		} else {
			mySaleDocumentVO = returnDocumentVO;
			myTextFields[0].jtextfield.setText(mydocument.getDocumentID());
			myTextFields[1].jtextfield.setText(mydocument.getTheCustomer()
					.getCustomerName());
			myTextFields[2].jtextfield.setText(mydocument.getExecutive());
			myTextFields[3].jtextfield.setText(mydocument.getWarehouse());
			myTextFields[4].jtextfield.setText(Double.toString(mydocument
					.getTotalPriceBefore()));
			myTextFields[5].jtextfield.setText(Double.toString(mydocument
					.getTheRebate()));
			myTextFields[6].jtextfield.setText(Double.toString(mydocument
					.getTheVoucher()));
			myTextFields[7].jtextfield.setText(Double.toString(mydocument
					.getTotalPriceAfter()));
			myTextFields[8].jtextfield.setText(mydocument.getTheMessage());
			creatImportTabel(mydocument.getTheList());// 画表

			return ResultMessage.SUCCESS;

		}
	}

	private void creatImportTabel(SaleList mySaleList) {// 进货商品表格
		if (columnName.isEmpty()) {
			addColumn();
		}
		Vector<Vector<String>> tabelInfo = new Vector<Vector<String>>();
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
		myTablePanel.getViewport().add(commodityTable);
		myTablePanel.setBounds(220, 120, 790, 410);
		myTablePanel.setVisible(true);
		this.add(myTablePanel);
	}
public void addCustomer(CustomerVO customer){
	customerVO=customer;
	myTextFields[1].jtextfield.setText(customer.getCustomerName());
	myTextFields[2].jtextfield.setText(customer.getOperator());
	this.updateUI();
}
public void addOperator(UserVO user){
	myTextFields[2].jtextfield.setText(user.getUserName());
	this.updateUI();
}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfSale.getImage(), 0, 0, null);
	}

}
