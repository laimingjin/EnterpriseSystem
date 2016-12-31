package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.CustomerPO;
import temp_business.CustomerBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CustomerVO;
import vo.LogVO;
import vo.UserVO;
import dataservice.CustomerDataservice;
import enumClass.ResultMessage;

public class CustomerBLImp implements CustomerBLService {

	private CustomerDataservice customerDataService;
	LoginBLService loginBLService=new LoginBLImp();
	LogBLService logBLService=new LogBLImp();
	public CustomerBLImp() {
		try {
			customerDataService = (CustomerDataservice) Naming
					.lookup(StaticInfo.URL_CUSTOMER);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	//专门用于日志创造时候的～～
	public void Addlog(String operater){
		UserVO userVO=loginBLService.getUserVO();
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
	    	String dataNow = dateFormat.format( now ); 

		LogVO logVO=new LogVO(dataNow, logBLService.getNewID(), userVO.getUserID(), userVO.getUserName(), operater);
		logBLService.add(logVO);
	}
	public ResultMessage addCustomer(CustomerVO vo) {
//		if (customerDataService) {
//			
//		}
		ResultMessage resultMessage=ResultMessage.FAIL;
		CustomerPO po = new CustomerPO(vo);
		try {
			resultMessage=customerDataService.add(po);
			 if(resultMessage.equals(ResultMessage.SUCCESS)){
		       	  Addlog("新增客户成功");
		         }else{
		       	  Addlog("新增客户失败");
		         }
				return resultMessage;
			
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage deleteCustomer(CustomerVO vo) {
		CustomerPO po = new CustomerPO(vo);
		ResultMessage resultMessage=ResultMessage.FAIL;
		if (po.hasReceivableOrPayable()) {
		resultMessage=ResultMessage.CUSTOMER_HAS_RECEIVE_AND_PAY;
		}
		
		try {
			resultMessage= customerDataService.delete(po);
		} catch (RemoteException e) {
          }
		if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("删除客户成功");
	         }else{
	       	  Addlog("删除客户失败");
	         }
			return resultMessage;
		
	}

	public ResultMessage updateCustomer(CustomerVO newVo) {
		ResultMessage resultMessage=ResultMessage.FAIL;
		try {
			resultMessage= customerDataService.update(new CustomerPO(newVo));
		} catch (RemoteException e) {
			e.printStackTrace();
			resultMessage= ResultMessage.FAIL;
		}
		if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("更新客户成功");
	         }else{
	       	  Addlog("更新客户失败");
	         }
			return resultMessage;
	}

	public CustomerVO findCustomer(int id) {
		try {
			CustomerVO vo=new CustomerVO(customerDataService.findByID(id));
			return vo;
		} catch (RemoteException e) {
			e.printStackTrace();
			
		}
		return null;
	}

	public ArrayList<CustomerVO> findsCustomer(String info) {
		ArrayList<CustomerPO> customerPOs = new ArrayList<CustomerPO>();
		;
		try {
			customerPOs = customerDataService.findVague(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<CustomerVO> customerVOs = new ArrayList<CustomerVO>();

		for (int i = 0; i < customerPOs.size(); i++) {
			customerVOs.add(new CustomerVO(customerPOs.get(i)));
		}

		return customerVOs;
	}

	public void getInit() {
		// TODO Auto-generated method stub
try {
	customerDataService.getinit();
} catch (RemoteException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	}

	public ArrayList<CustomerVO> disPlayAll() {
		ArrayList<CustomerPO> customerPOs = new ArrayList<CustomerPO>();
		;
		try {
			customerPOs = customerDataService.displayAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<CustomerVO> customerVOs = new ArrayList<CustomerVO>();

		for (int i = 0; i < customerPOs.size(); i++) {
			customerVOs.add(new CustomerVO(customerPOs.get(i)));
		}
		return customerVOs;
	}

	// public ResultMessage deleteCustomer(CustomerVO vo) {
	// CustomerPO po=new CustomerPO(vo);
	// try {
	// return customerDataService.add(po);
	// } catch (RemoteException e) {
	// e.printStackTrace();
	// return ResultMessage.FAIL;
	// }
	// }

	public int getNewId() {

		try {
			return (customerDataService.getFinalID() + 1);
		} catch (RemoteException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public CustomerVO find(String name) {
		
		try {
			CustomerVO customerVO=new CustomerVO(customerDataService.findByName(name)) ;
			return customerVO;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
