package inputUI;

/**
 * 财务人员账户管理的输入界面
 * 包括：账户名称和账户金额
 * @author nancy
 * @version 1.0
 */
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import presentation.AccountManagementUI;
import temp_business.AccountBLService;
import temp_businessImp.AccountBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.AccountVO;
import enumClass.ResultMessage;

public class AccountManagementInputUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	private MyTextField textAccountName, textAccountPrice;
	private AccountManagementUI accountManagementUI;
	AccountBLService accountBLService=new AccountBLImp();
	public AccountManagementInputUI(AccountManagementUI myPanel,JFrame frame) {
		super(frame);
		accountManagementUI = myPanel;
		initialize();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @see MyInputFrame
	 */
	private void initialize() {
		this.setSize(MyInputFrame.FRAME_WIDTH,MyInputFrame.FRAME_HEIGHT);
		textAccountName = new MyTextField(113, 184, 222, 40);
		this.add(textAccountName.jtextfield);
		textAccountPrice = new MyTextField(113, 309, 222, 40);
		this.add(textAccountPrice.jtextfield);

		MyButton submit = new MyButton(StaticImage.backOfSubmit, 177, 467, 120,
				45);
		this.add(submit.jbutton);
		MyButton cancel=new MyButton(StaticImage.backOfSmallCancel, 422, 5, 24, 24);
		//关闭建
				cancel.jbutton.addActionListener(new ActionListener() {//监听	
					public void actionPerformed(ActionEvent e) {



		         			closeFrame();

					
					}
				});
				cancel.jbutton.setVisible(true);
		this.add(cancel.jbutton);
		(submit.jbutton).addActionListener(new ButtonListener(this));
	}
	//鼠标监听
	class ButtonListener implements ActionListener{
	      AccountManagementInputUI currentPanel;
	      public ButtonListener(AccountManagementInputUI panel){
	    	  currentPanel=panel;
	      }
			public void actionPerformed(ActionEvent e) {

				String accountName = textAccountName.jtextfield.getText();

				double accountPrice = Double
						.parseDouble(textAccountPrice.jtextfield.getText());
				if(accountPrice<0){
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfSmallAmount
									.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}else{
				AccountVO newAccountVO = new AccountVO(accountName,
						accountPrice);
                         ResultMessage resultMessage=accountBLService.addAccount(newAccountVO);  
                         if(resultMessage.equals(ResultMessage.SUCCESS)){
                        	 accountManagementUI.addAccount(newAccountVO);
             				MyTipsFrame mtf = new MyTipsFrame();
             				MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(mtf,
             						StaticImage.backOfSuccess.getImage(),currentPanel);
             				mtf.getIni(mtPanel);
                         }else{
                        	 MyTipsFrame myTipsFrame = new MyTipsFrame();
         					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
         							myTipsFrame,
         							StaticImage.backOfFailToAddDocument
         									.getImage());
         					myTipsFrame.getIni(myFailTipsPanel);
                         }
				
			}
			}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfAccountManagementInput.getImage(), 0, 0,
				null);
	}

}
