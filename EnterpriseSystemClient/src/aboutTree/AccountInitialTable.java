package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.AccountInitialUI;
import vo.SetAccountVO;

public class AccountInitialTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table =null;
public JTable getTable() {
		return table;
	}
	/*
     * 初始化返回界面
     */
	public AccountInitialTable (AccountInitialUI myPanel) {
	}
//•	添加商品信息（商品分类，某一商品的名称，类别，型号，进价/售价(默认为上年平均)，
	//最近进价和最近售价留空），客户信息（客户分类，某一个客户的名称，联系方式等，应收应付(之前遗留)），银行账户信息（名称，余额）。
	private void addColumnsName() {//表头
	
	    columnNames.add("日期");
		columnNames.add("ID");
		columnNames.add("商品分类");
		columnNames.add("商品名称");
		columnNames.add("商品型号");
		columnNames.add("商品进价");
		columnNames.add("商品售价");
		columnNames.add("客户类别");
		columnNames.add("客户姓名");
		columnNames.add("联系方式");
		columnNames.add("应收");
		columnNames.add("应付");
		columnNames.add("账户名称");
		columnNames.add("账户金额");
		
	
	}
	public JTable creatCommoditySearchTable(ArrayList<SetAccountVO> arr) {
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
		DefaultTableModel newTableModel = new DefaultTableModel();

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		SetAccountVO setAccountVO;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			
			setAccountVO=arr.get(i);
			int sizeofCommodity=setAccountVO.getCommodityList().size();
			int sizeOfCustomer=setAccountVO.getCustomerList().size();
			int sizeOfAccount =setAccountVO.getAccountList().size();
			int max=sizeofCommodity;
			if(sizeofCommodity<sizeOfAccount){
				max=sizeOfAccount;
				if(sizeOfAccount<sizeOfCustomer){
					max=sizeOfCustomer;
				}
			}
		
			for(int j=0;j<max;j++){
			
				row_info = new Vector<String>();
			//	setAccountVO= arr.get(i);
				if(j==0){
					row_info.add(setAccountVO.getDate());
					
					row_info.add(Integer.toString(setAccountVO.getSetAccountID()));
				}else{
					row_info.add(" ");
					row_info.add(" ");
					
				}
				if(j<sizeofCommodity){
				row_info.add(setAccountVO.getCommodityList().get(j).getCommoditySortName());
				row_info.add(setAccountVO.getCommodityList().get(j).getCommodityName());
				row_info.add(setAccountVO.getCommodityList().get(j).getCommodityModel());	
				row_info.add(Double.toString(setAccountVO.getCommodityList().get(j).getPurchasePrice()));
				row_info.add(Double.toString(setAccountVO.getCommodityList().get(j).getRetailPrice()));
				}else{
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
				}
				if(j<sizeOfCustomer){
					row_info.add(setAccountVO.getCustomerList().get(j).getCustomerType());
					row_info.add(setAccountVO.getCustomerList().get(j).getCustomerName());
				//	row_info.add(setAccountVO.getCustomerList().get(j).geteMail());
					row_info.add(setAccountVO.getCustomerList().get(j).getTelePhone());
					row_info.add(Double.toString(setAccountVO.getCustomerList().get(j).getReceivableAmount()));
					row_info.add(Double.toString(setAccountVO.getCustomerList().get(j).getPayableAmount()));
					
				}else{
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
					row_info.add(" ");
				}
				if(j<sizeOfAccount){
					row_info.add(setAccountVO.getAccountList().get(j).getAccountName());
					row_info.add(Double.toString(setAccountVO.getAccountList().get(j).getAccountPrice()));
				}else{
					row_info.add(" ");
					row_info.add(" ");
				}
				commodity_info.add(row_info);
		
			
			
			}
			
			
		}
		newTableModel = new DefaultTableModel(commodity_info,
				columnNames) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(newTableModel);
		table.setGridColor(Color.black);
		table.setShowGrid(true);
		table.setBackground(Color.WHITE);
		table.setRowHeight(25);
		table.setFont(new Font("微软雅黑", 0, 12));
		return table;
	}
}

