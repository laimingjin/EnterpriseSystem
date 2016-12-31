package temp_businessImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.security.auth.login.LoginContext;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import po.SaleDocumentPO;
import temp_business.ExportBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.LogVO;
import vo.PromotionVO_Customer;
import vo.PromotionVO_Package;
import vo.PromotionVO_Price;
import vo.SaleDocumentVO;
import vo.UserVO;
import businesslogic.Commodity;
import businesslogic.SaleLineItem;
import dataservice.SaleDataService;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class ExportBLImp implements ExportBLService {

	private SaleDataService saleDataService;
	private PromotionBLImp promotionBLImp = new PromotionBLImp();

	public ExportBLImp() {
		try {
			saleDataService = (SaleDataService) Naming
					.lookup(StaticInfo.URL_SALE);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	private LoginBLService loginBLService=new LoginBLImp();
	private LogBLService logBLService=new LogBLImp();
	
	//专门用于日志创造时候的～～
		public void Addlog(String operater){
			UserVO userVO=loginBLService.getUserVO();
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		    	String dataNow = dateFormat.format( now ); 

			LogVO logVO=new LogVO(dataNow, logBLService.getNewID(), userVO.getUserID(), userVO.getUserName(), operater);
			logBLService.add(logVO);
		}

	// PO转VO
	private SaleDocumentVO POTrangeToVO(SaleDocumentPO p) {
		return new SaleDocumentVO(p.getTheDate(), p.getDocumentID(),
				p.getTheCustomer(), p.getExecutive(), p.getWarehouse(),
				p.getTheUser(), p.getTheList(), p.getTheMessage(),
				p.getTotalPriceBefore(), p.getTheRebate(), p.getTheVoucher(),
				p.getTotalPriceAfter(), p.getStateOfDocument());
	}

	public ResultMessage addExportDraft(SaleDocumentVO vo) {
ResultMessage resultMessage=null;
		addPromotion(vo);
		SaleDocumentPO po = new SaleDocumentPO(vo);
		try {
			return resultMessage=saleDataService.add(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage=ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				
				Addlog("增加销售单失败");
			}else {
				Addlog("增加销售单成功");
			}
		}
	}

	private void addPromotion(SaleDocumentVO vo) {

		vo.clearPromotion();
		
		addPromotion_Customer(vo);

		addPromotion_Price(vo);

		addPromotion_Packages(vo);

	}

	private void addPromotion_Packages(SaleDocumentVO vo) {
		ArrayList<PromotionVO_Package> promotionVO_Packages = promotionBLImp
				.displayAllpPromotionVO_Packages();
		ArrayList<PromotionVO_Package> vo_Packages = new ArrayList<PromotionVO_Package>();

		for (Iterator<PromotionVO_Package> iterator = promotionVO_Packages.iterator(); iterator
				.hasNext();) {// 遍历所有特价包
			PromotionVO_Package promotionVO_Package = (PromotionVO_Package) iterator
					.next();
			if (containsPackage(vo, promotionVO_Package)&&promotionVO_Package.valid()) {// 如果有符合要求的包则加入
				vo_Packages.add(promotionVO_Package);
			}
		}

		// 设置特价包
		vo.setPromotionVO_Packages(vo_Packages);
		

	}

	private boolean containsPackage(SaleDocumentVO vo,
			PromotionVO_Package promotionVO_Package) {
		ArrayList<SaleLineItem> saleLineItems = vo.getTheList().getTheList();// 取得要销售的商品列表

		for (int i = 0; i < promotionVO_Package.getCommodityKindSize(); i++) {// 遍历特价包中商品
			Commodity commodity_package = promotionVO_Package.getCommodity(i);// 取得特价包的一个商品种类
			int commodityAmount_package = promotionVO_Package
					.getCommodityAmount(i);// 取得特价包的一个商品种类

			boolean isThisContain = false;// 表示当前的特价包的商品种类是否包含

			for (Iterator<SaleLineItem> iterator = saleLineItems.iterator(); iterator
					.hasNext();) {// 遍历要销售的商品列表
				SaleLineItem saleLineItem = (SaleLineItem) iterator.next();

				if (saleLineItem.getTheCommodity().sameCommodityKind(
						commodity_package)) { // 如果商品的种类相同
					if (saleLineItem.getTheNumber() >= commodityAmount_package) {// 若要销售的数量大于等于特价包该种商品的数量
						isThisContain = true;
					} else {// 数量不足，说明不包含这个特价包
						return false;
					}
					continue;
				}
			}
			if (!isThisContain) {// 要销售的商品列表，且没有特价包的这种商品，那么说明也不包含这个特价包
				return false;
			}
		}

		return true;// 之前没有返回false，说明都包含
	}

	private void addPromotion_Price(SaleDocumentVO vo) {
		// price Promotion 的检查
		ArrayList<PromotionVO_Price> promotionVO_Prices = promotionBLImp
				.displayAllpPromotionVO_Prices();
		double max_Price = 0.0;
		for (Iterator<PromotionVO_Price> iterator = promotionVO_Prices.iterator(); iterator
				.hasNext();) {
			PromotionVO_Price promotionVO_Price = (PromotionVO_Price) iterator
					.next();
			if (max_Price <= promotionVO_Price.getPrice()&&promotionVO_Price.valid()) {// 取最高总价，如果总价相同，取最近一次
				max_Price = promotionVO_Price.getPrice();
				vo.setPromotionVO_Price(promotionVO_Price);
			}
		}
	}

	private void addPromotion_Customer(SaleDocumentVO vo) {
		// customer promotion 的检查
		ArrayList<PromotionVO_Customer> promotionVO_Customers = promotionBLImp
				.displayAllPromotionVO_Customers();
		for (Iterator<PromotionVO_Customer> iterator = promotionVO_Customers.iterator(); iterator
				.hasNext();) {
			PromotionVO_Customer promotionVO_Customer = (PromotionVO_Customer) iterator
					.next();
			if (promotionVO_Customer.getCustomerLevel() == vo.getTheCustomer()
					.getCustomerRank()&&promotionVO_Customer.valid()) {
				vo.setPromotionVO_Customer(promotionVO_Customer);
			}
		}
	}

	public ResultMessage deleteExportDraft(SaleDocumentVO vo) {
		SaleDocumentPO po = new SaleDocumentPO(vo);
		ResultMessage resultMessage=null;
		try {
			return resultMessage=saleDataService.delete(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage=ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				Addlog("删除销售单失败");
			}else {
				Addlog("删除销售单成功");
			}
		}
	}

	public ResultMessage updateExportDraft(SaleDocumentVO vo) {
		addPromotion(vo);
		ResultMessage resultMessage=null;
		SaleDocumentPO po = new SaleDocumentPO(vo);
		try {
			return resultMessage=saleDataService.update(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage=ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				
				Addlog("修改销售单失败");
			}else {
				Addlog("修改销售单成功");
			}
		}
	}

	public SaleDocumentVO returnExportBackDraft(SaleDocumentVO vo) {
		SaleDocumentVO newVo = null;
		try {
			newVo = new SaleDocumentVO(saleDataService.getNewID("XSTHD"), vo);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

//		SaleDocumentPO po = new SaleDocumentPO(newVo);
//		try {
//			saleDataService.add(po);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		return newVo;
	}

	// public ResultMessage sendExport(String DocNumber) {
	// // TODO Auto-generated method stub
	// return null;
	// }
	//
	// public ResultMessage sendExportBack(String DocNumber) {
	// // TODO Auto-generated method stub
	// return null;
	// }

	public ArrayList<SaleDocumentVO> findByTimezone(String timezone)
			throws RemoteException {
		ArrayList<SaleDocumentVO> vos = new ArrayList<SaleDocumentVO>();
		ArrayList<SaleDocumentPO> pos = saleDataService
				.findByTimezone(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;

	}

	// 给销售明细表用的
	public ArrayList<SaleDocumentVO> findSalesList(String time1, String time2,
			String commodityName, String customerName, String executive,
			String warehouse) throws RemoteException {
		ArrayList<SaleDocumentVO> vos = new ArrayList<SaleDocumentVO>();
		String timezone = "";
		if (!time1.equals("")) {
			timezone = time1 + "," + time2;
		}
		ArrayList<SaleDocumentPO> pos = saleDataService.findSalesList(timezone,
				commodityName, customerName, executive, warehouse);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}

	public void getinit() {
		try {
			saleDataService.getInit();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	// /经营情况表用
	public ArrayList<SaleDocumentVO> findForBusinessList(String timezone)
			throws RemoteException {
		ArrayList<SaleDocumentVO> vos = new ArrayList<SaleDocumentVO>();
		ArrayList<SaleDocumentPO> pos = saleDataService
				.findByTimezone(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}

	// 得到两个集合的并集
	public ArrayList<SaleDocumentPO> getSaleUnionSet(
			ArrayList<SaleDocumentPO> a1, ArrayList<SaleDocumentPO> a2)
			throws RemoteException {
		ArrayList<SaleDocumentPO> union = new ArrayList<SaleDocumentPO>();
		if (a1 == null && a2 != null) {
			return a2;
		} else if (a1 != null && a2 == null) {
			return a1;
		} else if (a1 == null && a2 == null) {
			return null;
		}
		for (int i = 0; i < a1.size(); i++) {
			for (int j = 0; j < a2.size(); j++) {
				if (a1.get(i).getDocumentID().equals(a2.get(j).getDocumentID())) {
					union.add(a1.get(i));
					break;// ////////////////////跳出j的循环
				}
			}
		}
		return union;

	}

	// /经营历程表用
	public ArrayList<SaleDocumentVO> findSaleDocument(String timezone,
			String customer, String executive, String storehouse)
			throws RemoteException {
		ArrayList<SaleDocumentVO> vos = new ArrayList<SaleDocumentVO>();
		ArrayList<SaleDocumentPO> sales1 = saleDataService
				.findByTimezone(timezone);
		ArrayList<SaleDocumentPO> sales2 = saleDataService
				.findByCustomer(customer);
		ArrayList<SaleDocumentPO> sales3 = saleDataService
				.findByExecutive(executive);
		ArrayList<SaleDocumentPO> sales4 = saleDataService
				.findByStorehouse(storehouse);

		ArrayList<SaleDocumentPO> sales5 = new ArrayList<SaleDocumentPO>();
		ArrayList<SaleDocumentPO> sales6 = new ArrayList<SaleDocumentPO>();
		ArrayList<SaleDocumentPO> sales7 = new ArrayList<SaleDocumentPO>();

		sales5 = getSaleUnionSet(sales1, sales2);
		sales6 = getSaleUnionSet(sales5, sales3);
		sales7 = getSaleUnionSet(sales6, sales4);

		if (sales7 != null) {
			for (int i = 0; i < sales7.size(); i++) {
				vos.add(POTrangeToVO(sales7.get(i)));
			}
		}
		return vos;
	}

	public ArrayList<SaleDocumentVO> displayAll() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	public ArrayList<SaleDocumentVO> displayAllDone() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE) {
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	// $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
	public String getNewId(String kind) {

		try {
			return saleDataService.getNewID(kind);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public void output_SaleDocument(String fileName, SaleDocumentVO vo) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("销售单");

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
		HSSFCell cell017 = row0.createCell((short) 17);
		HSSFCell cell018 = row0.createCell((short) 18);
		HSSFCell cell019 = row0.createCell((short) 19);
		HSSFCell cell020 = row0.createCell((short) 20);
		HSSFCell cell021 = row0.createCell((short) 21);
		// HSSFCell cell012 = row0.createCell((short) 12);
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
		cell017.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell018.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell019.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell020.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell021.setCellType(HSSFCell.CELL_TYPE_STRING);
		// cell012.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell0.setCellValue("单据类型");
		cell1.setCellValue("单据状态");
		cell2.setCellValue("时间");
		cell3.setCellValue("单据编号");
		cell4.setCellValue("客户ID");
		cell5.setCellValue("客户");
		cell6.setCellValue("操作员ID");
		cell7.setCellValue("操作员");
		cell8.setCellValue("业务员");
		cell9.setCellValue("仓库");
		cell10.setCellValue("出货商品清单");
		cell017.setCellValue("折让前总额");
		cell018.setCellValue("折让金额");
		cell019.setCellValue("使用代金券金额");
		cell020.setCellValue("折让后总额");
		cell021.setCellValue("备注");
		HSSFRow row1 = sheet.createRow((short) 1);

		HSSFCell cell110 = row1.createCell((short) 10);
		HSSFCell cell111 = row1.createCell((short) 11);
		HSSFCell cell112 = row1.createCell((short) 12);
		HSSFCell cell113 = row1.createCell((short) 13);
		HSSFCell cell114 = row1.createCell((short) 14);
		HSSFCell cell115 = row1.createCell((short) 15);
		HSSFCell cell116 = row1.createCell((short) 16);
		// HSSFCell cell116 = row1.createCell((short) 16);
		// HSSFCell cell117 = row1.createCell((short)17);
		// HSSFCell cell118= row1.createCell((short) 18);
		// HSSFCell cell119 = row1.createCell((short) 19);
		// HSSFCell cell120 = row1.createCell((short) 20);
		// HSSFCell cell121 = row1.createCell((short) 21);
		// 定义单元格为字符串类型

		cell110.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell111.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell112.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell113.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell114.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell115.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell116.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell110.setCellValue("商品ID");
		cell111.setCellValue("商品名");
		cell112.setCellValue("商品型号");
		cell113.setCellValue("数量");
		cell114.setCellValue("单价");
		cell115.setCellValue("金额");
		cell116.setCellValue("备注");

		// HSSFRow row2= sheet.createRow((short)2);

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
		// HSSFCell cell210 = row2.createCell((short) 10);
		// HSSFCell cell216 = row2.createCell((short) 16);
		HSSFCell cell217 = row1.createCell((short) 17);
		HSSFCell cell218 = row1.createCell((short) 18);
		HSSFCell cell219 = row1.createCell((short) 19);
		HSSFCell cell220 = row1.createCell((short) 20);
		HSSFCell cell221 = row1.createCell((short) 21);
		// HSSFCell cell012 = row0.createCell((short) 12);
		// 定义单元格为字符串类型
		cell20.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell21.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell22.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell23.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell24.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell25.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell26.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell27.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell28.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell29.setCellType(HSSFCell.CELL_TYPE_STRING);

		cell217.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell218.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell219.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell220.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell221.setCellType(HSSFCell.CELL_TYPE_STRING);
		// cell012.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		if (vo.getDocumentID().startsWith("XSD")) {
			cell20.setCellValue("销售单");
		} else {
			cell20.setCellValue("销售退货单");
		}
		if (vo.getStateOfDocument() == StateOfDocument.DRAFT) {
			cell21.setCellValue("草稿状态");
		} else if (vo.getStateOfDocument() == StateOfDocument.SENDED) {
			cell21.setCellValue("已发送，待审批状态");
		} else if (vo.getStateOfDocument() == StateOfDocument.EXAMINED) {
			cell21.setCellValue("已审批，待处理状态");
		} else {
			cell21.setCellValue("已处理");
		}

		cell22.setCellValue(vo.getTheDate());
		cell23.setCellValue(vo.getDocumentID());
		cell24.setCellValue(vo.getTheCustomer().getCustomerID());
		cell25.setCellValue(vo.getTheCustomer().getCustomerName());
		cell26.setCellValue(vo.getTheUser().getUserID());
		cell27.setCellValue(vo.getTheUser().getUserName());
		cell29.setCellValue(vo.getExecutive());
		cell29.setCellValue(vo.getWarehouse());
		// cell29.setCellValue("商品列表");

		cell217.setCellValue(vo.getTotalPriceBefore());
		cell218.setCellValue(vo.getTheRebate());
		cell219.setCellValue(vo.getTheVoucher());
		cell220.setCellValue(vo.getTotalPriceAfter());
		cell221.setCellValue(vo.getTheMessage());
		int size = vo.getTheList().getTheList().size();
		ArrayList<SaleLineItem> items = vo.getTheList().getTheList();
		for (int i = 0; i < size; i++) {
			HSSFRow row = sheet.createRow((short) i + 2);

			HSSFCell cell210 = row.createCell((short) 10);
			HSSFCell cell211 = row.createCell((short) 11);
			HSSFCell cell212 = row.createCell((short) 12);
			HSSFCell cell213 = row.createCell((short) 13);
			HSSFCell cell214 = row.createCell((short) 14);
			HSSFCell cell215 = row.createCell((short) 15);
			HSSFCell cell216 = row.createCell((short) 16);
			// HSSFCell cell216 = row1.createCell((short) 16);
			// HSSFCell cell217 = row1.createCell((short) 17);
			// HSSFCell cell218 = row1.createCell((short) 18);
			// HSSFCell cell219 = row1.createCell((short) 19);
			// HSSFCell cell220 = row1.createCell((short) 20);
			// HSSFCell cell221 = row1.createCell((short) 21);
			cell210.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell211.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell212.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell213.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell214.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell215.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell216.setCellType(HSSFCell.CELL_TYPE_STRING);
			// cell216.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell217.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell218.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell219.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell220.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell221.setCellType(HSSFCell.CELL_TYPE_STRING);

			cell210.setCellValue(items.get(i).getTheCommodity()
					.getCommodityID());
			cell211.setCellValue(items.get(i).getTheCommodity()
					.getCommodityName());
			cell212.setCellValue(items.get(i).getTheCommodity()
					.getCommodityModel());
			cell213.setCellValue(items.get(i).getTheCommodity()
					.getInventoryQuantity());
			cell214.setCellValue(items.get(i).getTheCommodity()
					.getLatestPurchasePrice());
			cell215.setCellValue(items.get(i).getTotal());
			cell216.setCellValue(items.get(i).getTheMessage());

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

	public ResultMessage sendExport(String DocNumber) {

		try {
			SaleDocumentPO po = saleDataService.findByID(DocNumber);
			if (po == null) {
				return ResultMessage.FAIL;
			}
			po.setStateOfDocument(StateOfDocument.SENDED);
			return saleDataService.update(po);

		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	// // TODO pb
	// /**
	// *这个方法UI层要用吗
	// *YSH
	// * @param DocNumber
	// * @return
	// */
	// public ResultMessage sendExportBack(String DocNumber) {
	// try {
	// SaleDocumentPO po=saleDataService.findByID(DocNumber);
	// if (po==null) {
	// return ResultMessage.FAIL;
	// }
	// po.setStateOfDocument(StateOfDocument.SENDED);
	// return saleDataService.update(po);
	//
	// } catch (RemoteException e) {
	// e.printStackTrace();
	// return ResultMessage.FAIL;
	// }
	// }

	public ArrayList<SaleDocumentVO> displayExport(String kind,
			StateOfDocument stateOfDocument) {
		switch (stateOfDocument) {
		case DRAFT:
			if (kind.equals("XSD")) {
				return exportDarft();

			} else if (kind.equals("XSTHD")) {
				return exportBackDraft();
			}
			break;
		case SENDED:
			if (kind.equals("XSD")) {
				return exportSended();
			} else if (kind.equals("XSTHD")) {
				return exportBackSended();
			}
			break;
		case EXAMINED:
			if (kind.equals("XSD")) {
				return exportExamined();
			} else if (kind.equals("XSTHD")) {
				return exportBackExamine();

			}
			break;
		case DONE:
			if (kind.equals("XSD")) {
				return exportDone();
			} else if (kind.equals("XSTHD")) {
				return exportBackDone();
			}
			break;
		default:
			break;
		}
		return null;
	}

	private ArrayList<SaleDocumentVO> exportBackDone() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE
					|| !saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者不包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportDone() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE
					|| saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportBackExamine() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.EXAMINED
					|| !saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者不包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportExamined() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.EXAMINED
					|| saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportBackSended() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.SENDED
					|| !saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者不包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportSended() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.SENDED
					|| saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportBackDraft() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DRAFT
					|| !saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者不包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	private ArrayList<SaleDocumentVO> exportDarft() {
		ArrayList<SaleDocumentPO> saleDocumentPOs = new ArrayList<SaleDocumentPO>();
		try {
			saleDocumentPOs = saleDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<SaleDocumentVO> saleDocumentVOs = new ArrayList<SaleDocumentVO>();

		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DRAFT
					|| saleDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者包含退货，则跳过
				continue;
			}
			saleDocumentVOs.add(new SaleDocumentVO(saleDocumentPOs.get(i)));
		}
		return saleDocumentVOs;
	}

	public ResultMessage sendExport(SaleDocumentVO vo) {
		vo.setStateOfDocument(StateOfDocument.SENDED);
		return ResultMessage.SUCCESS;
	}

	public SaleDocumentVO findByDocumentID(String documentID)
			throws RemoteException {
		// TODO Auto-generated method stub
		SaleDocumentPO po = saleDataService.findByID(documentID);
		SaleDocumentVO vo = POTrangeToVO(po);
		// System.out.println("price"+vo.getExecutive());
		return vo;
	}

	public SaleDocumentVO findByIDForWriteBack(String id)
			throws RemoteException {
		// TODO Auto-generated method stub
		SaleDocumentPO po = saleDataService.findByIDForWriteBack(id);
		return POTrangeToVO(po);
	}
}
