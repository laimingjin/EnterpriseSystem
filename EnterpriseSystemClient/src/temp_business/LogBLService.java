package temp_business;

import java.util.ArrayList;

import vo.LogVO;
import enumClass.ResultMessage;

public interface LogBLService {
	public ResultMessage add(LogVO vo);

	public LogVO find(long id);

	public ArrayList<LogVO> findByTime(String timezone);

	public ArrayList<LogVO> displayAll();

	public long getNewID();

	/**
	 * 根据操作员姓名 YSH 2014年12月27日下午1:27:49
	 * 
	 * @param operater
	 * @return
	 */
	public ArrayList<LogVO> display(String operater);
}
