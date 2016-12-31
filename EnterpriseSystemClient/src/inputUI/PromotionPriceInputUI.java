package inputUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import presentation.PromotionUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityVO;
import vo.PromotionVO_Price;
import businesslogic.Commodity;

import comChooseUI.PriceComChooseUI;

public class PromotionPriceInputUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	// 返回界面
	PromotionUI callBackPanel;
	// 输入框
	MyTextField totalPrice;
	MyTextField timeStart;
	MyTextField timeEnd;
	MyTextField number;
	MyTextField commodityName;
	MyTextField commodityNumber;

	JComboBox<String> amount;
	// 商品
	Commodity commodity = null;

	public PromotionPriceInputUI(JFrame frame, PromotionUI panel) {
		super(frame);
		callBackPanel = panel;
		initialize();
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
		number = new MyTextField(445, 140, 90, 20);
		commodityName = new MyTextField(150, 190, 150, 20);
		commodityName.jtextfield.setEditable(false);
		commodityNumber = new MyTextField(420, 190, 100, 20);

		this.add(totalPrice.jtextfield);
		this.add(timeStart.jtextfield);
		this.add(timeEnd.jtextfield);
		this.add(number.jtextfield);
		this.add(commodityName.jtextfield);
		this.add(commodityNumber.jtextfield);

		amount = new JComboBox<String>();
		amount.addItem("5元");
		amount.addItem("10元");
		amount.addItem("20元");
		amount.addItem("50元");
		amount.addItem("100元");

		amount.setSelectedItem("5元");// 默认是5
		amount.setFont(new Font("微软雅黑", 0, 16));
		amount.setBounds(180, 140, 90, 20);
		this.add(amount);

		MyButton edit = new MyButton(StaticImage.backOfEdit, 159, 312, 100, 32);
		MyButton save = new MyButton(StaticImage.backOfSave, 316, 312, 100, 32);
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
		private PromotionPriceInputUI currentPanel;
		private int buttonID;

		public ButtonListener(PromotionPriceInputUI panel, int id) {
			currentPanel = panel;
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == 0) {
				MyDocumentInputFrame myInputFrame = new MyDocumentInputFrame();
				PriceComChooseUI proComChooseUI = new PriceComChooseUI(
						currentPanel, myInputFrame);
				myInputFrame.getIni(proComChooseUI);
			} else {
				savePro();
			}
		}

	}

	public void addCommodityInfo(CommodityVO commodityVO) {
		commodity = new Commodity(commodityVO);
		commodityName.jtextfield.setText(commodity.getCommodityName()
				+ commodity.getCommodityModel());
	}

	private void savePro() {
		double price = Double.parseDouble(totalPrice.jtextfield.getText());
		String dateStsrt = timeStart.jtextfield.getText();
		String dateEnd = timeEnd.jtextfield.getText();
		int daijinquan = 5;
		int amountDai = 0;

		String tempDai = (String) amount.getSelectedItem();
		if (tempDai.endsWith("5元")) {
			daijinquan = 5;
		} else if (tempDai.endsWith("10元")) {
			daijinquan = 10;
		} else if (tempDai.endsWith("20元")) {
			daijinquan = 20;
		} else if (tempDai.endsWith("50元")) {
			daijinquan = 50;
		} else if (tempDai.endsWith("100元")) {
			daijinquan = 100;
		}
		amountDai = Integer.parseInt(number.jtextfield.getText());

		int commodity_Num = Integer.parseInt(commodityNumber.jtextfield
				.getText());
		PromotionVO_Price promotionVO_Price = new PromotionVO_Price("总价促销",
				dateStsrt, dateEnd, price, commodity, commodity_Num,
				daijinquan, amountDai);
		callBackPanel.addNewRow(promotionVO_Price);
		
		MyTipsFrame mtf = new MyTipsFrame();
		MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(mtf,
				StaticImage.backOfSuccess.getImage(),this);
		mtf.getIni(mtPanel);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfPromotionPriceInput.getImage(), 0, 0,
				null);
	}

}
