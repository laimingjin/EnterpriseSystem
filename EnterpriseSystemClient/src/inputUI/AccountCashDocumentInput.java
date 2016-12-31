package inputUI;

/**
 * 财务人员现金费用单的输入
 * 有增删改显示查看的功能
 * 单据中包含：单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员（当前登录用户），银行账户，条目清单，总额。
 * 条目清单中包括：条目名，金额，备注。
 * @author nancy
 * @version 1.0
 */
import inputUI.AccountReceiveDocumentInput.ButtonListener;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;

import comChooseUI.AccountChooseUI;
import comChooseUI.CustomerChooseUI;
import presentation.AccountDocumentUI;
import temp_business.CashListBLService;
import temp_business.LoginBLService;
import temp_business.UserBLService;
import temp_businessImp.CashListBLImp;
import temp_businessImp.LoginBLImp;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.AccountVO;
import vo.CashListVO;
import vo.UserVO;
import businesslogic.EntryLineItem;
import businesslogic.EntryList;
import enumClass.ResultMessage;

public class AccountCashDocumentInput extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	private AccountDocumentUI accountDocumentUI;
	private MyTextField textno, textyear, textmonth, textday, textnumber,
			textName, textprice, textnote, textnumber1, textName1, textprice1,
			textnote1, textnumber2, textName2, textprice2, textnote2,
			textnumber3, textName3, textprice3, textnote3, textoperater,
			textaccountName, texttotal;
	// 单据编号,年，月，日，行号，条目名，金额，备注，操作员 ，银行账户，总额
	CashListBLService cashListBLService = new CashListBLImp();
	LoginBLService loginBLService = new LoginBLImp();
	UserBLService userBLService = new UserBLImp();
	private MyButton edit, save, documentCancel, choose;// 编辑,保存，取消
	private static int BUTTON_EDIT = 0;
	private static int BUTTON_SAVE = 1;
	private static int BUTTON_CHOOSE = 2;
	private static int BUTTON_CANCEL = 3;

	public AccountCashDocumentInput(AccountDocumentUI myPanel, JFrame frame) {
		super(frame);
		accountDocumentUI = myPanel;
		initialize();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		textno = new MyTextField(83, 55, 90, 20);
		this.add(textno.jtextfield);
		textyear = new MyTextField(506, 55, 35, 20);
		this.add(textyear.jtextfield);
		textmonth = new MyTextField(562, 55, 35, 20);
		this.add(textmonth.jtextfield);
		textday = new MyTextField(621, 55, 35, 20);
		this.add(textday.jtextfield);

		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
		String dataNow = dateFormat.format(now);

		textyear.jtextfield.setText(dataNow.substring(0, 4));
		textmonth.jtextfield.setText(dataNow.substring(4, 6));
		textday.jtextfield.setText(dataNow.substring(6, 8));
		textno.jtextfield.setText(cashListBLService.getNewID());
		textno.jtextfield.setEditable(false);// 设置不可输入

		textnumber = new MyTextField(53, 121, 70, 30);
		this.add(textnumber.jtextfield);
		textnumber.jtextfield.setText(Integer.toString(1));// 设置行号
		textnumber.jtextfield.setEditable(false);
		textName = new MyTextField(148, 121, 142, 30);
		this.add(textName.jtextfield);
		textprice = new MyTextField(318, 121, 152, 30);
		this.add(textprice.jtextfield);
		textnote = new MyTextField(503, 121, 152, 30);
		this.add(textnote.jtextfield);

		textnumber1 = new MyTextField(53, 155, 70, 30);
		this.add(textnumber1.jtextfield);
		textnumber1.jtextfield.setText(Integer.toString(2));// 设置行号
		textnumber1.jtextfield.setEditable(false);
		textName1 = new MyTextField(148, 155, 142, 30);
		this.add(textName1.jtextfield);
		textprice1 = new MyTextField(318, 155, 152, 30);
		this.add(textprice1.jtextfield);
		textnote1 = new MyTextField(503, 155, 152, 30);
		this.add(textnote1.jtextfield);

		textnumber2 = new MyTextField(53, 189, 70, 30);
		this.add(textnumber2.jtextfield);
		textnumber2.jtextfield.setText(Integer.toString(3));// 设置行号
		textnumber2.jtextfield.setEditable(false);
		textName2 = new MyTextField(148, 189, 142, 30);
		this.add(textName2.jtextfield);
		textprice2 = new MyTextField(318, 189, 152, 30);
		this.add(textprice2.jtextfield);
		textnote2 = new MyTextField(503, 189, 152, 30);
		this.add(textnote2.jtextfield);

		textnumber3 = new MyTextField(53, 225, 70, 30);
		this.add(textnumber3.jtextfield);
		textnumber3.jtextfield.setText(Integer.toString(4));// 设置行号
		textnumber3.jtextfield.setEditable(false);
		textName3 = new MyTextField(148, 225, 142, 30);
		this.add(textName3.jtextfield);
		textprice3 = new MyTextField(318, 225, 152, 30);
		this.add(textprice3.jtextfield);
		textnote3 = new MyTextField(503, 225, 152, 30);
		this.add(textnote3.jtextfield);

		textoperater = new MyTextField(210, 270, 80, 22);
		this.add(textoperater.jtextfield);

		textoperater.jtextfield.setText(loginBLService.getUser());
		textoperater.jtextfield.setEditable(false);// 设置不可输入

		textaccountName = new MyTextField(400, 270, 80, 22);
		textaccountName.jtextfield.setEditable(false);
		this.add(textaccountName.jtextfield);
		texttotal = new MyTextField(555, 270, 80, 22);
		this.add(texttotal.jtextfield);
		edit = new MyButton(StaticImage.backOfEdit, 160, 312, 108, 32);
		save = new MyButton(StaticImage.backOfSave, 319, 312, 108, 32);
		documentCancel = new MyButton(StaticImage.backOfDocumentCancel, 477,
				312, 108, 32);
		choose = new MyButton(StaticImage.backOfBigChoose, 450, 271,
				StaticImage.backOfBigChoose.getIconWidth(),
				StaticImage.backOfBigChoose.getIconHeight());
		this.add(edit.jbutton);
		this.add(save.jbutton);
		this.add(documentCancel.jbutton);
		this.add(choose.jbutton);

		(choose.jbutton).addActionListener(new ButtonListener(this,
				BUTTON_CHOOSE));
		(edit.jbutton).addActionListener(new ButtonListener(this, BUTTON_EDIT));
		// 时间，单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员ID，操作员（当前登录用户），银行账户，总额,已发送，已通过，已处理。
		(save.jbutton).addActionListener(new ButtonListener(this, BUTTON_SAVE));

		(documentCancel.jbutton).addActionListener(new ButtonListener(this,
				BUTTON_CANCEL));
	}

	class ButtonListener implements ActionListener {

		AccountCashDocumentInput currentPanel;
		int buttonID;

		public ButtonListener(AccountCashDocumentInput panel, int id) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_EDIT) {
            MyDocumentInputFrame myDocumentInputFrame=new MyDocumentInputFrame();
            AccountChooseUI accountChooseUI=new AccountChooseUI(currentPanel, myDocumentInputFrame);
            myDocumentInputFrame.getIni(accountChooseUI);
			}
			if (buttonID == BUTTON_SAVE) {
				String year = textyear.jtextfield.getText();
				String month = textmonth.jtextfield.getText();
				String day = textday.jtextfield.getText();
				String date = year + month + day;
				String no = textno.jtextfield.getText();
				// TODO
				// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

				EntryList entryList = new EntryList();
				// String entryName;
				// double entryPrice;
				// String remark;
				double totalPrice = 0;
				double entryPrice = 0;
				double entryPrice1 = 0;
				double entryPrice2 = 0;
				double entryPrice3 = 0;
				int tag = 0;
				String entryName = textName.jtextfield.getText();

				if (!entryName.equals("")) {
					entryPrice = Double.parseDouble(textprice.jtextfield
							.getText());
					if (entryPrice < 0) {
						tag = 0;
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSmallAmount.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					} else {
						tag = 1;
						String remark = textnote.jtextfield.getText();
						EntryLineItem entryLineItem = new EntryLineItem(
								entryName, entryPrice, remark);
						entryList.addItem(entryLineItem);

						String entryName1 = textName1.jtextfield.getText();
						// double entryPrice1=0;
						if (!entryName1.equals("")) {
							entryPrice1 = Double
									.parseDouble(textprice1.jtextfield
											.getText());
							if (entryPrice1 < 0) {
								tag = 0;
								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame,
										StaticImage.backOfSmallAmount
												.getImage());
								myTipsFrame.getIni(myFailTipsPanel);
							} else {
								tag = 1;
								String remark1 = textnote1.jtextfield.getText();
								EntryLineItem entryLineItem1 = new EntryLineItem(
										entryName1, entryPrice1, remark1);
								entryList.addItem(entryLineItem1);

								String entryName2 = textName2.jtextfield
										.getText();
								// double entryPrice2=0;
								if (!entryName2.equals("")) {
									entryPrice2 = Double
											.parseDouble(textprice2.jtextfield
													.getText());
									if (entryPrice2 < 0) {
										tag = 0;
										MyTipsFrame myTipsFrame = new MyTipsFrame();
										MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
												myTipsFrame,
												StaticImage.backOfSmallAmount
														.getImage());
										myTipsFrame.getIni(myFailTipsPanel);
									} else {
										tag = 1;
										String remark2 = textnote1.jtextfield
												.getText();
										EntryLineItem entryLineItem2 = new EntryLineItem(
												entryName2, entryPrice2,
												remark2);
										entryList.addItem(entryLineItem2);

										String entryName3 = textName3.jtextfield
												.getText();
										// double entryPrice3=0;
										if (!entryName3.equals("")) {
											entryPrice3 = Double
													.parseDouble(textprice3.jtextfield
															.getText());
											if (entryPrice3 < 0) {
												tag = 0;
												MyTipsFrame myTipsFrame = new MyTipsFrame();
												MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
														myTipsFrame,
														StaticImage.backOfSmallAmount
																.getImage());
												myTipsFrame
														.getIni(myFailTipsPanel);
											} else {
												tag = 1;
												String remark3 = textnote1.jtextfield
														.getText();
												EntryLineItem entryLineItem3 = new EntryLineItem(
														entryName3,
														entryPrice3, remark3);
												entryList
														.addItem(entryLineItem3);
											}
										}
									}
								}
							}
						}
					}
				}
				totalPrice = entryPrice + entryPrice1 + entryPrice2
						+ entryPrice3;
				String operater = textoperater.jtextfield.getText();
				UserVO operaterVO = userBLService.find(operater);
				int operaterID = operaterVO.getUserID();

				String account = textaccountName.jtextfield.getText();
				if (tag == 1) {
					CashListVO newcashListVO = new CashListVO(date, no,
							operaterID, operater, account, entryList,
							totalPrice, false, false, false);

					ResultMessage resultMessage = cashListBLService
							.addCashList(newcashListVO);
					if (resultMessage.equals(ResultMessage.SUCCESS)) {
						accountDocumentUI.addAccountCashDocument(newcashListVO);

						MyTipsFrame mtf = new MyTipsFrame();
						MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(
								mtf, StaticImage.backOfSuccess.getImage(),
								currentPanel);
						mtf.getIni(mtPanel);
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFailToAddDocument.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				}
			}
			if (buttonID==BUTTON_CHOOSE) {
				MyDocumentInputFrame myDocumentInputFrame = new MyDocumentInputFrame();
				AccountChooseUI ChooseUI = new AccountChooseUI(
						currentPanel, myDocumentInputFrame);
				myDocumentInputFrame.getIni(ChooseUI);
			}
			if (buttonID == BUTTON_CANCEL) {
				closeFrame();
			}
		}
	}
public void addAccount(AccountVO account){
	textaccountName.jtextfield.setText(account.getAccountName());
}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfAccountCashDocumentInput.getImage(), 0,
				0, null);
	}

}
