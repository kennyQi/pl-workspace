package pay.record.api.client.common.api;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @类功能说明：接口动作
 * @类修改者：
 * @修改日期：2015-7-2下午3:27:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-2下午3:27:31
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PayRecordApiAction {
	/**
	 * 创建机票支付记录
	 */
	//用户->经销商
	public final static String PAY_RECORD_AIR_UTJ_CREATE = "PAY_RECORD_AIR_UTJ_CREATE";
	
	//分销->供应商
	public final static String PAY_RECORD_AIR_FTG_CREATE = "PAY_RECORD_AIR_FTG_CREATE";
	
	//供应商->分销
	public final static String PAY_RECORD_AIR_GTF_CREATE = "PAY_RECORD_AIR_GTF_CREATE";
	
	//经销商->用户
	public final static String PAY_RECORD_AIR_JTU_CREATE = "PAY_RECORD_AIR_JTU_CREATE";
	
	/**
	 * 创建线路支付记录
	 */
	//用户->经销商
	public final static String PAY_RECORD_LINE_UTJ_CREATE = "PAY_RECORD_LINE_UTJ_CREATE";
	
	/**
	 * @方法功能说明：接口名称
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-2下午3:20:03
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String value();
	
}
