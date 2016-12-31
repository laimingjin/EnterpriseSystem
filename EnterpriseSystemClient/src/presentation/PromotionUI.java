package presentation;

import inputUI.PromotionCustomerInputUI;
import inputUI.PromotionPriceInputUI;
import inputUI.SpecialPriceInputUI;

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

import temp_business.PromotionBLService;
import temp_businessImp.PromotionBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.PromotionVO;
import vo.PromotionVO_Customer;
import vo.PromotionVO_Package;
import vo.PromotionVO_Price;
import docDetailUI.PromotionDetailUI;
import enumClass.ResultMessage;

public class PromotionUI extends JPanel {

	//private static final long serialVersionUID = 1L;
	/**
	 * 促销策略界面
	 */

	// 每个按钮对应的常量
//130,50,90,35
	private static int JBO_PROMOTIONCUSTOMNER=0;
	private static int JBO_SPECIALPRICE = 1;
	private static int JBO_PROMOTIONPRIEC=2;
	private static int JBU_SHOW=3;
	private static int JBO_DELETE=4;
	private static int JBU_ALLSEND=5;
	private static int JBO_SEND=6;
	private static int JBU_DETIL = 7;
	private static int JBO_TURNBACK=8;
	private static int JBO_EXIT = 9;
	boolean isShow=false;//是否处于显示状态
	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons;// 存放按钮的数组
    //数据Table
	JTable  promotionTable=new JTable();
	//表头
	Vector<String> columnName=new Vector<String>();
	//存放促销策略
	ArrayList<PromotionVO> promotionList;
	//表格Panel
	JScrollPane myTablePanel;
	//逻辑
	private PromotionBLService promotionBLService;

	/**
	 * Create the application.
	 */
	public PromotionUI() {
		setLayout(null);
		promotionBLService=new PromotionBLImp();
		promotionList=new ArrayList<PromotionVO>();
		initialize();
		myTablePanel=new JScrollPane();
		myTablePanel.setBounds(220, 125, 790, 410);
		myTablePanel.setVisible(true);
		this.add(myTablePanel);
		getTabelInit();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);
		MyButton promotionCustomer=new MyButton(StaticImage.backOfPromotionCustomer,22,180, 130, 50);
		this.add(promotionCustomer.jbutton);
		MyButton specialPrice=new MyButton(StaticImage.backOfSpecialPrice,22,320, 130, 50);
		this.add(specialPrice.jbutton);
		MyButton promotionPrice=new MyButton(StaticImage.backOfPromotionPrice,22,452, 130, 50);
		this.add(promotionPrice.jbutton);
		MyButton show=new MyButton(StaticImage.backOfShow, 396, 79, StaticImage.backOfShow.getIconWidth(), StaticImage.backOfShow.getIconHeight());
		this.add(show.jbutton);
		MyButton delete=new MyButton(StaticImage.backOfDelete, 527, 79, 90, 35);
		this.add(delete.jbutton);
		MyButton allSend = new MyButton(StaticImage.backOfSaveAll, 659, 79,StaticImage.backOfjbu_AllSend.getIconWidth(),StaticImage.backOfjbu_AllSend.getIconHeight());
		this.add(allSend.jbutton);
		MyButton send=new MyButton(StaticImage.backOfSavebtn,789,79, 90,35);
		this.add(send.jbutton);
		MyButton detil=new MyButton(StaticImage.backOfjbu_docDetil,904,79,StaticImage.backOfjbu_docDetil.getIconWidth(),StaticImage.backOfjbu_docDetil.getIconHeight());
		this.add(detil.jbutton);
		MyButton turnBack=new MyButton(StaticImage.backOfTurnBack,907, 63, 50, 13);
		this.add(turnBack.jbutton);
		MyButton exit=new MyButton(StaticImage.backOfExit,965, 63, 50, 13);
		this.add(exit.jbutton);
	
		jButtons=new JButton[]{promotionCustomer.jbutton,specialPrice.jbutton,promotionPrice.jbutton,show.jbutton,delete.jbutton,allSend.jbutton,
				send.jbutton,detil.jbutton,turnBack.jbutton,exit.jbutton
		};
		
		for(int i=0;i<jButtons.length;i++){
			 jButtons[i].addActionListener(new ButtonsActionListener(i,this));
		}
	}
	
	private void addColumn(){//表头
		columnName.add("编号");
		columnName.add("制定时间");
		columnName.add("失效时间");
		columnName.add("促销类型");
	}
	private void getTabelInit(){//初始化表格
		if (columnName.isEmpty()) {
			addColumn();
		}
		Vector<Vector<String>>  rowInfo=new Vector<Vector<String>>();
		DefaultTableModel newTableModel = new DefaultTableModel(rowInfo, columnName){
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		promotionTable=new JTable(newTableModel);
		myTablePanel.getViewport().add(promotionTable);
 	}
	
	
	class ButtonsActionListener implements ActionListener {
		private int buttonID;
       PromotionUI currentPanel;
		public ButtonsActionListener(int id,PromotionUI panel) {
			buttonID = id;
			currentPanel=panel;
		}
		public void actionPerformed(ActionEvent e) {
			if (buttonID==JBO_PROMOTIONCUSTOMNER) {
				if (isShow) {
					isShow=false;
					promotionList=new ArrayList<PromotionVO>();
					getTabelInit();
				}
				MyDocumentInputFrame f=new MyDocumentInputFrame();
				PromotionCustomerInputUI customerInputUI=	new PromotionCustomerInputUI(f,currentPanel);
				f.getIni(customerInputUI);
			}
			if(buttonID==JBO_SPECIALPRICE){
				if (isShow) {
					isShow=false;
					promotionList=new ArrayList<PromotionVO>();
					getTabelInit();
				}
				MyDocumentInputFrame fSpecialPrice=new MyDocumentInputFrame();
				SpecialPriceInputUI myInputUI=new SpecialPriceInputUI(fSpecialPrice,currentPanel);
				fSpecialPrice.getIni(myInputUI);
			}
			if(buttonID==JBO_PROMOTIONPRIEC){
				if (isShow) {
					isShow=false;
					promotionList=new ArrayList<PromotionVO>();
					getTabelInit();
				}
				MyDocumentInputFrame fPromotionPackage=new MyDocumentInputFrame();
				PromotionPriceInputUI myInputUI=new PromotionPriceInputUI(fPromotionPackage,currentPanel);
				fPromotionPackage.getIni(myInputUI);
			}
			if (buttonID==JBU_SHOW) {
				if (promotionList.isEmpty()&&(!isShow)) {//未填写策略的情况
					isShow=true;
					getTabelInit();
					showPromotion();		
				}else {//提示操作错误
					MyTipsFrame myTipsFrame=new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backOfSendFirst.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			}
			if(buttonID==JBO_DELETE){
				int index=promotionTable.getSelectedRow();
				if(index>-1){
				if (isShow) {
					if (promotionList.get(index) instanceof PromotionVO_Customer) {
						promotionBLService.delete((PromotionVO_Customer)promotionList.get(index));
					}
					if (promotionList.get(index) instanceof PromotionVO_Price) {
						promotionBLService.delete((PromotionVO_Price)promotionList.get(index));
					}
					if (promotionList.get(index) instanceof PromotionVO_Package) {
						promotionBLService.delete((PromotionVO_Package)promotionList.get(index));
					}
				}
				
					promotionTable.remove(index);
					promotionList.remove(index);
					promotionTable.updateUI();
					
				}
			}
			if(buttonID==JBU_ALLSEND){
				if (isShow) {
					MyTipsFrame myTipsFrame=new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}else {
					allSend();
				}
			}
			if(buttonID==JBO_SEND){
				if (isShow) {
					MyTipsFrame myTipsFrame=new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backOfIllegal.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}else {
					sendVO();
				}
			}
		  if (buttonID==JBU_DETIL) {
			showDetail();
		}
			if(buttonID==JBO_TURNBACK){
				nextJpanel=new ManagerWholeUI();			  
		  		MyFrame.getFrame().changePanel( nextJpanel);
			}
			
			if(buttonID==JBO_EXIT){
				nextJpanel=new LogInUI();
			MyFrame.getFrame().changePanel(nextJpanel);
			}
		}
	}
	
	//發送單據
	private void sendVO(){
		int index=promotionTable.getSelectedRow();
		ResultMessage resutl=ResultMessage.FAIL;
		if (index>-1) {
			PromotionVO temPromotionVO=promotionList.get(index);
			if (temPromotionVO instanceof PromotionVO_Customer) {
				resutl=promotionBLService.add((PromotionVO_Customer)temPromotionVO);
			}else if(temPromotionVO instanceof PromotionVO_Package) {
				resutl=promotionBLService.add((PromotionVO_Package)temPromotionVO);
			}else if(temPromotionVO instanceof PromotionVO_Price) {
				resutl=promotionBLService.add((PromotionVO_Price)temPromotionVO);
			}           
			if (resutl==ResultMessage.SUCCESS) {
				MyTipsFrame myTipsFrame=new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backOfSuccess.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
				promotionList.remove(index);
				promotionTable.remove(index);
			}else {
				MyTipsFrame myTipsFrame=new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backofPro_Exist.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
	}
    	
//批量發送
	private void allSend(){

		for (int i = 0; i < promotionList.size(); i++) {
			PromotionVO temPromotionVO=promotionList.get(i);
			if (temPromotionVO instanceof PromotionVO_Customer) {
				promotionBLService.add((PromotionVO_Customer)temPromotionVO);
			}else if(temPromotionVO instanceof PromotionVO_Package) {
				promotionBLService.add((PromotionVO_Package)temPromotionVO);
			}else if(temPromotionVO instanceof PromotionVO_Price) {
				promotionBLService.add((PromotionVO_Price)temPromotionVO);
			}                                                                                                                                                                          
		}
		getTabelInit();
		MyTipsFrame myTipsFrame=new MyTipsFrame();
		MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(myTipsFrame, StaticImage.backOfSuccess.getImage());
		myTipsFrame.getIni(myFailTipsPanel);
	
	}
	//添加表格信息方法
	public void addNewRow(PromotionVO_Package newPromotionVO_Package){//特价包
	
			promotionList.add(newPromotionVO_Package);		
	
		String id=Integer.toString(newPromotionVO_Package.getId());//单据编号
		String date=newPromotionVO_Package.getDate_Start();
		String endDate=newPromotionVO_Package.getDate_End();
		String name="特价包";
		Vector<String> newRow=new Vector<String>();
		newRow.add(id);
		newRow.add(date);
		newRow.add(endDate);
		newRow.add(name);
		
		DefaultTableModel dtm=(DefaultTableModel) promotionTable.getModel();
		dtm.addRow(newRow);
//		myTablePanel.updateUI();
	}
	public void addNewRow(PromotionVO_Price newPromotionVO_Price){//总价促销
	
			promotionList.add(newPromotionVO_Price);
		
		String id=Integer.toString(newPromotionVO_Price.getId());//单据编号
		String date=newPromotionVO_Price.getDate_Start();//开始日期
		String endDate=newPromotionVO_Price.getDate_End();//失效日期
		String name="总价促销";//类型
		Vector<String> newRow=new Vector<String>();
		newRow.add(id);
		newRow.add(date);
		newRow.add(endDate);
		newRow.add(name);
		DefaultTableModel dtm=(DefaultTableModel) promotionTable.getModel();
		dtm.addRow(newRow);
//		myTablePanel.updateUI();
	}
	public void addNewRow(PromotionVO_Customer newPromotionVO_Customer){//特殊客户
	
			promotionList.add(newPromotionVO_Customer);
		
		String id=Integer.toString(newPromotionVO_Customer.getId());//单据编号
		String date=newPromotionVO_Customer.getDate_Start();//开始日期
		String endDate=newPromotionVO_Customer.getDate_End();//失效日期
		String name="客户优惠";
		Vector<String> newRow=new Vector<String>();
		newRow.add(id);
		newRow.add(date);
		newRow.add(endDate);
		newRow.add(name);
		DefaultTableModel dtm=(DefaultTableModel) promotionTable.getModel();
		dtm.addRow(newRow);
//		myTablePanel.updateUI();
	}
	
	private void showPromotion(){//显示所有策略
		ArrayList< PromotionVO_Customer> customerList=promotionBLService.displayAllPromotionVO_Customers();
		ArrayList< PromotionVO_Package> packageList=promotionBLService.displayAllpPromotionVO_Packages();
		ArrayList<PromotionVO_Price> priceList=promotionBLService.displayAllpPromotionVO_Prices();
		
		//添加到表格
		for (int i = 0; i < customerList.size(); i++) {
			addNewRow(customerList.get(i));
		   }
		for (int i = 0; i < packageList.size(); i++) {
			addNewRow(packageList.get(i));
		   }
		for (int i = 0; i < priceList.size(); i++) {
			addNewRow(priceList.get(i));
		   }
	}
	private void showDetail(){//显示具体信息
		int index=promotionTable.getSelectedRow();
		if (index>-1) {
			if(promotionList.get(index) instanceof PromotionVO_Customer){
				MyDocumentInputFrame myInputFrame=new MyDocumentInputFrame();
				PromotionDetailUI promotionDetailUI=new PromotionDetailUI(myInputFrame,(PromotionVO_Customer) promotionList.get(index));
				myInputFrame.getIni(promotionDetailUI);
			}else if (promotionList.get(index) instanceof PromotionVO_Price) {
				MyDocumentInputFrame myInputFrame=new MyDocumentInputFrame();
				PromotionDetailUI promotionDetailUI=new PromotionDetailUI(myInputFrame,(PromotionVO_Price) promotionList.get(index));
				myInputFrame.getIni(promotionDetailUI);
			} else {
				MyDocumentInputFrame myInputFrame=new MyDocumentInputFrame();
				PromotionDetailUI promotionDetailUI=new PromotionDetailUI(myInputFrame,(PromotionVO_Package) promotionList.get(index));
				myInputFrame.getIni(promotionDetailUI);
			}
		}
	}
	
protected void paintComponent(Graphics g) {
		
      	super.paintComponent(g);
      
		g.drawImage(StaticImage.backOfPromotionUI.getImage(), 0, 0, null);
	}
}
