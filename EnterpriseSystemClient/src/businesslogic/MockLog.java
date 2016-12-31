package businesslogic;

import java.io.Serializable;

public class MockLog extends Log implements Serializable{

	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	String data;
	String operator;
	String type;
	
	public MockLog(String data, String operator, String type) {
		super(data,0,0, operator, type);
		
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	
}
