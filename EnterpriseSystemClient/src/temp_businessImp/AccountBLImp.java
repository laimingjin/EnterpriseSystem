package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.AccountPO;
import dataservice.AccountDataService;
import enumClass.ResultMessage;
import temp_business.AccountBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.AccountVO;
import vo.LogVO;
import vo.UserVO;

public class AccountBLImp implements AccountBLService{

	private  AccountDataService accountdataService;   
	LoginBLService loginBLService=new LoginBLImp();
	LogBLService logBLService=new LogBLImp();
		public AccountBLImp() {
			try {
				accountdataService = (AccountDataService) Naming.lookup(StaticInfo.URL_ACCOUNT);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (RemoteException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
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
		//PO转VO
		private AccountVO POTrangeToVO(AccountPO p){
			if(p!=null){
				return new AccountVO(p.getAccountName(), p.getAccountPrice());
			}
			return null;
		}
		//VO转PO
		private AccountPO VOTrangeToPO(AccountVO v){
			if(v!=null){
				return new AccountPO(v.getAccountName(), v.getAccountPrice());
			}
			return null;
		}
	public ArrayList<AccountVO> displayAll() {
		// TODO Auto-generated method stub
		ArrayList<AccountPO> accountPOs=new ArrayList<AccountPO>();
		ArrayList<AccountVO> accountVOs=new ArrayList<AccountVO>();
		 try {
			 accountPOs=accountdataService.displayAll();
			if(accountPOs!=null){
				for(int i=0;i<accountPOs.size();i++){
					accountVOs.add(POTrangeToVO(accountPOs.get(i)));
					
				}
			}
			return accountVOs;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return accountVOs;
	}

	public ResultMessage addAccount(AccountVO vo) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage=ResultMessage.FAIL;
		AccountPO po=VOTrangeToPO(vo);
		try {
			resultMessage=accountdataService.add(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("新增账户成功");
	         }else{
	       	  Addlog("新增账户失败");
	         }
		return resultMessage;
	}

	public ResultMessage deleteAccount(String name) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage=ResultMessage.FAIL;
		try {
			AccountPO po=accountdataService.find(name);
			resultMessage=accountdataService.delete(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("删除账户成功");
	         }else{
	       	  Addlog("删除账户失败");
	         }
		return resultMessage;
	}

	public ResultMessage updateAccount(String name, AccountVO vo) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage=ResultMessage.FAIL;
		AccountPO newPo=VOTrangeToPO(vo);
		try {
			AccountPO po=accountdataService.find(name);
			if(po!=null){
			return accountdataService.update(po,newPo);
			}
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("更新账户名称成功");
	         }else{
	       	  Addlog("更新账户名称失败");
	         }
		return resultMessage;
	}

	public AccountVO findAccount(String name) {
		// TODO Auto-generated method stub
	//	AccountVO vo=null;
		try {
			AccountPO po=accountdataService.find(name);
		return POTrangeToVO(po);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<AccountVO> findsAccount(String info) {
		// TODO Auto-generated method stub
		ArrayList<AccountVO> vos=new ArrayList<AccountVO>();
		ArrayList<AccountPO>pos=new ArrayList<AccountPO>();
		try {
			pos=accountdataService.finds(info);
			if(pos!=null){
				for(int i=0;i<pos.size();i++){
					vos.add(POTrangeToVO(pos.get(i)));
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vos;
	}
	public void getinit(){
		try {
			accountdataService.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
