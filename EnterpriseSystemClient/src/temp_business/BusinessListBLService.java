package temp_business;

import vo.BusinessListVO;

public interface BusinessListBLService {
	public  BusinessListVO showBusinessList(String time1zone,boolean outputNeeded);//经营情况表
}
