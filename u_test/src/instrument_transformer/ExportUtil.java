package instrument_transformer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ExportUtil {

	/**
	 * 导出电压数据
	 * @param baseId 主记录id
	 * @return
	 */
	public static String  exportU(Long baseId) {
		try {
			String templatePath = System.getProperty("user.dir");
			File templete = new File(templatePath);
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
			Map<String, String> dataMapA =getUExportData(baseId,Constant.TEST_DATA_A);
			Map<String, String> dataMapB =getUExportData(baseId,Constant.TEST_DATA_B);
			Map<String, String> dataMapC =getUExportData(baseId,Constant.TEST_DATA_C);
			configuration.setDirectoryForTemplateLoading(templete);// FTL文件所存在的位置
			Template t = null;
			t = configuration.getTemplate("Utemplate.ftl"); // 文件名
			File dir = new File("C:/测试报告/电压/"+baseId);
			if (!dir.exists() && !dir.isDirectory()) {
				dir.mkdirs();
			}
			File outFileA = new File(dir + "/"+Constant.TEST_DATA_A+".doc");
			outFileA.createNewFile();
			Writer out = null;
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileA)));
			t.process(dataMapA, out);
			
			File outFileB = new File(dir + "/"+Constant.TEST_DATA_B+".doc");
			outFileB.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileB)));
			t.process(dataMapB, out);
			
			File outFileC = new File(dir + "/"+Constant.TEST_DATA_C+".doc");
			outFileC.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileC)));
			t.process(dataMapC, out);
			out.close();
			
			return "导出成功。导出文件在文件夹["+dir.getPath()+"]中。";
		} catch (Exception e) {
			e.printStackTrace();
			return "导出失败！";
		} 
	}

	/**
	 * 导出电流数据
	 * @param baseId 主记录id
	 * @return
	 */
	public static String  exportI(Long baseId) {
		try {
			Connection conn=DBConnection.getInstance();
			conn.setAutoCommit(false);
			Statement st=conn.createStatement();
			String sql="select certificate_no from i_base_info where id="+baseId;
			ResultSet rs=st.executeQuery(sql);
			String test_no=null;
			while(rs.next()){
				test_no=rs.getString("certificate_no");
			}
			rs.close();
			st.close();
			if(test_no==null || "".equals(test_no)){
				return "测试编号为空，不能导出。";
			}
			String templatePath = System.getProperty("user.dir");
			File templete = new File(templatePath);
			Configuration configuration = new Configuration();
			configuration.setDefaultEncoding("UTF-8");
			Map<String, String> dataMapA =getIExportData(baseId,Constant.TEST_DATA_A,test_no);
			Map<String, String> dataMapB =getIExportData(baseId,Constant.TEST_DATA_B,test_no);
			Map<String, String> dataMapC =getIExportData(baseId,Constant.TEST_DATA_C,test_no);
			configuration.setDirectoryForTemplateLoading(templete);// FTL文件所存在的位置
			Template t = null;
			t = configuration.getTemplate("Itemplate.ftl"); // 文件名
			File dir = new File("C:/测试报告/电流/"+baseId);
			if (!dir.exists() && !dir.isDirectory()) {
				dir.mkdirs();
			}
			File outFileA = new File(dir + "/"+Constant.TEST_DATA_A+".doc");
			outFileA.createNewFile();
			Writer out = null;
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileA)));
			t.process(dataMapA, out);
			
			File outFileB = new File(dir + "/"+Constant.TEST_DATA_B+".doc");
			outFileB.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileB)));
			t.process(dataMapB, out);
			
			File outFileC = new File(dir + "/"+Constant.TEST_DATA_C+".doc");
			outFileC.createNewFile();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileC)));
			t.process(dataMapC, out);
			out.close();
			
			return "导出成功。导出文件在文件夹["+dir.getPath()+"]中。";
		} catch (Exception e) {
			e.printStackTrace();
			return "导出失败！";
		} 
	}
	

	/**
	 * 获取待导出的数据
	 * @param baseId 主记录id
	 * @param deviation 相别，取值A、B、C
	 * @return
	 */
	private static Map<String,String> getUExportData(Long baseId,String deviation) {
		Map<String,String> map=new HashMap<>();
		map.put("deviation", deviation);
		try {
			Connection conn=DBConnection.getInstance();
			Statement st=conn.createStatement();
			String sql0="select * from u_base_info where id="+baseId;
			ResultSet rs0=st.executeQuery(sql0);
			while(rs0.next()){
				String ceShiRen=rs0.getString("tester");//测试人
				String huiLuMingCheng=rs0.getString("loop");//回路名称
				String dianYa=rs0.getString("rated_voltage"); //额定电压
				String zhunQueDengJi=rs0.getString("correctly_level");//准确等级
				String pinLv=rs0.getString("frequency");//频率
				String jiXing=rs0.getString("polarity");//极性
				String ceShiJieLun=rs0.getString("conclusion");//极性
				map.put("ceShiRen", ceShiRen);
				map.put("huiLuMingCheng", huiLuMingCheng);
				map.put("dianYa", dianYa);
				map.put("zhunQueDengJi", zhunQueDengJi);
				map.put("pinLv", pinLv);
				map.put("jiXing", jiXing);
				map.put("ceShiJieLun", ceShiJieLun);
			}
			rs0.close();
			String sql1="select * from u_test_data where base_id="+baseId+" and deviation='"+deviation+"' and rate_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.SN+"' ";
			String sql2="select * from u_test_data where base_id="+baseId+" and deviation='"+deviation+"' and angle_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.SN+"' ";
			String sql3="select * from u_test_data where base_id="+baseId+" and deviation='"+deviation+"' and rate_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.LN+"' ";
			String sql4="select * from u_test_data where base_id="+baseId+" and deviation='"+deviation+"' and angle_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.LN+"' ";
			ResultSet rs1=st.executeQuery(sql1);
			while(rs1.next()){
				String rate_sn_20=rs1.getString("value_20");
				String rate_sn_50=rs1.getString("value_50");
				String rate_sn_80=rs1.getString("value_80");
				String rate_sn_100=rs1.getString("value_100");
				String rate_sn_120=rs1.getString("value_120");
				map.put("rate_sn_20", rate_sn_20);
				map.put("rate_sn_50", rate_sn_50);
				map.put("rate_sn_80", rate_sn_80);
				map.put("rate_sn_100", rate_sn_100);
				map.put("rate_sn_120", rate_sn_120);
			}
			rs1.close();
			
			ResultSet rs2=st.executeQuery(sql2);
			while(rs2.next()){
				String angle_sn_20=rs2.getString("value_20");
				String angle_sn_50=rs2.getString("value_50");
				String angle_sn_80=rs2.getString("value_80");
				String angle_sn_100=rs2.getString("value_100");
				String angle_sn_120=rs2.getString("value_120");
				map.put("angle_sn_20", angle_sn_20);
				map.put("angle_sn_50", angle_sn_50);
				map.put("angle_sn_80", angle_sn_80);
				map.put("angle_sn_100", angle_sn_100);
				map.put("angle_sn_120", angle_sn_120);
			}
			rs2.close();
			
			ResultSet rs3=st.executeQuery(sql3);
			while(rs3.next()){
				String rate_ln_20=rs3.getString("value_20");
				String rate_ln_50=rs3.getString("value_50");
				String rate_ln_80=rs3.getString("value_80");
				String rate_ln_100=rs3.getString("value_100");
				String rate_ln_120=rs3.getString("value_120");
				map.put("rate_ln_20", rate_ln_20);
				map.put("rate_ln_50", rate_ln_50);
				map.put("rate_ln_80", rate_ln_80);
				map.put("rate_ln_100", rate_ln_100);
				map.put("rate_ln_120", rate_ln_120);
			}
			rs3.close();
			
			ResultSet rs4=st.executeQuery(sql4);
			while(rs4.next()){
				String angle_ln_20=rs4.getString("value_20");
				String angle_ln_50=rs4.getString("value_50");
				String angle_ln_80=rs4.getString("value_80");
				String angle_ln_100=rs4.getString("value_100");
				String angle_ln_120=rs4.getString("value_120");
				map.put("angle_ln_20", angle_ln_20);
				map.put("angle_ln_50", angle_ln_50);
				map.put("angle_ln_80", angle_ln_80);
				map.put("angle_ln_100", angle_ln_100);
				map.put("angle_ln_120", angle_ln_120);
			}
			rs4.close();
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取待导出的电流数据
	 * @param baseId 主记录id
	 * @param deviation 相别，取值A、B、C
	 * @param test_no 测试编号
	 * @return
	 */
	private static Map<String, String> getIExportData(Long baseId, String deviation, String test_no) {
		Map<String,String> map=new HashMap<>();
		map.put("deviation", deviation);
		try {
			Connection conn=DBConnection.getInstance();
			Statement st=conn.createStatement();
			String sql0="select * from i_base_info where id="+baseId;
			ResultSet rs0=st.executeQuery(sql0);
			while(rs0.next()){
				String ceShiRen=rs0.getString("tester");//测试人
				String huiLuMingCheng=rs0.getString("loop");//回路名称
				String dianYa=rs0.getString("rated_voltage"); //额定电压
				String zhunQueDengJi=rs0.getString("correctly_level");//准确等级
				String pinLv=rs0.getString("frequency");//频率
				String jiXing=rs0.getString("polarity");//极性
				String ceShiJieLun=rs0.getString("conclusion");//极性
				map.put("ceShiRen", ceShiRen);
				map.put("huiLuMingCheng", huiLuMingCheng);
				map.put("dianYa", dianYa);
				map.put("zhunQueDengJi", zhunQueDengJi);
				map.put("pinLv", pinLv);
				map.put("jiXing", jiXing);
				map.put("ceShiJieLun", ceShiJieLun);
			}
			rs0.close();
			String sql="select id from i_base_info where certificate_no='"+test_no+"'";
			ResultSet rs=st.executeQuery(sql);
			List<Long> ids=new ArrayList<>();
			while(rs.next()){
				ids.add(rs.getLong("id"));
			}
			rs.close();
			for (int i = 0; i < ids.size(); i++) {
				Long id=ids.get(i);
				String sql1="select * from i_test_data where base_id="+id+" and deviation='"+deviation+"' and rate_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.SN+"' ";
				String sql2="select * from i_test_data where base_id="+id+" and deviation='"+deviation+"' and angle_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.SN+"' ";
				String sql3="select * from i_test_data where base_id="+id+" and deviation='"+deviation+"' and rate_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.LN+"' ";
				String sql4="select * from i_test_data where base_id="+id+" and deviation='"+deviation+"' and angle_error='"+Constant.RATE_OR_ANGLE+"' and sn_ln='"+Constant.LN+"' ";
				ResultSet rs1=st.executeQuery(sql1);
				while(rs1.next()){
					String rate_sn_20=rs1.getString("value_20");
					String rate_sn_50=rs1.getString("value_50");
					String rate_sn_80=rs1.getString("value_80");
					String rate_sn_100=rs1.getString("value_100");
					String rate_sn_120=rs1.getString("value_120");
					map.put("rate_sn_20"+"_"+i, rate_sn_20);
					map.put("rate_sn_50"+"_"+i, rate_sn_50);
					map.put("rate_sn_80"+"_"+i, rate_sn_80);
					map.put("rate_sn_100"+"_"+i, rate_sn_100);
					map.put("rate_sn_120"+"_"+i, rate_sn_120);
				}
				rs1.close();
				
				ResultSet rs2=st.executeQuery(sql2);
				while(rs2.next()){
					String angle_sn_20=rs2.getString("value_20");
					String angle_sn_50=rs2.getString("value_50");
					String angle_sn_80=rs2.getString("value_80");
					String angle_sn_100=rs2.getString("value_100");
					String angle_sn_120=rs2.getString("value_120");
					map.put("angle_sn_20"+"_"+i, angle_sn_20);
					map.put("angle_sn_50"+"_"+i, angle_sn_50);
					map.put("angle_sn_80"+"_"+i, angle_sn_80);
					map.put("angle_sn_100"+"_"+i, angle_sn_100);
					map.put("angle_sn_120"+"_"+i, angle_sn_120);
				}
				rs2.close();
				
				ResultSet rs3=st.executeQuery(sql3);
				while(rs3.next()){
					String rate_ln_20=rs3.getString("value_20");
					String rate_ln_50=rs3.getString("value_50");
					String rate_ln_80=rs3.getString("value_80");
					String rate_ln_100=rs3.getString("value_100");
					String rate_ln_120=rs3.getString("value_120");
					map.put("rate_ln_20"+"_"+i, rate_ln_20);
					map.put("rate_ln_50"+"_"+i, rate_ln_50);
					map.put("rate_ln_80"+"_"+i, rate_ln_80);
					map.put("rate_ln_100"+"_"+i, rate_ln_100);
					map.put("rate_ln_120"+"_"+i, rate_ln_120);
				}
				rs3.close();
				
				ResultSet rs4=st.executeQuery(sql4);
				while(rs4.next()){
					String angle_ln_20=rs4.getString("value_20");
					String angle_ln_50=rs4.getString("value_50");
					String angle_ln_80=rs4.getString("value_80");
					String angle_ln_100=rs4.getString("value_100");
					String angle_ln_120=rs4.getString("value_120");
					map.put("angle_ln_20"+"_"+i, angle_ln_20);
					map.put("angle_ln_50"+"_"+i, angle_ln_50);
					map.put("angle_ln_80"+"_"+i, angle_ln_80);
					map.put("angle_ln_100"+"_"+i, angle_ln_100);
					map.put("angle_ln_120"+"_"+i, angle_ln_120);
				}
				rs4.close();
			}
				
			rs.close();
			st.close();
			//判断离3组数据还缺多少数据，需要把缺的数据置为空，否则导出的word里面就会有$开头的表达式
			for (int i = ids.size(); i < 3; i++) {
				map.put("rate_sn_20"+"_"+i, "");
				map.put("rate_sn_50"+"_"+i, "");
				map.put("rate_sn_80"+"_"+i, "");
				map.put("rate_sn_100"+"_"+i, "");
				map.put("rate_sn_120"+"_"+i, "");
				
				map.put("angle_sn_20"+"_"+i, "");
				map.put("angle_sn_50"+"_"+i, "");
				map.put("angle_sn_80"+"_"+i, "");
				map.put("angle_sn_100"+"_"+i, "");
				map.put("angle_sn_120"+"_"+i, "");
				
				map.put("rate_ln_20"+"_"+i, "");
				map.put("rate_ln_50"+"_"+i, "");
				map.put("rate_ln_80"+"_"+i, "");
				map.put("rate_ln_100"+"_"+i, "");
				map.put("rate_ln_120"+"_"+i, "");
				
				map.put("angle_ln_20"+"_"+i, "");
				map.put("angle_ln_50"+"_"+i, "");
				map.put("angle_ln_80"+"_"+i, "");
				map.put("angle_ln_100"+"_"+i, "");
				map.put("angle_ln_120"+"_"+i, "");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(map.get("rate_sn_20_0"));
		return map;
	}

}
