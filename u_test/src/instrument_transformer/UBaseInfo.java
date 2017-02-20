package instrument_transformer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

/**
 * 电压，基本数据操作
 * @author cuican
 *
 */
public class UBaseInfo {

	/**
	 * 电压，从数据库查询左侧table的数据
	 * @return DefaultTableModel对象，作为Jtable实例化的参数
	 */
	public static DefaultTableModel getUTableData(){
		DefaultTableModel model=new DefaultTableModel();
		Vector<Vector<String>> data=new Vector<>();//table的数据
		Vector<String> column=new Vector<>();//table的列名
		column.add(0, "ID");//主键
		column.add(1, "测试编号");
		column.add(2, "测试日期");
		Connection conn=DBConnection.getInstance();
		try {
			Statement st=conn.createStatement();
			//access不能像mysql那样limit只支持类似于oracle的top
			String sql="select top "+Constant.LIMIT+" id,certificate_no,test_date from u_base_info order by create_date desc ";
			System.out.println("电压，为左侧table加载数据的sql："+sql);
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				String id=rs.getString(1);
				String certificate_no=rs.getString(2);
				String test_date=rs.getString(3);
				Vector<String> row=new Vector<>();
				row.add(0, id);
				row.add(1, certificate_no);
				row.add(2, test_date);
				data.add(row);
			}
			
			model.setDataVector(data, column);
			rs.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return model;
	}
	
	/**
	 * 查询电压测试的基本信息
	 * @param baseId
	 * @return HashMap,key为JTextField 变量名，value为该变量对应的值
	 */
	public static HashMap<String,String> getUBaseInfo(Long baseId){
		HashMap<String,String> map=new HashMap<>();
		Connection conn=DBConnection.getInstance();
		try {
			Statement st=conn.createStatement();
			String sql="select *  from u_base_info where id="+baseId+"";
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				String huMing=rs.getString("name");
				String huiLuMingCheng=rs.getString("loop");
				String changMing_a=rs.getString("factory_name_a");
				String changMing_b=rs.getString("factory_name_b");
				String changMing_c=rs.getString("factory_name_c");
				String xingShi_a=rs.getString("model_a");
				String xingShi_b=rs.getString("model_b");
				String xingShi_c=rs.getString("model_c");
				String changHao_a=rs.getString("factory_no_a");
				String changHao_b=rs.getString("factory_no_b");
				String changHao_c=rs.getString("factory_no_c");
				String eDingRongLiang=rs.getString("volume");//额定容量
				String jiXing=rs.getString("polarity");//极性
				String bianBi=rs.getString("transformer_ratio");//变比
				String zhunQueDengJi=rs.getString("correctly_level");
				String chuChangNianYue=rs.getString("factory_date");
				String dianYa=rs.getString("rated_voltage");//额定电压（电压）
				String pinLv=rs.getString("frequency");//频率
				String juBianHao_a=rs.getString("no_a");
				String juBianHao_b=rs.getString("no_a");
				String juBianHao_c=rs.getString("no_a");
				String zhengShuBianHao=rs.getString("certificate_no");
				String ceShiRen=rs.getString("tester");
				String ceShiRiQi=rs.getString("test_date");
				String ceShiJieLun=rs.getString("conclusion");
				map.put("huMing", huMing);
				map.put("huiLuMingCheng", huiLuMingCheng);
				map.put("changMing_a", changMing_a);
				map.put("changMing_b", changMing_b);
				map.put("changMing_c", changMing_c);
				map.put("xingShi_a", xingShi_a);
				map.put("xingShi_b", xingShi_b);
				map.put("xingShi_c", xingShi_c);
				map.put("changHao_a", changHao_a);
				map.put("changHao_b", changHao_b);
				map.put("changHao_c", changHao_c);
				map.put("eDingRongLiang", eDingRongLiang);
				map.put("jiXing", jiXing);
				map.put("bianBi", bianBi);
				map.put("zhunQueDengJi", zhunQueDengJi);
				map.put("chuChangNianYue", chuChangNianYue);
				map.put("dianYa", dianYa);
				map.put("pinLv", pinLv);
				map.put("juBianHao_a", juBianHao_a);
				map.put("juBianHao_b", juBianHao_b);
				map.put("juBianHao_c", juBianHao_c);
				map.put("zhengShuBianHao",zhengShuBianHao);
				map.put("ceShiRen",ceShiRen);
				map.put("ceShiRiQi",ceShiRiQi);
				map.put("ceShiJieLun", ceShiJieLun);
			}
			rs.close();
			st.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 插入电压基本信息数据，同时插入空的测试数据信息。这样在录入测试数据时，只需update即可
	 * 两个插入操作是一个事务
	 * @param map 键值对，key为JTextField 变量名，value为该变量对应的值
	 * @return true成功，否则失败
	 */
	public static String addUBaseInfo(Map<String, String> map) {
		Connection conn=DBConnection.getInstance();
		try {
			conn.setAutoCommit(false);
			Statement st=conn.createStatement();
			//统计行数用来计算id
			String countSql="select max(id) as c from u_base_info ";
			ResultSet rs=st.executeQuery(countSql);
			Long count=0L;
			while (rs.next()){
				count=rs.getLong("c");
			}
			Long id=count+1;//计算id
			String create_date=DateUtil.getCurDate();//当前时间
			String name=map.get("huMing");
			String loop=map.get("huiLuMingCheng");
			String factory_name_a=map.get("changMing_a");
			String factory_name_b=map.get("changMing_b");
			String factory_name_c=map.get("changMing_c");
			String model_a=map.get("xingShi_a");
			String model_b=map.get("xingShi_b");
			String model_c=map.get("xingShi_c");
			String factory_no_a=map.get("changHao_a");
			String factory_no_b=map.get("changHao_b");
			String factory_no_c=map.get("changHao_c");
			String volume=map.get("eDingRongLiang");//额定容量
			String polarity=map.get("jiXing");//极性
			String transformer_ratio=map.get("bianBi");//变比（变压比）
			String correctly_level=map.get("zhunQueDengJi");//准确等级
			String factory_date=map.get("chuChangNianYue");//出厂年月
			String rated_voltage=map.get("dianYa");//电压（额定电压）
			String frequency=map.get("pinLv");//频率
			String no_a=map.get("juBianHao_a");
			String no_b=map.get("juBianHao_b");
			String no_c=map.get("juBianHao_c");
			String certificate_no=map.get("zhengShuBianHao");//测试编号
			String tester=map.get("ceShiRen");
			String test_date=map.get("ceShiRiQi");
			String conclusion=map.get("ceShiJieLun");//测试结论
			if(certificate_no==null || "".equals(certificate_no)){
				return "测试编号不能为空！";
			}
			//校验测试编号是否存在
//			String check_sql="select count(*) as total from u_base_info where certificate_no='"+certificate_no+"' ";
//			ResultSet rs1=st.executeQuery(check_sql);
//			Long total=-1L;
//			while (rs1.next()){
//				total=rs1.getLong("total");
//			}
//			if(total>0){
//				return "测试编号已存在！";
//			}
			String sql="insert into u_base_info "
						+ "(id,name,loop,"
						+ "factory_name_a,factory_name_b,factory_name_c,"
						+ "model_a,model_b,model_c,"
						+ "factory_no_a,factory_no_b,factory_no_c,"
						+ "volume,polarity,transformer_ratio,correctly_level,"
						+ "factory_date,rated_voltage,frequency,"
						+ "no_a,no_b,no_c,"
						+ "certificate_no,tester,test_date,conclusion,create_date) "
					+ "values"
						+ "("+id+",'"+name+"','"+loop+"',"
						+ "'"+factory_name_a+"','"+factory_name_b+"','"+factory_name_c+"',"
						+ "'"+model_a+"','"+model_b+"','"+model_c+"',"
						+ "'"+factory_no_a+"','"+factory_no_b+"','"+factory_no_c+"',"
						+ "'"+volume+"','"+polarity+"','"+transformer_ratio+"','"+correctly_level+"',"
						+ "'"+factory_date+"','"+rated_voltage+"','"+frequency+"',"
						+ "'"+no_a+"','"+no_b+"','"+no_c+"',"
						+ "'"+certificate_no+"','"+tester+"','"+test_date+"','"+conclusion+"','"+create_date+"'"
						+ ") ";
			System.out.println("新增电压基本信息：sql "+sql);
			st.executeUpdate(sql);
			//插入测试数据,一组基础数据对应12条测试数据。
			//12条数据是3个误差（ab，bc，ca）、满载+轻载（2种情况）、比差+角差（2种情况）的组合，3*2*2=12
			String[] deviation={Constant.TEST_DATA_A,Constant.TEST_DATA_B,Constant.TEST_DATA_C};//ab、bc、ca误差
			String[] sn_ln={Constant.SN,Constant.LN};//满载、轻载
			System.out.println("插入电压测试数据:");
			for (int i = 0; i < deviation.length; i++) {
				for (int j = 0; j < sn_ln.length; j++) {
					//比差
					String sql1="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln) "
							+ "values "
							+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[i]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[j]+"') ";
					//角差
					String sql2="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln) "
							+ "values "
							+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[i]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[j]+"') ";//比差
					System.out.println(sql1);
					System.out.println(sql2);
					st.executeUpdate(sql1);
					st.executeUpdate(sql2);
					
				}
			}
			conn.commit();
			rs.close();
			st.close();
			return Constant.SUCCESS;
		} catch (SQLException e) {
			System.err.println("电压，新增出错，回滚！");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println("回滚出错！");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println("setAutoCommit出错！");
				e.printStackTrace();
			}
		}
		return Constant.FAILED;
	}
	
	/**
	 * 删除电压互感器的数据（基本信息+测试数据）。
	 * 两个删除是一个事务
	 * @param baseId 基本信息id
	 * @return true成功，否则失败
	 */
	public static boolean delUData(Long baseId){
		boolean result=false;
		String sql1="delete from u_test_data where base_id="+baseId+"";
		String sql2="delete from u_base_info where id="+baseId+"";
		System.out.println("删除电压测试数据"+sql1);
		System.out.println("删除电压基础数据"+sql2);
		Connection conn=DBConnection.getInstance();
		try {
			conn.setAutoCommit(false);
			Statement st=conn.createStatement();
			st.executeUpdate(sql1);
			st.executeUpdate(sql2);
			conn.commit();
			st.close();
			return true;
		} catch (SQLException e) {
			System.err.println("删除电压互感器数据出错！");
			try {
				System.out.println("事务回滚。");
				conn.rollback();
			} catch (SQLException e1) {
				System.err.println("事务回滚出错");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				System.err.println("设置数据库AutoCommit出错");
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 更新电压的基本数据
	 * @param map 待更新的结果集，键值对形式，key为JTextField 变量名，value为该变量对应的值
	 * @param baseId 被更新的记录的id
	 * @return true成功，否则失败
	 */
	public static boolean updateUBaseData(Map<String,String> map,Long baseId){
		Connection conn=DBConnection.getInstance();
		try {
			Statement st=conn.createStatement();
//			String create_date=DateUtil.getCurDate();//当前时间
			String name=map.get("huMing");
			String loop=map.get("huiLuMingCheng");
			String factory_name_a=map.get("changMing_a");
			String factory_name_b=map.get("changMing_b");
			String factory_name_c=map.get("changMing_c");
			String model_a=map.get("xingShi_a");
			String model_b=map.get("xingShi_b");
			String model_c=map.get("xingShi_c");
			String factory_no_a=map.get("changHao_a");
			String factory_no_b=map.get("changHao_b");
			String factory_no_c=map.get("changHao_c");
			String volume=map.get("eDingRongLiang");//额定容量
			String polarity=map.get("jiXing");//极性
			String transformer_ratio=map.get("bianBi");//变比（变压比）
			String correctly_level=map.get("zhunQueDengJi");//准确等级
			String factory_date=map.get("chuChangNianYue");//出厂年月
			String rated_voltage=map.get("dianYa");//电压（额定电压）
			String frequency=map.get("pinLv");//频率
			String no_a=map.get("juBianHao_a");
			String no_b=map.get("juBianHao_b");
			String no_c=map.get("juBianHao_c");
			String certificate_no=map.get("zhengShuBianHao");//测试编号
			String tester=map.get("ceShiRen");
			String test_date=map.get("ceShiRiQi");
			String conclusion=map.get("ceShiJieLun");//测试结论
			String sql="update u_base_info "
						+ "set name='"+name+"',loop='"+loop+"',"
						+ "factory_name_a='"+factory_name_a+"',factory_name_b='"+factory_name_b+"',factory_name_c='"+factory_name_c+"',"
						+ "model_a='"+model_a+"',model_b='"+model_b+"',model_c='"+model_c+"',"
						+ "factory_no_a='"+factory_no_a+"',factory_no_b='"+factory_no_b+"',factory_no_c='"+factory_no_c+"',"
						+ "volume='"+volume+"',polarity='"+polarity+"',transformer_ratio='"+transformer_ratio+"',correctly_level='"+correctly_level+"',"
						+ "factory_date='"+factory_date+"',rated_voltage='"+rated_voltage+"',frequency='"+frequency+"',"
						+ "no_a='"+no_a+"',no_b='"+no_b+"',no_c='"+no_c+"',"
						+ "certificate_no='"+certificate_no+"',tester='"+tester+"',test_date='"+test_date+"',conclusion='"+conclusion+"' "
						+ "where id="+baseId;
			System.out.println("编辑电压基本信息： "+sql);
			st.executeUpdate(sql);
			st.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
