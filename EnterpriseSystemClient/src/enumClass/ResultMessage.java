package enumClass;

public enum ResultMessage {
	CreateDocument,
	SaleUpdate,SaleReturnUpdate,ImportUpdate,ImportReturnUpdate,CreateGiftDocument,GetGiftDocument,
	AddQuantity,DeleteQuantity,
	CommodityIsAlreadyExist,//已经存在了商品不能再添加
	CommoditySortIsAlreadyExist,//已经存在的商品分类不能添加
	CannotDeleteCommodity,//还有库存或者已经销售过不能删除
	CannotDeleteCommoditySort,//有子分类或者商品不能删除该分类
	CannotDeleteDocument,//已经发送不能删除
	Exist, NotExist,SUCCESS,FAIL,SUCCESSFULEND,AddnewCustomer,
	CUSTOMER_HAS_RECEIVE_AND_PAY,//若客户有应收应付
	
	
	//以下几个发生在审批中，括号后的是使用场景，xxx&back表示退货也是用，xxxback表示进退货可用
	CommodityAmount_NotEnough, //商品已发生销售，库存数量不足（importback，sale
	Customer_ReceivableLimit_Break,//超过客户的应收额度，无法进行销售（sale
	
	Commodity_NotExist, //商品列表内已经有商品不存在（import&back，sale&back
	Customer_NotExist, //客户已不存在（import&back，sale&back
	Statement_NotSendable,//在单据执行examine时，单据的状态不为发送（import&back，sale&back
	


}
