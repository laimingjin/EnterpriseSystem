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

import po.CommodityGiftPO;
import temp_business.CommodityGiftBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CommodityGiftVO;
import vo.LogVO;
import vo.UserVO;
import dataservice.CommodityGiftDataService;
import enumClass.ResultMessage;

public class CommodityGiftBLImp implements CommodityGiftBLService {

	private CommodityGiftDataService commodityGiftDataService;
	LoginBLService loginBLService = new LoginBLImp();
	LogBLService logBLService = new LogBLImp();

	public CommodityGiftBLImp() {
		try {
			commodityGiftDataService = (CommodityGiftDataService) Naming
					.lookup(StaticInfo.URL_COMMODITYGIFT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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

	public ResultMessage updataMessage(CommodityGiftVO vo) {
		ResultMessage result = ResultMessage.FAIL;
		CommodityGiftPO po = new CommodityGiftPO(vo);
		try {
			result = commodityGiftDataService.update(po);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result.equals(ResultMessage.SUCCESS)) {
			Addlog("更新赠送单成功");
		} else {
			Addlog("更新赠送单失败");
		}
		return result;
	}

	public ArrayList<CommodityGiftVO> displayAll() {
		ArrayList<CommodityGiftVO> commodityGiftVOs = new ArrayList<CommodityGiftVO>();
		ArrayList<CommodityGiftPO> commodityGiftPOs = new ArrayList<CommodityGiftPO>();
		try {
			commodityGiftPOs = commodityGiftDataService.displayAll();

			for (int i = 0; i < commodityGiftPOs.size(); i++) {
				CommodityGiftVO cDocumentVO = new CommodityGiftVO(
						commodityGiftPOs.get(i));
				commodityGiftVOs.add(cDocumentVO);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commodityGiftVOs;
	}

	//
	private CommodityGiftVO POTrangeToVO(CommodityGiftPO p) {
		return new CommodityGiftVO(p.getDate(), p.getDocumentID(),
				p.getCommodity(), p.getSendQuantity(), p.getCustomer(),
				p.isPass(), p.isSend(), p.isDealed());
	}

	public ArrayList<CommodityGiftVO> findForBusinessList(String timezone)
			throws RemoteException {
		ArrayList<CommodityGiftVO> vos = new ArrayList<CommodityGiftVO>();
		// String time[]=timezone.split(",");
		ArrayList<CommodityGiftPO> pos = commodityGiftDataService
				.finds(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}

	public ArrayList<CommodityGiftVO> findCommodityGift(String timezone)
			throws RemoteException {
		ArrayList<CommodityGiftVO> vos = new ArrayList<CommodityGiftVO>();
		// String time[]=timezone.split(",");
		ArrayList<CommodityGiftPO> pos = commodityGiftDataService
				.finds(timezone);
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}

	@SuppressWarnings("deprecation")
	public void output_CommodityGift(String fileName, CommodityGiftVO vo) {
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("库存赠送单");

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
		HSSFCell cell010 = row0.createCell((short) 10);
		HSSFCell cell011 = row0.createCell((short) 11);
		HSSFCell cell012 = row0.createCell((short) 12);
		HSSFCell cell013 = row0.createCell((short) 13);
		HSSFCell cell014 = row0.createCell((short) 14);
		HSSFCell cell015 = row0.createCell((short) 15);
		HSSFCell cell016 = row0.createCell((short) 16);
		HSSFCell cell017 = row0.createCell((short) 17);
		HSSFCell cell018 = row0.createCell((short) 18);
		HSSFCell cell019 = row0.createCell((short) 19);
		HSSFCell cell020 = row0.createCell((short) 20);
		HSSFCell cell021 = row0.createCell((short) 21);

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
		cell010.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell011.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell012.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell013.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell014.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell015.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell016.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell017.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell018.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell019.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell020.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell021.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 在单元格中输入一些内容
		// cell0.setCellValue(111);
		cell0.setCellValue("单据类型");
		cell1.setCellValue("单据状态");
		cell2.setCellValue("日期");
		cell3.setCellValue("单据编号");
		cell4.setCellValue("商品ID");
		cell5.setCellValue("商品名称");
		cell6.setCellValue("商品型号");
		cell7.setCellValue("库存数量");
		cell8.setCellValue("库存警戒值");
		cell9.setCellValue("赠送数量");
		cell010.setCellValue("客户ID");
		cell011.setCellValue("客户姓名");
		cell012.setCellValue("客户类型");
		cell013.setCellValue("客户级别");
		cell014.setCellValue("电话号码");
		cell015.setCellValue("地址");
		cell016.setCellValue("邮编");
		cell017.setCellValue("邮箱");
		cell018.setCellValue("应收额度");
		cell019.setCellValue("应收账款");
		cell020.setCellValue("应付账款");
		cell021.setCellValue("常业务员");

		HSSFRow row1 = sheet.createRow((short) 1);
		HSSFCell cell10 = row1.createCell((short) 0);
		HSSFCell cell11 = row1.createCell((short) 1);
		HSSFCell cell12 = row1.createCell((short) 2);
		HSSFCell cell13 = row1.createCell((short) 3);
		HSSFCell cell14 = row1.createCell((short) 4);
		HSSFCell cell15 = row1.createCell((short) 5);
		HSSFCell cell16 = row1.createCell((short) 6);
		HSSFCell cell17 = row1.createCell((short) 7);
		HSSFCell cell18 = row1.createCell((short) 8);
		HSSFCell cell19 = row1.createCell((short) 9);
		HSSFCell cell110 = row1.createCell((short) 10);
		HSSFCell cell111 = row1.createCell((short) 11);
		HSSFCell cell112 = row1.createCell((short) 12);
		HSSFCell cell113 = row1.createCell((short) 13);
		HSSFCell cell114 = row1.createCell((short) 14);
		HSSFCell cell115 = row1.createCell((short) 15);
		HSSFCell cell116 = row1.createCell((short) 16);
		HSSFCell cell117 = row1.createCell((short) 17);
		HSSFCell cell118 = row1.createCell((short) 18);
		HSSFCell cell119 = row1.createCell((short) 19);
		HSSFCell cell120 = row1.createCell((short) 20);
		HSSFCell cell121 = row1.createCell((short) 21);
		cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell13.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell17.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell18.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell19.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell110.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell111.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell112.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell113.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell114.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell115.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell116.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell117.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell118.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell119.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell120.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell121.setCellType(HSSFCell.CELL_TYPE_STRING);

		cell10.setCellValue("库存赠送单");

		if (vo.isSend() == false) {
			cell11.setCellValue("草稿状态");
		} else if (vo.isPass() == false) {
			cell11.setCellValue("已发送，未审批状态");
		} else if (vo.isDealed() == false) {
			cell11.setCellValue("已审批，未处理状态");
		} else {
			cell11.setCellValue("已处理");
		}

		cell12.setCellValue(vo.getDate());
		cell13.setCellValue(vo.getDocumentID());
		cell14.setCellValue(vo.getCommodity().getCommodityID());
		cell15.setCellValue(vo.getCommodity().getCommodityName());
		cell16.setCellValue(vo.getCommodity().getCommodityModel());
		cell17.setCellValue(vo.getCommodity().getInventoryQuantity());
		cell18.setCellValue(vo.getCommodity().getWarnQuantity());
		cell19.setCellValue(vo.getSendQuantity());
		cell110.setCellValue(vo.getCustomer().getCustomerID());
		cell111.setCellValue(vo.getCustomer().getCustomerName());
		cell112.setCellValue(vo.getCustomer().getCustomerType());
		cell113.setCellValue(vo.getCustomer().getCustomerRank());
		cell114.setCellValue(vo.getCustomer().getTelePhone());
		cell115.setCellValue(vo.getCustomer().getCustomerAddress());
		cell116.setCellValue(vo.getCustomer().getCustomerPostCode());
		cell117.setCellValue(vo.getCustomer().geteMail());
		cell118.setCellValue(vo.getCustomer().getReceivableLimit());
		cell119.setCellValue(vo.getCustomer().getReceivableAmount());
		cell120.setCellValue(vo.getCustomer().getPayableAmount());
		cell121.setCellValue(vo.getCustomer().getOperator());

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

	public CommodityGiftVO getCommodityGiftByID(int ID) {
		// TODO Auto-generated method stub
		CommodityGiftVO commodityGiftVO = null;
		try {
			CommodityGiftPO commodityGiftPO = commodityGiftDataService.find(ID);
			commodityGiftVO = new CommodityGiftVO(commodityGiftPO);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return commodityGiftVO;
	}

	public ResultMessage addCommodityGift(CommodityGiftVO vo)
			throws RemoteException {
		// TODO Auto-generated method stub
		CommodityGiftPO p = new CommodityGiftPO(vo);
		return commodityGiftDataService.add(p);
	}

	public CommodityGiftVO findByIDForWriteBack(long id) throws RemoteException {
		// TODO Auto-generated method stub
		CommodityGiftPO po = commodityGiftDataService.findByIDForWriteBack(id);
		return POTrangeToVO(po);
	}
}
