package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import presentation.SaleUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityVO;
import businesslogic.Commodity;
import businesslogic.SaleLineItem;

import comChooseUI.SaleComChooseUI;

/**
 * @author 小春
 *2014年12月9日下午9:43:29
 *EnterpriseSystem  
 *InputUI 
 *SaleInputUI.java 
 */
public class SaleInputUI extends SuperInputUI {

//	private static final long serialVersionUID = 1L;
/**
 * 进货管理界面用来添加商品的
 */
	//每个按钮对应的编号
	private static int BUTTON_CANEL=0;
	private static int BUTTON_SAVE=1;
	private static int BUTTON_CHOOSE = 2;
	//各种信息输入框
	JLabel commodityID;
	MyTextField commodityNameField;
	MyTextField commodityType;
	MyTextField commodityNumber;
	MyTextField commodityPrePrice;
	MyTextField commodityTotalPrice;
	MyTextField commodityRemark;
	//商品
	private CommodityVO mycommodity;
	//返回的页面
	private SaleUI callBackPanel;
	//构造器
	public SaleInputUI(SaleUI panel,JFrame frame){
		super(frame);
		callBackPanel=panel;
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		getButtonInit();
		getTextFiledInit();
	}
	
	/**
	 *小春
	 *2014年12月9日下午9:49:52
	 *EnterpriseSystem
	 *InputUI
	 *SaleInputUI.java
	 */
	private void getButtonInit(){//初始化按钮
		MyButton jbu_cannel=new MyButton(StaticImage.backOfjbu_cancel, 359,288, 110, 34);
		MyButton jbu_save=new MyButton(StaticImage.backOfjbu_save, 203, 288, 110, 34);
		MyButton jbu_choose = new MyButton(StaticImage.backOfjbu_choose, 579,
				87, StaticImage.backOfjbu_choose.getIconWidth(),
				StaticImage.backOfjbu_choose.getIconHeight());
		JButton[] myButton=new JButton[]{
			jbu_cannel.jbutton,jbu_save.jbutton	, jbu_choose.jbutton
		};
		for(int i=0;i<myButton.length;i++){
			myButton[i].addActionListener(new ButtonActionListener(i,this));
			myButton[i].setVisible(true);
			this.add(myButton[i]);
		}
	}
//内部类，按钮监听
	class ButtonActionListener implements ActionListener{
           private int buttonID;
           private SaleInputUI currentPanel;
           public ButtonActionListener(int id,SaleInputUI panel){
        	   buttonID=id;
        	   currentPanel = panel;
           }
		public void actionPerformed(ActionEvent e) {
			if(buttonID==BUTTON_CANEL){
				closeFrame();
			}
			if (buttonID==BUTTON_SAVE) {
//				System.exit(0);
				saveCommodity();
				MyTipsFrame mtf = new MyTipsFrame();
				MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
				mtf.getIni(mtPanel);	
			}
			if (buttonID == BUTTON_CHOOSE) {
				MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
				SaleComChooseUI newPanel = new SaleComChooseUI(currentPanel,newFrame);
				newFrame.getIni(newPanel);
			}
		}
		
	}
	
	private void getTextFiledInit(){//初始化输入框
//		MyTextField commodityID=new MyTextField(180, 88,155 , 20);
		 commodityID=new JLabel();//不可修改的用label
		commodityID.setBounds(180, 88,155 , 20);
		 commodityNameField=new MyTextField(460, 87,155 , 20);
		 commodityType=new MyTextField(180, 132, 155, 20);
		 commodityNumber=new MyTextField(460, 129, 155, 20);
		 commodityPrePrice=new MyTextField(180, 177, 155, 20);
		 commodityTotalPrice=new MyTextField(460, 175, 155, 20);
		 commodityRemark=new MyTextField(180, 221, 434, 54);
		
		this.add(commodityID);
		this.add(commodityNameField.jtextfield);
		this.add(commodityType.jtextfield);
		this.add(commodityNumber.jtextfield);
		this.add(commodityPrePrice.jtextfield);
		this.add(commodityTotalPrice.jtextfield);
		this.add(commodityRemark.jtextfield);
	}
	//在当前页面显示商品信息的
	public void addCommodityInfo(CommodityVO commodity) {
		mycommodity = commodity;
		commodityID.setText(Integer.toString(commodity.getCommodityID()));
		commodityNameField.jtextfield.setText(commodity.getCommodityName());
		commodityType.jtextfield.setText(commodity.getCommodityModel());
		commodityPrePrice.jtextfield.setText(Double.toString(commodity
				.getPurchasePrice()));
	}
	
	private void saveCommodity() {
	      int commodityNum=Integer.parseInt(commodityNumber.jtextfield.getText());//商品数量
	      String remark=commodityRemark.jtextfield.getText();//备注
	      Double price_per=Double.parseDouble(commodityPrePrice.jtextfield.getText()); 
	      Commodity commodity=new Commodity(mycommodity);
	      Double different=Math.abs(price_per-mycommodity.getLatestRetailPrice());
	      if(different>0.000001){//售价修改的情况
	    	  commodity.setLatestRetailPrice(price_per);
	      }
	      if((callBackPanel.isSale==true)&&(commodityNum>mycommodity.getInventoryQuantity())){//销售且数量不足的情况
	    	  MyTipsFrame mtf = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(mtf, StaticImage.backOfNum_Lack.getImage());
				mtf.getIni(myFailTipsPanel);	    
	    	}else{
	            SaleLineItem myLineItem=new SaleLineItem(commodity, commodityNum, remark);
	            callBackPanel.addCommodityRow(myLineItem);
	      }
	     
		}
	//背景
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImportInput.getImage(), 0, 0, null);
	}

}
