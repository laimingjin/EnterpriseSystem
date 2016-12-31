package vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * customerPromotionPo：针对不同用户制定促销策略 packagePromotionPo： 组合商品降价 pricePromotionPo：
 * 总价促销策略
 * 
 * @author YSH 2014年12月11日下午4:27:15
 */
public class PromotionVO {

	int id;
	String name;
	String date_Start;// 制定的日期
	String date_End;// 有效期，结束日期

	public PromotionVO(String name, String date_Start, String date_End) {
		super();
		this.name = name;
		this.date_Start = date_Start;
		this.date_End = date_End;
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
	 * @return the date_Start
	 */
	public String getDate_Start() {
		return date_Start;
	}

	/**
	 * @return the date_End
	 */
	public String getDate_End() {
		return date_End;
	}
	
	public boolean checkValidity(){
		return Integer.parseInt(date_End)>Integer.parseInt(getDate());
	}
	private String getDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		return dateFormat.format( now ); 
	}

}
