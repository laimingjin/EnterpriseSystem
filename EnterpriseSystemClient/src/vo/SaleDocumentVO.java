package vo;

import java.util.ArrayList;
import java.util.Iterator;

import org.omg.CORBA.PUBLIC_MEMBER;

import po.CommodityGiftPO;
import po.PromotionPo_Package;
import po.SaleDocumentPO;
import businesslogic.Commodity;
import businesslogic.Customer;
import businesslogic.SaleList;
import businesslogic.User;
import enumClass.StateOfDocument;

/**
 * 单据编号（XSD-yyyyMMdd-xxxxx），客户（仅显示销售商），业务员（和这个客户打交道的公司员工，可以设置一个客户的默认业务员-problem
 * 【是否可修改？】），操作员（当前登录系统的用户），仓库，出货商品清单，折让前总额，折让【级别不同，情况不同】，使用代金卷金额，折让后总额，备注。
 * 出货商品清单中要显示商品的编号，名称（从商品选择界面选择），型号，数量（手工输入），单价（默认为商品信息里的销售价，可修改），金额（自动生成），商品备注。
 * 2014年12月10日下午5:16:12
 */
public class SaleDocumentVO {
	@SuppressWarnings("unused")
	private final String headOfID_XSD = "XSD";
	@SuppressWarnings("unused")
	private final String headOfID_XSTHD = "XSTHD";
	String theDate;// 单据时间
	String documentID;// 单据编号
	Customer theCustomer;// 客户
	String executive;// 业务员
	User theUser;// 操作员
	String warehouse;// 仓库
	SaleList theList;// 商品列表
	String theMessage;// 备注
	double totalPriceBefore;// 折让前总额
	double theRebate;// 折让金额
	double theVoucher;// 代金券金额
	double totalPriceAfter;// 折让后总额
	StateOfDocument stateOfDocument;

	// 以下变量是为了辅助促销
	private ArrayList<CommodityGiftVO> commodityGiftVOs = new ArrayList<CommodityGiftVO>();
	private PromotionVO_Customer promotionVO_Customer;
	private PromotionVO_Price promotionVO_Price;
	private ArrayList<PromotionVO_Package> promotionVO_Packages = new ArrayList<PromotionVO_Package>();

	private int gift_Money_ForPromotion_Customer = 0;// 代金券 客户促销策略
	private int amountOfGift_Money_ForPromotion_Customer = 0;// 代金券数量 客户促销策略

	private int gift_Money_ForPromotion_Price = 0;// 代金券 总价促销策略
	private int amountOfGift_Money_ForPromotion_Price = 0;// 代金券数量 总价促销策略

	public SaleDocumentVO(String date, String id, Customer customer,
			String executive, String warehouse, User user, SaleList list,
			String message, double priceBefore, double rebate, double voucher,
			double priceAfter, StateOfDocument stateOfDocument) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.executive = executive;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate = rebate;
		theVoucher = voucher;
		totalPriceAfter = priceAfter;
		this.stateOfDocument = stateOfDocument;
	}

	public SaleDocumentVO(String date, String id, Customer customer,
			String executive, String warehouse, User user, SaleList list,
			String message, double priceBefore, double rebate, double voucher,
			double priceAfter, boolean pass, boolean send, boolean dealed) {
		theDate = date;
		documentID = id;
		theCustomer = customer;
		this.executive = executive;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate = rebate;
		theVoucher = voucher;
		totalPriceAfter = priceAfter;
		stateOfDocument = StateOfDocument.DRAFT;
		if (send) {
			stateOfDocument = StateOfDocument.SENDED;
		} else if (pass) {
			stateOfDocument = StateOfDocument.EXAMINED;
		} else if (dealed) {
			stateOfDocument = StateOfDocument.DONE;
		}
	}

	public SaleDocumentVO(String newID, SaleDocumentVO vo) {
		theDate = vo.getTheDate();
		documentID = newID;
		theCustomer = vo.getTheCustomer();
		this.executive = vo.getExecutive();
		this.warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPriceBefore = vo.getTotalPriceBefore();
		theRebate = vo.getTheRebate();
		theVoucher = vo.getTheVoucher();
		totalPriceAfter = vo.getTotalPriceAfter();
		this.stateOfDocument =StateOfDocument.DRAFT;
	}

	public SaleDocumentVO(SaleDocumentPO po) {
		theDate = po.getTheDate();
		documentID = po.getDocumentID();
		theCustomer = po.getTheCustomer();
		this.executive = po.getExecutive();
		this.warehouse = po.getWarehouse();
		theUser = po.getTheUser();
		theList = po.getTheList();
		theMessage = po.getTheMessage();
		totalPriceBefore = po.getTotalPriceBefore();
		theRebate = po.getTheRebate();
		theVoucher = po.getTheVoucher();
		totalPriceAfter = po.getTotalPriceAfter();
		this.stateOfDocument = po.getStateOfDocument();

		if (promotionVO_Customer != null) {
			promotionVO_Customer = new PromotionVO_Customer(
					po.getPromotionPO_Customer());
		}
		if (promotionVO_Price != null) {
			promotionVO_Price = new PromotionVO_Price(po.getPromotionPO_Price());
		}

		ArrayList<PromotionPo_Package> promotionPo_Packages = po
				.getPromotionPO_Packages();
		if (promotionPo_Packages != null) {
			for (Iterator<PromotionPo_Package> iterator = promotionPo_Packages
					.iterator(); iterator.hasNext();) {
				PromotionPo_Package promotionPo_Package = (PromotionPo_Package) iterator
						.next();
				this.promotionVO_Packages.add(new PromotionVO_Package(
						promotionPo_Package));
			}
		}
		gift_Money_ForPromotion_Customer = po
				.getGift_Money_ForPromotion_Customer();// 代金券 客户
		amountOfGift_Money_ForPromotion_Customer = po
				.getAmountOfGift_Money_ForPromotion_Customer();// 代金券数量 客户

		gift_Money_ForPromotion_Price = po.getGift_Money_ForPromotion_Price();// 代金券
																				// 总价
		amountOfGift_Money_ForPromotion_Price = po
				.getAmountOfGift_Money_ForPromotion_Price();// 代金券数量 总价

		ArrayList<CommodityGiftPO> commodityGiftPOs = po.getCommodityGiftPOs();
		if (commodityGiftPOs != null) {
			for (CommodityGiftPO commodityGiftPO : commodityGiftPOs) {
				this.commodityGiftVOs.add(new CommodityGiftVO(commodityGiftPO));
			}
		}
	}

	public boolean isPass() {
		return stateOfDocument == StateOfDocument.EXAMINED;
	}

	public void setPass(boolean isPass) {
		if (isPass) {
			stateOfDocument = StateOfDocument.EXAMINED;
		}
	}

	public boolean isSend() {
		return stateOfDocument == StateOfDocument.SENDED;
	}

	public void setSend(boolean isSend) {
		if (isSend) {
			stateOfDocument = StateOfDocument.SENDED;
		}
	}

	public boolean isDealed() {
		return stateOfDocument == StateOfDocument.DONE;
	}

	@SuppressWarnings("static-access")
	public void setDealed(boolean isDealed) {
		if (isDealed) {
			stateOfDocument = stateOfDocument.DONE;
		}
	}

	public String getTheDate() {
		return theDate;
	}

	public String getDocumentID() {
		return documentID;
	}

	public Customer getTheCustomer() {
		return theCustomer;
	}

	public String getExecutive() {
		return executive;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public User getTheUser() {
		return theUser;
	}

	public SaleList getTheList() {
		return theList;
	}

	public String getTheMessage() {
		return theMessage;
	}

	public double getTotalPriceBefore() {
		return totalPriceBefore;
	}

	public double getTheRebate() {
		return theRebate;
	}

	public double getTheVoucher() {
		return theVoucher;
	}

	public double getTotalPriceAfter() {
		return totalPriceAfter;
	}

	/**
	 * @return the stateOfDocument
	 */
	public StateOfDocument getStateOfDocument() {
		return stateOfDocument;
	}

	/**
	 * @param stateOfDocument
	 *            the stateOfDocument to set
	 */
	public void setStateOfDocument(StateOfDocument stateOfDocument) {
		this.stateOfDocument = stateOfDocument;
	}

	public String toString() {
		return documentID;
	}

	/**
	 * @return the promotionVO_Customer
	 */
	public PromotionVO_Customer getPromotionVO_Customer() {
		return promotionVO_Customer;
	}

	/**
	 * @return the promotionVO_Price
	 */
	public PromotionVO_Price getPromotionVO_Price() {
		return promotionVO_Price;
	}

	/**
	 * @return the promotionVO_Packages
	 */
	public ArrayList<PromotionVO_Package> getPromotionVO_Packages() {
		return promotionVO_Packages;
	}

	/**
	 * @param promotionVO_Customer
	 *            the promotionVO_Customer to set
	 */
	public void setPromotionVO_Customer(
			PromotionVO_Customer promotionVO_Customer) {
		this.promotionVO_Customer = promotionVO_Customer;
	}

	/**
	 * @param promotionVO_Price
	 *            the promotionVO_Price to set
	 */
	public void setPromotionVO_Price(PromotionVO_Price promotionVO_Price) {
		this.promotionVO_Price = promotionVO_Price;
	}

	/**
	 * @param promotionVO_Packages
	 *            the promotionVO_Packages to set
	 */
	public void setPromotionVO_Packages(
			ArrayList<PromotionVO_Package> promotionVO_Packages) {
		this.promotionVO_Packages = promotionVO_Packages;
	}

	public double getTotalPriceAfterPromotion() {
		double priceTotal = theList.getDocumentTotalPrice();
		double priceAfter_RebateAndVoucher = priceTotal - theRebate
				- theVoucher;

		double price_SubForPackages = getPrice_SubForPackages();

		double priceAfter_Rebate_Voucher_Packages = priceAfter_RebateAndVoucher
				- price_SubForPackages;

		double discountOfPromotion = getDiscountOfPromotion();// 取得客户等级带来的折扣

		return discountOfPromotion * priceAfter_Rebate_Voucher_Packages;

	}

	public double sgetTotalPriceAfterPromotion() {
		double priceTotal = theList.getDocumentTotalPrice();
		double priceAfter_RebateAndVoucher = priceTotal - theRebate
				- theVoucher;

		double price_SubForPackages = getPrice_SubForPackages();

		double priceAfter_Rebate_Voucher_Packages = priceAfter_RebateAndVoucher
				- price_SubForPackages;

		double discountOfPromotion = getDiscountOfPromotion();// 取得客户等级带来的折扣

		return totalPriceAfter = discountOfPromotion
				* priceAfter_Rebate_Voucher_Packages;

	}

	private double getPrice_SubForPackages() {
		if (promotionVO_Packages==null) {
			return 0;
		}
		int result = 0;
		for (Iterator iterator = promotionVO_Packages.iterator(); iterator
				.hasNext();) {
			PromotionVO_Package promotionVO_Package = (PromotionVO_Package) iterator
					.next();
			result += promotionVO_Package.getDiscountMoney();
		}
		return result;
	}

	private double getDiscountOfPromotion() {
		if (promotionVO_Customer == null) {
			return 1.0;
		}
		return promotionVO_Customer.getDiscount();
	}

	public ArrayList<Commodity> getGiftFromPromotion() {// 取得来自促销的赠品
		ArrayList<Commodity> commodities = new ArrayList<Commodity>();

		if (promotionVO_Customer == null) {
			return commodities;
		}

		for (int i = 0; i < promotionVO_Customer.getGifts().length; i++) {
			Commodity commodity = promotionVO_Customer.getGifts()[i];
			commodity.setInventoryQuantity(promotionVO_Customer
					.getAmountOfGifts()[i]);
		}
		return commodities;
	}

	public void clearPromotion() {
		promotionVO_Customer = null;
		promotionVO_Price = null;
		promotionVO_Packages = new ArrayList<PromotionVO_Package>();

	}

	/**
	 * @return the commodityGiftVOs
	 */
	public ArrayList<CommodityGiftVO> getCommodityGiftVOs() {
		return commodityGiftVOs;
	}

	/**
	 * @return the gift_Money_ForPromotion_Customer
	 */
	public int getGift_Money_ForPromotion_Customer() {
		return gift_Money_ForPromotion_Customer;
	}

	/**
	 * @return the amountOfGift_Money_ForPromotion_Customer
	 */
	public int getAmountOfGift_Money_ForPromotion_Customer() {
		return amountOfGift_Money_ForPromotion_Customer;
	}

	/**
	 * @return the gift_Money_ForPromotion_Price
	 */
	public int getGift_Money_ForPromotion_Price() {
		return gift_Money_ForPromotion_Price;
	}

	/**
	 * @return the amountOfGift_Money_ForPromotion_Price
	 */
	public int getAmountOfGift_Money_ForPromotion_Price() {
		return amountOfGift_Money_ForPromotion_Price;
	}

	public void addGift_Money() {
		if (promotionVO_Customer != null) {

			gift_Money_ForPromotion_Customer = promotionVO_Customer
					.getGift_Money();// 代金券
			// 客户
			amountOfGift_Money_ForPromotion_Customer = promotionVO_Customer
					.getAmountOfGift_Money();// 代金券数量 客户
		}
		if (promotionVO_Price != null) {

			gift_Money_ForPromotion_Price = promotionVO_Price.getGift_Money();// 代金券
			// 总价
			amountOfGift_Money_ForPromotion_Price = promotionVO_Price
					.getAmountOfGift_Money();// 代金券数量 总价
		}

	}

	public void addCommodityGiftVO(CommodityGiftVO vo) {
		if (commodityGiftVOs==null) {
			return;
		}
		commodityGiftVOs.add(vo);
	}

}
