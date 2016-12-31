package dataStub_Ser;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.CommodityPO;
import po.ImportDocumentPO;
import po.SaleDocumentPO;
import businesslogic.Commodity;
import businesslogic.Customer;
import businesslogic.ImportLineItem;
import businesslogic.ImportList;
import businesslogic.User;
import dataservice.ImportDataService;
import enumClass.ResultMessage;
import enumClass.StateOfDocument;

public class ImportData_Ser extends UnicastRemoteObject implements
		ImportDataService {

	// //////////////////////////////////////////////
	// 初始化读写器
	ReadAndWrite rw = new ReadAndWrite();

	// TODO 默认地址，应该写入配置
	String address = "file/import.out";

	// 存放所有PO的类
	ArrayList<ImportDocumentPO> importDocumentPOs;

	// //单例模式，Account只需要一个实例
	// private static final ImportData_Ser IMPORT_DATA_SER = new
	// ImportData_Ser();
	// public static ImportData_Ser getInstance() {
	// return IMPORT_DATA_SER;
	// }

	/**
	 * 构造器：初始化Data
	 */
	public ImportData_Ser() throws RemoteException {
		importDocumentPOs = (ArrayList<ImportDocumentPO>) rw.read(address);

		// 若为第一次运行则初始化accountPOs
		if (importDocumentPOs == null) {
			importDocumentPOs = new ArrayList<ImportDocumentPO>();
			output();
		}
	}

	/**
	 * 更新数据 2014年11月30日
	 */
	private void output() {
		rw.write(importDocumentPOs, address);
	}

	// //////////////////////////////////////////////
	private int containsSameID(String ID) {
		if (importDocumentPOs.size()!=0) {
			for (int j = 0; j < importDocumentPOs.size(); j++) {
			if (importDocumentPOs.get(j).getDocumentID().equals(ID)) {
				return j;
			}
		}
		}
		
		return -1;
	}

	public ImportDocumentPO findByID(String documentID) throws RemoteException {

		int index = containsSameID(documentID);
		if (index == -1) {
			System.out.println("无该ID的进货单存在");
			return null;
		} else {
			return importDocumentPOs.get(index);
		}

	}

	//专为红冲和红冲复制写的find方法
		public ImportDocumentPO findByIDForWriteBack(String documentID) throws RemoteException {
			if(importDocumentPOs.size()==0){
				return null;
			}
			ArrayList<ImportDocumentPO>pos=new ArrayList<ImportDocumentPO>();
			for (int j = 0; j < importDocumentPOs.size(); j++) {
				if (importDocumentPOs.get(j).getDocumentID().equals(documentID)) {
					pos.add(importDocumentPOs.get(j));
				}
			}
			if(pos!=null){
				return pos.get(pos.size()-1);
			}
			return null;
		}
	public ArrayList<ImportDocumentPO> findByTimezone(String time)
			throws RemoteException {
		if(importDocumentPOs.size()==0){
			return null;
		}
		if (time.equals(",")) {
			return importDocumentPOs;
		}
		else{
			String[] times = time.split(",");
			long time1 = Long.parseLong(times[0]);
			long time2 = Long.parseLong(times[1]);
			ArrayList<ImportDocumentPO> al = new ArrayList<ImportDocumentPO>();
			for (int i = 0; i < importDocumentPOs.size(); i++) {
				long date = Long.parseLong(importDocumentPOs.get(i).getTheDate());
				if (date >= time1 && date <= time2) {
					al.add(importDocumentPOs.get(i));
				}
			}
			return al;
		}
		
		
	}

	public ArrayList<ImportDocumentPO> findByCommodity(String commodityName)
			throws RemoteException {
		if(importDocumentPOs.size()==0){
			return null;
		}
		if (commodityName.equals("")) {
			return importDocumentPOs;
		}
		ArrayList<ImportDocumentPO> al = new ArrayList<ImportDocumentPO>();
		for (int i = 0; i < importDocumentPOs.size(); i++) {
			int size = importDocumentPOs.get(i).getTheList()
					.getImportLineList().size();
			for (int j = 0; j < size; j++) {
				if (importDocumentPOs.get(i).getTheList().getImportLineList()
						.get(j).getTheCommodity().getCommodityName()
						.equals(commodityName)) {
					al.add(importDocumentPOs.get(i));
				}
			}
		}
		return al;
	}

	public ArrayList<ImportDocumentPO> findByCustomer(String customerName)
			throws RemoteException {
		if(importDocumentPOs.size()==0){
			return null;
		}
		if (customerName.equals("")) {
			return importDocumentPOs;
		}
		ArrayList<ImportDocumentPO> al = new ArrayList<ImportDocumentPO>();
		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getTheCustomer().getCustomerName()
					.equals(customerName)) {
				al.add(importDocumentPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<ImportDocumentPO> findByStorehouse(String storehouse)
			throws RemoteException {
		if(importDocumentPOs.size()==0){
			return null;
		}
		if (storehouse.equals("")) {
			return importDocumentPOs;
		}
		ArrayList<ImportDocumentPO> al = new ArrayList<ImportDocumentPO>();
		for (int i = 0; i < importDocumentPOs.size(); i++) {
			if (importDocumentPOs.get(i).getWarehouse().equals(storehouse)) {
				al.add(importDocumentPOs.get(i));
			}
		}
		return al;
	}

	public ArrayList<ImportDocumentPO> dispAll() throws RemoteException {
		return importDocumentPOs;
	}

	public ResultMessage add(ImportDocumentPO po) throws RemoteException {
		importDocumentPOs.add(po);
		output();
		return ResultMessage.SUCCESS;
	}

	public ResultMessage update(ImportDocumentPO po) throws RemoteException {
		String id = po.getDocumentID();
		int index = containsSameID(id);
		if(index==-1){
			return ResultMessage.FAIL;
		}
		importDocumentPOs.set(index, po);
		output();
		return ResultMessage.SUCCESS;
	}

	// 得到新的单据号，如果跟最后一个单据日期在同一天就接下去编号，否则就从00001开始编号
	public String getNewID(String kind) throws RemoteException {
//		%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		// TODO 单据逻辑，如果单据库为空
		if (importDocumentPOs.size()==0) {
		 		return kind + "-" + getDate() + "-00001";
		}
//		%%%%%%%%%%%%%%%%%%%%%%%%%%%%
		ArrayList<ImportDocumentPO>pos=new ArrayList<ImportDocumentPO>();
		for(int i=0;i<importDocumentPOs.size();i++){
			if(importDocumentPOs.get(i).getDocumentID().contains(kind)){
				pos.add(importDocumentPOs.get(i));
			}
		}
		if(pos.size()==0){
			return kind + "-" + getDate()  + "-00001";
		}
		String formerID = pos.get(pos.size()-1)
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

	// public void show(){
	// for(int i=0;i<importDocumentPOs.size();i++){
	// String
	// list=importDocumentPOs.get(i).getTheDate()+" "+saleDocumentPOs.get(i).getDocumentID()+" 客户"+saleDocumentPOs.get(i).getExecutive()+" 操作员"+saleDocumentPOs.get(i).getWarehouse()+"商品列表 "+saleDocumentPOs.get(i).getTheMessage();
	// System.out.println(list);
	// String
	// list1=importDocumentPOs.get(i).getTheCustomer().getCustomerID()+" "+saleDocumentPOs.get(i).getTheCustomer().getCustomerType()+" "+saleDocumentPOs.get(i).getTheCustomer().getCustomerRank()+" "+saleDocumentPOs.get(i).getTheCustomer().getCustomerName()+"   "+saleDocumentPOs.get(i).getTheCustomer().getTelePhone()+" "+saleDocumentPOs.get(i).getTheCustomer().getCustomerAddress()+" "+saleDocumentPOs.get(i).getTheCustomer().getCustomerPostCode()+" "+saleDocumentPOs.get(i).getTheCustomer().geteMail()+" "+saleDocumentPOs.get(i).getTheCustomer().getReceivableLimit()+" "+saleDocumentPOs.get(i).getTheCustomer().getReceivableAmount()+" "+saleDocumentPOs.get(i).getTheCustomer().getPayableAmount()+" ";
	// System.out.println(list1);
	// String
	// list2=importDocumentPOs.get(i).getTheUser().getUserID()+" "+saleDocumentPOs.get(i).getTheUser().getUserName()+" "+saleDocumentPOs.get(i).getTheUser().getPassword()+" "+saleDocumentPOs.get(i).getTheUser().getTheJob()+" "+saleDocumentPOs.get(i).getTheUser().getPowerLevel();
	// System.out.println(list2);
	//
	// }
	//
	// }

	public ResultMessage finish() {
		return null;
	}

	public ResultMessage delete(ImportDocumentPO po) throws RemoteException {
		String id = po.getDocumentID();
		int index = containsSameID(id);
		if (index == -1) {
			return ResultMessage.FAIL;
		}
		importDocumentPOs.remove(index);
		output();
		return ResultMessage.SUCCESS;
	}

public void getInit(){
	  importDocumentPOs=new ArrayList<ImportDocumentPO>();
//	String date="20141214";
//	String id="JHD-20141214-00002";
//	Customer customer=new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
//	String warehouse="仓库1";
//	User user=new User("00002","xs", "xs", "进货销售人员", 1);
//        Commodity commodity=new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 0, 100,  100,  150, 0, "20141206", 0,10);
//		ImportLineItem importLineItem=new ImportLineItem(commodity, 200, "good");
//        ImportList importList=new ImportList();
//        importList.addImportLineItem(importLineItem);
//        String message="wu";
//        double price=importList.getTotal();
//        ImportDocumentPO importDocumentPO1=new ImportDocumentPO(date, id, customer, warehouse, user, importList, message, price, StateOfDocument.DRAFT);
//        
//        String date1="20141215";
//    	String id1="JHD-20141215-00006";
//    	Customer customer1=new Customer(3, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 0, 233,"amy");
//    	String warehouse1="仓库2";
//    	User user2=new User("00002","xs", "xs", "进货销售人员", 1);
//            Commodity commodity1=new Commodity( "电灯泡",005,  "阳谷",0003, "992r", 0, 100,  100,  200, 0, "20141206", 0,10);
//    		ImportLineItem importLineItem1=new ImportLineItem(commodity1, 100, "good");
//	 ImportList importList1=new ImportList();
//        importList.addImportLineItem(importLineItem1);
//        String messgee1="hao";
//        double price1=importList.getTotal();
//        ImportDocumentPO importDocumentPO2=new ImportDocumentPO(date1, id1, customer1, warehouse1, user2, importList1, messgee1, price1, StateOfDocument.DONE);
//     
	
	//下面的这些是我测试时用的 可以注释掉然后用上面那些原来的初始化数据，其他单据也相同
	String date="20131214";
	String id="JHD-20131214-00001";
	Customer customer=new Customer(1, "供应商", 2, "王二", "18933445555", "wu", 210023, "qq@com", 100000, 16000, 0,"xs");
	String warehouse="仓库1";
	User user=new User(3,"xs", "xs", "进货销售人员", 1);
        Commodity commodity=new Commodity("电灯泡", 005,"飞利浦",  0001, "990e", 200, 100,  150,  80, 0, "20131206", 80,10);
        ImportLineItem importLineItem=new ImportLineItem(commodity, 200, "good");
        ImportList importList=new ImportList();
        importList.addImportLineItem(importLineItem);
        String message="wu";
        double price=importList.getTotal();
        ImportDocumentPO importDocumentPO1=new ImportDocumentPO(date, id, customer, warehouse, user, importList, message, price, StateOfDocument.DONE);
        
        String date1="20140215";
    	String id1="JHD-20140215-00001";
    	Customer customer1=new Customer(2, "供应商", 1, "张三", "18933445544", "wu", 210023, "qq@com", 100000, 9000, 0,"xs");
    	String warehouse1="仓库2";
    	User user2=new User(3,"xs", "xs", "进货销售人员", 1);
            Commodity commodity1=new Commodity("电灯泡",  005, "阳光", 0002,"991w", 100, 100,  120,  90, 0, "20141206", 90,10);
    		ImportLineItem importLineItem1=new ImportLineItem(commodity1, 100, "good");
    		 ImportList importList1=new ImportList();
        importList1.addImportLineItem(importLineItem1);
        String messgee1="hao";
        double price1=importList.getTotal();
        ImportDocumentPO importDocumentPO2=new ImportDocumentPO(date1, id1, customer1, warehouse1, user2, importList, messgee1, price1, StateOfDocument.SENDED);
        
        String date3="20140215";
    	String id3="JHD-20140215-00002";
    	Customer customer3=new Customer(3, "供应商", 2, "林琳", "18944445555", "wu", 210046, "qq@com", 100000, 15000, 0,"xs");
    	String warehouse3="仓库2";
    	User user3=new User(3,"xs", "xs", "进货销售人员", 1);
            Commodity commodity3=new Commodity( "遮光布", 006, "春花牌",0006, "33e", 200, 150,  200,  150, 0, "20141226", 150,10);
    		ImportLineItem importLineItem3=new ImportLineItem(commodity3, 200, "good");
    		 ImportList importList3=new ImportList();
        importList3.addImportLineItem(importLineItem3);
        String messgee3="hao";
        double price3=importList.getTotal();
        ImportDocumentPO importDocumentPO3=new ImportDocumentPO(date3, id3, customer3, warehouse3, user3, importList3, messgee3, price3, StateOfDocument.DONE);
        
        String date4="20140415";
    	String id4="JHTHD-20140415-00001";
    	Customer customer4=new Customer(2, "供应商", 1, "张三", "18933445544", "wu", 210023, "qq@com", 100000, 0, 0,"xs");
    	String warehouse4="仓库2";
    	User user4=new User(3,"xs", "xs", "进货销售人员", 1);
            Commodity commodity4=new Commodity("电灯泡",  005, "阳光", 0002,"991w", 100, 100,  120,  90, 0, "20141206", 90,10);
    		ImportLineItem importLineItem4=new ImportLineItem(commodity4, 100, "good");
    		 ImportList importList4=new ImportList();
        importList4.addImportLineItem(importLineItem4);
        String messgee4="hao";
        double price4=importList.getTotal();
        ImportDocumentPO importDocumentPO4=new ImportDocumentPO(date4, id4, customer4, warehouse4, user4, importList4, messgee4, price4, StateOfDocument.DONE);

        
        importDocumentPOs.add(importDocumentPO1);
        importDocumentPOs.add(importDocumentPO2);
        importDocumentPOs.add(importDocumentPO3);
        importDocumentPOs.add(importDocumentPO4);
        
        output();

}
}