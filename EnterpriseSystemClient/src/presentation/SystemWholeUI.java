package presentation;

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

public class SystemWholeUI extends JPanel {

	// 每个按钮对应的常量
//	private static final long serialVersionUID = 1L;
	private static int JBO_USERMANAGEMENT = 0;
	private static int JBO_SYSTEMSET = 1;
	private static int JBO_EXIT = 2;
	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[3];// 存放按钮的数组

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public SystemWholeUI() {
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton userManagement = new MyButton(
				StaticImage.backOfUserManagement, 228, 287, 93, 88);
		this.add(userManagement.jbutton);
		MyButton systemSet = new MyButton(StaticImage.backOfSystemSet, 821,
				390, 88, 88);
		this.add(systemSet.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 964, 77, 50, 13);
		this.add(exit.jbutton);
		jButtons[0] = userManagement.jbutton;
		jButtons[1] = systemSet.jbutton;
		jButtons[2] = exit.jbutton;

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i));
		}
	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		public ButtonsActionListener(int id) {
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == JBO_USERMANAGEMENT) {
				nextJpanel = new UserManageUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_SYSTEMSET) {
				MyInputFrame myInputFrame = new MyInputFrame();
				PersonlInfoUI myInputInfoUI = new PersonlInfoUI(myInputFrame);
				myInputFrame.getIni(myInputInfoUI);
			}

			if (buttonID == JBO_EXIT) {
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
		}
	}

	// 画背景什么的
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfSystemWholeUI.getImage(), 0, 0, null);
	}
}
