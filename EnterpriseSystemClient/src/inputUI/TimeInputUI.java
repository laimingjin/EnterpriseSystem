package inputUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import tool.MyButton;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;

public class TimeInputUI extends SuperInputUI{

	//private static final long serialVersionUID = 1L;
	public  TimeInputUI (JFrame frame) {
		super(frame);
		currentFrame=frame;
		initialize();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyInputFrame.FRAME_WIDTH,MyInputFrame.FRAME_HEIGHT);
		//6个下拉框
		JComboBox<String> year1=new JComboBox<String> ();
		year1.addItem("2016年");	
		year1.addItem("2015年");	
		year1.addItem("2014年");		//添加下拉可选项目
		year1.addItem("2013年");
		year1.addItem("2012年");
		year1.setSelectedItem("2015年");//默认是2014
        year1.setFont(new Font("微软雅黑",0,16));
		year1.setBounds(24, 142, 112, 43);
		this.add(year1);
		
		JComboBox<String> year2=new JComboBox<String> ();
		
		year2.addItem("2016年");
		year2.addItem("2015年");		
		year2.addItem("2014年");		//添加下拉可选项目
		year2.addItem("2013年");
		year2.addItem("2012年");
		year2.setSelectedItem("2015年");//默认是2014
        year2.setFont(new Font("微软雅黑",0,16));
		year2.setBounds(24, 245, 112, 43);
		this.add(year2);
	
		JComboBox<String> month1=new JComboBox<String> ();
		for(int i=1;i<=12;i++){
			month1.addItem(i+"月");
			//MyJComboBox day=new MyJComboBox(i,305,145,112,43);
		}
		month1.setSelectedItem("1月");
		month1.setFont(new Font("微软雅黑",0,16));
		month1.setBounds(163, 145, 112, 43);
		this.add(month1);
	
		
			 
		JComboBox<String> month2=new JComboBox <String>();
		for(int i=1;i<=12;i++){
			month2.addItem(i+"月");
		
			
		}
		month2.setSelectedItem("1月");
		month2.setFont(new Font("微软雅黑",0,16));
		month2.setBounds(163, 245, 112, 43);
		this.add(month2);
		
		JComboBox <String>day1=new JComboBox<String> ();
		for(int i=1;i<=31;i++){
			 day1.addItem(i+"日");
		
			
		}
		 day1.setSelectedItem("1日");
		 day1.setFont(new Font("微软雅黑",0,16));
		 day1.setBounds(303, 145, 112, 43);
		this.add( day1);
		
		JComboBox <String>day2=new JComboBox<String> ();
		for(int i=1;i<=31;i++){
			 day2.addItem(i+"日");
		
			
		}
		 day2.setSelectedItem("1日");
		 day2.setFont(new Font("微软雅黑",0,16));
		 day2.setBounds(303, 245, 112, 43);
		this.add( day2);
		
		
		MyButton submit=new MyButton(StaticImage.backOfSubmit,177, 467, 120, 45);
		this.add(submit.jbutton);
		 (submit.jbutton).addActionListener(new ButtonListener(this));
}
	class ButtonListener implements ActionListener {
		private TimeInputUI currentPanel;
        public ButtonListener(TimeInputUI panel){
        	currentPanel=panel;
        }
		public void actionPerformed(ActionEvent e) {
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
