package tool;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author 小春
 *2014年12月12日下午3:39:37
 *EnterpriseSystem  
 *tool 
 *MyFailTipsPanel.java 
 *用来提示失败操作或者成功但是不关闭窗口
 */
public class MyFailTipsPanel extends JPanel{


//	private static final long serialVersionUID = 1L;
	private Frame currentFrame;
	private Image currentImage;
	public MyFailTipsPanel(Frame f,Image image) {
		currentFrame=f;
		initialize();
		setLayout(null);
		currentImage=image;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		this.setSize(175,121);
		JButton mybuButton=new JButton(StaticImage.backOfjbu_click);
		mybuButton.setBounds(122, 93, StaticImage.backOfjbu_click.getIconWidth(), StaticImage.backOfjbu_click.getIconHeight());
		mybuButton.setBorderPainted(false);
		mybuButton.setOpaque(false);
		mybuButton.setVisible(true);
		mybuButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				currentFrame.dispose();		
			}
		});
		this.add(mybuButton);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(currentImage, 0, 0, null);
	}


}
