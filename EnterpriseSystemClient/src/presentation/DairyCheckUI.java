package presentation;

import inputUI.DairyCheckTimeInput;
import inputUI.SmallOperatorNameInput;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import temp_business.LogBLService;
import temp_businessImp.LogBLImp;
import tool.MyButton;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.LogVO;
import aboutTree.LogTable;

public class DairyCheckUI extends JPanel{

	  /**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private JPanel nextJpanel;
	  private JTable myTable;
//		 private String selectedSonSort;
			private JScrollPane myScrollPane;
			boolean isAccount;
			LogTable logTable;
			DairyCheckUI dairyCheckUI=this;
			LogBLService logBLService=new LogBLImp();
	public DairyCheckUI (boolean isAccount) {
		initialize();
		setLayout(null);
		this.isAccount=isAccount;
		myScrollPane=new JScrollPane();
		logTable=new LogTable(this);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton exit=new MyButton(StaticImage.backOfExit,965, 63, 50, 13);
		this.add(exit.jbutton);
		MyButton turnBack=new MyButton(StaticImage.backOfTurnBack,907, 63, 50, 13);
		this.add(turnBack.jbutton);
		MyButton show=new MyButton(StaticImage.backOfShowAll,30,174, 130, 50);
		this.add(show.jbutton);
		MyButton byTime=new MyButton(StaticImage.backOfByTime,30,314, 130, 50);
		this.add(byTime.jbutton);
		MyButton byOperator=new MyButton(StaticImage.backOfByOperator,30,446, 130, 50);
		this.add(byOperator.jbutton);
	
		 MyTextField search=new MyTextField(785, 84, 200, 32);
			this.add(search.jtextfield);
		 (exit.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					nextJpanel=new LogInUI();
					  
			  		MyFrame.getFrame().changePanel( nextJpanel);
				}
			});
		 (turnBack.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if(isAccount){
					nextJpanel=new AccountWholeUI();
					}else {
						nextJpanel=new ManagerWholeUI();
					}
			  		MyFrame.getFrame().changePanel( nextJpanel);
				}
			});
		
		
		 (byTime.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					MyInputFrame accountinput1=new MyInputFrame();
					DairyCheckTimeInput dairyCheckTimeInput=new DairyCheckTimeInput(dairyCheckUI, accountinput1);
					accountinput1.getIni(dairyCheckTimeInput);
					
					
				}
			});
		 (byOperator.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				
					MyTipsFrame myTipsFrame=new MyTipsFrame();
					SmallOperatorNameInput myFailTipsPanel=new SmallOperatorNameInput(dairyCheckUI,myTipsFrame, StaticImage.backOfSmallOperatorInput.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
					
					
				}
			});
		 (show.jbutton).addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					 
					showSearchInfo( logBLService.displayAll());
				}
			});
	}
	public void showSearchInfo(ArrayList<LogVO>  arr){

		myTable=logTable.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	
	protected void paintComponent(Graphics g) {
		
      	super.paintComponent(g);
      
		g.drawImage(StaticImage.backOfDiaryCheckUI.getImage(), 0, 0, null);
	}
}

