package aboutTree;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import presentation.DocumentProcessUI;
import enumClass.KindOfDocuments;

/**
 * @author 小春
 *2014年12月19日下午8:18:15
 *EnterpriseSystem  
 *aboutTree 
 *DocProTree.java 
*单据审批树
 */
public class DocProTree implements TreeSelectionListener {
private static KindOfDocuments [] kindOfDocuments={KindOfDocuments.IMPORTDOCUMENT,KindOfDocuments.IMPRETURNDOC,
	KindOfDocuments.SALEDOCUMENT,KindOfDocuments.SALERETURNDOC,KindOfDocuments.PAYMENT,KindOfDocuments.RECEIPT,KindOfDocuments.CASHLIST,
	KindOfDocuments.COMMODITYDOCUMENT};
    private Node root=new Node("待审批单据");
    
    private JTree myTree=null;
    private DocProSortNode selectNode=null;
    private DocumentProcessUI callbackPanel;
    
    public DocProTree(DocumentProcessUI panel){
    	callbackPanel=panel;
    }
    
    private void creatTree(){
    	for (int i = 0; i < kindOfDocuments.length; i++) {
			root.add(new DocProSortNode(kindOfDocuments[i]));
		}
    }
    public JTree getDocProTree(){
    	if (myTree==null) {
			creatTree();
			myTree=new JTree(root);
			myTree.addTreeSelectionListener(this);
		}
    	return myTree;
    }
    
    
public DocProSortNode getSelectNode() {
		return selectNode;
	}

public void valueChanged(TreeSelectionEvent e) {
      Object n=e.getPath().getLastPathComponent();
      if (n instanceof DocProSortNode) {
		selectNode=(DocProSortNode) n;
		callbackPanel.creatTabel(selectNode.getKindOfNode());
	}
	
}



}
