package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import po.LogPO;
import dataservice.LogDataService;
import enumClass.ResultMessage;

public class LogData_Ser extends UnicastRemoteObject implements LogDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/log.out";

	// 存放所有PO的类
	ArrayList<LogPO> logPOs;

	// //单例模式，Account只需要一个实例
	// private static final LogData_Ser LOG_DATA_SER = new LogData_Ser();
	// public static LogData_Ser getInstance() {
	// return LOG_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public LogData_Ser() throws RemoteException {
		logPOs = (ArrayList<LogPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (logPOs == null) {
			logPOs = new ArrayList<LogPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(logPOs, address);
	}

	// //////////////////////////////////////////////

	public LogPO find(long id) throws RemoteException {
		// TODO Auto-generated method stub
		if(logPOs.size()!=0){
			for (int i = 0; i < logPOs.size(); i++) {
			if (logPOs.get(i).getId() == id) {
				// show();
				return logPOs.get(i);
			}
		}
		}
		return null;
	}

	public ArrayList<LogPO> findByTime(String timezone) throws RemoteException {
		// TODO Auto-generated method stub
		if(logPOs.size()==0){
			return null;
		}
		ArrayList<LogPO> al = new ArrayList<LogPO>();
		String[] times = timezone.split(",");
		long time1 = Long.parseLong(times[0]);
		long time2 = Long.parseLong(times[1]);
		for (int i = 0; i < logPOs.size(); i++) {
			long date = Long.parseLong(logPOs.get(i).getDate());
			if (date >= time1 && date <= time2) {
				al.add(logPOs.get(i));
			}
		}
		//show();
		//System.out.println();
		return al;
	}

	public long getFinalID()  throws RemoteException{
		if (logPOs.isEmpty()) {
			//show();
			return 0;
		} else {
			//show();
			return logPOs.get(logPOs.size() - 1).getId();
		}
	}

	public ResultMessage add(LogPO po)  throws RemoteException{
		logPOs.add(po);
		output();
	//	show();
		return ResultMessage.SUCCESS;
	}

	public ArrayList<LogPO>displayAll()throws RemoteException{
		return logPOs;
	}
	private void show() {
		for (int j = 0; j < logPOs.size(); j++) {
			String list = logPOs.get(j).getDate() + " " + logPOs.get(j).getId()
					+ " " + logPOs.get(j).getOperaterID() + " "
					+ logPOs.get(j).getOperater() + " "
					+ logPOs.get(j).getOperationType();
			System.out.println(list);
		}
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultMessage init() throws RemoteException {
		// TODO Auto-generated method stub
		long ID=1;
		LogPO logPO=new LogPO("20131212",ID,1,"xt","增加库存人员");
		LogPO logPO1=new LogPO("20131215",ID+1,1,"xt","增加销售人员");
		LogPO logPO2=new LogPO("20131217",ID+2,1,"xt","增加财务人员");
		LogPO logPO3=new LogPO("20131222",ID+3,1,"xt","增加总经理");
	
		logPOs.add(logPO);
		logPOs.add(logPO1);
		logPOs.add(logPO2);
		logPOs.add(logPO3);
	
		output();
		return null;
		
		
	}

}
