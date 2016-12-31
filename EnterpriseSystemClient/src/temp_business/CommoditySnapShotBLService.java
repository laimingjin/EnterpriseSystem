package temp_business;

import vo.CommoditySnapshotVO;

public interface CommoditySnapShotBLService {
	public CommoditySnapshotVO LookCommoditySnapShot();// 增商品

	public CommoditySnapshotVO findbyDate(String date);// 通过日期找库存快照

	public void output(String fileName, CommoditySnapshotVO vo);

}
