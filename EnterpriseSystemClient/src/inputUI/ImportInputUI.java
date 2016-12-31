package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import presentation.ImportUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityVO;
import businesslogic.Commodity;
import businesslogic.ImportLineItem;

import comChooseUI.ImpComChooseUI;

public class ImportInputUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	/**
	 * 进货管理界面用来添加商品的
	 */
	// 每个按钮对应的编号
	private static int BUTTON_CANEL = 0;
	private static int BUTTON_SAVE = 1;
	private static int BUTTON_CHOOSE = 2;

	JLabel commodityID;
	MyTextField commodityNameField;
	MyTextField commodityType;
	MyTextField commodityNumber;
	MyTextField commodityPrePrice;
	MyTextField commodityTotalPrice;
	MyTextField commodityRemark;

	private CommodityVO mycommodity;
	private ImportUI callBackPanel;
	public ImportInputUI(ImportUI panel,JFrame frame) {
		super(frame);
		callBackPanel=panel;
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		getButtonInit();
		getTextFiledInit();
	}

	private void getButtonInit() {// 初始化按钮
		MyButton jbu_cannel = new MyButton(StaticImage.backOfjbu_cancel, 359,
				288, 110, 34);
		MyButton jbu_save = new MyButton(StaticImage.backOfjbu_save, 203, 288,
				110, 34);
		MyButton jbu_choose = new MyButton(StaticImage.backOfjbu_choose, 579,
				87, StaticImage.backOfjbu_choose.getIconWidth(),
				StaticImage.backOfjbu_choose.getIconHeight());
		JButton[] myButton = new JButton[] { jbu_cannel.jbutton,
				jbu_save.jbutton, jbu_choose.jbutton };
		for (int i = 0; i < myButton.length; i++) {
			myButton[i].addActionListener(new ButtonActionListener(i, this));
			myButton[i].setVisible(true);
			this.add(myButton[i]);
		}
	}

	class ButtonActionListener implements ActionListener {
		private int buttonID;
		private ImportInputUI currentPanel;

		public ButtonActionListener(int id, ImportInputUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				// System.exit(0);
				saveCommodity();
				MyTipsFrame mtf = new MyTipsFrame();
				MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
				mtf.getIni(mtPanel);	
			}
			if (buttonID == BUTTON_CHOOSE) {
				MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
				ImpComChooseUI newPanel = new ImpComChooseUI(currentPanel,newFrame);
				newFrame.getIni(newPanel);
						
			}
		}
	}

	private void getTextFiledInit() {// 初始化输入框
		// MyTextField commodityID=new MyTextField(180, 88,155 , 20);
		commodityID = new JLabel();// 不可修改的用label
		commodityID.setBounds(180, 88, 155, 20);
		commodityNameField = new MyTextField(460, 87, 155, 20);
		commodityType = new MyTextField(180, 132, 155, 20);
		commodityNumber = new MyTextField(460, 129, 155, 20);
		commodityPrePrice = new MyTextField(180, 177, 155, 20);
		commodityTotalPrice = new MyTextField(460, 175, 155, 20);
		commodityRemark = new MyTextField(180, 221, 434, 54);

		this.add(commodityID);
		this.add(commodityNameField.jtextfield);
		this.add(commodityType.jtextfield);
		this.add(commodityNumber.jtextfield);
		this.add(commodityPrePrice.jtextfield);
		this.add(commodityTotalPrice.jtextfield);
		this.add(commodityRemark.jtextfield);

	}

	public void addCommodityInfo(CommodityVO commodity) {
		mycommodity = commodity;
		commodityID.setText(Integer.toString(commodity.getCommodityID()));
		commodityNameField.jtextfield.setText(commodity.getCommodityName());
		commodityType.jtextfield.setText(commodity.getCommodityModel());
		commodityPrePrice.jtextfield.setText(Double.toString(commodity
				.getPurchasePrice()));
	}

	// 返回添加的商品
	private void saveCommodity() {
      int commodityNum=Integer.parseInt(commodityNumber.jtextfield.getText());//商品数量
      String remark=commodityRemark.jtextfield.getText();//备注
      Double price_per=Double.parseDouble(commodityPrePrice.jtextfield.getText()); 
      Commodity commodity=new Commodity(mycommodity);
      Double different=Math.abs(price_per-mycommodity.getLatestPurchasePrice());
      if(different>0.000001){//进价修改的情况
    	  commodity.setLatestPurchasePrice(price_per);
      }
      if((callBackPanel.isImport==false)&&(commodityNum>mycommodity.getInventoryQuantity())){//退货且数量不足的情况
    		MyTipsFrame mtf = new MyTipsFrame();
			MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfNum_Lack.getImage(),this);
			mtf.getIni(mtPanel);	  
    	}else{
            ImportLineItem myLineItem=new ImportLineItem(commodity, commodityNum, remark);
            callBackPanel.addCommodityRow(myLineItem);
      }
     
	}
	// 背景
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImportInput.getImage(), 0, 0, null);
	}
}
