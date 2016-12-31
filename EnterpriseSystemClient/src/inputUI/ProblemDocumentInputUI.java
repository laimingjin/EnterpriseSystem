package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import presentation.CommodityDocumentUI;
import temp_business.CommodityBLService;
import temp_business.CommodityDocumentBLService;
import temp_businessImp.CommodityBLImp;
import temp_businessImp.CommodityDocumentBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityDocumentVO;
import businesslogic.Commodity;
import enumClass.PROBLEM;
import enumClass.ResultMessage;

public class ProblemDocumentInputUI extends SuperInputUI{

	//private static final long serialVersionUID = 1L;
	MyTextField textno,textyear,textmonth,textday, textProblemName,textCommodityModel, textCommodityName,
	textActualQuantity, textSystemQuantity;
	private CommodityDocumentUI commodityDocumentUI;
	private String commodityDocumentType;
	CommodityDocumentBLService commodityDocumentBLService=new CommodityDocumentBLImp();
	CommodityBLService commodityBLService=new CommodityBLImp();
	public ProblemDocumentInputUI (CommodityDocumentUI myPanel, String commodityDocumentinfo,JFrame frame) {
		super(frame);
		commodityDocumentType=commodityDocumentinfo;
		commodityDocumentUI=myPanel;
		initialize();
		setLayout(null);
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
	     textno=new MyTextField(147, 59, 90, 20);
		this.add(textno.jtextfield);
		textyear=new MyTextField(450, 59, 35, 20);
		this.add(textyear.jtextfield);
		 textmonth=new MyTextField(510, 59, 35, 20);
		this.add(textmonth.jtextfield);
		 textday=new MyTextField(571, 59, 35, 20);
		this.add(textday.jtextfield);
        textProblemName=new MyTextField(335, 87, 220, 30);
		this.add(textProblemName.jtextfield);
		 textCommodityName=new MyTextField(335, 132, 220, 30);
			this.add(textCommodityName.jtextfield);
		 textCommodityModel=new MyTextField(335, 176, 220, 30);
			this.add(textCommodityModel.jtextfield);
		  textActualQuantity=new MyTextField(335, 225, 220, 30);
			this.add(textActualQuantity.jtextfield);
		 textSystemQuantity=new MyTextField(335, 264, 220, 30);
			this.add(textSystemQuantity.jtextfield);
			
			textProblemName.jtextfield.setEditable(false);// 设置不可输入
			textProblemName.jtextfield.setText(commodityDocumentType);
			MyButton edit=new MyButton(StaticImage.backOfEdit,160, 312, 108, 32);
			MyButton save=new MyButton(StaticImage.backOfSave,319, 312, 108, 32);
			MyButton documentCancel=new MyButton(StaticImage.backOfDocumentCancel,477, 312, 108, 32);
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		String dataNow = dateFormat.format( now ); 
		
			textyear.jtextfield.setText(dataNow.substring(0,4));
			textmonth.jtextfield.setText(dataNow.substring(4,6));
			textday.jtextfield.setText(dataNow.substring(6,8));
	        textno.jtextfield.setText(String.valueOf(commodityDocumentBLService.getNewCommodityDocumentID()));
	        textno.jtextfield.setEditable(false);// 设置不可输入
		this.add(edit.jbutton);
		 (edit.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
				}
			});
		 
		 
		 this.add(save.jbutton);
		 (save.jbutton).addActionListener(new ButtonListener(this));
		 
		 
		 this.add(documentCancel.jbutton);
		 (documentCancel.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					closeFrame();
				}
			});	 
	}
	class ButtonListener implements ActionListener{
     private ProblemDocumentInputUI currentPanel;
     public ButtonListener(ProblemDocumentInputUI panel){
    	 currentPanel=panel;
     }
		public void actionPerformed(ActionEvent e) {
			
			 String year=textyear.jtextfield.getText();
			 String month=textmonth.jtextfield.getText();
			 String day=textday.jtextfield.getText();
			 String date=year+month+day;
              int ID=Integer.parseInt(textno.jtextfield.getText());
              String problem = textProblemName.jtextfield.getText();
			 String commodityName = textCommodityName.jtextfield.getText();
			 String commodityModel=textCommodityModel.jtextfield.getText();
			 Commodity commodity=new Commodity(commodityBLService.find(commodityName, commodityModel));
			int actualQuantity=Integer.parseInt(textActualQuantity.jtextfield.getText());
			textActualQuantity .jtextfield.setText(String.valueOf(commodity.getInventoryQuantity()));
			//    Commodity PI=new Commodity("卡西欧", 0011, "CASIOA", 11, "990e", 0, 100,  100,  0, 0, "20141206", 10,10);
              PROBLEM P = null;
              if(problem.equals("未发送报溢单")){
           	   P=PROBLEM.OVERFLOW;
              }else if(problem.equals("未发送报损单")){
           	   P=PROBLEM.LOSS;
              }else if(problem.equals("未发送报警单")){
           	   P=PROBLEM.WARN;
              }else{
           	   //不可在此处添加
            	
              }
		CommodityDocumentVO newCommodityDocumentVO=new CommodityDocumentVO(date, ID, commodity, P, actualQuantity, false, false, false);
		ResultMessage resultMessage=commodityDocumentBLService.addCommodityDocument(newCommodityDocumentVO);
		if(resultMessage.equals(ResultMessage.SUCCESS)){
			MyTipsFrame mtf = new MyTipsFrame();
			MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
			mtf.getIni(mtPanel);
		}else{
			// 无法添加
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
					myTipsFrame,
					StaticImage.backOfFailToAddDocument.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
		commodityDocumentUI.addCommodityDocument(newCommodityDocumentVO);

	
		}
		
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfProblemDocumentUI.getImage(), 0, 0, null);
	}

}


