package aboutTree;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;

import presentation.ImpSaleDocumentUI;
import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import temp_businessImp.ExportBLImp;
import temp_businessImp.ImportDocumentImp;
import vo.ImportDocumentVO;
import vo.SaleDocumentVO;
import enumClass.StateOfDocNode;
import enumClass.StateOfDocument;

public class ImpSaleDocTree implements TreeSelectionListener {
	public Node root = new Node("单据");
	private Node nodeDraft = new Node("未发生单据");
	private Node nodeSended = new Node("待审批单据");
	private Node nodeExamined = new Node("已审批单据");
	private Node nodeDone = new Node("已处理单据");
	private JTree docTree = null;// 树
	private ImpDocSortNode selectImpNode = null;// 记录被选中的点
	private SaleDocSortNode seleceSaleNode = null;

	private static Vector<String> columnName = new Vector<String>();// 表头
	private JTable document_table;// 表格
	ImpSaleDocumentUI callBackPanel = null;

	ImportDocumentBLService importDocumentBLService = null;
	ExportBLService saleBLService = null;

	public ImpSaleDocTree(ImpSaleDocumentUI panel) {
		callBackPanel = panel;
		importDocumentBLService = new ImportDocumentImp();
		saleBLService = new ExportBLImp();
	}

	private void creatDocSortNode() {
		// 进货单
		ArrayList<ImportDocumentVO> impDocDraft = importDocumentBLService
				.displayImport("JHD", StateOfDocument.DRAFT);
		ArrayList<ImportDocumentVO> impDocSended = importDocumentBLService
				.displayImport("JHD", StateOfDocument.SENDED);
		ArrayList<ImportDocumentVO> impDocExamined = importDocumentBLService
				.displayImport("JHD", StateOfDocument.EXAMINED);
		ArrayList<ImportDocumentVO> impDocDone = importDocumentBLService
				.displayImport("JHD", StateOfDocument.DONE);
		// 进货退货单
		ArrayList<ImportDocumentVO> impReturnDocDraft = importDocumentBLService
				.displayImport("JHTHD", StateOfDocument.DRAFT);
		ArrayList<ImportDocumentVO> impReturnDocSended = importDocumentBLService
				.displayImport("JHTHD", StateOfDocument.SENDED);
		ArrayList<ImportDocumentVO> impReturnDocExamined = importDocumentBLService
				.displayImport("JHTHD", StateOfDocument.EXAMINED);
		ArrayList<ImportDocumentVO> impReturnDocDone = importDocumentBLService
				.displayImport("JHTHD", StateOfDocument.DONE);
		// 销售单
		ArrayList<SaleDocumentVO> saleDocDraft = saleBLService.displayExport(
				"XSD", StateOfDocument.DRAFT);
		ArrayList<SaleDocumentVO> saleDocSended = saleBLService.displayExport(
				"XSD", StateOfDocument.SENDED);
		ArrayList<SaleDocumentVO> saleDocExamined = saleBLService
				.displayExport("XSD", StateOfDocument.EXAMINED);
		ArrayList<SaleDocumentVO> saleDocDone = saleBLService.displayExport(
				"XSD", StateOfDocument.DONE);
		// 销售退货单
		ArrayList<SaleDocumentVO> saleReturnDocDraft = saleBLService
				.displayExport("XSTHD", StateOfDocument.DRAFT);
		ArrayList<SaleDocumentVO> saleReturnDocSended = saleBLService
				.displayExport("XSTHD", StateOfDocument.SENDED);
		ArrayList<SaleDocumentVO> saleReturnDocExamined = saleBLService
				.displayExport("XSTHD", StateOfDocument.EXAMINED);
		ArrayList<SaleDocumentVO> saleReturnDocDone = saleBLService
				.displayExport("XSTHD", StateOfDocument.DONE);

		// System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		// System.out.println(impDocDraft);
		// System.out.println(impDocSended);
		// System.out.println(importDocumentBLService.displayImport("JHD",
		// StateOfDocument.EXAMINED));
		// System.err.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		nodeDraft.add(new ImpDocSortNode("进货单", StateOfDocNode.ImpDraft,
				impDocDraft));
		nodeDraft.add(new ImpDocSortNode("进货退货单",
				StateOfDocNode.ImpReturnDraft, impReturnDocDraft));
		nodeDraft.add(new SaleDocSortNode("销售单", StateOfDocNode.SaleDraft,
				saleDocDraft));
		nodeDraft.add(new SaleDocSortNode("销售退货单",
				StateOfDocNode.SaleReturnDraft, saleReturnDocDraft));

		nodeSended.add(new ImpDocSortNode("进货单", StateOfDocNode.ImpSended,
				impDocSended));
		nodeSended.add(new ImpDocSortNode("进货退货单",
				StateOfDocNode.ImpReturnSended, impReturnDocSended));
		nodeSended.add(new SaleDocSortNode("销售单", StateOfDocNode.SaleSended,
				saleDocSended));
		nodeSended.add(new SaleDocSortNode("销售退货单",
				StateOfDocNode.SaleReturnSended, saleReturnDocSended));

		nodeExamined.add(new ImpDocSortNode("进货单", StateOfDocNode.ImpExamined,
				impDocExamined));
		nodeExamined.add(new ImpDocSortNode("进货退货单",
				StateOfDocNode.ImpReturnExamined, impReturnDocExamined));
		nodeExamined.add(new SaleDocSortNode("销售单",
				StateOfDocNode.SaleExamined, saleDocExamined));
		nodeExamined.add(new SaleDocSortNode("销售退货单",
				StateOfDocNode.SaleReturnExamined, saleReturnDocExamined));

		nodeDone.add(new ImpDocSortNode("进货单", StateOfDocNode.ImpDone,
				impDocDone));
		nodeDone.add(new ImpDocSortNode("进货退货单", StateOfDocNode.ImpReturnDone,
				impReturnDocDone));
		nodeDone.add(new SaleDocSortNode("销售单", StateOfDocNode.SaleDone,
				saleDocDone));
		nodeDone.add(new SaleDocSortNode("销售退货单",
				StateOfDocNode.SaleReturnDone, saleReturnDocDone));

		root.add(nodeDraft);
		root.add(nodeSended);
		root.add(nodeExamined);
		root.add(nodeDone);
	}

	public JTree getDocumentTree() {// 取得树的方法
		if (docTree == null) {
			creatDocSortNode();
			docTree = new JTree(root);
			docTree.addTreeSelectionListener(this);
		}
		return docTree;
	}

	public ImpDocSortNode getSelectImpNode() {
		return selectImpNode;
	}

	public SaleDocSortNode getSeleceSaleNode() {
		return seleceSaleNode;
	}

	public void valueChanged(TreeSelectionEvent e) {// 对树的监听
		Object n = e.getPath().getLastPathComponent();
		if (n instanceof ImpDocSortNode) {
			selectImpNode = (ImpDocSortNode) n;
			seleceSaleNode = null;
			callBackPanel.showDocumentInfo();
		}
		if (n instanceof SaleDocSortNode) {
			seleceSaleNode = (SaleDocSortNode) n;
			selectImpNode = null;
			callBackPanel.showDocumentInfo();
		}

	}

	private void addColumnName() {// 添加表头
		columnName.add("单据编号");
		columnName.add("客户");
		columnName.add("仓库");
		columnName.add("操作员");
		columnName.add("总价");
		columnName.add("备注");
	}

	public JTable creatDocTable() {
		if (columnName.isEmpty()) {
			addColumnName();
		}

		Vector<Vector<String>> document_info = new Vector<Vector<String>>();
		Vector<String> row_info = new Vector<String>();
		if (seleceSaleNode == null) {// 选择了进货单的情况
			int length = selectImpNode.getImportDocList().size();
			for (int i = 0; i < length; i++) {// 添加信息
				ImportDocumentVO document = selectImpNode.getDocumentVO(i);
				row_info = new Vector<String>();
				row_info.add(document.getDocumentID());// 编号
				row_info.add(document.getTheCustomer().getCustomerName());// 客户
				row_info.add(document.getWarehouse());// 仓库
				row_info.add(document.getTheUser().getUserName());// 操作员
				row_info.add(Double.toString(document.getTotalPrice()));// 总价
				row_info.add(document.getTheMessage());// 备注

				document_info.add(row_info);
			}
		} else {// 选择了销售单的情况
			int length = seleceSaleNode.getSaleDocList().size();
			System.out.println(seleceSaleNode.getSaleDocList().size());
			for (int i = 0; i < length; i++) {
				SaleDocumentVO document = seleceSaleNode.getDocumentVO(i);
				row_info = new Vector<String>();
				row_info.add(document.getDocumentID());// 编号

				System.out.println("********************************************************************");
				System.out.println("i :" + i);
				System.out.println("document  : " + document);
				System.out.println("document   customer  :"
						+ document.getTheCustomer());
				System.out.println("document   customer   customerName :"
						+ document.getTheCustomer().getCustomerName());
				System.out.println("********************************************************************");
				
				row_info.add(document.getTheCustomer().getCustomerName());// 客户
				row_info.add(document.getWarehouse());// 仓库
				row_info.add(document.getTheUser().getUserName());// 操作员
				row_info.add(Double.toString(document.getTotalPriceAfter()));// 总价
				row_info.add(document.getTheMessage());// 备注

				document_info.add(row_info);
			}
		}
		DefaultTableModel newTabelModel = new DefaultTableModel(document_info,
				columnName) {// 设置不可修改
		//	private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		document_table = new JTable(newTabelModel);
		document_table.setGridColor(Color.black);
		document_table.setShowGrid(true);
		document_table.setBackground(Color.WHITE);
		document_table.setRowHeight(25);
		document_table.setFont(new Font("微软雅黑", 0, 12));
		return document_table;
	}
}
