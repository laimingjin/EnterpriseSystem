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

import temp_business.ExportBLService;
import temp_businessImp.ExportBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.SaleDocumentVO;
import businesslogic.Customer;
import businesslogic.SaleLineItem;
import businesslogic.SaleList;
import businesslogic.User;
import enumClass.ResultMessage;

public class NewSaleDocumentUI extends SuperInputUI {
//	private static final long serialVersionUID = 1L;
	private SaleDocumentVO saleDocumentVO = null;
	private SaleDocumentUI currentPanel;
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;
	ExportBLService exportBLService = new ExportBLImp();

	public NewSaleDocumentUI(JFrame frame, SaleDocumentVO co) {
		super(frame);
		saleDocumentVO = co;

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
						String theDate = saleDocumentVO.getTheDate();
						String documentID = saleDocumentVO.getDocumentID();
						Customer theCustomer = saleDocumentVO.getTheCustomer();
						String executive = saleDocumentVO.getExecutive();
						User theUser = saleDocumentVO.getTheUser();
						String warehouse = saleDocumentVO.getWarehouse();
						SaleList theList = saleDocumentVO.getTheList();
						String theMessage = saleDocumentVO.getTheMessage();
						double totalPriceBefore = 0;
						double theRebate = saleDocumentVO.getTheRebate();
						double theVoucher = saleDocumentVO.getTheVoucher();
						double totalPriceAfter = saleDocumentVO
								.getTotalPriceAfter();
						for (int i = 0; i < theList.getTheList().size(); i++) {
							String numberString = (String) commodityTable
									.getValueAt(i, 7);
							int number = Integer.parseInt(numberString);
							theList.getTheList().get(i).setTheNumber(number);
							totalPriceBefore = totalPriceBefore + number
									* theList.getTheList().get(i).getPrice();
						}
						SaleDocumentVO newSaleDocumentVO = new SaleDocumentVO(
								theDate, documentID, theCustomer, executive,
								warehouse, theUser, theList, theMessage,
								totalPriceBefore, theRebate, theVoucher,
								totalPriceAfter, true, true, false);
						ResultMessage resultMessage = exportBLService
								.addExportDraft(newSaleDocumentVO);
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
		ArrayList<SaleLineItem> saleLineItems = saleDocumentVO.getTheList()
				.getTheList();// 商品列表

		for (int i = 0; i < saleLineItems.size(); i++) {
			Vector<String> row_info = new Vector<String>();
			if (i == 0) {
				row_info.add(saleDocumentVO.getTheDate());
				if (saleDocumentVO.getDocumentID().startsWith("XS")) {
					row_info.add("销售单");
				} else if (saleDocumentVO.getDocumentID().startsWith("XSTH")) {
					row_info.add("销售退货单");
				} else {

				}
				// 供应商，仓库，操作员，入库商品列表，备注，总额合计。其中入库商品列表包含的信息有：商品编号，
				// 名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）。
				row_info.add(saleDocumentVO.getDocumentID());
				row_info.add(saleDocumentVO.getTheCustomer().getCustomerName());
				row_info.add(saleDocumentVO.getWarehouse());
				row_info.add(saleDocumentVO.getExecutive());// 业务员
				// row_info.add(saleDocumentVO.getTheUser().getUserName());

				// row_info.add(Integer.toString(saleLineItems.get(i).getTheCommodity().getCommodityID()));
				row_info.add(saleLineItems.get(i).getTheCommodity()
						.getCommodityName());
				row_info.add(saleLineItems.get(i).getTheCommodity()
						.getCommodityModel());
				row_info.add(Integer.toString(saleLineItems.get(i)
						.getTheNumber()));
				row_info.add(Double.toString(saleLineItems.get(i).getPrice()));
				row_info.add(Double.toString(saleLineItems.get(i).getTotal()));
				// row_info.add(saleLineItems.get(i).getTheMessage());//备注

				row_info.add(Double.toString(saleDocumentVO
						.getTotalPriceAfter()));
				// row_info.add(saleDocumentVO.getTheMessage());
				tabelInfo.add(row_info);
			} else {
				row_info.add("");
				row_info.add("");
				row_info.add("");
				row_info.add("");
				row_info.add("");
				row_info.add("");// 业务员
				// row_info.add("");

				// row_info.add(Integer.toString(saleLineItems.get(i).getTheCommodity().getCommodityID()));
				row_info.add(saleLineItems.get(i).getTheCommodity()
						.getCommodityName());
				row_info.add(saleLineItems.get(i).getTheCommodity()
						.getCommodityModel());
				row_info.add(Integer.toString(saleLineItems.get(i)
						.getTheNumber()));
				row_info.add(Double.toString(saleLineItems.get(i).getPrice()));
				row_info.add(Double.toString(saleLineItems.get(i).getTotal()));
				// row_info.add(saleLineItems.get(i).getTheMessage());//备注
				row_info.add("");
				// row_info.add("");
				tabelInfo.add(row_info);

			}
		}

		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 8) {
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
		// 供应商，仓库，操作员，入库商品列表，备注，总额合计。其中入库商品列表包含的信息有：商品编号，
		// 名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）。
		columnName.add("日期");
		columnName.add("单据类型");
		columnName.add("单据编号");
		columnName.add("供应商");
		columnName.add("仓库");
		columnName.add("业务员");
		// columnName.add("操作员");
		// columnName.add("商品编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品数量");
		columnName.add("商品单价");
		columnName.add("商品金额");
		// columnName.add("商品备注");

		columnName.add("总额");
		// columnName.add("备注");
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfRedRepeat.getImage(), 0, 0, null);
	}
}
