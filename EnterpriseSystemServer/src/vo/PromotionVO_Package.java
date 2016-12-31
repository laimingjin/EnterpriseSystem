package vo;

import java.awt.event.ItemEvent;
import java.util.ArrayList;

import businesslogic.Commodity;
import po.CommodityPO;
import po.PromotionPo_Package;

public class PromotionVO_Package extends PromotionVO {

	// Commdity_NeedToBuy;//这个包含了一个商品和一个数量
	ArrayList<Commodity> comoditys_NeedToBuy;// 特价包需要的所有商品以及其数量项
	ArrayList<Integer> commoditys_Amount;
	double price;// 这些商品合起来的特别总价

	public PromotionVO_Package(String name, String date_Start, String date_End,
			ArrayList<Commodity> comoditys_NeedToBuy,
			ArrayList<Integer> commoditys_Amount, double price) {
		super(name, date_Start, date_End);
		this.comoditys_NeedToBuy = comoditys_NeedToBuy;
		this.commoditys_Amount = commoditys_Amount;
		this.price = price;
	}

	public PromotionVO_Package(PromotionPo_Package po) {
		super(po.getName(), po.getDate_start(), po.getDate_end());
		this.comoditys_NeedToBuy = po.getComoditys_NeedToBuy();
		this.commoditys_Amount = po.getCommoditys_Amount();
		this.price = po.getPrice();
	}

	/**
	 * @return the comoditys_NeedToBuy
	 */
	public ArrayList<Commodity> getComoditys_NeedToBuy() {
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
