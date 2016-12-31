package temp_business;

import vo.SalesListVO;

public interface SalesListBLService {
	public SalesListVO showSalesList(String time1, String time2,
			String commodityName, String customerName, String executive,
			String warehouse, boolean outputNeeded);

}
