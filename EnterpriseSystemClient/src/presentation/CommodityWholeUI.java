package presentation;

/**
 * 库存管理人员的主界面
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

public class CommodityWholeUI extends JPanel {
//	private static final long serialVersionUID = 1L;
	// private static int JBO_COMMODITYCHECK=0; //增加
	// private static int JBO_COMMODITYMANAGEMENT= 1; //删除
	// private static int JBO_MESSAGE=2; //修改
	// private static int JBO_COMMODITYDOCUMENT=3; //显示
	// private static int JBO_SYSTEMSET=4; //查找
	// private static int JBO_EXIT = 5; //退出登陆、
	private JButton[] jButtons = new JButton[6];// 存放按钮的数组
	private JPanel nextJpanel;

	// private JFrame frame;
	/**
	 * Create the application.
	 */
	public CommodityWholeUI() {
		setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton commodityCheck = new MyButton(
				StaticImage.backOfCommodityCheck, 116, 181, 87, 91);
		this.add(commodityCheck.jbutton);
		MyButton commodityManagement = new MyButton(
				StaticImage.backOfCommodityManagement, 232, 284, 93, 91);
		this.add(commodityManagement.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 76, 50, 13);
		this.add(exit.jbutton);
		MyButton commodityGift = new MyButton(StaticImage.backOfCommodityGift,
				468, 290, 95, 86);
		this.add(commodityGift.jbutton);
		MyButton commodityDocument = new MyButton(
				StaticImage.backOfAccountDocument, 582, 179, 95, 86);
		this.add(commodityDocument.jbutton);
		MyButton systemSet = new MyButton(StaticImage.backOfSystemSet, 821,
				390, 88, 88);
		this.add(systemSet.jbutton);
		jButtons[0] = commodityCheck.jbutton;
		jButtons[1] = commodityManagement.jbutton;
		jButtons[2] = commodityGift.jbutton;
		jButtons[3] = commodityDocument.jbutton;
		jButtons[4] = systemSet.jbutton;
		jButtons[5] = exit.jbutton;
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
			switch (buttonID) {
			case 0:
				nextJpanel = new CommodityCheckUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			case 1:
				nextJpanel = new CommodityUI();

				MyFrame.getFrame().changePanel(nextJpanel);
				break;

			case 2:
				nextJpanel = new CommodityGiftDealUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			case 3:
				nextJpanel = new CommodityDocumentUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;
			case 4:
				MyInputFrame myInputFrame = new MyInputFrame();
				PersonlInfoUI myInputInfoUI = new PersonlInfoUI(myInputFrame);
				myInputFrame.getIni(myInputInfoUI);

				break;
			case 5:
				nextJpanel = new LogInUI();

				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			}
		}
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodityWhole.getImage(), 0, 0, null);
	}

}
