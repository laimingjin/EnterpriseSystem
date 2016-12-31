package businesslogic;

import java.io.Serializable;

import vo.UserVO;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int userID;
	String userName;
	String password;
	String theJob;
	int powerLevel;

	public User(Integer id, String name, String password, String job,
			int powerlevel) {
		userID = id;
		userName = name;
		this.password = password;
		theJob = job;
		powerLevel = powerlevel;
	}

	public User(UserVO userVO) {
		userID = userVO.getUserID();
		userName = userVO.getUserName();
		password = userVO.getPassword();
		theJob = userVO.getTheJob();
		powerLevel = userVO.getPowerLevel();
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

	public int getPowerLevel() {
		return powerLevel;
	}

	public String getTheJob() {
		return theJob;
	}

}
