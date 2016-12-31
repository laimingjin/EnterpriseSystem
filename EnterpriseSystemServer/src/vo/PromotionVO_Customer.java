package vo;

import businesslogic.Commodity;
import po.CommodityPO;
import po.PromotionPO_Customer;

/**
 * 
 * 总经理可以针对不同级别的用户制定促销策略（赠品、价格折让、 赠送代金劵） 单级用户属性：用户等级，赠品，价格折让，赠送代金券
 * 
 * @author YSH 2014年12月11日下午2:22:08
 */
public class PromotionVO_Customer extends PromotionVO {

	// TODO 
	/**
	 * 此种写法，不能对同级用户赠送多种商品
	 */
	int customerLevel;//用戶等級
	Commodity[] gifts;// 属性包含商品
	int[] amountOfGifts;// 赠送品数量
	int gift_Money;// 代金券
	int amountOfGift_Money;// 代金券数量
	double discount;

	public PromotionVO_Customer(String name, String date_Start,
			String date_End, int customerLevel,Commodity[] gifts, int[] amountOfGifts,int gift_Money, int amountOfGift_Money,
			double discount) {
		super(name, date_Start, date_End);
		this.customerLevel=customerLevel;
		this.gifts = gifts;
		this.amountOfGifts = amountOfGifts;
		this.gift_Money = gift_Money;
		this.amountOfGift_Money = amountOfGift_Money;
		this.discount = discount;
	}

	public PromotionVO_Customer(PromotionPO_Customer po) {
		super(po.getName(), po.getDate_start(), po.getDate_end());
		id=po.getId();
		this.gifts = po.getGifts();
		this.amountOfGifts = po.getAmountOfGifts();
		this.discount = po.getDiscount();
	}

	

	/**
	 * @return the gifts
	 */
	public Commodity[] getGifts() {
		return gifts;
	}

	/**
	 * @return the amountOfGifts
	 */
	public int[] getAmountOfGifts() {
		return amountOfGifts;
	}

	/**
	 * @return the discount
	 */
	public double getDiscount() {
		return discount;
	}

	public int getCustomerLevel() {
		return customerLevel;
	}

}
