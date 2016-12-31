package temp_businessImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import temp_business.BusinessListBLService;
import temp_business.CommodityDocumentBLService;
import temp_business.CommodityGiftBLService;
import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import vo.BusinessListVO;
import vo.CommodityDocumentVO;
import vo.CommodityGiftVO;
import vo.ImportDocumentVO;
import vo.SaleDocumentVO;
import businesslogic.ImportLineItem;
import enumClass.PROBLEM;
import enumClass.StateOfDocument;

public class BusinessListBLImp implements BusinessListBLService {

	public BusinessListVO showBusinessList(String timezone, boolean outputNeeded) {
		// TODO Auto-generated method stub
		double saleIncome = 0;// 销售收入
		double commodityOverflowIncome = 0;// 商品报溢收入
		double costAdjustIncome = 0;// 成本调价收入
		double importAndExportSpreadIncome = 0;// 进货退货差价
		double withVoucherIncome = 0;// 代金券与实际收款差额收入
		double rebateIncomeAfter = 0;// 折让后总收入
		double rebatePrice = 0;// 折让了多少
		double saleCost = 0;// 销售成本
		double commodityLossCost = 0;// 商品报损支出
		double commodityGiftCost = 0;// 商品赠出支出
		double totalEnpense = 0;// 总支出
		double profit = 0;// 利润

		ImportDocumentBLService importDocumentImp = new ImportDocumentImp();
		ExportBLService exportBLImp = new ExportBLImp();
		CommodityDocumentBLService commodityDocumentBLImp = new CommodityDocumentBLImp();
		CommodityGiftBLService commodityGiftBLImp = new CommodityGiftBLImp();
		// String timezone=time1+","+time2;
		try {
			ArrayList<ImportDocumentVO> importDocumentVOs = importDocumentImp
					.findForBusinessList(timezone);
			ArrayList<SaleDocumentVO> saleDocumentVOs = exportBLImp
					.findForBusinessList(timezone);
			ArrayList<CommodityDocumentVO> commodityDocumentVOs = commodityDocumentBLImp
					.findForBusinessList(timezone);
			ArrayList<CommodityGiftVO> commodityGiftVOs = commodityGiftBLImp
					.findForBusinessList(timezone);

			// System.out.println("size="+importDocumentVOs.size());
			// for(int a=0;a<importDocumentVOs.size();a++){
			// System.out.println(importDocumentVOs.get(a).getDocumentID());
			// }
			for (int i = 0; i < saleDocumentVOs.size(); i++) {
				if (saleDocumentVOs.get(i).getStateOfDocument() == StateOfDocument.DONE
						|| saleDocumentVOs.get(i).getStateOfDocument() == StateOfDocument.EXAMINED) {

					if (saleDocumentVOs.get(i).getDocumentID()
							.startsWith("XSD")) {
						saleIncome = saleIncome
								+ saleDocumentVOs.get(i).getTotalPriceAfter();// 销售收入
						rebatePrice = rebatePrice
								+ saleDocumentVOs.get(i).getTheRebate();// 折让了多少
						withVoucherIncome = withVoucherIncome
								+ saleDocumentVOs.get(i).getTheVoucher()
								- saleDocumentVOs.get(i).getTheRebate();// 代金券与实际收款差额收入
						saleCost = saleCost
								+ saleDocumentVOs.get(i).getTheList()
										.getTotalPurchasePrice();// 销售成本
					} else {
						saleIncome = saleIncome
								- saleDocumentVOs.get(i).getTotalPriceAfter();// 销售收入
						withVoucherIncome = withVoucherIncome
								+ saleDocumentVOs.get(i).getTheVoucher();
						saleCost = saleCost
								- saleDocumentVOs.get(i).getTheList()
										.getTotalPurchasePrice();// 销售成本
					}

				}

			}
			for (int j = 0; j < commodityDocumentVOs.size(); j++) {
				if (commodityDocumentVOs.get(j).isPass() == true) {
					if (commodityDocumentVOs.get(j).getProblem()
							.equals(PROBLEM.OVERFLOW)) {
						commodityOverflowIncome = commodityOverflowIncome
								+ (commodityDocumentVOs.get(j)
										.getRealQuantity() - commodityDocumentVOs
										.get(j).getCommodity()
										.getInventoryQuantity())
								* commodityDocumentVOs.get(j).getCommodity()
										.getPurchasePrice();// 商品报溢收入
					} else if (commodityDocumentVOs.get(j).getProblem()
							.equals(PROBLEM.LOSS)) {
						commodityLossCost = commodityLossCost
								+ (commodityDocumentVOs.get(j).getCommodity()
										.getInventoryQuantity() - commodityDocumentVOs
										.get(j).getRealQuantity())
								* commodityDocumentVOs.get(j).getCommodity()
										.getPurchasePrice();// 商品报损支出
					}

				}

			}

			// costAdjustIncome;//成本调价收入.............................怎么得到这个？？？？？？？？？

			for (int k = 0; k < importDocumentVOs.size(); k++) {
				ImportDocumentVO po = importDocumentVOs.get(k);
				if (po.isPass() == true) {
					if (po.getDocumentID().contains("JHD")) {
						importAndExportSpreadIncome = importAndExportSpreadIncome
								- totalEnpense;// 进货退货差价
						ArrayList<ImportLineItem> items = po.getTheList()
								.getImportLineList();
						if (items != null) {
							for (int l = 0; l < items.size(); l++) {
								costAdjustIncome = costAdjustIncome
										+ items.get(l)
												.getTheCommodity()
												.getLatestPurchasePriceForChengBenTiaoJiao()
										- items.get(l).getTheCommodity()
												.getLatestPurchasePrice();
							}
						}

					} else {
						importAndExportSpreadIncome = importAndExportSpreadIncome
								+ totalEnpense;// 进货退货差价
						ArrayList<ImportLineItem> items = po.getTheList()
								.getImportLineList();
						if (items != null) {
							for (int l = 0; l < items.size(); l++) {
								costAdjustIncome = costAdjustIncome
										- items.get(l)
												.getTheCommodity()
												.getLatestPurchasePriceForChengBenTiaoJiao()
										+ items.get(l).getTheCommodity()
												.getLatestPurchasePrice();
							}
						}
					}
				}

			}

			rebateIncomeAfter = saleIncome + commodityOverflowIncome
					+ costAdjustIncome + importAndExportSpreadIncome
					+ withVoucherIncome;// 折让后总收入

			for (int m = 0; m < commodityGiftVOs.size(); m++) {
				CommodityGiftVO po = commodityGiftVOs.get(m);
				if (po.isPass() == true) {
					commodityGiftCost = commodityGiftCost
							+ po.getSendQuantity()
							* (po.getCommodity().getPurchasePrice());// 商品赠出支出
				}

			}

			totalEnpense = saleCost + commodityLossCost + commodityGiftCost;// 总支出
			profit = rebateIncomeAfter - totalEnpense;// 利润
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		BusinessListVO vo = new BusinessListVO(saleIncome,
				commodityOverflowIncome, costAdjustIncome,
				importAndExportSpreadIncome, withVoucherIncome,
				rebateIncomeAfter, rebatePrice, saleCost, commodityLossCost,
				commodityGiftCost, totalEnpense, profit);
		if (outputNeeded) {
			output("D://20141217.xls", vo);
		}
		return vo;
	}

	@SuppressWarnings("deprecation")
	public void output(String fileName, BusinessListVO vo) {

		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("经营情况表");

		HSSFRow row0 = sheet.createRow((short) 0);

		// 在索引0的位置创建单元格（左上端）

		HSSFCell cell0 = row0.createCell((short) 0);
		HSSFCell cell1 = row0.createCell((short) 1);
		HSSFCell cell2 = row0.createCell((short) 2);
		HSSFCell cell3 = row0.createCell((short) 3);
		HSSFCell cell4 = row0.createCell((short) 4);
		HSSFCell cell5 = row0.createCell((short) 5);
		HSSFCell cell6 = row0.createCell((short) 6);
		HSSFCell cell7 = row0.createCell((short) 7);
		HSSFCell cell8 = row0.createCell((short) 8);
		HSSFCell cell9 = row0.createCell((short) 9);
		HSSFCell cell10 = row0.createCell((short) 10);
		HSSFCell cell11 = row0.createCell((short) 11);
		// 定义单元格为字符串类型
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell11.setCellType(HSSFCell.CELL_TYPE_STRING);

		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell0.setCellValue("销售收入");
		cell1.setCellValue("商品报溢收入");
		cell2.setCellValue("成本调价收入");
		cell3.setCellValue("进货退货差价");
		cell4.setCellValue("代金券与实际收款差额收入");
		cell5.setCellValue("折让后总收入");
		cell6.setCellValue("折让了多少");
		cell7.setCellValue("销售成本");
		cell8.setCellValue("商品报损支出");
		cell9.setCellValue("商品赠出支出");
		cell10.setCellValue("总支出");
		cell11.setCellValue("利润");

		HSSFRow row1 = sheet.createRow((short) 1);

		// 在索引0的位置创建单元格（左上端）

		HSSFCell cell20 = row1.createCell((short) 0);
		HSSFCell cell21 = row1.createCell((short) 1);
		HSSFCell cell22 = row1.createCell((short) 2);
		HSSFCell cell23 = row1.createCell((short) 3);
		HSSFCell cell24 = row1.createCell((short) 4);
		HSSFCell cell25 = row1.createCell((short) 5);
		HSSFCell cell26 = row1.createCell((short) 6);
		HSSFCell cell27 = row1.createCell((short) 7);
		HSSFCell cell28 = row1.createCell((short) 8);
		HSSFCell cell29 = row1.createCell((short) 9);
		HSSFCell cell210 = row1.createCell((short) 10);
		HSSFCell cell211 = row1.createCell((short) 11);
		// 定义单元格为字符串类型
		cell20.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell21.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell22.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell23.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell24.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell25.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell26.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell27.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell28.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell29.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell210.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell211.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell20.setCellValue(vo.getSaleIncome());
		cell21.setCellValue(vo.getCommodityOverflowIncome());
		cell22.setCellValue(vo.getCostAdjustIncome());
		cell23.setCellValue(vo.getImportAndExportSpreadIncome());
		cell24.setCellValue(vo.getWithVoucherIncome());
		cell25.setCellValue(vo.getRebateIncomeAfter());
		cell26.setCellValue(vo.getRebatePrice());
		cell27.setCellValue(vo.getSaleCost());
		cell28.setCellValue(vo.getCommodityLossCost());
		cell29.setCellValue(vo.getCommodityGiftCost());
		cell210.setCellValue(vo.getTotalEnpense());
		cell211.setCellValue(vo.getProfit());

		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(fileName);
			workbook.write(fOut);

			fOut.flush();

			// 操作结束，关闭文件

			fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 把相应的Excel 工作簿存盘

		System.out.println("文件生成...");
	}
}
