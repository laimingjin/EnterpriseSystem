package vo;

import po.LogPO;

public class LogVO {
	String date;
	long id;
	int operaterID;
	String operater;
	String operationType;
	public LogVO(String date, Long id,int operaterID,String operater, String operationType) {
		super();
		this.date=date;
		this.id=id;
		this.operaterID=operaterID;
		this.operater = operater;
		this.date = date;
		this.operationType = operationType;
	}
	public LogVO(LogPO logPO){
		this.date=logPO.getDate();
		id=logPO.getId();
		operaterID=logPO.getOperaterID();
		operater=logPO.getOperater();
		operationType=logPO.getOperationType();
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @return the operater
	 */
	public String getOperater() {
		return operater;
	}
	/**
	 * @return the data
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @return the operationType
	 */
	public String getOperationType() {
		return operationType;
	}
	public int getOperaterID(){
		return operaterID;
	}
	public String toString(){
		return Long.toString(id);
	}
}
