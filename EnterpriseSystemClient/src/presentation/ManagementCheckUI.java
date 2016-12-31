package presentation;

import inputUI.ManagementCheckInputUI;
import inputUI.ManagerBusinessProcessInputUI;
import inputUI.TimeInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tool.MyButton;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.StaticImage;
import vo.BusinessListVO;
import vo.DocumentVO;
import vo.SalesListVO;
import vo.WholeDocumentVO;
import aboutTree.BusinessListTable;
import aboutTree.BusinessProcessTable;
import aboutTree.SalesListsTable;

public class ManagementCheckUI extends JPanel {
//	private static final long serialVersionUID = 1L;
	// private static int JBO_SALESLIST=0;
	// private static int JBO_BUSINESSPROCESSLIST = 1;
	// private static int JBO_BUSINESSLIST=2;
	// private static int JBO_ACCOUNTCHECK=3; //导出
	// private static int JBO_TURNBACK=4; //返回上一级
	// private static int JBO_EXIT = 5; //退出登陆

	JPanel nextJpanel;// 即将跳转的panel
	private JButton[] jButtons = new JButton[6];// 存放按钮的数组
	private JTable myTable;
	private JScrollPane myScrollPane;
	public WholeDocumentVO wholeDocumentVO;
	public SalesListsTable salesListsTable;
	public BusinessProcessTable businessProcessTable;
	public BusinessListTable businessListTable;

	public ManagementCheckUI() {
		initialize();
		setLayout(null);
		salesListsTable = new SalesListsTable(this);
		businessProcessTable = new BusinessProcessTable(this);
		businessListTable = new BusinessListTable(this);
		myScrollPane = new JScrollPane();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);

		MyButton salesList = new MyButton(StaticImage.backOfSalesList, 31, 169,
				145, 57);
		this.add(salesList.jbutton);
		MyButton businessProcessList = new MyButton(
				StaticImage.backOfBusinessProcessList, 31, 268, 145, 57);
		this.add(businessProcessList.jbutton);
		MyButton businessList = new MyButton(StaticImage.backOfBusinessList,
				32, 363, 145, 57);
		this.add(businessList.jbutton);
		MyButton accountExport = new MyButton(StaticImage.backOfListDetail, 32,
				462, 145, 57);
		this.add(accountExport.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);

		jButtons[0] = salesList.jbutton;
		jButtons[1] = businessProcessList.jbutton;
		jButtons[2] = businessList.jbutton;
		jButtons[3] = accountExport.jbutton;
		jButtons[4] = turnBack.jbutton;
		jButtons[5] = exit.jbutton;
		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}

	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private ManagementCheckUI managementCheckUI;

		public ButtonsActionListener(int id, ManagementCheckUI myPanel) {
			buttonID = id;
			// mytree = jTree;
			managementCheckUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				MyInputFrame accountinput1 = new MyInputFrame();
				ManagementCheckInputUI newPanel = new ManagementCheckInputUI(
						managementCheckUI, accountinput1);
				accountinput1.getIni(newPanel);

				break;
			case 1:
				MyInputFrame accountinput2 = new MyInputFrame();
				ManagerBusinessProcessInputUI newPanel1 = new ManagerBusinessProcessInputUI(
						managementCheckUI, accountinput2);
				accountinput2.getIni(newPanel1);
				break;
			case 2:
				MyInputFrame timeinput = new MyInputFrame();
				TimeInputUI myInputUI2 = new TimeInputUI(timeinput);
				timeinput.getIni(myInputUI2);
				break;
			case 3:
				break;
			case 4:
				nextJpanel = new ManagerWholeUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			case 5:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			}
		}
	}

	// 销售明细表格
	public void showSaleListSearchInfo(ArrayList<SalesListVO> arr) {

		myTable = salesListsTable.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	// 经营查询表格
	public void showBusinessProcessSearchInfo(ArrayList<DocumentVO> arr) {

		myTable = businessProcessTable.SaleListTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	public void showBusinessListSearchInfo(ArrayList<BusinessListVO> arr) {

		myTable = businessListTable.SaleListTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfAccountCheckUI.getImage(), 0, 0, null);
	}

	public WholeDocumentVO getWholeDocumentVO() {
		return wholeDocumentVO;
	}

	public void setWholeDocumentVO(WholeDocumentVO wholeDocumentVO) {
		this.wholeDocumentVO = wholeDocumentVO;
	}


}
