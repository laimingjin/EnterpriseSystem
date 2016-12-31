package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.SalesListVO;

public class SalesListsTable {
	public static Vector<String> columnNames = new Vector<String>();
	/*
	     * 初始化返回界面
	     */
		public SalesListsTable(JPanel myPanel) {
		}
//时间（精确到天），商品名，型号，数量，单价，总额。需要支持导出数据。
		private void addColumnsName() {//表头
		
		    columnNames.add("日期");
			columnNames.add("商品名");
			columnNames.add("型号");
			columnNames.add("数量");
			columnNames.add("单价");
			columnNames.add("总额");
		
		
		}
		public JTable creatCommoditySearchTable(ArrayList<SalesListVO> arr) {//创造模糊查找得到表格
			if (columnNames.isEmpty()) {
				addColumnsName();
			}
		
		
			Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
			Vector<String> row_info;
			SalesListVO salesListVO;
			int childCount = arr.size();
			for (int i = 0; i < childCount; i++) {
				
				
				salesListVO= arr.get(i);
				for(int j=0;j<salesListVO.getTheList().size();j++){
				row_info = new Vector<String>();
				row_info.add(salesListVO.getTheList().get(j).getDate());

				row_info.add(salesListVO.getTheList().get(j).getCommodityName());//商品名
			
				row_info.add(salesListVO.getTheList().get(j).getCommodityModel());// 型号
				row_info.add(Integer.toString(salesListVO.getTheList().get(j).getRetailQuantity()));// 数量
				row_info.add(Double.toString(salesListVO.getTheList().get(j).getLatestRetailPrice()));// 单价
				row_info.add(Double.toString(salesListVO.getTheList().get(j).getTotal()));// 总额
		
				commodity_info.add(row_info);
			
			}
			}
			DefaultTableModel newTableModel = new DefaultTableModel(commodity_info,
					columnNames) {
			//	private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			
			JTable commodity_table =  new JTable(newTableModel);
			commodity_table.setGridColor(Color.black);
			commodity_table.setShowGrid(true);
			commodity_table.setBackground(Color.WHITE);
			commodity_table.setRowHeight(25);
			commodity_table.setFont(new Font("微软雅黑", 0, 12));
			return commodity_table;
		}
}
