package aboutTree;

import enumClass.KindOfDocuments;

public class DocProSortNode extends Node{
	//private static final long serialVersionUID = 1L;
	KindOfDocuments kindOfNode;
    String nodeName;
    public DocProSortNode(KindOfDocuments kind){
        kindOfNode=kind;
      getNameInit();
    }
    private void getNameInit(){
    	switch (kindOfNode) {
		case IMPORTDOCUMENT:
			nodeName="进货单";
			break;
		case IMPRETURNDOC:
			nodeName="进货退货单";
			break;
		case SALEDOCUMENT:
				nodeName="销售单";
				break;
		case SALERETURNDOC:
			nodeName="销售退货单";
			break;
		case PAYMENT:
			nodeName="付款单";
			break;
		case RECEIPT:
			nodeName="收款单";
			break;
		case CASHLIST:
		nodeName="现金费用单";
	    	break;
		case COMMODITYDOCUMENT:
			nodeName="库存问题单据";
			break;
		default:
			System.out.println("Error!");
			break;
		}
    }

    
    public KindOfDocuments getKindOfNode() {
		return kindOfNode;
	}
	@Override
	public String toString() {
		return  nodeName;
	}

}
