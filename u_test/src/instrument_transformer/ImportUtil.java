package instrument_transformer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 导入的工具类
 * 
 * @author cuican
 * 
 */
public class ImportUtil {

	private static BufferedReader bufferedReader;
	/**
	 * 导入一组电流测试数据
	 * 
	 * @param filePath
	 * @return
	 */
	public static String ImportITestData(String filePath) {
		String result = Constant.FAILED;
		String[] s = filePath.split("\\.");
		Map<String, String> map = new HashMap<>();
		String txt = s[s.length - 1];
		if (!"txt".equals(txt.toLowerCase())) {
			return "导入的文件必须是txt格式的，请重新选择。";
		}
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int line = 0;// 行号
				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.trim();
					if("".equals(lineTxt))continue;
					String[] pairs = lineTxt.split(":");
					String key = pairs[0];
					String value = pairs[1];
					//如果是测试数据（以deviation_开头的行），则:后面的都作为key的value
					if(key.startsWith("deviation_")){
						value="";
						for (int i = 1; i < pairs.length; i++) {
							value+=pairs[i]+":";
						}
						value=value.substring(0, value.length()-1);
					}
					System.out.println(key + "=" + value);
					if(line ==0){
						if (!"type".equals(key) || !"I".equals(value)) {
							return "文件内容有误，请重新选择。";
						}
					}
					if (line == 1) {
						if (!"BH".equals(key)) {
							return "文件内容有误，请重新选择。";
						}
					}
					map.put(key, value);//将导入的数据存入map
					line++;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return "导入测试数据失败！请检查导入的文件与数据是否有误。";
		}
		if(map!=null){
			//插入基本数据和空的测试数据
			Map<String, String> baseInfoMap=new HashMap<>();
			baseInfoMap.put("huMing", map.get("name")+"");
			baseInfoMap.put("huiLuMingCheng", map.get("loop")+"");
			baseInfoMap.put("changMing_a", map.get("factory_name_a")+"");
			baseInfoMap.put("changMing_b", map.get("factory_name_b")+"");
			baseInfoMap.put("changMing_c", map.get("factory_name_c")+"");
			baseInfoMap.put("xingShi_a", map.get("model_a")+"");
			baseInfoMap.put("xingShi_b", map.get("model_b")+"");
			baseInfoMap.put("xingShi_c", map.get("model_c")+"");
			baseInfoMap.put("changHao_a", map.get("factory_no_a")+"");
			baseInfoMap.put("changHao_b", map.get("factory_no_b")+"");
			baseInfoMap.put("changHao_c", map.get("factory_no_c")+"");
			baseInfoMap.put("eDingRongLiang", map.get("volume")+"");
			baseInfoMap.put("jiXing", map.get("polarity")+"");
			baseInfoMap.put("bianBi", map.get("transformer_ratio")+"");
			baseInfoMap.put("zhunQueDengJi", map.get("DJ")+"");
			baseInfoMap.put("chuChangNianYue", map.get("factory_date")+"");
			baseInfoMap.put("dianYa", map.get("rated_voltage")+"");
			baseInfoMap.put("pinLv", map.get("frequency")+"");
			baseInfoMap.put("juBianHao_a", map.get("no_a")+"");
			baseInfoMap.put("juBianHao_b", map.get("no_b")+"");
			baseInfoMap.put("juBianHao_c", map.get("no_c")+"");
			baseInfoMap.put("zhengShuBianHao", map.get("BH")+"");
			baseInfoMap.put("ceShiRen", map.get("tester")+"");
			baseInfoMap.put("ceShiRiQi", map.get("Time")+"");
			baseInfoMap.put("ceShiJieLun", map.get("conclusion")+"");
			Connection conn=DBConnection.getInstance();
			try {
				conn.setAutoCommit(false);
				Statement st=conn.createStatement();
				//统计行数用来计算id
				String countSql="select max(id) as c from i_base_info ";
				ResultSet rs=st.executeQuery(countSql);
				Long count=0L;
				while (rs.next()){
					count=rs.getLong("c");
				}
				Long id=count+1;//计算id
				String create_date=DateUtil.getCurDate();//当前时间
				String name=baseInfoMap.get("huMing");
				String loop=baseInfoMap.get("huiLuMingCheng");
				String factory_name_a=baseInfoMap.get("changMing_a");
				String factory_name_b=baseInfoMap.get("changMing_b");
				String factory_name_c=baseInfoMap.get("changMing_c");
				String model_a=baseInfoMap.get("xingShi_a");
				String model_b=baseInfoMap.get("xingShi_b");
				String model_c=baseInfoMap.get("xingShi_c");
				String factory_no_a=baseInfoMap.get("changHao_a");
				String factory_no_b=baseInfoMap.get("changHao_b");
				String factory_no_c=baseInfoMap.get("changHao_c");
				String volume=baseInfoMap.get("eDingRongLiang");//额定容量
				String polarity=baseInfoMap.get("jiXing");//极性
				String transformer_ratio=baseInfoMap.get("bianBi");//变比（变压比）
				String correctly_level=baseInfoMap.get("zhunQueDengJi");//准确等级
				String factory_date=baseInfoMap.get("chuChangNianYue");//出厂年月
				String rated_voltage=baseInfoMap.get("dianYa");//电压（额定电压）
				String frequency=baseInfoMap.get("pinLv");//频率
				String no_a=baseInfoMap.get("juBianHao_a");
				String no_b=baseInfoMap.get("juBianHao_b");
				String no_c=baseInfoMap.get("juBianHao_c");
				String certificate_no=baseInfoMap.get("zhengShuBianHao");//测试编号
				String tester=baseInfoMap.get("ceShiRen");
				String test_date=baseInfoMap.get("ceShiRiQi");
				String conclusion=baseInfoMap.get("ceShiJieLun");//测试结论
				
				if(certificate_no==null || "".equals(certificate_no)){
					return "测试编号不能为空！";
				}
				//校验测试编号是否存在
//				String check_sql="select count(*) as total from i_base_info where certificate_no='"+certificate_no+"' ";
//				ResultSet rs1=st.executeQuery(check_sql);
//				Long total=-1L;
//				while (rs1.next()){
//					total=rs1.getLong("total");
//				}
//				if(total>0){
//					return "测试编号已存在！";
//				}
				
				String sql="insert into i_base_info "
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
				System.out.println("新增电流基本信息：sql "+sql);
				st.executeUpdate(sql);
				//插入测试数据,一组基础数据对应12条测试数据。
				//12条数据是3个误差（ab，bc，ca）、满载+轻载（2种情况）、比差+角差（2种情况）的组合，3*2*2=12
				String[] deviation={Constant.TEST_DATA_A,Constant.TEST_DATA_B,Constant.TEST_DATA_C};//ab、bc、ca误差
				String[] sn_ln={Constant.SN,Constant.LN};//满载、轻载
				System.out.println("插入电压测试数据:");
				
				String deviation_a_sn=map.get("deviation_a_sn");
				String[] data_a_sn=deviation_a_sn.split(",");
				String[] bicha_a_sn=data_a_sn[0].split(":")[1].split("#");
				String[] jiaocha_a_sn=data_a_sn[1].split(":")[1].split("#");
				
				String deviation_a_ln=map.get("deviation_a_ln");
				String[] data_a_ln=deviation_a_ln.split(",");
				String[] bicha_a_ln=data_a_ln[0].split(":")[1].split("#");
				String[] jiaocha_a_ln=data_a_ln[1].split(":")[1].split("#");
				
				String deviation_b_sn=map.get("deviation_b_sn");
				String[] data_b_sn=deviation_b_sn.split(",");
				String[] bicha_b_sn=data_b_sn[0].split(":")[1].split("#");
				String[] jiaocha_b_sn=data_b_sn[1].split(":")[1].split("#");
				
				String deviation_b_ln=map.get("deviation_b_ln");
				String[] data_b_ln=deviation_b_ln.split(",");
				String[] bicha_b_ln=data_b_ln[0].split(":")[1].split("#");
				String[] jiaocha_b_ln=data_b_ln[1].split(":")[1].split("#");
				
				String deviation_c_sn=map.get("deviation_c_sn");
				String[] data_c_sn=deviation_c_sn.split(",");
				String[] bicha_c_sn=data_c_sn[0].split(":")[1].split("#");
				String[] jiaocha_c_sn=data_c_sn[1].split(":")[1].split("#");
				
				String deviation_c_ln=map.get("deviation_c_ln");
				String[] data_c_ln=deviation_c_ln.split(",");
				String[] bicha_c_ln=data_c_ln[0].split(":")[1].split("#");
				String[] jiaocha_c_ln=data_c_ln[1].split(":")[1].split("#");
				
				//a-满载-比差
				String sql1="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
							+ "'"+bicha_a_sn[0]+"','"+bicha_a_sn[1]+"','"+bicha_a_sn[2]+"','"+bicha_a_sn[3]+"','"+bicha_a_sn[4]+"') ";
				//a-满载-角差
				String sql2="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_a_sn[0]+"','"+jiaocha_a_sn[1]+"','"+jiaocha_a_sn[2]+"','"+jiaocha_a_sn[3]+"','"+jiaocha_a_sn[4]+"') ";
				//a-轻载-比差
				String sql3="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
							+ "'"+bicha_a_ln[0]+"','"+bicha_a_ln[1]+"','"+bicha_a_ln[2]+"','"+bicha_a_ln[3]+"','"+bicha_a_ln[4]+"') ";
				//a-轻载-角差
				String sql4="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_a_ln[0]+"','"+jiaocha_a_ln[1]+"','"+jiaocha_a_ln[2]+"','"+jiaocha_a_ln[3]+"','"+jiaocha_a_ln[4]+"') ";
				
				
				//b-满载-比差
				String sql5="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
						+ "'"+bicha_b_sn[0]+"','"+bicha_b_sn[1]+"','"+bicha_b_sn[2]+"','"+bicha_b_sn[3]+"','"+bicha_b_sn[4]+"') ";
				//b-满载-角差
				String sql6="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_b_sn[0]+"','"+jiaocha_b_sn[1]+"','"+jiaocha_b_sn[2]+"','"+jiaocha_b_sn[3]+"','"+jiaocha_b_sn[4]+"') ";
				//b-轻载-比差
				String sql7="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
						+ "'"+bicha_b_ln[0]+"','"+bicha_b_ln[1]+"','"+bicha_b_ln[2]+"','"+bicha_b_ln[3]+"','"+bicha_b_ln[4]+"') ";
				//b-轻载-角差
				String sql8="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_b_ln[0]+"','"+jiaocha_b_ln[1]+"','"+jiaocha_b_ln[2]+"','"+jiaocha_b_ln[3]+"','"+jiaocha_b_ln[4]+"') ";
				
				//c-满载-比差
				String sql9="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
						+ "'"+bicha_c_sn[0]+"','"+bicha_c_sn[1]+"','"+bicha_c_sn[2]+"','"+bicha_c_sn[3]+"','"+bicha_c_sn[4]+"') ";
				//c-满载-角差
				String sql10="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_c_sn[0]+"','"+jiaocha_c_sn[1]+"','"+jiaocha_c_sn[2]+"','"+jiaocha_c_sn[3]+"','"+jiaocha_c_sn[4]+"') ";
				//c-轻载-比差
				String sql11="insert into i_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
						+ "'"+bicha_c_ln[0]+"','"+bicha_c_ln[1]+"','"+bicha_c_ln[2]+"','"+bicha_c_ln[3]+"','"+bicha_c_ln[4]+"') ";
				//c-轻载-角差
				String sql12="insert into i_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_c_ln[0]+"','"+jiaocha_c_ln[1]+"','"+jiaocha_c_ln[2]+"','"+jiaocha_c_ln[3]+"','"+jiaocha_c_ln[4]+"') ";
				
				st.executeUpdate(sql1);
				st.executeUpdate(sql2);
				st.executeUpdate(sql3);
				st.executeUpdate(sql4);
				st.executeUpdate(sql5);
				st.executeUpdate(sql6);
				st.executeUpdate(sql7);
				st.executeUpdate(sql8);
				st.executeUpdate(sql9);
				st.executeUpdate(sql10);
				st.executeUpdate(sql11);
				st.executeUpdate(sql12);
				conn.commit();
				rs.close();
				st.close();
				return "导入成功。";
			} catch (Exception e) {
				System.err.println("电流，新增出错，回滚！");
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
		}
		if(Constant.FAILED.equals(result)){
			return "导入测试数据失败！请检查导入的文件与数据是否有误。";
		}else {
			return "导入成功。";
		}
	}
	/**
	 * 导入一组电压测试数据
	 * 
	 * @param filePath
	 * @return
	 */
	public static String ImportUTestData(String filePath) {
		String result = Constant.FAILED;
		String[] s = filePath.split("\\.");
		Map<String, String> map = new HashMap<>();
		String txt = s[s.length - 1];
		if (!"txt".equals(txt.toLowerCase())) {
			return "导入的文件必须是txt格式的，请重新选择。";
		}
		try {
			String encoding = "utf-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				 bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				int line = 0;// 行号
				while ((lineTxt = bufferedReader.readLine()) != null) {
					lineTxt = lineTxt.trim();
					if("".equals(lineTxt))continue;
					String[] pairs = lineTxt.split(":");
					String key = pairs[0];
					String value = pairs[1];
					//如果是测试数据（以deviation_开头的行），则:后面的都作为key的value
					if(key.startsWith("deviation_")){
						value="";
						for (int i = 1; i < pairs.length; i++) {
							value+=pairs[i]+":";
						}
						value=value.substring(0, value.length()-1);
					}
					System.out.println(key + "=" + value);
					if(line ==0){
						if (!"type".equals(key) || !"U".equals(value)) {
							return "文件内容有误，请重新选择。";
						}
					}
					if (line == 1) {
						if (!"BH".equals(key)) {
							return "文件内容有误，请重新选择。";
						}
					}
					map.put(key, value);//将导入的数据存入map
					line++;
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
			return "导入测试数据失败！请检查导入的文件与数据是否有误。";
		}
		if(map!=null){
			//插入基本数据和空的测试数据
			Map<String, String> baseInfoMap=new HashMap<>();
			baseInfoMap.put("huMing", map.get("name")+"");
			baseInfoMap.put("huiLuMingCheng", map.get("loop")+"");
			baseInfoMap.put("changMing_a", map.get("factory_name_a")+"");
			baseInfoMap.put("changMing_b", map.get("factory_name_b")+"");
			baseInfoMap.put("changMing_c", map.get("factory_name_c")+"");
			baseInfoMap.put("xingShi_a", map.get("model_a")+"");
			baseInfoMap.put("xingShi_b", map.get("model_b")+"");
			baseInfoMap.put("xingShi_c", map.get("model_c")+"");
			baseInfoMap.put("changHao_a", map.get("factory_no_a")+"");
			baseInfoMap.put("changHao_b", map.get("factory_no_b")+"");
			baseInfoMap.put("changHao_c", map.get("factory_no_c")+"");
			baseInfoMap.put("eDingRongLiang", map.get("volume")+"");
			baseInfoMap.put("jiXing", map.get("polarity")+"");
			baseInfoMap.put("bianBi", map.get("transformer_ratio")+"");
			baseInfoMap.put("zhunQueDengJi", map.get("DJ")+"");
			baseInfoMap.put("chuChangNianYue", map.get("factory_date")+"");
			baseInfoMap.put("dianYa", map.get("rated_voltage")+"");
			baseInfoMap.put("pinLv", map.get("frequency")+"");
			baseInfoMap.put("juBianHao_a", map.get("no_a")+"");
			baseInfoMap.put("juBianHao_b", map.get("no_b")+"");
			baseInfoMap.put("juBianHao_c", map.get("no_c")+"");
			baseInfoMap.put("zhengShuBianHao", map.get("BH")+"");
			baseInfoMap.put("ceShiRen", map.get("tester")+"");
			baseInfoMap.put("ceShiRiQi", map.get("Time")+"");
			baseInfoMap.put("ceShiJieLun", map.get("conclusion")+"");
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
				String name=baseInfoMap.get("huMing");
				String loop=baseInfoMap.get("huiLuMingCheng");
				String factory_name_a=baseInfoMap.get("changMing_a");
				String factory_name_b=baseInfoMap.get("changMing_b");
				String factory_name_c=baseInfoMap.get("changMing_c");
				String model_a=baseInfoMap.get("xingShi_a");
				String model_b=baseInfoMap.get("xingShi_b");
				String model_c=baseInfoMap.get("xingShi_c");
				String factory_no_a=baseInfoMap.get("changHao_a");
				String factory_no_b=baseInfoMap.get("changHao_b");
				String factory_no_c=baseInfoMap.get("changHao_c");
				String volume=baseInfoMap.get("eDingRongLiang");//额定容量
				String polarity=baseInfoMap.get("jiXing");//极性
				String transformer_ratio=baseInfoMap.get("bianBi");//变比（变压比）
				String correctly_level=baseInfoMap.get("zhunQueDengJi");//准确等级
				String factory_date=baseInfoMap.get("chuChangNianYue");//出厂年月
				String rated_voltage=baseInfoMap.get("dianYa");//电压（额定电压）
				String frequency=baseInfoMap.get("pinLv");//频率
				String no_a=baseInfoMap.get("juBianHao_a");
				String no_b=baseInfoMap.get("juBianHao_b");
				String no_c=baseInfoMap.get("juBianHao_c");
				String certificate_no=baseInfoMap.get("zhengShuBianHao");//测试编号
				String tester=baseInfoMap.get("ceShiRen");
				String test_date=baseInfoMap.get("ceShiRiQi");
				String conclusion=baseInfoMap.get("ceShiJieLun");//测试结论
				
				if(certificate_no==null || "".equals(certificate_no)){
					return "测试编号不能为空！";
				}
				//校验测试编号是否存在
//				String check_sql="select count(*) as total from u_base_info where certificate_no='"+certificate_no+"' ";
//				ResultSet rs1=st.executeQuery(check_sql);
//				Long total=-1L;
//				while (rs1.next()){
//					total=rs1.getLong("total");
//				}
//				if(total>0){
//					return "测试编号已存在！";
//				}
				
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
				System.out.println("新增电流基本信息：sql "+sql);
				st.executeUpdate(sql);
				//插入测试数据,一组基础数据对应12条测试数据。
				//12条数据是3个误差（ab，bc，ca）、满载+轻载（2种情况）、比差+角差（2种情况）的组合，3*2*2=12
				String[] deviation={Constant.TEST_DATA_A,Constant.TEST_DATA_B,Constant.TEST_DATA_C};//ab、bc、ca误差
				String[] sn_ln={Constant.SN,Constant.LN};//满载、轻载
				System.out.println("插入电压测试数据:");
				
				String deviation_a_sn=map.get("deviation_a_sn");
				String[] data_a_sn=deviation_a_sn.split(",");
				String[] bicha_a_sn=data_a_sn[0].split(":")[1].split("#");
				String[] jiaocha_a_sn=data_a_sn[1].split(":")[1].split("#");
				
				String deviation_a_ln=map.get("deviation_a_ln");
				String[] data_a_ln=deviation_a_ln.split(",");
				String[] bicha_a_ln=data_a_ln[0].split(":")[1].split("#");
				String[] jiaocha_a_ln=data_a_ln[1].split(":")[1].split("#");
				
				String deviation_b_sn=map.get("deviation_b_sn");
				String[] data_b_sn=deviation_b_sn.split(",");
				String[] bicha_b_sn=data_b_sn[0].split(":")[1].split("#");
				String[] jiaocha_b_sn=data_b_sn[1].split(":")[1].split("#");
				
				String deviation_b_ln=map.get("deviation_b_ln");
				String[] data_b_ln=deviation_b_ln.split(",");
				String[] bicha_b_ln=data_b_ln[0].split(":")[1].split("#");
				String[] jiaocha_b_ln=data_b_ln[1].split(":")[1].split("#");
				
				String deviation_c_sn=map.get("deviation_c_sn");
				String[] data_c_sn=deviation_c_sn.split(",");
				String[] bicha_c_sn=data_c_sn[0].split(":")[1].split("#");
				String[] jiaocha_c_sn=data_c_sn[1].split(":")[1].split("#");
				
				String deviation_c_ln=map.get("deviation_c_ln");
				String[] data_c_ln=deviation_c_ln.split(",");
				String[] bicha_c_ln=data_c_ln[0].split(":")[1].split("#");
				String[] jiaocha_c_ln=data_c_ln[1].split(":")[1].split("#");
				
				//a-满载-比差
				String sql1="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
						+ "'"+bicha_a_sn[0]+"','"+bicha_a_sn[1]+"','"+bicha_a_sn[2]+"','"+bicha_a_sn[3]+"','"+bicha_a_sn[4]+"') ";
				//a-满载-角差
				String sql2="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_a_sn[0]+"','"+jiaocha_a_sn[1]+"','"+jiaocha_a_sn[2]+"','"+jiaocha_a_sn[3]+"','"+jiaocha_a_sn[4]+"') ";
				//a-轻载-比差
				String sql3="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
						+ "'"+bicha_a_ln[0]+"','"+bicha_a_ln[1]+"','"+bicha_a_ln[2]+"','"+bicha_a_ln[3]+"','"+bicha_a_ln[4]+"') ";
				//a-轻载-角差
				String sql4="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[0]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_a_ln[0]+"','"+jiaocha_a_ln[1]+"','"+jiaocha_a_ln[2]+"','"+jiaocha_a_ln[3]+"','"+jiaocha_a_ln[4]+"') ";
				
				
				//b-满载-比差
				String sql5="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
						+ "'"+bicha_b_sn[0]+"','"+bicha_b_sn[1]+"','"+bicha_b_sn[2]+"','"+bicha_b_sn[3]+"','"+bicha_b_sn[4]+"') ";
				//b-满载-角差
				String sql6="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_b_sn[0]+"','"+jiaocha_b_sn[1]+"','"+jiaocha_b_sn[2]+"','"+jiaocha_b_sn[3]+"','"+jiaocha_b_sn[4]+"') ";
				//b-轻载-比差
				String sql7="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
						+ "'"+bicha_b_ln[0]+"','"+bicha_b_ln[1]+"','"+bicha_b_ln[2]+"','"+bicha_b_ln[3]+"','"+bicha_b_ln[4]+"') ";
				//b-轻载-角差
				String sql8="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[1]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_b_ln[0]+"','"+jiaocha_b_ln[1]+"','"+jiaocha_b_ln[2]+"','"+jiaocha_b_ln[3]+"','"+jiaocha_b_ln[4]+"') ";
				
				//c-满载-比差
				String sql9="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"',"
						+ "'"+bicha_c_sn[0]+"','"+bicha_c_sn[1]+"','"+bicha_c_sn[2]+"','"+bicha_c_sn[3]+"','"+bicha_c_sn[4]+"') ";
				//c-满载-角差
				String sql10="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[0]+"', "
						+ "'"+jiaocha_c_sn[0]+"','"+jiaocha_c_sn[1]+"','"+jiaocha_c_sn[2]+"','"+jiaocha_c_sn[3]+"','"+jiaocha_c_sn[4]+"') ";
				//c-轻载-比差
				String sql11="insert into u_test_data(id,base_id,deviation,rate_error,sn_ln,"
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"',"
						+ "'"+bicha_c_ln[0]+"','"+bicha_c_ln[1]+"','"+bicha_c_ln[2]+"','"+bicha_c_ln[3]+"','"+bicha_c_ln[4]+"') ";
				//c-轻载-角差
				String sql12="insert into u_test_data(id,base_id,deviation,angle_error,sn_ln, "
						+ "value_20,value_50,value_80,value_100,value_120) "
						+ "values "
						+ "('"+UUID.randomUUID().toString()+"',"+id+",'"+deviation[2]+"','"+Constant.RATE_OR_ANGLE+"','"+sn_ln[1]+"', "
						+ "'"+jiaocha_c_ln[0]+"','"+jiaocha_c_ln[1]+"','"+jiaocha_c_ln[2]+"','"+jiaocha_c_ln[3]+"','"+jiaocha_c_ln[4]+"') ";
				
				st.executeUpdate(sql1);
				st.executeUpdate(sql2);
				st.executeUpdate(sql3);
				st.executeUpdate(sql4);
				st.executeUpdate(sql5);
				st.executeUpdate(sql6);
				st.executeUpdate(sql7);
				st.executeUpdate(sql8);
				st.executeUpdate(sql9);
				st.executeUpdate(sql10);
				st.executeUpdate(sql11);
				st.executeUpdate(sql12);
				conn.commit();
				rs.close();
				st.close();
				return "导入成功。";
			} catch (Exception e) {
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
		}
		if(Constant.FAILED.equals(result)){
			return "导入测试数据失败！请检查导入的文件与数据是否有误。";
		}else {
			return "导入成功。";
		}
	}
}
