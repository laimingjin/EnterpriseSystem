package client;

import presentation.CustomerUI;
import presentation.LogInUI;
import tool.MyFrame;

/**
 * 客户端 软件唯一入口
 * 
 * @author 小春 2014年11月26日
 */
public class Client {

	public static void main(String[] args) {
		
//		StaticImage.getInit();
//		try {
//			LocateRegistry.createRegistry(1099);
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		
//MyFrame.getFrame().changePanel(new CommodityWholeUI());
	//MyFrame.getFrame().changePanel(new ManagerWholeUI());
		
	//	MyFrame.getFrame().changePanel(new CommodityUI());
	MyFrame.getFrame().changePanel(new CustomerUI()); 
		// MyFrame.getFrame().changePanel(new CommodityUI());
///// MyFrame.getFrame().changePanel(new LogInUI());
	// MyFrame.getFrame().changePanel(new CommodityWholeUI());
//MyFrame.getFrame().changePanel(new ManagerWholeUI());
	//MyFrame.getFrame().changePanel(new CommodityUI());
//	 MyFrame.getFrame().changePanel(new LogInUI());
	//	MyFrame.getFrame().changePanel(new UserManageUI());
	// MyFrame.getFrame().changePanel(new ManagerWholeUI());
//MyFrame.getFrame().changePanel(new AccountWholeUI());
	//	MyFrame.getFrame().changePanel(new SaleUI());	
	//MyFrame.getFrame().changePanel(new LogInUI());
		// MyTipsFrame frame=new MyTipsFrame();
		// SuccessUI myUi=new SuccessUI(frame);
		// frame.getIni(myUi);
//AccountBLImp accountBLImp=new AccountBLImp();
//accountBLImp.displayAll();
	}

}

// 120,45