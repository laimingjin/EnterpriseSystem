package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.CommodityCheckUI;
import vo.CommoditySnapshotVO;
import businesslogic.Commodity;

public class CommoditySnapshotTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table =null;
public JTable getTable() {
		return table;
	}
	/*
     * 初始化返回界面
     */
	public CommoditySnapshotTable(CommodityCheckUI myPanel) {
	}
//•	库存盘点（盘点的是当天的库存快照，
	//包括当天的各种商品的名称，型号，库存数量，库存均价，批次，批号，出厂日期，并且显示行号。要求可以导出Excel。
	private void addColumnsName() {//表头
		 columnNames.add("行号");
	    columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("商品型号");
		columnNames.add("商品数量");
		columnNames.add("库存均价");
		columnNames.add("批次");
		columnNames.add("批号");
		columnNames.add("出厂日期");
	}
	public JTable creatCommoditySearchTable(ArrayList<CommoditySnapshotVO> arr) {//创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}
	
	
		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommoditySnapshotVO commoditySnapshotVO;
		int childCount = arr.size();
		@SuppressWarnings("unused")
		int linenumber=1;
		for (int i = 0; i < childCount; i++) {
	
			commoditySnapshotVO= arr.get(i);
			ArrayList<Commodity> commodities=commoditySnapshotVO.getCommodity();
		
			for(int j=0;j<commodities.size();j++){	
				row_info = new Vector<String>();
				row_info.add(Integer.toString(j+1));
				row_info.add(Integer.toString(commodities.get(j).getCommodityID()));
				row_info.add(commodities.get(j).getCommodityName());
				row_info.add(commodities.get(j).getCommodityModel());
				row_info.add(Integer.toString(commodities.get(j).getInventoryQuantity()));
				row_info.add(Double.toString(commodities.get(j).getAveragePrice()));
				row_info.add(commoditySnapshotVO.getLot());// 批次
				row_info.add(Integer.toString(commoditySnapshotVO.getLotNumber()));// 批号
				row_info.add(commodities.get(j).getDate());
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
		table = new JTable(newTableModel);
		table.setGridColor(Color.black);
		table.setShowGrid(true);
		table.setBackground(Color.WHITE);
		table.setRowHeight(25);
		table.setFont(new Font("微软雅黑", 0, 12));
		return table;
	}
}
