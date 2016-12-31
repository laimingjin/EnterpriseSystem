package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import presentation.DairyCheckUI;
import vo.LogVO;

public class LogTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table = null;

	public JTable getTable() {
		return table;
	}

	/*
	 * 初始化返回界面
	 */
	public LogTable(DairyCheckUI myPanel) {
	}

	private void addColumnsName() {// 表头
		columnNames.add("日期");
		columnNames.add("日志ID");
		columnNames.add("操作员ID");
		columnNames.add("操作员姓名");
		columnNames.add("操作员类型");

	}

	public JTable creatCommoditySearchTable(ArrayList<LogVO> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		LogVO logVO;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			logVO = arr.get(i);
			row_info.add(logVO.getDate());
			row_info.add(Long.toString(logVO.getId()));
			row_info.add(Integer.toString(logVO.getOperaterID()));
			row_info.add(logVO.getOperater());// 编号
			row_info.add(logVO.getOperationType());
			commodity_info.add(row_info);
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
