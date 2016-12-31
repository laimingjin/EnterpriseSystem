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

import po.CommoditySnapshotPO;
import temp_business.CommodityBLService;
import temp_business.CommoditySnapShotBLService;
import vo.CommoditySnapshotVO;
import vo.CommodityVO;
import businesslogic.Commodity;
import dataservice.SnapshotDataService;

public class CommoditySnapshotBLImp implements  CommoditySnapShotBLService{
	SnapshotDataService snapshotDataService;  
	public  CommoditySnapshotBLImp () {
		try {
			snapshotDataService = (SnapshotDataService ) Naming.lookup(StaticInfo.URL_SNAPSHOT);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	public  CommoditySnapshotVO  LookCommoditySnapShot(
		) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//可以方便地修改日期格式
		String dataNow = dateFormat.format( now ); 
         ArrayList<Commodity >commodities=new ArrayList<Commodity>();
   CommodityBLService commodityBLService=new CommodityBLImp();
   ArrayList<CommodityVO> coms = commodityBLService.displayAll();
		for (int i = 0; i < coms.size(); i++) {
			commodities.add(new Commodity(coms.get(i)));
		}
		int lotnumber = 0;
		try {
			lotnumber = snapshotDataService.getFinalID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 CommoditySnapshotVO commoditySnapshotvVO=new CommoditySnapshotVO(commodities, dataNow, lotnumber);
	 try {
		snapshotDataService.add(new CommoditySnapshotPO(commoditySnapshotvVO));
	} catch (RemoteException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return commoditySnapshotvVO;
	}
	public CommoditySnapshotVO findbyDate(String date) {
		// TODO Auto-generated method stub
		CommoditySnapshotPO commoditySnapshotPO = null;
		CommoditySnapshotVO commoditySnapshotVO = null;
		try {
			commoditySnapshotPO = snapshotDataService.findBylot(date);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if(commoditySnapshotPO!=null){
		
				commoditySnapshotVO=(new CommoditySnapshotVO(commoditySnapshotPO));
			
		}else{
			
		}
          return commoditySnapshotVO;
	}
	@SuppressWarnings("deprecation")
	public void output(String fileName, CommoditySnapshotVO vo) {
		// TODO Auto-generated method stub
		@SuppressWarnings("resource")
		HSSFWorkbook workbook = new HSSFWorkbook();
	       HSSFSheet sheet = workbook.createSheet("库存盘点");
	       
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
	       	       // 在单元格中输入一些内容
	        //cell0.setCellValue(111);
	       cell0.setCellValue("行号");
	       cell1.setCellValue("商品编号");
	       cell2.setCellValue("商品名称");
	       cell3.setCellValue("商品型号");
	       cell4.setCellValue("库存数量");
	       cell5.setCellValue("库存均价");
	       cell6.setCellValue("批次");
	       cell7.setCellValue("批号");
	       cell8.setCellValue("出厂日期");
	   
	       
	    
  
  int size=vo.getCommodity().size();
  for(int i=0;i<size;i++){
	   HSSFRow row1 = sheet.createRow((short)i+1);
	   HSSFCell cell10 = row1.createCell((short) 0);
	   HSSFCell cell11 = row1.createCell((short) 1);
	   HSSFCell cell12 = row1.createCell((short) 2);
	   HSSFCell cell13 = row1.createCell((short) 3);
	   HSSFCell cell14 = row1.createCell((short) 4);
	   HSSFCell cell15 = row1.createCell((short) 5);
	   HSSFCell cell16 = row1.createCell((short) 6);
	   HSSFCell cell17 = row1.createCell((short) 7);
	   HSSFCell cell18 = row1.createCell((short) 8);
	   
	   cell10.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	   cell11.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	   cell12.setCellType(HSSFCell.CELL_TYPE_STRING);
	   cell13.setCellType(HSSFCell.CELL_TYPE_STRING);
	   cell14.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	   cell15.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	   cell16.setCellType(HSSFCell.CELL_TYPE_STRING);
	   cell17.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	   cell18.setCellType(HSSFCell.CELL_TYPE_STRING);
	  
	  Commodity  com=vo.getCommodity().get(i);
	  cell10.setCellValue(i+1);
	  cell11.setCellValue(com.getCommodityID());
	  cell12.setCellValue(com.getCommodityName());
	  cell13.setCellValue(com.getCommodityModel());
	  cell14.setCellValue(com.getInventoryQuantity());
	  cell15.setCellValue(com.getAveragePrice());
	  cell16.setCellValue(vo.getLot());
	  cell17.setCellValue(vo.getLotNumber());
	  cell18.setCellValue(com.getDate());
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
	

}
