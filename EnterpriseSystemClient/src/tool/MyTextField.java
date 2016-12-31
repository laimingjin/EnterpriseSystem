package tool;

import javax.swing.JTextField;

public class MyTextField {
public 	 JTextField jtextfield;
public 	MyTextField (int x0,int y0,int x1,int y1){
		jtextfield= new JTextField();
		jtextfield.setColumns(10);
		jtextfield.setOpaque(false);
		jtextfield.setBorder(null);
		jtextfield.setBounds(x0, y0, x1, y1);
	}
	
}
