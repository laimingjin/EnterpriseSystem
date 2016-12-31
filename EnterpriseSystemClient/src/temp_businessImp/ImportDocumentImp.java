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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import po.ImportDocumentPO;
import temp_business.ImportDocumentBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.ImportDocumentVO;
import vo.LogVO;
import vo.UserVO;
import businesslogic.ImportLineItem;
import dataservice.ImportDataService;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class ImportDocumentImp implements ImportDocumentBLService {

	private ImportDataService importDataService;

	public ImportDocumentImp() {
		try {
			importDataService = (ImportDataService) Naming
					.lookup(StaticInfo.URL_IMPORT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
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

	public ResultMessage addImportDraft(ImportDocumentVO vo) {
		ImportDocumentPO po = new ImportDocumentPO(vo);
		ResultMessage resultMessage=null;
		try {
			return resultMessage= importDataService.add(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage= ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				Addlog("增加进货单失败");
			}else {
				Addlog("增加进货单成功");
			}
		}
	}

	public ResultMessage deleteImportDraft(ImportDocumentVO vo) {
		
		ResultMessage resultMessage=null;
		
		ImportDocumentPO po = new ImportDocumentPO(vo);
		try {
			return  resultMessage=importDataService.delete(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage=ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				Addlog("删除进货单失败");
			}else {
				Addlog("删除进货单成功");
			}
		}
	}

	public ResultMessage updateImportDraft(ImportDocumentVO vo) {
		ImportDocumentPO po = new ImportDocumentPO(vo);
		ResultMessage resultMessage=null;
		try {
			return resultMessage =importDataService.update(po);
		} catch (RemoteException e) {
			e.printStackTrace();
			return resultMessage=ResultMessage.FAIL;
		}finally{
			if (resultMessage==ResultMessage.FAIL) {
				Addlog("更新进货单失败");
			}else {
				Addlog("更新进货单成功");
			}
		}
	}

	public ImportDocumentVO returnImportBackDraft(ImportDocumentVO vo) {
		ImportDocumentVO newVo = null;
		try {
			newVo = new ImportDocumentVO(importDataService.getNewID("JHTHD"),
					vo);
		} catch (RemoteException e1) {
			e1.printStackTrace();
		}

//		ImportDocumentPO po = new ImportDocumentPO(newVo);
//		try {
//			importDataService.add(po);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
		return newVo;
	}

	public ArrayList<ImportDocumentVO> displayAll() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	public ArrayList<ImportDocumentVO> displayAllDone() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE) {
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	public ResultMessage sendImport(String DocNumber) {
		try {
			ImportDocumentPO po = importDataService.findByID(DocNumber);
			if (po == null) {
				return ResultMessage.FAIL;
			}
			po.setStateOfDocument(StateOfDocument.SENDED);
			return importDataService.update(po);

		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<ImportDocumentVO> findForBusinessList(String timezone)
			throws RemoteException {
		ArrayList<ImportDocumentVO> vos = new ArrayList<ImportDocumentVO>();
		ArrayList<ImportDocumentPO> pos = importDataService
				.findByTimezone(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(new ImportDocumentVO(pos.get(i)));
			}

		}
		return vos;
	}

	// 得到两个集合的并集
	public ArrayList<ImportDocumentPO> getImportUnionSet(
			ArrayList<ImportDocumentPO> a1, ArrayList<ImportDocumentPO> a2)
			throws RemoteException {
		ArrayList<ImportDocumentPO> union = new ArrayList<ImportDocumentPO>();
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
					break;// ////////////////////这个确定是跳出j的循环
				}
			}
		}
		return union;

	}

	// 经营历程表
	public ArrayList<ImportDocumentVO> findImportDocument(String timezone,
			String customer, String storehouse) throws RemoteException {
		ArrayList<ImportDocumentVO> vos = new ArrayList<ImportDocumentVO>();
		ArrayList<ImportDocumentPO> import1 = importDataService
				.findByTimezone(timezone);
		ArrayList<ImportDocumentPO> import2 = importDataService
				.findByCustomer(customer);
		ArrayList<ImportDocumentPO> import3 = importDataService
				.findByStorehouse(storehouse);

		ArrayList<ImportDocumentPO> import4 = new ArrayList<ImportDocumentPO>();
		ArrayList<ImportDocumentPO> import5 = new ArrayList<ImportDocumentPO>();

		import4 = getImportUnionSet(import1, import2);
		import5 = getImportUnionSet(import4, import3);

		if (import5 != null) {
			for (int i = 0; i < import5.size(); i++) {
				vos.add(new ImportDocumentVO(import5.get(i)));
			}
		}
		return vos;
	}

	public ArrayList<ImportDocumentVO> findByTimezone(String timezone)
			throws RemoteException {
		ArrayList<ImportDocumentVO> vos = new ArrayList<ImportDocumentVO>();
		// if(timezone.equals("")){
		// ArrayList<ImportDocumentPO>pos1=importDataService.dispAll();
		// if(pos1!=null){
		// for(int i=0;i<pos1.size();i++){
		// vos.add( new ImportDocumentVO(pos1.get(i)));
		// }
		// }return vos;
		// }
		ArrayList<ImportDocumentPO> pos = importDataService
				.findByTimezone(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(new ImportDocumentVO(pos.get(i)));
			}
		}
		return vos;

	}

	public String getNewId(String kind) {

		try {
			return importDataService.getNewID(kind);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "ID_MISTAKE";
	}

	@SuppressWarnings("deprecation")
	public void output_ImportDocument(String fileName, ImportDocumentVO vo) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("进货单");

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
		HSSFCell cell016 = row0.createCell((short) 16);
		HSSFCell cell017 = row0.createCell((short) 17);
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
		cell016.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell017.setCellType(HSSFCell.CELL_TYPE_STRING);
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
		cell8.setCellValue("仓库");
		cell9.setCellValue("商品列表");
		cell016.setCellValue("总价");
		cell017.setCellValue("备注");

		HSSFRow row1 = sheet.createRow((short) 1);
		HSSFCell cell19 = row1.createCell((short) 9);
		HSSFCell cell110 = row1.createCell((short) 10);
		HSSFCell cell111 = row1.createCell((short) 11);
		HSSFCell cell112 = row1.createCell((short) 12);
		HSSFCell cell113 = row1.createCell((short) 13);
		HSSFCell cell114 = row1.createCell((short) 14);
		HSSFCell cell115 = row1.createCell((short) 15);
		// HSSFCell cell116 = row1.createCell((short) 16);
		// HSSFCell cell117 = row1.createCell((short)17);
		// HSSFCell cell118= row1.createCell((short) 18);
		// HSSFCell cell119 = row1.createCell((short) 19);
		// HSSFCell cell120 = row1.createCell((short) 20);
		// HSSFCell cell121 = row1.createCell((short) 21);
		// 定义单元格为字符串类型
		cell19.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell110.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell111.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell112.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell113.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell114.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell115.setCellType(HSSFCell.CELL_TYPE_STRING);

		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell19.setCellValue("商品ID");
		cell110.setCellValue("商品名");
		cell111.setCellValue("商品型号");
		cell112.setCellValue("数量");
		cell113.setCellValue("单价");
		cell114.setCellValue("金额");
		cell115.setCellValue("备注");

		// HSSFRow row2= sheet.createRow((short)1);

		HSSFCell cell20 = row1.createCell((short) 0);
		HSSFCell cell21 = row1.createCell((short) 1);
		HSSFCell cell22 = row1.createCell((short) 2);
		HSSFCell cell23 = row1.createCell((short) 3);
		HSSFCell cell24 = row1.createCell((short) 4);
		HSSFCell cell25 = row1.createCell((short) 5);
		HSSFCell cell26 = row1.createCell((short) 6);
		HSSFCell cell27 = row1.createCell((short) 7);
		HSSFCell cell28 = row1.createCell((short) 8);
		// HSSFCell cell29 = row1.createCell((short) 9);
		HSSFCell cell216 = row1.createCell((short) 16);
		HSSFCell cell217 = row1.createCell((short) 17);
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
		// cell29.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell216.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell217.setCellType(HSSFCell.CELL_TYPE_STRING);
		// cell012.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		if (vo.getDocumentID().startsWith("JHD")) {
			cell20.setCellValue("进货单");
		} else {
			cell20.setCellValue("进货退货单");
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
		cell28.setCellValue(vo.getWarehouse());
		// cell29.setCellValue("商品列表");
		cell216.setCellValue(vo.getTotalPrice());
		cell217.setCellValue(vo.getTheMessage());

		int size = vo.getTheList().getImportLineList().size();
		ArrayList<ImportLineItem> items = vo.getTheList().getImportLineList();
		for (int i = 0; i < size; i++) {
			HSSFRow row = sheet.createRow((short) (i + 2));
			HSSFCell cell29 = row.createCell((short) 9);
			HSSFCell cell210 = row.createCell((short) 10);
			HSSFCell cell211 = row.createCell((short) 11);
			HSSFCell cell212 = row.createCell((short) 12);
			HSSFCell cell213 = row.createCell((short) 13);
			HSSFCell cell214 = row.createCell((short) 14);
			HSSFCell cell215 = row.createCell((short) 15);
			// HSSFCell cell216 = row1.createCell((short) 16);
			// HSSFCell cell217 = row1.createCell((short) 17);
			// HSSFCell cell218 = row1.createCell((short) 18);
			// HSSFCell cell219 = row1.createCell((short) 19);
			// HSSFCell cell220 = row1.createCell((short) 20);
			// HSSFCell cell221 = row1.createCell((short) 21);
			cell29.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell210.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell211.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell212.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell213.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell214.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell215.setCellType(HSSFCell.CELL_TYPE_STRING);
			// cell216.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell217.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell218.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell219.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell220.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			// cell221.setCellType(HSSFCell.CELL_TYPE_STRING);

			cell29.setCellValue(items.get(i).getTheCommodity().getCommodityID());
			cell210.setCellValue(items.get(i).getTheCommodity()
					.getCommodityName());
			cell211.setCellValue(items.get(i).getTheCommodity()
					.getCommodityModel());
			cell212.setCellValue(items.get(i).getTheCommodity()
					.getInventoryQuantity());
			cell213.setCellValue(items.get(i).getTheCommodity()
					.getLatestPurchasePrice());
			cell214.setCellValue(items.get(i).getTotal());
			cell215.setCellValue(items.get(i).getTheMessage());

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

	public ArrayList<ImportDocumentVO> displayImport(String kind,
			StateOfDocument stateOfDocument) {
		switch (stateOfDocument) {
		case DRAFT:
			if (kind.equals("JHD")) {
				
				
				return importDarft();

			} else if (kind.equals("JHTHD")) {
				return importBackDraft();
			}
			break;
		case SENDED:
			if (kind.equals("JHD")) {
				return importSended();
			} else if (kind.equals("JHTHD")) {
				return importBackSended();
			}
			break;
		case EXAMINED:
			if (kind.equals("JHD")) {
				return importExamined();
			} else if (kind.equals("JHTHD")) {
				return importBackExamine();

			}
			break;
		case DONE:
			if (kind.equals("JHD")) {
				return importDone();
			} else if (kind.equals("JHTHD")) {
				return importBackDone();
			}
			break;
		default:
			break;
		}
		return null;
	}

	private ArrayList<ImportDocumentVO> importBackDone() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE
					|| !importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是处理或者不包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importDone() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DONE
					|| importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果处理状态不是审批或者包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importBackExamine() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.EXAMINED
					|| !importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是审批或者bu包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importExamined() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.EXAMINED
					|| importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是审批或者包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importBackDraft() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DRAFT
					|| !importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是草稿或者bu包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importDarft() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.DRAFT
					|| importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是草稿或者包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
//		System.err.println("@@@@@@@@@@@@@2"
//				+ "");
//		System.out.println(importVOs);
//		System.err.println("@@@@@@@@@@@@@2"
//				+ "");
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importBackSended() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.SENDED
					|| !importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是已发送或者不包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	private ArrayList<ImportDocumentVO> importSended() {
		ArrayList<ImportDocumentPO> importDocumentPOs = new ArrayList<ImportDocumentPO>();
		try {
			importDocumentPOs = importDataService.dispAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<ImportDocumentVO> importVOs = new ArrayList<ImportDocumentVO>();

		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getStateOfDocument() != StateOfDocument.SENDED
					|| importDocumentPOs.get(i).getDocumentID().contains("TH")) {// 如果单据状态不是已发送或者包含退货，则跳过
				continue;
			}
			importVOs.add(new ImportDocumentVO(importDocumentPOs.get(i)));
		}
		return importVOs;
	}

	public ImportDocumentVO findByID(String id) throws RemoteException {
		ImportDocumentPO po = importDataService.findByID(id);
		return new ImportDocumentVO(po);
	}

	public ImportDocumentVO findByIDForWriteBack(String id)
			throws RemoteException {
		// TODO Auto-generated method stub
		ImportDocumentPO po=importDataService.findByIDForWriteBack(id);
		return new ImportDocumentVO(po);
	}

}
