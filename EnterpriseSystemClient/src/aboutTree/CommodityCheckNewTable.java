package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.CommodityCheckUI;
import businesslogic.CommodityCheck;
import businesslogic.ImportList;
import businesslogic.SaleList;

public class CommodityCheckNewTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table = null;

	public JTable getTable() {
		return table;
	}

	/*
	 * 初始化返回界面
	 */
	public CommodityCheckNewTable(CommodityCheckUI myPanel) {
	}

	// • 设定一个时间段，查看此时间段内的出/入库数量/金额，销售/进货的数量/金额。库存数量要有合计，这一点统一于普适需求
	private void addColumnsName() {// 表头

		columnNames.add("商品编号");
		columnNames.add("商品名称");
		columnNames.add("商品型号");
		columnNames.add("商品进价");
		columnNames.add("商品进货数量");
		columnNames.add("商品售价");
		columnNames.add("商品销售数量");
		columnNames.add("进价总数");
		columnNames.add("售价总数");

	}

	@SuppressWarnings("null")
	public JTable creatCommoditySearchTable(ArrayList<CommodityCheck> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		CommodityCheck commodityCheck;
		int childCount = arr.size();
		@SuppressWarnings("unused")
		int linenumber = 1;
		for (int i = 0; i < childCount; i++) {

			commodityCheck = arr.get(i);
			SaleList thesaleList = commodityCheck.getThesaleList();// 商品列表
			ImportList theimportList = commodityCheck.getTheimportList();// 商品列表
			if ((thesaleList != null) && (theimportList == null)) {
				for (int j = 0; j < thesaleList.getTheList().size(); j++) {
					row_info = new Vector<String>();
					row_info.add(Integer.toString(thesaleList.getTheList()
							.get(j).getTheCommodity().getCommodityID()));
					row_info.add(thesaleList.getTheList().get(j)
							.getTheCommodity().getCommodityName());
					row_info.add(thesaleList.getTheList().get(j)
							.getTheCommodity().getCommodityModel());
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Double.toString(thesaleList.getTheList()
							.get(j).getPrice()));
					row_info.add(Double.toString(thesaleList.getTheList()
							.get(j).getTheNumber()));
					row_info.add(Integer.toString(0));

					row_info.add(Double.toString(thesaleList.getTheList()
							.get(j).getTotal()));
					commodity_info.add(row_info);
				}
			} else if ((thesaleList == null) && (theimportList != null)) {
				for (int j = 0; j < theimportList.getImportLineList().size(); j++) {
					row_info = new Vector<String>();
					row_info.add(Integer.toString(theimportList
							.getImportLineList().get(j).getTheCommodity()
							.getCommodityID()));
					row_info.add(theimportList.getImportLineList().get(j)
							.getTheCommodity().getCommodityName());
					row_info.add(theimportList.getImportLineList().get(j)
							.getTheCommodity().getCommodityModel());
					row_info.add(Double.toString(theimportList
							.getImportLineList().get(j).getPrice()));
					row_info.add(Double.toString(theimportList
							.getImportLineList().get(j).getTheNumber()));
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Double.toString(theimportList
							.getImportLineList().get(j).getTotal()));
					row_info.add(Integer.toString(0));
					commodity_info.add(row_info);

				}
			} else if ((thesaleList == null) && (theimportList == null)) {
				for (int j = 0; j < theimportList.getImportLineList().size(); j++) {
					row_info = new Vector<String>();
					row_info.add(Integer.toString(theimportList
							.getImportLineList().get(j).getTheCommodity()
							.getCommodityID()));
					row_info.add(theimportList.getImportLineList().get(j)
							.getTheCommodity().getCommodityName());
					row_info.add(theimportList.getImportLineList().get(j)
							.getTheCommodity().getCommodityModel());
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));
					row_info.add(Integer.toString(0));

					commodity_info.add(row_info);
				}
			} else {

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
