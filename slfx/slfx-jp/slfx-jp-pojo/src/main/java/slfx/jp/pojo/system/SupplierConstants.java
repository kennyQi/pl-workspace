package slfx.jp.pojo.system;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @类功能说明：供应商代码、编码常量
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2014-8-7下午3:29:38
 * @版本：V1.0
 *
 */
public class SupplierConstants {
	
	public final static Map<String, String> suppMap = new LinkedHashMap<String, String>();
	public final static String PRE_USE = "0";
	public final static String USE = "1";
	
	public final static String PRE_USE_STRING = "未启用";
	public final static String PUBLIC_USE = "启用";
	
	
	public final static Map<String,String> STATUS_MAP = new HashMap<String,String>();
	static {
		STATUS_MAP.put( PRE_USE, PRE_USE_STRING);
		STATUS_MAP.put(USE, PUBLIC_USE);
	}
	static{
		//key平台代码   value平台编号+名称
		suppMap.put("BT", "001--百拓商旅网");
		suppMap.put("B5", "002--联拓天际");
		suppMap.put("PM", "003--票盟");
		suppMap.put("C5", "004--517NA");
		suppMap.put("DZ", "005--本票通");
		suppMap.put("YX", "007--易行天下");
		suppMap.put("Y8", "008--八千翼");
		suppMap.put("JR", "009--今日天下通");
		suppMap.put("KK", "010--KKKK商旅平台");
		suppMap.put("PP", "011--鹏朋");
		suppMap.put("IT", "012--纵横天地");
		suppMap.put("YT", "013--羿天下");
		suppMap.put("ET", "014--翔游天下");
		suppMap.put("LH", "015--联合800");
		suppMap.put("CD", "016--四川成都铁航");
		suppMap.put("BY", "017--上海不夜城");
		suppMap.put("AT", "018--北京爱特博");
		suppMap.put("YG", "019--北京阳光");
		suppMap.put("MF", "020--厦门航空");
		suppMap.put("CA", "021--中国国际航空");
		suppMap.put("MU", "022--中国东方航空");
		suppMap.put("HU", "023--海南航空");
		suppMap.put("ZH", "024--深圳航空");
		suppMap.put("KN", "025--联合航空");
		suppMap.put("SC", "026--山东航空");
		suppMap.put("3U", "027--四川航空");
		suppMap.put("CZ", "028--南方航空");
		suppMap.put("FM", "029--上海航空");
		suppMap.put("HO", "030--吉祥航空");
		suppMap.put("KY", "031--昆明航空");
		suppMap.put("XF", "032--幸福航空");
		suppMap.put("ST", "033--380出票网");
		suppMap.put("YP", "036--龙腾九州");
		suppMap.put("B3", "038--B3B平台");
		suppMap.put("NB", "040--宁波天天");
		suppMap.put("XA", "041--西安航旅");
		suppMap.put("BL", "042--包头联航");
		suppMap.put("MC", "045--美程国际");
		suppMap.put("FY", "046--北京阳光");
		suppMap.put("ES", "047--19E");
		suppMap.put("GS", "048--天津航空");
		suppMap.put("8L", "049--祥鹏航空");
		suppMap.put("JD", "050--首都航空");
		suppMap.put("EU", "051--成都航空");
		suppMap.put("PN", "052--西部航空");
		suppMap.put("NS", "053--河北航空");
		suppMap.put("TV", "054--西藏航空");
		suppMap.put("BK", "055--奥凯航空");
		suppMap.put("G5", "056--华夏航空");
		suppMap.put("CN", "057--大新华航空");
	}

	public static Map<String, String> getSuppmap() {
		return suppMap;
	}
			
}
