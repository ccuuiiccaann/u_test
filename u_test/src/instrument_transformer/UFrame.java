package instrument_transformer;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class UFrame extends JFrame {

	private static final long serialVersionUID = -7263015389943520596L;
	public JTable uBaseTable;
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
	private JTable table_ab;
	private JTable table_ca;
	private JTable table_bc;
	/**
	 * 新增时弹出的frame
	 */
	private static  UAddFrame uAddFrame;
	private JTextField zhengShuBianHao;
	private JTextField ceShiRen;
	private JTextField ceShiRiQi;
	private JTextField ceShiJieLun;

	/**
	 * 初始化
	 * 1.新增时弹出的uAddFrame，并设置为不显示，需要时直接显示即可，不用重复new
	 */
	public static  void init(){
		if(uAddFrame==null){
			uAddFrame=new UAddFrame();
		}
		uAddFrame.setVisible(false);
	}
	/**
	 * Create the frame.
	 */
	public UFrame() {
		setTitle("电压互感器测试数据管理");
		init();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				MainFrame.frame.setVisible(true);
			}
		});
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 799, 500);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("测试数据导入");
		menuBar.add(mnNewMenu);
		
		JMenuItem menuItem = new JMenuItem("导入测试数据");
		menuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				 JFileChooser jfc=new JFileChooser();  
			        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
			        jfc.showDialog(new JLabel(), "选择");  
			        File file=jfc.getSelectedFile();  
			        if(file!=null){
			        	String filePath=file.getAbsolutePath();
			        	System.out.println(filePath); 
			        	String s=ImportUtil.ImportUTestData(filePath);
			        	JOptionPane.showMessageDialog(null, s);
			        	if(MainFrame.uFrame.uBaseTable!=null){
			        		MainFrame.uFrame.uBaseTable.setModel(UBaseInfo.getUTableData());
			        	}
			        }
			}
		});
		mnNewMenu.add(menuItem);
		
//		JMenuItem menuItem_1 = new JMenuItem("批量导入测试数据");
//		mnNewMenu.add(menuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("导出报告");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmword = new JMenuItem("导出数据到word模板");
		mntmword.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(Constant.BASE_ID==null || Constant.BASE_ID==-1){
					JOptionPane.showMessageDialog(null, "请在左侧表格中选择需要导出的数据。");
				}else {
					String s=ExportUtil.exportU(Constant.BASE_ID);
					JOptionPane.showMessageDialog(null, s);
				}
			}
		});
		mnNewMenu_1.add(mntmword);
		
		JButton button_add = new JButton("新增");
		button_add.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				uAddFrame.setVisible(true);
			}
		});
		menuBar.add(button_add);
		
		JButton button_del = new JButton("删除");
		button_del.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("删除");
				if(Constant.BASE_ID==null || Constant.BASE_ID<0){
					JOptionPane.showMessageDialog(null, "请在左侧表格中选择需要删除的数据");
				}
				int choose=JOptionPane.showConfirmDialog(null, "确定删除id为["+Constant.BASE_ID+"]的这批数据吗？\n删除之后不能恢复。", "删除", JOptionPane.OK_CANCEL_OPTION);
				if(choose==JOptionPane.OK_OPTION){
					boolean result=UBaseInfo.delUData(Constant.BASE_ID);
					if(result){
						JOptionPane.showMessageDialog(null, "删除成功");
						if(MainFrame.uFrame.uBaseTable!=null){
							MainFrame.uFrame.uBaseTable.setModel(UBaseInfo.getUTableData());
						}
					}else {
						JOptionPane.showMessageDialog(null, "删除出错！");
					}
				}
			}
		});
		menuBar.add(button_del);
		
		JButton button_save = new JButton("保存");
		button_save.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("保存");
				if(Constant.TAB_ABC.equals(Constant.TEST_DATA_BASE)) {//选中的是基本信息tab
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
					boolean b=UBaseInfo.updateUBaseData(map, Constant.BASE_ID);
					if(b){
						JOptionPane.showMessageDialog(null, "保存成功。");
						if(MainFrame.uFrame.uBaseTable!=null){
							MainFrame.uFrame.uBaseTable.setModel(UBaseInfo.getUTableData());
						}
					}else {
						JOptionPane.showMessageDialog(null, "保存失败！","错误",JOptionPane.ERROR_MESSAGE);
					}
				}else {
					TableModel model=new DefaultTableModel();
					if(Constant.TAB_ABC.equals(Constant.TEST_DATA_A)){//如果选中的是tab a
						model=table_ab.getModel();
					}else if(Constant.TAB_ABC.equals(Constant.TEST_DATA_B)){//如果选中的是tab b
						model=table_bc.getModel();
					}else if(Constant.TAB_ABC.equals(Constant.TEST_DATA_C)){//如果选中的是tab c
						model=table_ca.getModel();
					}
					boolean b=UTestData.updateUTestData(model, Constant.TAB_ABC, Constant.BASE_ID);
					if(b){
						JOptionPane.showMessageDialog(null, "保存成功。");
						if(MainFrame.uFrame.uBaseTable!=null){
							MainFrame.uFrame.uBaseTable.setModel(UBaseInfo.getUTableData());
						}
					}else {
						JOptionPane.showMessageDialog(null, "保存失败！","错误",JOptionPane.ERROR_MESSAGE);
					}
				}
				
			}
		});
		menuBar.add(button_save);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{100,100,100,584};
		gridBagLayout.rowHeights = new int[]{20,20,30};
		gridBagLayout.columnWeights = new double[]{1.0,1.0,1,1.0};
		gridBagLayout.rowWeights = new double[]{0.1,0.1,5.0};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("请选择测试数据");
		lblNewLabel.setForeground(Color.blue);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 3;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				JTabbedPane selectedTab=(JTabbedPane)e.getSource();
				int index=selectedTab.getSelectedIndex();
				System.out.println("选中tab的index="+index);
				if(index==1){
					Constant.TAB_ABC=Constant.TEST_DATA_A;
					System.out.println("开始加载测试数据a");
					table_ab.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
					
				}else if(index==2){
					Constant.TAB_ABC=Constant.TEST_DATA_B;
					table_bc.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
				}else if(index==3){
					Constant.TAB_ABC=Constant.TEST_DATA_C;
					table_ca.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
				}else {
					Constant.TAB_ABC=Constant.TEST_DATA_BASE;
					loadUBaseInfo(Constant.BASE_ID);
				}
				System.out.println("当前选中的是第"+index+"个tabbedPane");
			}
		});
		tabbedPane.addComponentListener(new ComponentAdapter() {
			@Override
			//给tab添加事件，动态改变table的行高
			public void componentResized(ComponentEvent e) {
				int h=tabbedPane.getHeight();
				table_ab.setRowHeight((h-Constant.EXTRA_HEIGHT)/4);
				table_bc.setRowHeight((h-Constant.EXTRA_HEIGHT)/4);
				table_ca.setRowHeight((h-Constant.EXTRA_HEIGHT)/4);
			}
		});
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.gridheight = 3;
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 3;
		gbc_tabbedPane.gridy = 0;
		getContentPane().add(tabbedPane, gbc_tabbedPane);
		
		JPanel panel_base = new JPanel();
		tabbedPane.addTab("基本信息", null, panel_base, null);
		GridBagLayout gbl_panel_base = new GridBagLayout();
		gbl_panel_base.columnWidths = new int[]{45, 0,80,0,80,0,0,60,50};
		gbl_panel_base.rowHeights = new int[]{0, 0,0,0,0,0,0,0,0};
		gbl_panel_base.columnWeights = new double[]{1.0,1,1.0,1,1.0,1.0,1.0,1.0,1.0};
		gbl_panel_base.rowWeights = new double[]{1,1.0,1.0,1.0,1,1.0,1,1.0,1.0};
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
		gbc_huiLuMingCheng.insets = new Insets(0, 0, 5, 5);
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
		gbc_changHao_a.insets = new Insets(0, 0, 5, 5);
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
		gbc_changHao_b.insets = new Insets(0, 0, 5, 5);
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
		gbc_changHao_c.insets = new Insets(0, 0, 5, 5);
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
		gbc_bianBi.insets = new Insets(0, 0, 5, 5);
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
		panel_4.setBorder(new LineBorder(new Color(192, 192, 192)));
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.gridheight = 3;
		gbc_panel_4.gridwidth = 3;
		gbc_panel_4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.gridx = 5;
		gbc_panel_4.gridy = 5;
		panel_base.add(panel_4, gbc_panel_4);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{20,10,54};
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
		
		JLabel lblNewLabel_36 = new JLabel("测试编号");
		GridBagConstraints gbc_lblNewLabel_36 = new GridBagConstraints();
		gbc_lblNewLabel_36.gridwidth = 2;
		gbc_lblNewLabel_36.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_36.gridx = 0;
		gbc_lblNewLabel_36.gridy = 7;
		panel_base.add(lblNewLabel_36, gbc_lblNewLabel_36);
		
		zhengShuBianHao = new JTextField();
		GridBagConstraints gbc_zhengShuBianHao = new GridBagConstraints();
		gbc_zhengShuBianHao.insets = new Insets(0, 0, 5, 5);
		gbc_zhengShuBianHao.fill = GridBagConstraints.HORIZONTAL;
		gbc_zhengShuBianHao.gridx = 2;
		gbc_zhengShuBianHao.gridy = 7;
		panel_base.add(zhengShuBianHao, gbc_zhengShuBianHao);
		zhengShuBianHao.setColumns(10);
		
		JLabel label_22 = new JLabel("测试日期");
		GridBagConstraints gbc_label_22 = new GridBagConstraints();
		gbc_label_22.anchor = GridBagConstraints.EAST;
		gbc_label_22.insets = new Insets(0, 0, 5, 5);
		gbc_label_22.gridx = 3;
		gbc_label_22.gridy = 7;
		panel_base.add(label_22, gbc_label_22);
		
		ceShiRiQi = new JTextField();
		GridBagConstraints gbc_ceShiRiQi = new GridBagConstraints();
		gbc_ceShiRiQi.insets = new Insets(0, 0, 5, 5);
		gbc_ceShiRiQi.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiRiQi.gridx = 4;
		gbc_ceShiRiQi.gridy = 7;
		panel_base.add(ceShiRiQi, gbc_ceShiRiQi);
		ceShiRiQi.setColumns(10);
		
		JLabel lblNewLabel_37 = new JLabel("测试人");
		GridBagConstraints gbc_lblNewLabel_37 = new GridBagConstraints();
		gbc_lblNewLabel_37.gridwidth = 2;
		gbc_lblNewLabel_37.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_37.gridx = 0;
		gbc_lblNewLabel_37.gridy = 8;
		panel_base.add(lblNewLabel_37, gbc_lblNewLabel_37);
		
		ceShiRen = new JTextField();
		GridBagConstraints gbc_ceShiRen = new GridBagConstraints();
		gbc_ceShiRen.insets = new Insets(0, 0, 0, 5);
		gbc_ceShiRen.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiRen.gridx = 2;
		gbc_ceShiRen.gridy = 8;
		panel_base.add(ceShiRen, gbc_ceShiRen);
		ceShiRen.setColumns(10);
		
		JLabel lblNewLabel_38 = new JLabel("测试结论");
		GridBagConstraints gbc_lblNewLabel_38 = new GridBagConstraints();
		gbc_lblNewLabel_38.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_38.gridx = 3;
		gbc_lblNewLabel_38.gridy = 8;
		panel_base.add(lblNewLabel_38, gbc_lblNewLabel_38);
		
		ceShiJieLun = new JTextField();
		GridBagConstraints gbc_ceShiJieLun = new GridBagConstraints();
		gbc_ceShiJieLun.gridwidth = 4;
		gbc_ceShiJieLun.insets = new Insets(0, 0, 0, 5);
		gbc_ceShiJieLun.fill = GridBagConstraints.HORIZONTAL;
		gbc_ceShiJieLun.gridx = 4;
		gbc_ceShiJieLun.gridy = 8;
		panel_base.add(ceShiJieLun, gbc_ceShiJieLun);
		ceShiJieLun.setColumns(10);
		
		JPanel panel_ab = new JPanel();
		tabbedPane.addTab("AB误差", null, panel_ab, null);
		GridBagLayout layout_ab = new GridBagLayout();
		layout_ab.columnWidths = new int[]{20, 20,20,40,40,40,40,40};
		layout_ab.rowHeights = new int[]{20, 40,40,40,40};
		layout_ab.columnWeights = new double[]{0.1, 0.1,0.2,1,1,1,1,1};
		layout_ab.rowWeights = new double[]{0.5,1,1,1,1};
		panel_ab.setLayout(layout_ab);
		
		JLabel lblNewLabel_1 = new JLabel("百分表");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridwidth = 3;
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel_ab.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		DefaultTableModel modelAB=UTestData.getUTestData(Constant.BASE_ID,Constant.TAB_ABC);
		
		JScrollPane scrollPane_ab = new JScrollPane();
		GridBagConstraints gbc_scrollPane_ab = new GridBagConstraints();
		gbc_scrollPane_ab.gridwidth = 5;
		gbc_scrollPane_ab.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_ab.gridheight = 5;
		gbc_scrollPane_ab.gridx = 3;
		gbc_scrollPane_ab.gridy = 0;
		panel_ab.add(scrollPane_ab, gbc_scrollPane_ab);
		table_ab = new JTable(modelAB);
		table_ab.putClientProperty("terminateEditOnFocusLost", true);//不失去焦点，正在编辑的cell值也能提交
		table_ab.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table_ab.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_ab.setViewportView(table_ab);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridheight = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		panel_ab.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{20, 0};
		gbl_panel.rowHeights = new int[]{40, 40, 0};
		gbl_panel.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel lblNewLabel_2 = new JLabel("<html><br>满<br>载</html>");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.gridheight = 2;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		JLabel lblNewLabel_28 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_lblNewLabel_28 = new GridBagConstraints();
		gbc_lblNewLabel_28.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_28.gridx = 1;
		gbc_lblNewLabel_28.gridy = 1;
		panel_ab.add(lblNewLabel_28, gbc_lblNewLabel_28);
		
		JLabel lblNewLabel_32 = new JLabel("修约值");
		GridBagConstraints gbc_lblNewLabel_32 = new GridBagConstraints();
		gbc_lblNewLabel_32.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_32.gridx = 2;
		gbc_lblNewLabel_32.gridy = 1;
		panel_ab.add(lblNewLabel_32, gbc_lblNewLabel_32);
		
		JLabel lblNewLabel_29 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_lblNewLabel_29 = new GridBagConstraints();
		gbc_lblNewLabel_29.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_29.gridx = 1;
		gbc_lblNewLabel_29.gridy = 2;
		panel_ab.add(lblNewLabel_29, gbc_lblNewLabel_29);
		
		JLabel lblNewLabel_33 = new JLabel("修约值");
		GridBagConstraints gbc_lblNewLabel_33 = new GridBagConstraints();
		gbc_lblNewLabel_33.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_33.gridx = 2;
		gbc_lblNewLabel_33.gridy = 2;
		panel_ab.add(lblNewLabel_33, gbc_lblNewLabel_33);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridheight = 2;
		gbc_panel_3.insets = new Insets(0, 0, 0, 5);
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 3;
		panel_ab.add(panel_3, gbc_panel_3);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{20, 0};
		gbl_panel_3.rowHeights = new int[]{40, 40, 0};
		gbl_panel_3.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblNewLabel_3 = new JLabel("<html>轻<br>载</html>");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.gridheight = 2;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 0;
		panel_3.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		JLabel lblNewLabel_30 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_lblNewLabel_30 = new GridBagConstraints();
		gbc_lblNewLabel_30.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_30.gridx = 1;
		gbc_lblNewLabel_30.gridy = 3;
		panel_ab.add(lblNewLabel_30, gbc_lblNewLabel_30);
		
		JLabel lblNewLabel_34 = new JLabel("修约值");
		GridBagConstraints gbc_lblNewLabel_34 = new GridBagConstraints();
		gbc_lblNewLabel_34.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_34.gridx = 2;
		gbc_lblNewLabel_34.gridy = 3;
		panel_ab.add(lblNewLabel_34, gbc_lblNewLabel_34);
		
		JLabel lblNewLabel_31 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_lblNewLabel_31 = new GridBagConstraints();
		gbc_lblNewLabel_31.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_31.gridx = 1;
		gbc_lblNewLabel_31.gridy = 4;
		panel_ab.add(lblNewLabel_31, gbc_lblNewLabel_31);
		
		JLabel lblNewLabel_35 = new JLabel("修约值");
		GridBagConstraints gbc_lblNewLabel_35 = new GridBagConstraints();
		gbc_lblNewLabel_35.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_35.gridx = 2;
		gbc_lblNewLabel_35.gridy = 4;
		panel_ab.add(lblNewLabel_35, gbc_lblNewLabel_35);
		
		JPanel panel_bc = new JPanel();
		tabbedPane.addTab("BC误差", null, panel_bc, null);
		GridBagLayout layout_bc = new GridBagLayout();
		layout_bc.columnWidths = new int[]{20, 20,20,40,40,40,40,40};
		layout_bc.rowHeights = new int[]{20, 40,40,40,40};
		layout_bc.columnWeights = new double[]{0.1, 0.1,0.2,1,1,1,1,1};
		layout_bc.rowWeights = new double[]{0.5,1,1,1,1};
		panel_bc.setLayout(layout_bc);
		
		JLabel label = new JLabel("百分表");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridwidth = 3;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		panel_bc.add(label, gbc_label);
		
		JScrollPane scrollPane_bc = new JScrollPane();
		GridBagConstraints gbc_scrollPane_bc = new GridBagConstraints();
		gbc_scrollPane_bc.gridwidth = 5;
		gbc_scrollPane_bc.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_bc.gridheight = 5;
		gbc_scrollPane_bc.gridx = 3;
		gbc_scrollPane_bc.gridy = 0;
		panel_bc.add(scrollPane_bc, gbc_scrollPane_bc);
		
		table_bc = new JTable(modelAB);
		table_bc.putClientProperty("terminateEditOnFocusLost", true);//不失去焦点，正在编辑的cell值也能提交
		scrollPane_bc.setViewportView(table_bc);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridheight = 2;
		gbc_panel_2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_bc.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{20, 0};
		gbl_panel_2.rowHeights = new int[]{40, 40, 0};
		gbl_panel_2.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel label_1 = new JLabel("<html><br>满<br>载</html>");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridheight = 2;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 0;
		panel_2.add(label_1, gbc_label_1);
		
		JLabel label_2 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 1;
		panel_bc.add(label_2, gbc_label_2);
		
		JLabel label_3 = new JLabel("修约值");
		GridBagConstraints gbc_label_3 = new GridBagConstraints();
		gbc_label_3.insets = new Insets(0, 0, 5, 5);
		gbc_label_3.gridx = 2;
		gbc_label_3.gridy = 1;
		panel_bc.add(label_3, gbc_label_3);
		
		JLabel label_4 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.insets = new Insets(0, 0, 5, 5);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 2;
		panel_bc.add(label_4, gbc_label_4);
		
		JLabel label_5 = new JLabel("修约值");
		GridBagConstraints gbc_label_5 = new GridBagConstraints();
		gbc_label_5.insets = new Insets(0, 0, 5, 5);
		gbc_label_5.gridx = 2;
		gbc_label_5.gridy = 2;
		panel_bc.add(label_5, gbc_label_5);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.gridheight = 2;
		gbc_panel_5.insets = new Insets(0, 0, 0, 5);
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 3;
		panel_bc.add(panel_5, gbc_panel_5);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{20, 0};
		gbl_panel_5.rowHeights = new int[]{40, 40, 0};
		gbl_panel_5.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel label_6 = new JLabel("<html>轻<br>载</html>");
		GridBagConstraints gbc_label_6 = new GridBagConstraints();
		gbc_label_6.insets = new Insets(0, 0, 5, 0);
		gbc_label_6.gridheight = 2;
		gbc_label_6.gridx = 0;
		gbc_label_6.gridy = 0;
		panel_5.add(label_6, gbc_label_6);
		
		JLabel label_7 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_label_7 = new GridBagConstraints();
		gbc_label_7.insets = new Insets(0, 0, 5, 5);
		gbc_label_7.gridx = 1;
		gbc_label_7.gridy = 3;
		panel_bc.add(label_7, gbc_label_7);
		
		JLabel label_8 = new JLabel("修约值");
		GridBagConstraints gbc_label_8 = new GridBagConstraints();
		gbc_label_8.insets = new Insets(0, 0, 5, 5);
		gbc_label_8.gridx = 2;
		gbc_label_8.gridy = 3;
		panel_bc.add(label_8, gbc_label_8);
		
		JLabel label_9 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_label_9 = new GridBagConstraints();
		gbc_label_9.insets = new Insets(0, 0, 0, 5);
		gbc_label_9.gridx = 1;
		gbc_label_9.gridy = 4;
		panel_bc.add(label_9, gbc_label_9);
		
		JLabel label_10 = new JLabel("修约值");
		GridBagConstraints gbc_label_10 = new GridBagConstraints();
		gbc_label_10.insets = new Insets(0, 0, 0, 5);
		gbc_label_10.gridx = 2;
		gbc_label_10.gridy = 4;
		panel_bc.add(label_10, gbc_label_10);
		
		JPanel panel_ca = new JPanel();
		tabbedPane.addTab("CA误差", null, panel_ca, null);
		GridBagLayout layout_ca = new GridBagLayout();
		layout_ca.columnWidths = new int[]{20, 20,20,40,40,40,40,40};
		layout_ca.rowHeights = new int[]{20, 40,40,40,40};
		layout_ca.columnWeights = new double[]{0.1, 0.1,0.2,1,1,1,1,1};
		layout_ca.rowWeights = new double[]{0.5,1,1,1,1};
		panel_ca.setLayout(layout_ca);
		
		JLabel label_11 = new JLabel("百分表");
		GridBagConstraints gbc_label_11 = new GridBagConstraints();
		gbc_label_11.gridwidth = 3;
		gbc_label_11.insets = new Insets(0, 0, 5, 5);
		gbc_label_11.gridx = 0;
		gbc_label_11.gridy = 0;
		panel_ca.add(label_11, gbc_label_11);
		
		JScrollPane scrollPane_ca = new JScrollPane();
		GridBagConstraints gbc_scrollPane_ca = new GridBagConstraints();
		gbc_scrollPane_ca.gridwidth = 5;
		gbc_scrollPane_ca.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_ca.gridheight = 5;
		gbc_scrollPane_ca.gridx = 3;
		gbc_scrollPane_ca.gridy = 0;
		panel_ca.add(scrollPane_ca, gbc_scrollPane_ca);
		
		table_ca = new JTable(modelAB);
		table_ca.putClientProperty("terminateEditOnFocusLost", true);//不失去焦点，正在编辑的cell值也能提交
		scrollPane_ca.setViewportView(table_ca);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.gridheight = 2;
		gbc_panel_7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 1;
		panel_ca.add(panel_7, gbc_panel_7);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{20, 0};
		gbl_panel_7.rowHeights = new int[]{40, 40, 0};
		gbl_panel_7.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JLabel label_12 = new JLabel("<html><br>满<br>载</html>");
		GridBagConstraints gbc_label_12 = new GridBagConstraints();
		gbc_label_12.insets = new Insets(0, 0, 5, 0);
		gbc_label_12.gridheight = 2;
		gbc_label_12.gridx = 0;
		gbc_label_12.gridy = 0;
		panel_7.add(label_12, gbc_label_12);
		
		JLabel label_13 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_label_13 = new GridBagConstraints();
		gbc_label_13.insets = new Insets(0, 0, 5, 5);
		gbc_label_13.gridx = 1;
		gbc_label_13.gridy = 1;
		panel_ca.add(label_13, gbc_label_13);
		
		JLabel label_14 = new JLabel("修约值");
		GridBagConstraints gbc_label_14 = new GridBagConstraints();
		gbc_label_14.insets = new Insets(0, 0, 5, 5);
		gbc_label_14.gridx = 2;
		gbc_label_14.gridy = 1;
		panel_ca.add(label_14, gbc_label_14);
		
		JLabel label_15 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_label_15 = new GridBagConstraints();
		gbc_label_15.insets = new Insets(0, 0, 5, 5);
		gbc_label_15.gridx = 1;
		gbc_label_15.gridy = 2;
		panel_ca.add(label_15, gbc_label_15);
		
		JLabel label_16 = new JLabel("修约值");
		GridBagConstraints gbc_label_16 = new GridBagConstraints();
		gbc_label_16.insets = new Insets(0, 0, 5, 5);
		gbc_label_16.gridx = 2;
		gbc_label_16.gridy = 2;
		panel_ca.add(label_16, gbc_label_16);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridheight = 2;
		gbc_panel_8.insets = new Insets(0, 0, 0, 5);
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 3;
		panel_ca.add(panel_8, gbc_panel_8);
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[]{20, 0};
		gbl_panel_8.rowHeights = new int[]{40, 40, 0};
		gbl_panel_8.columnWeights = new double[]{0.1, Double.MIN_VALUE};
		gbl_panel_8.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		panel_8.setLayout(gbl_panel_8);
		
		JLabel label_17 = new JLabel("<html>轻<br>载</html>");
		GridBagConstraints gbc_label_17 = new GridBagConstraints();
		gbc_label_17.insets = new Insets(0, 0, 5, 0);
		gbc_label_17.gridheight = 2;
		gbc_label_17.gridx = 0;
		gbc_label_17.gridy = 0;
		panel_8.add(label_17, gbc_label_17);
		
		JLabel label_18 = new JLabel("<html>比<br>值<br>差</html>");
		GridBagConstraints gbc_label_18 = new GridBagConstraints();
		gbc_label_18.insets = new Insets(0, 0, 5, 5);
		gbc_label_18.gridx = 1;
		gbc_label_18.gridy = 3;
		panel_ca.add(label_18, gbc_label_18);
		
		JLabel label_19 = new JLabel("修约值");
		GridBagConstraints gbc_label_19 = new GridBagConstraints();
		gbc_label_19.insets = new Insets(0, 0, 5, 5);
		gbc_label_19.gridx = 2;
		gbc_label_19.gridy = 3;
		panel_ca.add(label_19, gbc_label_19);
		
		JLabel label_20 = new JLabel("<html>相<br>位<br>差<html>");
		GridBagConstraints gbc_label_20 = new GridBagConstraints();
		gbc_label_20.insets = new Insets(0, 0, 0, 5);
		gbc_label_20.gridx = 1;
		gbc_label_20.gridy = 4;
		panel_ca.add(label_20, gbc_label_20);
		
		JLabel label_21 = new JLabel("修约值");
		GridBagConstraints gbc_label_21 = new GridBagConstraints();
		gbc_label_21.insets = new Insets(0, 0, 0, 5);
		gbc_label_21.gridx = 2;
		gbc_label_21.gridy = 4;
		panel_ca.add(label_21, gbc_label_21);
		
		DefaultTableModel model=UBaseInfo.getUTableData();
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 3;
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		getContentPane().add(scrollPane, gbc_scrollPane);
		uBaseTable = new JTable(model);
		TableColumn col_id=uBaseTable.getColumnModel().getColumn(0);
		col_id.setPreferredWidth(20);
		col_id.setMinWidth(5);
		col_id.setMaxWidth(30);
		scrollPane.setViewportView(uBaseTable);
		
		//为左侧table添加行选中事件
		uBaseTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(!e.getValueIsAdjusting()){//鼠标释放
					int row=uBaseTable.getSelectedRow();
					if(row<0){
						row=0;//刷新时，可能出现失去焦点的情况，故始终默认选中第一行
						uBaseTable.setRowSelectionInterval(0, 0);
					}
						
					Long s=Long.parseLong(uBaseTable.getValueAt(row, 0)+"");
//					String s1=uBaseTable.getValueAt(row, 1)+"";
//					String s2=uBaseTable.getValueAt(row, 2)+"";
					System.out.println("当前选中行"+row);
					Constant.BASE_ID=s;
					//加载数据
					if(Constant.TAB_ABC.equals(Constant.TEST_DATA_A)){
						table_ab.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
						
					}if(Constant.TAB_ABC.equals(Constant.TEST_DATA_B)){
						table_bc.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
					}if(Constant.TAB_ABC.equals(Constant.TEST_DATA_C)){
						table_ca.setModel(UTestData.getUTestData(Constant.BASE_ID, Constant.TAB_ABC));
					}else {
						loadUBaseInfo(Constant.BASE_ID);
					}
				}
			}
		});
		//左侧表格默认选中第一行
		uBaseTable.setRowSelectionInterval(0, 0);
	}
	
	

	/**
	 * 为基本信息区的textFile加载数据
	 * @param baseId
	 */
	public void loadUBaseInfo(Long baseId){
		if(huMing!=null){//不为空时，表示frame已经初始化了jtextfield，否则会报错。
			Map< String, String> map= UBaseInfo.getUBaseInfo(baseId);
			this.huMing.setText(map.get("huMing"));
			this.huiLuMingCheng.setText(map.get("huiLuMingCheng"));
			this.changMing_a.setText(map.get("changMing_a"));
			this.changMing_b.setText(map.get("changMing_b"));
			this.changMing_c.setText(map.get("changMing_c"));
			this.xingShi_a.setText(map.get("xingShi_a"));
			this.xingShi_b.setText(map.get("xingShi_b"));
			this.xingShi_c.setText(map.get("xingShi_c"));
			this.changHao_a.setText(map.get("changHao_a"));
			this.changHao_b.setText(map.get("changHao_b"));
			this.changHao_c.setText(map.get("changHao_c"));
			this.eDingRongLiang.setText(map.get("eDingRongLiang"));
			this.jiXing.setText(map.get("jiXing"));
			this.bianBi.setText(map.get("bianBi"));
			this.zhunQueDengJi.setText(map.get("zhunQueDengJi"));
			this.chuChangNianYue.setText(map.get("chuChangNianYue"));
			this.dianYa.setText(map.get("dianYa"));
			this.pinLv.setText(map.get("pinLv"));
			this.juBianHao_a.setText(map.get("juBianHao_a"));
			this.juBianHao_b.setText(map.get("juBianHao_b"));
			this.juBianHao_c.setText(map.get("juBianHao_c"));
			this.ceShiRen.setText(map.get("ceShiRen"));
			this.ceShiRiQi.setText(map.get("ceShiRiQi"));
			this.ceShiJieLun.setText(map.get("ceShiJieLun"));
			this.zhengShuBianHao.setText(map.get("zhengShuBianHao"));
		}
	}

}
