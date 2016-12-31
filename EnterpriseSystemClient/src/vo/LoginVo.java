package vo;

/**
 * 登陆信息类，只用于登陆窗口
 * @author YSH
 *2014年12月10日下午1:16:24
 */
public class LoginVo {

	private String admin;
	private String Password;
	public LoginVo(String admin, String password) {
		super();
		this.admin = admin;
		Password = password;
	}
	/**
	 * @return the admin
	 */
	public String getAdmin() {
		return admin;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return Password;
	}
	
}
