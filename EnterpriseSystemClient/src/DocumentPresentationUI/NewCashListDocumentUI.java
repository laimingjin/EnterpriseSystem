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

import temp_business.BusinessProcessListBLService;
import temp_business.CashListBLService;
import temp_businessImp.BusinessProcessListBLImp;
import temp_businessImp.CashListBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CashListVO;
import businesslogic.EntryLineItem;
import businesslogic.EntryList;
import enumClass.ResultMessage;

public class NewCashListDocumentUI extends SuperInputUI{
//	private static final long serialVersionUID = 1L;
private CashListVO cashListVO=null;
private CashListDocumentUI currentPanel=null;
private Vector<String> columnName=new Vector<String>();//表头
private Vector<Vector<String>> tabelInfo;//具体内容
private JScrollPane myTabelPane=new JScrollPane();
private JTable commodityTable;
CashListBLService cashListBLService=new CashListBLImp();
BusinessProcessListBLService bListBLService=new BusinessProcessListBLImp();

	public NewCashListDocumentUI  (JFrame frame,CashListVO co) {
		
		super(frame);
		cashListVO=co;

		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH, MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	private void getButtonInit(){//按钮初始化
		
		

		MyButton jbu_red=new MyButton(StaticImage.backOfOK, 318, 312,StaticImage.backOfJbu_Verify.getIconWidth() , StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_red.jbutton.addActionListener(new ActionListener() {//监听	
			public void actionPerformed(ActionEvent e) {
				String date=cashListVO.getDate();
				String documentID=cashListVO.getDocumentID();
				int userID=cashListVO.getUserID();
				String userName=cashListVO.getUserName();
				String accountName=cashListVO.getAccountName();
				EntryList entryList=cashListVO.getEntryList();
				double totalPrice=0;
			for(int i=0;i<entryList.getTheList().size();i++){
				  String number=(String) commodityTable.getValueAt(i, 6);
				double price=Double.parseDouble(number);
				  entryList.getTheList().get(i).setEntryPrice(price);
				  totalPrice=totalPrice+price;
			}
			CashListVO newcashListVO=new CashListVO(date, documentID, userID, userName, accountName, entryList, totalPrice, true, true, false);
			
 ResultMessage resultMessage=cashListBLService.addCashList(newcashListVO);
 if(resultMessage.equals(ResultMessage.SUCCESS)){
	 MyTipsFrame mtf = new MyTipsFrame();
		MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
		mtf.getIni(mtPanel);	
			
	}else{
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
		MyButton jbu_off=new MyButton(StaticImage.backOfOff, 659, 13,26,24);
		jbu_off.jbutton.addActionListener(new ActionListener() {//监听	
			public void actionPerformed(ActionEvent e) {
		closeFrame();
	
			}
		});
		jbu_off.jbutton.setVisible(true);
		this.add(jbu_off.jbutton);
	
	}
private void creatImportTabel(){//进货商品表格
	if (columnName.isEmpty()) {
		addColumnName();
	}
	tabelInfo=new Vector<Vector<String>>();
	ArrayList<EntryLineItem> entryLineItems=cashListVO.getEntryList().getTheList();// 商品列表


	for(int i=0;i<entryLineItems.size();i++){
		Vector<String> row_info=new Vector<String>();
		if(i==0){
			row_info.add(cashListVO.getDate());
			row_info.add("现金费用单");
			//单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员（当前登录用户），银行账户，条目清单，总额。条目清单中包括：条目名，金额，备注。
			row_info.add(cashListVO.getDocumentID());
			row_info.add(cashListVO.getUserName());
			row_info.add(cashListVO.getAccountName());
			row_info.add(entryLineItems.get(i).getEntryName());
			row_info.add(Double.toString(entryLineItems.get(i).getEntryPrice()));
			row_info.add(entryLineItems.get(i).getRemark());
			row_info.add(Double.toString(cashListVO.getTotalPrice()));
			
			tabelInfo.add(row_info);		
		}else{
			row_info.add("");
			row_info.add("");
			//单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员（当前登录用户），银行账户，条目清单，总额。条目清单中包括：条目名，金额，备注。
			row_info.add("");
			row_info.add("");
			row_info.add("");
			row_info.add(entryLineItems.get(i).getEntryName());
			row_info.add(Double.toString(entryLineItems.get(i).getEntryPrice()));
			row_info.add(entryLineItems.get(i).getRemark());
			row_info.add("");
			
			tabelInfo.add(row_info);		
			
		}
	}
		
	DefaultTableModel newTabelModel=new DefaultTableModel(tabelInfo,columnName){//设置不可修改
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column==6){
				return true;
			}else{
				return false;
			}
		}
	};
	commodityTable=new JTable(newTabelModel);
	myTabelPane.getViewport().add(commodityTable);
	myTabelPane.setBounds(18, 45, 665, 230);
	myTabelPane.setVisible(true);
	this.add(myTabelPane);
}

private void addColumnName(){
	//单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员（当前登录用户），银行账户，条目清单，总额。条目清单中包括：条目名，金额，备注。（手动输入）。
	columnName.add("日期");
	columnName.add("单据类型");
	columnName.add("单据编号");
	columnName.add("操作员");
	columnName.add("银行账户");
	columnName.add("条目名");
	columnName.add("金额");
	columnName.add("备注");
	columnName.add("总额");
	
}
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(StaticImage.backOfRedRepeat.getImage(), 0, 0, null);
}
}



