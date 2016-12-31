package tool;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class MoveLabel extends JLabel implements Runnable {
	 //1891684760189602720L
	//  private static final long serialVersionUID = 0;
	 
	 // private String text = null;
	 private  ImageIcon image=StaticImage.backOfCircle;
	  private Thread thread = null;
	 
	  private int x =554;
	 
	  private int w = 26, h = 26;
	 
	  public MoveLabel(ImageIcon image) {
	   super(image);
	   this.setIcon(image);
	   //this.text = text;
	   thread = new Thread(this);
	   thread.start();
	   	  }
	 
	/*  public String getText() {
	   return text;
	  }
	 
	  public void setText(String text) {
	   super.setText(text);
	   this.text = text;
	  }
	 
	*/  protected void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   //g.setColor(this.getBackground());
	   //g.fillRect(0, 0, w = this.getWidth(), h = this.getHeight());
	   //g.setColor(this.getForeground());
	   //g.setFont(this.getFont());
	    g.drawImage(image.getImage(),x, h -2, null);
	   //   g.drawString(text, x, h -2);
	  }
	 
	  public void run() {
	   while (true) {
	    x -= 1;
	    if (x < -w) {
	     x = w;
	    }
	   this.repaint();
	    try {
	     Thread.sleep(50);
	    } catch (InterruptedException e) {
	     e.printStackTrace();
	    }
	   }
	  }
	 }