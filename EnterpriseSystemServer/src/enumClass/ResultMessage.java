package enumClass;

public enum ResultMessage {
	CreateDocument,
	SaleUpdate,SaleReturnUpdate,ImportUpdate,ImportReturnUpdate,CreateGiftDocument,GetGiftDocument,
	AddQuantity,DeleteQuantity,
	CommodityIsAlreadyExist,//已经存在了商品不能再添加
	CommoditySortIsAlreadyExist,//已经存在的商品分类不能添加
	CannotDeleteCommodity,//还有库存或者已经销售过不能删除
	CannotDeleteCommoditySort,//有子分类或者商品不能删除该分类
	
	Exist, NotExist,SUCCESS,FAIL,SUCCESSFULEND,AddnewCustomer,
	CUSTOMER_HAS_RECEIVE_AND_PAY,//若客户有应收应付
	

}
