package DocumentPresentationUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.BusinessProcessListBLService;
import temp_business.CommodityDocumentBLService;
import temp_businessImp.BusinessProcessListBLImp;
import temp_businessImp.CommodityDocumentBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityDocumentVO;
import enumClass.KindOfDocuments;

public class ProbelmDocumentUI extends SuperInputUI {
//	private static final long serialVersionUID = 1L;
	private CommodityDocumentVO commodityDocumentVO = null;
	ProbelmDocumentUI currentPanel = this;
	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable commodityTable;
	BusinessProcessListBLService bListBLService = new BusinessProcessListBLImp();
	CommodityDocumentBLService commodityDocumentBLService = new CommodityDocumentBLImp();

	public ProbelmDocumentUI(JFrame frame, CommodityDocumentVO co) {
		super(frame);
		commodityDocumentVO = co;
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
							int id = Integer.parseInt(ID);
							commodityDocumentBLService
									.output_CommodityDocument(
											"C://" + dataNow + "问题单.xls",
											commodityDocumentBLService
													.getCommodityDocumentByID(id));

							MyTipsFrame myTipsFrame = new MyTipsFrame();
							MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
									myTipsFrame, StaticImage.backOfSuccess
											.getImage());
							myTipsFrame.getIni(myFailTipsPanel);

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
										KindOfDocuments.COMMODITYDOCUMENT, ID);
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
								CommodityDocumentVO commodityDocumentVO = bListBLService
										.writeBackCopy_CommodityDocument(ID);

								MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
								NewProblemDocumentUI newProblemDocumentUI = new NewProblemDocumentUI(
										newFrame, commodityDocumentVO);

								newFrame.getIni(newProblemDocumentUI);

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

		Vector<String> row_info = new Vector<String>();
		row_info.add(commodityDocumentVO.getDate());
		row_info.add(commodityDocumentVO.getDocType());
		row_info.add(Integer.toString(commodityDocumentVO.getDocumentID()));

		row_info.add(commodityDocumentVO.getCommodity().getCommodityName());
		row_info.add(commodityDocumentVO.getCommodity().getCommodityModel());
		row_info.add(Integer.toString(commodityDocumentVO.getCommodity()
				.getInventoryQuantity()));
		row_info.add(Integer.toString(commodityDocumentVO.getRealQuantity()));

		tabelInfo.add(row_info);

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
		columnName.add("日期");
		columnName.add("单据类型");
		columnName.add("单据编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品系统数量");
		columnName.add("商品实际数量");

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfDocumentPresentation.getImage(), 0, 0,
				null);
	}
}
