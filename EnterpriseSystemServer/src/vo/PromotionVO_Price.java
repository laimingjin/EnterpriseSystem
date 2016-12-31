package vo;

import businesslogic.Commodity;
import po.CommodityPO;
import po.PromotionPo_Price;

/**
 * 
 * 总经理可以制定针对不同总价的促销策略（赠品、赠送代金卷）。所有促销策略都有其实时间和间隔时间
 * td
 * 若同时满足两个总价策略，取总价较大的那一个
 * 
 * @author YSH 2014年12月11日下午2:41:31
 */
public class PromotionVO_Price extends PromotionVO {

	double price;

	Commodity commodity;// 属性包含 赠品的 商品
	int amountOfGift;// 赠品数量

	int gift_Money;// 代金券
	int amountOfGift_Money;// 代金券数量

	public PromotionVO_Price(String name, String date_Start, String date_End,
			double price, Commodity commodity, int amountOfGift,
			int gift_Money, int amountOfGift_Money) {
		super(name, date_Start, date_End);
		this.price = price;
		this.commodity = commodity;
		this.amountOfGift = amountOfGift;
		this.gift_Money = gift_Money;
		this.amountOfGift_Money = amountOfGift_Money;
	}

	public PromotionVO_Price(PromotionPo_Price po) {
		super(po.getName(), po.getDate_start(), po.getDate_end());
		id=po.getId();
		this.price = po.getPrice();
		this.commodity= po.getCommodity();
		this.amountOfGift = po.getAmountOfGift();
		this.gift_Money = po.getGift_Money();
		this.amountOfGift_Money = po.getAmountOfGift();
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the commodityVO
	 */
	public Commodity getCommodity() {
		return commodity;
	}

	/**
	 * @return the amountOfGift
	 */
	public int getAmountOfGift() {
		return amountOfGift;
	}

	/**
	 * @return the gift_Money
	 */
	public int getGift_Money() {
		return gift_Money;
	}

	/**
	 * @return the amountOfGift_Money
	 */
	public int getAmountOfGift_Money() {
		return amountOfGift_Money;
	}

	

	
	
	

}
