package docDetailUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.PromotionVO_Customer;
import vo.PromotionVO_Package;
import vo.PromotionVO_Price;
import businesslogic.Commodity;

public class PromotionDetailUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;

	private Vector<String> columnName = new Vector<String>();// 表头
	private Vector<Vector<String>> tabelInfo;// 具体内容
	private JScrollPane myTabelPane = new JScrollPane();
	private JTable promotionTable;

	public PromotionDetailUI(JFrame frame, PromotionVO_Customer procustomer) {
		super(frame);
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatCustomerTabel(procustomer);
		getButtonInit();
	}

	public PromotionDetailUI(JFrame frame, PromotionVO_Package propackage) {
		super(frame);
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatPacksgeTable(propackage);
		getButtonInit();
	}

	public PromotionDetailUI(JFrame frame, PromotionVO_Price price) {
		super(frame);
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
		creatPriceTabel(price);
		getButtonInit();
	}

	private void getButtonInit() {// 按钮初始化
		MyButton jbu_verify = new MyButton(StaticImage.backOfJbu_Verify, 288,
				307, StaticImage.backOfJbu_Verify.getIconWidth(),
				StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_verify.jbutton.addActionListener(new ActionListener() {// 监听
					public void actionPerformed(ActionEvent e) {
						closeFrame();
					}
				});
		jbu_verify.jbutton.setVisible(true);
		this.add(jbu_verify.jbutton);
	}

	private void creatCustomerTabel(PromotionVO_Customer pro_customer) {
		Commodity[] temp = pro_customer.getGifts();
		int[] amount = pro_customer.getAmountOfGifts();
		JLabel level = new JLabel("客户级别"
				+ Integer.toString(pro_customer.getCustomerLevel()));
		level.setBounds(92, 60, 190, 30);
		level.setVisible(true);
		this.add(level);
		JLabel discount = new JLabel("折让"
				+ Double.toString(pro_customer.getDiscount()));
		discount.setBounds(400, 60, 190, 30);
		discount.setVisible(true);
		this.add(discount);
		addColumnName();
		tabelInfo = new Vector<Vector<String>>();
		for (int i = 0; i < temp.length; i++) {
			Vector<String> tempVector = new Vector<String>();
			tempVector.add(Integer.toString(temp[i].getCommodityID()));
			tempVector.add(temp[i].getCommodityName());
			tempVector.add(temp[i].getCommodityModel());
			tempVector.add(Integer.toString(amount[i]));

			tabelInfo.add(tempVector);
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		promotionTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(promotionTable);
		myTabelPane.setBounds(92, 100, 550, 200);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void creatPacksgeTable(PromotionVO_Package package1) {
		addColumnName();
		ArrayList<Commodity> tempList = package1.getCommoditys_NeedToBuy();
		ArrayList<Integer> tempAmout = package1.getCommoditys_Amount();
		tabelInfo = new Vector<Vector<String>>();
		for (int i = 0; i < tempList.size(); i++) {
			Vector<String> tempVector = new Vector<String>();
			tempVector.add(Integer.toString(tempList.get(i).getCommodityID()));
			tempVector.add(tempList.get(i).getCommodityName());
			tempVector.add(tempList.get(i).getCommodityModel());
			tempVector.add(Integer.toString(tempAmout.get(i)));

			tabelInfo.add(tempVector);
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(tabelInfo,
				columnName) {// 设置不可修改
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		promotionTable = new JTable(newTabelModel);
		myTabelPane.getViewport().add(promotionTable);
		myTabelPane.setBounds(92, 100, 550, 200);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
	}

	private void creatPriceTabel(PromotionVO_Price price) {
		JLabel amount = new JLabel("总金额" + Double.toString(price.getPrice()));
		amount.setBounds(92, 60, 190, 30);
		amount.setVisible(true);
		this.add(amount);

		JLabel munber = new JLabel("赠品数量"
				+ Integer.toString(price.getAmountOfGift()));
		munber.setBounds(400, 60, 190, 30);
		munber.setVisible(true);
		this.add(munber);

		JLabel name = new JLabel("赠品名称"
				+ price.getCommodity().getCommodityName());
		name.setBounds(92, 130, 190, 30);
		name.setVisible(true);
		this.add(name);

		JLabel model = new JLabel("赠品型号"
				+ price.getCommodity().getCommodityModel());
		model.setBounds(400, 130, 190, 30);
		model.setVisible(true);
		this.add(model);

		JLabel daijinquan = new JLabel("代金券金额"
				+ Integer.toString(price.getGift_Money()));
		daijinquan.setBounds(92, 200, 190, 30);
		daijinquan.setVisible(true);
		this.add(daijinquan);

		JLabel gifrNumber = new JLabel("代金券数额"
				+ Integer.toString(price.getAmountOfGift_Money()));
		gifrNumber.setBounds(400, 200, 190, 30);
		gifrNumber.setVisible(true);
		this.add(gifrNumber);

	}

	private void addColumnName() {
		columnName.add("商品编号");
		columnName.add("商品名称");
		columnName.add("商品型号");
		columnName.add("商品数量");
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfCommodityDetil.getImage(), 0, 0, null);
	}

}
