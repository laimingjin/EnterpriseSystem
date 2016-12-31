package inputUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SuperInputUI extends JPanel{

	//private static final long serialVersionUID = 1L;
    public JFrame currentFrame;
public SuperInputUI(JFrame frame){
	currentFrame=frame;
}
public void closeFrame(){
	currentFrame.dispose();
}
}
