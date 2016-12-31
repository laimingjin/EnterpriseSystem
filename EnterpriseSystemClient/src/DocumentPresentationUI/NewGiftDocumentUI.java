package DocumentPresentationUI;

import inputUI.SuperInputUI;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import temp_business.BusinessProcessListBLService;
import temp_business.CommodityGiftBLService;
import temp_businessImp.BusinessProcessListBLImp;
import temp_businessImp.CommodityGiftBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.MyFailTipsPanel;
import tool.MySuccessTipsPanel;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommodityGiftVO;
import businesslogic.Commodity;
import businesslogic.Customer;
import enumClass.ResultMessage;

public class NewGiftDocumentUI extends SuperInputUI{
//private static final long serialVersionUID = 1L;
private CommodityGiftVO commoditGiftVO=null;
private GiftDocumentUI currentPanel=null;
private Vector<String> columnName=new Vector<String>();//表头
private Vector<Vector<String>> tabelInfo;//具体内容
private JScrollPane myTabelPane=new JScrollPane();
private JTable commodityTable;
CommodityGiftBLService commodityGiftBLService=new CommodityGiftBLImp();
BusinessProcessListBLService bListBLService=new BusinessProcessListBLImp();
	public   NewGiftDocumentUI  (JFrame frame,CommodityGiftVO co) {
		super(frame);
		commoditGiftVO=co;
		this.setLayout(null);
		this.setSize(MyDocumentInputFrame.FRAME_WIDTH, MyDocumentInputFrame.FRAME_HEIGHT);
		creatImportTabel();
		getButtonInit();
	}

	private void getButtonInit(){//按钮初始化

		MyButton jbu_red=new MyButton(StaticImage.backOfOK, 318, 312,StaticImage.backOfJbu_Verify.getIconWidth() , StaticImage.backOfJbu_Verify.getIconHeight());
		jbu_red.jbutton.addActionListener(new ActionListener() {//监听	
			public void actionPerformed(ActionEvent e) {
				int deleteIndex=commodityTable.getSelectedRow();
			 String date=	commoditGiftVO.getDate();
	         long  ID=commoditGiftVO.getDocumentID();
	         Commodity commodity=commoditGiftVO.getCommodity();
	         Customer customer=commoditGiftVO.getCustomer();
		     String Quantitystring=(String) commodityTable.getValueAt(deleteIndex, 8);
			int quantity=Integer.parseInt(Quantitystring);
			
			//boolean isdealed=commoditGiftVO.isDealed();
				CommodityGiftVO newcommodityGiftVO=new CommodityGiftVO(date,ID,commodity,quantity,customer,true,true,false);
				try {
				ResultMessage resultMessage=	commodityGiftBLService.addCommodityGift(newcommodityGiftVO);
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
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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

		Vector<String> row_info=new Vector<String>();
		row_info.add(commoditGiftVO.getDate());
		row_info.add("赠送单");
		row_info.add(Long.toString(commoditGiftVO.getDocumentID()));
        row_info.add(commoditGiftVO.getCustomer().getCustomerName());
        row_info.add(commoditGiftVO.getCustomer().getTelePhone());
	   row_info.add(commoditGiftVO.getCommodity().getCommodityName());
	   row_info.add(commoditGiftVO.getCommodity().getCommodityModel());
	   row_info.add(Integer.toString(commoditGiftVO.getCommodity().getInventoryQuantity()));
	   row_info.add(Integer.toString(commoditGiftVO.getSendQuantity()));
	
	
		tabelInfo.add(row_info);		
	
	DefaultTableModel newTabelModel=new DefaultTableModel(tabelInfo,columnName){//设置不可修改
		private static final long serialVersionUID = 1L;
		@Override
		public boolean isCellEditable(int row, int column) {
		 if(column==8){
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
	columnName.add("日期");
	columnName.add("单据类型");
	columnName.add("单据编号");
	columnName.add("客户姓名");
	columnName.add("联系方式");
	
	columnName.add("商品名称");
	columnName.add("商品型号");
	columnName.add("商品系统数量");
	columnName.add("赠送数量");
	
}
protected void paintComponent(Graphics g) {
	super.paintComponent(g);
	g.drawImage(StaticImage.backOfRedRepeat.getImage(), 0, 0, null);
}
}


