package docDetailUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.DocumentProcessUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.PaymentVO;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;

public class PayDocDetailUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	private DocumentProcessUI callBackPanel;
	private PaymentVO paymentVO;
	private static int BUTTON_UPDATE = 0;
	private static int BUTTON_CANEL = 1;

	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable cashTable;

	public PayDocDetailUI(JFrame frame, DocumentProcessUI panel, PaymentVO temp) {
		super(frame);
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		callBackPanel = panel;
		paymentVO = temp;
		getButtonInit();
		creatTabel();
	}

	private void getButtonInit() {// 初始化按钮

		MyButton updateButton = new MyButton(StaticImage.backOfjbu_update, 288,
				308, StaticImage.backOfjbu_update.getIconWidth(),
				StaticImage.backOfjbu_update.getIconHeight());
		MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
				308, StaticImage.backOfjbu_cancel.getIconWidth(),
				StaticImage.backOfjbu_cancel.getIconHeight());
		JButton[] myButtons = new JButton[] { updateButton.jbutton , cancelButton.jbutton };
		for (int i = 0; i < myButtons.length; i++) {
			myButtons[i].addActionListener(new ButtonListener(i, this));
			myButtons[i].setVisible(true);
			this.add(myButtons[i]);
		}
	}

	/**
	 * 
	 * @author 小春 2014年12月21日下午5:09:46 EnterpriseSystem docDetailUI
	 *         CashDocDetailUI.java 内部类，监听鼠标
	 */
	class ButtonListener implements ActionListener {
		int buttonID;
		PayDocDetailUI currentPanel;

		public ButtonListener(int id, PayDocDetailUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_UPDATE) {
				updateDoc();
			}
			closeFrame();
		}
	}

	private void addColumn() {// 表头
		columnName.add("账户名");
		columnName.add("金额");
		columnName.add("备注");
	}

	private void creatTabel() {
		if (columnName.isEmpty()) {
			addColumn();
		}
		tabelInfo = new Vector<Vector<String>>();
		TransferList tempLis = paymentVO.getTransferList();// 条目
		ArrayList<TransferLineItem> transferLineItems = tempLis.getTheList();
		for (int i = 0; i < transferLineItems.size(); i++) {
			Vector<String> row_info = new Vector<String>();
			row_info.add(transferLineItems.get(i).getAccountName());
			row_info.add(Double.toString(transferLineItems.get(i)
					.getTransferPrice()));
			row_info.add(transferLineItems.get(i).getRemark());

			tabelInfo.add(row_info);
		}

		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 1) {
					return true;
				}
				return false;
			}
		};
		cashTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(cashTable);
		myTabelPane.setBounds(18, 45, 665, 230);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void updateDoc() {
		int index = cashTable.getSelectedRow();// 选择的行数
		ArrayList<TransferLineItem> transferLineItems = paymentVO
				.getTransferList().getTheList();// 条目
		TransferLineItem tempItem = new TransferLineItem(transferLineItems.get(
				index).getAccountName(), Double.parseDouble((String) cashTable
				.getValueAt(index, 1)), transferLineItems.get(index)
				.getRemark());
		transferLineItems.set(index, tempItem);

		TransferList tempList = new TransferList();
		for (int i = 0; i < transferLineItems.size(); i++) {
			tempList.addItem(transferLineItems.get(i));
		}

		PaymentVO newPayVO = new PaymentVO(paymentVO.getDate(),
				paymentVO.getDocumentID(), paymentVO.getCustomerID(),
				paymentVO.getCustomerName(), paymentVO.getUserID(),
				paymentVO.getCustomerName(), tempList,
				tempList.getTotalPrice(), paymentVO.isPass(),
				paymentVO.isSend(), paymentVO.isDealed());

		callBackPanel.UpadteDoc(newPayVO);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfDocProDetail.getImage(), 0, 0, null);
	}

}
