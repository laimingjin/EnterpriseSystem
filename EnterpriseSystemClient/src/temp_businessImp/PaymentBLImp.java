package temp_businessImp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import po.PaymentPO;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import temp_business.PaymentBLService;
import vo.LogVO;
import vo.PaymentVO;
import vo.UserVO;
import businesslogic.TransferLineItem;
import dataservice.PaymentDataService;
import enumClass.ResultMessage;

public class PaymentBLImp implements PaymentBLService{

	private  PaymentDataService paymentDataService;   
	LoginBLService loginBLService=new LoginBLImp();
	LogBLService logBLService=new LogBLImp();
	public PaymentBLImp() {
		try {
			paymentDataService = (PaymentDataService) Naming.lookup(StaticInfo.URL_PAYMENT);
			
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
	private PaymentVO POTrangeToVO(PaymentPO p){
		if(p!=null){
			return new PaymentVO(p.getDate(),p.getDocumentID(),p.getCustomerID(),p.getCustomerName(),p.getUserID(),p.getUserName(),p.getTransferList(),p.getTotalPrice(),p.isPass(),p.isSend(),p.isDealed());
			
		}
		return null;
	}
	//VO转PO
	private PaymentPO VOTrangeToPO(PaymentVO v){
		if(v!=null){
			return new PaymentPO(v.getDate(),v.getDocumentID(),v.getCustomerID(),v.getCustomerName(),v.getUserID(),v.getUserName(),v.getTransferList(),v.getTotalPrice(),v.isPass(),v.isSend(),v.isDealed());
		}
		return null;
	}
	
	public PaymentVO findByID(String id) throws RemoteException{
		PaymentPO po=paymentDataService.findByDocumentID(id);
		return POTrangeToVO(po);
	}
	public ResultMessage addPayment(PaymentVO vo) {
		ResultMessage resultMessage=ResultMessage.FAIL;
		try {
			resultMessage=paymentDataService.add(VOTrangeToPO(vo));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		 if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("新增付款单成功");
	         }else{
	       	  Addlog("新增付款单失败");
	         }
		return resultMessage;
	}

	public ArrayList<PaymentVO> displayAll() {
		// TODO Auto-generated method stub
		 ArrayList<PaymentVO> vos=new ArrayList<PaymentVO>();
		 try {
			 ArrayList<PaymentPO> pos=paymentDataService.displayAll();
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

	public ResultMessage updatePayment(String documentID, PaymentVO vo) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage=ResultMessage.FAIL;
		PaymentPO p=VOTrangeToPO(vo);
		try {
			PaymentPO po=paymentDataService.findByDocumentID(documentID);
			if(po!=null){
				resultMessage=paymentDataService.update(p);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(resultMessage.equals(ResultMessage.SUCCESS)){
	       	  Addlog("更新付款单成功");
	         }else{
	       	  Addlog("更新付款单失败");
	         }
		return resultMessage;
	}

	public void endManagement() {
		// TODO Auto-generated method stub
		
	}

	// 得到两个集合的并集
		public ArrayList<PaymentPO> getPaymentUnionSet(ArrayList<PaymentPO> a1,
				ArrayList<PaymentPO> a2)  throws RemoteException{
			ArrayList<PaymentPO> union = new ArrayList<PaymentPO>();
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

	public ArrayList<PaymentVO> findPayment(String timezone, String customer)  throws RemoteException{
		ArrayList<PaymentVO> vos=new ArrayList<PaymentVO>();
		ArrayList<PaymentPO> payment1 = paymentDataService.findByTime(timezone);
		ArrayList<PaymentPO> payment2 = paymentDataService
				.findByCustomer(customer);

		ArrayList<PaymentPO> payment3 = new ArrayList<PaymentPO>();

		payment3 = getPaymentUnionSet(payment1, payment2);
		if(payment3!=null){
			for(int i=0;i<payment3.size();i++){
				vos.add(POTrangeToVO(payment3.get(i)));
			}
		}
		return vos;
	}
	
	@SuppressWarnings("deprecation")
	public void output_Payment(String fileName,PaymentVO vo)throws RemoteException{
	    ArrayList<TransferLineItem>lineItems=vo.getTransferList().getTheList();
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
	       HSSFSheet sheet = workbook.createSheet("付款单");
	       
	       HSSFRow row0 = sheet.createRow((short)0);
	       
	     //在索引0的位置创建单元格（左上端）
	       
	       HSSFCell cell0 = row0.createCell((short) 0);
	       HSSFCell cell1 = row0.createCell((short) 1);
	       HSSFCell cell2 = row0.createCell((short) 2);
	       HSSFCell cell3 = row0.createCell((short) 3);
	       HSSFCell cell4 = row0.createCell((short) 4);
	       HSSFCell cell5 = row0.createCell((short) 5);
	       HSSFCell cell06 = row0.createCell((short) 6);
	       HSSFCell cell9 = row0.createCell((short) 9);
	       HSSFCell cell010 = row0.createCell((short) 10);
	       HSSFCell cell011 = row0.createCell((short) 11);
	       HSSFCell cell012 = row0.createCell((short) 12);
	       // 定义单元格为字符串类型
	        cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell06.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell010.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell011.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell012.setCellType(HSSFCell.CELL_TYPE_STRING);
	       // 在单元格中输入一些内容
	        //cell0.setCellValue(111);
	       cell0.setCellValue("时间");
	       cell1.setCellValue("单据编号");
	       cell2.setCellValue("客户ID");
	       cell3.setCellValue("客户");
	       cell4.setCellValue("操作员ID");
	       cell5.setCellValue("操作员");
	       cell06.setCellValue("转账列表");
	       cell9.setCellValue("总额汇总");
	       cell010.setCellValue("是否发送");
	       cell011.setCellValue("是否通过");
	       cell012.setCellValue("是否处理");
	       int size=lineItems.size();
	       HSSFRow row1 = sheet.createRow((short)1);
	       HSSFCell cell10 = row1.createCell((short) 0);
           HSSFCell cell11 = row1.createCell((short) 1);
           HSSFCell cell12 = row1.createCell((short) 2);
           HSSFCell cell13 = row1.createCell((short) 3);
           HSSFCell cell14 = row1.createCell((short) 4);
           HSSFCell cell15 = row1.createCell((short) 5);
           HSSFCell cell6 = row1.createCell((short) 6);
	       HSSFCell cell7 = row1.createCell((short) 7);
	       HSSFCell cell8 = row1.createCell((short) 8);
           HSSFCell cell19 = row1.createCell((short) 9);
           HSSFCell cell110 = row1.createCell((short) 10);
           HSSFCell cell111 = row1.createCell((short) 11);
           HSSFCell cell112 = row1.createCell((short) 12);
           cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell12.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
           cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
           cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell19.setCellType(HSSFCell.CELL_TYPE_STRING);
           cell110.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
           cell111.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
           cell112.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
           
           cell10.setCellValue(vo.getDate());
           cell11.setCellValue(vo.getDocumentID());
           cell12.setCellValue(vo.getCustomerID());
           cell13.setCellValue(vo.getCustomerName());
           cell14.setCellValue(vo.getUserID());
           cell15.setCellValue(vo.getUserName());
           cell6.setCellValue("银行账户");
	       cell7.setCellValue("转账金额");
	       cell8.setCellValue("备注");
           cell19.setCellValue(vo.getTotalPrice());
           cell110.setCellValue(vo.isSend());
           cell111.setCellValue(vo.isPass());
           cell112.setCellValue(vo.isDealed());
           
	       for(int i=0;i<size;i++){
	    	   HSSFRow row = sheet.createRow((short)i+2);
	    	
	           HSSFCell cell16 = row.createCell((short) 6);
	           HSSFCell cell17 = row.createCell((short) 7);
	           HSSFCell cell18 = row.createCell((short) 8);
	           // 定义单元格为字符串类型
	          
	           cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
	           cell17.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	           cell18.setCellType(HSSFCell.CELL_TYPE_STRING);
	           // 在单元格中输入一些内容
	            //cell0.setCellValue(111);
	           cell16.setCellValue(lineItems.get(i).getAccountName());
	           cell17.setCellValue(lineItems.get(i).getTransferPrice());
	           cell18.setCellValue(lineItems.get(i).getRemark());
	       }
	       
	       FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(fileName);
			   workbook.write(fOut);
			     
			    fOut.flush();
			     
			    // 操作结束，关闭文件
			     
			    fOut.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	       
	    // 把相应的Excel 工作簿存盘
	     
	 
	     
	    System.out.println("文件生成...");
	}
	public void getinit(){
		try {
			paymentDataService.init();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getNewID() {
		try {
			return paymentDataService.getNewID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ResultMessage deletePayment(String ID) {

	  PaymentPO paymentPO = null;
		ResultMessage resultMessage=ResultMessage.FAIL;
		
		try {
			paymentPO = paymentDataService.findByDocumentID(ID);
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	try {
		if(paymentPO.isSend()==true){
		
			resultMessage=ResultMessage.CannotDeleteDocument;
		}else{
		
			resultMessage=paymentDataService.delete(paymentPO);
			
		}

	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(resultMessage.equals(ResultMessage.SUCCESS)){
     	  Addlog("删除付款单成功");
       }else{
     	  Addlog("删除付款单失败");
       }
		return resultMessage;
	}
	public PaymentVO findByIDForWriteBack(String id) throws RemoteException {
		// TODO Auto-generated method stub
		PaymentPO po=paymentDataService.findByIDForWriteBack(id);
		return POTrangeToVO(po);
	}

}
