package DocumentPresentationUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import enumClass.KindOfDocuments;

public class ReceiptDocumentUI extends SuperInputUI {
	//private static final long serialVersionUID = 1L;
	private ReceiptVO receiptVO = null;
	private ReceiptDocumentUI currentPanel = this;
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;
	BusinessProcessListBLService bListBLService = new BusinessProcessListBLImp();
	ReceiptBLService receiptBLService = new ReceiptBLImp();

	public ReceiptDocumentUI(JFrame frame, ReceiptVO co) {
		super(frame);
		receiptVO = co;
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	private void getButtonInit() {// 按钮初始化
		MyButton jbu_export = new MyButton(
				StaticImage.backOfDocumentPresentationExport, 160, 314,
				StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_export.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {
						Date now = new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat(
								"yyyyMMdd");// 可以方便地修改日期格式
						String dataNow = dateFormat.format(now);
						int deleteIndex = commodityTable.getSelectedRow();
						if (deleteIndex > -1) {
							String ID = (String) commodityTable.getValueAt(
									deleteIndex, 2);
							try {
								receiptBLService.output_Receipt("C://"
										+ dataNow + "收款单.xls",
										receiptBLService.findByID(ID));

								MyTipsFrame myTipsFrame = new MyTipsFrame();
								MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
										myTipsFrame, StaticImage.backOfSuccess
												.getImage());
								myTipsFrame.getIni(myFailTipsPanel);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else {
							MyTipsFrame myTipsFrame = new MyTipsFrame();
							MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
									myTipsFrame, StaticImage.backOfFailToExport
											.getImage());
							myTipsFrame.getIni(myFailTipsPanel);
						}
					}
				});
		jbu_export.jbutton.setVisible(true);
		this.add(jbu_export.jbutton);

		MyButton jbu_red = new MyButton(
				StaticImage.backOfDocumentPresentationRed, 318, 312,
				StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_red.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {
						int deleteIndex = commodityTable.getSelectedRow();
						if (deleteIndex > -1) {
							String ID = (String) commodityTable.getValueAt(
									deleteIndex, 2);

							try {
								bListBLService.writeBack(
										KindOfDocuments.RECEIPT, ID);
								MyTipsFrame mtf = new MyTipsFrame();
								MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(
										mtf, StaticImage.backOfSuccess
												.getImage(), currentPanel);
								mtf.getIni(mtPanel);
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						} else {
							MyTipsFrame myTipsFrame = new MyTipsFrame();
							MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
									myTipsFrame, StaticImage.backOfFailToRed
											.getImage());
							myTipsFrame.getIni(myFailTipsPanel);
						}
					}
				});
		jbu_red.jbutton.setVisible(true);
		this.add(jbu_red.jbutton);

		MyButton jbu_redRepeat = new MyButton(
				StaticImage.backOfDocumentPresentationRedRepeat, 478, 313,
				StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_redRepeat.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {

						int deleteIndex = commodityTable.getSelectedRow();
						if (deleteIndex > -1) {
							String ID = (String) commodityTable.getValueAt(
									deleteIndex, 2);

							try {
								ReceiptVO receiptVO = bListBLService
										.writeBackCopy_Receipt(ID);
								MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
								NewReceiptDocumentUI newReceiptDocumentUI = new NewReceiptDocumentUI(
										newFrame, receiptVO);

								newFrame.getIni(newReceiptDocumentUI);

							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else {
							MyTipsFrame myTipsFrame = new MyTipsFrame();
							MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
									myTipsFrame,
									StaticImage.backOfFailToRedRepeat
											.getImage());
							myTipsFrame.getIni(myFailTipsPanel);
						}
					}
				});
		jbu_redRepeat.jbutton.setVisible(true);
		this.add(jbu_redRepeat.jbutton);
		MyButton jbu_off = new MyButton(StaticImage.backOfOff, 659, 13, 26, 24);
		jbu_off.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {

						currentPanel.closeFrame();

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
				return false;
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
		g.drawImage(StaticImage.backOfDocumentPresentation.getImage(), 0, 0,
				null);
	}
}
