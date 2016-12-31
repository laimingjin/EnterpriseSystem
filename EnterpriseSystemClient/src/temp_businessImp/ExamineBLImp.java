package temp_businessImp;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import po.CashListPO;
import po.PaymentPO;
import po.ReceiptPO;
import temp_business.AccountBLService;
import temp_business.CommodityBLService;
import temp_business.CommodityGiftBLService;
import temp_business.CommoditySortBLService;
import temp_business.CustomerBLService;
import temp_business.ExamineBLService;
import temp_business.ExportBLService;
import temp_business.ImportDocumentBLService;
import vo.AccountVO;
import vo.CashListVO;
import vo.CommodityDocumentVO;
import vo.CommodityGiftVO;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.ImportDocumentVO;
import vo.PaymentVO;
import vo.PromotionVO_Customer;
import vo.PromotionVO_Price;
import vo.ReceiptVO;
import vo.SaleDocumentVO;
import businesslogic.Commodity;
import businesslogic.Customer;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.SaleLineItem;
import businesslogic.TransferLineItem;
import businesslogic.TransferList;
import enumClass.PROBLEM;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class ExamineBLImp implements ExamineBLService {
	private CommodityBLService commodityBLService = new CommodityBLImp();
	private CommoditySortBLService commoditySortBLService = new CommoditySortBLImp();
	private AccountBLService accountBLService = new AccountBLImp();
	private CustomerBLService customerBLService = new CustomerBLImp();

	private ImportDocumentBLService importDocumentBLService = new ImportDocumentImp();
	private ExportBLService exportBLService = new ExportBLImp();
	private CommodityGiftBLService commodityGiftBLService=new CommodityGiftBLImp();

	private static final ResultMessage[] RESULT_MESSAGES_IMPORT_FAIL = {
			ResultMessage.FAIL, ResultMessage.Commodity_NotExist,
			ResultMessage.Customer_NotExist,
			ResultMessage.Statement_NotSendable };// import单错误情况

	private static final ResultMessage[] RESULT_MESSAGES_IMPORT_BACK_FAIL = {
			ResultMessage.FAIL, ResultMessage.Commodity_NotExist,
			ResultMessage.Customer_NotExist,
			ResultMessage.Statement_NotSendable,
			ResultMessage.CommodityAmount_NotEnough, };// importback单错误情况;

	private static final ResultMessage[] RESULT_MESSAGES_EXPORT_FAIL = {
			ResultMessage.FAIL, ResultMessage.Commodity_NotExist,
			ResultMessage.Customer_NotExist,
			ResultMessage.Statement_NotSendable,
			ResultMessage.CommodityAmount_NotEnough,

			ResultMessage.Customer_ReceivableLimit_Break, };// export单错误情况

	private static final ResultMessage[] RESULT_MESSAGES_EXPORT_BACK_FAIL = {
			ResultMessage.FAIL, ResultMessage.Commodity_NotExist,
			ResultMessage.Customer_NotExist,
			ResultMessage.Statement_NotSendable,

	};// exportBack单错误情况

	// public ResultMessage examine(ExamineAble examinedAble) {
	// return examinedAble.examined();
	// }
	public ResultMessage process(ImportDocumentVO vo) {

		if (vo.getDocumentID().contains("TH")) {// 如果单据编号中包含退货，即为退货单
			return processImportBack(vo);// 处理进货退货单
		}
		return processImport(vo);// 处理进货单
	}

	private ResultMessage processImport(ImportDocumentVO vo) {// 处理进货单

		// 检查单据的可行性
		ResultMessage resultMessage = checkPracticabilityForImport(vo);
		if (importFail(resultMessage)) {// 如果出错返回，不进行对数据层的修改
			return resultMessage;
		}

		// 修改库存，增
		updateCommodityAmountForImport(vo);// 修改库存数量(兼为成本调价保存数据）

		// 修改客户应shou，增
		updateCustomerReceivableForImport(vo);// 修改客户应shou

		// 修单据状态为已审批
		vo.setStateOfDocument(StateOfDocument.EXAMINED);

		// 将单据保存
		importDocumentBLService.updateImportDraft(vo);

		return ResultMessage.SUCCESS;
	}

	private ResultMessage checkPracticabilityForImport(ImportDocumentVO vo) {

		// 检查单据状态
		if (vo.getStateOfDocument() != StateOfDocument.SENDED) {// 如果单据状态不为已发送
			return ResultMessage.Statement_NotSendable;
		}

		// 检查库存中对应的商品是否存在
		ArrayList<ImportLineItem> importLineList = vo.getTheList()
				.getImportLineList();// 取得商品列表

		for (int i = 0; i < importLineList.size(); i++) {// 对表中的商品进行遍历
			ImportLineItem importLineItem = importLineList.get(i);
			Commodity commodity = importLineItem.getTheCommodity();// 取得商品

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得vo

			if (commodityVo == null) {// 如果这个商品不存在
				return ResultMessage.Commodity_NotExist;
			}
		}

		// 检查客户是否存在
		CustomerVO customerVO = customerBLService.findCustomer(vo
				.getTheCustomer().getCustomerID());// 取得客户

		if (customerVO == null) {// 如果客户不存在
			return ResultMessage.Customer_NotExist;
		}

		return ResultMessage.SUCCESS;

	}

	private ResultMessage processImportBack(ImportDocumentVO vo) {
		// 检查单据的可行性
		ResultMessage resultMessage = checkPracticabilityForImportBack(vo);
		if (importBackFail(resultMessage)) {// 如果出错返回，不进行对数据层的修改
			return resultMessage;
		}

		// 修改库存，增
		updateCommodityAmountForImportBack(vo);// 修改库存数量(兼为成本调价保存数据）

		// 修改客户应shou，jian
		updateCustomerReceivableForImportBack(vo);// 修改客户应shou

		// 修单据状态为已审批
		vo.setStateOfDocument(StateOfDocument.EXAMINED);

		// 将单据保存
		importDocumentBLService.updateImportDraft(vo);

		return ResultMessage.SUCCESS;

	}

	private void updateCustomerReceivableForImportBack(ImportDocumentVO vo) {
		Customer customer = vo.getTheCustomer();

		CustomerVO customerVO = customerBLService.findCustomer(customer
				.getCustomerID());

		double amountToAdd = vo.getTotalPrice(); // 取得总额
		customerVO.setReceivableAmount(customerVO.getReceivableAmount()
				- amountToAdd);// 减少应收

		customerBLService.updateCustomer(customerVO);// 修改库存中的客户

	}

	private void updateCommodityAmountForImportBack(ImportDocumentVO vo) {

		// 取得商品列表
		ImportList importList = vo.getTheList();
		ArrayList<ImportLineItem> importLineList = importList
				.getImportLineList();

		for (int i = 0; i < importLineList.size(); i++) {// 对表中的商品进行遍历
			ImportLineItem importLineItem = importLineList.get(i);
			Commodity commodity = importLineItem.getTheCommodity();// 取得商品
			double price = importLineItem.getPrice(); // 取得进价
			int commodityAmount = importLineItem.getNumber();// 取得商品数量

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得vo

			// commodity.setLatestPurchasePriceForChengBenTiaoJiao(commodityVo
			// .getLatestPurchasePrice());// 为成本调节修改数据（退货单不修改）
			// commodityVo.setLatestPurchasePrice(price);// 修改最近进价（退货单不修改）

			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()
					- commodityAmount);// 减少库存数量
			commodityBLService.updateCommodity(commodityVo);
		}
	}

	private boolean importBackFail(ResultMessage resultMessage) {
		for (int i = 0; i < RESULT_MESSAGES_IMPORT_BACK_FAIL.length; i++) {
			if (resultMessage == RESULT_MESSAGES_IMPORT_BACK_FAIL[i]) {// 若结果属于失败的情况，则返回true
				return true;
			}
		}
		return false;// 没有失败过，返回false
	}

	private ResultMessage checkPracticabilityForImportBack(ImportDocumentVO vo) {
		// 检查单据状态
		if (vo.getStateOfDocument() != StateOfDocument.SENDED) {// 如果单据状态不为已发送
			return ResultMessage.Statement_NotSendable;
		}

		// 检查库存中对应的商品是否存在,是否发生过销售，导致库存不足
		ArrayList<ImportLineItem> importLineList = vo.getTheList()
				.getImportLineList();// 取得商品列表

		for (int i = 0; i < importLineList.size(); i++) {// 对表中的商品进行遍历
			ImportLineItem importLineItem = importLineList.get(i);
			Commodity commodity = importLineItem.getTheCommodity();// 取得商品
			int Amount_toSub = importLineItem.getNumber();

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得vo
			if (commodityVo == null)
				return ResultMessage.Commodity_NotExist;

			int inventoryAmount = commodityVo.getInventoryQuantity();

			if (inventoryAmount < Amount_toSub) {// 如果库存数量小于需要减去的，则发生销售，库存不足
				return ResultMessage.CommodityAmount_NotEnough;
			}
		}

		// 检查客户是否存在
		CustomerVO customerVO = customerBLService.findCustomer(vo
				.getTheCustomer().getCustomerID());// 取得客户

		if (customerVO == null) {// 如果客户不存在
			return ResultMessage.Customer_NotExist;
		}

		return ResultMessage.SUCCESS;
	}

	private void updateCommodityAmountForImport(ImportDocumentVO vo) {

		// 取得商品列表
		ImportList importList = vo.getTheList();
		ArrayList<ImportLineItem> importLineList = importList
				.getImportLineList();

		for (int i = 0; i < importLineList.size(); i++) {// 对表中的商品进行遍历
			ImportLineItem importLineItem = importLineList.get(i);
			Commodity commodity = importLineItem.getTheCommodity();// 取得商品
			double price = importLineItem.getPrice(); // 取得进价
			int commodityAmount = importLineItem.getNumber();// 取得商品数量

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得vo

			commodity.setLatestPurchasePriceForChengBenTiaoJiao(commodityVo
					.getLatestPurchasePrice());// 为成本调节修改数据

			commodityVo.setLatestPurchasePrice(price);// 修改最近进价
			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()
					+ commodityAmount);// 增加库存数量
			commodityBLService.updateCommodity(commodityVo);
			
			System.out.println("*****************************");
			System.out.println("resultMsg： "+commodityBLService.updateCommodity(commodityVo));
			System.out.println("amount:   "+commodityVo.getInventoryQuantity()); 
			System.out.println("*****************************");
		}
	}

	private void updateCustomerReceivableForImport(ImportDocumentVO vo) {
		Customer customer = vo.getTheCustomer();

		CustomerVO customerVO = customerBLService.findCustomer(customer
				.getCustomerID());

		double amountToAdd = vo.getTotalPrice(); // 取得总额
		customerVO.setReceivableAmount(customerVO.getReceivableAmount()
				+ amountToAdd);// 增加客户应收

		customerBLService.updateCustomer(customerVO);// 修改库存中的客户
	}

	private boolean importFail(ResultMessage resultMessage) {// 判断进货的结果是否正确
		for (int i = 0; i < RESULT_MESSAGES_IMPORT_FAIL.length; i++) {
			if (resultMessage == RESULT_MESSAGES_IMPORT_FAIL[i]) {// 若结果属于失败的情况，则返回true
				return true;
			}
		}
		return false;// 没有失败过，返回false
	}

	public ResultMessage process(SaleDocumentVO vo) {
		if (vo.getDocumentID().contains("TH")) {// 如果单据编号中包含退货，即为退货单
			return processExportBack(vo);// 处理进货退货单
		}
		return processExport(vo);// 处理进货单
	}

	private ResultMessage processExportBack(SaleDocumentVO vo) {
		// 检查单据的可行性
				ResultMessage resultMessage = checkPracticabilityForExportBack(vo);
				if (exportBackFail(resultMessage)) {// 如果出错返回，不进行对数据层的修改
					return resultMessage;
				}


				// 修改库存，减
				updateCommodityAmountForExportBack(vo);// 修改库存数量

				// 修改客户应付，增
				updateCustomerPayableForExportBack(vo);// 修改客户应付

				// 修单据状态为已审批
				vo.setStateOfDocument(StateOfDocument.EXAMINED);

				// 将单据保存
				exportBLService.updateExportDraft(vo);

				return ResultMessage.SUCCESS;
	}

	private void updateCustomerPayableForExportBack(SaleDocumentVO vo) {
		Customer customer = vo.getTheCustomer();

		CustomerVO customerVO = customerBLService.findCustomer(customer
				.getCustomerID());

		double payAbleAmountToAdd = vo.sgetTotalPriceAfterPromotion(); // 取得总额,并改变总额
		customerVO.setPayableAmount(customerVO.getPayableAmount()
				- payAbleAmountToAdd);// 减少客户应付

		customerBLService.updateCustomer(customerVO);// 修改库存中的客户

	}

	private void updateCommodityAmountForExportBack(SaleDocumentVO vo) {
		ArrayList<SaleLineItem> saleLineItems = vo.getTheList().getTheList();// 取得要销售的商品列表

		for (Iterator iterator = saleLineItems.iterator(); iterator.hasNext();) {// 遍历要销售的商品列表
			SaleLineItem saleLineItem = (SaleLineItem) iterator.next();

			Commodity commodity = saleLineItem.getTheCommodity();// 要销售的商品
			double price = saleLineItem.getPrice(); //取得商品售价
			int commodityAmount = saleLineItem.getTheNumber();// 要销售的商品数量

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			
			commodityVo.setLatestRetailPrice(price);//修改最近售价
			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()+commodityAmount);//zengjia库存数量
		commodityBLService.updateCommodity(commodityVo);
		}

		ArrayList<Commodity> commodities_Gift = vo.getGiftFromPromotion();
		for (int i = 0; i < commodities_Gift.size(); i++) {
			Commodity commodity= commodities_Gift.get(i);//取得要赠送商品
			int commodityAmount = commodity.getInventoryQuantity();//取得要赠送商品的数量
			
			
			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()+commodityAmount);//zengjia库存数量

			commodityBLService.updateCommodity(commodityVo);
		}
		
	}

	private boolean exportBackFail(ResultMessage resultMessage) {
		for (int i = 0; i < RESULT_MESSAGES_EXPORT_BACK_FAIL.length; i++) {
			if (resultMessage == RESULT_MESSAGES_EXPORT_BACK_FAIL[i]) {// 若结果属于失败的情况，则返回true
				return true;
			}
		}
		return false;// 没有失败过，返回false
	}

	private ResultMessage checkPracticabilityForExportBack(SaleDocumentVO vo) {
		// 检查单据状态
				if (vo.getStateOfDocument() != StateOfDocument.SENDED) {// 如果单据状态不为已发送
					return ResultMessage.Statement_NotSendable;
				}

				// 检查库存中对应的商品是否存在
				ArrayList<SaleLineItem> saleLineItems = vo.getTheList().getTheList();// 取得要销售的商品列表

				for (Iterator iterator = saleLineItems.iterator(); iterator.hasNext();) {// 遍历要销售的商品列表
					SaleLineItem saleLineItem = (SaleLineItem) iterator.next();

					Commodity commodity = saleLineItem.getTheCommodity();// 要销售的商品
					int commodityAmount = saleLineItem.getTheNumber();// 要销售的商品数量

					CommodityVO commodityVo = commodityBLService
							.find(commodity.getCommodityName(),
									commodity.getCommodityModel());// 从数据中取得Commodity的vo
					if (commodityVo == null) {// 如果这个商品不存在
						return ResultMessage.Commodity_NotExist;
					}
				}

				ArrayList<Commodity> commodities_Gift = vo.getGiftFromPromotion();
				for (int i = 0; i < commodities_Gift.size(); i++) {
					Commodity commodity = commodities_Gift.get(i);
					CommodityVO commodityVo = commodityBLService
							.find(commodity.getCommodityName(),
									commodity.getCommodityModel());// 从数据中取得Commodity的vo
					if (commodityVo == null) {// 如果这个商品不存在
						return ResultMessage.Commodity_NotExist;
					}
				

				}

				// 检查客户是否存在
				CustomerVO customerVO = customerBLService.findCustomer(vo
						.getTheCustomer().getCustomerID());// 取得客户

				if (customerVO == null) {// 如果客户不存在
					return ResultMessage.Customer_NotExist;
				}

	

				return ResultMessage.SUCCESS;
	}

	private ResultMessage processExport(SaleDocumentVO vo) {
		// 检查单据的可行性
		ResultMessage resultMessage = checkPracticabilityForExport(vo);
		if (exportFail(resultMessage)) {// 如果出错返回，不进行对数据层的修改
			return resultMessage;
		}

		// 生成代金券
		vo.addGift_Money();

		// 生成赠送单
		createCommodityGifts(vo);

		// 修改库存，减
		updateCommodityAmountForExport(vo);// 修改库存数量

		// 修改客户应付，增
		updateCustomerPayableForExport(vo);// 修改客户应收

		// 修单据状态为已审批
		vo.setStateOfDocument(StateOfDocument.EXAMINED);

		// 将单据保存
		exportBLService.updateExportDraft(vo);

		return ResultMessage.SUCCESS;
	}

	private boolean exportFail(ResultMessage resultMessage) {
		for (int i = 0; i < RESULT_MESSAGES_EXPORT_FAIL.length; i++) {
			if (resultMessage == RESULT_MESSAGES_EXPORT_FAIL[i]) {// 若结果属于失败的情况，则返回true
				return true;
			}
		}
		return false;// 没有失败过，返回false
	}

	private void updateCustomerPayableForExport(SaleDocumentVO vo) {
		Customer customer = vo.getTheCustomer();

		CustomerVO customerVO = customerBLService.findCustomer(customer
				.getCustomerID());

		double payAbleAmountToAdd = vo.sgetTotalPriceAfterPromotion(); // 取得总额,并改变总额
		customerVO.setPayableAmount(customerVO.getPayableAmount()
				+ payAbleAmountToAdd);// 增加客户应付

		customerBLService.updateCustomer(customerVO);// 修改库存中的客户

		
	}

	private void updateCommodityAmountForExport(SaleDocumentVO vo) {
		ArrayList<SaleLineItem> saleLineItems = vo.getTheList().getTheList();// 取得要销售的商品列表

		for (Iterator iterator = saleLineItems.iterator(); iterator.hasNext();) {// 遍历要销售的商品列表
			SaleLineItem saleLineItem = (SaleLineItem) iterator.next();

			Commodity commodity = saleLineItem.getTheCommodity();// 要销售的商品
			double price = saleLineItem.getPrice(); //取得商品售价
			int commodityAmount = saleLineItem.getTheNumber();// 要销售的商品数量

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			
			commodityVo.setLatestRetailPrice(price);//修改最近售价
			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()-commodityAmount);//减少库存数量
		commodityBLService.updateCommodity(commodityVo);
		}

		ArrayList<Commodity> commodities_Gift = vo.getGiftFromPromotion();
		for (int i = 0; i < commodities_Gift.size(); i++) {
			Commodity commodity= commodities_Gift.get(i);//取得要赠送商品
			int commodityAmount = commodity.getInventoryQuantity();//取得要赠送商品的数量
			
			
			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			commodityVo.setInventoryQuantity(commodityVo.getInventoryQuantity()-commodityAmount);//减少库存数量

			commodityBLService.updateCommodity(commodityVo);
		}
	}

	private void createCommodityGifts(SaleDocumentVO vo) {

		Customer customer = vo.getTheCustomer();
		
		PromotionVO_Customer promotionVO_Customer = vo
				.getPromotionVO_Customer();
		
		if (promotionVO_Customer!=null) {
			Commodity[] gifts_Customer = promotionVO_Customer.getGifts();// 属性包含商品
			int[] amountOfGifts_Customer = promotionVO_Customer.getAmountOfGifts();// 赠送品数量
			
			for (int i = 0; i < gifts_Customer.length; i++) {
				createOneCommodityGifs(gifts_Customer[i],
						amountOfGifts_Customer[i], customer,vo);
			}
		}

		PromotionVO_Price promotionVO_Price = vo.getPromotionVO_Price();
		if (promotionVO_Price!=null) {
			Commodity gifts_Price = promotionVO_Price.getCommodity();// 属性包含商品
			int amountOfGifts_Price = promotionVO_Price.getAmountOfGift();// 赠送品数量
			
			createOneCommodityGifs(gifts_Price, amountOfGifts_Price,
					customer,vo);
		}
	}

	private void createOneCommodityGifs(Commodity commodity, int amount,
			Customer customer, SaleDocumentVO vo2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");// 设置日期格式
		String nowDate = df.format(new Date());
		CommodityGiftVO vo = new CommodityGiftVO(nowDate,
				commodityBLService.getNewCommodityID(), commodity, amount,
				customer, true, true, false);
		try {
			commodityGiftBLService.addCommodityGift(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		vo2.addCommodityGiftVO(vo);
	}

	private ResultMessage checkPracticabilityForExport(SaleDocumentVO vo) {
		// 检查单据状态
		if (vo.getStateOfDocument() != StateOfDocument.SENDED) {// 如果单据状态不为已发送
			return ResultMessage.Statement_NotSendable;
		}

		// 检查库存中对应的商品是否存在，库存数量是否足够销售
		ArrayList<SaleLineItem> saleLineItems = vo.getTheList().getTheList();// 取得要销售的商品列表

		for (Iterator iterator = saleLineItems.iterator(); iterator.hasNext();) {// 遍历要销售的商品列表
			SaleLineItem saleLineItem = (SaleLineItem) iterator.next();

			Commodity commodity = saleLineItem.getTheCommodity();// 要销售的商品
			int commodityAmount = saleLineItem.getTheNumber();// 要销售的商品数量

			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			if (commodityVo == null) {// 如果这个商品不存在
				return ResultMessage.Commodity_NotExist;
			}
			if (commodityVo.getInventoryQuantity() < commodityAmount) {// 如果要销售的商品数量大于库存数量
				return ResultMessage.CommodityAmount_NotEnough;
			}
		}

		ArrayList<Commodity> commodities_Gift = vo.getGiftFromPromotion();
		for (int i = 0; i < commodities_Gift.size(); i++) {
			Commodity commodity = commodities_Gift.get(i);
			CommodityVO commodityVo = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());// 从数据中取得Commodity的vo
			if (commodityVo == null) {// 如果这个商品不存在
				return ResultMessage.Commodity_NotExist;
			}
			if (commodityVo.getInventoryQuantity() < commodity
					.getInventoryQuantity()) {// 如果要赠送的商品数量大于库存数量
				return ResultMessage.CommodityAmount_NotEnough;
			}

		}

		// 检查客户是否存在
		CustomerVO customerVO = customerBLService.findCustomer(vo
				.getTheCustomer().getCustomerID());// 取得客户

		if (customerVO == null) {// 如果客户不存在
			return ResultMessage.Customer_NotExist;
		}

		return ResultMessage.SUCCESS;
	}

	public ResultMessage process(ReceiptVO receiptVO) {
		ResultMessage resultMessage = ResultMessage.SUCCESS;
		ReceiptPO po = new ReceiptPO(receiptVO);
		CustomerBLImp customerBLImp = new CustomerBLImp();
		
		
		CustomerVO v = customerBLImp.findCustomer(receiptVO.getCustomerID());
		double payableAmount = v.getPayableAmount() - receiptVO.getTotalPrice();//减少客户 应付
		CustomerVO newVo = new CustomerVO(v.getCustomerID(),
				v.getCustomerType(), v.getCustomerRank(), v.getCustomerName(),
				v.getTelePhone(), v.getCustomerAddress(),
				v.getCustomerPostCode(), v.geteMail(), v.getReceivableLimit(),
				v.getReceivableAmount(), payableAmount, v.getOperator());
		
		resultMessage = customerBLImp.updateCustomer(newVo);

		
		TransferList transferList=receiptVO.getTransferList();
		ArrayList< TransferLineItem> transferLineItems=transferList.getTheList();
		
		
		for (int i = 0; i < transferLineItems.size(); i++) {
			TransferLineItem transferLineItem=transferLineItems.get(i);
			AccountVO accountVO=accountBLService.findAccount(transferLineItem.getAccountName());
			if (accountVO ==null) {
				continue;
			}
			accountVO.setAccountPrice(accountVO.getAccountPrice()+transferLineItem.getTransferPrice());//客户给我们钱，我们账户的钱多了
			resultMessage=accountBLService.updateAccount(accountVO.getAccountName(), accountVO);
		}
		
		
		return resultMessage;
	}

	public ResultMessage process(PaymentVO paymentVO) {
		ResultMessage resultMessage = ResultMessage.SUCCESS;
		PaymentPO po = new PaymentPO(paymentVO);
		CustomerBLImp customerBLImp = new CustomerBLImp();
		// resultMessage=paymentDataService.add(po);
		// if (resultMessage.equals(ResultMessage.SUCCESS)) {// 要修改客户的应付
		CustomerVO v = customerBLImp.findCustomer(paymentVO.getCustomerID());
		double receiveAmount = v.getReceivableAmount()
				- paymentVO.getTotalPrice();//我们付给客户钱，客户应收少了
		CustomerVO newVo = new CustomerVO(v.getCustomerID(),
				v.getCustomerType(), v.getCustomerRank(), v.getCustomerName(),
				v.getTelePhone(), v.getCustomerAddress(),
				v.getCustomerPostCode(), v.geteMail(), v.getReceivableLimit(),
				receiveAmount, v.getPayableAmount(), v.getOperator());

		resultMessage = customerBLImp.updateCustomer(newVo);

		
		TransferList transferList=paymentVO.getTransferList();
		ArrayList< TransferLineItem> transferLineItems=transferList.getTheList();
		
		
		for (int i = 0; i < transferLineItems.size(); i++) {
			TransferLineItem transferLineItem=transferLineItems.get(i);
			AccountVO accountVO=accountBLService.findAccount(transferLineItem.getAccountName());
			if (accountVO ==null) {
				continue;
			}
			accountVO.setAccountPrice(accountVO.getAccountPrice()-transferLineItem.getTransferPrice());//我们给客户钱，账户的钱少了
			resultMessage=accountBLService.updateAccount(accountVO.getAccountName(), accountVO);
		}
		return resultMessage;

	}

	public ResultMessage process(CashListVO cashListVO) {
		ResultMessage resultMessage = ResultMessage.SUCCESS;
		CashListPO po = new CashListPO(cashListVO);
		AccountBLImp accountBLImp = new AccountBLImp();

		// try {
		// resultMessage = cashListDataService.add(po);
		// if (resultMessage.equals(ResultMessage.SUCCESS)) {
		AccountVO accountVO = accountBLImp.findAccount(po.getAccountName());
		double newPrice = accountVO.getAccountPrice()
				- cashListVO.getTotalPrice();
		resultMessage = accountBLImp.updateAccount(accountVO.getAccountName(),
				new AccountVO(accountVO.getAccountName(), newPrice));
		// }
		// } catch (RemoteException e) {
		// e.printStackTrace();
		// }
		// if (resultMessage.equals(ResultMessage.SUCCESS)) {
		// Addlog("新增现金费用单成功");
		// } else {
		// Addlog("新增现金费用单失败");
		// }
		return resultMessage;
	}

	public ResultMessage process(CommodityDocumentVO commodityDocumentVO) {
		Commodity commodity = commodityDocumentVO.getCommodity();
		int realQuantity = commodityDocumentVO.getRealQuantity();
		ResultMessage resultMessage = ResultMessage.FAIL;
		if (commodityDocumentVO.getProblem().equals(PROBLEM.OVERFLOW)) {

			CommodityVO commodityVO = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());
			commodityVO.setInventoryQuantity(commodity.getInventoryQuantity()
					+ realQuantity);
			resultMessage = commodityBLService.updateCommodity(commodityVO);
              commodityDocumentVO.setPass(true);
		} else if (commodityDocumentVO.getProblem().equals(PROBLEM.LOSS)) {
			CommodityVO commodityVO = commodityBLService
					.find(commodity.getCommodityName(),
							commodity.getCommodityModel());
			commodityVO.setInventoryQuantity(commodity.getInventoryQuantity()
					- realQuantity);
			resultMessage = commodityBLService.updateCommodity(commodityVO);
		    commodityDocumentVO.setPass(true);
		}
		return resultMessage;
	}

}
