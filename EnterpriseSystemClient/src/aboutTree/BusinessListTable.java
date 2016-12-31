package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vo.BusinessListVO;

public class BusinessListTable {
	public static Vector<String> columnNames = new Vector<String>();
	/*
	 * 初始化返回界面
	 */
	public BusinessListTable(JPanel myPanel) {
	}

	// 时间，单据编号（SKD-yyyyMMdd-xxxxx），客户ID，客户（同时包含供应商和销售商）操作员ID，操作员（当前登录用户），总额汇总,已发送，已通过，已处理。
	private void addDocumentColumnsName() {// 表头
		columnNames.add("销售收入");
		columnNames.add("商品报溢收入");
		columnNames.add("成本调价收入");
		columnNames.add("进货退货差价");
		columnNames.add("代金券与实际收款差额收入");
		columnNames.add("折让后总收入");
		columnNames.add("折让了金额");
		columnNames.add("销售成本");
		columnNames.add("商品报损支出");
		columnNames.add("商品赠出支出");
		columnNames.add("总支出");
		columnNames.add("利润");
	}

	// 时间，单据编号（XJFYD-yyyyMMdd-xxxxx）,操作员ID，操作员（当前登录用户），银行账户，总额,已发送，已通过，已处理。

	public JTable SaleListTable(ArrayList<BusinessListVO> arr) {// 创造模糊查找得到表格
		if (columnNames.isEmpty()) {
			addDocumentColumnsName();
		}

		Vector<Vector<String>> commodity_info = new Vector<Vector<String>>();
		Vector<String> row_info;
		BusinessListVO businessListVO;
		int childCount = arr.size();
		for (int i = 0; i < childCount; i++) {
			row_info = new Vector<String>();
			businessListVO = arr.get(i);

			row_info.add(String.valueOf(businessListVO.getSaleIncome()));
			row_info.add(String.valueOf(businessListVO
					.getCommodityOverflowIncome()));
			row_info.add(String.valueOf(businessListVO.getCostAdjustIncome()));
			row_info.add(String.valueOf(businessListVO
					.getImportAndExportSpreadIncome()));
			row_info.add(String.valueOf(businessListVO.getWithVoucherIncome()));
			row_info.add(String.valueOf(businessListVO.getRebateIncomeAfter()));
			row_info.add(String.valueOf(businessListVO.getRebatePrice()));
			row_info.add(String.valueOf(businessListVO.getSaleCost()));
			row_info.add(String.valueOf(businessListVO.getCommodityLossCost()));
			row_info.add(String.valueOf(businessListVO.getCommodityGiftCost()));
			row_info.add(String.valueOf(businessListVO.getTotalEnpense()));
			row_info.add(String.valueOf(businessListVO.getProfit()));

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
		JTable commodity_table = new JTable(newTableModel);
		commodity_table.setGridColor(Color.black);
		commodity_table.setShowGrid(true);
		commodity_table.setBackground(Color.WHITE);
		commodity_table.setRowHeight(25);
		commodity_table.setFont(new Font("微软雅黑", 0, 12));
		return commodity_table;
	}

}
