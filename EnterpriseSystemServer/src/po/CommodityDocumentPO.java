package po;

import java.io.Serializable;

import enumClass.PROBLEM;
import businesslogic.Commodity;

public class CommodityDocumentPO  implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = 6366506924302327705L;
private  String date;
	private int documentID;                 //单据编号
	private Commodity commodity;            //出错的商品
	private PROBLEM problem;				// 出错类型
	private int realQuantity;				// 实际库存
	private boolean isPass;// 是否通过审批
	private boolean isSend;// 是否已经提交审批
	private boolean isDealed;
	public  CommodityDocumentPO(String d,int di,Commodity c,PROBLEM p,int rq,boolean isPass,
			boolean isSend,boolean isDealed	){
		documentID=di;              //单据编号
		commodity =c;
	    problem=p;
	    date=d;
		realQuantity=rq;			//实际库存
		this.isPass = isPass;
		this.isSend = isSend;
		this.isDealed=isDealed;
	}
	public int getDocumentID() {
		return documentID;
	}
	public Commodity getCommodity() {
		return commodity;
	}
	public PROBLEM getProblem() {
		return problem;
	}
	public boolean isPass() {
		return isPass;
	}

	public void setPass(boolean isPass) {
		this.isPass = isPass;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}
public boolean isDealed(){
	return isDealed;
}
public void setDealed(boolean isDealed){
	this.isDealed=isDealed;
}
	public String getDate() {
		return date;
	}
	public int getRealQuantity() {
		return realQuantity;
	}

}
