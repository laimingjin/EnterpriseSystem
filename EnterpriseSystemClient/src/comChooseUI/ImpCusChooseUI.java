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

import presentation.ImportUI;
import temp_business.CustomerBLService;
import temp_businessImp.CustomerBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.CustomerVO;

public class ImpCusChooseUI extends SuperInputUI{


	//private static final long serialVersionUID = 1L;
	private ImportUI callBackPanel;// 返回界面
	private JTable custDocTable;//数据表格
	private JScrollPane myTablePane=new JScrollPane();
	//按钮对应的编号
	private static int BUTTON_SAVE = 0;
	private static int BUTTON_SHOW=1;
	private static int BUTTON_CANEL =2;
     //用于存放单据
	private ArrayList<CustomerVO> customerVOs=new ArrayList<CustomerVO>();
	//表头
	private Vector<String> columnName=new Vector<String>();
	//BL接口
	CustomerBLService customerBLService=new CustomerBLImp();

	public ImpCusChooseUI(ImportUI panel, JFrame frame) {
		super(frame);
		callBackPanel = panel;
		getInit();
		getButtonInit();
		getTabelInit();
	}
	//初始化界面Panel
	private void getInit(){
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
			MyButton infoButton=new MyButton(StaticImage.backOfJbu_Info, 109,307,StaticImage.backOfJbu_Info.getIconWidth(),
					StaticImage.backOfJbu_Info.getIconHeight());
			MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
					307, StaticImage.backOfjbu_cancel.getIconWidth(),
					StaticImage.backOfjbu_cancel.getIconHeight());
			JButton[] myButtons = new JButton[] {saveButton.jbutton,infoButton.jbutton,
					cancelButton.jbutton };
			for (int i = 0; i < myButtons.length; i++) {
				myButtons[i].addActionListener(new ButtonListener(i,this));
				myButtons[i].setVisible(true);
				this.add(myButtons[i]);
			}
		}
		private void getTabelInit(){
			if(columnName.isEmpty()){
				addColumn();
			}
			ArrayList<CustomerVO> tempList=customerBLService.disPlayAll();
			for (int i = 0; i < tempList.size(); i++) {
				if (tempList.get(i).getCustomerType().equals("供应商")) {//遍历取得所有供应商
					customerVOs.add(tempList.get(i));
				}
			}
			
			Vector<Vector<String>> document_info=new Vector<Vector<String>>();
			Vector<String> row_info=new Vector<String>();
			CustomerVO cuatomer=null;
			if(!customerVOs.isEmpty()){
				for (int i = 0; i < customerVOs.size(); i++) {
					cuatomer=customerVOs.get(i);
					row_info=new Vector<String>();
					row_info.add(Integer.toString(cuatomer.getCustomerID()));//编号
					row_info.add(cuatomer.getCustomerName());//名称
					row_info.add(Integer.toString(cuatomer.getCustomerRank()));//级别
					row_info.add(cuatomer.getTelePhone());//号码
					row_info.add(cuatomer.getOperator());//操作员	
					
					document_info.add(row_info);
				}
			}
			DefaultTableModel newTabelModel=new DefaultTableModel(document_info,columnName){//设置不可修改
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			custDocTable=new JTable(newTabelModel);
			myTablePane.getViewport().add(custDocTable);
			myTablePane.setBounds(18, 45, 655, 230);
			myTablePane.setVisible(true);
			this.add(myTablePane);
		}
	private void addColumn(){
		
			columnName.add("客户编号");
			columnName.add("客户姓名");
			columnName.add("客户级别");
			columnName.add("客户号码");	
			columnName.add("常业务员");
	}
	class ButtonListener implements ActionListener {
		int buttonID;
		ImpCusChooseUI currentPanel;
		public ButtonListener(int id,ImpCusChooseUI panel) {
			buttonID = id;
			currentPanel=panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				int index = custDocTable.getSelectedRow();
				if (index>-1) {
					CustomerVO customerVO=customerVOs.get(index);		
					callBackPanel.addCustomer(customerVO);
					
					closeFrame();//成功之后关闭窗口		
				}
			}
			if (buttonID==BUTTON_SHOW) {
				//已废
			}
		}
	}
	//背景
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImportChoose.getImage(), 0, 0, null);
	}

}
