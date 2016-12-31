package vo;

import java.util.ArrayList;
import java.util.Iterator;

import po.PromotionPo_Package;
import businesslogic.Commodity;

public class PromotionVO_Package extends PromotionVO {

	// Commdity_NeedToBuy;//这个包含了一个商品和一个数量
	ArrayList<Commodity> commoditys_NeedToBuy;// 特价包需要的所有商品
	ArrayList<Integer> commoditys_Amount;
	double price;// 这些商品合起来的特别总价

	public PromotionVO_Package(String name, String date_Start, String date_End,
			ArrayList<Commodity> comoditys_NeedToBuy,
			ArrayList<Integer> commoditys_Amount, double price) {
		super(name, date_Start, date_End);
		this.commoditys_NeedToBuy = comoditys_NeedToBuy;
		this.commoditys_Amount = commoditys_Amount;
		this.price = price;
	}

	public PromotionVO_Package(PromotionPo_Package po) {
		super(po.getName(), po.getDate_start(), po.getDate_end());
		this.commoditys_NeedToBuy = po.getCommoditys_NeedToBuy();
		this.commoditys_Amount = po.getCommoditys_Amount();
		this.price = po.getPrice();
	}

	/**
	 * @return the comoditys_NeedToBuy
	 */
	public ArrayList<Commodity> getCommoditys_NeedToBuy() {
		return commoditys_NeedToBuy;
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

	public Commodity getCommodity(int i) {
		return commoditys_NeedToBuy.get(i);
	}

	public Integer getCommodityAmount(int i) {
		return commoditys_Amount.get(i);
	}

	public int getCommodityKindSize() {
		return commoditys_NeedToBuy.size();
	}

	// double oldPrice;//原来没有促销的时候要卖多少

	public int getDiscountMoney() {
		int result = 0;
		for (int j = 0; j < commoditys_NeedToBuy.size(); j++) {
			result += commoditys_NeedToBuy.get(j).getPurchasePrice() * commoditys_Amount.get(j)-price;//原总价-现总价
		}
		return result;
	}

}
