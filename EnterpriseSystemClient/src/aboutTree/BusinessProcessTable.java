package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.DocumentVO;

public class BusinessProcessTable {
	public static Vector<String> columnNames = new Vector<String>();
	JTable table = new JTable();

	public JTable getTable() {
		return table;
	}

	/*
	 * 初始化返回界面
	 */
	public BusinessProcessTable(JPanel myPanel) {
	}

	// 时间，单据编号（SKD-yyyyMMdd-xxxxx），客户ID，客户（同时包含供应商和销售商）操作员ID，操作员（当前登录用户），总额汇总,已发送，已通过，已处理。
	private void addDocumentColumnsName() {// 表头
		columnNames.add("单据类型");
		columnNames.add("时间");
		columnNames.add("单据编号");
		columnNames.add("已发送");
		columnNames.add("已通过");
		columnNames.add("已处理");
	}

	// 时间，单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员ID，操作员（当前登录用户），银行账户，总额,已发送，已通过，已处理。

	public JTable SaleListTable(ArrayList<DocumentVO> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addDocumentColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		DocumentVO documentVO;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			documentVO = arr.get(i);

			row_info.add(documentVO.getType());
			row_info.add(documentVO.getTheDate());// 日期
			row_info.add(String.valueOf(documentVO.getDocumentID()));
			row_info.add(String.valueOf(documentVO.isSend()));// 发送
			row_info.add(String.valueOf(documentVO.isPass()));// 审批
			row_info.add(String.valueOf(documentVO.isDealed()));// 处理

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
