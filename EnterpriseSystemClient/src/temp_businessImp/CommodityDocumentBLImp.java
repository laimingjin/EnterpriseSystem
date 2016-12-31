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

import po.CommodityDocumentPO;
import temp_business.CommodityDocumentBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CommodityDocumentVO;
import vo.LogVO;
import vo.UserVO;
import businesslogic.Commodity;
import dataservice.CommodityDocumentDataService;
import enumClass.PROBLEM;
import enumClass.ResultMessage;

public class CommodityDocumentBLImp implements CommodityDocumentBLService {
	CommodityDocumentDataService commodityDocumentDataService;  
	LoginBLService loginBLService=new LoginBLImp();
	LogBLService logBLService=new LogBLImp();
	public CommodityDocumentBLImp () {
		try {
			commodityDocumentDataService = (CommodityDocumentDataService ) Naming.lookup(StaticInfo.URL_COMMODITYDOCUMENT);
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
	public ArrayList<CommodityDocumentVO> diaplay() {
		// TODO Auto-generated method stub
		ArrayList<CommodityDocumentVO> commodityDocumentVOs =new ArrayList<CommodityDocumentVO> ();
		ArrayList<CommodityDocumentPO> commodityDocumentPOs=new ArrayList<CommodityDocumentPO>();  
		try {
			commodityDocumentPOs=commodityDocumentDataService.displayAll();
		for(int i=0;i<commodityDocumentPOs.size();i++){
			String dataString=commodityDocumentPOs.get(i).getDate();
			
			int ID=commodityDocumentPOs.get(i).getDocumentID();
			Commodity commodity=commodityDocumentPOs.get(i).getCommodity();
			PROBLEM p=commodityDocumentPOs.get(i).getProblem();
			int realQuantity=commodityDocumentPOs.get(i).getRealQuantity();
			boolean issend=commodityDocumentPOs.get(i).isSend();
			boolean ispass=commodityDocumentPOs.get(i).isPass();
		
			boolean isdealed=commodityDocumentPOs.get(i).isDealed();
			
			CommodityDocumentVO commodityDocumentVO=new CommodityDocumentVO(dataString, ID, commodity, p, realQuantity, ispass,issend,isdealed );
			commodityDocumentVOs.add(commodityDocumentVO);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return commodityDocumentVOs;
	}

	public ResultMessage addCommodityDocument(
			CommodityDocumentVO commodityDocumentVO) {
		// TODO Auto-generated method stub
		ResultMessage result=ResultMessage.FAIL;
		CommodityDocumentPO po=this.changeVOToPO(commodityDocumentVO);
		try {
			result=commodityDocumentDataService.add(po);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals(ResultMessage.SUCCESS)){
        	  Addlog("新增问题单据成功");
          }else{
        	  Addlog("新增问题单据失败");
          }
		return result;
	}

	public ResultMessage updateCommodityDocument(
			CommodityDocumentVO commodityDocumentVO) {
		// TODO Auto-generated method stub
		ResultMessage result=ResultMessage.FAIL;
		CommodityDocumentPO po=this.changeVOToPO(commodityDocumentVO);
		try {
			result=commodityDocumentDataService.update(po);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(result.equals(ResultMessage.SUCCESS)){
        	  Addlog("更新问题单据成功");
          }else{
        	  Addlog("更新问题单据失败");
          }
		return result;
	}

	
	public  CommodityDocumentPO changeVOToPO(CommodityDocumentVO commodityDocumentVO){
				
         String dataString=commodityDocumentVO.getDate();
		
		int ID=commodityDocumentVO.getDocumentID();
		Commodity commodity=commodityDocumentVO.getCommodity();
		PROBLEM p=commodityDocumentVO.getProblem();
		int realQuantity=commodityDocumentVO.getRealQuantity();
		boolean issend=commodityDocumentVO.isSend();
		boolean ispass=commodityDocumentVO.isPass();
	
		boolean isdealed=commodityDocumentVO.isDealed();
		CommodityDocumentPO po=new CommodityDocumentPO(dataString, ID, commodity, p, realQuantity, ispass, issend, isdealed);
		
		return po;
	}

	//
	private CommodityDocumentVO POTrangeToVO(CommodityDocumentPO p){
		return new CommodityDocumentVO(p.getDate(),p.getDocumentID(),p.getCommodity(),p.getProblem(),p.getRealQuantity(),p.isPass(),p.isSend(),p.isDealed());
	}
	public ArrayList<CommodityDocumentVO>findForBusinessList(String timezone)throws RemoteException{
		ArrayList<CommodityDocumentVO> vos=new ArrayList<CommodityDocumentVO>();
		ArrayList<CommodityDocumentPO> pos=commodityDocumentDataService.findByTime(timezone);
		if(pos!=null){
			for(int i=0;i<pos.size();i++){
				vos.add(POTrangeToVO(pos.get(i)));
			}
			
		}
		return vos;
	}

	public int getNewCommodityDocumentID() {
		// TODO Auto-generated method stub
		
		int finalID=0;
		try {
             finalID=commodityDocumentDataService.getFinalID();
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return (finalID+1);
	}
	public CommodityDocumentVO getCommodityDocumentByID(int ID) {
		// TODO Auto-generated method stub
		CommodityDocumentVO commodityDocumentVO=null;
		try {
			CommodityDocumentPO commodityDocumentPO=commodityDocumentDataService.find(ID);
			commodityDocumentVO=this.POTrangeToVO(commodityDocumentPO);
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return 	commodityDocumentVO;
	}

	public ArrayList<CommodityDocumentVO>findCommodityDocument(String time)  throws RemoteException{
		ArrayList<CommodityDocumentVO>  vos=new 	ArrayList<CommodityDocumentVO>();
		ArrayList<CommodityDocumentPO>pos=new 	ArrayList<CommodityDocumentPO>();
		pos=commodityDocumentDataService.findByTime(time);
		if(pos!=null){
			for(int i=0;i<pos.size();i++){
				vos.add(POTrangeToVO(pos.get(i)));
			}
		}
		return vos;
	}
	@SuppressWarnings("deprecation")
	public void output_CommodityDocument(String fileName,CommodityDocumentVO vo){
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
	       HSSFSheet sheet = workbook.createSheet("库存类单据");
	       
	       HSSFRow row0 = sheet.createRow((short)0);
	       
	     //在索引0的位置创建单元格（左上端）
	       
	       HSSFCell cell0 = row0.createCell((short) 0);
	       HSSFCell cell1 = row0.createCell((short) 1);
	       HSSFCell cell2 = row0.createCell((short) 2);
	       HSSFCell cell3 = row0.createCell((short) 3);
	       HSSFCell cell4 = row0.createCell((short) 4);
	       HSSFCell cell5 = row0.createCell((short) 5);
	       HSSFCell cell6 = row0.createCell((short) 6);
	       HSSFCell cell7 = row0.createCell((short) 7);
	       HSSFCell cell8 = row0.createCell((short) 8);
	       HSSFCell cell9 = row0.createCell((short) 9);
	       // 定义单元格为字符串类型
	        cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell7.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell8.setCellType(HSSFCell.CELL_TYPE_STRING);
	       cell9.setCellType(HSSFCell.CELL_TYPE_STRING);
	       // 在单元格中输入一些内容
	        //cell0.setCellValue(111);
	       cell0.setCellValue("单据类型");
	       cell1.setCellValue("单据状态");
	       cell2.setCellValue("日期");
	       cell3.setCellValue("单据编号");
	       cell4.setCellValue("商品ID");
	       cell5.setCellValue("商品名称");
	       cell6.setCellValue("商品型号");
	       cell7.setCellValue("库存数量");
	       cell8.setCellValue("库存警戒值");
	       cell9.setCellValue("实际库存");
	     
	       HSSFRow row1 = sheet.createRow((short)1);
	       HSSFCell cell10 = row1.createCell((short) 0);
        HSSFCell cell11 = row1.createCell((short) 1);
        HSSFCell cell12 = row1.createCell((short) 2);
        HSSFCell cell13 = row1.createCell((short) 3);
        HSSFCell cell14 = row1.createCell((short) 4);
        HSSFCell cell15 = row1.createCell((short) 5);
        HSSFCell cell16 = row1.createCell((short) 6);
        HSSFCell cell17 = row1.createCell((short) 7);
        HSSFCell cell18 = row1.createCell((short) 8);
        HSSFCell cell19 = row1.createCell((short) 9);
        
        cell10.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell11.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell13.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell15.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell17.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell18.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        cell19.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        
        if(vo.getProblem()==PROBLEM.LOSS){
        	   cell10.setCellValue("报损单");
        }else if(vo.getProblem()==PROBLEM.OVERFLOW){
        	  cell10.setCellValue("报溢单");
        }else {
        	   cell10.setCellValue("报警单");
        }
     if(vo.isSend()==false){
    	   cell11.setCellValue("草稿状态");
     }else if(vo.isPass()==false){
  	   cell11.setCellValue("已发送，未审批状态");
     }else if(vo.isDealed()==false){
  	   cell11.setCellValue("已审批，未处理状态");
     }else {
  	   cell11.setCellValue("已处理");
     }
     
        cell12.setCellValue(vo.getDate());
        cell13.setCellValue(vo.getDocumentID());
        cell14.setCellValue(vo.getCommodity().getCommodityID());
        cell15.setCellValue(vo.getCommodity().getCommodityName());
        cell16.setCellValue(vo.getCommodity().getCommodityModel());
        cell17.setCellValue(vo.getCommodity().getInventoryQuantity());
        cell18.setCellValue(vo.getCommodity().getWarnQuantity());
        cell19.setCellValue(vo.getRealQuantity());
        
	       
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
	public ResultMessage deleteCommodityDocument(int ID) {
		CommodityDocumentPO commodityDocumentPO = null;
		ResultMessage resultMessage=ResultMessage.FAIL;
		
		try {
			commodityDocumentPO = commodityDocumentDataService.find(ID);
		
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	try {
		if(commodityDocumentPO.isSend()==true){
			resultMessage=ResultMessage.CannotDeleteDocument;
		}else{
			resultMessage=commodityDocumentDataService.delete(commodityDocumentPO);
			
		}
		
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(resultMessage.equals(ResultMessage.SUCCESS)){
  	  Addlog("删除问题单据成功");
    }else{
  	  Addlog("删除问题单据失败");
    }
		return resultMessage;
	}

	public CommodityDocumentVO findByIDForWriteBack(int id)
			throws RemoteException {
		// TODO Auto-generated method stub
		CommodityDocumentPO po=commodityDocumentDataService.findByIDForWriteBack(id);
		return POTrangeToVO(po);
	}
	
}
