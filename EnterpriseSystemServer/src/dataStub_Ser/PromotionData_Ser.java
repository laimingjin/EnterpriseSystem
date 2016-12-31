package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import businesslogic.Commodity;
import po.CommodityPO;
import po.PromotionPO;
import po.PromotionPO_Customer;
import po.PromotionPo_Package;
import po.PromotionPo_Price;

import dataservice.PromotionDataService;
import enumClass.ResultMessage;

public class PromotionData_Ser extends UnicastRemoteObject implements
		PromotionDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	// String address = "file/promotion.out";
	String address1 = "file/promotion_package.out";
	String address2 = "file/promotion_price.out";
	String address3 = "file/promotion_customer.out";
	// 存放所有PO的类
	// ArrayList<PromotionPO> promotionPOs;
	ArrayList<PromotionPo_Package> promotionPO_Packages;
	ArrayList<PromotionPo_Price> promotionPO_Prices;
	ArrayList<PromotionPO_Customer> promotionPO_Customers;

	// // 单例模式，Account只需要一个实例
	// private static final PromotionData_Ser PROMOTION_DATA_SER = new
	// PromotionData_Ser();
	//
	// public static PromotionData_Ser getInstance() {
	// return PROMOTION_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public PromotionData_Ser() throws RemoteException {
		// promotionPOs = (ArrayList<PromotionPO>) rw.read(address);
		promotionPO_Packages = (ArrayList<PromotionPo_Package>) rw
				.read(address1);
		promotionPO_Prices = (ArrayList<PromotionPo_Price>) rw.read(address2);
		promotionPO_Customers = (ArrayList<PromotionPO_Customer>) rw
				.read(address3);
		// 若为第一次运行则初始化accountPOs
		// if (promotionPOs == null) {
		// promotionPOs = new ArrayList<PromotionPO>();
		// }
		if (promotionPO_Packages == null) {
			promotionPO_Packages = new ArrayList<PromotionPo_Package>();
			output1();
		}
		if (promotionPO_Prices == null) {
			promotionPO_Prices = new ArrayList<PromotionPo_Price>();
			output2();
		}
		if (promotionPO_Customers == null) {
			promotionPO_Customers = new ArrayList<PromotionPO_Customer>();
			output3();
		}
	}

	// /**
	// * 更新数据 2014年11月30日
	// */
	// private void output() {
	// rw.write(promotionPOs, address);
	// }

	private void output1() {
		rw.write(promotionPO_Packages, address1);
	}

	private void output2() {
		rw.write(promotionPO_Prices, address2);
	}

	private void output3() {
		rw.write(promotionPO_Customers, address3);
	}

	// //////////////////////////////////////////////

	public ResultMessage add(PromotionPo_Package po) throws RemoteException {
		promotionPO_Packages.add(po);
		output1();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage add(PromotionPo_Price po) throws RemoteException {
		promotionPO_Prices.add(po);
		output2();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage add(PromotionPO_Customer po) throws RemoteException {
		promotionPO_Customers.add(po);
		output3();

		return ResultMessage.SUCCESS;
	}

	// 是根据什么来查找销售策略的-----------日期和id唯一确定一个策略
	public ResultMessage update(PromotionPo_Package po) throws RemoteException {
		// TODO Auto-generated method stub
		String date = po.getDate_start();
		for (int i = 0; i < promotionPO_Packages.size(); i++) {
			if (promotionPO_Packages.get(i).getDate_start().equals(date)
					&& promotionPO_Packages.get(i).getDate_end()
							.equals(po.getDate_end())) {
				promotionPO_Packages.set(i, po);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output1();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(PromotionPo_Price po) throws RemoteException {
		// TODO Auto-generated method stub
		String date = po.getDate_start();
		for (int i = 0; i < promotionPO_Prices.size(); i++) {
			if (promotionPO_Prices.get(i).getDate_start().equals(date)
					&& promotionPO_Prices.get(i).getDate_end()
							.equals(po.getDate_end())) {
				promotionPO_Prices.set(i, po);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output2();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(PromotionPO_Customer po) throws RemoteException {
		// TODO Auto-generated method stub
		String date = po.getDate_start();
		for (int i = 0; i < promotionPO_Customers.size(); i++) {
			if (promotionPO_Customers.get(i).getDate_start().equals(date)
					&& promotionPO_Customers.get(i).getDate_end()
							.equals(po.getDate_end())) {
				promotionPO_Customers.set(i, po);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output3();
		return ResultMessage.SUCCESS;
	}

	// public void show(){
	//
	// for(int i=0;i<promotionPOs.size();i++){
	// String
	// list=promotionPOs.get(i).getDate()+" "+promotionPOs.get(i).getPromotionID()+" "+promotionPOs.get(i).getPlan()+" "+promotionPOs.get(i).getMoner()+" "+promotionPOs.get(i).getType();
	// System.out.println(list);
	//
	// }
	// }
	// public int getFinalID(){
	// if(! promotionPO_Packages.isEmpty()){
	// return
	// }
	// }

	// 查找某时间制定的所以促销策略，这是查看有效的促销策略，所以在date之后没有下一次的促销策略
	public ArrayList<PromotionPo_Package> findPackages(String date)
			throws RemoteException {
		ArrayList<PromotionPo_Package> al = new ArrayList<PromotionPo_Package>();
		// int index = -1;// 记录第一次到达所要的日期时的位置
		// for (int i = 0; i < promotionPO_Packages.size(); i++) {
		// if (promotionPO_Packages.get(i).getDate().equals(date)) {
		// index = i;
		// break;
		// }
		// }
		// if (index == -1) {// 没找到该日期的
		// return null;
		// }
		// al = dispAllPromotionPackage(index);
		return al;
	}

	public ArrayList<PromotionPo_Price> findPrices(String date)
			throws RemoteException {
		ArrayList<PromotionPo_Price> al = new ArrayList<PromotionPo_Price>();
		// int index = -1;// 记录第一次到达所要的日期时的位置
		// for (int i = 0; i < promotionPO_Prices.size(); i++) {
		// if (promotionPO_Prices.get(i).getDate().equals(date)) {
		// index = i;
		// break;
		// }
		// }
		// if (index == -1) {// 没找到该日期的
		// return null;
		// }
		// al = dispAllPromotionPrice(index);
		return al;
	}

	public ArrayList<PromotionPO_Customer> findCustomers(String date)
			throws RemoteException {
		ArrayList<PromotionPO_Customer> al = new ArrayList<PromotionPO_Customer>();
		// int index = -1;// 记录第一次到达所要的日期时的位置
		// for (int i = 0; i < promotionPO_Customers.size(); i++) {
		// if (promotionPO_Customers.get(i).getDate().equals(date)) {
		// index = i;
		// break;
		// }
		// }
		// if (index == -1) {// 没找到该日期的
		// return null;
		// }
		// al = dispAllPromotionCustomer(index);
		return al;
	}

	// 从begin位置处开始展示所有的促销策略
	public ArrayList<PromotionPo_Package> dispAllPromotionPackage()
			throws RemoteException {
		
		return promotionPO_Packages;
	}

	public ArrayList<PromotionPo_Price> dispAllPromotionPrice()
			throws RemoteException {
		
		return promotionPO_Prices;
	}

	public ArrayList<PromotionPO_Customer> dispAllPromotionCustomer()
			throws RemoteException {
		
		return promotionPO_Customers;
	}

	public ResultMessage finish() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultMessage delete(PromotionPo_Package po) throws RemoteException {
		String date = po.getDate_start();
		int id = po.getId();
		for (int i = 0; i < promotionPO_Packages.size(); i++) {
			if (promotionPO_Packages.get(i).getDate_start().equals(date)
					&& promotionPO_Packages.get(i).getId() == id) {
				promotionPO_Packages.remove(i);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output1();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(PromotionPo_Price po) throws RemoteException {
		String date = po.getDate_start();
		int id = po.getId();
		for (int i = 0; i < promotionPO_Prices.size(); i++) {
			if (promotionPO_Prices.get(i).getDate_start().equals(date)
					&& promotionPO_Prices.get(i).getId() == id) {
				promotionPO_Prices.remove(i);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output2();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage delete(PromotionPO_Customer po) throws RemoteException {
		String date = po.getDate_start();
		int id = po.getId();
		for (int i = 0; i < promotionPO_Customers.size(); i++) {
			if (promotionPO_Customers.get(i).getDate_start().equals(date)
					&& promotionPO_Customers.get(i).getId() == id) {
				promotionPO_Customers.remove(i);
			}
			// if(promotionPOs.get(i).getPromotionID()==id){
			// promotionPOs.set(i, po);
			// }
		}
		output3();
		return ResultMessage.SUCCESS;
	}

	public void getInit() throws RemoteException {
		Commodity[] gifts;
		gifts = new Commodity[] {
				new Commodity( "电灯泡",005, "飞利浦", 0001, "990e", 0, 100, 100,
						0, 0, "20141206", 0, 10),
				new Commodity("电灯泡",005,   "阳光", 0002,"991w", 0, 100, 100, 0,
						0, "20141206", 0, 10),
				new Commodity("电灯泡",005,  "阳谷",0003,  "992r", 0, 100, 100, 0,
						0, "20141206", 0, 10), };
		int[] amountOfGifts = new int[] { 2, 2, 1 };
		double discount =1.0;

		PromotionPO_Customer promotionPO_Customer1 = new PromotionPO_Customer(
				"hehe","20141225", "20150101",  333,gifts, amountOfGifts, discount);
		promotionPO_Customers.add(promotionPO_Customer1);

		ArrayList<Commodity> commodity_list = new ArrayList<Commodity>();
		commodity_list.add(new Commodity( "电灯泡",005,  "飞利浦",0001, "990e", 0,
				100, 100, 150, 200, "20141206", 0, 10));
		commodity_list.add(new Commodity( "电灯泡",005, "阳光",0002,  "991w", 0,
				100, 100, 150, 200, "20141206", 0, 10));

		ArrayList<Integer> commodity_amount = new ArrayList<Integer>();
		commodity_amount.add(new Integer(20));
		commodity_amount.add(new Integer(10));

		double price = 4000.0;
		PromotionPo_Package promotionPo_Package1 = new PromotionPo_Package(
				"特价包", "20141214", "20150103", commodity_list,
				commodity_amount, price);
		promotionPO_Packages.add(promotionPo_Package1);

		double price_need = 1000;
		Commodity giftPo = new Commodity( "电灯泡",005,  "阳谷",0003, "992r", 0,
				100, 100, 0, 0, "20141206", 0, 10);
		int amountOfGift = 3;

		int gift_Money = 10;
		int amountOfGift_Money = 10;
		PromotionPo_Price promotionPo_Price1 = new PromotionPo_Price("heheh",
				"20141214", "20150103", price_need, giftPo, amountOfGift,
				gift_Money, amountOfGift_Money);
		promotionPO_Prices.add(promotionPo_Price1);

		output1();
		output2();
		output3();
	}

	public int getFinalID_CustemerProm() throws RemoteException {
		int result = 0;
		if (promotionPO_Customers.size() != 0) {
			result = promotionPO_Customers
					.get(promotionPO_Customers.size() - 1).getId();
		}
		return result;
	}

	public int getFinalID_PackageProm() throws RemoteException {
		int result = 0;
		if (promotionPO_Packages.size() != 0) {
			result = promotionPO_Packages.get(promotionPO_Packages.size() - 1)
					.getId();
		}
		return result;
	}

	public int getFinalID_PriceProm() throws RemoteException {
		int result = 0;
		if (promotionPO_Prices.size() != 0) {
			result = promotionPO_Prices.get(promotionPO_Prices.size() - 1)
					.getId();
		}
		return result;
	}

}
