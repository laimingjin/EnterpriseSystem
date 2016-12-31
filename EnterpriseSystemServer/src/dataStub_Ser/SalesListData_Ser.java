package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import businesslogic.SaleLineItem;
import businesslogic.SalesDetailLineItem;
import po.SaleDocumentPO;
import po.SalesListPO;
import dataservice.SalesListDataService;
import enumClass.ResultMessage;

public class SalesListData_Ser  extends UnicastRemoteObject   implements SalesListDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/salesList.out";

	// 存放所有PO的类
	ArrayList<SalesListPO> salesListPOs;

//	// 单例模式，Account只需要一个实例
//	private static final SalesListData_Ser SALESLIST_DATA_SER = new SalesListData_Ser();
//
//	public static SalesListData_Ser getInstance() {
//		return SALESLIST_DATA_SER;
//	}

	/**
	 * 构造器：初始化Data
	 */
	public  SalesListData_Ser() throws RemoteException{
		salesListPOs = (ArrayList<SalesListPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (salesListPOs == null) {
			salesListPOs = new ArrayList<SalesListPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(salesListPOs, address);
	}

	// //////////////////////////////////////////////
	// 得到两个集合的并集
	public ArrayList<SaleDocumentPO> getUnionSet(ArrayList<SaleDocumentPO> a1,
			ArrayList<SaleDocumentPO> a2)  throws RemoteException{
		ArrayList<SaleDocumentPO> union = new ArrayList<SaleDocumentPO>();
		for (int i = 0; i < a1.size(); i++) {
			for (int j = 0; j < a2.size(); j++) {
				if (a1.get(i).getDocumentID().equals(a2.get(j).getDocumentID())) {
					union.add(a1.get(i));
					break;// ////////////////////这个确定是跳出j的循环
				}
			}
		}
		return union;

	}

//	public ArrayList<SalesListPO> find(String timezone, String commodityName,
//			String customer, String executive, String storehouse) throws RemoteException {
//		// TODO Auto-generated method stub
//		ArrayList<SalesListPO> al = new ArrayList<SalesListPO>();
//		SaleData_Ser saleData_Ser =new SaleData_Ser();
//		ArrayList<SaleDocumentPO> sales1 = saleData_Ser
//				.findByTimezone(timezone);
//		ArrayList<SaleDocumentPO> sales2 = saleData_Ser
//				.findByCommodity(commodityName);
//		ArrayList<SaleDocumentPO> sales3 = saleData_Ser
//				.findByCustomer(customer);
//		ArrayList<SaleDocumentPO> sales4 = saleData_Ser
//				.findByExecutive(executive);
//		ArrayList<SaleDocumentPO> sales5 = saleData_Ser
//				.findByStorehouse(storehouse);
//
//		ArrayList<SaleDocumentPO> sales6 = new ArrayList<SaleDocumentPO>();
//		ArrayList<SaleDocumentPO> sales7 = new ArrayList<SaleDocumentPO>();
//		ArrayList<SaleDocumentPO> sales8 = new ArrayList<SaleDocumentPO>();
//		ArrayList<SaleDocumentPO> sales9 = new ArrayList<SaleDocumentPO>();
//
//		sales6 = getUnionSet(sales1, sales2);
//		sales7 = getUnionSet(sales6, sales3);
//		sales8 = getUnionSet(sales7, sales4);
//		sales9 = getUnionSet(sales8, sales5);
//
//		for (int i = 0; i < sales9.size(); i++) {
//			ArrayList<SaleLineItem> items = sales9.get(i).getTheList()
//					.getTheList();
//			ArrayList<SalesDetailLineItem> theList = new ArrayList<SalesDetailLineItem>();
//			for (int j = 0; j < items.size(); j++) {
//				theList.add(new SalesDetailLineItem(items.get(j)
//						.getTheCommodity().getCommodityName(), items.get(j)
//						.getTheCommodity().getCommodityModel(), items.get(j)
//						.getTheCommodity().getInventoryQuantity(), items.get(j)
//						.getTheCommodity().getLatestRetailPrice(), items.get(j)
//						.getTotal()));
//			}
//			al.add(new SalesListPO(sales9.get(i).getTheDate(), theList));
//		}
//
//		return al;
//	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultMessage init() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
