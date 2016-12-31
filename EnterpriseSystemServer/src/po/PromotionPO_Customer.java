package po;


import businesslogic.Commodity;
import vo.PromotionVO_Customer;

public class PromotionPO_Customer extends PromotionPO{
private static final long serialVersionUID = -1428720685862847083L;
	int customerLevel;//用戶等級
	Commodity[] gifts;//属性包含商品
	int[] amountOfGifts;//赠送品数量
	double discount;
	
	


	
	public PromotionPO_Customer(PromotionVO_Customer vo) {
		super(vo.getName(), vo.getDate_Start(), vo.getDate_End());
		id=vo.getId();
		this.customerLevel=vo.getCustomerLevel();
		this.gifts = vo.getGifts();
		this.amountOfGifts = vo.getAmountOfGifts();
		this.discount = vo.getDiscount();
	}

	public PromotionPO_Customer(String date_start,String date_end,String name,int customerLevel,Commodity[] gifts,int[] amountOfGifts,double discount){
		super(name, date_start, date_end);
		this.customerLevel=customerLevel;
		this.gifts=gifts;
		this.amountOfGifts=amountOfGifts;
		this.discount=discount;
	}
	
	

	/**
	 * @return the serialversionuid
	 */
//	public static long getSerialversionuid() {
//		return serialVersionUID;
//	}

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