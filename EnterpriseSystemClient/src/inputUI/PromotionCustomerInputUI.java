package inputUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.PromotionUI;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityVO;
import vo.PromotionVO_Customer;
import businesslogic.Commodity;

import comChooseUI.ProComChooseUI;

public class PromotionCustomerInputUI extends SuperInputUI {

	//private static final long serialVersionUID = 1L;
	private JComboBox<String> level;
	JComboBox<String> kind;
	JComboBox<String> amount;
	MyTextField disount;
	MyTextField muber;
	MyTextField timeStart;
	MyTextField timeEnd;
	ArrayList<CommodityVO> commodityVOs=new ArrayList<CommodityVO>();
	private Vector<String> columnName=new Vector<String>();//表头
	private Vector<Vector<String>> tabelInfo;//具体内容
	private JScrollPane myTabelPane=new JScrollPane();
	private JTable promotionTable;
	PromotionUI callBackUi=new PromotionUI();
 	public PromotionCustomerInputUI(JFrame frame,PromotionUI panel) {
		super(frame);
		currentFrame = frame;
		callBackUi=panel;
		initialize();
		getTableInit();
		setLayout(null);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH,
				MyDocumentInputFrame.FRAME_HEIGHT);
//		MyTextField no = new MyTextField(313, 222, 222, 30);
//		this.add(no.jtextfield);
		level = new JComboBox<String>();
		level.addItem("1级"); // 添加下拉可选项目
		level.addItem("2级");
		level.addItem("3级");
		level.addItem("4级");
		level.addItem("5级");
		level.setSelectedItem("1级");// 默认是2014
		level.setFont(new Font("微软雅黑", 0, 16));
		level.setBounds(193,102, 80, 20);
		this.add(level);

		kind = new JComboBox<String>();
		kind.addItem("赠品"); // 添加下拉可选项目
		kind.addItem("价格折让");
		kind.addItem("赠送代金券");

		kind.setSelectedItem("赠品");// 默认是2014
		kind.setFont(new Font("微软雅黑", 0, 16));
		kind.setBounds(368, 102,80, 20);
		this.add(kind);
		
		amount=new JComboBox<String>();
		amount.addItem("5元");
		amount.addItem("10元");
		amount.addItem("20元");
		amount.addItem("50元");
		amount.addItem("100元");
		
		amount.setSelectedItem("5元");// 默认是5
		amount.setFont(new Font("微软雅黑", 0, 16));
		amount.setBounds(199, 143,80, 20);
		this.add(amount);
		
		 disount = new MyTextField(533, 105, 80, 20);
		this.add(disount.jtextfield);
		muber = new MyTextField(374, 143, 80,20);
		this.add(muber.jtextfield);
		timeStart=new MyTextField(184, 177, 80, 20);
		this.add(timeStart.jtextfield);
		timeEnd=new MyTextField(444, 177, 80, 20);
		this.add(timeEnd.jtextfield);
		
		MyButton jbu_choose = new MyButton(StaticImage.backOfjbu_choose, 552,
				144, StaticImage.backOfjbu_choose.getIconWidth(),
				StaticImage.backOfjbu_choose.getIconHeight());
		MyButton edit = new MyButton(StaticImage.backOfEdit, 159, 312, 100, 32);
		MyButton save = new MyButton(StaticImage.backOfSave, 316, 312, 100, 32);
		MyButton documentCancel = new MyButton(
				StaticImage.backOfDocumentCancel, 477, 312, 108, 32);

		this.add(edit.jbutton);
		(edit.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

			}
		});
        this.add(jbu_choose.jbutton);
        (jbu_choose.jbutton).addActionListener(new ButtonListener(this,1));
        
		this.add(save.jbutton);
		(save.jbutton).addActionListener(new ButtonListener(this,0));

		this.add(documentCancel.jbutton);
		(documentCancel.jbutton).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closeFrame();
			}
		});
	}
class ButtonListener implements ActionListener{
    private PromotionCustomerInputUI currentPanel;
    private int buttonID;
    public ButtonListener (PromotionCustomerInputUI panel,int index){
    	currentPanel=panel;
    	buttonID=index;
    }
	public void actionPerformed(ActionEvent e) {
if (buttonID==0) {
	savePro();
}else if (buttonID==1) {
	MyDocumentInputFrame myInputFrame=new MyDocumentInputFrame();
	ProComChooseUI proComChooseUI=new ProComChooseUI(currentPanel, myInputFrame);
	myInputFrame.getIni(proComChooseUI);
	}
}
	}

private  void savePro(){//保存生成的VO
	int size=commodityVOs.size();
	Commodity [] com =new Commodity[size];
	int [] number=new int[size];
	int customerlevel=1;
	double disount;
	int daijinquan=5;
	int amountDai=0;
	String nameString="针对不同用户制定促销策略";
	String time_Start;
	String time_End;
	for (int i = 0; i < size; i++) {
		com[i]=new Commodity(commodityVOs.get(i));
		number[i]=Integer.parseInt((String)promotionTable.getValueAt(i, 3));
	}
	String tempCustomer=(String)level.getSelectedItem();
	if (tempCustomer.endsWith("1级")) {
		customerlevel=1;
	}else if (tempCustomer.endsWith("2级")) {
		customerlevel=2;
	}else if (tempCustomer.endsWith("3级")) {
		customerlevel=3;
	}else if (tempCustomer.endsWith("4级")) {
		customerlevel=4;
	}else if (tempCustomer.endsWith("5级")) {
		customerlevel=5;
	}
	disount=Double.parseDouble(this.disount.jtextfield.getText());
	String tempDai=(String)amount.getSelectedItem();
	if (tempDai.endsWith("5元")) {
		daijinquan=5;
	}else if (tempDai.endsWith("10元")) {
		daijinquan=10;
	}else if (tempDai.endsWith("20元")) {
		daijinquan=20;
	}else if (tempDai.endsWith("50元")) {
		daijinquan=50;
	}else if (tempDai.endsWith("100元")) {
		daijinquan=100;
	}
	amountDai=Integer.parseInt(muber.jtextfield.getText());
//	if
//	PromotionVO_Customer tempCustomer2=new pro
	time_Start=timeStart.jtextfield.getText();
	time_End=timeEnd.jtextfield.getText();
	
	PromotionVO_Customer promotionVO_Customer=new PromotionVO_Customer(nameString, time_Start, time_End, customerlevel, com, number,daijinquan,amountDai, disount);
	callBackUi.addNewRow(promotionVO_Customer);
	
	MyTipsFrame mtf = new MyTipsFrame();
	MySuccessTipsPanel mtPanel = new MySuccessTipsPanel(mtf,
			StaticImage.backOfSuccess.getImage(),this);
	mtf.getIni(mtPanel);	
}
public void addCommodityInfo(CommodityVO commodity) {
	commodityVOs.add(commodity);
	Vector<String> tempVector=new Vector<String>();
	tempVector.add(Integer.toString(commodity.getCommodityID()));
	tempVector.add(commodity.getCommodityName());
	tempVector.add(commodity.getCommodityModel());
	tempVector.add("0");
	DefaultTableModel dtm=(DefaultTableModel)promotionTable.getModel();
	dtm.addRow(tempVector);
	promotionTable.updateUI();
}
private void addColumnName(){
	columnName.add("商品编号");
	columnName.add("商品名称");
	columnName.add("商品型号");
	columnName.add("商品数量");
}
private void getTableInit(){
	addColumnName();
	tabelInfo=new Vector<Vector<String>>();
	 DefaultTableModel newTabelModel=new DefaultTableModel(tabelInfo,columnName){//设置不可修改
			private static final long serialVersionUID = 1L;
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column==3) {
					return true;
				}
				return false;
			}
		};
		promotionTable=new JTable(newTabelModel);
		myTabelPane.getViewport().add(promotionTable);
		myTabelPane.setBounds(110, 208, 485,90);
		myTabelPane.setVisible(true);
		this.add(myTabelPane);
}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfPromotionCustomerInput.getImage(), 0, 0,
				null);
	}

}
