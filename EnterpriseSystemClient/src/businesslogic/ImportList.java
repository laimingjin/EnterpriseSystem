package businesslogic;

import java.io.Serializable;
import java.util.ArrayList;

public class ImportList implements Serializable {
	private static final long serialVersionUID = 1L;
	ArrayList<ImportLineItem> importLineList = new ArrayList<ImportLineItem>();

	public void addImportLineItem(ImportLineItem theItem) {
		importLineList.add(theItem);
	}

	public double getTotal() {
		double result = 0;
		for (int i = 0; i < importLineList.size(); i++) {
			result = result + importLineList.get(i).getTotal();
		}
		return result;
	}

	public ArrayList<ImportLineItem> getImportLineList() {
		return importLineList;
	}

	public void removeLineItem(int index) {
		importLineList.remove(index);
	}
}
