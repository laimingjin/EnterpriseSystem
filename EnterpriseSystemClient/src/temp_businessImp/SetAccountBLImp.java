package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.SetAccountPO;
import temp_business.AccountBLService;
import temp_business.CommodityBLService;
import temp_business.CustomerBLService;
import temp_business.SetAccountBLService;
import vo.AccountVO;
import vo.CommodityVO;
import vo.CustomerVO;
import vo.SetAccountVO;
import businesslogic.Account;
import businesslogic.CommodityLineItem;
import businesslogic.Customer;
import dataservice.SetAccountDataService;
import enumClass.ResultMessage;

public class SetAccountBLImp implements SetAccountBLService {

	private SetAccountDataService setAccountdataService;

	public SetAccountBLImp() {
		try {
			setAccountdataService = (SetAccountDataService) Naming
					.lookup(StaticInfo.URL_SETACCOUNT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	// PO转VO
	private SetAccountVO POTrangeToVO(SetAccountPO p) {
		if (p != null) {
			return new SetAccountVO(p.getDate(), p.getSetAccountID(),
					p.getCommodityList(), p.getCustomerList(),
					p.getAccountList());
		}
		return null;
	}

	// VO转PO
	@SuppressWarnings("unused")
	private SetAccountPO VOTrangeToPO(SetAccountVO v) {
		if (v != null) {
			return new SetAccountPO(v.getDate(), v.getSetAccountID(),
					v.getCommodityList(), v.getCustomerList(),
					v.getAccountList());
		}
		return null;
	}

	// VO转CommodityLineItem
	private CommodityLineItem VOTrangeTolineItem(CommodityVO p) {

		return new CommodityLineItem(p.getCommoditySortID(),
				p.getCommoditySortName(), p.getCommodityID(),
				p.getCommodityName(), p.getCommodityModel(),
				p.getPurchasePrice(), p.getRetailPrice(),
				p.getLatestPurchasePrice(), p.getLatestRetailPrice());
	}

	// CustomerVO转Customer
	private Customer TrangeToCustomer(CustomerVO p) {

		return new Customer(p.getCustomerID(), p.getCustomerType(),
				p.getCustomerRank(), p.getCustomerName(), p.getTelePhone(),
				p.getCustomerAddress(), p.getCustomerPostCode(), p.geteMail(),
				p.getReceivableLimit(), p.getReceivableAmount(),
				p.getPayableAmount(), p.getOperator());
	}

	// AccountVO 转Account
	private Account TrangeToAccount(AccountVO p) {
		return new Account(p.getAccountName(), p.getAccountPrice());
	}

	public int getFinalID() throws RemoteException {
		return setAccountdataService.getFinalID();
	}

	public ResultMessage addSetAccount(String today) throws RemoteException {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		ResultMessage resultMessage = ResultMessage.FAIL;
		int id = getFinalID() + 1;
		CommodityBLService commodityBLImp = new CommodityBLImp();
		CustomerBLService customerBLImp = new CustomerBLImp();
		AccountBLService accountBLImp = new AccountBLImp();
		ArrayList<CommodityLineItem> commodityList = new ArrayList<CommodityLineItem>();
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		ArrayList<Account> accountList = new ArrayList<Account>();

		ArrayList<CommodityVO> commodityVOs = commodityBLImp.displayAll();
		ArrayList<CustomerVO> customerVOs = customerBLImp.disPlayAll();
		ArrayList<AccountVO> accountVOs = accountBLImp.displayAll();

		if (commodityVOs != null) {
			for (int i = 0; i < commodityVOs.size(); i++) {
				commodityList.add(VOTrangeTolineItem(commodityVOs.get(i)));
			}

		}
		if (customerVOs != null) {
			for (int j = 0; j < customerVOs.size(); j++) {
				customerList.add(TrangeToCustomer(customerVOs.get(j)));
			}
		}

		if (accountVOs != null) {
			for (int k = 0; k < accountVOs.size(); k++) {
				accountList.add(TrangeToAccount(accountVOs.get(k)));
			}
		}
		SetAccountPO po = new SetAccountPO(today, id, commodityList,
				customerList, accountList);
		return setAccountdataService.add(po);
	}

	public SetAccountVO findSetAccount(String exactTime) throws RemoteException {
		// TODO Auto-generated method stub
		SetAccountVO setAccountVO = null;
		try {
			SetAccountPO setAccountPO = setAccountdataService
					.findByExactTime(exactTime);
			if (setAccountPO != null) {
				setAccountVO = POTrangeToVO(setAccountPO);
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return setAccountVO;
	}

	public ArrayList<SetAccountVO> finds(String timezone)
			throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SetAccountVO> vos = new ArrayList<SetAccountVO>();
		ArrayList<SetAccountPO> pos = new ArrayList<SetAccountPO>();
		try {
			pos = setAccountdataService.findByTimezone(timezone);
			if (pos != null) {
				for (int i = 0; i < pos.size(); i++) {
					vos.add(POTrangeToVO(pos.get(i)));
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return vos;
	}

	public ArrayList<SetAccountVO> displayAll() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SetAccountVO> vos = new ArrayList<SetAccountVO>();
		ArrayList<SetAccountPO> pos = new ArrayList<SetAccountPO>();
		pos = setAccountdataService.displayAll();
		if (pos != null) {
			for (int i = 0; i < pos.size(); i++) {
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}

	public void endManagement() {
		// TODO Auto-generated method stub

	}

	public void getinit() {
		try {
			setAccountdataService.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
