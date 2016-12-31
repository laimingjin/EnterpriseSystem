package comChooseUI;

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

import presentation.SaleUI;
import temp_business.UserBLService;
import temp_businessImp.UserBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.UserVO;
import enumClass.POSITION;

public class SaleOpeChooseUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	private SaleUI callBackPanel;// 返回界面
	private JTable custDocTable;// 数据表格
	private JScrollPane myTablePane = new JScrollPane();
	// 按钮对应的编号
	private static int BUTTON_SAVE = 0;
	private static int BUTTON_CANEL = 1;
	// 用于存放单据
	private ArrayList<UserVO> userVOs = new ArrayList<UserVO>();
	// 表头
	private Vector<String> columnName = new Vector<String>();
	// BL接口
	UserBLService userBLService = new UserBLImp();

	public SaleOpeChooseUI(SaleUI panel, JFrame frame) {
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
		MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
				307, StaticImage.backOfjbu_cancel.getIconWidth(),
				StaticImage.backOfjbu_cancel.getIconHeight());
		JButton[] myButtons = new JButton[] { saveButton.jbutton,
				cancelButton.jbutton };
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
		ArrayList<UserVO> tempList = userBLService.dispAll();
		for (int i = 0; i < tempList.size(); i++) {
			if (tempList.get(i).getPostion() == POSITION.SALE_MANAGER
					|| tempList.get(i).getPostion() == POSITION.SUPER_SALE_MANAGER) {// 遍历取得所有销售商
				userVOs.add(tempList.get(i));
			}
		}

		Vector<Vector<String>> document_info = new Vector<Vector<String>>();
		Vector<String> row_info = new Vector<String>();
		UserVO user = null;
		if (!userVOs.isEmpty()) {
			for (int i = 0; i < userVOs.size(); i++) {
				user = userVOs.get(i);
				row_info = new Vector<String>();
				row_info.add(Integer.toString(user.getUserID()));// 编号
				row_info.add(user.getUserName());// 名称
				row_info.add(Integer.toString(user.getPowerLevel()));// 级别
				row_info.add(user.getTheJob());// 职位

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
		custDocTable = new JTable(newTabelModel);
		myTablePane.getViewport().add(custDocTable);
		myTablePane.setBounds(18, 45, 655, 230);
		myTablePane.setVisible(true);
		this.add(myTablePane);
	}

	private void addColumn() {

		columnName.add("编号");
		columnName.add("姓名");
		columnName.add("级别");
		columnName.add("职位");
	}

	class ButtonListener implements ActionListener {
		int buttonID;
		SaleOpeChooseUI currentPanel;

		public ButtonListener(int id, SaleOpeChooseUI panel) {
			buttonID = id;
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				int index = custDocTable.getSelectedRow();
				if (index > -1) {
					UserVO user = userVOs.get(index);
					callBackPanel.addOperator(user);

					closeFrame();// 成功之后关闭窗口
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
