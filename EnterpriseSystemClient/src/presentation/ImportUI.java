package presentation;

import inputUI.ImpDocChooseUI;
import inputUI.ImportInputUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import temp_business.CustomerBLService;
import temp_business.ImportDocumentBLService;
import temp_businessImp.CustomerBLImp;
import temp_businessImp.ImportDocumentImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CustomerVO;
import vo.ImportDocumentVO;
import businesslogic.Customer;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.User;

import comChooseUI.ImpCusChooseUI;

import enumClass.ResultMessage;

//TODO 需要修改：供应商改为下来选框【等BL】
public class ImportUI extends JPanel {
	//private static final long serialVersionUID = 1L;
	// 即将跳转的按钮
	JPanel nextPanel;
	// 存放商品的树
	JTable commodityTable;
	Vector<String> columnName = new Vector<String>();
	JScrollPane myTablePanel = new JScrollPane();
	// 信息输入框
	JLabel docuID;
	JTextField docuCustomer;
	JTextField docuStorage;
	JTextField docuOperator;
	JTextField docuRemark;
	JLabel docuPrice;
	// 数据
	ImportList myList = new ImportList();
	public boolean isImport = true;
	ImportDocumentBLService myimportBL = new ImportDocumentImp();
	CustomerBLService myCustomerBLService = new CustomerBLImp();
	ImportDocumentVO myReturnDocumentVO;
	CustomerVO customerVO = null;
	// 每个按钮对应的数字
	private static int BUTTON_ADD = 0;
	private static int BUTTON_DEL = 1;
	private static int BUTTON_UPD = 2;
	private static int BUTTON_IMP = 3;
	private static int BUTTON_RETURNIMP = 4;
	private static int BUTTON_SAVE = 5;
	private static int BUTTON_CUSCHOOSE = 6;
	private static int BUTTON_TURNBACK = 7;
	private static int BUTTON_EXIT = 8;
	// 初始化按钮
	private static MyButton add = new MyButton(StaticImage.backOfAdd, 248, 82,
			90, 35);
	private static MyButton delete = new MyButton(StaticImage.backOfDelete,
			358, 82, 90, 35);
	private static MyButton update = new MyButton(StaticImage.backOfUpdate,
			468, 82, 90, 35);
	private static MyButton Import = new MyButton(StaticImage.backOfjbu_import,
			688, 82, 90, 35);
	private static MyButton returnImp = new MyButton(
			StaticImage.backOfjbu_returnImp, 578, 82, 90, 35);
	private static MyButton submit = new MyButton(StaticImage.backOfSavebtn,
			799, 82, 90, 35);
	private static MyButton cusChoose = new MyButton(
			StaticImage.backOfBigChoose, 176, 296,
			StaticImage.backOfBigChoose.getIconWidth(),
			StaticImage.backOfBigChoose.getIconHeight());
	private static MyButton turnBack = new MyButton(StaticImage.backOfTurnBack,
			907, 63, 50, 13);
	private static MyButton exit = new MyButton(StaticImage.backOfExit, 965,
			63, 50, 13);

	private static JButton[] buttons;

	public ImportUI() {
		this.setLayout(null);
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		getButtonInit();
		getTextFiledInit();
		getTableInit();
	}

	private void getButtonInit() {
		buttons = new JButton[] { add.jbutton, delete.jbutton, update.jbutton,
				Import.jbutton, returnImp.jbutton, submit.jbutton,
				cusChoose.jbutton, turnBack.jbutton, exit.jbutton };
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setVisible(true);
			buttons[i].addActionListener(new ButtonListener(i, this));
			this.add(buttons[i]);
		}
	}

	private void getTextFiledInit() {
		docuID = new JLabel();// 不可修改的单据编号
//		docuID.setText(myimportBL.getNewId("JHD"));
		docuID.setBounds(95, 246, 129, 23);
		docuID.setBackground(Color.white);
		docuID.setVisible(true);
		this.add(docuID);

		docuCustomer = new JTextField();// 供应商q
		docuCustomer.setBounds(95, 296, 129, 23);
		docuCustomer.setBackground(Color.white);
		docuCustomer.setEditable(false);
		docuCustomer.setOpaque(false);
		docuCustomer.setBorder(null);
		docuCustomer.setVisible(true);
		this.add(docuCustomer);

		docuStorage = new JTextField();// 仓库
		docuStorage.setBounds(95, 341, 129, 23);
		docuStorage.setBackground(Color.white);
		docuStorage.setOpaque(false);
		docuStorage.setBorder(null);
		docuStorage.setVisible(true);
		this.add(docuStorage);

		docuOperator = new JTextField();// 操作员
		docuOperator.setBounds(95, 392, 129, 23);
		docuOperator.setBackground(Color.white);
		docuOperator.setText(MyFrame.getTheUser().getUserName());
		docuOperator.setEditable(false);
		docuOperator.setOpaque(false);
		docuOperator.setBorder(null);
		docuOperator.setVisible(true);
		this.add(docuOperator);

		docuRemark = new JTextField();// 备注
		docuRemark.setBounds(95, 440, 129, 23);
		docuRemark.setBackground(Color.white);
		docuRemark.setOpaque(false);
		docuRemark.setBorder(null);
		docuRemark.setVisible(true);
		this.add(docuRemark);

		docuPrice = new JLabel();// 不可修改的总金额
		docuPrice.setBounds(95, 493, 129, 23);
		docuPrice.setBackground(Color.white);
		docuPrice.setVisible(true);
		this.add(docuPrice);

	}

	/**
	 * 
	 * 小春 2014年12月8日下午3:31:24 EnterpriseSystem presentation ImportUI.java 初始化表格
	 */
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
				return false;
			}
		};
		commodityTable = new JTable(newTableModel);
		myTablePanel.getViewport().add(commodityTable);
		myTablePanel.setBounds(220, 120, 790, 410);
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
		ImportUI currentPanel;

		public ButtonListener(int id, ImportUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_ADD) {
				if (isImport) {
					MyDocumentInputFrame myNewFrame = new MyDocumentInputFrame();
					ImportInputUI myNewUI = new ImportInputUI(currentPanel,
							myNewFrame);
					myNewFrame.getIni(myNewUI);
				} else {// 退货时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					;
				}
			}
			if (buttonID == BUTTON_DEL) {// 删除
				if (isImport) {
					int deletiIndex = commodityTable.getSelectedRow();
					myList.removeLineItem(deletiIndex);
					commodityTable.remove(deletiIndex);
					docuPrice.setText(Double.toString(myList.getTotal()));
					docuPrice.updateUI();
				} else {// 退货时不可操作
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					;
				}
			}
			
			if (buttonID == BUTTON_IMP) {
				isImport = true;
				myList = new ImportList();
				getTableInit();
				String ID = myimportBL.getNewId("JHD");
				docuID.setText(ID);

			}
			if (buttonID == BUTTON_RETURNIMP) {
				isImport = false;
				myList = new ImportList();
				MyDocumentInputFrame tempFrame = new MyDocumentInputFrame();
				ImpDocChooseUI myChooseUI = new ImpDocChooseUI(currentPanel,
						tempFrame);
				tempFrame.getIni(myChooseUI);
			}
			if (buttonID == BUTTON_SAVE) {
				submitDocu();
			}
			if (buttonID == BUTTON_CUSCHOOSE) {
				MyDocumentInputFrame myDocumentInputFrame = new MyDocumentInputFrame();
				ImpCusChooseUI impCuChooseUI = new ImpCusChooseUI(currentPanel,
						myDocumentInputFrame);
				myDocumentInputFrame.getIni(impCuChooseUI);
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
	public void addCommodityRow(ImportLineItem myiItem) {
		myList.addImportLineItem(myiItem);// 保存商品数据
		Vector<String> rowInfo = new Vector<String>();
		// 更新表格
		rowInfo.add(Integer
				.toString(myiItem.getTheCommodity().getCommodityID()));
		rowInfo.add(myiItem.getTheCommodity().getCommodityName());
		rowInfo.add(myiItem.getTheCommodity().getCommodityModel());
		rowInfo.add(Integer.toString(myiItem.getNumber()));
		rowInfo.add(Double.toString(myiItem.getTheCommodity()
				.getLatestPurchasePrice()));
		rowInfo.add(Double.toString(myiItem.getTotal()));
		rowInfo.add(myiItem.getTheMessage());
		DefaultTableModel dtm = (DefaultTableModel) commodityTable.getModel();
		dtm.addRow(rowInfo);
		commodityTable.updateUI();

		docuPrice.setText(Double.toString(myList.getTotal()));
		docuPrice.updateUI();
	}

	// 提交审批
	private void submitDocu() {
		if (isImport) {
			String date = docuID.getText().split("-")[1];// 日期
			String id = docuID.getText();// 编号
			Customer customer = new Customer(customerVO);
			User user = new User(MyFrame.getTheUser());
			String Storage = docuStorage.getText();
			String message = docuRemark.getText();
			double price = myList.getTotal();
			ImportDocumentVO importDocumentVO = new ImportDocumentVO(date, id,
					customer, Storage, user, myList, message, price, false,
					false, false);
			myimportBL.addImportDraft(importDocumentVO);
		} else {
			myimportBL.addImportDraft(myReturnDocumentVO);
		}
		MyTipsFrame myTipsFrame = new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
				StaticImage.backOfSuccess.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	}

	public ResultMessage creatBackDocument(ImportDocumentVO importDocument) {// 创建退货单
		ImportDocumentVO returnDocumentVO = myimportBL
				.returnImportBackDraft(importDocument);
		if (returnDocumentVO.equals(null)) {
			return ResultMessage.FAIL;
		} else {
			myReturnDocumentVO = returnDocumentVO;
			docuID.setText(returnDocumentVO.getDocumentID());
			docuCustomer.setText(returnDocumentVO.getTheCustomer()
					.getCustomerName());
			docuStorage.setText(returnDocumentVO.getWarehouse());
			docuOperator.setText(returnDocumentVO.getTheUser().getUserName());
			docuRemark.setText(returnDocumentVO.getTheMessage());
			docuPrice
					.setText(Double.toString(returnDocumentVO.getTotalPrice()));
			creatImportTabel(returnDocumentVO.getTheList()); // 画表
			return ResultMessage.SUCCESS;
		}
	}

	private void creatImportTabel(ImportList myImportList) {// 进货商品表格
		if (columnName.isEmpty()) {
			addColumn();
		}
		Vector<Vector<String>> tabelInfo = new Vector<Vector<String>>();
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
		myTablePanel.getViewport().add(commodityTable);
		myTablePanel.setBounds(220, 120, 790, 410);
		myTablePanel.setVisible(true);
		this.add(myTablePanel);
	}

	public void addCustomer(CustomerVO customer) {
		customerVO = customer;
		docuCustomer.setText(customer.getCustomerName());
		this.updateUI();
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImport.getImage(), 0, 0, null);
	}
}
