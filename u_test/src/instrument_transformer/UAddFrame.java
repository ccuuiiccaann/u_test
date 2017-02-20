package instrument_transformer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * 电压互感器，点击“新增”是弹出的页面
 * @author cuican
 *
 */
public class UAddFrame extends JFrame {

	private static final long serialVersionUID = -4861680976409115282L;
	private JTextField huMing;
	private JTextField huiLuMingCheng;
	private JTextField changMing_a;
	private JTextField changMing_b;
	private JTextField changMing_c;
	private JTextField xingShi_a;
	private JTextField xingShi_b;
	private JTextField xingShi_c;
	private JTextField changHao_a;
	private JTextField changHao_b;
	private JTextField changHao_c;
	private JTextField eDingRongLiang;
	private JTextField jiXing;
	private JTextField bianBi;
	private JTextField zhunQueDengJi;
	private JTextField chuChangNianYue;
	private JTextField dianYa;
	private JTextField pinLv;
	private JTextField juBianHao_a;
	private JTextField juBianHao_b;
	private JTextField juBianHao_c;
	private JTextField zhengShuBianHao;
	private JTextField ceShiRen;
	private JTextField ceShiRiQi;
	private JTextField ceShiJieLun;

	/**
	 * Create the frame.
	 */
	public UAddFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				MainFrame.uFrame.setVisible(true);
			}
		});
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 500);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100,100,100,584};
		gridBagLayout.rowHeights = new int[]{20,20,30};
		gridBagLayout.columnWeights = new double[]{1.0,1.0,1,1.0};
		gridBagLayout.rowWeights = new double[]{0.1,0.1,5.0};
		getContentPane().setLayout(gridBagLayout);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridwidth = 4;
		gbc_tabbedPane.gridheight = 3;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 0;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel panel_base = new JPanel();
		tabbedPane.addTab("请输入基本信息", null, panel_base, null);
		GridBagLayout gbl_panel_base = new GridBagLayout();
		gbl_panel_base.columnWidths = new int[]{45, 0,80,0,80,0,0,60};
		gbl_panel_base.rowHeights = new int[]{0, 0,0,0,0,0,0,0,0,0};
		gbl_panel_base.columnWeights = new double[]{1.0,1.0,1.0,1.0,1.0,1.0,1.0,1.0};
		gbl_panel_base.rowWeights = new double[]{1,1.0,1.0,1.0,1,1.0,1,1.0,1.2,1.5};
		panel_base.setLayout(gbl_panel_base);
		
		JLabel lblNewLabel_4 = new JLabel("户名");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridwidth = 2;
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 0;
		panel_base.add(lblNewLabel_4, gbc_lblNewLabel_4);
		
		huMing = new JTextField();
		GridBagConstraints gbc_huMing = new GridBagConstraints();
		gbc_huMing.gridwidth = 2;
		gbc_huMing.insets = new Insets(0, 0, 5, 5);
		gbc_huMing.fill = GridBagConstraints.HORIZONTAL;
		gbc_huMing.gridx = 2;
		gbc_huMing.gridy = 0;
		panel_base.add(huMing, gbc_huMing);
		huMing.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("回路名称");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.gridwidth = 2;
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 4;
		gbc_lblNewLabel_5.gridy = 0;
		panel_base.add(lblNewLabel_5, gbc_lblNewLabel_5);
		
		huiLuMingCheng = new JTextField();
		GridBagConstraints gbc_huiLuMingCheng = new GridBagConstraints();
		gbc_huiLuMingCheng.gridwidth = 2;
		gbc_huiLuMingCheng.insets = new Insets(0, 0, 5, 0);
		gbc_huiLuMingCheng.fill = GridBagConstraints.HORIZONTAL;
		gbc_huiLuMingCheng.gridx = 6;
		gbc_huiLuMingCheng.gridy = 0;
		panel_base.add(huiLuMingCheng, gbc_huiLuMingCheng);
		huiLuMingCheng.setColumns(10);
		
		JLabel lblA = new JLabel("A");
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 0;
		gbc_lblA.gridy = 1;
		panel_base.add(lblA, gbc_lblA);
		
		JLabel lblNewLabel_8 = new JLabel("厂名");
		GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
		gbc_lblNewLabel_8.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_8.gridx = 1;
		gbc_lblNewLabel_8.gridy = 1;
		panel_base.add(lblNewLabel_8, gbc_lblNewLabel_8);
		
		changMing_a = new JTextField();
		GridBagConstraints gbc_changMing_a = new GridBagConstraints();
		gbc_changMing_a.insets = new Insets(0, 0, 5, 5);
		gbc_changMing_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_changMing_a.gridx = 2;
		gbc_changMing_a.gridy = 1;
		panel_base.add(changMing_a, gbc_changMing_a);
		changMing_a.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("型式");
		GridBagConstraints gbc_lblNewLabel_11 = new GridBagConstraints();
		gbc_lblNewLabel_11.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_11.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_11.gridx = 3;
		gbc_lblNewLabel_11.gridy = 1;
		panel_base.add(lblNewLabel_11, gbc_lblNewLabel_11);
		
		xingShi_a = new JTextField();
		GridBagConstraints gbc_xingShi_a = new GridBagConstraints();
		gbc_xingShi_a.insets = new Insets(0, 0, 5, 5);
		gbc_xingShi_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_xingShi_a.gridx = 4;
		gbc_xingShi_a.gridy = 1;
		panel_base.add(xingShi_a, gbc_xingShi_a);
		xingShi_a.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("厂号");
		GridBagConstraints gbc_lblNewLabel_14 = new GridBagConstraints();
		gbc_lblNewLabel_14.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_14.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_14.gridx = 5;
		gbc_lblNewLabel_14.gridy = 1;
		panel_base.add(lblNewLabel_14, gbc_lblNewLabel_14);
		
		changHao_a = new JTextField();
		GridBagConstraints gbc_changHao_a = new GridBagConstraints();
		gbc_changHao_a.gridwidth = 2;
		gbc_changHao_a.insets = new Insets(0, 0, 5, 0);
		gbc_changHao_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_changHao_a.gridx = 6;
		gbc_changHao_a.gridy = 1;
		panel_base.add(changHao_a, gbc_changHao_a);
		changHao_a.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("B");
		GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
		gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_6.gridx = 0;
		gbc_lblNewLabel_6.gridy = 2;
		panel_base.add(lblNewLabel_6, gbc_lblNewLabel_6);
		
		JLabel lblNewLabel_9 = new JLabel("厂名");
		GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
		gbc_lblNewLabel_9.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_9.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_9.gridx = 1;
		gbc_lblNewLabel_9.gridy = 2;
		panel_base.add(lblNewLabel_9, gbc_lblNewLabel_9);
		
		changMing_b = new JTextField();
		GridBagConstraints gbc_changMing_b = new GridBagConstraints();
		gbc_changMing_b.insets = new Insets(0, 0, 5, 5);
		gbc_changMing_b.fill = GridBagConstraints.HORIZONTAL;
		gbc_changMing_b.gridx = 2;
		gbc_changMing_b.gridy = 2;
		panel_base.add(changMing_b, gbc_changMing_b);
		changMing_b.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("型式");
		GridBagConstraints gbc_lblNewLabel_12 = new GridBagConstraints();
		gbc_lblNewLabel_12.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_12.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_12.gridx = 3;
		gbc_lblNewLabel_12.gridy = 2;
		panel_base.add(lblNewLabel_12, gbc_lblNewLabel_12);
		
		xingShi_b = new JTextField();
		GridBagConstraints gbc_xingShi_b = new GridBagConstraints();
		gbc_xingShi_b.insets = new Insets(0, 0, 5, 5);
		gbc_xingShi_b.fill = GridBagConstraints.HORIZONTAL;
		gbc_xingShi_b.gridx = 4;
		gbc_xingShi_b.gridy = 2;
		panel_base.add(xingShi_b, gbc_xingShi_b);
		xingShi_b.setColumns(10);
		
		JLabel lblNewLabel_15 = new JLabel("厂号");
		GridBagConstraints gbc_lblNewLabel_15 = new GridBagConstraints();
		gbc_lblNewLabel_15.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_15.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_15.gridx = 5;
		gbc_lblNewLabel_15.gridy = 2;
		panel_base.add(lblNewLabel_15, gbc_lblNewLabel_15);
		
		changHao_b = new JTextField();
		GridBagConstraints gbc_changHao_b = new GridBagConstraints();
		gbc_changHao_b.gridwidth = 2;
		gbc_changHao_b.insets = new Insets(0, 0, 5, 0);
		gbc_changHao_b.fill = GridBagConstraints.HORIZONTAL;
		gbc_changHao_b.gridx = 6;
		gbc_changHao_b.gridy = 2;
		panel_base.add(changHao_b, gbc_changHao_b);
		changHao_b.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("C");
		GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
		gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_7.gridx = 0;
		gbc_lblNewLabel_7.gridy = 3;
		panel_base.add(lblNewLabel_7, gbc_lblNewLabel_7);
		
		JLabel lblNewLabel_10 = new JLabel("厂名");
		GridBagConstraints gbc_lblNewLabel_10 = new GridBagConstraints();
		gbc_lblNewLabel_10.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_10.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_10.gridx = 1;
		gbc_lblNewLabel_10.gridy = 3;
		panel_base.add(lblNewLabel_10, gbc_lblNewLabel_10);
		
		changMing_c = new JTextField();
		GridBagConstraints gbc_changMing_c = new GridBagConstraints();
		gbc_changMing_c.insets = new Insets(0, 0, 5, 5);
		gbc_changMing_c.fill = GridBagConstraints.HORIZONTAL;
		gbc_changMing_c.gridx = 2;
		gbc_changMing_c.gridy = 3;
		panel_base.add(changMing_c, gbc_changMing_c);
		changMing_c.setColumns(10);
		
		JLabel lblNewLabel_13 = new JLabel("型式");
		GridBagConstraints gbc_lblNewLabel_13 = new GridBagConstraints();
		gbc_lblNewLabel_13.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_13.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_13.gridx = 3;
		gbc_lblNewLabel_13.gridy = 3;
		panel_base.add(lblNewLabel_13, gbc_lblNewLabel_13);
		
		xingShi_c = new JTextField();
		GridBagConstraints gbc_xingShi_c = new GridBagConstraints();
		gbc_xingShi_c.insets = new Insets(0, 0, 5, 5);
		gbc_xingShi_c.fill = GridBagConstraints.HORIZONTAL;
		gbc_xingShi_c.gridx = 4;
		gbc_xingShi_c.gridy = 3;
		panel_base.add(xingShi_c, gbc_xingShi_c);
		xingShi_c.setColumns(10);
		
		JLabel lblNewLabel_16 = new JLabel("厂号");
		GridBagConstraints gbc_lblNewLabel_16 = new GridBagConstraints();
		gbc_lblNewLabel_16.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_16.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_16.gridx = 5;
		gbc_lblNewLabel_16.gridy = 3;
		panel_base.add(lblNewLabel_16, gbc_lblNewLabel_16);
		
		changHao_c = new JTextField();
		GridBagConstraints gbc_changHao_c = new GridBagConstraints();
		gbc_changHao_c.gridwidth = 2;
		gbc_changHao_c.insets = new Insets(0, 0, 5, 0);
		gbc_changHao_c.fill = GridBagConstraints.HORIZONTAL;
		gbc_changHao_c.gridx = 6;
		gbc_changHao_c.gridy = 3;
		panel_base.add(changHao_c, gbc_changHao_c);
		changHao_c.setColumns(10);
		
		JLabel lblNewLabel_17 = new JLabel("额定容量");
		GridBagConstraints gbc_lblNewLabel_17 = new GridBagConstraints();
		gbc_lblNewLabel_17.gridwidth = 2;
		gbc_lblNewLabel_17.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_17.gridx = 0;
		gbc_lblNewLabel_17.gridy = 4;
		panel_base.add(lblNewLabel_17, gbc_lblNewLabel_17);
		
		eDingRongLiang = new JTextField();
		GridBagConstraints gbc_eDingRongLiang = new GridBagConstraints();
		gbc_eDingRongLiang.insets = new Insets(0, 0, 5, 5);
		gbc_eDingRongLiang.fill = GridBagConstraints.HORIZONTAL;
		gbc_eDingRongLiang.gridx = 2;
		gbc_eDingRongLiang.gridy = 4;
		panel_base.add(eDingRongLiang, gbc_eDingRongLiang);
		eDingRongLiang.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("极性");
		GridBagConstraints gbc_lblNewLabel_18 = new GridBagConstraints();
		gbc_lblNewLabel_18.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_18.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_18.gridx = 3;
		gbc_lblNewLabel_18.gridy = 4;
		panel_base.add(lblNewLabel_18, gbc_lblNewLabel_18);
		
		jiXing = new JTextField();
		GridBagConstraints gbc_jiXing = new GridBagConstraints();
		gbc_jiXing.insets = new Insets(0, 0, 5, 5);
		gbc_jiXing.fill = GridBagConstraints.HORIZONTAL;
		gbc_jiXing.gridx = 4;
		gbc_jiXing.gridy = 4;
		panel_base.add(jiXing, gbc_jiXing);
		jiXing.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("变比");
		GridBagConstraints gbc_lblNewLabel_19 = new GridBagConstraints();
		gbc_lblNewLabel_19.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_19.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_19.gridx = 5;
		gbc_lblNewLabel_19.gridy = 4;
		panel_base.add(lblNewLabel_19, gbc_lblNewLabel_19);
		
		bianBi = new JTextField();
		GridBagConstraints gbc_bianBi = new GridBagConstraints();
		gbc_bianBi.gridwidth = 2;
		gbc_bianBi.insets = new Insets(0, 0, 5, 0);
		gbc_bianBi.fill = GridBagConstraints.HORIZONTAL;
		gbc_bianBi.gridx = 6;
		gbc_bianBi.gridy = 4;
		panel_base.add(bianBi, gbc_bianBi);
		bianBi.setColumns(10);
		
		JLabel lblNewLabel_20 = new JLabel("准确等级");
		GridBagConstraints gbc_lblNewLabel_20 = new GridBagConstraints();
		gbc_lblNewLabel_20.gridwidth = 2;
		gbc_lblNewLabel_20.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_20.gridx = 0;
		gbc_lblNewLabel_20.gridy = 5;
		panel_base.add(lblNewLabel_20, gbc_lblNewLabel_20);
		
		zhunQueDengJi = new JTextField();
		GridBagConstraints gbc_zhunQueDengJi = new GridBagConstraints();
		gbc_zhunQueDengJi.insets = new Insets(0, 0, 5, 5);
		gbc_zhunQueDengJi.fill = GridBagConstraints.HORIZONTAL;
		gbc_zhunQueDengJi.gridx = 2;
		gbc_zhunQueDengJi.gridy = 5;
		panel_base.add(zhunQueDengJi, gbc_zhunQueDengJi);
		zhunQueDengJi.setColumns(10);
		
		JLabel lblNewLabel_21 = new JLabel("出厂年月");
		GridBagConstraints gbc_lblNewLabel_21 = new GridBagConstraints();
		gbc_lblNewLabel_21.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_21.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_21.gridx = 3;
		gbc_lblNewLabel_21.gridy = 5;
		panel_base.add(lblNewLabel_21, gbc_lblNewLabel_21);
		
		chuChangNianYue = new JTextField();
		GridBagConstraints gbc_chuChangNianYue = new GridBagConstraints();
		gbc_chuChangNianYue.insets = new Insets(0, 0, 5, 5);
		gbc_chuChangNianYue.fill = GridBagConstraints.HORIZONTAL;
		gbc_chuChangNianYue.gridx = 4;
		gbc_chuChangNianYue.gridy = 5;
		panel_base.add(chuChangNianYue, gbc_chuChangNianYue);
		chuChangNianYue.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY, 3));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridheight = 3;
		gbc_panel_4.gridwidth = 3;
		gbc_panel_4.insets = new Insets(0, 0, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 5;
		gbc_panel_4.gridy = 5;
		panel_base.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{28,10,46};
		gbl_panel_4.rowHeights = new int[]{28,28,28};
		gbl_panel_4.columnWeights = new double[]{1,0.5,1.0};
		gbl_panel_4.rowWeights = new double[]{1,1,1};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblNewLabel_22 = new JLabel("局编号");
		GridBagConstraints gbc_lblNewLabel_22 = new GridBagConstraints();
		gbc_lblNewLabel_22.gridheight = 3;
		gbc_lblNewLabel_22.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_22.gridx = 0;
		gbc_lblNewLabel_22.gridy = 0;
		panel_4.add(lblNewLabel_22, gbc_lblNewLabel_22);
		
		JLabel lblNewLabel_25 = new JLabel("A");
		GridBagConstraints gbc_lblNewLabel_25 = new GridBagConstraints();
		gbc_lblNewLabel_25.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_25.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_25.gridx = 1;
		gbc_lblNewLabel_25.gridy = 0;
		panel_4.add(lblNewLabel_25, gbc_lblNewLabel_25);
		
		juBianHao_a = new JTextField();
		GridBagConstraints gbc_juBianHao_a = new GridBagConstraints();
		gbc_juBianHao_a.insets = new Insets(0, 0, 5, 0);
		gbc_juBianHao_a.fill = GridBagConstraints.HORIZONTAL;
		gbc_juBianHao_a.gridx = 2;
		gbc_juBianHao_a.gridy = 0;
		panel_4.add(juBianHao_a, gbc_juBianHao_a);
		juBianHao_a.setColumns(10);
		
		JLabel lblNewLabel_26 = new JLabel("B");
		GridBagConstraints gbc_lblNewLabel_26 = new GridBagConstraints();
		gbc_lblNewLabel_26.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_26.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_26.gridx = 1;
		gbc_lblNewLabel_26.gridy = 1;
		panel_4.add(lblNewLabel_26, gbc_lblNewLabel_26);
		
		juBianHao_b = new JTextField();
		GridBagConstraints gbc_juBianHao_b = new GridBagConstraints();
		gbc_juBianHao_b.insets = new Insets(0, 0, 5, 0);
		gbc_juBianHao_b.fill = GridBagConstraints.HORIZONTAL;
		gbc_juBianHao_b.gridx = 2;
		gbc_juBianHao_b.gridy = 1;
		panel_4.add(juBianHao_b, gbc_juBianHao_b);
		juBianHao_b.setColumns(10);
		
		JLabel lblNewLabel_27 = new JLabel("C");
		GridBagConstraints gbc_lblNewLabel_27 = new GridBagConstraints();
		gbc_lblNewLabel_27.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_27.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_27.gridx = 1;
		gbc_lblNewLabel_27.gridy = 2;
		panel_4.add(lblNewLabel_27, gbc_lblNewLabel_27);
		
		juBianHao_c = new JTextField();
		GridBagConstraints gbc_juBianHao_c = new GridBagConstraints();
		gbc_juBianHao_c.fill = GridBagConstraints.HORIZONTAL;
		gbc_juBianHao_c.gridx = 2;
		gbc_juBianHao_c.gridy = 2;
		panel_4.add(juBianHao_c, gbc_juBianHao_c);
		juBianHao_c.setColumns(10);
		
		JLabel lblNewLabel_23 = new JLabel("电压");
		GridBagConstraints gbc_lblNewLabel_23 = new GridBagConstraints();
		gbc_lblNewLabel_23.gridwidth = 2;
		gbc_lblNewLabel_23.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_23.gridx = 0;
		gbc_lblNewLabel_23.gridy = 6;
		panel_base.add(lblNewLabel_23, gbc_lblNewLabel_23);
		
		dianYa = new JTextField();
		GridBagConstraints gbc_dianYa = new GridBagConstraints();
		gbc_dianYa.insets = new Insets(0, 0, 5, 5);
		gbc_dianYa.fill = GridBagConstraints.HORIZONTAL;
		gbc_dianYa.gridx = 2;
		gbc_dianYa.gridy = 6;
		panel_base.add(dianYa, gbc_dianYa);
		dianYa.setColumns(10);
		
		JLabel lblNewLabel_24 = new JLabel("频率");
		GridBagConstraints gbc_lblNewLabel_24 = new GridBagConstraints();
		gbc_lblNewLabel_24.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_24.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_24.gridx = 3;
		gbc_lblNewLabel_24.gridy = 6;
		panel_base.add(lblNewLabel_24, gbc_lblNewLabel_24);
		
		pinLv = new JTextField();
		GridBagConstraints gbc_pinLv = new GridBagConstraints();
		gbc_pinLv.insets = new Insets(0, 0, 5, 5);
		gbc_pinLv.fill = GridBagConstraints.HORIZONTAL;
		gbc_pinLv.gridx = 4;
		gbc_pinLv.gridy = 6;
		panel_base.add(pinLv, gbc_pinLv);
		pinLv.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("测试编号");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 7;
		panel_base.add(lblNewLabel, gbc_lblNewLabel);
		
		zhengShuBianHao = new JTextField();
		GridBagConstraints gbc_zhengShuBianHao = new GridBagConstraints();
		gbc_zhengShuBianHao.insets = new Insets(0, 0, 5, 5);
		gbc_zhengShuBianHao.fill = GridBagConstraints.HORIZONTAL;
		gbc_zhengShuBianHao.gridx = 2;
		gbc_zhengShuBianHao.gridy = 7;
		panel_base.add(zhengShuBianHao, gbc_zhengShuBianHao);
		zhengShuBianHao.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("测试人");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 8;
		panel_base.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		ceShiRen = new JTextField();
		GridBagConstraints gbc_ceShiRen = new GridBagConstraints();
		gbc_ceShiRen.insets = new Insets(0, 0, 5, 5);
		gbc_ceShiRen.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiRen.gridx = 1;
		gbc_ceShiRen.gridy = 8;
		panel_base.add(ceShiRen, gbc_ceShiRen);
		ceShiRen.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("测试日期");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 2;
		gbc_lblNewLabel_2.gridy = 8;
		panel_base.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		ceShiRiQi = new JTextField();
		GridBagConstraints gbc_ceShiRiQi = new GridBagConstraints();
		gbc_ceShiRiQi.insets = new Insets(0, 0, 5, 5);
		gbc_ceShiRiQi.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiRiQi.gridx = 3;
		gbc_ceShiRiQi.gridy = 8;
		panel_base.add(ceShiRiQi, gbc_ceShiRiQi);
		ceShiRiQi.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("测试结论");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 4;
		gbc_lblNewLabel_3.gridy = 8;
		panel_base.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		ceShiJieLun = new JTextField();
		GridBagConstraints gbc_ceShiJieLun = new GridBagConstraints();
		gbc_ceShiJieLun.gridwidth = 3;
		gbc_ceShiJieLun.insets = new Insets(0, 0, 5, 5);
		gbc_ceShiJieLun.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiJieLun.gridx = 5;
		gbc_ceShiJieLun.gridy = 8;
		panel_base.add(ceShiJieLun, gbc_ceShiJieLun);
		ceShiJieLun.setColumns(10);
		
		JButton saveBtn = new JButton("保存");
		saveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Map<String, String> map=new HashMap<>();
				map.put("huMing", huMing.getText());
				map.put("huiLuMingCheng", huiLuMingCheng.getText());
				map.put("changMing_a", changMing_a.getText());
				map.put("changMing_b", changMing_b.getText());
				map.put("changMing_c", changMing_c.getText());
				map.put("xingShi_a", xingShi_a.getText());
				map.put("xingShi_b", xingShi_b.getText());
				map.put("xingShi_c", xingShi_c.getText());
				map.put("changHao_a", changHao_a.getText());
				map.put("changHao_b", changHao_b.getText());
				map.put("changHao_c", changHao_c.getText());
				map.put("eDingRongLiang", eDingRongLiang.getText());
				map.put("jiXing", jiXing.getText());
				map.put("bianBi", bianBi.getText());
				map.put("zhunQueDengJi", zhunQueDengJi.getText());
				map.put("chuChangNianYue", chuChangNianYue.getText());
				map.put("dianYa", dianYa.getText());
				map.put("pinLv", pinLv.getText());
				map.put("juBianHao_a", juBianHao_a.getText());
				map.put("juBianHao_b", juBianHao_b.getText());
				map.put("juBianHao_c", juBianHao_c.getText());
				map.put("zhengShuBianHao",zhengShuBianHao.getText());
				map.put("ceShiRen", ceShiRen.getText());
				map.put("ceShiRiQi", ceShiRiQi.getText());
				map.put("ceShiJieLun", ceShiJieLun.getText());
				String b=UBaseInfo.addUBaseInfo(map);
				if(Constant.SUCCESS.equals(b)){
					JOptionPane.showMessageDialog(null, "保存成功。");
					setVisible(false);
					MainFrame.uFrame.setVisible(true);
					if(MainFrame.uFrame.uBaseTable!=null){
						MainFrame.uFrame.uBaseTable.setModel(UBaseInfo.getUTableData());
					}
				}else if(Constant.FAILED.equals(b)) {
					JOptionPane.showMessageDialog(null, "保存失败！","错误",JOptionPane.ERROR_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, b,"警告",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_saveBtn = new GridBagConstraints();
		gbc_saveBtn.insets = new Insets(0, 0, 0, 5);
		gbc_saveBtn.gridx = 3;
		gbc_saveBtn.gridy = 9;
		panel_base.add(saveBtn, gbc_saveBtn);
		
		JButton cancelBtn = new JButton("取消");
		cancelBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				MainFrame.uFrame.setVisible(true);
			}
		});
		GridBagConstraints gbc_cancelBtn = new GridBagConstraints();
		gbc_cancelBtn.insets = new Insets(0, 0, 0, 5);
		gbc_cancelBtn.gridx = 4;
		gbc_cancelBtn.gridy = 9;
		panel_base.add(cancelBtn, gbc_cancelBtn);
		
	}
	
	

}
