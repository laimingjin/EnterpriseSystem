package presentation;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import temp_business.CommodityGiftBLService;
import temp_businessImp.CommodityGiftBLImp;
import tool.MyButton;
import tool.MyFrame;
import tool.MyTextField;
import tool.StaticImage;
import vo.CommodityGiftVO;
import aboutTree.CommodityGiftTree;

public class CommodityGiftDealUI extends JPanel {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private JPanel nextJpanel;
	private JScrollPane myScrollPane;
	public String selectedSonSort;
	private JTable myTable;
	private CommodityGiftTree commodityGiftTree;
	JScrollPane pane = new JScrollPane();
	CommodityGiftBLService commodityGiftBLService = new CommodityGiftBLImp();

	public CommodityGiftDealUI() {
		initialize();
		setLayout(null);
		// 树
		commodityGiftTree = new CommodityGiftTree(this);

		pane.getViewport().add(commodityGiftTree.getCustomerTree());
		pane.setBounds(10, 120, 210, 410);
		this.add(pane);
		// 表格
		myScrollPane = new JScrollPane();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(1024, 565);
		MyButton exit = new MyButton(StaticImage.backOfExit, 965, 63, 50, 13);
		this.add(exit.jbutton);
		MyButton turnBack = new MyButton(StaticImage.backOfTurnBack, 907, 63,
				50, 13);
		this.add(turnBack.jbutton);
		MyButton refresh = new MyButton(StaticImage.backOfRefresh, 578, 82, 90,
				35);
		this.add(refresh.jbutton);
		MyButton deal = new MyButton(StaticImage.backOfDeal, 688, 82, 90, 35);
		this.add(deal.jbutton);
		MyTextField search = new MyTextField(785, 84, 200, 32);
		this.add(search.jtextfield);

		(exit.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextJpanel = new LogInUI();

				MyFrame.getFrame().changePanel(nextJpanel);
			}
		});
		(turnBack.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nextJpanel = new CommodityWholeUI();

				MyFrame.getFrame().changePanel(nextJpanel);
			}
		});

		(deal.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int deleteIndex2 = myTable.getSelectedRow();
				if (deleteIndex2 > -1) {
					String ID = (String) myTable.getValueAt(deleteIndex2, 2);
					CommodityGiftVO commodityGiftVO = commodityGiftBLService
							.getCommodityGiftByID(Integer.parseInt(ID));

					commodityGiftVO.setDealed(true);

					commodityGiftBLService.updataMessage(commodityGiftVO);

					// 调用BL的方法！！！！！！！！！！！！！！！！！！！！！！！！！！！
					// 然后可以refresh
					// 从未处理到已处理
				}

			}
		});
		(refresh.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CommodityGiftDealUI commodityGiftDealUI = new CommodityGiftDealUI();
				commodityGiftTree = new CommodityGiftTree(commodityGiftDealUI);

				pane.getViewport().add(commodityGiftTree.getCustomerTree());
				pane.updateUI();

			}
		});
	}

	public void showCommodityDocumentInfo() {
		myTable = commodityGiftTree.creatCommodityDocumentTable();
		myScrollPane.getViewport().add(myTable);
		myScrollPane.setBounds(220, 120, 790, 410);
		myScrollPane.setVisible(true);
		this.add(myScrollPane);
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		g.drawImage(StaticImage.backOfCommodityGiftUI.getImage(), 0, 0, null);
	}
}
