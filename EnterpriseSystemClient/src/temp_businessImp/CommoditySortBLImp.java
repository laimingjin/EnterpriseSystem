package temp_businessImp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import po.CommodityPO;
import po.CommoditySortPO;
import temp_business.CommodityBLService;
import temp_business.CommoditySortBLService;
import temp_business.LogBLService;
import temp_business.LoginBLService;
import vo.CommoditySortVO;
import vo.CommodityVO;
import vo.LogVO;
import vo.UserVO;
import dataservice.CommoditySortDataService;
import enumClass.ResultMessage;

public class CommoditySortBLImp implements CommoditySortBLService {
	CommoditySortDataService commoditySortDataService;
	LoginBLService loginBLService=new LoginBLImp();
	LogBLService logBLService=new LogBLImp();
//为毛不能初始化。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。
	public CommoditySortBLImp() {
		try {
			commoditySortDataService = (CommoditySortDataService) Naming
					.lookup(StaticInfo.URL_COMMODITYSORT);
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
	public CommoditySortVO getRoot() {
		// TODO Auto-generated method stub
		// 获得最根根根的根节点全部分类商品的根
		CommoditySortPO root = null;
		try {
			root = commoditySortDataService.find("商品");

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CommoditySortVO commoditySortVO = new CommoditySortVO(root);
		return commoditySortVO;
	
	}

	public ResultMessage addCommoditySort(CommoditySortVO commoditySortVO) {
		// TODO Auto-generated method stub
		CommoditySortPO commoditySortPO =null;
		ResultMessage result = ResultMessage.FAIL;
		// 如果不能增加是已经存在了
		if (isAbleToAddCommoditySort(commoditySortVO) == false) {
			result = ResultMessage.CommoditySortIsAlreadyExist;
		} else {
			String fatherString = commoditySortVO.getFather();
			try {
				commoditySortPO = new CommoditySortPO(
						commoditySortVO);
				CommoditySortPO  fatherCommoditySortPO=commoditySortDataService.find(fatherString);
				fatherCommoditySortPO.getCommoditySortSonList().add(commoditySortPO);
			      commoditySortDataService.update(fatherCommoditySortPO);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		
			try {
				result = commoditySortDataService.add(commoditySortPO);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		  if(result.equals(ResultMessage.SUCCESS)){
            	Addlog("新增商品分类成功");
            }else{
            	Addlog("新增商品分类失败");
            }
		return result;
	}

	public boolean isAbleToAddCommoditySort(CommoditySortVO commoditySortVO) {
		// TODO Auto-generated method stub
		boolean isableToADD = false;

		String name = commoditySortVO.getCommoditySortName();

		try {
			CommoditySortPO result = commoditySortDataService.find(name);
			if (result == null) {
				isableToADD = true;
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isableToADD;

	}

	public ResultMessage deleteCommoditySort(String commoditySortName) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
//		if (isAbleToDeleteCommoditySort(commoditySortName) == false) {
//			// 不能删除
//			resultMessage = ResultMessage.CannotDeleteCommodity;
//		}
//		else {
			try {
					CommoditySortPO commoditySortPO = commoditySortDataService.find(commoditySortName);
					String fatherString = commoditySortPO.getFather();
					CommoditySortVO fatherCommoditySortVO=getCommoditySortbyName(fatherString);
			//	CommoditySortPO  fatherCommoditySortPO=commoditySortDataService.find(fatherString);
				for(int i=0;i<fatherCommoditySortVO.getCommoditySortSonList().size();i++){
					if(fatherCommoditySortVO.getCommoditySortSonList().get(i).getCommoditySortName().equals(commoditySortName)){
		
						fatherCommoditySortVO.getCommoditySortSonList().remove(i);
						
					}
					updateCommoditySort( fatherCommoditySortVO);
				}
				
			      resultMessage = commoditySortDataService.delete(commoditySortPO);
				// 成功返回success
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			  if(resultMessage.equals(ResultMessage.SUCCESS)){
	              	Addlog("删除商品分类成功");
	              }else{
	              	Addlog("删除商品分类失败");
	              }
		return resultMessage;
	}

//	public boolean isAbleToDeleteCommoditySort(String commoditySortName) {
//		// TODO Auto-generated method stub
//		boolean result = false;
//		try {
//			CommoditySortPO commoditySortPO = commoditySortDataService
//					.find(commoditySortName);
//			if ((commoditySortPO.getCommodityList() .size()==0)
//					&& (commoditySortPO.getCommoditySortSonList().size()==0)) {
//				result = true;
//			}
//		} catch (RemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return result;
//	}

	public ResultMessage updateCommoditySort(int sortID,
			String commoditySortName) {
		// TODO Auto-generated method stub

		ResultMessage resultMessage = ResultMessage.FAIL;
		try {
			CommoditySortPO commoditySortPO = commoditySortDataService
					.find(sortID);
			ArrayList<CommodityPO> commodityVOs = commoditySortPO
					.getCommodityList();
			ArrayList<CommoditySortPO> commoditySortVOs = commoditySortPO
					.getCommoditySortSonList();
			boolean hasCommodity = commoditySortPO.isHasCommodity();
			String fatherString = commoditySortPO.getFather();
			CommoditySortPO  fatherCommoditySortPO=commoditySortDataService.find(fatherString);

			CommoditySortPO cSortPO = new CommoditySortPO(commoditySortName,
					sortID, fatherString, hasCommodity, commoditySortVOs,
					commodityVOs);
			for(int i=0;i<fatherCommoditySortPO.getCommoditySortSonList().size();i++){
				if(fatherCommoditySortPO.getCommoditySortSonList().get(i).getCommoditySortID()==sortID){
					fatherCommoditySortPO.getCommoditySortSonList().get(i).setCommoditySortName(commoditySortName);
					commoditySortDataService.update(fatherCommoditySortPO);
				}
			}
			if(cSortPO.getCommoditySortSonList()!=null){
				for(int i=0;i<cSortPO.getCommoditySortSonList().size();i++){
					cSortPO.getCommoditySortSonList().get(i).setFather(commoditySortName);
					commoditySortDataService.update(cSortPO.getCommoditySortSonList().get(i));
					
				}
			}
			if(cSortPO.getCommodityList()!=null){
				for(int i=0;i<cSortPO.getCommodityList().size();i++){
					cSortPO.getCommodityList().get(i).setCommoditySortName(commoditySortName);
					
					CommodityBLService commodityBLService=new CommodityBLImp();
					CommodityVO commodityVO=commodityBLService.getCommodityByID(cSortPO.getCommodityList().get(i).getCommodityID());
				commodityVO.setCommoditySortName(commoditySortName);
					commodityBLService.updateCommodity(commodityVO);
				}
			}
			
			resultMessage = commoditySortDataService.update(cSortPO);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!resultMessage.equals(ResultMessage.SUCCESS)){
			  Addlog("更新商品分类信息失败");
          }else{
          	
          }
		return resultMessage;
	}

	public ResultMessage endSortManagement() {
		return null;
	}

	public int getNewSortID() {
		// TODO Auto-generated method stub
		int finalID = 0;
		try {
			finalID = commoditySortDataService.getFinalID();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (finalID + 1);
	}

	public CommoditySortVO getCommoditySortbyID(int ID) {
		// TODO Auto-generated method stub
		CommoditySortPO commoditySortPO = null;
		try {
			commoditySortPO = commoditySortDataService.find(ID);

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		CommoditySortVO commoditySortVO = new CommoditySortVO(	commoditySortPO );

		return commoditySortVO;
	}

	public ResultMessage updateCommoditySort( CommoditySortVO commoditySortVO) {
		// TODO Auto-generated method stub
		ResultMessage resultMessage = ResultMessage.FAIL;
	
		try {
		//更新新加的!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			CommoditySortPO commoditySortPO = new CommoditySortPO(
					commoditySortVO);
			resultMessage = commoditySortDataService.update(commoditySortPO);
			if(!(commoditySortVO.getFather().equals("商品根"))){
				CommoditySortVO fatherCommoditySortVO=getCommoditySortbyName(commoditySortVO.getFather());
				
				for(int i=0;i<fatherCommoditySortVO.getCommoditySortSonList().size();i++){
					if(fatherCommoditySortVO.getCommoditySortSonList().get(i).getCommoditySortID()==commoditySortVO.getCommoditySortID()){
						fatherCommoditySortVO.getCommoditySortSonList().set(i, commoditySortVO);
						updateCommoditySort(fatherCommoditySortVO);
						break;
					}
				}
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		  if(!resultMessage.equals(ResultMessage.SUCCESS)){
			  Addlog("更新商品分类信息失败");
            }else{
            	
            }
		return resultMessage;
	}

	public CommoditySortVO getCommoditySortbyName(String sortName) {
		// TODO Auto-generated method stub
		//CommoditySortPO commoditySortPO = null;
		
			try {
				CommoditySortPO commoditySortPO = commoditySortDataService.find(sortName);
				CommoditySortVO commoditySortVO = new CommoditySortVO(commoditySortPO );

				return commoditySortVO;
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		 catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
		return null;
	}

}
