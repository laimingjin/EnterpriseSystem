package po;

import java.io.Serializable;

import businesslogic.User;

public class SystemPO implements Serializable{
	
//	private static final long serialVersionUID = 1L;
	String time;//操作时间
	String operater;//操作员
    String type;//操作类型
    User user;//操作对象
    public SystemPO(String time,String operater,String type,User user){
    	this.time=time;
    	this.operater=operater;
    	this.type=type;
    	this.user=user;
    	
    }
    
    public String getTime() {
  		return time;
  	}
  	public String getOperater() {
  		return operater;
  	}
  	public String getType() {
  		return type;
  	}
  	public User getUser() {
  		return user;
  	}
}
