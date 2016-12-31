package po;

import java.util.ArrayList;

import businesslogic.Commodity;
import vo.PromotionVO_Package;

public class PromotionPo_Package extends PromotionPO {
	
private static final long serialVersionUID = 6342427534020672605L;
	// Commdity_NeedToBuy;//这个包含了一个商品和一个数量
	ArrayList<Commodity> comoditys_NeedToBuy;// 特价包需要的所有商品以及其数量项
	ArrayList<Integer> commoditys_Amount;
	double price;// 这些商品合起来的特别总价

	public PromotionPo_Package(String name, String date_Start, String date_End,
			ArrayList<Commodity> comoditys_NeedToBuy,
			ArrayList<Integer> commoditys_Amount, double price) {
		super(name, date_Start, date_End);
		this.comoditys_NeedToBuy = comoditys_NeedToBuy;
		this.commoditys_Amount = commoditys_Amount;
		this.price = price;
	}

	public PromotionPo_Package(PromotionVO_Package vo) {
		super(vo.getName(), vo.getDate_Start(), vo.getDate_End());
		id = vo.getId();
		this.comoditys_NeedToBuy = vo.getCommoditys_NeedToBuy();
		this.commoditys_Amount = vo.getCommoditys_Amount();
		this.price = vo.getPrice();
	}

	/**
	 * @return the comoditys_NeedToBuy
	 */
	public ArrayList<Commodity> getCommoditys_NeedToBuy() {
		return comoditys_NeedToBuy;
	}

	/**
	 * @return the commoditys_Amount
	 */
	public ArrayList<Integer> getCommoditys_Amount() {
		return commoditys_Amount;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	// double oldPrice;//原来没有促销的时候要卖多少
}
