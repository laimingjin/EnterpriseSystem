package po;

import java.io.Serializable;

public class BusinessListPO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
double saleIncome;//销售收入
double commodityOverflowIncome;//商品报溢收入
double costAdjustIncome;//成本调价收入
double importAndExportSpreadIncome;//进货退货差价
double withVoucherIncome;//代金券与实际收款差额收入
double rebateIncomeAfter;//折让后总收入
double rebatePrice;//折让了多少
double saleCost;//销售成本
double commodityLossCost;//商品报损支出
double commodityGiftCost;//商品赠出支出
double totalEnpense;//总支出
double profit;//利润
public BusinessListPO(double saleIncome,double commodityOverflowIncome,double costAdjustIncome,double importAndExportSpreadIncome,
		double withVoucherIncome,double rebateIncomeAfter,double rebatePrice,double saleCost,double commodityLossCost,double commodityGiftCost,double totalEnpense,double profit		){
	this.saleIncome=saleIncome;//销售收入
	this.commodityOverflowIncome=commodityOverflowIncome;//商品报溢收入
	this.costAdjustIncome=costAdjustIncome;//成本调价收入
	this.importAndExportSpreadIncome=importAndExportSpreadIncome;//进货退货差价
	 this.withVoucherIncome=withVoucherIncome;//代金券与实际收款差额收入
	this.rebateIncomeAfter=rebateIncomeAfter;//折让后总收入
	this.rebatePrice=rebatePrice;//折让了多少
	this.saleCost=saleCost;//销售成本
	this.commodityLossCost=commodityLossCost;//商品报损支出
	this.commodityGiftCost=commodityGiftCost;//商品赠出支出
	this.totalEnpense= totalEnpense;//总支出
	this.profit=profit;//利润
}
public double getSaleIncome() {
	return saleIncome;
}
public double getCommodityOverflowIncome() {
	return commodityOverflowIncome;
}
public double getCostAdjustIncome() {
	return costAdjustIncome;
}
public double getImportAndExportSpreadIncome() {
	return importAndExportSpreadIncome;
}
public double getWithVoucherIncome() {
	return withVoucherIncome;
}
public double getRebateIncomeAfter() {
	return rebateIncomeAfter;
}
public double getRebatePrice() {
	return rebatePrice;
}
public double getSaleCost() {
	return saleCost;
}
public double getCommodityLossCost() {
	return commodityLossCost;
}
public double getCommodityGiftCost() {
	return commodityGiftCost;
}
public double getTotalEnpense() {
	return totalEnpense;
}
public double getProfit() {
	return profit;
}
}
