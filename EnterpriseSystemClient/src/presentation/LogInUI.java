package presentation;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import temp_business.LoginBLService;
import temp_businessImp.LoginBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.LoginVo;
import enumClass.POSITION;

public class LogInUI extends JPanel {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField textField_1;

	private JPanel nextJPanel;
	LoginBLService myloginBl;

	/**
	 * Create the application.
	 */
	public LogInUI() {
		myloginBl = new LoginBLImp();
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyFrame.FRAME_WIDTH, MyFrame.FRAME_HEIGHT);

		MyButton log = new MyButton(StaticImage.backOfbtn_LogIn, 402, 449, 242,
				48);
		this.add(log.jbutton);

		textField_1 = new JPasswordField(20);
		textField_1.setColumns(10);
		textField_1.setBounds(394, 289, 304, 48);
		textField_1.setOpaque(false);
		textField_1.setBorder(null);
		this.add(textField_1);
		textField = new JTextField(20);
		this.add(textField);
		textField.setBounds(393, 210, 304, 48);
		textField.setColumns(10);
		textField.setOpaque(false);
		textField.setBorder(null);

		(log.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String name = textField.getText();
				String password = textField_1.getText();
				LoginVo tempLogin = new LoginVo(name, password);
				POSITION result_Login = myloginBl.login(tempLogin);
				// 如果是库存管理人员
				if (result_Login == POSITION.COMMODITY_MANAGER) {
					nextJPanel = new CommodityWholeUI();
					MyFrame.getFrame().changePanel(nextJPanel);
					MyFrame.setTheUser(myloginBl.getUserVO());
				}// 如果是销售人员
				else if (result_Login == POSITION.SALE_MANAGER) {
					nextJPanel = new ImpSalerMainUI();
					MyFrame.getFrame().changePanel(nextJPanel);
					MyFrame.setTheUser(myloginBl.getUserVO());
				}// 如果是财务人员
				else if (result_Login == POSITION.FINANCIAL_MANAGER) {
					nextJPanel = new AccountWholeUI();
					MyFrame.getFrame().changePanel(nextJPanel);
					MyFrame.setTheUser(myloginBl.getUserVO());
				}
				// 如果是总经理
				else if (result_Login == POSITION.GENERAL_MANAGER) {
					nextJPanel = new ManagerWholeUI();
					MyFrame.getFrame().changePanel(nextJPanel);
					MyFrame.setTheUser(myloginBl.getUserVO());
				}// 如果是系统管理员
				else if (result_Login == POSITION.SYSTEM_MANAGER) {
					nextJPanel = new SystemWholeUI();
					MyFrame.getFrame().changePanel(nextJPanel);
					MyFrame.setTheUser(myloginBl.getUserVO());
				} else if (result_Login == POSITION.ADMIN_MISTAKE) {// 用户不存在

					MyTipsFrame mtf = new MyTipsFrame();
					MyFailTipsPanel mtPanel = new MyFailTipsPanel(mtf,
							StaticImage.backOfFail_login.getImage());
					mtf.getIni(mtPanel);

				} else if (result_Login == POSITION.PASSWORD_MISTAKE) { // 密码错误

					MyTipsFrame mtf = new MyTipsFrame();
					MyFailTipsPanel mtPanel = new MyFailTipsPanel(mtf,
							StaticImage.backOfFail_password.getImage());
					mtf.getIni(mtPanel);
					//
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfLogin.getImage(), 0, 0, null);
	}
}
