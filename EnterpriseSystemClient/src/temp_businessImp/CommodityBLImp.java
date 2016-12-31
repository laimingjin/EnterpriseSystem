package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.CommodityPO;
import po.SaleDocumentPO;
import temp_business.CommodityBLService;
import temp_business.CommoditySortBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CommoditySortVO;
import vo.CommodityVO;
import vo.ImportDocumentVO;
import vo.LogVO;
import vo.SaleDocumentVO;
import vo.UserVO;
import businesslogic.CommodityCheck;
import businesslogic.ImportList;
import businesslogic.SaleList;
import dataservice.CommodityDataService;
import enumClass.ResultMessage;

public class CommodityBLImp implements CommodityBLService {

	CommodityDataService commodityDataService;
	LoginBLService loginBLService = new LoginBLImp();
	LogBLService logBLService = new LogBLImp();

	public CommodityBLImp() {
		try {
			commodityDataService = (CommodityDataService) Naming
					.lookup(StaticInfo.URL_COMMODITY);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	// PO转VO
	private CommodityVO POTrangeToVO(CommodityPO po) {
		return new CommodityVO(po.getCommoditySortName(),
				po.getCommoditySortID(), po.getCommodityName(),
				po.getCommodityID(), po.getCommodityModel(),
				po.getInventoryQuantity(), po.getPurchasePrice(),
				po.getRetailPrice(), po.getLatestPurchasePrice(),
				po.getLatestRetailPrice(), po.getDate(), po.getAveragePrice(),
				po.getWarnQuantity());

	}

	// SaleDocumentVO转PO
	@SuppressWarnings("unused")
	private SaleDocumentPO TrangeToSaleDocumentPO(SaleDocumentVO v) {
		return new SaleDocumentPO(v.getTheDate(), v.getDocumentID(),
				v.getTheCustomer(), v.getExecutive(), v.getWarehouse(),
				v.getTheUser(), v.getTheList(), v.getTheMessage(),
				v.getTotalPriceBefore(), v.getTheRebate(), v.getTheVoucher(),
				v.getTotalPriceAfter(), v.isPass(), v.isSend(), v.isDealed());
	}

	public ArrayList<CommodityVO> getCommodityByKey(String key) {

		ArrayList<CommodityPO> commodityPOs = null;
		ArrayList<CommodityVO> commodityVOs = new ArrayList<CommodityVO>();
		try {
			commodityPOs = commodityDataService.findVague(key);

			if (commodityPOs != null) {

				for (int i = 0; i < commodityPOs.size(); i++) {
					CommodityVO cVo = new CommodityVO(commodityPOs.get(i));
					commodityVOs.add(cVo);
				}
			}

		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return commodityVOs;
	}

	// 
	public ArrayList<CommodityCheck> checkCommodityInventory(String time1,
			String time2) {
		ArrayList<CommodityCheck> commodityChecks = new ArrayList<CommodityCheck>();
		ExportBLImp exportBLImp = new ExportBLImp();
		ImportDocumentImp importDocumentImp = new ImportDocumentImp();
		@SuppressWarnings("unused")
		ArrayList<SaleDocumentPO> salePOs = null;
		try {
			UserVO userVO = loginBLService.getUserVO();
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
			String dataNow = dateFormat.format(now);

			@SuppressWarnings("unused")
			LogVO logVO = new LogVO(dataNow, logBLService.getNewID(),
					userVO.getUserID(), userVO.getUserName(), "库存查看");

			ArrayList<SaleDocumentVO> vos = exportBLImp.findByTimezone(time1
					+ "," + time2);
			ArrayList<ImportDocumentVO> importDocumentVOs = importDocumentImp
					.findByTimezone(time1 + "," + time2);
			if (vos != null) {

				for (int i = 0; i < vos.size(); i++) {
					// salePOs.add(TrangeToSaleDocumentPO(vos.get(i)));
					SaleList saleList = vos.get(i).getTheList();

					CommodityCheck check = new CommodityCheck(vos.get(i)
							.getTheDate(), saleList, null);

					// ArrayList<ImportLineItem> importLineItems=new
					// ArrayList<ImportLineItem>();
					commodityChecks.add(check);
				}

			}
			if (importDocumentVOs != null) {

				for (int i = 0; i < importDocumentVOs.size(); i++) {
					ImportList importList = importDocumentVOs.get(i)
							.getTheList();
					CommodityCheck check = new CommodityCheck(importDocumentVOs
							.get(i).getTheDate(), null, importList);
					commodityChecks.add(check);
				}
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return commodityChecks;

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

	public ResultMessage addCommodity(CommodityVO commodityVO) {
		// TODO Auto-generated method stub
		CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
		ResultMessage result = ResultMessage.FAIL;
		// 如果不能增加是已经存在了
		if (isAbleToAddCommodity(commodityVO) == false) {
			result = ResultMessage.CommodityIsAlreadyExist;
		} else {

			CommodityPO commodityPO = new CommodityPO(commodityVO);
			try {
				result = commodityDataService.add(commodityPO);

				CommoditySortVO commodityfatherSortVO = commoditySortBLService
						.getCommoditySortbyID(commodityPO.getCommoditySortID());
				commodityfatherSortVO.getCommodityList().add(commodityVO);

				commodityfatherSortVO.setHasCommodity(true);
				commoditySortBLService
						.updateCommoditySort(commodityfatherSortVO);

				// 更新他的父亲

				// 如果成功添加了返回SUCCESS
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result.equals(ResultMessage.SUCCESS)) {
			Addlog("新增商品成功");
		} else {
			Addlog("新增商品失败");
		}
		return result;
	}

	public boolean isAbleToAddCommodity(CommodityVO commodityVO) {
		// TODO Auto-generated method stub
		boolean isableToADD = false;

		String name = commodityVO.getCommodityName();
		String modelString = commodityVO.getCommodityModel();

		try {
			CommodityPO result = commodityDataService.find(name, modelString);
			if (result == null) {
				isableToADD = true;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isableToADD;
	}

	public ResultMessage deleteCommodity(String commodityName,
			String commodityModel) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
		if (isAbleToDeleteCommodity(commodityName, commodityModel) == false) {
			// 不能删除
			resultMessage = ResultMessage.CannotDeleteCommodity;
		} else {
			try {
				CommodityPO commodityPO = commodityDataService.find(
						commodityName, commodityModel);
				resultMessage = commodityDataService.delete(commodityPO);

				int fatherID = commodityPO.getCommoditySortID();

				CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
				CommoditySortVO commoditySortVO = commoditySortBLService
						.getCommoditySortbyID(fatherID);
				for (int i = 0; i < commoditySortVO.getCommodityList().size(); i++) {
					if (commoditySortVO.getCommodityList().get(i)
							.getCommodityID() == commodityPO.getCommodityID()) {
						commoditySortVO.getCommodityList().remove(i);
						if (commoditySortVO.getCommodityList().size() == 0) {
							commoditySortVO.setHasCommodity(false);
						}
						commoditySortBLService
								.updateCommoditySort(commoditySortVO);
						break;
					}
				}
				// 成功返回success
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("删除商品成功");
		} else {
			Addlog("删除商品失败");
		}
		return resultMessage;
	}

	public boolean isAbleToDeleteCommodity(String commodityName,
			String commodityModel) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			CommodityPO commodityPO = commodityDataService.find(commodityName,
					commodityModel);
			if ((commodityPO.getInventoryQuantity() == 0)
					&& (commodityPO.getLatestPurchasePrice() == 0)) {
				result = true;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public ResultMessage updateCommodity(int ID, String commodityName,
			String commodityModel) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
		try {
			CommodityPO commodityPO = commodityDataService.find(ID);
			int sortID = commodityPO.getCommoditySortID();
			CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
			CommoditySortVO commoditySortVO = commoditySortBLService
					.getCommoditySortbyID(sortID);
			for (int i = 0; i < commoditySortVO.getCommodityList().size(); i++) {
				if (commoditySortVO.getCommodityList().get(i).getCommodityID() == ID) {
					commoditySortVO.getCommodityList().get(i)
							.setCommodityName(commodityName);
					commoditySortVO.getCommodityList().get(i)
							.setCommodityModel(commodityModel);
					commoditySortBLService.updateCommoditySort(commoditySortVO);
					break;
				}
			}
			String sortNameString = commodityPO.getCommoditySortName();

			int quantity = commodityPO.getInventoryQuantity();
			double purchasePrice = commodityPO.getPurchasePrice();

			double retailPrice = commodityPO.getRetailPrice();

			double latestPurchasePrice = commodityPO.getLatestPurchasePrice();
			double latestRetailPrice = commodityPO.getLatestRetailPrice();
			String date = commodityPO.getDate();
			double averagePrice = commodityPO.getAveragePrice();
			int warnQuantity = commodityPO.getWarnQuantity();
			CommodityPO cPo = new CommodityPO(sortID, sortNameString, ID,
					commodityName, commodityModel, quantity, purchasePrice,
					retailPrice, latestPurchasePrice, latestRetailPrice, date,
					averagePrice, warnQuantity);

			resultMessage = commodityDataService.update(cPo);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("更新商品名称型号成功");
		} else {
			Addlog("更新商品名称型号失败");
		}
		return resultMessage;
	}

	public int getNewCommodityID() {
		// TODO Auto-generated method stub
		int finalID = 0;
		try {
			finalID = commodityDataService.getFinalID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (finalID + 1);
	}

	public ResultMessage updateCommodity(CommodityVO commodityVO) {
		ResultMessage resultMessage = ResultMessage.FAIL;
		CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
		CommodityPO commodityPO = new CommodityPO(commodityVO);

		try {
			resultMessage = commodityDataService.update(commodityPO);
			CommoditySortVO commoditySortVO = commoditySortBLService
					.getCommoditySortbyID(commodityPO.getCommoditySortID());
			
			for (int i = 0; i < commoditySortVO.getCommodityList().size(); i++) {
				if (commoditySortVO.getCommodityList().get(i).getCommodityID() == commodityVO.getCommodityID()) {
					commoditySortVO.getCommodityList().set(i, commodityVO);
					commoditySortBLService.updateCommoditySort(commoditySortVO);
					break;
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (resultMessage.equals(ResultMessage.SUCCESS)) {
			Addlog("更新商品信息成功");
		} else {
			Addlog("更新商品信息失败");
		}

		return resultMessage;

	}

	public ArrayList<CommodityVO> displayAll() {
		ArrayList<CommodityVO> vos = new ArrayList<CommodityVO>();
		ArrayList<CommodityPO> pos = new ArrayList<CommodityPO>();
		try {
			pos = commodityDataService.dispAll();
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

	public CommodityVO getCommodityByID(int ID) {
		CommodityPO commodityPO = null;
		CommodityVO commodityVO = null;
		try {
			commodityPO = commodityDataService.find(ID);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (commodityPO != null) {

			commodityVO = (POTrangeToVO(commodityPO));

		}
		return commodityVO;
	}

	public void getinit() {
		try {
			commodityDataService.init();
		} catch (RemoteException e) {
			e.printStackTrace();
		}

	}

	public CommodityVO find(String name, String model) {
		try {
			return new CommodityVO(commodityDataService.find(name, model));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
