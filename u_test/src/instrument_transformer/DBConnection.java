package instrument_transformer;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;


/**
 * 数据库连接类。产生一个单例的数据库连接对象供程序使用。
 * 不用手动关闭此数据库连接。
 * 程序退出时关闭连接。防止频繁开启、关闭连接产生性能问题。
 * @author cuican
 */
public class DBConnection {
	private static Connection conn=null;
	
	/**
	 *  对外开放的获取数据库连接对象的方法。
	 * @return 数据库连接对象
	 */
	synchronized public static Connection getInstance(){
		if(conn==null){
			new DBConnection();
		}
		return conn;
	}
	
	
	/**
	 * 重写构造方法，并设为私有的。
	 */
	private DBConnection(){
		String mdbFilePath=System.getProperty("java.class.path");
		String[] s=mdbFilePath.split("main\\.jar");
		mdbFilePath=s[0]+"/UTest.mdb";//数据库文件名不能更改。
		mdbFilePath="c:/UTest.mdb";//test,导出之前需要注释掉
		try {
			Class.forName("com.hxtt.sql.access.AccessDriver").newInstance();
	        String url = "jdbc:Access:///"+mdbFilePath;
	        conn = DriverManager.getConnection(url);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "获取数据库连接失败。请确认"+mdbFilePath+"文件是否存在。");
			System.exit(0);
		}
	}
	/* 
	 * 重写clone方法，防止产生新的实例
	 */
	@Override
	public Connection clone() {
		return getInstance();
	}

}
