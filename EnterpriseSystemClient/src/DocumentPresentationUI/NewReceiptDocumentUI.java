package DocumentPresentationUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.BusinessProcessListBLService;
import temp_business.ReceiptBLService;
import temp_businessImp.BusinessProcessListBLImp;
import temp_businessImp.ReceiptBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.ReceiptVO;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import enumClass.ResultMessage;

public class NewReceiptDocumentUI extends SuperInputUI {
//	private static final long serialVersionUID = 1L;
	private ReceiptVO receiptVO = null;
	private ReceiptDocumentUI currentPanel = null;
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;
	BusinessProcessListBLService bListBLService = new BusinessProcessListBLImp();
	ReceiptBLService receiptBLService = new ReceiptBLImp();

	public NewReceiptDocumentUI(JFrame frame, ReceiptVO co) {
		super(frame);
		receiptVO = co;
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	private void getButtonInit() {// 按钮初始化

		MyButton jbu_red = new MyButton(StaticImage.backOfOK, 318, 312,
				StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_red.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {
						String date = receiptVO.getDate();
						String documentID = receiptVO.getDocumentID();
						int customerID = receiptVO.getCustomerID();
						String customerName = receiptVO.getCustomerName();
						int userID = receiptVO.getUserID();
						String userName = receiptVO.getUserName();
						TransferList transferList = receiptVO.getTransferList();
						double totalPrice = 0;
						for (int i = 0; i < transferList.getTheList().size(); i++) {
							String numberString = (String) commodityTable
									.getValueAt(i, 6);
							double price = Double.parseDouble(numberString);
							transferList.getTheList().get(i)
									.setTransferPrice(price);
							totalPrice = totalPrice + price;
						}
						ReceiptVO newrReceiptVO = new ReceiptVO(date,
								documentID, customerID, customerName, userID,
								userName, transferList, totalPrice, true, true,
								false);

						ResultMessage resultMessage = receiptBLService
								.addReceipt(newrReceiptVO);
						if (resultMessage.equals(ResultMessage.SUCCESS)) {
							MyTipsFrame mtf = new MyTipsFrame();
							MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(
									mtf, StaticImage.backOfSuccess.getImage(),
									currentPanel);
							mtf.getIni(mtPanel);

						} else {
							MyTipsFrame myTipsFrame = new MyTipsFrame();
							MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
									myTipsFrame,
									StaticImage.backOfFailToAddDocument
											.getImage());
							myTipsFrame.getIni(myFailTipsPanel);
						}
					}
				});
		jbu_red.jbutton.setVisible(true);
		this.add(jbu_red.jbutton);

		MyButton jbu_off = new MyButton(StaticImage.backOfOff, 659, 13, 26, 24);
		jbu_off.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {

						closeFrame();

					}
				});
		jbu_off.jbutton.setVisible(true);
		this.add(jbu_off.jbutton);

	}

	private void creatImportTabel() {// 进货商品表格
		if (columnName.isEmpty()) {
			addColumnName();
		}
		tabelInfo = new Vector<Vector<String>>();

		ArrayList<TransferLineItem> transferLineItems = receiptVO
				.getTransferList().getTheList();// 商品列表

		for (int i = 0; i < transferLineItems.size(); i++) {
			Vector<String> row_info = new Vector<String>();
			if (i == 0) {
				row_info.add(receiptVO.getDate());
				row_info.add("收款单");
				// 单据编号（SKD-yyyyMMdd-xxxxx），客户（同时包含供应商和销售商），
				// 操作员（当前登录用户），转账列表，总额汇总。转账列表中的一项包含：银行账户，转账金额，备注
				row_info.add(receiptVO.getDocumentID());
				row_info.add(receiptVO.getCustomerName());
				row_info.add(receiptVO.getUserName());
				row_info.add(transferLineItems.get(i).getAccountName());
				row_info.add(Double.toString(transferLineItems.get(i)
						.getTransferPrice()));
				row_info.add(transferLineItems.get(i).getRemark());
				row_info.add(Double.toString(receiptVO.getTotalPrice()));

				tabelInfo.add(row_info);
			} else {
				row_info.add("");
				row_info.add("");
				row_info.add("");
				row_info.add("");
				row_info.add("");

				row_info.add(transferLineItems.get(i).getAccountName());
				row_info.add(Double.toString(transferLineItems.get(i)
						.getTransferPrice()));
				row_info.add(transferLineItems.get(i).getRemark());

				row_info.add("");
				tabelInfo.add(row_info);

			}
		}

		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 6) {
					return true;
				} else {
					return false;
				}
			}
		};
		commodityTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(commodityTable);
		myTabelPane.setBounds(18, 45, 665, 230);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void addColumnName() {
		// 单据编号（SKD-yyyyMMdd-xxxxx），客户（同时包含供应商和销售商），
		// 操作员（当前登录用户），转账列表，总额汇总。转账列表中的一项包含：银行账户，转账金额，备注
		columnName.add("日期");
		columnName.add("单据类型");
		columnName.add("单据编号");
		columnName.add("客户");
		columnName.add("操作员");
		columnName.add("银行账户");
		columnName.add("转账金额");
		columnName.add("备注");
		columnName.add("总额");

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfRedRepeat.getImage(), 0, 0, null);
	}
}
