package tool;

import inputUI.SuperInputUI;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MySuccessTipsPanel extends JPanel{

	//private static final long serialVersionUID = 1L;
	private Frame currentFrame;
	private Image i;
	SuperInputUI currentPanelInputUI;
	public MySuccessTipsPanel(Frame f,Image image,SuperInputUI panel) {//这只是为了找到使用它的UI
		currentPanelInputUI=panel;
		currentFrame=f;
		initialize();
		setLayout(null);
		i=image;
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
				currentPanelInputUI.closeFrame();
			}
		});
		this.add(mybuButton);
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(i, 0, 0, null);
	}

}
