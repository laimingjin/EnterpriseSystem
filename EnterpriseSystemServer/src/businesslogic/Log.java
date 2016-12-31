package businesslogic;

import java.io.Serializable;

public class Log implements Serializable{
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	String date;
	long documentID;
	int operatorID;
	String operator;
	String type;
	
	public Log(String date,long documentID,int operatorID, String operator, String type) {
		super();
		this.date = date;
		this.documentID=documentID;
		this.operatorID=operatorID;
		this.operator = operator;
		this.type = type;
	}
	/**
	 * @return the data
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @return the operator
	 */
	public long getDocumentID() {
		return documentID;
	}
	public String getOperatorName() {
		return operator;
	}
	public int getOperatorID(){
		return operatorID;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
}
