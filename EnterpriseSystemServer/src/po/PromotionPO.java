package po;

import java.io.Serializable;
import java.util.ArrayList;

import enumClass.ResultMessage;

public class PromotionPO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8631845590971653947L;

	/**
	 * customerPromotionPo：针对不同用户制定促销策略 packagePromotionPo： 组合商品降价
	 * pricePromotionPo： 总价促销策略
	 */

	int id;

	String name;
	String date_start;// 制定的日期
	String date_end;// 有效期，结束日期
	// boolean isValid;//当到达结束日期之后就无效了

	/**
	 * @return the id
	 */
	public PromotionPO(String name, String date_start, String date_end) {
		super();
		this.name = name;
		this.date_start = date_start;
		this.date_end = date_end;
	}
	
	
	

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}




	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the date_start
	 */
	public String getDate_start() {
		return date_start;
	}

	/**
	 * @return the date_end
	 */
	public String getDate_end() {
		return date_end;
	}

	// public int getId() {
	// return id;
	// }

	// public boolean getIsValid(){
	// return isValid;
	// }
	// public void setValid(){
	// this.isValid=isValid;
	// }

	// String date;
	// int promotionID;
	// String plan;
	// String money;
	// String type;
	// public PromotionPO(String date,int promotionID,String plan, String money,
	// String type) {
	// super();
	// this.date=date;
	// this.promotionID=promotionID;
	// this.plan = plan;
	// this.money = money;
	// this.type = type;
	// }
	//
	// public String getDate(){
	// return date;
	// }
	// public int getPromotionID(){
	// return promotionID;
	// }
	// /**
	// * @return the plan
	// */
	// public String getPlan() {
	// return plan;
	// }
	// /**
	// * @return the moner
	// */
	// public String getMoner() {
	// return money;
	// }
	// /**
	// * @return the type
	// */
	// public String getType() {
	// return type;
	// }

}
