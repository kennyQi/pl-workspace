package hsl.web.util;
import hg.log.util.HgLogger;
import hsl.pojo.dto.jp.FlightClassDTO;
import hsl.pojo.dto.jp.FlightDTO;
import java.util.List;
/**
 * @类功能说明：机票的工具类（一些公用方法）
 * @类修改者：
 * @修改日期：2015年1月20日下午1:53:25
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年1月20日下午1:53:25
 *
 */
public class JpUtil {
	/**
	 * @方法功能说明：根据得到JpOrderDto转换得到舱位名称
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月20日下午1:56:37
	 * @修改内容：
	 * @参数：@param jporders
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public static String getJpClassName(FlightDTO flightDTO){
		//根据机票的舱位列表和订单的舱位码判断哪个舱位
		try{
			List<FlightClassDTO> fcs = flightDTO.getFlightClassList();
			for(FlightClassDTO fc : fcs){
				if(fc.getClassCode().equals(flightDTO.getClassCode())){
					String jClassName = "";
					if(fc.getDiscount()>=1&&fc.getDiscount()<=100){
						jClassName = "经济舱";
					}else if(fc.getDiscount()>=101&&fc.getDiscount()<=200){
						jClassName = "商务舱";
					}else if(fc.getDiscount()>=201){
						jClassName = "头等舱";
					}else{
						HgLogger.getInstance().error("zhuxy", "仓位折扣不正确 discount:"+fc.getDiscount());
					}
					return  jClassName;
				}
			}
		}catch(NullPointerException e){
			HgLogger.getInstance().error("zhuxy", "查询订单详情--获取机票信息失败"+HgLogger.getStackTrace(e));
		}
		return "";
	}
}
