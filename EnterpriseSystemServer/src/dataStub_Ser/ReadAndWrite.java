package dataStub_Ser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ReadAndWrite {

	public Object read(String object_address) {
		FileInputStream fis;
		Object ob = null;
		try {
			fis = new FileInputStream(object_address);
			
//			System.out.println(object_address);
			
			ObjectInputStream ois = new ObjectInputStream(fis);
			ob = ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			//若找不到文件则，选择新建文件（初始化时使用）
			File file = new File(object_address);
			if (!file.exists()) {
				try {
					file.createNewFile();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ob;
	}
	
	public void write(Object ob,String object_address){
	
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(object_address);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(ob);
				
				oos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

}
