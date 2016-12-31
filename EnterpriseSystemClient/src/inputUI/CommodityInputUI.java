package inputUI;

/**
 * 库存管理人员的商品输入
 * @author nancy
 * @version 1.0
 */
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import presentation.CommodityUI;
import temp_business.CommodityBLService;
import temp_business.CommoditySortBLService;
import temp_businessImp.CommodityBLImp;
import temp_businessImp.CommoditySortBLImp;
import tool.MyButton;
import tool.MyFailTipsPanel;
import tool.MyInputFrame;
import tool.MySuccessTipsPanel;
import tool.MyTextField;
import tool.MyTipsFrame;
import tool.StaticImage;
import vo.CommoditySortVO;
import vo.CommodityVO;
import enumClass.ResultMessage;

public class CommodityInputUI extends SuperInputUI {
	/**
	 * 
	 */
//	private static final long serialVersionUID = 1L;
	private MyTextField textCommoditySortName;
	private CommoditySortVO selectedVO;
	private CommodityUI commodityUI;
	private MyTextField textCommodityName, textCommodityType,
			textPurchasePrice, textRetailPrice, textWarnQuantity;
	public static JScrollPane sc;

	enum addtype {
		SON, COMMODITY, BOTH, ERROR
	};

	CommodityBLService commodityBLService=new CommodityBLImp();
	CommoditySortBLService commoditySortBLService=new CommoditySortBLImp();;

	// 传入点击的那个节点的VO
	public CommodityInputUI(CommodityUI myPanel, CommoditySortVO cs, JFrame frame) {
		super(frame);
		selectedVO = cs;
		commodityUI = myPanel;
		this.setSize(MyInputFrame.FRAME_WIDTH, MyInputFrame.FRAME_HEIGHT);
		this.setLayout(null);
		this.initialize(cs);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(CommoditySortVO cs) {
		textCommoditySortName = new MyTextField(27, 146, 192, 35);
		this.add(textCommoditySortName.jtextfield);
		textWarnQuantity = new MyTextField(235, 146, 180, 35);
		this.add(textWarnQuantity.jtextfield);
		textCommodityName = new MyTextField(28, 248, 192, 35);
		this.add(textCommodityName.jtextfield);
		textCommodityType = new MyTextField(238, 248, 180, 35);
		this.add(textCommodityType.jtextfield);
		textPurchasePrice = new MyTextField(27, 345, 192, 35);
		this.add(textPurchasePrice.jtextfield);
		textRetailPrice = new MyTextField(237, 345, 180, 35);
		this.add(textRetailPrice.jtextfield);
		if (cs.getCommodityList() == null
				&& (cs.getCommoditySortSonList() != null)) {
			textCommodityName.jtextfield.setEditable(false);// 设置不可输入
			textCommodityType.jtextfield.setEditable(false);// 设置不可输入
			textPurchasePrice.jtextfield.setEditable(false);// 设置不可输入
			textRetailPrice.jtextfield.setEditable(false);// 设置不可输入
			textWarnQuantity.jtextfield.setEditable(false);
		} else if (cs.getCommodityList() != null
				&& (cs.getCommoditySortSonList() == null)) {
			textCommoditySortName.jtextfield.setEditable(false);// 设置不可输入
		} else if (cs.getCommodityList() == null
				&& (cs.getCommoditySortSonList() == null)) {
		} else {
		}

		MyButton submit = new MyButton(StaticImage.backOfSubmit, 177, 467, 120,
				45);
		this.add(submit.jbutton);
		MyButton cancel=new MyButton(StaticImage.backOfSmallCancel, 422, 5, 24, 24);
		//关闭建
				cancel.jbutton.addActionListener(new ActionListener() {//监听	
					public void actionPerformed(ActionEvent e) {



		         			closeFrame();

					
					}
				});
				cancel.jbutton.setVisible(true);
		this.add(cancel.jbutton);
		(submit.jbutton).addActionListener(new ButtonListener(this));
	}

	class ButtonListener implements ActionListener {
		private CommodityInputUI currentPanel;

		public ButtonListener(CommodityInputUI panel) {
			currentPanel = panel;
		}

		public void actionPerformed(ActionEvent e) {
			String commoditySort = textCommoditySortName.jtextfield.getText();
			/**
			 * 一处调用@@@@@@@author nancy
			 */
            
			// ID需要调用方法得到
			CommoditySortVO commoditySortVO = commoditySortBLService
					.getCommoditySortbyName(selectedVO.getCommoditySortName());
			int commoditySortID = commoditySortVO.getCommoditySortID();

			// ID需要调用方法得到
			int commodityID = commodityBLService.getNewCommodityID();
			// 如果商品分类是空的没有输入，就是说是那个新增商品而不是新增商品分类
			if (commoditySort.equals("")) {
				// 获得下面输入的东西
				String commodityName = textCommodityName.jtextfield.getText();
				String commodityModel = textCommodityType.jtextfield.getText();
				double purchasePrice = Double
						.parseDouble(textPurchasePrice.jtextfield.getText());
				double retailPrice = Double
						.parseDouble(textRetailPrice.jtextfield.getText());
				int warnQuantity = Integer.parseInt(textWarnQuantity.jtextfield
						.getText());
				// 创建一个新的vo
				CommodityVO newCommodity = new CommodityVO(
						selectedVO.getCommoditySortName(), commoditySortID,
						commodityName, commodityID, commodityModel, 0,
						purchasePrice, retailPrice, 0.0, 0.0, "20141217", 0.0,
						warnQuantity);
			
				ArrayList<CommodityVO> commodityList = selectedVO
						.getCommodityList();
				commodityList.add(newCommodity);
				/**
				 * 一处调用@@@@@@@author nancy
				 */

				ResultMessage resultMessage = commodityBLService
						.addCommodity(newCommodity);
				if (resultMessage.equals(ResultMessage.SUCCESS)) {
					commodityUI.addCommodity(newCommodity);
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
					
				} else if (resultMessage
						.equals(ResultMessage.CommodityIsAlreadyExist)) {
					// 无法添加：因为已经存在的
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfCommodityIsAlreadyExist
									.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {
					// 无法添加
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFailToAddDocument.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}

			} else {
				// 如果商品分类不是空的，就是说是那个新增商品分类而不是新增商品
				// 创一个新的commoditySortvo
				CommoditySortVO newCommoditySortVO = new CommoditySortVO(
						commoditySort, commoditySortID,
						selectedVO.getCommoditySortName(), false, null, null);
				ArrayList<CommoditySortVO> commoditySortSonList = selectedVO
						.getCommoditySortSonList();
				commoditySortSonList.add(newCommoditySortVO);
				/**
				 * 一处调用@@@@@@@author nancy
				 */
				ResultMessage resultMessage = commoditySortBLService
						.addCommoditySort(newCommoditySortVO);
				if (resultMessage.equals(ResultMessage.SUCCESS)) {
					commodityUI.addCommoditySort(newCommoditySortVO);
					MyTipsFrame mtf = new MyTipsFrame();
					MySuccessTipsPanel mtPanel=new MySuccessTipsPanel(mtf, StaticImage.backOfSuccess.getImage(),currentPanel);
					mtf.getIni(mtPanel);	
					
				} else if (resultMessage
						.equals(ResultMessage.CommoditySortIsAlreadyExist)) {
					// 无法添加
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfCommoditySortIsAlreadyExist
									.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				} else {
					// 无法添加
					MyTipsFrame myTipsFrame = new MyTipsFrame();
					MyFailTipsPanel myFailTipsPanel = new MyFailTipsPanel(
							myTipsFrame,
							StaticImage.backOfFailToAddDocument.getImage());
					myTipsFrame.getIni(myFailTipsPanel);
				}

			}

		}

	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(StaticImage.backOfCommodityInput.getImage(), 0, 0, null);
	}

}
