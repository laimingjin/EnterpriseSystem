package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;

import presentation.AccountDocumentUI;
import temp_business.CashListBLService;
import temp_business.PaymentBLService;
import temp_business.ReceiptBLService;
import temp_businessImp.CashListBLImp;
import temp_businessImp.PaymentBLImp;
import temp_businessImp.ReceiptBLImp;
import vo.CashListVO;
import vo.PaymentVO;
import vo.ReceiptVO;
import businesslogic.EntryLineItem;
import businesslogic.TransferLineItem;

public class AccountDocumentTree implements TreeSelectionListener{
//	public final static String[] stationName = { "未发送","已发送待审批","已审批待处理", "已处理" };
	public final static String[] sortName = {  "未发送收款单","未发送付款单", "未发送现金费用单",
		 "已发送收款单","已发送付款单", "已发送现金费用单", "已通过收款单","已通过付款单", "已通过现金费用单",
		 "已处理收款单","已处理付款单", "已处理现金费用单"};// 确定分类节点的名称
	public Node root = new Node("单据");
	public Node root0 = new Node("未发送");
	public Node root1 = new Node("已发送");
	public Node root2 = new Node("已通过");
	public Node root3 = new Node("已处理");
	
	public AccountDocumentSortNode[] sortItem = null;
	private JTree myTree = null;
	public static Vector<String> columnNames = new Vector<String>();
ReceiptBLService receiptBLService=new ReceiptBLImp();
PaymentBLService paymentBLService=new PaymentBLImp();
CashListBLService cashListBLService=new CashListBLImp();

	private AccountDocumentSortNode selectNode = null;
	private AccountDocumentUI callBackPanel = null;
	private JTable customer_table=new JTable();
	public JTable getCustomer_table() {
		return customer_table;
	}

	public AccountDocumentTree(AccountDocumentUI myPanel) {
		this.callBackPanel = myPanel;
	}

	private void creatAccountDocumentSortNode() {// 生成固定的分类节点，商品的节点应该是动态的
	root.add(root0);root.add(root1);root.add(root2);root.add(root3);
		sortItem =new  AccountDocumentSortNode[12];
		for(int i=0;i<3;i++){
			sortItem[i] = new AccountDocumentSortNode(sortName[i]);
			root0.add(sortItem[i]);
		}
		for (int i = 3; i <6; i++) {
			sortItem[i] = new AccountDocumentSortNode(sortName[i]);
			root1.add(sortItem[i]);
		}
		for (int i = 6; i < 9; i++) {
			sortItem[i] = new AccountDocumentSortNode(sortName[i]);
			root2.add(sortItem[i]);
		}
		for (int i = 9; i <12 ; i++) {
			sortItem[i] = new AccountDocumentSortNode(sortName[i]);
			root3.add(sortItem[i]);
		}
		
		selectNode = sortItem[0];
	}

	public AccountDocumentSortNode getSelectNode() {
		return selectNode;
	}

	public void setSelecetNode(AccountDocumentSortNode seleceNode) {
		this.selectNode = seleceNode;
	}

	public void setSeleceNode(String sort) {// 记录当前选择的节点
	// selectNode = sortItem[index];
		if (sort.equals(sortName[0])) {
			selectNode = sortItem[0];
		}
		if (sort.equals(sortName[1])) {
			selectNode = sortItem[1];
		}
		if (sort.equals(sortName[2])) {
			selectNode = sortItem[2];
		}
		if (sort.equals(sortName[3])) {
			selectNode = sortItem[3];
		}
		if (sort.equals(sortName[4])) {
			selectNode = sortItem[4];
		}
		if (sort.equals(sortName[5])) {
			selectNode = sortItem[5];
		}
		if (sort.equals(sortName[6])) {
			selectNode = sortItem[6];
		}
		if (sort.equals(sortName[7])) {
			selectNode = sortItem[7];
		}
		if (sort.equals(sortName[8])) {
			selectNode = sortItem[8];
		}
		if (sort.equals(sortName[9])) {
			selectNode = sortItem[9];
		}
		if (sort.equals(sortName[10])) {
			selectNode = sortItem[10];
		}
		if (sort.equals(sortName[11])) {
			selectNode = sortItem[11];
		}
	}

	public void addDocument() {// 这是初始化树的方法，要和BL连接

		ArrayList<ReceiptVO> thereceiveList =receiptBLService.displayAll();
		ArrayList<PaymentVO> thepayList=paymentBLService.displayAll();
		ArrayList<CashListVO> thecashList =cashListBLService.displayAll();
		for (int i = 0; i < thereceiveList.size(); i++) {
			if((thereceiveList.get(i).isDealed()==true)&&(thereceiveList.get(i).isPass()==true)&&(thereceiveList.get(i).isSend()==true)){
			//root1.getgetChildAt(0)).addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
				sortItem[9].addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
			}else if((thereceiveList.get(i).isPass()==true)&&(thereceiveList.get(i).isSend()==true)&&(thereceiveList.get(i).isDealed()==false)){
				sortItem[6].addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
			}else if((thereceiveList.get(i).isSend()==true)&&(thereceiveList.get(i).isPass()==false)&&(thereceiveList.get(i).isDealed()==false)){
				sortItem[3].addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
			}else if((thereceiveList.get(i).isSend()==false)&&(thereceiveList.get(i).isPass()==false)&&(thereceiveList.get(i).isDealed()==false)){
				sortItem[0].addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
			}else{

				
			}
		//sortItem[0].addAccountReceiveDocument(new AccountReceiveDocumentNode(thereceiveList.get(i)));
		}
		for (int i = 0; i < thepayList.size(); i++) {
			if((thepayList.get(i).isDealed()==true)&&(thepayList.get(i).isPass()==true)&&(thepayList.get(i).isSend()==true)){
				sortItem[10].addAccountPayDocument(new AccountPayDocumentNode(thepayList.get(i)));
			}else if((thepayList.get(i).isPass()==true)&&(thepayList.get(i).isSend()==true)){
				sortItem[7].addAccountPayDocument(new AccountPayDocumentNode(thepayList.get(i)));
			}else if(thepayList.get(i).isSend()==true){
				sortItem[4].addAccountPayDocument(new AccountPayDocumentNode(thepayList.get(i)));
			}else {
				sortItem[1].addAccountPayDocument(new AccountPayDocumentNode(thepayList.get(i)));
			}
			
			}
		for (int i = 0; i < thecashList.size(); i++) {
			if((thecashList.get(i).isDealed()==true)&&(thecashList.get(i).isPass()==true)&&(thecashList.get(i).isSend()==true)){
				sortItem[11].addAccountCashDocument(new AccountCashDocumentNode(thecashList.get(i)));
				
			}else if((thecashList.get(i).isPass()==true)&&(thecashList.get(i).isSend()==true)){
				sortItem[8].addAccountCashDocument(new AccountCashDocumentNode(thecashList.get(i)));
			}else if(thecashList.get(i).isSend()==true){
				sortItem[5].addAccountCashDocument(new AccountCashDocumentNode(thecashList.get(i)));
			}
			else{
				sortItem[2].addAccountCashDocument(new AccountCashDocumentNode(thecashList.get(i)));
			}
			}
		myTree.addTreeSelectionListener(this);
	}

	public JTree getAccountDocumentTree() {
		if (myTree == null) {
			creatAccountDocumentSortNode();
			myTree = new JTree(root);
			addDocument();
		}
		return myTree;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof AccountDocumentSortNode) {
			selectNode = (AccountDocumentSortNode) n;
			callBackPanel.showAccountDocumentInfo();;
		}
	}
//时间，单据编号（SKD-yyyyMMdd-xxxxx），客户ID，客户（同时包含供应商和销售商）操作员ID，操作员（当前登录用户），总额汇总,已发送，已通过，已处理。
	private void addColumnsName() {// 表头银行账户，转账金额，备注
		columnNames.add("单据类型");
		columnNames.add("时间");
		columnNames.add("单据编号");
		columnNames.add("客户编号");
		columnNames.add("客户姓名");
		columnNames.add("操作员编号");
		columnNames.add("操作员姓名");
		columnNames.add("银行账户");
		columnNames.add("转账金额");
		columnNames.add("备注");
		columnNames.add("总额");
	
	}
	//时间，单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员ID，操作员（当前登录用户），银行账户，总额,已发送，已通过，已处理。
	private void addCashColumnsName() {// 表头条目名，金额，备注
		
		columnNames.add("单据类型");
		columnNames.add("时间");
		columnNames.add("单据编号");
		columnNames.add("操作员编号");
		columnNames.add("操作员姓名");
		columnNames.add("银行账户");
		columnNames.add("条目名");
		columnNames.add("金额");
		columnNames.add("备注");
		columnNames.add("总额");
		columnNames.add("");
	}
	

	public JTable creatAccountDocumentTable() {// 创造表格
		Vector<Vector<String>> document_info = new Vector<Vector<String>>();
		if((selectNode.getName()=="未发送收款单")||(selectNode.getName()=="已发送收款单")||(selectNode.getName()=="已通过收款单")||(selectNode.getName()=="已处理收款单")){
			if (columnNames.isEmpty()) {
				addColumnsName();
			}else{
				columnNames.clear();
				addColumnsName();
			}
			Vector<String> row_info;
			ReceiptVO receiptVO;
			int childCount = selectNode.getChildCount();
			for (int i = 0; i < childCount; i++) {
				
				receiptVO = ((AccountReceiveDocumentNode) selectNode.getChildAt(i)).getAccountReceiveDocumentVO();
				
				
				ArrayList<TransferLineItem> fArrayList=receiptVO.getTransferList().getTheList();
				for(int j=0;j<fArrayList.size();j++){
					row_info = new Vector<String>();
					if(j==0){
						row_info.add("收款单");
						row_info.add(receiptVO.getDate());// 时间
						row_info.add(receiptVO.getDocumentID());//单据编号
						row_info.add(Integer.toString(receiptVO.getCustomerID()));// 客户编号
						row_info.add(receiptVO.getCustomerName());// 客户姓名
						row_info.add(Integer.toString(receiptVO.getUserID()));// 操作员编号
						row_info.add(receiptVO.getUserName());// 操作员姓名
						row_info.add(fArrayList.get(0).getAccountName());
						row_info.add(Double.toString(fArrayList.get(0).getTransferPrice()));
						row_info.add(fArrayList.get(0).getRemark());
						row_info.add(Double.toString(receiptVO.getTotalPrice()));// 总额
			
						
					}else{
						row_info.add("");
						row_info.add("");// 时间
						row_info.add("");//单据编号
						row_info.add("");// 客户编号
						row_info.add("");// 客户姓名
						row_info.add("");// 操作员编号
						row_info.add("");// 操作员姓名
						row_info.add(fArrayList.get(j).getAccountName());
						row_info.add(Double.toString(fArrayList.get(j).getTransferPrice()));
						row_info.add(fArrayList.get(j).getRemark());
						row_info.add("");// 总额
					}
					document_info.add(row_info);
				}
			
			}

			
		}else if((selectNode.getName()=="未发送付款单")||(selectNode.getName()=="已发送付款单")||(selectNode.getName()=="已通过付款单")||(selectNode.getName()=="已处理付款单")){
			if (columnNames.isEmpty()) {
				addColumnsName();
			}else{
				columnNames.clear();
				addColumnsName();
			}
			Vector<String> row_info;
			PaymentVO paymentVO;
			int childCount = selectNode.getChildCount();
			for (int i = 0; i < childCount; i++) {
			
				paymentVO = ((AccountPayDocumentNode) selectNode.getChildAt(i)).getAccountPayDocumentVO();
				
				ArrayList<TransferLineItem> fArrayList=	paymentVO.getTransferList().getTheList();
				for(int j=0;j<fArrayList.size();j++){
					row_info = new Vector<String>();
					if(j==0){
				row_info.add("付款单");
				row_info.add(paymentVO.getDate());// 时间
				row_info.add(paymentVO.getDocumentID());//单据编号
				row_info.add(Integer.toString(paymentVO.getCustomerID()));// 客户编号
				row_info.add(paymentVO.getCustomerName());// 客户姓名
				row_info.add(Integer.toString(paymentVO.getUserID()));// 操作员编号
				row_info.add(paymentVO.getUserName());// 操作员姓名
				row_info.add(fArrayList.get(0).getAccountName());
				row_info.add(Double.toString(fArrayList.get(0).getTransferPrice()));
				row_info.add(fArrayList.get(0).getRemark());
				
				row_info.add(Double.toString(paymentVO.getTotalPrice()));// 总额
		
					}else{
						row_info.add("");
						row_info.add("");// 时间
						row_info.add("");//单据编号
						row_info.add("");// 客户编号
						row_info.add("");// 客户姓名
						row_info.add("");// 操作员编号
						row_info.add("");// 操作员姓名
						row_info.add(fArrayList.get(j).getAccountName());
						row_info.add(Double.toString(fArrayList.get(j).getTransferPrice()));
						row_info.add(fArrayList.get(j).getRemark());
						row_info.add("");// 总额
					}
				document_info.add(row_info);
				}
			}
		}
		else if((selectNode.getName()=="未发送现金费用单")||(selectNode.getName()=="已发送现金费用单")||(selectNode.getName()=="已通过现金费用单")||(selectNode.getName()=="已处理现金费用单")){
			if (columnNames.isEmpty()) {
				addCashColumnsName();
			}else{	columnNames.clear();
				addCashColumnsName();
			}
			Vector<String> row_info;
			CashListVO cashListVO;
			int childCount = selectNode.getChildCount();
			for (int i = 0; i < childCount; i++) {
				
				cashListVO = ((AccountCashDocumentNode) selectNode.getChildAt(i)).getAccountCashDocumentVO();
			ArrayList<EntryLineItem> entryLineItems=	cashListVO.getEntryList().getTheList();
			for(int j=0;j<entryLineItems.size();j++){
				row_info = new Vector<String>();
				if(j==0){
				
				row_info.add("现金费用单");
				row_info.add(cashListVO.getDate());// 时间
				row_info.add(cashListVO.getDocumentID());//单据编号
				row_info.add(Integer.toString(cashListVO.getUserID()));// 操作员编号
				row_info.add(cashListVO.getUserName());// 操作员姓名
				row_info.add(cashListVO.getAccountName());//账户名
				row_info.add(entryLineItems.get(0).getEntryName());
				row_info.add(Double.toString(entryLineItems.get(0).getEntryPrice()));
				row_info.add(entryLineItems.get(0).getRemark());
				row_info.add(Double.toString(cashListVO.getTotalPrice()));// 总额
		
				row_info.add("");
				}else{
					row_info.add("");
					row_info.add("");// 时间
					row_info.add("");//单据编号
					row_info.add("");// 操作员编号
					row_info.add("");// 操作员姓名
					row_info.add("");//账户名
				row_info.add(entryLineItems.get(j).getEntryName());
				row_info.add(Double.toString(entryLineItems.get(j).getEntryPrice()));
				row_info.add(entryLineItems.get(j).getRemark());
				row_info.add("");// 总额
		
				}
				document_info.add(row_info);
				}
				
			}
		}
		else{
			
		}
		DefaultTableModel newTableModel = new DefaultTableModel(document_info,
				columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0||column == 1||column == 9||column == 10) {//控制不可
					return false;
				}
				return true;
			}
		};
		// new DefaultTableModel(customer_info,
		// columnNames)
	    customer_table = new JTable(newTableModel);
		customer_table.setGridColor(Color.black);
		customer_table.setShowGrid(true);
		customer_table.setBackground(Color.WHITE);
		customer_table.setRowHeight(25);
		customer_table.setFont(new Font("微软雅黑", 0, 12));
		return customer_table;
	}
	public void removeDocument(int index) {
		selectNode.removeDocument(index);
		myTree.updateUI();
	}
	
	public void removeAccount() {
		selectNode.removeFromParent();
		myTree.updateUI();
	}
	
}
