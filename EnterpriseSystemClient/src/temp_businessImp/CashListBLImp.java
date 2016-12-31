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

import po.CashListPO;
import temp_business.CashListBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CashListVO;
import vo.LogVO;
import vo.UserVO;
import businesslogic.EntryLineItem;
import dataservice.CashListDataService;
import enumClass.ResultMessage;

public class CashListBLImp implements CashListBLService {

	private CashListDataService cashListDataService;
	LoginBLService loginBLService = new LoginBLImp();
	LogBLService logBLService = new LogBLImp();

	public CashListBLImp() {
		try {
			cashListDataService = (CashListDataService) Naming
					.lookup(StaticInfo.URL_CASHLIST);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	// PO转VO
	private CashListVO POTrangeToVO(CashListPO p) {
		if (p != null) {
			return new CashListVO(p.getDate(), p.getDocumentID(),
					p.getUserID(), p.getUserName(), p.getAccountName(),
					p.getEntryList(), p.getTotalPrice(), p.isPass(),
					p.isSend(), p.isDealed());

		}
		return null;
	}

	// VO转PO
	private CashListPO VOTrangeToPO(CashListVO v) {
		if (v != null) {
			return new CashListPO(v.getDate(), v.getDocumentID(),
					v.getUserID(), v.getUserName(), v.getAccountName(),
					v.getEntryList(), v.getTotalPrice(), v.isPass(),
					v.isSend(), v.isDealed());
		}
		return null;
	}

	// 专门用于日志创造时候的～～
	public void Addlog(String operater) {
		UserVO userVO = loginBLService.getUserVO();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
		String dataNow = dateFormat.format(now);

		LogVO logVO = new LogVO(dataNow, logBLService.getNewID(),
				userVO.getUserID(), userVO.getUserName(), operater);
		logBLService.add(logVO);
	}

	public CashListVO findByID(String id) throws RemoteException {
		CashListPO po = cashListDataService.findByDocumentID(id);
		return POTrangeToVO(po);
	}

	public ResultMessage addCashList(CashListVO vo) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
		CashListPO po = VOTrangeToPO(vo);
		try {
			resultMessage = cashListDataService.add(po);
			// 下面这段是 审批后逻辑
			// if(resultMessage.equals(ResultMessage.SUCCESS)){
			// AccountVO
			// accountVO=accountBLImp.findAccount(po.getAccountName());
			// double newPrice=accountVO.getAccountPrice()-vo.getTotalPrice();
			// accountBLImp.updateAccount(accountVO.getAccountName(), new
			// AccountVO(accountVO.getAccountName(), newPrice));
			// }
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("新增现金费用单成功");
		} else {
			Addlog("新增现金费用单失败");
		}
		return resultMessage;
	}

	public ArrayList<CashListVO> displayAll() {
		// TODO Auto-generated method stub
		ArrayList<CashListVO> vos = new ArrayList<CashListVO>();
		try {
			ArrayList<CashListPO> pos = cashListDataService.displayAll();
			if (pos != null) {
				for (int i = 0; i < pos.size(); i++) {
					vos.add(POTrangeToVO(pos.get(i)));
				}
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return vos;
	}

	public ResultMessage updateCashList(String documentID, CashListVO vo) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
		CashListPO p = VOTrangeToPO(vo);
		try {
			CashListPO po = cashListDataService.findByDocumentID(documentID);
			if (po != null) {
				resultMessage = cashListDataService.update(p);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("更新现金费用单成功");
		} else {
			Addlog("更新现金费用单失败");
		}
		return resultMessage;
	}

	public void endManagement() {
		// TODO Auto-generated method stub

	}

	// 得到两个集合的并集
	public ArrayList<CashListPO> getCashListUnionSet(ArrayList<CashListPO> a1,
			ArrayList<CashListPO> a2) throws RemoteException {
		ArrayList<CashListPO> union = new ArrayList<CashListPO>();
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

	public ArrayList<CashListVO> findCashList(String timezone)
			throws RemoteException {
		ArrayList<CashListVO> vos = new ArrayList<CashListVO>();
		if (timezone.equals("")) {
			ArrayList<CashListPO> cashList = cashListDataService.displayAll();
			if (cashList != null) {
				for (int i = 0; i < cashList.size(); i++) {
					vos.add(POTrangeToVO(cashList.get(i)));
				}
			}
		}
		ArrayList<CashListPO> cashList1 = cashListDataService
				.findByTime(timezone);
		if (cashList1 != null) {
			for (int i = 0; i < cashList1.size(); i++) {
				vos.add(POTrangeToVO(cashList1.get(i)));
			}
		}
		return vos;
	}

	@SuppressWarnings("deprecation")
	public void output_CashList(String fileName, CashListVO vo)
			throws RemoteException {
		ArrayList<EntryLineItem> lineItems = vo.getEntryList().getTheList();
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("现金费用单");

		HSSFRow row0 = sheet.createRow((short) 0);

		// 在索引0的位置创建单元格（左上端）

		HSSFCell cell0 = row0.createCell((short) 0);
		HSSFCell cell1 = row0.createCell((short) 1);
		HSSFCell cell2 = row0.createCell((short) 2);
		HSSFCell cell3 = row0.createCell((short) 3);
		HSSFCell cell4 = row0.createCell((short) 4);
		HSSFCell cell05 = row0.createCell((short) 5);
		HSSFCell cell8 = row0.createCell((short) 8);
		HSSFCell cell9 = row0.createCell((short) 9);
		HSSFCell cell010 = row0.createCell((short) 10);
		HSSFCell cell011 = row0.createCell((short) 11);
		// 定义单元格为字符串类型
		cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell05.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell010.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell011.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell0.setCellValue("时间");
		cell1.setCellValue("单据编号");
		cell2.setCellValue("操作员ID");
		cell3.setCellValue("操作员");
		cell4.setCellValue("银行账户");
		cell05.setCellValue("条目清单");
		cell8.setCellValue("总额");
		cell9.setCellValue("是否发送");
		cell010.setCellValue("是否通过");
		cell011.setCellValue("是否处理");
		int size = lineItems.size();
		HSSFRow row1 = sheet.createRow((short) 1);
		HSSFCell cell10 = row1.createCell((short) 0);
		HSSFCell cell11 = row1.createCell((short) 1);
		HSSFCell cell12 = row1.createCell((short) 2);
		HSSFCell cell13 = row1.createCell((short) 3);
		HSSFCell cell14 = row1.createCell((short) 4);
		HSSFCell cell5 = row1.createCell((short) 5);
		HSSFCell cell6 = row1.createCell((short) 6);
		HSSFCell cell7 = row1.createCell((short) 7);
		HSSFCell cell18 = row1.createCell((short) 8);
		HSSFCell cell19 = row1.createCell((short) 9);
		HSSFCell cell110 = row1.createCell((short) 10);
		HSSFCell cell111 = row1.createCell((short) 11);
		cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell12.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell14.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell18.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell19.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		cell110.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
		cell111.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);

		cell10.setCellValue(vo.getDate());
		cell11.setCellValue(vo.getDocumentID());
		cell12.setCellValue(vo.getUserID());
		cell13.setCellValue(vo.getUserName());
		cell14.setCellValue(vo.getAccountName());
		cell5.setCellValue("条目名");
		cell6.setCellValue("金额");
		cell7.setCellValue("备注");
		cell18.setCellValue(vo.getTotalPrice());
		cell19.setCellValue(vo.isSend());
		cell110.setCellValue(vo.isPass());
		cell111.setCellValue(vo.isDealed());

		for (int i = 0; i < size; i++) {
			HSSFRow row = sheet.createRow((short) i + 2);

			HSSFCell cell15 = row.createCell((short) 5);
			HSSFCell cell16 = row.createCell((short) 6);
			HSSFCell cell17 = row.createCell((short) 7);
			// 定义单元格为字符串类型

			cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell16.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cell17.setCellType(HSSFCell.CELL_TYPE_STRING);
			// 在单元格中输入一些内容
			// cell0.setCellValue(111);
			cell15.setCellValue(lineItems.get(i).getEntryName());
			cell16.setCellValue(lineItems.get(i).getEntryPrice());
			cell17.setCellValue(lineItems.get(i).getRemark());
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

	public void getinit() {
		try {
			cashListDataService.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNewID() {
		try {
			return cashListDataService.getNewID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ResultMessage deleteCashList(String ID) {
		// TODO Auto-generated method stub
		CashListPO cashListPO = null;
		ResultMessage resultMessage = ResultMessage.FAIL;

		try {
			cashListPO = cashListDataService.findByDocumentID(ID);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (cashListPO.isSend() == true) {
				resultMessage = ResultMessage.CannotDeleteDocument;
			} else {
				resultMessage = cashListDataService.delete(cashListPO);

			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("删除现金费用单成功");
		} else {
			Addlog("删除现金费用单失败");
		}
		return resultMessage;
	}

	public CashListVO findByIDForWriteBack(String id) throws RemoteException {
		// TODO Auto-generated method stub
		CashListPO po = cashListDataService.findByIDForWriteBack(id);
		return POTrangeToVO(po);
	}

}
