package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.PromotionUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityVO;
import vo.PromotionVO_Package;
import businesslogic.Commodity;

import comChooseUI.PackageComChooseUI;

public class SpecialPriceInputUI extends SuperInputUI {

//	private static final long serialVersionUID = 1L;
	PromotionUI callBackPanel;
	ArrayList<CommodityVO> commodityVOs = new ArrayList<CommodityVO>();
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable promotionTable;
	// 输入框
	MyTextField totalPrice;
	MyTextField timeStart;
	MyTextField timeEnd;

	public SpecialPriceInputUI(JFrame frame, PromotionUI panel) {
		super(frame);
		callBackPanel = panel;
		initialize();
		getTableInit();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);

		totalPrice = new MyTextField(160, 96, 90, 20);
		timeStart = new MyTextField(335, 96, 90, 20);
		timeEnd = new MyTextField(520, 96, 90, 20);

		this.add(totalPrice.jtextfield);
		this.add(timeStart.jtextfield);
		this.add(timeEnd.jtextfield);

		MyButton edit = new MyButton(StaticImage.backOfEdit, 160, 312, 108, 32);
		MyButton save = new MyButton(StaticImage.backOfSave, 319, 312, 108, 32);
		MyButton documentCancel = new MyButton(
				StaticImage.backOfDocumentCancel, 477, 312, 108, 32);

		this.add(edit.jbutton);
		(edit.jbutton).addActionListener(new ButtonListener(this, 0));

		this.add(save.jbutton);
		(save.jbutton).addActionListener(new ButtonListener(this, 1));

		this.add(documentCancel.jbutton);
		(documentCancel.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
	}

	class ButtonListener implements ActionListener {
		private SpecialPriceInputUI currentPanel;
		private int buttonID;

		public ButtonListener(SpecialPriceInputUI panel, int id) {
			currentPanel = panel;
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == 0) {
				MyDocumentInputFrame myInputFrame = new MyDocumentInputFrame();
				PackageComChooseUI proComChooseUI = new PackageComChooseUI(
						currentPanel, myInputFrame);
				myInputFrame.getIni(proComChooseUI);
			} else {
				savePro();
			}
		}

	}

	public void addCommodityInfo(CommodityVO commodity) {
		commodityVOs.add(commodity);
		Vector<String> tempVector = new Vector<String>();
		tempVector.add(Integer.toString(commodity.getCommodityID()));
		tempVector.add(commodity.getCommodityName());
		tempVector.add(commodity.getCommodityModel());
		tempVector.add("0");
		tempVector.add(Double.toString(commodity.getLatestRetailPrice()));
		DefaultTableModel dtm = (DefaultTableModel) promotionTable.getModel();
		dtm.addRow(tempVector);
		promotionTable.updateUI();
	}

	private void addColumnName() {
		columnName.add("商品编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品数量");
		columnName.add("商品单价");
	}

	private void getTableInit() {
		addColumnName();
		tabelInfo = new Vector<Vector<String>>();
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
		promotionTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(promotionTable);
		myTabelPane.setBounds(100, 130, 485, 120);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void savePro() {
		double price = Double.parseDouble(totalPrice.jtextfield.getText());
		String time_start = timeStart.jtextfield.getText();
		String time_End = timeEnd.jtextfield.getText();
		ArrayList<Integer> amount = new ArrayList<Integer>();
		for (int i = 0; i < commodityVOs.size(); i++) {
			amount.add(Integer.parseInt((String) promotionTable
					.getValueAt(i, 3)));
		}
		ArrayList<Commodity> tempCommodities = new ArrayList<Commodity>();
		for (int i = 0; i < commodityVOs.size(); i++) {
			tempCommodities.add(new Commodity(commodityVOs.get(i)));
		}
		PromotionVO_Package package1 = new PromotionVO_Package("特价包",
				time_start, time_End, tempCommodities, amount, price);
		callBackPanel.addNewRow(package1);
		
		MyTipsFrame mtf = new MyTipsFrame();
		MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(mtf,
				StaticImage.backOfSuccess.getImage(), this);
		mtf.getIni(mtPanel);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfPro_PackInput.getImage(), 0, 0, null);
	}

}
