package temp_businessImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import temp_business.ExportBLService;
import temp_business.SalesListBLService;
import vo.SaleDocumentVO;
import vo.SalesListVO;
import businesslogic.SaleLineItem;
import businesslogic.SalesDetailLineItem;
import dataservice.SalesListDataService;

public class SalesListBLImp implements SalesListBLService {

	@SuppressWarnings("unused")
	private SalesListDataService salesListDataService;

	public SalesListBLImp() {
		try {
			salesListDataService = (SalesListDataService) Naming
					.lookup(StaticInfo.URL_SALESLIST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public SalesListVO showSalesList(String time1, String time2,
			String commodityName, String customerName, String executive,
			String warehouse, boolean outputNeeded) {
		// TODO Auto-generated method stub
		ExportBLService exportBLImp = new ExportBLImp();

		ArrayList<SalesDetailLineItem> theList = new ArrayList<SalesDetailLineItem>();
		try {
			ArrayList<SaleDocumentVO> vos = exportBLImp.findSalesList(time1,
					time2, commodityName, customerName, executive, warehouse);
			if (vos != null) {
				for (int i = 0; i < vos.size(); i++) {
					ArrayList<SaleLineItem> items = vos.get(i).getTheList()
							.getTheList();

					String dateString = vos.get(i).getTheDate();
					for (int j = 0; j < items.size(); j++) {
						theList.add(new SalesDetailLineItem(dateString, items
								.get(j).getTheCommodity().getCommodityName(),
								items.get(j).getTheCommodity()
										.getCommodityModel(), items.get(j)
										.getTheNumber(), items.get(j)
										.getPrice(), items.get(j).getTotal()));
					}

				}

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (outputNeeded) {// 需要导出报表
			output("D://Excel1.xls", new SalesListVO(theList));
		}

		return new SalesListVO(theList);
	}

	@SuppressWarnings("deprecation")
	private void output(String fileName, SalesListVO vo) {
		ArrayList<SalesDetailLineItem> lineItems = vo.getTheList();
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("销售明细表");

		HSSFRow row0 = sheet.createRow((short) 0);

		// 在索引0的位置创建单元格（左上端）

		HSSFCell cell0 = row0.createCell((short) 0);
		HSSFCell cell1 = row0.createCell((short) 1);
		HSSFCell cell2 = row0.createCell((short) 2);
		HSSFCell cell3 = row0.createCell((short) 3);
		HSSFCell cell4 = row0.createCell((short) 4);
		HSSFCell cell5 = row0.createCell((short) 5);
		// 定义单元格为字符串类型
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell0.setCellValue("时间");
		cell1.setCellValue("商品名");
		cell2.setCellValue("型号");
		cell3.setCellValue("数量");
		cell4.setCellValue("单价");
		cell5.setCellValue("总价");
		int size = lineItems.size();
		for (int i = 0; i < size; i++) {
			HSSFRow row = sheet.createRow((short) i + 1);
			HSSFCell cell10 = row.createCell((short) 0);
			HSSFCell cell11 = row.createCell((short) 1);
			HSSFCell cell12 = row.createCell((short) 2);
			HSSFCell cell13 = row.createCell((short) 3);
			HSSFCell cell14 = row.createCell((short) 4);
			HSSFCell cell15 = row.createCell((short) 5);
			// 定义单元格为字符串类型
			cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell13.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell15.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// 在单元格中输入一些内容
			// cell0.setCellValue(111);
			cell10.setCellValue(lineItems.get(i).getDate());
			cell11.setCellValue(lineItems.get(i).getCommodityName());
			cell12.setCellValue(lineItems.get(i).getCommodityModel());
			cell13.setCellValue(lineItems.get(i).getRetailQuantity());
			cell14.setCellValue(lineItems.get(i).getLatestRetailPrice());
			cell15.setCellValue(lineItems.get(i).getTotal());
		}

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
