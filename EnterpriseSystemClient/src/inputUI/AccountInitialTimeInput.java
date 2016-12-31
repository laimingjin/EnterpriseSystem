package inputUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import presentation.AccountInitialUI;
import temp_business.ExportBLService;
import temp_business.SetAccountBLService;
import temp_businessImp.ExportBLImp;
import temp_businessImp.SetAccountBLImp;
import tool.MyButton;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.SetAccountVO;

public class AccountInitialTimeInput extends SuperInputUI{
	JComboBox<String> year1,year2,month1,month2,day1,day2;
	ExportBLService exportBLService=new ExportBLImp();
	private  AccountInitialUI accountInitialUI;
	

	SetAccountBLService setAccountBLService=new SetAccountBLImp();
	//private static final long serialVersionUID = 1L;
	public   AccountInitialTimeInput  ( AccountInitialUI myPanel,JFrame frame) {
		super(frame);
		accountInitialUI=myPanel;
	//	currentFrame=frame;
		initialize();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyInputFrame.FRAME_WIDTH,MyInputFrame.FRAME_HEIGHT);
		//6个下拉框
		 year1=new JComboBox<String> ();
		 year1.addItem("2016年");	
			year1.addItem("2015年");	
			year1.addItem("2014年");		//添加下拉可选项目
			year1.addItem("2013年");
			year1.addItem("2012年");
			year1.setSelectedItem("2015年");//默认是2014
	        year1.setFont(new Font("微软雅黑",0,16));
			year1.setBounds(24, 142, 112, 43);
			this.add(year1);
			
			 year2=new JComboBox<String> ();
			
			year2.addItem("2016年");
			year2.addItem("2015年");		
		 year2.addItem("2014年");		//添加下拉可选项目
		year2.addItem("2013年");
		year2.addItem("2012年");
		year2.setSelectedItem("2015年");//默认是2014
        year2.setFont(new Font("微软雅黑",0,16));
		year2.setBounds(24, 245, 112, 43);
		this.add(year2);
	
		month1=new JComboBox<String> ();
		for(int i=1;i<=9;i++){
			month1.addItem("0"+i+"月");
			//MyJComboBox day=new MyJComboBox(i,305,145,112,43);
		}
		month1.addItem("10月");
		month1.addItem("11月");
		month1.addItem("12月");
		
		month1.setSelectedItem("01月");
		month1.setFont(new Font("微软雅黑",0,16));
		month1.setBounds(163, 145, 112, 43);
		this.add(month1);
		
		
			 
		month2=new JComboBox <String>();
		for(int i=1;i<=12;i++){
			month2.addItem("0"+i+"月");
		
			
		}
		month2.addItem(10+"月");
		
		month2.addItem(11+"月");
		
		month2.addItem(12+"月");
		
		month2.setSelectedItem("01月");
		month2.setFont(new Font("微软雅黑",0,16));
		month2.setBounds(163, 245, 112, 43);
		this.add(month2);
		
		day1=new JComboBox<String> ();
		for(int i=1;i<=9;i++){
			 day1.addItem("0"+i+"日");
		
			
		}
		for(int i=10;i<=31;i++){
			day1.addItem(i+"日");
		}
		 day1.setSelectedItem("01日");
		 day1.setFont(new Font("微软雅黑",0,16));
		 day1.setBounds(303, 145, 112, 43);
		this.add( day1);
		
		day2=new JComboBox<String> ();
		for(int i=1;i<=9;i++){
			 day2.addItem("0"+i+"日");
		
			
		}
		for(int i=10;i<=31;i++){
			day2.addItem(i+"日");
		}
		 day2.setSelectedItem("01日");
		 day2.setFont(new Font("微软雅黑",0,16));
		 day2.setBounds(303, 245, 112, 43);
		this.add( day2);
		
		MyButton	cancel=new MyButton(StaticImage.backOfSmallCancel, 422, 5, 24, 24);
		//关闭建
				cancel.jbutton.addActionListener(new ActionListener() {//监听	
					public void actionPerformed(ActionEvent e) {



		         			closeFrame();

					
					}
				});
				cancel.jbutton.setVisible(true);
		this.add(cancel.jbutton);
		MyButton submit=new MyButton(StaticImage.backOfSubmit,177, 467, 120, 45);
		this.add(submit.jbutton);
		 (submit.jbutton).addActionListener(new ButtonListener(this));
}
	class ButtonListener implements ActionListener {
		private AccountInitialTimeInput currentPanel;
        public ButtonListener(AccountInitialTimeInput panel){
        	currentPanel=panel;
        }
		public void actionPerformed(ActionEvent e) {
			
			
			String y1=(String) year1.getSelectedItem();
			String m1=(String) month1.getSelectedItem();
			String d1=(String) day1.getSelectedItem();
			String y2=(String) year2.getSelectedItem();
			String m2=(String) month2.getSelectedItem();
			String d2=(String) day2.getSelectedItem();
			
			try {
				ArrayList<SetAccountVO>  setAccountVOs=	setAccountBLService.finds(y1.substring(0,4 )+m1.substring(0,2)+d1.substring(0,2)+","+y2.substring(0,4)+m2.substring(0,2)+d2.substring(0,2));
			    accountInitialUI.showSearchInfo(setAccountVOs);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	
			      
			//    commodityCheckUI.   showCommodityCheckListSearchInfo(checks);
		
			//diaoyongBL!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			
			
			MyTipsFrame mtf = new MyTipsFrame();
			MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
			mtf.getIni(mtPanel);	
		}
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfTimeInput.getImage(), 0, 0, null);
	}
	
	
	


}
