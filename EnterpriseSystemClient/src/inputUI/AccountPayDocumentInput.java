package inputUI;

/**
 *财务人员付款单的输入
 * 有增删改显示查看的功能
 * 单据中包含：单据编号（SKD-yyyyMMdd-xxxxx），客户（同时包含供应商和销售商），操作员（当前登录用户），转账列表，总额汇总。
 * 转账列表中的一项包含：银行账户，转账金额，备注。
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
import temp_business.CustomerBLService;
import temp_business.LoginBLService;
import temp_business.PaymentBLService;
import temp_business.UserBLService;
import temp_businessImp.CustomerBLImp;
import temp_businessImp.LoginBLImp;
import temp_businessImp.PaymentBLImp;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.AccountVO;
import vo.CustomerVO;
import vo.PaymentVO;
import vo.UserVO;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import enumClass.ResultMessage;

public class AccountPayDocumentInput extends SuperInputUI {

	//private static final long serialVersionUID = 1L;

	private AccountDocumentUI accountDocumentUI;

	private MyTextField textno, textyear, textmonth, textday, textnumber,
			textName, textprice, textnote, textnumber1, textName1, textprice1,
			textnote1, textnumber2, textName2, textprice2, textnote2,
			textnumber3, textName3, textprice3, textnote3, textcustomer,
			textoperater, texttotal;
	// 单据编号,年，月，日，行号，条目名，金额，备注，客户，操作员 ，总额
	PaymentBLService paymentBLService = new PaymentBLImp();
	CustomerBLService customerBLService = new CustomerBLImp();
	UserBLService userBLService = new UserBLImp();
	LoginBLService loginBLService = new LoginBLImp();
	private MyButton edit, save, documentCancel, choose;// 编辑,保存，取消
	private static int BUTTON_EDIT = 0;
	private static int BUTTON_SAVE = 1;
	private static int BUTTON_CUSTOMER = 2;
	private static int BUTTON_CANCEL = 3;

	public AccountPayDocumentInput(AccountDocumentUI myPanel, JFrame frame) {
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
		textno = new MyTextField(83, 55, 150, 20);
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
		textno.jtextfield.setText(paymentBLService.getNewID());
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
		textcustomer = new MyTextField(370, 270, 80, 22);
		this.add(textcustomer.jtextfield);
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
				BUTTON_CUSTOMER));
		(edit.jbutton).addActionListener(new ButtonListener(this, BUTTON_EDIT));

		// 时间，单据编号（SKD-yyyyMMdd-xxxxx），客户ID，客户（同时包含供应商和销售商）操作员ID，操作员（当前登录用户），总额汇总,已发送，已通过，已处理。
		(save.jbutton).addActionListener(new ButtonListener(this, BUTTON_SAVE));

		(documentCancel.jbutton).addActionListener(new ButtonListener(this,
				BUTTON_CANCEL));
	}

	// 鼠标监听
	class ButtonListener implements ActionListener {

		AccountPayDocumentInput currentPanel;
		int buttonID;

		public ButtonListener(AccountPayDocumentInput panel, int id) {
			currentPanel = panel;
			buttonID = id;
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
				// todo!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

				TransferList transferList = new TransferList();
				double totalPrice = 0;
				double transferPrice = 0;
				double transferPrice1 = 0;
				double transferPrice2 = 0;
				double transferPrice3 = 0;
				int tag = 0;
				String accountName = textName.jtextfield.getText();
				// double transferPrice=0;
				if (!accountName.equals("")) {
					transferPrice = Double.parseDouble(textprice.jtextfield
							.getText());
					if (transferPrice < 0) {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfSmallAmount.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
						tag = 0;
					} else {
						tag = 1;
						String remark = textnote.jtextfield.getText();
						TransferLineItem transferLineItem = new TransferLineItem(
								accountName, transferPrice, remark);
						transferList.addItem(transferLineItem);

						String accountName1 = textName1.jtextfield.getText();
						// double transferPrice1=0;
						if (!accountName1.equals("")) {
							transferPrice1 = Double
									.parseDouble(textprice1.jtextfield
											.getText());
							if (transferPrice1 < 0) {
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
								TransferLineItem transferLineItem1 = new TransferLineItem(
										accountName1, transferPrice1, remark1);
								transferList.addItem(transferLineItem1);

								String accountName2 = textName2.jtextfield
										.getText();
								// double transferPrice2=0;
								if (!accountName2.equals("")) {

									transferPrice2 = Double
											.parseDouble(textprice2.jtextfield
													.getText());
									if (transferPrice2 < 0) {
										tag = 0;
										MyTipsFrame myTipsFrame = new MyTipsFrame();
										MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
												myTipsFrame,
												StaticImage.backOfSmallAmount
														.getImage());
										myTipsFrame.getIni(myFailTipsPanel);
									} else {
										tag = 1;
										String remark2 = textnote2.jtextfield
												.getText();
										TransferLineItem transferLineItem2 = new TransferLineItem(
												accountName2, transferPrice2,
												remark2);
										transferList.addItem(transferLineItem2);

										String accountName3 = textName3.jtextfield
												.getText();
										// double transferPrice3=0;
										if (!accountName3.equals("")) {
											transferPrice3 = Double
													.parseDouble(textprice3.jtextfield
															.getText());
											if (transferPrice3 < 0) {
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
												String remark3 = textnote3.jtextfield
														.getText();
												TransferLineItem transferLineItem3 = new TransferLineItem(
														accountName3,
														transferPrice3, remark3);
												transferList
														.addItem(transferLineItem3);

											}
										}
									}
								}
							}
						}
					}
				}
				totalPrice = transferPrice + transferPrice1 + transferPrice2
						+ transferPrice3;
				String customer = textcustomer.jtextfield.getText();
				CustomerVO customerVO = customerBLService.find(customer);
				int customerID = customerVO.getCustomerID();

				String operater = textoperater.jtextfield.getText();
				UserVO operaterVO = userBLService.find(operater);
				int operaterID = operaterVO.getUserID();

				if (tag == 1) {
					PaymentVO newPaymentVO = new PaymentVO(date, no,
							customerID, customer, operaterID, operater,
							transferList, totalPrice, false, false, false);
					ResultMessage resultMessage = paymentBLService
							.addPayment(newPaymentVO);
					if (resultMessage.equals(ResultMessage.SUCCESS)) {
						accountDocumentUI.addAccountPayDocument(newPaymentVO);
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
			if (buttonID == BUTTON_CUSTOMER) {
				MyDocumentInputFrame myDocumentInputFrame = new MyDocumentInputFrame();
				CustomerChooseUI recCusChooseUI = new CustomerChooseUI(
						currentPanel, myDocumentInputFrame);
				myDocumentInputFrame.getIni(recCusChooseUI);
			}
			if (buttonID == BUTTON_CANCEL) {
				closeFrame();
			}
		}
	}

	public void addCustomer(CustomerVO customer) {
		textcustomer.jtextfield.setText(customer.getCustomerName());
	}

	public void addAccount(AccountVO account) {
		if (textName.jtextfield.getText().equals("")) {
			textName.jtextfield.setText(account.getAccountName());
		} else if (textName1.jtextfield.getText().equals("")) {
			textName1.jtextfield.setText(account.getAccountName());
		} else if (textName2.jtextfield.getText().equals("")) {
			textName2.jtextfield.setText(account.getAccountName());
		} else if (textName3.jtextfield.getText().equals("")) {
			textName3.jtextfield.setText(account.getAccountName());
		} else {
			MyTipsFrame myTipsFrame = new MyTipsFrame();
			MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(myTipsFrame,
					StaticImage.backOfIllegal.getImage());
			myTipsFrame.getIni(myFailTipsPanel);
		}
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfAccountPayDocumentInput.getImage(), 0, 0,
				null);
	}

}
