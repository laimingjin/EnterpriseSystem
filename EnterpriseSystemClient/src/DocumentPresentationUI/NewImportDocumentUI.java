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
import temp_business.ImportDocumentBLService;
import temp_businessImp.BusinessProcessListBLImp;
import temp_businessImp.ImportDocumentImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.ImportDocumentVO;
import businesslogic.Customer;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.User;
import enumClass.ResultMessage;

public class NewImportDocumentUI extends SuperInputUI{
//	private static final long serialVersionUID = 1L;
private ImportDocumentVO importDocumentVO=null;
private  ImportDocumentUI currentPanel=null;
private Vector<String> columnName=new Vector<String>();//表头
private Vector<Vector<String>> tabelInfo;//具体内容
private JScrollPane myTabelPane=new JScrollPane();
private JTable commodityTable;
BusinessProcessListBLService bListBLService=new BusinessProcessListBLImp();
ImportDocumentBLService importDocumentBLService=new ImportDocumentImp();
	public   NewImportDocumentUI (JFrame frame,ImportDocumentVO co) {
		super(frame);
		importDocumentVO=co;
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH, MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	private void getButtonInit(){//按钮初始化

		MyButton jbu_red=new MyButton(StaticImage.backOfOK, 318, 312,StaticImage.backOfJbu_Verify.getIconWidth() , StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_red.jbutton.addActionListener(new ActionListener() {//监听	
			public void actionPerformed(ActionEvent e) {
				String theDate=importDocumentVO.getTheDate();
				String documentID=importDocumentVO.getDocumentID();
				Customer theCustomer=importDocumentVO.getTheCustomer();
				 String warehouse=importDocumentVO.getWarehouse();
				 User theUser=importDocumentVO.getTheUser();
				 ImportList theList=importDocumentVO.getTheList();
				 double totalPrice=0;
				 for(int i=0;i<theList.getImportLineList().size();i++){
					 String numberString=(String) commodityTable.getValueAt(i, 7);
					 int number=Integer.parseInt(numberString);
					theList.getImportLineList().get(i).setTheNumber(number);
					totalPrice=totalPrice+number*theList.getImportLineList().get(i).getPrice();
				 }
				 
				 String theMessage=importDocumentVO.getTheMessage();
			
				 ImportDocumentVO newImportDocumentVO=new ImportDocumentVO(theDate, documentID, theCustomer, warehouse, theUser, theList, theMessage, totalPrice, true, true, false);
			ResultMessage resultMessage=importDocumentBLService.addImportDraft(newImportDocumentVO);
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
	ArrayList<ImportLineItem> importLineItems=  importDocumentVO.getTheList().getImportLineList();

	for(int i=0;i<importLineItems.size();i++){
		Vector<String> row_info=new Vector<String>();
		if(i==0){
			row_info.add(importDocumentVO.getTheDate());
			if(importDocumentVO.getDocumentID().startsWith("JH")){
				row_info.add("进货单");
			}else if(importDocumentVO.getDocumentID().startsWith("JHTH")){
				row_info.add("进货退货单");	
			}else{
				
			}
			//单据编号,供应商，仓库，操作员，入库商品列表，备注，总额合计。其中入库商品列表包含的信息有：商品编号，
			//名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）。
			
			row_info.add(importDocumentVO.getDocumentID());
		    row_info.add(importDocumentVO.getTheCustomer().getCustomerName());
		    row_info.add(importDocumentVO.getWarehouse());
		 //   row_info.add(importDocumentVO.getTheUser().getUserName());
		 
		//    row_info.add(Integer.toString(importLineItems.get(i).getTheCommodity().getCommodityID()));
		    row_info.add(importLineItems.get(i).getTheCommodity().getCommodityName());
		    row_info.add(importLineItems.get(i).getTheCommodity().getCommodityModel());
		    row_info.add(Integer.toString(importLineItems.get(i).getTheNumber()));
		    row_info.add(Double.toString(importLineItems.get(i).getPrice()));
		    row_info.add(Double.toString(importLineItems.get(i).getTotal()));
		//    row_info.add(importLineItems.get(i).getTheMessage());//备注
		   
		    row_info.add(Double.toString(importDocumentVO.getTotalPrice()));
		 //   row_info.add(importDocumentVO.getTheMessage());
			
		}else{
			row_info.add("");
			row_info.add("");
			row_info.add("");
		    row_info.add("");
		    row_info.add("");
		//    row_info.add("");
		 
		//    row_info.add(Integer.toString(importLineItems.get(i).getTheCommodity().getCommodityID()));
		    row_info.add(importLineItems.get(i).getTheCommodity().getCommodityName());
		    row_info.add(importLineItems.get(i).getTheCommodity().getCommodityModel());
		    row_info.add(Integer.toString(importLineItems.get(i).getTheNumber()));
		    row_info.add(Double.toString(importLineItems.get(i).getPrice()));
		    row_info.add(Double.toString(importLineItems.get(i).getTotal()));
		 //   row_info.add(importLineItems.get(i).getTheMessage());//备注
		   
		    row_info.add("");	
		 //   row_info.add("");	
			
		}
		tabelInfo.add(row_info);	
	}
		
	DefaultTableModel newTabelModel=new DefaultTableModel(tabelInfo,columnName){//设置不可修改
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
			if(column==7){
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
	//单据编号,供应商，仓库，操作员，入库商品列表，备注，总额合计。其中入库商品列表包含的信息有：商品编号，
	//名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）。
	
	columnName.add("日期");
	columnName.add("单据类型");
	columnName.add("单据编号");
	columnName.add("供应商");
	columnName.add("仓库");
//	columnName.add("操作员");
//	columnName.add("商品编号");
	columnName.add("商品名称");
	columnName.add("商品型号");
	columnName.add("商品数量");
	columnName.add("商品单价");
	columnName.add("商品金额");
//	columnName.add("商品备注");

	columnName.add("总额");
//	columnName.add("备注");
	
}
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(StaticImage.backOfRedRepeat.getImage(), 0, 0, null);
}
}


