package vo;

import po.UserPO;
import enumClass.POSITION;

public class UserVO {
	private int userID;
	private String userName;
	private String password;
	private String theJob;
	private int powerLevel;

	public UserVO(int userID, String userName, String password, String theJob,
			int powerLevel) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.theJob = theJob;
		this.powerLevel = powerLevel;
		
		
//		String[] jobs = { "库存管理人员", "进货销售人员","销售经理", "财务人员", "总经理", "系统管理人员" };
//		POSITION[] positions = { POSITION.COMMODITY_MANAGER,
//				POSITION.SALE_MANAGER, POSITION.SUPER_SALE_MANAGER,POSITION.FINANCIAL_MANAGER,
//				POSITION.GENERAL_MANAGER, POSITION.SYSTEM_MANAGER };
//		

	}

	public UserVO(UserPO po) {
		this.userID = po.getUserID();
		this.userName = po.getUserName();
		this.password = po.getPassword();
		this.theJob = po.getTheJobString();
		this.powerLevel = po.getPowerLevel();
	}

	public int getUserID() {
		return userID;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getTheJob() {
		return theJob;
	}

	public int getPowerLevel() {
		return powerLevel;
	}

	/**
	 * @param theJob
	 *            the theJob to set
	 */
	public void setTheJob(String theJob) {
		this.theJob = theJob;
	}

	public String toString() {
		return userName;
	}

	public POSITION getPostion() {
		String[] jobs = { "库存管理人员", "进货销售人员", "销售经理","财务人员", "总经理", "系统管理人员" };
		POSITION[] positions = { POSITION.COMMODITY_MANAGER,
				POSITION.SALE_MANAGER,POSITION.SUPER_SALE_MANAGER, POSITION.FINANCIAL_MANAGER,
				POSITION.GENERAL_MANAGER, POSITION.SYSTEM_MANAGER };

		for (int i = 0; i < jobs.length; i++) {
			if (theJob.equals(jobs[i])) {
				return positions[i];
			}
		}
		return null;
	}
}
