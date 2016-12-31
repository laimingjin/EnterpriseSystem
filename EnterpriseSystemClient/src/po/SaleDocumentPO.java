package po;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import vo.CommodityGiftVO;
import vo.PromotionVO_Package;
import vo.SaleDocumentVO;
import businesslogic.Customer;
import businesslogic.SaleList;
import businesslogic.User;
import enumClass.StateOfDocument;



public class SaleDocumentPO implements Serializable{
	
	
/**
	 * 
	 */
	private static final long serialVersionUID = -6143295218863856856L;
	//	private static final long serialVersionUID = 1L;
	String theDate;//单据时间
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

	//以下变量是为了辅助促销
private 	ArrayList<CommodityGiftPO>  commodityGiftPOs;
private 	PromotionPO_Customer promotionPO_Customer;
private 	PromotionPo_Price promotionPO_Price;
private 	ArrayList<PromotionPo_Package> promotionPO_Packages;
	
private 	int gift_Money_ForPromotion_Customer=0;// 代金券 客户促销策略
private 	int amountOfGift_Money_ForPromotion_Customer=0;// 代金券数量 客户促销策略
	
private 	int gift_Money_ForPromotion_Price=0;// 代金券 总价促销策略
private 	int amountOfGift_Money_ForPromotion_Price=0;// 代金券数量 总价促销策略
    
	public SaleDocumentPO(String date,String id, Customer customer, String executive,String warehouse,
			User user, SaleList list, String message, double priceBefore,
			double rebate, double voucher, double priceAfter, 	StateOfDocument stateOfDocument) {
		theDate=date;
		documentID = id;
		theCustomer = customer;
		this.executive=executive;
		this.warehouse = warehouse;
		theUser = user;
		theList = list;
		theMessage = message;
		totalPriceBefore = priceBefore;
		theRebate=rebate;
		theVoucher=voucher;
		totalPriceAfter=priceAfter;
		this.stateOfDocument=stateOfDocument;
	}
	public SaleDocumentPO(String date, String id, Customer customer,
			String executive, String warehouse, User user, SaleList list,
			String message, double priceBefore, double rebate, double voucher,
			double priceAfter,  boolean pass,boolean send,boolean dealed) {
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

	public SaleDocumentPO(SaleDocumentVO vo) {
		theDate=vo.getTheDate();
		documentID = vo.getDocumentID();
		theCustomer = vo.getTheCustomer();
		this.executive=vo.getExecutive();
		this.warehouse = vo.getWarehouse();
		theUser = vo.getTheUser();
		theList = vo.getTheList();
		theMessage = vo.getTheMessage();
		totalPriceBefore = vo.getTotalPriceBefore();
		theRebate=vo.getTheRebate();
		theVoucher=vo.getTheVoucher();
		totalPriceAfter=vo.getTotalPriceAfter();
		this.stateOfDocument=vo.getStateOfDocument();
		if (promotionPO_Customer!=null) {
			
			promotionPO_Customer=new PromotionPO_Customer(vo.getPromotionVO_Customer());
		}
		if (promotionPO_Price!=null) {
			promotionPO_Price=new PromotionPo_Price(vo.getPromotionVO_Price());
		}
		ArrayList<PromotionVO_Package> promotionVO_Packages=vo.getPromotionVO_Packages();
		if (promotionVO_Packages!=null) {
			
			for (Iterator<PromotionVO_Package> iterator = promotionVO_Packages.iterator(); iterator
					.hasNext();) {
				PromotionVO_Package promotionVO_Package = (PromotionVO_Package) iterator
						.next();
				this.promotionPO_Packages.add(new PromotionPo_Package(promotionVO_Package));
			}
		}
		
		 gift_Money_ForPromotion_Customer=vo.getGift_Money_ForPromotion_Customer();// 代金券 客户
		 amountOfGift_Money_ForPromotion_Customer=vo.getAmountOfGift_Money_ForPromotion_Customer();// 代金券数量 客户
		
		 gift_Money_ForPromotion_Price=vo.getGift_Money_ForPromotion_Price();// 代金券 总价
		 amountOfGift_Money_ForPromotion_Price=vo.getAmountOfGift_Money_ForPromotion_Price();// 代金券数量 总价
		ArrayList<CommodityGiftVO> commodityGiftVOs=vo.getCommodityGiftVOs();
		if (commodityGiftVOs!=null) {
			
			for (CommodityGiftVO commodityGiftVO : commodityGiftVOs) {
				this.commodityGiftPOs.add(new CommodityGiftPO(commodityGiftVO));
			}
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
	 * @param stateOfDocument the stateOfDocument to set
	 */
	public void setStateOfDocument(StateOfDocument stateOfDocument) {
		this.stateOfDocument = stateOfDocument;
	}
	/**
	 * @return the promotionVO_Customer
	 */
	public PromotionPO_Customer getPromotionPO_Customer() {
		return promotionPO_Customer;
	}
	/**
	 * @return the promotionVO_Price
	 */
	public PromotionPo_Price getPromotionPO_Price() {
		return promotionPO_Price;
	}
	/**
	 * @return the promotionVO_Packages
	 */
	public ArrayList<PromotionPo_Package> getPromotionPO_Packages() {
		return promotionPO_Packages;
	}
	/**
	 * @param promotionPO_Customer the promotionPO_Customer to set
	 */
	public void setPromotionPO_Customer(PromotionPO_Customer promotionPO_Customer) {
		this.promotionPO_Customer = promotionPO_Customer;
	}
	/**
	 * @param promotionPO_Price the promotionPO_Price to set
	 */
	public void setPromotionPO_Price(PromotionPo_Price promotionPO_Price) {
		this.promotionPO_Price = promotionPO_Price;
	}
	/**
	 * @param promotionPO_Packages the promotionPO_Packages to set
	 */
	public void setPromotionPO_Packages(
			ArrayList<PromotionPo_Package> promotionPO_Packages) {
		this.promotionPO_Packages = promotionPO_Packages;
	}
	/**
	 * @return the commodityGiftPOs
	 */
	public ArrayList<CommodityGiftPO> getCommodityGiftPOs() {
		return commodityGiftPOs;
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
	
	
}
