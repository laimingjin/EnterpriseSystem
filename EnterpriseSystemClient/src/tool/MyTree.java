package tool;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import vo.CustomerVO;
import vo.UserVO;
import enumClass.TypeOfTree;



public class MyTree {

	public static JTree creatTree(ArrayList<Object> theList, TypeOfTree theType) {
		JTree resultTree = null;

		if (theType == TypeOfTree.customer) {
			resultTree = getCustomerTree(theList);
		}
		if (theType == TypeOfTree.commodity) {
           // TODO 商品情况比较复杂~~~还有单据什么的没写
		}
		if (theType == TypeOfTree.user) {
			resultTree = getUserTree(theList);
		}
     if(theType==TypeOfTree.commodityDoc){
    	 
     }
		return resultTree;
	}

	private static JTree getCustomerTree(ArrayList<Object> theList) {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("客户");
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("销售商");
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("供应商");

		for (int i = 0; i < theList.size(); i++) {// 添加节点
			CustomerVO myCustomerVO = (CustomerVO) theList.get(i);
			if (myCustomerVO.getCustomerType().equals("销售商")) {
				node1.add(new DefaultMutableTreeNode(myCustomerVO));
			} else {
				node2.add(new DefaultMutableTreeNode(myCustomerVO));
			}
		}

		top.add(node1);
		top.add(node2);

		JTree resultTree = new JTree(top);
		resultTree.setFont(new Font("微软雅黑", 0, 20));
		resultTree.setOpaque(false);
		resultTree.setBackground(new Color(2, 2, 2));
		return resultTree;
	}

	private static JTree getUserTree(ArrayList<Object> theList) {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("系统用户");
		DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("库存管理员");
		DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("进货销售人员");
		DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("财务人员");
		DefaultMutableTreeNode node4 = new DefaultMutableTreeNode("总经理");
		for (int i = 0; i < theList.size(); i++) {
			UserVO tempUserVO = (UserVO) theList.get(i);
			if (tempUserVO.getTheJob().equals("库存管理员")) {
				node1.add(new DefaultMutableTreeNode(tempUserVO));
			} else if (tempUserVO.getTheJob().equals("进货销售人员")) {
				node2.add(new DefaultMutableTreeNode(tempUserVO));
			} else if (tempUserVO.getTheJob().equals("财务人员")) {
				node3.add(new DefaultMutableTreeNode(tempUserVO));
			} else if (tempUserVO.getTheJob().equals("总经理")) {
				node4.add(new DefaultMutableTreeNode(tempUserVO));
			}
		}
		top.add(node1);
		top.add(node2);
		top.add(node3);
		top.add(node4);

		JTree resultTree = new JTree(top);
		resultTree.setFont(new Font("微软雅黑", 0, 20));
		resultTree.setOpaque(false);
		resultTree.setBackground(new Color(2, 2, 2));
		return resultTree;
	}
}
