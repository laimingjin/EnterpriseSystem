package businesslogic;

import java.io.Serializable;

import po.ExamineAndApprovePO;

public class JXCDocument  implements Serializable{

	// TODO
	//此处该为个单据多态方法，时间仓促未实现
	public ExamineAndApprovePO getPO() {
		return new ExamineAndApprovePO(new JXCDocument());
	}

}
