package po;

import java.io.Serializable;

import vo.LogVO;

public class LogPO implements Serializable {
	
	private static final long serialVersionUID = -8618177103168167485L;
	String date;
	long id;
	int operaterID;
	String operater;
	String operationType;
	public LogPO(String date, Long id,int operaterID,String operater, String operationType) {
		super();
		this.date=date;
		this.id=id;
		this.operaterID=operaterID;
		this.operater = operater;
		this.date = date;
		this.operationType = operationType;
	}
	public LogPO(LogVO logVO){
		date=logVO.getDate();
		id=logVO.getId();
		operaterID=logVO.getOperaterID();
		operater=logVO.getOperater();
		operationType=logVO.getOperationType();
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
	
}
