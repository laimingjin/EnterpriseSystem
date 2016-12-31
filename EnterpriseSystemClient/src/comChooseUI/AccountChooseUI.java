package comChooseUI;

import inputUI.AccountCashDocumentInput;
import inputUI.AccountPayDocumentInput;
import inputUI.AccountReceiveDocumentInput;
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

import temp_business.AccountBLService;
import temp_businessImp.AccountBLImp;
import tool.MyButton;
import tool.MyDocumentInputFrame;
import tool.StaticImage;
import vo.AccountVO;

public class AccountChooseUI extends SuperInputUI{


	private static final long serialVersionUID = 1L;
	private SuperInputUI callBackPanel;// 返回界面
	private JTable accountDocTable;//数据表格
	private JScrollPane myTablePane=new JScrollPane();
	//按钮对应的编号
	private static int BUTTON_SAVE = 0;
	private static int BUTTON_CANEL =1;
     //用于存放单据
	private ArrayList<AccountVO> accountVOs=new ArrayList<AccountVO>();
	//表头
	private Vector<String> columnName=new Vector<String>();
	//BL接口
	AccountBLService accountBLService=new AccountBLImp();

	public AccountChooseUI(SuperInputUI panel, JFrame frame) {
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
			MyButton cancelButton = new MyButton(StaticImage.backOfjbu_cancel, 491,
					307, StaticImage.backOfjbu_cancel.getIconWidth(),
					StaticImage.backOfjbu_cancel.getIconHeight());
			JButton[] myButtons = new JButton[] {saveButton.jbutton,
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
			 accountVOs=accountBLService.displayAll();
			
			
			Vector<Vector<String>> document_info=new Vector<Vector<String>>();
			Vector<String> row_info=new Vector<String>();
			AccountVO  accountVO=null;
			if(!accountVOs.isEmpty()){
				for (int i = 0; i < accountVOs.size(); i++) {
					accountVO=accountVOs.get(i);
					row_info=new Vector<String>();
					row_info.add(accountVO.getAccountName());//名称
					row_info.add(Double.toString(accountVO.getAccountPrice()));//编号
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
			accountDocTable=new JTable(newTabelModel);
			myTablePane.getViewport().add(accountDocTable);
			myTablePane.setBounds(18, 45, 655, 230);
			myTablePane.setVisible(true);
			this.add(myTablePane);
		}
	private void addColumn(){
		
			columnName.add("客户名称");
			columnName.add("客户金额");
		
	}
	class ButtonListener implements ActionListener {
		int buttonID;
		AccountChooseUI currentPanel;
		public ButtonListener(int id,AccountChooseUI panel) {
			buttonID = id;
			currentPanel=panel;
		}

		public void actionPerformed(ActionEvent e) {
			if (buttonID == BUTTON_CANEL) {
				closeFrame();
			}
			if (buttonID == BUTTON_SAVE) {
				int index = accountDocTable.getSelectedRow();
				if (index>-1) {
					AccountVO accountVO=accountVOs.get(index);		
					if (callBackPanel instanceof AccountCashDocumentInput) {
						((AccountCashDocumentInput)callBackPanel).addAccount(accountVO);
					}else if(callBackPanel instanceof AccountPayDocumentInput){
						((AccountPayDocumentInput)callBackPanel).addAccount(accountVO);
					}else if(callBackPanel instanceof AccountReceiveDocumentInput){
						((AccountReceiveDocumentInput)callBackPanel).addAccount(accountVO);
					}
					

					closeFrame();//成功之后关闭窗口		
				}
			}
		}
	}
	//背景
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfImportChoose.getImage(), 0, 0, null);
	}

}
