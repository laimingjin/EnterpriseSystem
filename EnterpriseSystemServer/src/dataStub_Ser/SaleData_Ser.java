package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import businesslogic.Commodity;
import businesslogic.Customer;
import businesslogic.SaleLineItem;
import businesslogic.SaleList;
import businesslogic.SalesDetailLineItem;
import businesslogic.User;
import po.ReceiptPO;
import po.SaleDocumentPO;
import po.SalesListPO;
import dataservice.SaleDataService;
import enumClass.POSITION;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class SaleData_Ser extends UnicastRemoteObject implements
		SaleDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/sale.out";

	// 存放所有PO的类
	ArrayList<SaleDocumentPO> saleDocumentPOs;

	// //单例模式，Account只需要一个实例
	// private static final SaleData_Ser SALE_DATA_SER = new SaleData_Ser();
	// public static SaleData_Ser getInstance() {
	// return SALE_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public SaleData_Ser() throws RemoteException {
		saleDocumentPOs = (ArrayList<SaleDocumentPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (saleDocumentPOs == null) {
			saleDocumentPOs = new ArrayList<SaleDocumentPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(saleDocumentPOs, address);
	}

	// //////////////////////////////////////////////
	private int containsSameID(String ID) {
		if(saleDocumentPOs.size()!=0){
			for (int j = 0; j < saleDocumentPOs.size(); j++) {
			if (saleDocumentPOs.get(j).getDocumentID().equals(ID)) {
				return j;
			}
		}
		}
		
		
		return -1;
	}

	public SaleDocumentPO findByID(String documentID) throws RemoteException {
		int index = containsSameID(documentID);
		if (index == -1) {
			System.out.println("无该ID的销售单存在");
			return null;
		} else {
			return saleDocumentPOs.get(index);
		}
	}
//专为红冲和红冲复制写的find方法
	public SaleDocumentPO findByIDForWriteBack(String documentID) throws RemoteException {
	if(saleDocumentPOs.size()==0){
		return null;
	}
		ArrayList<SaleDocumentPO>pos=new ArrayList<SaleDocumentPO>();
		for (int j = 0; j < saleDocumentPOs.size(); j++) {
			if (saleDocumentPOs.get(j).getDocumentID().equals(documentID)) {
				pos.add(saleDocumentPOs.get(j));
			}
		}
		if(pos!=null){
			return pos.get(pos.size()-1);
		}
		return null;
	}
	
	
	public ArrayList<SaleDocumentPO> findByTimezone(String time)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(saleDocumentPOs.size()==0){
			return null;
		}
		if(time.equals(",")){
			return saleDocumentPOs;
		}else{
			String[] times = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			ArrayList<SaleDocumentPO> al = new ArrayList<SaleDocumentPO>();
			for (int i = 0; i < saleDocumentPOs.size(); i++) {
				long date = Long.parseLong(saleDocumentPOs.get(i).getTheDate());
				if (date >= time1 && date <= time2) {
					al.add(saleDocumentPOs.get(i));
				}
			}
			// show();
			// System.out.println();
			return al;
		}
	
	}

	public ArrayList<SaleDocumentPO> findByCommodity(String commodityName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(saleDocumentPOs.size()==0){
			return null;
		}
		if(commodityName.equals("")){
			return saleDocumentPOs;
		}
		ArrayList<SaleDocumentPO> al = new ArrayList<SaleDocumentPO>();
		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			int size = saleDocumentPOs.get(i).getTheList().getTheList().size();
			for (int j = 0; j < size; j++) {
				if (saleDocumentPOs.get(i).getTheList().getTheList().get(j)
						.getTheCommodity().getCommodityName()
						.equals(commodityName)) {
					al.add(saleDocumentPOs.get(i));
				}
			}
		}
		return al;
	}

	public ArrayList<SaleDocumentPO> findByExecutive(String executive)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(saleDocumentPOs.size()==0){
			return null;
		}
		if(executive.equals("")){
			return saleDocumentPOs;
		}
		ArrayList<SaleDocumentPO> al = new ArrayList<SaleDocumentPO>();
		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getExecutive().equals(executive)) {
				al.add(saleDocumentPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<SaleDocumentPO> findByCustomer(String customerName)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(saleDocumentPOs.size()==0){
			return null;
		}
		if(customerName.equals("")){
			return saleDocumentPOs;
		}
		ArrayList<SaleDocumentPO> al = new ArrayList<SaleDocumentPO>();
		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getTheCustomer().getCustomerName()
					.equals(customerName)) {
				al.add(saleDocumentPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<SaleDocumentPO> findByStorehouse(String storehouse)
			throws RemoteException {
		// TODO Auto-generated method stub
		if(saleDocumentPOs.size()==0){
			return null;
		}
		if(storehouse.equals("")){
			return saleDocumentPOs;
		}
		ArrayList<SaleDocumentPO> al = new ArrayList<SaleDocumentPO>();
		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			if (saleDocumentPOs.get(i).getWarehouse().equals(storehouse)) {
				al.add(saleDocumentPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<SaleDocumentPO> displayAll() throws RemoteException {
		// TODO Auto-generated method stub
	
		return saleDocumentPOs;
	}

	public ResultMessage add(SaleDocumentPO po) throws RemoteException {
		// TODO Auto-generated method stub
		saleDocumentPOs.add(po);
		output();
	//	show();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(SaleDocumentPO po) throws RemoteException {
		// TODO Auto-generated method stub
		String id = po.getDocumentID();
		int index = containsSameID(id);
		saleDocumentPOs.set(index, po);
		output();
		//show();
		return ResultMessage.SUCCESS;
	}

	// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号
	public String getNewID(String kind) throws RemoteException {
		// TODO 单据逻辑，如果单据库为空
		if (saleDocumentPOs.size()==0) {
		 		return kind + "-" + getDate() + "-00001";
		}
		String formerID = saleDocumentPOs.get(saleDocumentPOs.size() - 1)
				.getDocumentID();
		String sep[] = formerID.split("-");
		String date = getDate();
		if (!date.equals(sep[1])) {
			return kind + "-" + date + "-00001";
		} else {
			int time = Integer.parseInt(sep[2]);
			if (time == 99999) {
				System.out.println("error");
				return null;
			} else {
				time++;
			}
			String iss = String.format("%5d", time).replace(" ", "0");
			return kind + "-" + date + "-" + iss;
		}
	}

	private String getDate() {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");// 可以方便地修改日期格式
		return dateFormat.format(now);
	}

	private void show() {
		for (int i = 0; i < saleDocumentPOs.size(); i++) {
			String list = saleDocumentPOs.get(i).getTheDate() + " "
					+ saleDocumentPOs.get(i).getDocumentID() + " 客户"
					+ saleDocumentPOs.get(i).getExecutive() + " 操作员"
					+ saleDocumentPOs.get(i).getWarehouse() + "商品列表 "
					+ saleDocumentPOs.get(i).getTheMessage();
			System.out.println(list);
			String list1 = saleDocumentPOs.get(i).getTheCustomer()
					.getCustomerID()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer().getCustomerType()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer().getCustomerRank()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer().getCustomerName()
					+ "   "
					+ saleDocumentPOs.get(i).getTheCustomer().getTelePhone()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer()
							.getCustomerAddress()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer()
							.getCustomerPostCode()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer().geteMail()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer()
							.getReceivableLimit()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer()
							.getReceivableAmount()
					+ " "
					+ saleDocumentPOs.get(i).getTheCustomer()
							.getPayableAmount() + " ";
			System.out.println(list1);
			String list2 = saleDocumentPOs.get(i).getTheUser().getUserID()
					+ " " + saleDocumentPOs.get(i).getTheUser().getUserName()
					+ " " + saleDocumentPOs.get(i).getTheUser().getPassword()
					+ " " + saleDocumentPOs.get(i).getTheUser().getTheJob()
					+ " " + saleDocumentPOs.get(i).getTheUser().getPowerLevel();
			System.out.println(list2);

		}

	}

	// //////////////////////////////////////////////
	// 得到两个集合的并集
	public ArrayList<SaleDocumentPO> getUnionSet(ArrayList<SaleDocumentPO> a1,
			ArrayList<SaleDocumentPO> a2) throws RemoteException {
		ArrayList<SaleDocumentPO> union = new ArrayList<SaleDocumentPO>();
		if(a1==null && a2!=null){
			return a2;
		}else if(a1!=null &&a2==null){
			return a1;
		}else  if(a1==null && a2==null){
			return null;
		}
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

	// 销售明细表要调用销售单的东西，所以在这里写
	public ArrayList<SaleDocumentPO> findSalesList(String timezone,
			String commodityName, String customer, String executive,
			String storehouse) throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<SalesListPO> al = new ArrayList<SalesListPO>();
		SaleData_Ser saleData_Ser = new SaleData_Ser();
		
		ArrayList<SaleDocumentPO> sales1 =new ArrayList<SaleDocumentPO>();
		if(!timezone.equals("")){
			sales1= saleData_Ser
					.findByTimezone(timezone);
		}else{
			sales1=saleData_Ser.displayAll();
		}
		
		ArrayList<SaleDocumentPO> sales2 =new ArrayList<SaleDocumentPO>();
		if(!commodityName.equals("")){
			sales2=saleData_Ser
					.findByCommodity(commodityName);
		}else{
			sales2=saleData_Ser.displayAll();
		}
				
		ArrayList<SaleDocumentPO> sales3 = new ArrayList<SaleDocumentPO> ();
		if(!customer.equals("")){
			sales3=saleData_Ser
				.findByCustomer(customer);
		}else{
			sales3=saleData_Ser.displayAll();
		}
				
		ArrayList<SaleDocumentPO> sales4 =new ArrayList<SaleDocumentPO>();
		if(!executive.equals("")){
			sales4=saleData_Ser
				.findByExecutive(executive);
		}	else{
			sales4=saleData_Ser.displayAll();
		}
		
		ArrayList<SaleDocumentPO> sales5 = new ArrayList<SaleDocumentPO>();
		if(!storehouse.equals("")){
			sales5=saleData_Ser
				.findByStorehouse(storehouse);
		}else{
			sales5=saleData_Ser.displayAll();
		}		
		

		ArrayList<SaleDocumentPO> sales6 = new ArrayList<SaleDocumentPO>();
		ArrayList<SaleDocumentPO> sales7 = new ArrayList<SaleDocumentPO>();
		ArrayList<SaleDocumentPO> sales8 = new ArrayList<SaleDocumentPO>();
		ArrayList<SaleDocumentPO> sales9 = new ArrayList<SaleDocumentPO>();

		sales6 = getUnionSet(sales1, sales2);
		sales7 = getUnionSet(sales6, sales3);
		sales8 = getUnionSet(sales7, sales4);
		sales9 = getUnionSet(sales8, sales5);

		return sales9;
	}

	public ResultMessage finish() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultMessage delete(SaleDocumentPO po) throws RemoteException {
		String id = po.getDocumentID();
		int index = containsSameID(id);

		if (index == -1) {
			return ResultMessage.FAIL;
		}

		saleDocumentPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}
	public ResultMessage  getInit()throws RemoteException{
		
//		Customer theCustomer1=new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
//		User user1=new User("00001","xs1", "xs1", "进货销售人员", 1);
//		SaleList theList1=new SaleList();
//		theList1.addLineItem(new SaleLineItem(new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 0, 100,  100,  150, 180, "20140106", 0,10), 100, "careful"));
//		theList1.addLineItem(new SaleLineItem(new Commodity("电灯泡", 007,"飞尼日",  0011, "990e", 0, 100,  100,  150, 190, "20140106", 0,10), 100, "careful"));
//		saleDocumentPOs.add(new SaleDocumentPO("20140505","XSD-20140505-00220",theCustomer1,"林冲","No1",user1,theList1,"~~",theList1.getDocumentTotalPrice(),40,50,theList1.getDocumentTotalPrice()-40,StateOfDocument.DRAFT));
//		
//		Customer theCustomer2=new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
//		User user2=new User("00002","xs", "xs", "进货销售人员", 1);
//		SaleList theList2=new SaleList();
//		theList2.addLineItem(new SaleLineItem(new Commodity("电灯泡",005,  "阳谷",0003, "992r", 0, 100,  100,  200, 220, "20141207", 0,10),200,"good"));
//		saleDocumentPOs.add(new SaleDocumentPO("20141010","XSD-20141010-01234",theCustomer2,"萧蔷","No2",user2,theList2,"~~",theList2.getDocumentTotalPrice(),40,100,theList2.getDocumentTotalPrice()-40,StateOfDocument.DRAFT));
		
		Customer theCustomer1=new Customer(4, "销售商", 3, "张琪", "18933445453", "wu", 210046, "qq@com", 100000, 0, 30000,"xs");
		User user1=new User(3,"xs", "xs", "进货销售人员", 1);
		SaleList theList1=new SaleList();
		theList1.addLineItem(new SaleLineItem(new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 200, 100,  150,  80, 200, "20131206", 150,10), 50, "careful"));
		theList1.addLineItem(new SaleLineItem(new Commodity("遮光布", 006, "春花牌",0006, "33e", 200, 150,  200,  150, 200, "20141226", 150,10), 100, "careful"));
		saleDocumentPOs.add(new SaleDocumentPO("20140505","XSD-20140505-00001",theCustomer1,"xs","仓库1",user1,theList1,"~~",theList1.getDocumentTotalPrice(),40,50,theList1.getDocumentTotalPrice()-40,StateOfDocument.DRAFT));
		
		Customer theCustomer2=new Customer(5, "销售商", 3, "李四", "18933448995", "wu", 210023, "qq@com", 100000, 0, 3000,"xs");
		User user2=new User(3,"xs", "xs", "进货销售人员", 1);
		SaleList theList2=new SaleList();
		theList2.addLineItem(new SaleLineItem(new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 100, 100,  150,  80, 150, "20131206", 150,10),20,"good"));
		saleDocumentPOs.add(new SaleDocumentPO("20140910","XSD-20140910-00001",theCustomer2,"xs","仓库1",user2,theList2,"~~",theList2.getDocumentTotalPrice(),40,100,theList2.getDocumentTotalPrice()-40,StateOfDocument.EXAMINED));
		
		Customer theCustomer3=new Customer(5, "销售商", 3, "李四", "18933448995", "wu", 210023, "qq@com", 100000, 0, 0,"xs");
		User user3=new User(3,"xs", "xs", "进货销售人员", 1);
		SaleList theList3=new SaleList();
		theList3.addLineItem(new SaleLineItem(new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 100, 100,  150,  80, 150, "20131206", 150,10),20,"good"));
		saleDocumentPOs.add(new SaleDocumentPO("20141010","XSTHD-20141010-00001",theCustomer3,"xs","仓库1",user3,theList3,"~~",theList3.getDocumentTotalPrice(),40,100,theList2.getDocumentTotalPrice()-40,StateOfDocument.EXAMINED));
		
		output();
		return ResultMessage.SUCCESS;
	}
}
