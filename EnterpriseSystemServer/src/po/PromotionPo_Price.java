package po;

import businesslogic.Commodity;
import vo.PromotionVO_Price;



public class PromotionPo_Price extends PromotionPO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7057684675013036067L;

	double price;

	Commodity commodity;// 属性包含 赠品的 商品
	int amountOfGift;// 赠品数量

	int gift_Money;// 代金券
	int amountOfGift_Money;// 代金券数量

	public PromotionPo_Price(String name, String date_Start, String date_End,
			double price, Commodity commodity, int amountOfGift,
			int gift_Money, int amountOfGift_Money) {
		super(name, date_Start, date_End);
		this.price = price;
		this.commodity = commodity;
		this.amountOfGift = amountOfGift;
		this.gift_Money = gift_Money;
		this.amountOfGift_Money = amountOfGift_Money;
	}

	public PromotionPo_Price(PromotionVO_Price vo) {
		super(vo.getName(), vo.getDate_Start(), vo.getDate_End());
		id=vo.getId();
		this.price = vo.getPrice();
		this.commodity = vo.getCommodity();
		this.amountOfGift = vo.getAmountOfGift();
		this.gift_Money = vo.getGift_Money();
		this.amountOfGift_Money = vo.getAmountOfGift_Money();
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @return the commodityPO
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
