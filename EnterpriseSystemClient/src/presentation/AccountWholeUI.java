package presentation;

/**
 *财务人员的主界面
 *包括：期初建账，账户管理，账户查看，单据管理，系统管理
 * @author nancy
 * @version 1.0
 */
import inputUI.PersonlInfoUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import tool.MyButton;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.StaticImage;

public class AccountWholeUI extends JPanel {
// static final long serialVersionUID = 1L;
	private static int JBO_ACCOUNTINITIAL = 0;
	private static int JBO_ACCOUNTMANAGEMENT = 1;
	private static int JBO_ACCOUNTCHECK = 2;
	private static int JBO_ACCOUNTDOCUMENT = 3;
	private static int JBO_ACCOUNTDIARYCHECK = 4;
	private static int JBO_SYSTEMSET = 5;
	private static int JBO_EXIT = 6;
	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons;// 存放按钮的数组

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public AccountWholeUI() {
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton accountInitial = new MyButton(
				StaticImage.backOfAccountInitial, 116, 181, 87, 91);
		MyButton accountManagement = new MyButton(
				StaticImage.backOfAccountManagement, 232, 284, 93, 91);
		MyButton accountCheck = new MyButton(StaticImage.backOfAccountCheck,
				468, 290, 95, 86);
		MyButton accountDocument = new MyButton(
				StaticImage.backOfAccountDocument, 581, 180, 95, 86);
		MyButton accountDiaryCheck = new MyButton(
				StaticImage.backOfAccountDiaryCheck, 355, 390, 88, 92);
		MyButton systemSet = new MyButton(StaticImage.backOfSystemSet, 821,
				390, 88, 88);

		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 76, 50, 13);
		jButtons = new JButton[] { accountInitial.jbutton,
				accountManagement.jbutton, accountCheck.jbutton,
				accountDocument.jbutton, accountDiaryCheck.jbutton,
				systemSet.jbutton, exit.jbutton };

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].setVisible(true);
			this.add(jButtons[i]);
			jButtons[i].addActionListener(new ButtonsActionListener(i));
		}

	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		public ButtonsActionListener(int id) {
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {

			if (buttonID == JBO_ACCOUNTINITIAL) {
				nextJpanel = new AccountInitialUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_ACCOUNTMANAGEMENT) {
				nextJpanel = new AccountManagementUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_ACCOUNTCHECK) {
				nextJpanel = new AccountCheckUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}

			if (buttonID == JBO_ACCOUNTDOCUMENT) {
				nextJpanel = new AccountDocumentUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_ACCOUNTDIARYCHECK) {
				nextJpanel = new DairyCheckUI(true);
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_SYSTEMSET) {
				MyInputFrame myInputFrame = new MyInputFrame();
				PersonlInfoUI myInputUI = new PersonlInfoUI(myInputFrame);
				myInputFrame.getIni(myInputUI);

			}

			if (buttonID == JBO_EXIT) {
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}

		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountWhole.getImage(), 0, 0, null);
	}

}
