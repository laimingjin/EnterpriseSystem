package vo;

public class DocumentVO {
	String type;

	String theDate;
	   String documentID;//单据编号
	   boolean isPass;//是否通过审核
		boolean isSend;//是否已提交审核
	   boolean isDealed;
	   public DocumentVO(String t,String date,String documentID,boolean pass,boolean send,boolean dealed){
		   
		   type=t;
		   theDate=date;
		   this.documentID=documentID;
		   isPass=pass;
		   isSend=send;
		   isDealed=dealed;
	   }
		public String getType() {
			return type;
		}
		public String getTheDate() {
			return theDate;
		}
		public String getDocumentID() {
			return documentID;
		}
		public boolean isPass() {
			return isPass;
		}
		public boolean isSend() {
			return isSend;
		}
		public boolean isDealed() {
			return isDealed;
		}
}
