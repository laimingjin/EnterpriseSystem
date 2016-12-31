package inputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import presentation.CustomerUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CustomerVO;
import enumClass.ResultMessage;

public class CustomerInputUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	// 每个按钮对应的编号
	private static int BUTTON_CANEL = 0;
	private static int BUTTON_SAVE = 1;
	private CustomerUI customerUI;
	private String customerType;
	private int customerID;
	JTextField[] mytTextFields;

	public CustomerInputUI(CustomerUI myPanel, String infoCustomer,int id,JFrame frame) {
		super(frame);
		customerType = infoCustomer;
		customerID=id;
		currentFrame=frame;
		customerUI = myPanel;
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		getButtonInit();
		getTextFieldInit();
	}

	private void getButtonInit() {// 初始化按钮
		MyButton jbu_cannel = new MyButton(StaticImage.backOfjbu_cancel, 359,
				288, 110, 34);
		MyButton jbu_save = new MyButton(StaticImage.backOfjbu_save, 203, 288,
				110, 34);
		JButton[] myButton = new JButton[] { jbu_cannel.jbutton,
				jbu_save.jbutton };
		for (int i = 0; i < myButton.length; i++) {
			myButton[i].addActionListener(new ButtonActionListener(i,this));
			myButton[i].setVisible(true);
			this.add(myButton[i]);
		}
	}

	private void getTextFieldInit() {
		MyTextField customerID = new MyTextField(180, 78, 155, 20);
		MyTextField customerAdress = new MyTextField(435, 78, 155, 20);
		MyTextField customerSort = new MyTextField(180, 118, 155, 20);
		MyTextField customerPostNum = new MyTextField(435, 118, 155, 20);
		MyTextField customerLevel = new MyTextField(180, 154, 155, 20);
		MyTextField customerEmail = new MyTextField(435, 154, 155, 20);
		MyTextField customerName = new MyTextField(180, 189, 155, 20);
		MyTextField customerReceive = new MyTextField(435, 189, 155, 20);
		MyTextField customerPhone = new MyTextField(180, 224, 155, 20);
		MyTextField customerSaleaman = new MyTextField(435, 224, 155, 20);

		customerSort.jtextfield.setText(customerType);
		customerSort.jtextfield.setEditable(false);// 设置不可输入
		customerID.jtextfield.setText(Integer.toString(this.customerID));
		customerID.jtextfield.setEditable(false);// 设置不可输入
		mytTextFields = new JTextField[] { customerID.jtextfield,
				customerSort.jtextfield, customerLevel.jtextfield,
				customerName.jtextfield, customerPhone.jtextfield,
				customerAdress.jtextfield, customerPostNum.jtextfield,
				customerEmail.jtextfield, customerReceive.jtextfield,
				customerSaleaman.jtextfield };

		for (int i = 0; i < mytTextFields.length; i++) {
			this.add(mytTextFields[i]);
		}
	}

	class ButtonActionListener implements ActionListener {
		private int buttonID;
        private CustomerInputUI currentPanel;
		public ButtonActionListener(int id,CustomerInputUI panel) {
			buttonID = id;
			currentPanel=panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				ResultMessage result=addCustomer();
				if (result==ResultMessage.SUCCESS) {//成功会关闭窗口
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
				}else {//失败
					MyTipsFrame mtf = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel=new MyFailTipsPanel(mtf, StaticImage.backOfCustomer_Exit.getImage());
					mtf.getIni(myFailTipsPanel);
				}
			}
		}
	}
private ResultMessage addCustomer(){
	int customerID = Integer.parseInt(mytTextFields[0].getText());
	String customerType = mytTextFields[1].getText();
	int customerRank = Integer.parseInt(mytTextFields[2].getText());
	String customerName = mytTextFields[3].getText();
	String telePhone = mytTextFields[4].getText();
	String customerAddress = mytTextFields[5].getText();
	int customerPostCode = Integer.parseInt(mytTextFields[6]
			.getText());
	String eMail = mytTextFields[7].getText();
	double receivableLimit = Double.parseDouble(mytTextFields[8]
			.getText());
	double receivableAmount = 0.0;
	double payableAmount = 0.0;
	String operator = mytTextFields[9].getText();
	CustomerVO newCustomer = new CustomerVO(customerID,
			customerType, customerRank, customerName, telePhone,
			customerAddress, customerPostCode, eMail,
			receivableLimit, receivableAmount, payableAmount,
			operator);
	ResultMessage addResultMessage=customerUI.addCustomer(newCustomer);
	return addResultMessage;//返回添加结果
}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfCustomer_Input.getImage(), 0, 0, null);
	}
}
