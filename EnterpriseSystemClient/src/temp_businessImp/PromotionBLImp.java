package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

import po.PromotionPO_Customer;
import po.PromotionPo_Package;
import po.PromotionPo_Price;
import temp_business.PromotionBLService;
import vo.PromotionVO_Customer;
import vo.PromotionVO_Package;
import vo.PromotionVO_Price;
import dataservice.PromotionDataService;
import enumClass.ResultMessage;

public class PromotionBLImp implements PromotionBLService {

	private PromotionDataService promotionDataService;

	public PromotionBLImp() {
		try {
			promotionDataService = (PromotionDataService) Naming
					.lookup(StaticInfo.URL_PROMOTION);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public ResultMessage add(PromotionVO_Customer vo) {
		try {
			PromotionPO_Customer po_Customer = new PromotionPO_Customer(vo);
			po_Customer.setId(getNewID_Customer() + 1);
			return promotionDataService.add(po_Customer);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage update(PromotionVO_Customer vo) {
		try {
			return promotionDataService.update(new PromotionPO_Customer(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage delete(PromotionVO_Customer vo) {
		try {
			return promotionDataService.delete(new PromotionPO_Customer(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ArrayList<PromotionVO_Customer> displayAllPromotionVO_Customers() {
		ArrayList<PromotionPO_Customer> po_Customers = new ArrayList<PromotionPO_Customer>();
		;
		try {
			po_Customers = promotionDataService.dispAllPromotionCustomer();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<PromotionVO_Customer> vo_Customers = new ArrayList<PromotionVO_Customer>();

		for (int i = 0; i < po_Customers.size(); i++) {
			vo_Customers.add(new PromotionVO_Customer(po_Customers.get(i)));
		}

		return vo_Customers;
	}

	public ResultMessage add(PromotionVO_Package vo) {
		try {
			PromotionPo_Package po_Package = new PromotionPo_Package(vo);
			po_Package.setId(getNewID_Package() + 1);
			return promotionDataService.add(po_Package);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage update(PromotionVO_Package vo) {
		try {
			return promotionDataService.update(new PromotionPo_Package(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage delete(PromotionVO_Package vo) {
		try {
			return promotionDataService.delete(new PromotionPo_Package(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ArrayList<PromotionVO_Package> displayAllpPromotionVO_Packages() {
		ArrayList<PromotionPo_Package> po_Customers = new ArrayList<PromotionPo_Package>();
		;
		try {
			po_Customers = promotionDataService.dispAllPromotionPackage();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<PromotionVO_Package> vo_Customers = new ArrayList<PromotionVO_Package>();

		for (int i = 0; i < po_Customers.size(); i++) {
			vo_Customers.add(new PromotionVO_Package(po_Customers.get(i)));
		}

		return vo_Customers;
	}

	public ResultMessage add(PromotionVO_Price vo) {
		try {
			PromotionPo_Price po_Price = new PromotionPo_Price(vo);
			po_Price.setId(getNewID_Price() + 1);
			return promotionDataService.add(po_Price);
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage update(PromotionVO_Price vo) {
		try {
			return promotionDataService.update(new PromotionPo_Price(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ResultMessage delete(PromotionVO_Price vo) {
		try {
			return promotionDataService.delete(new PromotionPo_Price(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
			return ResultMessage.FAIL;
		}
	}

	public ArrayList<PromotionVO_Price> displayAllpPromotionVO_Prices() {
		ArrayList<PromotionPo_Price> po_Customers = new ArrayList<PromotionPo_Price>();
		;
		try {
			po_Customers = promotionDataService.dispAllPromotionPrice();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		ArrayList<PromotionVO_Price> vo_Customers = new ArrayList<PromotionVO_Price>();

		for (int i = 0; i < po_Customers.size(); i++) {
			vo_Customers.add(new PromotionVO_Price(po_Customers.get(i)));
		}

		return vo_Customers;
	}

	public int getNewID_Customer() {
		try {
			return (promotionDataService.getFinalID_CustemerProm() + 1);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNewID_Price() {
		try {
			return (promotionDataService.getFinalID_PriceProm() + 1);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int getNewID_Package() {
		try {
			return (promotionDataService.getFinalID_PackageProm() + 1);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}

}
