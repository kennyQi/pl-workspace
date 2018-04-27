package hsl.pojo.dto.ad;
import java.util.HashMap;
import java.util.Map;
public class HslAdConstant {
	public static Map<String , Integer> positionMap = new HashMap<String , Integer>();

	static{
		positionMap.put("hot",1);//热门景点推荐
		positionMap.put("special",2);
	}

	public static final String SPEC = "hsl_spec";

	public static final String TOP = "hsl_top";

	public static final String AD = "hsl_ad";

	public static final String HOT = "hsl_hot";

	public static final String TEAMAD = "hsl_teamAd";

	public static final String HOTFIRST = "hsl_hotFirst";

	public static final String SIDE= "hsl_side";//侧边栏
	
	public static final String FRIENDSHIHC= "hsl_friendshihc";//友情连接文字
	
	public static final String FRIENDSHIPIC= "hsl_friendshipic";//友情连接图片

	/**
	 * 特价门票的广告位
	 */
	public static final String ZTLY = "hsl_spec_ztly";
	public static final String GZMQ = "hsl_spec_gzmq";
	public static final String YGXG = "hsl_spec_ygxg";
	public static final String SQQS = "hsl_spec_sqqs";
	public static final String SSYL = "hsl_spec_ssyl";
	public static final String MJRW = "hsl_spec_mjrw";
	public static final String WZHW = "hsl_spec_wzhw";

	public  static  Map<String,String> TYPEMAP = new HashMap<String,String>();

	{
		TYPEMAP.put(ZTLY, "主题乐园");
		TYPEMAP.put(GZMQ, "古镇迷情");
		TYPEMAP.put(YGXG, "游逛香港");
		TYPEMAP.put(SQQS, "暑期亲水");
		TYPEMAP.put(SSYL, "山水园林");
		TYPEMAP.put(MJRW, "美景人文");
		TYPEMAP.put(WZHW, "玩转海外");
	}
	public static final String THZQ = "hsl_linead_thzq";//特惠专区
	/******************************* 移动端广告 ***********************************/
	public static final String HSL_H5_BANNER = "hsl_h5_ad";

}
