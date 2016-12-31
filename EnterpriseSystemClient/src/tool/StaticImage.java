package tool;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class StaticImage {

	/**
	 * 图片地址
	 */
	private static String pathOfLogin = "graphic\\Login.jpg";
	private static String pathOfCommodityWhole = "graphic\\CommodityWhole.jpg";
	private static String pathOfCommodityUI = "graphic\\CommodityUI.jpg";
	private static String pathOfCommodityCheck = "graphic\\CommodityCheck.jpg";
	private static String pathOfCommodityGift = "graphic\\CommodityGift.jpg";
	private static String pathOfCommodityGiftUI = "graphic\\CommodityGiftUI.jpg";
	private static String pathOfExit = "graphic\\Exit.jpg";
	private static String pathOfCommodityManagement = "graphic\\CommodityManagement.jpg";
	private static String pathOfMessage = "graphic\\Message.jpg";
	private static String pathOfProblemDocument = "graphic\\ProblemDocument.jpg";
	private static String pathOfAccountWhole = "graphic\\AccountWhole.jpg";
	private static String pathOfTurnBack = "graphic\\TurnBack.jpg";
	private static String pathOfAdd = "graphic\\Add.jpg";
	private static String pathOfUpdate = "graphic\\Update.jpg";
	private static String pathOfDelete = "graphic\\Delete.jpg";
	private static String pathOfFind = "graphic\\Find.jpg";
	private static String pathOfShow = "graphic\\Show.jpg";
	private static String pathOfDeal = "graphic\\Deal.jpg";
	private static String pathOfProblemDocumentUI = "graphic\\ProblemDocumentUI.jpg";
	private static String pathOfSave = "graphic\\Save.jpg";
	private static String pathOfEdit = "graphic\\Edit.jpg";
	private static String pathOfDocumentCancel = "graphic\\DocumentCancel.jpg";
	private static String pathOfSavebtn = "graphic\\Savebtn.jpg";
	private static String pathOfSmallInput = "graphic\\SmallInput.jpg";
	private static String pathOfOff = "graphic\\Off.jpg";
	private static String pathOfSaveAll = "graphic\\SaveAll.jpg";
	
	private static String pathOfSmallAmount = "graphic\\SmallAmount.jpg";
	private static String pathOfSmallCancel = "graphic\\SmallCancel.jpg";
	private static String pathOfSmallWarn = "graphic\\SmallWarn.jpg";
	private static String pathOfSmallSearch = "graphic\\SmallSearch.jpg";
	private static String pathOfSmall_AccountInput = "graphic\\Small_AccountInput.jpg";
	private static String pathOfCommodityIsAlreadyExist = "graphic\\CommodityIsAlreadyExist.jpg";
	private static String pathOfCommoditySortIsAlreadyExist = "graphic\\CommoditySortIsAlreadyExist.jpg";
	private static String pathOfFailToGetDocument = "graphic\\FailToGetDocument.jpg";
	private static String pathOfByOperator = "graphic\\ByOperator.jpg";
	private static String pathOfByTime = "graphic\\ByTime.jpg";
	private static String pathOfShowAll = "graphic\\ShowAll.jpg";
	private static String pathOfCommodityCheckUI = "graphic\\CommodityCheckUI.jpg";
	private static String pathOfDailyStockCheck = "graphic\\DailyStockCheck.jpg";
	private static String pathOfStockCheck = "graphic\\StockCheck.jpg";
	private static String pathOfStockExport = "graphic\\StockExport.jpg";
	private static String pathOfTimeInput = "graphic\\TimeInput.jpg";
	private static String pathOfTimeInput2 = "graphic\\TimeInput2.jpg";
	private static String pathOfOutPut = "graphic\\OutPut.jpg";
	private static String pathOfAccountCheck = "graphic\\AccountCheck.jpg";
	private static String pathOfAccountDocument = "graphic\\AccountDocument.jpg";
	private static String pathOfAccountInitial = "graphic\\AccountInitial.jpg";
	private static String pathOfAccountDiaryCheck = "graphic\\AccountDiaryCheck.jpg";
	private static String pathOfAccountManagement = "graphic\\AccountManagement.jpg";
	private static String pathOfAccountManagementUI = "graphic\\AccountManagementUI.jpg";
	private static String pathOfAccountInitialUI = "graphic\\AccountInitialUI.jpg";
	private static String pathOfAccountInitialButton = "graphic\\AccountInitialButton.jpg";
	private static String pathOfAccountInitialCheckButton = "graphic\\AccountInitialCheckButton.jpg";
	private static String pathOfAccountCheckUI = "graphic\\AccountCheckUI.jpg";
	private static String pathOfListDetail = "graphic\\ListDetail.jpg";
	private static String pathOfSalesList = "graphic\\SalesList.jpg";
	private static String pathOfBusinessProcessList = "graphic\\BusinessProcessList.jpg";
	private static String pathOfBusinessList = "graphic\\BusinessList.jpg";
	private static String pathOfAccountExport = "graphic\\AccountExport.jpg";
	private static String pathOfAccountDocumentUI = "graphic\\AccountDocumentUI.jpg";
	private static String pathOfAccountInput = "graphic\\AccountInput.jpg";
	private static String pathOfCircle = "graphic\\Circle.jpg";
	private static String pathOfAccountManagementInput = "graphic\\AccountManagementInput.jpg";
	private static String pathOfAccountPayDocumentInput = "graphic\\AccountPayDocumentInput.jpg";
	private static String pathOfAccountReceiveDocumentInput = "graphic\\AccountReceiveDocumentInput.jpg";
	private static String pathOfAccountCashDocumentInput = "graphic\\AccountCashDocumentInput.jpg";
	private static String pathOfAccountInput2 = "graphic\\AccountInput2.jpg";
	private static String pathOfRefresh="graphic\\Refresh.jpg";
	private static String pathOfcannotAddCommodityDocumentHere="graphic\\cannotAddCommodityDocumentHere.jpg";
	private static String pathOfSmallOperatorInput = "graphic\\SmallOperatorInput.jpg";
	private static String pathOfDocumentPresentation = "graphic\\DocumentPresentation.jpg";
	private static String pathOfDocumentPresentationExport = "graphic\\DocumentPresentationExport.jpg";
	private static String pathOfDocumentPresentationRed = "graphic\\DocumentPresentationRed.jpg";
	private static String pathOfDocumentPresentationRedRepeat = "graphic\\DocumentPresentationRedRepeat.jpg";
	
	private static String pathOfJboCustMag = "graphic/ImportAndSale/jboCustMag.jpg";// 客户管理按钮
	private static String pathOfJboImpMag = "graphic/ImportAndSale/jboImpMag.jpg";// 进货管理按钮
	private static String pathOfJboImfo = "graphic/ImportAndSale/jboImfo.jpg";// 消息提醒按钮
	private static String pathOfJboSaleMag = "graphic/ImportAndSale/jboSaleMag.jpg";// 销售管理按钮
	private static String pathOfJboSystSet = "graphic/ImportAndSale/jboSystSet.jpg";// 系统设置
	private static String pathOfJboVoucher = "graphic/ImportAndSale/jboVoucher.jpg";// 单据管理
	private static String pathOfImpSaleMain = "graphic/ImportAndSale/ImpSaleMain.jpg";// 进货销售人员主界面
	private static String pathOfManagerWholeUI = "graphic\\ManagerWholeUI.jpg";
	private static String pathOfDiaryCheck = "graphic\\DiaryCheck.jpg";
	private static String pathOfManagementCheck = "graphic\\ManagementCheck.jpg";
	private static String pathOfPromotion = "graphic\\Promotion.jpg";
	private static String pathOfDocumentProcess = "graphic\\DocumentProcess.jpg";
	private static String pathOfDocumentDetail = "graphic\\DocumentDetail.jpg";
	private static String pathOfSystemWholeUI = "graphic\\SystemWholeUI.jpg";
	private static String pathOfSystemSet = "graphic\\SystemSet.jpg";
	private static String pathOfUserManagement = "graphic\\UserManagement.jpg";
	private static String pathOfCommodityInput = "graphic\\CommodityInput.jpg";
	private static String pathOfSuccess = "graphic\\Success.jpg";
	private static String pathOfSubmit = "graphic\\Submit.jpg";
	private static String pathOfCustomerUI = "graphic/ImportAndSale/CustomerUI.jpg";// 客户管理界面背景
	private static String pathOfImportUI = "graphic/ImportAndSale/ImportUI.jpg";// 进货管理界面背景
	private static String pathOfSaleUI = "graphic/ImportAndSale/SaleUI.jpg";// 进货管理界面背景
	private static String pathOfjbu_import = "graphic/ImportAndSale/jbu_import.jpg";// 进货按钮
	private static String pathOfjbu_returnImp = "graphic/ImportAndSale/jbu_returnImp.jpg";// 退货按钮
	private static String pathOfjbu_submit = "graphic/ImportAndSale/jbu_submit.jpg";// 提交按钮
	private static String pathOfjbu_sale = "graphic/ImportAndSale/jbu_sale.jpg";// 销售按钮
	private static String pathOfUserMag = "graphic/ImportAndSale/UserMagUI.jpg";// 用户管理界面背景
	private static String pathOfPromotionUI = "graphic\\PromotionUI.jpg";
	private static String pathOfPromotionCustomer = "graphic\\PromotionCustomer.jpg";
	private static String pathOfSpecialPrice = "graphic\\SpecialPrice.jpg";
	private static String pathOfPromotionPrice = "graphic\\PromotionPrice.jpg";
	private static String pathOfSend = "graphic\\Send.jpg";
	private static String pathOfCancel = "graphic\\Cancel.jpg";
	private static String pathOfDocumentProcessUI = "graphic\\DocumentProcessUI.jpg";
	private static String pathOfManagerPass = "graphic\\ManagerPass.jpg";
	private static String pathOfManagerUpdate = "graphic\\ManagerUpdate.jpg";
	private static String pathOfDiaryCheckUI = "graphic\\DiaryCheckUI.jpg";
	private static String pathOfPromotionCustomerInput = "graphic\\PromotionCustomerInput.jpg";
	private static String pathOfSpecialPriceInput = "graphic\\SpecialPriceInput.jpg";
	private static String pathOfPromotionPriceInput = "graphic\\PromotionPriceInput.jpg";
	private static String pathOfRedRepeat = "graphic\\RedRepeat.jpg";
	private static String pathOfOK = "graphic\\OK.jpg";
	private static String pathOfbtn_LogIn = "graphic\\btn_LogIn.JPG";

	private static String pathOfPersonalInfo = "graphic/personalInfo.jpg";// 个人信息界面
	private static String pathOfjbu_veeify = "graphic/verify.png";// 个人信息界面确认键

	private static String pathOfImportInput = "graphic/ImportAndSale/importInput.jpg";// 进货管理商品信息登记页面
	private static String pathOfjbu_cancel = "graphic/ImportAndSale/cancel.png";// 取消按钮
	private static String pathOfjbu_save = "graphic/ImportAndSale/jbu_save.png";// 保存按钮

	private static String pathOffaile_login = "graphic/Fail_Login.png";// 用户不存在
	private static String pathOffail_password = "graphic/Fail_Password.jpg";// 密码不正确
	private static String pathOfjbu_click = "graphic/click.png";// 提示小框确认键
	private static String pathOffail_add = "graphic/Fail_Add.jpg";// 不可添加
	private static String pathOfcustomer_input = "graphic/CustomerInput.jpg";// 客户信息输入界面
	private static String pathOfjbu_choose="graphic/ImportAndSale/jbu_choose.png";//进货添加商品的选择按钮
	private static String pathOfcommodity_choose="graphic/ImportAndSale/CommodityChoose.jpg";//进货添加商品的背景
	private static String pathOfNum_Lack="graphic/Num_Lack.jpg";//提示界面背景
	private static String pathOfFail_CommodityReturn="graphic/Fail_CommodityReturn.jpg";//提示不可退货

	private static String pathOfCustomer_Exit="graphic/ImportAndSale/Customer_Exit.jpg";//提示客户已存在
	private static String pathOfFail_CustomerDelete="graphic/ImportAndSale/Fail_CustomerDelete.jpg";//提示客户不可删除
	private static String pathOfCustomer_Select="graphic/ImportAndSale/Customer_Select.jpg";//提示需要选择客户
	
	private static String pathOfFailToRed = "graphic/FailToRed.jpg";// 
	private static String pathOfFailToRedRepeat = "graphic/FailToRedRepeat.jpg";// 
	private static String pathOfFailToAddDocument = "graphic/FailToAddDocument.jpg";// 
	private static String pathOfFailToExport = "graphic/FailToExport.jpg";// 
	private static String pathOfFailToDeleteSort = "graphic/FailToDeleteSort.jpg";// 不能新增商品分类
	private static String pathOfFailToUpdate = "graphic/FailToUpdate.jpg";// 不能新增商品分类
	private static String pathOfFail_Level = "graphic/ImportAndSale/Fail_Level.jpg";// 不可修改
	private static String pathOfFail_Operation = "graphic/ImportAndSale/Fail_Operation.jpg";// 操作失败
	private static String pathOfIlleage= "graphic/ImportAndSale/Illegal.jpg";//非法操作
	private static String pathOfImportChoose="graphic/ImportAndSale/ImportChoose.jpg";//选择进货单
	private static String pathOfJbu_Info="graphic/ImportAndSale/jbu_info.png";//详情按钮
	private static String pathOfJbu_verify="graphic/ImportAndSale/jbu_verify.png";//确定
	private static String pathOfCommodityDetil="graphic/ImportAndSale/CommodityDetil.jpg";//商品详情
	private static String pathOfImpSaleDocUI="graphic/ImportAndSale/ImpSaleDocUI.jpg";//单据
	private static String pathOfjbu_AllSend="graphic/ImportAndSale/jbu_AllSend.png";//批量发送
	private static String pathOfSelect_Draft="graphic/ImportAndSale/Select_Draft.jpg";//选择未发送单据
	private static String pathOfjbu_docDetil="graphic/ImportAndSale/jbu_docDetil.png";//选择未发送单据
	private static String pathOfSendFirst="graphic/SomeImage/sendFirst.jpg";//提示先发生促销策略
	private static String pathOfFail_Delete="graphic/SomeImage/Fail_Delete.jpg";//提示不可刪除
	private static String pathOfPro_exist="graphic/SomeImage/Pro_Exist.jpg";//策略已存在
	private static String pathOfAllPass="graphic/SomeImage/AllPass.jpg";//批量审批
	private static String pathOfPromotionPackageInput="graphic/PromotionPackageInput.jpg";//特价包
	private static String pathOfjbu_update="graphic/SomeImage/jbu_update.jpg";//小修改按钮
	private static String pathOfDocProDetail="graphic/SomeImage/DocDetail.jpg";//审批单据详情背景
	private static String pathOfjbu_BigChoose="graphic/ImportAndSale/big_choose.png";//选择按钮
	private static String pathOfCom_NotExit="graphic/SomeImage/Commodity_NotExist.jpg";
	private static String pathOfComAouNotEno="graphic/SomeImage/CommodityAmount_NotEnough.jpg";
	private static String pathOfReceLimit="graphic/SomeImage/Customer_ReceivableLimit_Break.jpg";
	private static String pathOfCustomerNotExit="graphic/SomeImage/Customer_NotExit.jpg";
	/**
	 * 图片名称
	 */
	// 每加一个只需要这一行就行了
	public static ImageIcon backOfLogin = new ImageIcon(pathOfLogin);
	public static ImageIcon backOfCommodityWhole = new ImageIcon(
			pathOfCommodityWhole);
	public static ImageIcon backOfAccountWhole = new ImageIcon(
			pathOfAccountWhole);
	public static ImageIcon backOfCommodityUI = new ImageIcon(pathOfCommodityUI);
	public static ImageIcon backOfCommodityCheck = new ImageIcon(
			pathOfCommodityCheck);
	public static ImageIcon backOfCommodityGift = new ImageIcon(
			pathOfCommodityGift);
	public static ImageIcon backOfCommodityGiftUI = new ImageIcon(
			pathOfCommodityGiftUI);
	public static ImageIcon backOfExit = new ImageIcon(pathOfExit);
	public static ImageIcon backOfSmallOperatorInput = new ImageIcon(pathOfSmallOperatorInput);
	public static ImageIcon backOfCommodityManagement = new ImageIcon(
			pathOfCommodityManagement);
	public static ImageIcon backOfMessage = new ImageIcon(pathOfMessage);
	public static ImageIcon backOfProblemDocument = new ImageIcon(
			pathOfProblemDocument);
	public static ImageIcon backOfTurnBack = new ImageIcon(pathOfTurnBack);
	public static ImageIcon backOfAdd = new ImageIcon(pathOfAdd);
	public static ImageIcon backOfUpdate = new ImageIcon(pathOfUpdate);
	public static ImageIcon backOfDelete = new ImageIcon(pathOfDelete);
	public static ImageIcon backOfFind = new ImageIcon(pathOfFind);
	public static ImageIcon backOfShow = new ImageIcon(pathOfShow);
	public static ImageIcon backOfCircle = new ImageIcon(pathOfCircle);
	public static ImageIcon backOfCommodityCheckUI = new ImageIcon(
			pathOfCommodityCheckUI);
	public static ImageIcon backOfDailyStockCheck = new ImageIcon(
			pathOfDailyStockCheck);
	public static ImageIcon backOfStockCheck = new ImageIcon(pathOfStockCheck);
	public static ImageIcon backOfStockExport = new ImageIcon(pathOfStockExport);
	public static ImageIcon backOfTimeInput = new ImageIcon(pathOfTimeInput);
	public static ImageIcon backOfTimeInput2 = new ImageIcon(pathOfTimeInput2);
	public static ImageIcon backOfOutPut = new ImageIcon(pathOfOutPut);
	public static ImageIcon backOfProblemDocumentUI = new ImageIcon(
			pathOfProblemDocumentUI);
	public static ImageIcon backOfSave = new ImageIcon(pathOfSave);
	public static ImageIcon backOfOff = new ImageIcon(pathOfOff);
	public static ImageIcon backOfEdit = new ImageIcon(pathOfEdit);
	public static ImageIcon backOfDeal = new ImageIcon(pathOfDeal);
	public static ImageIcon backOfDocumentCancel = new ImageIcon(
			pathOfDocumentCancel);
	public static ImageIcon backOfDocumentPresentation = new ImageIcon(
			pathOfDocumentPresentation);
	public static ImageIcon backOfDocumentPresentationExport = new ImageIcon(
			pathOfDocumentPresentationExport);
	public static ImageIcon backOfDocumentPresentationRed = new ImageIcon(
			pathOfDocumentPresentationRed);
	public static ImageIcon backOfDocumentPresentationRedRepeat = new ImageIcon(
			pathOfDocumentPresentationRedRepeat);
	
	public static ImageIcon backOfDocumentDetail = new ImageIcon(
			pathOfDocumentDetail);
	public static ImageIcon backOfSavebtn = new ImageIcon(pathOfSavebtn);
	public static ImageIcon backOfSmallInput = new ImageIcon(pathOfSmallInput);
	public static ImageIcon backOfSmallCancel = new ImageIcon(pathOfSmallCancel);
	public static ImageIcon backOfSmallAmount = new ImageIcon(pathOfSmallAmount);
	public static ImageIcon backOfSmallWarn = new ImageIcon(pathOfSmallWarn);
	public static ImageIcon backOfSmallSearch = new ImageIcon(pathOfSmallSearch);
	public static ImageIcon backOfSmall_AccountInput = new ImageIcon(pathOfSmall_AccountInput);
	public static ImageIcon backOfCommodityIsAlreadyExist = new ImageIcon(pathOfCommodityIsAlreadyExist);
	public static ImageIcon backOfCommoditySortIsAlreadyExist = new ImageIcon(pathOfCommoditySortIsAlreadyExist);
	public static ImageIcon backOfcannotAddCommodityDocumentHere = new ImageIcon(pathOfcannotAddCommodityDocumentHere);
	public static ImageIcon backOfFailToGetDocument = new ImageIcon(pathOfFailToGetDocument);
	public static ImageIcon backOfByOperator = new ImageIcon(pathOfByOperator);
	public static ImageIcon backOfByTime = new ImageIcon(pathOfByTime);
	public static ImageIcon backOfShowAll = new ImageIcon(pathOfShowAll);
	public static ImageIcon JboCustMag = new ImageIcon(pathOfJboCustMag);// 客户管理按钮
	public static ImageIcon JboImfo = new ImageIcon(pathOfJboImfo);// 消息提醒按钮
	public static ImageIcon JboImpMag = new ImageIcon(pathOfJboImpMag);// 进货管理按钮
	public static ImageIcon JboSaleMag = new ImageIcon(pathOfJboSaleMag);// 销售管理按钮
	public static ImageIcon JboSystSet = new ImageIcon(pathOfJboSystSet);// 系统设置
	public static ImageIcon JboVoucher = new ImageIcon(pathOfJboVoucher);// 单据管理
	public static ImageIcon ImpSaleMain = new ImageIcon(pathOfImpSaleMain);// 进货销售人员主界面

	public static ImageIcon backOfAccountCheck = new ImageIcon(
			pathOfAccountCheck);
	public static ImageIcon backOfAccountDiaryCheck = new ImageIcon(
			pathOfAccountDiaryCheck);
	public static ImageIcon backOfAccountDocument = new ImageIcon(
			pathOfAccountDocument);
	public static ImageIcon backOfAccountInitial = new ImageIcon(
			pathOfAccountInitial);
	public static ImageIcon backOfAccountManagement = new ImageIcon(
			pathOfAccountManagement);
	public static ImageIcon backOfAccountManagementUI = new ImageIcon(
			pathOfAccountManagementUI);
	public static ImageIcon backOfAccountInitialUI = new ImageIcon(
			pathOfAccountInitialUI);
	public static ImageIcon backOfAccountInitialButton = new ImageIcon(
			pathOfAccountInitialButton);
	public static ImageIcon backOfAccountInitialCheckButton = new ImageIcon(
			pathOfAccountInitialCheckButton);
	public static ImageIcon backOfAccountCheckUI = new ImageIcon(
			pathOfAccountCheckUI);
	public static ImageIcon backOfSalesList = new ImageIcon(pathOfSalesList);
	public static ImageIcon backOfBusinessProcessList = new ImageIcon(
			pathOfBusinessProcessList);
	public static ImageIcon backOfBusinessList = new ImageIcon(
			pathOfBusinessList);
	public static ImageIcon backOfListDetail = new ImageIcon(
			pathOfListDetail);
	public static ImageIcon backOfAccountExport = new ImageIcon(
			pathOfAccountExport);
	public static ImageIcon backOfAccountDocumentUI = new ImageIcon(
			pathOfAccountDocumentUI);
	public static ImageIcon backOfAccountInput = new ImageIcon(
			pathOfAccountInput);
	public static ImageIcon backOfAccountInput2 = new ImageIcon(
			pathOfAccountInput2);
	public static ImageIcon backOfAccountManagementInput = new ImageIcon(
			pathOfAccountManagementInput);
	public static ImageIcon backOfAccountPayDocumentInput = new ImageIcon(
			pathOfAccountPayDocumentInput);
	public static ImageIcon backOfAccountReceiveDocumentInput = new ImageIcon(
			pathOfAccountReceiveDocumentInput);
	public static ImageIcon backOfAccountCashDocumentInput = new ImageIcon(
			pathOfAccountCashDocumentInput);
	public static ImageIcon backOfRefresh = new ImageIcon(
			pathOfRefresh);
	public static ImageIcon backOfManagerWholeUI = new ImageIcon(
			pathOfManagerWholeUI);// 总经理主界面
	public static ImageIcon backOfDiaryCheck = new ImageIcon(pathOfDiaryCheck);
	public static ImageIcon backOfManagementCheck = new ImageIcon(
			pathOfManagementCheck);
	public static ImageIcon backOfPromotion = new ImageIcon(pathOfPromotion);
	public static ImageIcon backOfDocumentProcess = new ImageIcon(
			pathOfDocumentProcess);
	public static ImageIcon backOfSystemWholeUI = new ImageIcon(
			pathOfSystemWholeUI);
	public static ImageIcon backOfSystemSet = new ImageIcon(pathOfSystemSet);
	public static ImageIcon backOfUserManagement = new ImageIcon(
			pathOfUserManagement);
	public static ImageIcon backOfCommodityInput = new ImageIcon(
			pathOfCommodityInput);
	public static ImageIcon backOfSuccess = new ImageIcon(pathOfSuccess);
	public static ImageIcon backOfSubmit = new ImageIcon(pathOfSubmit);
	public static ImageIcon backOfCustomer = new ImageIcon(pathOfCustomerUI);// 客户管理背景
	public static ImageIcon backOfImport = new ImageIcon(pathOfImportUI);// 进货管理界面背景
	public static ImageIcon backOfSale = new ImageIcon(pathOfSaleUI);// 进货管理界面背景
	public static ImageIcon backOfjbu_import = new ImageIcon(pathOfjbu_import);// 进货按钮
	public static ImageIcon backOfjbu_returnImp = new ImageIcon(
			pathOfjbu_returnImp);// 退货按钮
	public static ImageIcon backOfjbu_submit = new ImageIcon(pathOfjbu_submit);// 提交按钮
	public static ImageIcon backOfjbu_sale = new ImageIcon(pathOfjbu_sale);// 提交按钮
	public static ImageIcon backOfuserMagUI = new ImageIcon(pathOfUserMag);// 用户管理界面背景
	public static ImageIcon backOfOK = new ImageIcon(pathOfOK);

	public static ImageIcon backOfbtn_LogIn = new ImageIcon(pathOfbtn_LogIn);
	public static ImageIcon backOfSaveAll = new ImageIcon(pathOfSaveAll);
	public static ImageIcon backOfPromotionUI = new ImageIcon(pathOfPromotionUI);
	public static ImageIcon backOfPromotionCustomer = new ImageIcon(
			pathOfPromotionCustomer);
	public static ImageIcon backOfSpecialPrice = new ImageIcon(
			pathOfSpecialPrice);
	public static ImageIcon backOfPromotionPrice = new ImageIcon(
			pathOfPromotionPrice);
	public static ImageIcon backOfSend = new ImageIcon(pathOfSend);
	public static ImageIcon backOfCancel = new ImageIcon(pathOfCancel);
	public static ImageIcon backOfDocumentProcessUI = new ImageIcon(
			pathOfDocumentProcessUI);
	public static ImageIcon backOfManagerPass = new ImageIcon(pathOfManagerPass);
	public static ImageIcon backOfManagerUpdate = new ImageIcon(
			pathOfManagerUpdate);
	public static ImageIcon backOfDiaryCheckUI = new ImageIcon(
			pathOfDiaryCheckUI);
	public static ImageIcon backOfPromotionCustomerInput = new ImageIcon(
			pathOfPromotionCustomerInput);
	public static ImageIcon backOfSpecialPriceInput = new ImageIcon(
			pathOfSpecialPriceInput);
	public static ImageIcon backOfPromotionPriceInput = new ImageIcon(
			pathOfPromotionPriceInput);
	public static ImageIcon backOfPersonalInfo = new ImageIcon(
			pathOfPersonalInfo);// 个人信息界面
	public static ImageIcon backOfVerify = new ImageIcon(pathOfjbu_veeify);// 确认键
	public static ImageIcon backOfImportInput = new ImageIcon(pathOfImportInput);// 进货管理商品信息输入界面背景
	public static ImageIcon backOfjbu_cancel = new ImageIcon(pathOfjbu_cancel);// 取消按钮
	public static ImageIcon backOfjbu_save = new ImageIcon(pathOfjbu_save);// 保存按钮

	public static ImageIcon backOfFail_login = new ImageIcon(pathOffaile_login);// 用户名不存在
	public static ImageIcon backOfFailToUpdate = new ImageIcon(pathOfFailToUpdate);
	public static ImageIcon backOfFail_password = new ImageIcon(
			pathOffail_password);// 密码不正确
	public static ImageIcon backOfjbu_click = new ImageIcon(pathOfjbu_click);// 提示小框确认按钮
	public static ImageIcon backOfFail_add = new ImageIcon(pathOffail_add);// 不可添加
	public static ImageIcon backOfCustomer_Input = new ImageIcon(
			pathOfcustomer_input);// 客户信息输入界面
	public static ImageIcon backOfjbu_choose=new ImageIcon(pathOfjbu_choose);//进货添加商品的选择按钮
	public static ImageIcon backOfCommodity_choose=new ImageIcon(pathOfcommodity_choose);//进货添加商品的背景
	public static ImageIcon backOfNum_Lack=new ImageIcon(pathOfNum_Lack);//数据不全背景
	public static ImageIcon backOfFail_CommodityReturn=new ImageIcon(pathOfFail_CommodityReturn);//提示不可退货

	public static ImageIcon backOfCustomer_Exit=new ImageIcon(pathOfCustomer_Exit);//提示客户已存在
	public static ImageIcon backOfFail_CustomerDelete=new ImageIcon(pathOfFail_CustomerDelete);//提示客户已存在
	public static ImageIcon backOfFailToExport=new ImageIcon(pathOfFailToExport);//
	public static ImageIcon backOfFailToRedRepeat=new ImageIcon(pathOfFailToRedRepeat);//
	public static ImageIcon backOfRedRepeat=new ImageIcon(pathOfRedRepeat);//
	public static ImageIcon backOfFailToRed=new ImageIcon(pathOfFailToRed);//
	public static ImageIcon backOfFailToAddDocument=new ImageIcon(pathOfFailToAddDocument);//
	public static ImageIcon backOfFailToDeleteSort=new ImageIcon(pathOfFailToDeleteSort);//
	public static ImageIcon backOfCustomer_Select=new ImageIcon(pathOfCustomer_Select);//提示选择客户
    public static ImageIcon backOfFail_Level=new ImageIcon(pathOfFail_Level);//无权限修改
    public static ImageIcon backOfFail_Operation=new ImageIcon(pathOfFail_Operation);//操作失败
    public static ImageIcon backOfIllegal=new ImageIcon(pathOfIlleage);//非法操作
    public static ImageIcon backOfImportChoose=new ImageIcon(pathOfImportChoose);//选择进货单
    public static ImageIcon backOfJbu_Info=new ImageIcon(pathOfJbu_Info);//详情按钮
    public static ImageIcon backOfJbu_Verify=new ImageIcon(pathOfJbu_verify);//商品详情的确认键
    public static ImageIcon backOfCommodityDetil=new ImageIcon(pathOfCommodityDetil);//详情
	public static ImageIcon backOfImpSaleDocUI=new ImageIcon(pathOfImpSaleDocUI);//进货销售单据管理
	public static ImageIcon backOfjbu_AllSend=new ImageIcon(pathOfjbu_AllSend);
	public static ImageIcon backOfSelect_Draft=new ImageIcon(pathOfSelect_Draft);
	public static ImageIcon backOfjbu_docDetil=new ImageIcon(pathOfjbu_docDetil);
	public static ImageIcon backOfSendFirst=new ImageIcon(pathOfSendFirst);//提示先发生策略
	public static ImageIcon backOfFail_Delete=new ImageIcon(pathOfFail_Delete);//提示刪除失敗
	public static ImageIcon backofPro_Exist=new ImageIcon(pathOfPro_exist);//策略已存在
	public static ImageIcon backOfAllPass=new ImageIcon(pathOfAllPass);//批量审批
	public static ImageIcon backOfPro_PackInput=new ImageIcon(pathOfPromotionPackageInput);
	public static ImageIcon backOfjbu_update=new ImageIcon(pathOfjbu_update);//小修改按钮
	public static ImageIcon backOfDocProDetail=new ImageIcon(pathOfDocProDetail);//审批单据详情背景
	public static ImageIcon backOfBigChoose=new ImageIcon(pathOfjbu_BigChoose);//选择按钮
	public static ImageIcon backOfComNotExti=new ImageIcon(pathOfCom_NotExit);
	public static ImageIcon backOfComAmoNotEno=new ImageIcon(pathOfComAouNotEno);
	public static ImageIcon backofReceLinit=new ImageIcon(pathOfReceLimit);
	public static ImageIcon backofCusNotExit=new ImageIcon(pathOfCustomerNotExit);
 // 按组存放的图片
	public static ArrayList<ImageIcon> ImpSaleMag = new ArrayList<ImageIcon>();
	
	public static void getInit() {
		// 进货销售主界面
		ImpSaleMag.add(JboCustMag);
		ImpSaleMag.add(JboImfo);
		ImpSaleMag.add(JboImpMag);
		ImpSaleMag.add(JboSaleMag);
		ImpSaleMag.add(JboSystSet);
		ImpSaleMag.add(JboVoucher);
		ImpSaleMag.add(backOfExit);

	}
}
