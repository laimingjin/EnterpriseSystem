package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;

import presentation.ManagementCheckUI;
import temp_business.BusinessProcessListBLService;
import temp_businessImp.BusinessProcessListBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.DocumentVO;
import vo.WholeDocumentVO;

public class ManagerBusinessProcessInputUI extends SuperInputUI {
	private  MyTextField textStartTime,textendTime,textType, textCustomer, textoperater,textcarriage;
	//开始时间，结束时间，商品名，客户，业务员，仓库
	private 	MyButton submit,cancel;
	private ManagementCheckUI managementCheckUI;
	BusinessProcessListBLService businessProcessListBLService = new BusinessProcessListBLImp();
	//	private static final long serialVersionUID = 1L;
		public  ManagerBusinessProcessInputUI( ManagementCheckUI myUI,JFrame frame) {
			super(frame);
			managementCheckUI=myUI;
//			currentFrame=frame;
			initialize();
			setLayout(null);
		}

		/**
		 * @see MyInputFrame
		 */
		private void initialize() {
			this.setSize(MyInputFrame.FRAME_WIDTH,MyInputFrame.FRAME_HEIGHT);
			textStartTime=new MyTextField(27, 146, 192, 35);
			this.add(textStartTime.jtextfield);
			textendTime=new MyTextField(235, 146, 180, 35);
			this.add(textendTime.jtextfield);
			 textType=new MyTextField(28, 248, 192, 35);
			this.add( textType.jtextfield);
			textCustomer=new MyTextField(238, 248, 180, 35);
			this.add(textCustomer.jtextfield);
			textoperater=new MyTextField(27, 345, 192, 35);
			this.add(textoperater.jtextfield);
			textcarriage=new MyTextField(237, 345, 180, 35);
			this.add(textcarriage.jtextfield);
			
			submit=new MyButton(StaticImage.backOfSubmit,177, 467, 120, 45);
			this.add(submit.jbutton);
			 (submit.jbutton).addActionListener(new ButtonListener(this));
				cancel = new MyButton(StaticImage.backOfSmallCancel, 422, 5, 24, 24);
				// 关闭建
				cancel.jbutton.addActionListener(new ActionListener() {// 监听
							public void actionPerformed(ActionEvent e) {

								closeFrame();

							}
						});
				cancel.jbutton.setVisible(true);
				this.add(cancel.jbutton);
		}
	   class ButtonListener implements ActionListener{
		  ManagerBusinessProcessInputUI currentPanel;
	     public ButtonListener(   ManagerBusinessProcessInputUI panel){
	    	  currentPanel=panel;
	      }
		public void actionPerformed(ActionEvent e) {

			String time1=textStartTime.jtextfield.getText();
			String time2=textendTime.jtextfield.getText();
			String  type= textType.jtextfield.getText();
			String customerString=textCustomer.jtextfield.getText();
			String operatorString=textoperater.jtextfield.getText();
			String carriageString=textcarriage.jtextfield.getText();
			String timezoneString = "";
			if (time1.equals("")) {
				timezoneString = ",";
			} else {
				timezoneString = time1 + "," + time2;
			}

			if (type.equals("")) {
				type = "";
			}
			if (customerString.equals("")) {
				customerString = "";
			}
			if (operatorString.equals("")) {
				operatorString = "";
			}
			if (carriageString.equals("")) {
				carriageString = "";
			}
			// 调用bl的方法！！！！！！！！！！！
			// 返回了arraylist.................................................................
			WholeDocumentVO wholeDocumentVO = null;
			try {
				wholeDocumentVO = businessProcessListBLService
						.showBusinessProcessList(timezoneString, type,
								customerString, operatorString, carriageString);
				managementCheckUI.setWholeDocumentVO(wholeDocumentVO);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			ArrayList<DocumentVO> documentVOs = wholeDocumentVO
					.getDocumentVOs();
			if (documentVOs.size() != 0) {
				managementCheckUI.showBusinessProcessSearchInfo(documentVOs);

				MyTipsFrame mtf = new MyTipsFrame();
				MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(mtf,
						StaticImage.backOfSuccess.getImage(), currentPanel);
				mtf.getIni(mtPanel);
			} else {
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame,
						StaticImage.backOfFailToGetDocument.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			}
		}
	   }
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(StaticImage.backOfAccountInput2.getImage(), 0, 0, null);
		}

	}


