package tool;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


import vo.CommodityVO;
import vo.CustomerVO;
import vo.UserVO;
import enumClass.TypeOfTable;

public class MyTable {
	JTable myTable;
	public JTable getJTable(ArrayList<Object> theList, TypeOfTable theType) {
		Vector<String> columnNames = new Vector<String>();
		Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
		if (theType == TypeOfTable.customerTable) {// 显示客户信息表格
			columnNames.clear();
			rowData.clear();
			columnNames.add("编号");
			columnNames.add("类型");
			columnNames.add("级别");
			columnNames.add("姓名");
			columnNames.add("电话");
			columnNames.add("地址");
			columnNames.add("邮编");
			columnNames.add("电子邮箱");
			columnNames.add("应收额度");
			columnNames.add("应收金额");
			columnNames.add("应付金额");
			columnNames.add("业务员");
			if (!theList.isEmpty()) {
				for (int i = 0; i < theList.size(); i++) {
					Vector<Object> tempVector = new Vector<Object>();// 每一行数据的临时存放地
					CustomerVO tempVO = (CustomerVO) theList.get(i);// 取得要显示的信息

					tempVector.add(tempVO.getCustomerID());// 编号
					tempVector.add(tempVO.getCustomerType());// 类型
					tempVector.add(tempVO.getCustomerRank());// 级别
					tempVector.add(tempVO.getCustomerName());// 姓名
					tempVector.add(tempVO.getTelePhone());// 号码
					tempVector.add(tempVO.getCustomerAddress());// 地址
					tempVector.add(tempVO.getCustomerPostCode());// 邮编
					tempVector.add(tempVO.geteMail());// 电子邮箱
					tempVector.add(tempVO.getReceivableLimit());// 应收额度
					tempVector.add(tempVO.getReceivableAmount());// 应收
					tempVector.add(tempVO.getPayableAmount());// 应付
					tempVector.add(tempVO.getOperator());// 业务员

					rowData.add(tempVector);// 将每一行的数据加入到总容器中
				}
			}
			DefaultTableModel newTableModel = new DefaultTableModel(rowData, columnNames) {
				private static final long serialVersionUID = 1L;
				@Override
				public boolean isCellEditable(int row, int column) {
					if (column == 0||column == 1||column == 9||column == 10) {//控制不可修改
						return false;
					}
					return true;
				}
			};
			 myTable = new JTable(newTableModel);
		}
		if (theType == TypeOfTable.commodityTable) {
			columnNames.clear();
			rowData.clear();
			columnNames.add("编号");
			columnNames.add("名称");
			columnNames.add("型号");
			columnNames.add("库存数量");
			columnNames.add("进价");
			columnNames.add("零售价");
			columnNames.add("最近进价");
			columnNames.add("最近零售价");
			for (int i = 0; i < theList.size(); i++) {
				Vector<Object> tempVector = new Vector<Object>();// 每一行数据的临时存放地
				CommodityVO tempVO = (CommodityVO) theList.get(i);// 要存放的数据

				tempVector.add(tempVO.getCommodityID());// 编号
				tempVector.add(tempVO.getCommodityName());// 名称
				tempVector.add(tempVO.getCommodityModel());// 型号
				tempVector.add(tempVO.getInventoryQuantity());// 库存数量
				tempVector.add(tempVO.getPurchasePrice());// 进价
				tempVector.add(tempVO.getRetailPrice());// 零售价
				tempVector.add(tempVO.getLatestPurchasePrice());// 最近进价
				tempVector.add(tempVO.getLatestRetailPrice());// 最近零售价

				rowData.add(tempVector);
			}
			 myTable = new JTable(rowData, columnNames);
		}
		if (theType == TypeOfTable.userTable) {
			rowData.clear();
			columnNames.clear();

			columnNames.add("用户编号");
			columnNames.add("用户名");
			columnNames.add("密码");
			columnNames.add("职位");
			columnNames.add("权限级别");

			for (int i = 0; i < theList.size(); i++) {
				Vector<Object> tempVector = new Vector<Object>();
				UserVO tempUserVO = (UserVO) theList.get(i);
				tempVector.add(tempUserVO.getUserID());// 用户编号
				tempVector.add(tempUserVO.getUserName());// 用户名
				tempVector.add(tempUserVO.getPassword());// 用户密码
				tempVector.add(tempUserVO.getTheJob());// 用户职位
				tempVector.add(tempUserVO.getPowerLevel());// 用户级别

				rowData.add(tempVector);
			}
			 myTable = new JTable(rowData, columnNames);
		}

		if (theType == TypeOfTable.commodityTest) {
			columnNames.clear();
			rowData.clear();
			columnNames.add("所属分数名称");
			columnNames.add("名称");
			columnNames.add("型号");
			columnNames.add("库存数量");
			columnNames.add("进价");
			columnNames.add("零售价");

//			for (int i = 0; i < theList.size(); i++) {
//				Vector<Object> tempVector = new Vector<Object>();// 每一行数据的临时存放地
//				CommodityTest tempVO = (CommodityTest) theList.get(i);// 要存放的数据
//
//				tempVector.add(tempVO.getCommoditySortName());// 编号
//				tempVector.add(tempVO.getCommodityName());// 名称
//				tempVector.add(tempVO.getCommodityModel());// 型号
//				tempVector.add(tempVO.getInventoryQuantity());// 库存数量
//				tempVector.add(tempVO.getPurchasePrice());// 进价
//				tempVector.add(tempVO.getRetailPrice());// 零售
//
//				rowData.add(tempVector);
//			}
			 myTable = new JTable(rowData, columnNames);
		}	
		myTable.setFont(new Font("微软雅黑", 0, 12));
		myTable.setIntercellSpacing(new Dimension(2, 3));
		myTable.setGridColor(new Color(44, 151, 203));
		myTable.setForeground(new Color(2, 86, 127));
		myTable.setAutoCreateRowSorter(true);
		return myTable;
	}

}
