package presentation;

/**
 * 财务人员的期初建账的主界面
 *包括：期初建账和查看
 * @author nancy
 * @version 1.0
 */
import inputUI.AccountInitialTimeInput;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import temp_business.SetAccountBLService;
import temp_businessImp.SetAccountBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.SetAccountVO;
import aboutTree.AccountInitialTable;
import enumClass.ResultMessage;

public class AccountInitialUI extends JPanel {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static int JBO_ACCOUNTINITIAL = 0; // 期初建账
	@SuppressWarnings("unused")
	private static int JBO_ACCOUTINITIALCHECK = 1; // 查看
	@SuppressWarnings("unused")
	private static int JBO_TURNBACK = 2; // 返回上一级
	@SuppressWarnings("unused")
	private static int JBO_EXIT = 3; // 退出登陆

	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[4];// 存放按钮的数组
	SetAccountBLService setAccountBLService = new SetAccountBLImp();
	private JTable myTable;
	private JScrollPane myScrollPane;
	private AccountInitialTable accountInitialTable;

	public AccountInitialUI() {
		initialize();
		setLayout(null);
		accountInitialTable = new AccountInitialTable(this);
		myScrollPane = new JScrollPane();
	}

	/**
	 * Initialize the contents of the jpanel.
	 * 
	 * @see MyFrame
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton accountInitial = new MyButton(
				StaticImage.backOfAccountInitialButton, 30, 200, 135, 52);
		this.add(accountInitial.jbutton);
		MyButton accountInitialCheck = new MyButton(
				StaticImage.backOfAccountInitialCheckButton, 30, 399, 135, 52);
		this.add(accountInitialCheck.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);

		jButtons[0] = accountInitial.jbutton;
		jButtons[1] = accountInitialCheck.jbutton;
		jButtons[2] = turnBack.jbutton;
		jButtons[3] = exit.jbutton;

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}

	}

	/*
	 * 按钮监听内部类
	 */
	class ButtonsActionListener implements ActionListener {
		private int buttonID;
		private AccountInitialUI accountInitialUI;

		public ButtonsActionListener(int id, AccountInitialUI mypanel) {
			buttonID = id;
			accountInitialUI = mypanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
				String dataNow = dateFormat.format(now);
				try {
					ResultMessage resultMessage = setAccountBLService
							.addSetAccount(dataNow);
					if (resultMessage.equals(ResultMessage.SUCCESS)) {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSuccess.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case 1:

				MyInputFrame accountinput2 = new MyInputFrame();
				AccountInitialTimeInput newPanel = new AccountInitialTimeInput(
						accountInitialUI, accountinput2);
				accountinput2.getIni(newPanel);

				break;
			case 2:
				nextJpanel = new AccountWholeUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			case 3:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			}
		}
	}

	public void showSearchInfo(ArrayList<SetAccountVO> arr) {

		myTable = accountInitialTable.creatCommoditySearchTable(arr);

		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountInitialUI.getImage(), 0, 0, null);
	}

}
