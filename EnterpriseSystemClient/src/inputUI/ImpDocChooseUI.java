package inputUI;

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

import presentation.ImportUI;
import temp_business.ImportDocumentBLService;
import temp_businessImp.ImportDocumentImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.ImportDocumentVO;
import businesslogic.ImportList;
import docDetailUI.CommodityDetilUI;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

/**
 * @author 小春 2014年12月12日上午11:34:41 EnterpriseSystem InputUI ImpDocChooseUI.java
 */
public class ImpDocChooseUI extends SuperInputUI {

	// static final long serialVersionUID = 1L;
	private ImportUI callBackPanel;// 返回界面
	private JTable impDocTable;// 数据表格
	private JScrollPane myTablePane = new JScrollPane();
	// 按钮对应的编号
	private static int BUTTON_SAVE = 0;
	private static int BUTTON_SHOW = 1;
	private static int BUTTON_CANEL = 2;
	// 用于存放单据
	private ArrayList<ImportDocumentVO> documentVOs = new ArrayList<ImportDocumentVO>();
	// 表头
	private Vector<String> columnName = new Vector<String>();
	// BL接口
	ImportDocumentBLService documentBL = new ImportDocumentImp();

	public ImpDocChooseUI(ImportUI panel, JFrame frame) {
		super(frame);
		callBackPanel = panel;
		getInit();
		getButtonInit();
		getTabelInit();
	}

	// 初始化界面Panel
	private void getInit() {
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		this.setVisible(true);
	}

	// 初始化按钮
	private void getButtonInit() {
		MyButton saveButton = new MyButton(StaticImage.backOfjbu_save, 288,
				307, StaticImage.backOfjbu_save.getIconWidth(),
				StaticImage.backOfjbu_save.getIconHeight());
		MyButton infoButton = new MyButton(StaticImage.backOfJbu_Info, 109,
				307, StaticImage.backOfJbu_Info.getIconWidth(),
				StaticImage.backOfJbu_Info.getIconHeight());
		MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
				307, StaticImage.backOfjbu_cancel.getIconWidth(),
				StaticImage.backOfjbu_cancel.getIconHeight());
		JButton[] myButtons = new JButton[] { saveButton.jbutton,
				infoButton.jbutton, cancelButton.jbutton };
		for (int i = 0; i < myButtons.length; i++) {
			myButtons[i].addActionListener(new ButtonListener(i, this));
			myButtons[i].setVisible(true);
			this.add(myButtons[i]);
		}
	}

	private void getTabelInit() {
		if (columnName.isEmpty()) {
			addColumn();
		}
		documentVOs = documentBL.displayImport("JHD", StateOfDocument.DONE);// 取得已处理进货单
		// TODO 记得去注释

		Vector<Vector<String>> document_info = new Vector<Vector<String>>();
		Vector<String> row_info = new Vector<String>();
		ImportDocumentVO document = null;
		if (!documentVOs.isEmpty()) {
			for (int i = 0; i < documentVOs.size(); i++) {
				document = documentVOs.get(i);
				row_info = new Vector<String>();
				row_info.add(document.getDocumentID());// 编号
				row_info.add(document.getTheCustomer().getCustomerName());// 客户
				row_info.add(document.getWarehouse());// 仓库
				row_info.add(document.getTheUser().getUserName());// 操作员
				row_info.add(Double.toString(document.getTotalPrice()));// 总价
				row_info.add(document.getTheMessage());// 备注

				document_info.add(row_info);
			}
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(document_info,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		impDocTable = new JTable(newTabelModel);
		myTablePane.getViewport().add(impDocTable);
		myTablePane.setBounds(18, 45, 655, 230);
		myTablePane.setVisible(true);
		this.add(myTablePane);
	}

	private void addColumn() {

		columnName.add("单据编号");
		columnName.add("客户");
		columnName.add("仓库");
		columnName.add("操作员");
		columnName.add("总价");
		columnName.add("备注");
	}

	class ButtonListener implements ActionListener {
		int buttonID;
		ImpDocChooseUI currentPanel;

		public ButtonListener(int id, ImpDocChooseUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				int index = impDocTable.getSelectedRow();
				if (index > -1) {
					ImportDocumentVO myDocument = documentVOs.get(index);
					ResultMessage resultMessage = callBackPanel
							.creatBackDocument(myDocument);
					if (resultMessage == ResultMessage.SUCCESS) {
						closeFrame();
					} else {
						MyTipsFrame myTipsFrame = new MyTipsFrame();
						MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
								myTipsFrame,
								StaticImage.backOfFail_CommodityReturn
										.getImage());
						myTipsFrame.getIni(myFailTipsPanel);
					}
				}
			}
			if (buttonID == BUTTON_SHOW) {// 显示商品具体信息
				int index = impDocTable.getSelectedRow();
				if (index > -1) {
					ImportList tempList = documentVOs.get(index).getTheList();
					MyDocumentInputFrame newFrame = new MyDocumentInputFrame();
					CommodityDetilUI myCommodityDetilUI = new CommodityDetilUI(
							newFrame, tempList);
					newFrame.getIni(myCommodityDetilUI);
				}
			}
		}
	}

	// 背景
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImportChoose.getImage(), 0, 0, null);
	}
}
