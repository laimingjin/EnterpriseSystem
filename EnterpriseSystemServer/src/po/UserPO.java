package po;

import java.io.Serializable;

import javax.swing.text.Position;

import vo.UserVO;
import enumClass.POSITION;

/**
 * UserPo以userName为判断标准
 * 
 * @author YSH 2014年12月8日上午11:16:25
 */
public class UserPO implements Serializable {

	//private static final long serialVersionUID = 1L;

	int userID;

	String userName;
	String password;
	POSITION theJob;
	int powerLevel;// TODO 权限

	/**
	 * @param userName
	 * @param password
	 * @param theJob
	 * @param powerLevel
	 */
	public UserPO(int userID, String userName, String password,
			POSITION theJob, int powerLevel) {
		this.userID = userID;
		this.userName = userName;
		this.password = password;
		this.theJob = theJob;
		this.powerLevel = powerLevel;
	}

	public int getUserID() {
		return userID;
	}

	public UserPO(UserVO vo) {
		this.userName = vo.getUserName();
		this.password = vo.getPassword();
		this.theJob = vo.getPostion();
		this.powerLevel = vo.getPowerLevel();
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public POSITION getTheJob() {
		return theJob;
	}

	public int getPowerLevel() {
		return powerLevel;
	}

	public String getTheJobString() {
		switch (theJob) {
		case SALE_MANAGER:
			return "销售人员";
		case SYSTEM_MANAGER:

			return "系统管理人员";
		case SUPER_SALE_MANAGER:

			return "销售经理";
		case GENERAL_MANAGER:

			return "总经理";
		case FINANCIAL_MANAGER:

			return "财务人员";
		case COMMODITY_MANAGER:

			return "库存人永远";
		default:
			return "错误职位";
		}
	}
}
