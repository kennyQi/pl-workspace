package hsl.pojo.dto.ad;
import java.util.HashMap;
import java.util.Map;
public class HslAdConstant {
	public static final Map<String , Integer> positionMap = new HashMap<String , Integer>();

	static{
		positionMap.put("hot",1);//热门景点推荐
		positionMap.put("special",2);
	}

	public static final String SPEC = "zzgl_spec";

	public static final String TOP = "zzgl_top";

	public static final String AD = "zzgl_ad";

	public static final String HOT = "zzgl_hot";

	public static final String TEAMAD = "zzgl_teamAd";

	public static final String HOTFIRST = "zzgl_hotFirst";

	public static final String SIDE= "zzgl_side";//侧边栏
	
	public static final String FRIENDSHIHC= "zzgl_friendshihc";//友情连接文字
	
	public static final String FRIENDSHIPIC= "zzgl_friendshipic";//友情连接图片
	
	public static final String LINETOP= "zzgl_line_top";//线路列表头部
	
	public static final String LINEBOTTOM= "zzgl_line_bottom";//线路列表底部
	/**
	 * 特价门票的广告位
	 */
	public static final String ZTLY = "zzgl_spec_ztly";
	public static final String GZMQ = "zzgl_spec_gzmq";
	public static final String YGXG = "zzgl_spec_ygxg";
	public static final String SQQS = "zzgl_spec_sqqs";
	public static final String SSYL = "zzgl_spec_ssyl";
	public static final String MJRW = "zzgl_spec_mjrw";
	public static final String WZHW = "zzgl_spec_wzhw";
	public  static final  Map<String,String> TYPEMAP = new HashMap<String,String>();
	static{
		TYPEMAP.put(ZTLY, "主题乐园");
		TYPEMAP.put(GZMQ, "古镇迷情");
		TYPEMAP.put(YGXG, "游逛香港");
		TYPEMAP.put(SQQS, "暑期亲水");
		TYPEMAP.put(SSYL, "山水园林");
		TYPEMAP.put(MJRW, "美景人文");
		TYPEMAP.put(WZHW, "玩转海外");
	}
	public static final String THZQ = "zzgl_linead_thzq";//特惠专区
	/******************************* 移动端广告 ***********************************/
	public static final String HSL_H5_BANNER = "zzgl_h5_ad";

}
