package RMI;



import java.awt.TextField;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import dataStub_Ser.AccountData_Ser;
import dataStub_Ser.CashListData_Ser;
import dataStub_Ser.CommodityData_Ser;
import dataStub_Ser.CommodityDocumentData_Ser;
import dataStub_Ser.CommodityGiftData_Ser;
import dataStub_Ser.CommoditySortData_Ser;
import dataStub_Ser.CustomerData_Ser;
import dataStub_Ser.ImportData_Ser;
import dataStub_Ser.LogData_Ser;
import dataStub_Ser.PaymentData_Ser;
import dataStub_Ser.PromotionData_Ser;
import dataStub_Ser.ReceiptData_Ser;
import dataStub_Ser.SaleData_Ser;
import dataStub_Ser.SalesListData_Ser;
import dataStub_Ser.SetAccountData_Ser;
import dataStub_Ser.SnapshotData_Ser;
import dataStub_Ser.UserData_Ser;
import dataservice.AccountDataService;
import dataservice.CashListDataService;
import dataservice.CommodityDataService;
import dataservice.CommodityDocumentDataService;
import dataservice.CommodityGiftDataService;
import dataservice.CommoditySortDataService;
import dataservice.CustomerDataservice;
import dataservice.ImportDataService;
import dataservice.LogDataService;
import dataservice.PaymentDataService;
import dataservice.PromotionDataService;
import dataservice.ReceiptDataService;
import dataservice.SaleDataService;
import dataservice.SalesListDataService;
import dataservice.SetAccountDataService;
import dataservice.SnapshotDataService;
import dataservice.UserDataService;

public class RmiServer {

	public static Remote reg=null;
	public static void main(String[] args) {
		bindObjects();
	
	}
public static void closeRemote(){
	try {
		java.rmi.server.UnicastRemoteObject.unexportObject(reg, true);
		System.out.println("releaseProt,closeSever");
	} catch (NoSuchObjectException e) {
		e.printStackTrace();
	}
}
	public static void bindObjects() {
		try {

			reg=LocateRegistry.createRegistry(1099);

			UserDataService userDataService =  new UserData_Ser();
			Naming.rebind("User", userDataService);

			AccountDataService accountDataService = new AccountData_Ser();
			Naming.rebind("Account", accountDataService);

			ReceiptDataService receiptDataService = new ReceiptData_Ser();
			Naming.rebind("Receipt", receiptDataService);

			PaymentDataService paymentDataService = new PaymentData_Ser();
			Naming.rebind("Payment", paymentDataService);

			CashListDataService cashListDataService = new CashListData_Ser();
			Naming.rebind("CashList", cashListDataService);

			CommodityDataService commodityDataService = new CommodityData_Ser();
			Naming.rebind("Commodity", commodityDataService);

			CommoditySortDataService commoditySortDataService = new CommoditySortData_Ser();
			Naming.rebind("CommoditySort", commoditySortDataService);

			CommodityGiftDataService commodityGiftDataService = new CommodityGiftData_Ser();
			Naming.rebind("CommodityGift", commodityGiftDataService);

			CommodityDocumentDataService commodityDocumentDataService = new CommodityDocumentData_Ser();
			Naming.rebind("CommodityDocument", commodityDocumentDataService);

			CustomerDataservice customerDataservice = new CustomerData_Ser();
			Naming.rebind("Customer", customerDataservice);

			ImportDataService importDataService = new ImportData_Ser();
			Naming.rebind("Import", importDataService);

			SaleDataService saleDataService = new SaleData_Ser();
			Naming.rebind("Sale", saleDataService);

			SetAccountDataService setAccountDataService = new SetAccountData_Ser();
			Naming.rebind("SetAccount", setAccountDataService);

			PromotionDataService promotionDataService = new PromotionData_Ser();
			Naming.rebind("Promotion", promotionDataService);

			LogDataService logDataService = new LogData_Ser();
			Naming.rebind("Log", logDataService);
			
			 SnapshotDataService snapshotDataService=new SnapshotData_Ser();
			 Naming.rebind("Snapshot", snapshotDataService);
			 
			 SalesListDataService salesListDataService=new SalesListData_Ser();
			 Naming.rebind("SalesList", salesListDataService);
			//初始化一次之后就不要再初始化了   不然数据都是重复的
			 
			// 每次用于测试初始化初始化 初始化！！！！！！！！！！！！！！！！！！！！！！！！
		
//			userDataService.getinit();
//			commodityDataService.init();
//		commoditySortDataService.init();
//			commodityGiftDataService.init();
//		commodityDocumentDataService.init();
//			logDataService.init();
//			accountDataService.init();
//			receiptDataService.init();
//			paymentDataService.init();
//			cashListDataService.init();
//			setAccountDataService.init();
//			snapshotDataService.init();
//			customerDataservice.getinit();
//			importDataService.getInit();
//            saleDataService.getInit();
//                promotionDataService.getInit();
//           
			System.out.println("ready");

			InetAddress addr;
			try {
				addr = InetAddress.getLocalHost();
				String	ip=addr.getHostAddress().toString();//获得本机IP
				String	address=addr.getHostName().toString();//获得本机名称
				MyTipsFrame myTipsFrame = new MyTipsFrame();
				MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
						myTipsFrame,Imageic.backOfIP.getImage());
				myTipsFrame.getIni(myFailTipsPanel);
			    TextField textIP = new TextField();
			    textIP.setBounds(60, 53, 95, 27);
			    textIP.setText(ip);
			    
			    myFailTipsPanel.add(textIP);
			    textIP.setVisible(true);
				
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	

     
            
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	public static void init() {
	}
}
