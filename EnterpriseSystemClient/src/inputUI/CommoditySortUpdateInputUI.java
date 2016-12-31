package inputUI;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import presentation.CommodityUI;
import temp_business.CommoditySortBLService;
import temp_businessImp.CommoditySortBLImp;
import tool.MyTextField;
import tool.StaticImage;
import vo.CommoditySortVO;

public class CommoditySortUpdateInputUI extends JPanel{


	//private static final long serialVersionUID = 1L;
	private CommoditySortVO selectedSortVO;
	private Frame currentFrame;
	private Image currentImage;
	private MyTextField textCommoditySortName;
	CommoditySortBLService commoditySortBLService=new CommoditySortBLImp();
	private int sortID;
	public CommoditySortUpdateInputUI(CommodityUI myPanel,CommoditySortVO cs,Frame f,Image image) {
		currentFrame=f;
		selectedSortVO= cs;
		initialize(cs);
		setLayout(null);
		currentImage=image;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(CommoditySortVO cs) {
		
		this.setSize(175,121);
		textCommoditySortName = new MyTextField(29, 52, 122, 35);
		this.add(textCommoditySortName.jtextfield);
	    textCommoditySortName.jtextfield.setText(cs.getCommoditySortName());
		JButton mybuButton=new JButton(StaticImage.backOfjbu_click);
		mybuButton.setBounds(122, 93, StaticImage.backOfjbu_click.getIconWidth(), StaticImage.backOfjbu_click.getIconHeight());
		mybuButton.setBorderPainted(false);
		mybuButton.setOpaque(false);
		mybuButton.setVisible(true);
		CommoditySortVO commoditySortVO=commoditySortBLService.getCommoditySortbyName(cs.getCommoditySortName());
	 sortID=commoditySortVO.getCommoditySortID();
		mybuButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//先通过相与商品名找到ID，更在之后再传回去
				
				 String newSortName=   textCommoditySortName.jtextfield.getText();
				 selectedSortVO.setCommoditySortName(newSortName);
				commoditySortBLService.updateCommoditySort(sortID, newSortName);
				
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
