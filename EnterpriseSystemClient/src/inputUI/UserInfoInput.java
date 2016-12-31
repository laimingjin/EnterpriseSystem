package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import presentation.UserManageUI;
import temp_business.UserBLService;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.UserVO;
import enumClass.ResultMessage;

public class UserInfoInput extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	MyTextField textUserID;
	MyTextField textUserJob;
	MyTextField textUserLevel;
	MyTextField textUserName;
	MyTextField textUserPassword;
	UserManageUI callBackPanel;
	String Job;
	//逻辑接口
	UserBLService myuserBlService;
	public UserInfoInput(UserManageUI panel, String job,JFrame frame) {
		super(frame);
		callBackPanel = panel;
		Job = job;
		myuserBlService=new UserBLImp();
		this.setSize(MyInputFrame.FRAME_WIDTH, MyInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		getPanelInit();
	}

	private void getPanelInit() {
		// UserVO myUser=MyFrame.getTheUser();
		MyButton verify = new MyButton(StaticImage.backOfVerify, 142, 427,
				StaticImage.backOfVerify.getIconWidth(),
				StaticImage.backOfVerify.getIconHeight());
		verify.jbutton.addActionListener(new ButtonListener(this));
		verify.jbutton.setVisible(true);
		this.add(verify.jbutton);

		// 不可修改的部分用Jlabel
		// 编号
		textUserID = new MyTextField(209, 136, 181, 39);
		int ID = myuserBlService.getNewID();// TODO 应该调用生成编号的方法
		textUserID.jtextfield.setText(Integer.toString(ID));
		textUserID.jtextfield.setEditable(false);
		textUserID.jtextfield.setVisible(true);
		this.add(textUserID.jtextfield);
		// 职位
		textUserJob = new MyTextField(209, 292, 181, 39);
		textUserJob.jtextfield.setText(Job);
		textUserJob.jtextfield.setEditable(false);
		textUserJob.jtextfield.setVisible(true);
		this.add(textUserJob.jtextfield);
		// 权限级别
		textUserLevel = new MyTextField(209, 343, 181, 39);
		textUserLevel.jtextfield.setVisible(true);
		this.add(textUserLevel.jtextfield);
		// 用户名
		textUserName = new MyTextField(209, 189, 181, 39);
		// textUserName.jtextfield.setText(myUser.getUserName());
		textUserName.jtextfield.setVisible(true);
		this.add(textUserName.jtextfield);
		// 密码
		textUserPassword = new MyTextField(209, 240, 181, 39);
		textUserPassword.jtextfield.setText("123456");
		textUserPassword.jtextfield.setEditable(false);
		textUserPassword.jtextfield.setVisible(true);
		this.add(textUserPassword.jtextfield);
	}

	class ButtonListener implements ActionListener {
		UserInfoInput currentPanel;
   public ButtonListener(UserInfoInput panel){
	   currentPanel=panel;
   }
		public void actionPerformed(ActionEvent e) {
			addUser();	
		}
	} 

	private void addUser() {//添加用户
		int userID = Integer.parseInt(textUserID.jtextfield.getText());
		String userName = textUserName.jtextfield.getText();
		String userPassword = textUserPassword.jtextfield.getText();
		String userJob = textUserJob.jtextfield.getText();
		int userLevel = Integer.parseInt(textUserLevel.jtextfield.getText());
		UserVO newUser = new UserVO(userID, userName, userPassword, userJob,
				userLevel);
       ResultMessage result= callBackPanel.addUser(newUser);
       if (result==ResultMessage.SUCCESS) {//添加成功
    	   MyTipsFrame mtf = new MyTipsFrame();
			MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),this);
			mtf.getIni(mtPanel);	
	}else {//添加失败
		MyTipsFrame mtf = new MyTipsFrame();
		MyFailTipsPanel mtPanel=new MyFailTipsPanel(mtf, StaticImage.backOfCustomer_Exit.getImage());
		mtf.getIni(mtPanel);	
	}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfPersonalInfo.getImage(), 0, 0, null);
	}

}
