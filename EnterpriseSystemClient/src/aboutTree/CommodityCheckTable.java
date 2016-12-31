package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.CommodityCheckUI;
import vo.CommodityVO;

public class CommodityCheckTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table = null;

	public JTable getTable() {
		return table;
	}

	/*
	 * 初始化返回界面
	 */
	public CommodityCheckTable(CommodityCheckUI myPanel) {
	}

	// • 库存查看（设定一个时间段，查看此时间段内的出/入库数量/金额，销售/进货的数量/金额。库存数量要有合计，这一点统一于普适需求。）
	private void addColumnsName() {// 表头

		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("商品型号");
		columnNames.add("商品数量");
		columnNames.add("商品进价");
		columnNames.add("商品售价");
		columnNames.add("商品最近进价");
		columnNames.add("商品进货数量");
		columnNames.add("商品最近售价");
		columnNames.add("商品售出数量");

	}

	public JTable creatCommoditySearchTable(ArrayList<CommodityVO> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommodityVO commodity;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			commodity = arr.get(i);

			row_info.add(Integer.toString(commodity.getCommodityID()));// 编号
			row_info.add(commodity.getCommodityName());// 姓名
			row_info.add(commodity.getCommodityModel());// 型号
			row_info.add(Integer.toString(commodity.getInventoryQuantity()));// 数量
			row_info.add(Double.toString(commodity.getPurchasePrice()));// 进价
			row_info.add(Double.toString(commodity.getRetailPrice()));// 售价
			row_info.add(Double.toString(commodity.getLatestPurchasePrice()));// 应付
			row_info.add(Double.toString(commodity.getLatestRetailPrice()));// 应付
			commodity_info.add(row_info);
		}
		DefaultTableModel newTableModel = new DefaultTableModel(commodity_info,
				columnNames) {
			//private static final long serialVersionUID = 1L;

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
