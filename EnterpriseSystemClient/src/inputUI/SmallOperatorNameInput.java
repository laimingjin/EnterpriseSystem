package inputUI;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import presentation.DairyCheckUI;
import temp_business.LogBLService;
import temp_businessImp.LogBLImp;
import tool.MyTextField;
import tool.StaticImage;
import vo.LogVO;

public class SmallOperatorNameInput extends JPanel {

	//private static final long serialVersionUID = 1L;
	private Frame currentFrame;
	private Image currentImage;
	private DairyCheckUI dairyCheckUI;
	private MyTextField textCommoditySortName;
	private LogBLService logBLService=new LogBLImp();

	public SmallOperatorNameInput(DairyCheckUI myPanel, Frame f, Image image) {
		currentFrame = f;
		dairyCheckUI = myPanel;
		initialize();
		setLayout(null);
		currentImage = image;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setSize(175, 121);
		textCommoditySortName = new MyTextField(29, 52, 122, 35);
		this.add(textCommoditySortName.jtextfield);

		JButton mybuButton = new JButton(StaticImage.backOfjbu_click);
		mybuButton.setBounds(122, 93,
				StaticImage.backOfjbu_click.getIconWidth(),
				StaticImage.backOfjbu_click.getIconHeight());
		mybuButton.setBorderPainted(false);
		mybuButton.setOpaque(false);
		mybuButton.setVisible(true);

		mybuButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 先通过相与商品名找到ID，更在之后再传回去

				String operstorName = textCommoditySortName.jtextfield
						.getText();
				ArrayList<LogVO> logVOs=logBLService.display(operstorName);
				dairyCheckUI.showSearchInfo(logVOs);
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
