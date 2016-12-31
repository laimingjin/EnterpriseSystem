package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.LogPO;
import temp_business.LogBLService;
import vo.LogVO;
import dataservice.LogDataService;
import enumClass.ResultMessage;

public class LogBLImp implements LogBLService {
	LogDataService logDataService;

	public LogBLImp() {
		try {
			logDataService = (LogDataService) Naming.lookup(StaticInfo.URL_LOG);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	public ResultMessage add(LogVO vo) {
		try {
			return logDataService.add(new LogPO(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return ResultMessage.FAIL;
	}

	public LogVO find(long id) {
		try {
			LogVO logVO = new LogVO(logDataService.find(id));
			return logVO;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public ArrayList<LogVO> findByTime(String timezone) {
		ArrayList<LogVO> logVOs = new ArrayList<LogVO>();
		ArrayList<LogPO> logPOs = new ArrayList<LogPO>();
		try {
			logPOs = logDataService.findByTime(timezone);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < logPOs.size(); i++) {
			logVOs.add(new LogVO(logPOs.get(i)));
		}
		return logVOs;
	}

	public ArrayList<LogVO> displayAll() {
		ArrayList<LogVO> logVOs = new ArrayList<LogVO>();
		ArrayList<LogPO> logPOs = new ArrayList<LogPO>();
		try {
			logPOs = logDataService.displayAll();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 0; i < logPOs.size(); i++) {
			logVOs.add(new LogVO(logPOs.get(i)));
		}
		return logVOs;
	}

	public long getNewID() {
		long ID = 0;
		try {
			ID = logDataService.getFinalID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ID + 1);
	}

	public ArrayList<LogVO> display(String operater) {
		ArrayList<LogPO> logPOs = null;
		try {
			logPOs = logDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<LogVO> logVOs = new ArrayList<LogVO>();
		for (int i = 0; i < logPOs.size(); i++) {
			if (logPOs.get(i).getOperater().equals(operater)) {
				logVOs.add(new LogVO(logPOs.get(i)));
			}
		}

		return logVOs;
	}

}
