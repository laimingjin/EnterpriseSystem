package temp_business;

import java.util.ArrayList;

import vo.PromotionVO_Customer;
import vo.PromotionVO_Package;
import vo.PromotionVO_Price;
import enumClass.ResultMessage;

public interface PromotionBLService {

	public ResultMessage add(PromotionVO_Customer vo);

	public ResultMessage update(PromotionVO_Customer vo);

	public ResultMessage delete(PromotionVO_Customer vo);

	public ArrayList<PromotionVO_Customer> displayAllPromotionVO_Customers();

	public ResultMessage add(PromotionVO_Package vo);

	public ResultMessage update(PromotionVO_Package vo);

	public ResultMessage delete(PromotionVO_Package vo);

	public ArrayList<PromotionVO_Package> displayAllpPromotionVO_Packages();

	public ResultMessage add(PromotionVO_Price vo);

	public ResultMessage update(PromotionVO_Price vo);

	public ResultMessage delete(PromotionVO_Price vo);

	public ArrayList<PromotionVO_Price> displayAllpPromotionVO_Prices();

	public int getNewID_Customer();

	public int getNewID_Price();

	public int getNewID_Package();

}
