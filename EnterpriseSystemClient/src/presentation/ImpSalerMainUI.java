package presentation;

import inputUI.PersonlInfoUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import tool.MyFrame;
import tool.MyInputFrame;
import tool.StaticImage;

public class ImpSalerMainUI extends JPanel {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;

	// 每个按钮对应的常量
	/*
	 * private static int JBO_CUSTMAG = 0; private static int JBO_IMFO = 1;
	 * private static int JBO_IMPMAG = 2; private static int JBO_SALEMAG = 3;
	 * private static int JBO_SYSTSET = 4; private static int JBO_VOUCHER = 5;
	 * private static int JBO_EXIT = 6;// 退出按钮是千年吊车尾
	 */
	public ImpSalerMainUI() {
		this.setSize(1024, 565);
		setLayout(null);
		this.getButtonsInit();
	}

	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons;// 存放按钮的数组

	// 按钮的大小
	private static int[] WIDTH_BUTTONS;
	private static int[] HEIGHT_BUTTONS;
	static {
		WIDTH_BUTTONS = new int[] { StaticImage.JboCustMag.getIconWidth(),
			
				StaticImage.JboImpMag.getIconWidth(),
				StaticImage.JboSaleMag.getIconWidth(),
				StaticImage.JboSystSet.getIconWidth(),
				StaticImage.JboVoucher.getIconWidth(),
				StaticImage.backOfExit.getIconWidth() };
		HEIGHT_BUTTONS = new int[] { StaticImage.JboCustMag.getIconHeight(),
			
				StaticImage.JboImpMag.getIconHeight(),
				StaticImage.JboSaleMag.getIconHeight(),
				StaticImage.JboSystSet.getIconHeight(),
				StaticImage.JboVoucher.getIconHeight(),
				StaticImage.backOfExit.getIconHeight() };
	}

	// 按钮位置
	private static int[] X_BUTTONS;
	private static int[] Y_BUTTONS;
	static {
		X_BUTTONS = new int[] { 117,  237, 587, 822, 356, 965 };
		Y_BUTTONS = new int[] { 179,  287, 180, 384, 385, 76 };
	}

	private void getButtonsInit() {
		jButtons = new JButton[] { new JButton(), new JButton(),
				new JButton(), new JButton(), new JButton(), new JButton() };
		jButtons[0].setIcon(StaticImage.JboCustMag);
		
		jButtons[1].setIcon(StaticImage.JboImpMag);
		jButtons[2].setIcon(StaticImage.JboSaleMag);
		jButtons[3].setIcon(StaticImage.JboSystSet);
		jButtons[4].setIcon(StaticImage.JboVoucher);
		jButtons[5].setIcon(StaticImage.backOfExit);
		
		for (int i = 0; i < jButtons.length; i++) {
			
			jButtons[i].setBounds(X_BUTTONS[i], Y_BUTTONS[i], WIDTH_BUTTONS[i],
					HEIGHT_BUTTONS[i]);
			jButtons[i].setBorderPainted(false);// 设置无边框
			jButtons[i].setContentAreaFilled(false);// 设置背景透明
			jButtons[i].addActionListener(new ButtonsActionListener(i));

			this.add(jButtons[i]);
			jButtons[i].setVisible(true);
		}
	}

	/**
	 * 内部类 实现按钮监听
	 * 
	 * @author 小春 2014年11月28日
	 */
	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		public ButtonsActionListener(int id) {
			buttonID = id;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				nextJpanel = new CustomerUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			
			case 1:
				nextJpanel = new ImportUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			case 2:
				nextJpanel = new SaleUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			case 3:
				MyInputFrame myInputFrame = new MyInputFrame();
				PersonlInfoUI myInputInfoUI=new PersonlInfoUI(myInputFrame);
				myInputFrame.getIni(myInputInfoUI);
				break;
			case 4:
				nextJpanel=new ImpSaleDocumentUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			case 5:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			}
		}
	}

	// 画背景什么的
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.ImpSaleMain.getImage(), 0, 0, null);
	}
}
