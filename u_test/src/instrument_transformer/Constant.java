package instrument_transformer;

public class Constant {
		
	/**
	 * 每次查询的最大记录条数
	 */
	public static final String LIMIT="100";
	
	/**
	 * 动态改变ab、bc、ca三个区域的表格的行高时，需要减去的高度。相当于窗口上下边框+tab边框+表头的高度和。
	 * 只是个估算值，若发现在调整窗口大小时，测试数据表格的行和左侧的“修约值”不在同一水平线上，可以调整此值。
	 */
	public static final Integer EXTRA_HEIGHT=50;
	
	/**
	 * 某行数据是“比差”还是“角差（相位差）”。例如，如果是比差，则rate_error会等于Y
	 */
	public static final String RATE_OR_ANGLE="Y";
	/**
	 * 标识，是测试数据A。若数据库字段deviation='A',表示该行是测试数据A
	 */
	public static final String TEST_DATA_A="A";
	/**
	 * 标识，是测试数据B。若数据库字段deviation='B',表示该行是测试数据B
	 */
	public static final String TEST_DATA_B="B";
	/**
	 * 标识，是测试数据C。若数据库字段deviation='C',表示该行是测试数据C
	 */
	public static final String TEST_DATA_C="C";
	/**
	 * 标识，是基本数据
	 */
	public static final String TEST_DATA_BASE="BASE";
	
	/**
	 * 满载
	 */
	public static final String SN="sn";
	/**
	 * 轻载
	 */
	public static final String LN="ln";
	
	
	//--------- 以下是用户可能用到的全局变量，在程序运行时动态赋值
	
	/**
	 * 当前选中的基本信息id
	 */
	public static Long BASE_ID=-1L;
	/**
	 * 当前选中的tab是A、B、还是C。例如，选中的是A，则此变量值为A(即Constant.TEST_DATA_A)。
	 */
	public static String TAB_ABC="";
	
	/**
	 * 成功
	 */
	public static String SUCCESS="1";
	/**
	 * 失败
	 */
	public static final String FAILED="0";

}
