package instrument_transformer;

import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = -5238600605228685760L;

	/**
	 * Launch the application.
	 */
	public  static UFrame uFrame;
	public static MainFrame frame;
	public static Connection conn;
	/**
	 * 初始化
	 * 1.数据库连接(单例的）
	 * 2.电压主界面UFrame并设置为不显示，需要时显示即可，不用重新new。其它Frame也用此种处理方式。
	 * 
	 */
	public static void init(){
		//获取数据库连接
		conn=DBConnection.getInstance();
		//初始化电压主frame并设置为不显示
		if(uFrame==null){
			uFrame=new UFrame();
		}
		uFrame.setVisible(false);
		
	}
	public static void main(String[] args) {
		init();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(frame==null){
						frame = new MainFrame();
					}
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					conn.close();
				} catch (SQLException e1) {
					JOptionPane.showConfirmDialog(null, "数据库连接关闭失败！");
					e1.printStackTrace();
				}
//				System.out.println("main frame closed");
				System.exit(0);
			}
		});
		setTitle("互感器测试数据管理软件");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JButton btnUTest = new JButton("电压互感器");
		btnUTest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
				uFrame.setVisible(true);
			}
		});
		btnUTest.setBounds(34, 41, 141, 23);
		getContentPane().add(btnUTest);
		
		JButton btnITest = new JButton("电流互感器");
		btnITest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.setVisible(false);
			}
		});
		btnITest.setBounds(264, 41, 132, 23);
		getContentPane().add(btnITest);
		
		//初始化电压的frame
	}
}
