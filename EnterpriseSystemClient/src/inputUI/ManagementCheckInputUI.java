package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFrame;

import presentation.ManagementCheckUI;
import temp_business.SalesListBLService;
import temp_businessImp.SalesListBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.SalesListVO;
import businesslogic.SalesDetailLineItem;

public class ManagementCheckInputUI extends SuperInputUI {
	private  MyTextField textStartTime,textendTime,textCommodityName, textCustomer, textoperater,textcarriage;
	//开始时间，结束时间，商品名，客户，业务员，仓库
	private 	MyButton submit,output,cancel;
		private ManagementCheckUI managementCheckUI;
		SalesListBLService salesListBLService=new SalesListBLImp();
	//	private static final long serialVersionUID = 1L;
		public ManagementCheckInputUI(ManagementCheckUI myUI,JFrame frame) {
			super(frame);
			managementCheckUI=myUI;
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
			 textCommodityName=new MyTextField(28, 248, 192, 35);
			this.add(textCommodityName.jtextfield);
			textCustomer=new MyTextField(238, 248, 180, 35);
			this.add(textCustomer.jtextfield);
			textoperater=new MyTextField(27, 345, 192, 35);
			this.add(textoperater.jtextfield);
			textcarriage=new MyTextField(237, 345, 180, 35);
			this.add(textcarriage.jtextfield);
			
			submit=new MyButton(StaticImage.backOfSubmit,67, 466, 120, 45);
			output=new MyButton(StaticImage.backOfOutPut,256, 466, 120, 45);
			cancel = new MyButton(StaticImage.backOfSmallCancel, 422, 5, 24, 24);
			// 关闭建
			cancel.jbutton.addActionListener(new ActionListener() {// 监听
						public void actionPerformed(ActionEvent e) {

							closeFrame();

						}
					});
			cancel.jbutton.setVisible(true);
			this.add(cancel.jbutton);
			this.add(submit.jbutton);
			this.add(output.jbutton);
			 (submit.jbutton).addActionListener(new ButtonListener(this));
			 (output.jbutton).addActionListener(new outPutButtonListener(this));
		}
		//监听
		class ButtonListener implements ActionListener{
	     ManagementCheckInputUI currentPanel;
	      public ButtonListener(    ManagementCheckInputUI  panel){
	    	  currentPanel=panel;
	      }
			public void actionPerformed(ActionEvent e) {

				
				String time1=textStartTime.jtextfield.getText();
				String time2=textendTime.jtextfield.getText();
				String commodityName=textCommodityName.jtextfield.getText();
				String customerString=textCustomer.jtextfield.getText();
				String operatorString=textoperater.jtextfield.getText();
				String carriageString=textcarriage.jtextfield.getText();
				boolean control=false;

	            if(time1.equals("")){          
	            	time1="";
	            }
	            if(time2.equals("")){          
	            	time2="";
	            }
	            if(commodityName.equals("")){          
	            	commodityName="";
	            }
	            if(customerString.equals("")){          
	            	customerString="";
	            }
	            if(operatorString.equals("")){          
	            	operatorString="";
	            }
	            if(carriageString.equals("")){          
	            	carriageString="";
	            }
			    

	            ArrayList<SalesListVO>  arr=new ArrayList<SalesListVO>  ();
				SalesListVO  salesListVO=salesListBLService.showSalesList(time1, time2, commodityName, customerString,operatorString,carriageString,control );
				arr.add(salesListVO);
				if(arr.size()!=0){
					managementCheckUI.showSaleListSearchInfo(arr);
					
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
				
				}else{
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFailToGetDocument.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
			
				
			}
			
		}
		
		//监听
			class outPutButtonListener implements ActionListener{
			    ManagementCheckInputUI  currentPanel;
		      public outPutButtonListener(    ManagementCheckInputUI  panel){
		    	  currentPanel=panel;
		      }
				public void actionPerformed(ActionEvent e) {

					
					String time1=textStartTime.jtextfield.getText();
					String time2=textendTime.jtextfield.getText();
					String commodityName=textCommodityName.jtextfield.getText();
					String customerString=textCustomer.jtextfield.getText();
					String operatorString=textoperater.jtextfield.getText();
					String carriageString=textcarriage.jtextfield.getText();
					boolean control=true;//要求导出
					if(time1.equals("")){          
		            	time1="";
		            }
		            if(time2.equals("")){          
		            	time2="";
		            }
		            if(commodityName.equals("")){          
		            	commodityName="";
		            }
		            if(customerString.equals("")){          
		            	customerString="";
		            }
		            if(operatorString.equals("")){          
		            	operatorString="";
		            }
		            if(carriageString.equals("")){          
		            	carriageString="";
		            }
				    
			
				ArrayList<SalesListVO>  arr=new ArrayList<SalesListVO>  ();
			
				SalesListVO  salesListVO=salesListBLService.showSalesList(time1, time2, commodityName, customerString,operatorString,carriageString,control );
				arr.add(salesListVO);
				
				if(arr.size()!=0){
					managementCheckUI.showSaleListSearchInfo(arr);
				
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
				
				}else{
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFailToGetDocument.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}
				
			}
			}
		public void closeFrame(){//关闭窗口
			currentFrame.dispose();	
		}
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(StaticImage.backOfAccountInput.getImage(), 0, 0, null);
		}

	}


