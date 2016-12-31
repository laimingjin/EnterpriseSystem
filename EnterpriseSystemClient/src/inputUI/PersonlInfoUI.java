package inputUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import temp_business.UserBLService;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.UserVO;
import enumClass.ResultMessage;

/**
 * @author 小春
 *2014年12月13日下午11:18:33
 *EnterpriseSystem  
 *inputUI 
 *PersonlInfoUI.java 
 *用户自我管理界面
 */
public class PersonlInfoUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	JPanel nextPanel;
	MyTextField textUserPassword;
UserBLService userBLService;
	public PersonlInfoUI(JFrame frame) {
		super(frame);
		userBLService=new UserBLImp();
		this.setSize(MyInputFrame.FRAME_WIDTH, MyInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		getPanelInit();
	}

	private void getPanelInit() {
		UserVO myUser=MyFrame.getTheUser();
		MyButton verify = new MyButton(StaticImage.backOfVerify, 142, 427,
				StaticImage.backOfVerify.getIconWidth(),
				StaticImage.backOfVerify.getIconHeight());
		verify.jbutton.addActionListener(new ButtonListener(this));
		verify.jbutton.setVisible(true);
		this.add(verify.jbutton);

		//不可修改的部分用Jlabel
		//编号
		JLabel textUserID = new JLabel();
		textUserID.setBounds(209, 136, 181, 39);
		textUserID.setText(Integer.toString(myUser.getUserID()));
		textUserID.setBackground(Color.white);
		textUserID.setVisible(true);
		this.add(textUserID);
		//职位
		JLabel textUserJob=new JLabel();
		textUserJob.setBounds(209, 292, 181, 39);
		textUserJob.setText(myUser.getTheJob());
		textUserJob.setBackground(Color.white);
		textUserJob.setVisible(true);
		this.add(textUserJob);
		//权限级别
		JLabel textUserLevel = new JLabel();
		textUserLevel.setBounds(209, 343, 181, 39);
		textUserLevel.setText(Integer.toString(myUser.getPowerLevel()));
		textUserLevel.setBackground(Color.white);
		textUserLevel.setVisible(true);
		this.add(textUserLevel);
        //用户名
		JLabel textUserName = new JLabel();
		textUserName.setBounds(209, 189, 181, 39);
		textUserName.setText(myUser.getUserName());
		textUserName.setVisible(true);
		this.add(textUserName);
		
		textUserPassword = new MyTextField(209, 240, 181, 39);
		textUserPassword.jtextfield.setText("******");
		textUserPassword.jtextfield.setVisible(true);
		this.add(textUserPassword.jtextfield);
		
	}

	class ButtonListener implements ActionListener {
		private PersonlInfoUI currentPanel;
        public ButtonListener(PersonlInfoUI panel){
        	currentPanel=panel;
        }
		public void actionPerformed(ActionEvent e) {
			String passWord=textUserPassword.jtextfield.getText();
			if (passWord.equals("******")) {//无修改
				closeFrame();
			}else {//修改了密码
				UserVO myUser=MyFrame.getTheUser();//旧用户
				UserVO newUser=new UserVO(myUser.getUserID(), myUser.getUserName(), passWord, myUser.getTheJob(), myUser.getPowerLevel());
				ResultMessage result=userBLService.updUser(myUser, newUser);
				if (result==ResultMessage.SUCCESS) {
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
				}else {
					MyTipsFrame mtf=new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(mtf, StaticImage.backOfFail_Operation.getImage());
					mtf.getIni(myFailTipsPanel);
				}
			}
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfPersonalInfo.getImage(), 0, 0, null);
	}
}
