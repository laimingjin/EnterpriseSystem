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

public class ManagerWholeUI extends JPanel {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	// 每个按钮对应的常量

	private static int JBO_DiaryCheck = 0;
	private static int JBO_MANAGEMENTCHECK = 1;
	private static int JBO_PROMOTION = 2;
	private static int JBO_DOCUMENTPROCESS = 3;
	private static int JBO_SYSTEMSET = 4;
	private static int JBO_EXIT = 5;
	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[5];// 存放按钮的数组

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public ManagerWholeUI() {
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton diaryCheck = new MyButton(StaticImage.backOfDiaryCheck, 111,
				184, 88, 88);
		MyButton managementCheck = new MyButton(
				StaticImage.backOfManagementCheck, 232, 286, 88, 88);
		MyButton promotion = new MyButton(StaticImage.backOfPromotion, 466,
				288, 87, 83);
		MyButton documentProcess = new MyButton(
				StaticImage.backOfDocumentProcess, 585, 183, 83, 89);
		MyButton systemSet = new MyButton(StaticImage.backOfSystemSet, 821,
				390, 88, 88);
		MyButton exit = new MyButton(StaticImage.backOfExit, 964, 77, 50, 13);

		jButtons = new JButton[] { diaryCheck.jbutton, managementCheck.jbutton,
				promotion.jbutton, documentProcess.jbutton, systemSet.jbutton,
				exit.jbutton };
		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i));
			this.add(jButtons[i]);
		}
	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		public ButtonsActionListener(int id) {
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == JBO_DiaryCheck) {
				nextJpanel = new DairyCheckUI(false);
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_MANAGEMENTCHECK) {
				nextJpanel = new ManagementCheckUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_PROMOTION) {
				nextJpanel = new PromotionUI();
				MyFrame.getFrame().changePanel(nextJpanel);
			}
			if (buttonID == JBO_DOCUMENTPROCESS) {
				nextJpanel = new DocumentProcessUI();
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

		g.drawImage(StaticImage.backOfManagerWholeUI.getImage(), 0, 0, null);
	}

}
