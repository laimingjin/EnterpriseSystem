package presentation;

/**
 *库存管理人员查看库存的界面
 * 包括：库存查看，库存快照，导出
 * @author nancy
 * @version 1.0
 */
import inputUI.CommodityCheckInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import temp_business.CommoditySnapShotBLService;
import temp_business.ExportBLService;
import temp_businessImp.CommoditySnapshotBLImp;
import temp_businessImp.ExportBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyFrame;
import tool.MyInputFrame;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommoditySnapshotVO;
import aboutTree.CommodityCheckNewTable;
import aboutTree.CommoditySnapshotTable;
import businesslogic.CommodityCheck;

public class CommodityCheckUI extends JPanel {

	// 每个按钮对应的常量
	//private static final long serialVersionUID = 1L;
	// private static int JBO_STOCKCheck=0;
	// private static int JBO_DAILYSTOCKCEHCK = 1;
	// private static int JBO_STOCKEXPORT=2;
	// private static int JBO_TURNBACK=3;
	// private static int JBO_EXIT = 4;
	JPanel nextJpanel;// 即将跳转的panel
	private JTable myTable;
	private JScrollPane myScrollPane;
	// private CommodityCheckTable commodityCheckTable;
	private CommodityCheckNewTable commodityCheckNewTable;
	private CommoditySnapshotTable commoditySnapshotTable;
	CommoditySnapShotBLService commoditySnapShotBLService = new CommoditySnapshotBLImp();
	ExportBLService exportBLService = new ExportBLImp();
	private JButton[] jButtons = new JButton[5];// 存放按钮的数组

	/**
	 * Create the application.
	 */
	public CommodityCheckUI() {
		setLayout(null);
		initialize();
		commodityCheckNewTable = new CommodityCheckNewTable(this);
		commoditySnapshotTable = new CommoditySnapshotTable(this);
		myScrollPane = new JScrollPane();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton stockCheck = new MyButton(StaticImage.backOfStockCheck, 28,
				177, 135, 52);
		this.add(stockCheck.jbutton);
		MyButton dailyStockCheck = new MyButton(
				StaticImage.backOfDailyStockCheck, 30, 317, 135, 52);
		this.add(dailyStockCheck.jbutton);
		MyButton stockExport = new MyButton(StaticImage.backOfStockExport, 30,
				448, 135, 52);
		this.add(stockExport.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		jButtons[0] = stockCheck.jbutton;
		jButtons[1] = dailyStockCheck.jbutton;
		jButtons[2] = stockExport.jbutton;
		jButtons[3] = turnBack.jbutton;
		jButtons[4] = exit.jbutton;

		for (int i = 0; i < jButtons.length; i++) {
			jButtons[i].addActionListener(new ButtonsActionListener(i, this));
		}
	}

	class ButtonsActionListener implements ActionListener {
		private int buttonID;

		private CommodityCheckUI commodityCheckUI;

		public ButtonsActionListener(int id, CommodityCheckUI myPanel) {
			buttonID = id;
			// mytree = jTree;
			commodityCheckUI = myPanel;
		}

		public void actionPerformed(ActionEvent e) {
			switch (buttonID) {
			case 0:
				MyInputFrame f = new MyInputFrame();
				CommodityCheckInputUI myInputUI = new CommodityCheckInputUI(
						commodityCheckUI, f);
				f.getIni(myInputUI);
				// 调用BL方法！！！！！！！！！！！！！！！！

				break;
			case 1:

				// 调用BL方法！！！！！！！！！！！！！！！！
				// 查看今日库存快照的。。。。。。。
				ArrayList<CommoditySnapshotVO> commoditySnapshotVOs = new ArrayList<CommoditySnapshotVO>();
				//
				CommoditySnapshotVO commoditySnapshotVO = commoditySnapShotBLService
						.LookCommoditySnapShot();
				commoditySnapshotVOs.add(commoditySnapshotVO);
				// 把得到的ArrayList<CommoditySnapshotVO>传入

				showCommoditySnapshotSearchInfo(commoditySnapshotVOs);

				break;
			case 2:
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
				String dataNow = dateFormat.format(now);
				commoditySnapShotBLService.output(
						"C://" + dataNow + "库存盘点.xls",
						commoditySnapShotBLService.LookCommoditySnapShot());
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame, StaticImage.backOfSuccess.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
				break;
			case 3:
				nextJpanel = new CommodityWholeUI();

				MyFrame.getFrame().changePanel(nextJpanel);

				break;

			case 4:
				nextJpanel = new LogInUI();
				MyFrame.getFrame().changePanel(nextJpanel);
				break;
			}
		}
	}

	public void showCommodityCheckListSearchInfo(ArrayList<CommodityCheck> arr) {

		myTable = commodityCheckNewTable.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	public void showCommoditySnapshotSearchInfo(
			ArrayList<CommoditySnapshotVO> arr) {

		myTable = commoditySnapshotTable.creatCommoditySearchTable(arr);
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodityCheckUI.getImage(), 0, 0, null);
	}
}
