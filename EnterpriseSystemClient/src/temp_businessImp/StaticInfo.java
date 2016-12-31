package temp_businessImp;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class StaticInfo {

	static BufferedReader reader;
	public static String IP;
	static{
		try {
			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(
							"/Users/mj/documents/workspace/EnterpriseSystemServer/ip.ip"), "UTF-8"));
			IP = reader.readLine();
			
			// TODO
			System.out.println(IP);
			
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public static final String URL_USER = "rmi://" + IP + "/User";
	public static final String URL_ACCOUNT = "rmi://" + IP + "/Account";
	public static final String URL_BUSINESSLIST = "rmi://" + IP
			+ "/BusinessList";
	public static final String URL_BUSINESSPROCESSLIST = "rmi://" + IP
			+ "/BusinessProcessList";
	public static final String URL_RECEIPT = "rmi://" + IP + "/Receipt";
	public static final String URL_PAYMENT = "rmi://" + IP + "/Payment";
	public static final String URL_CASHLIST = "rmi://" + IP + "/CashList";
	public static final String URL_COMMODITY = "rmi://" + IP + "/Commodity";
	public static final String URL_COMMODITYSORT = "rmi://" + IP
			+ "/CommoditySort";
	public static final String URL_COMMODITYGIFT = "rmi://" + IP
			+ "/CommodityGift";
	public static final String URL_COMMODITYDOCUMENT = "rmi://" + IP
			+ "/CommodityDocument";
	public static final String URL_CUSTOMER = "rmi://" + IP + "/Customer";
	public static final String URL_IMPORT = "rmi://" + IP + "/Import";
	public static final String URL_SALE = "rmi://" + IP + "/Sale";
	public static final String URL_SALESLIST = "rmi://" + IP + "/SalesList";
	public static final String URL_SETACCOUNT = "rmi://" + IP + "/SetAccount";
	public static final String URL_EXAMINEANDAPPROVE = "rmi://" + IP
			+ "/ExamineAndApprove";
	public static final String URL_PROMOTION = "rmi://" + IP + "/Promotion";
	public static final String URL_LOG = "rmi://" + IP + "/Log";
	public static final String URL_SNAPSHOT = "rmi://" + IP + "/Snapshot";

	//
	//
	// import java.io.BufferedReader;
	//
	// import java.io.BufferedWriter;
	//
	// import java.io.File;
	//
	// import java.io.FileInputStream;
	//
	// import java.io.FileNotFoundException;
	//
	// import java.io.FileOutputStream;
	//
	// import java.io.IOException;
	//
	// import java.io.InputStreamReader;
	//
	// import java.io.OutputStreamWriter;
	//
	//
	// public class WriterOrReaderTxt {
	//
	// // 写文件
	//
	// private void writerTxt() {
	//
	// BufferedWriter fw = null;
	//
	// try {
	//
	// File file = new File("C://EnterPriseSystem//ip.ip");
	//
	// fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,
	// true), "UTF-8")); // 指定编码格式，以免读取时中文字符异常
	//
	// fw.append("我写入的内容");
	//
	// fw.newLine();
	//
	// fw.append("我又写入的内容");
	//
	// fw.flush(); // 全部写入缓存中的内容
	//
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// } finally {
	//
	// if (fw != null) {
	//
	// try {
	//
	// fw.close();
	//
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	//
	// }
	//
	// }
	//
	// }
	//
	// }
	//
	//
	// // 读文件
	//
	// private void readTxt() {
	//
	// String filePath =
	// WriterOrReaderTxt.class.getResource("").getPath().replace("file:", "")
	//
	// + "/test.txt"; // 文件和该类在同个目录下
	//
	// BufferedReader reader = null;
	//
	// try {
	//
	// reader = new BufferedReader(new InputStreamReader(new
	// FileInputStream(filePath), "UTF-8")); // 指定读取文件的编码格式，要和写入的格式一致，以免出现中文乱码,
	//
	// String str = null;
	//
	// while ((str = reader.readLine()) != null) {
	//
	// System.out.println(str);
	//
	// }
	//
	// } catch (FileNotFoundException e) {
	//
	// e.printStackTrace();
	//
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	//
	// } finally {
	//
	// try {
	//
	// reader.close();
	//
	// } catch (IOException e) {
	//
	// e.printStackTrace();
	//
	// }
	//
	// }
	//
	// }
	//
	// }

}
