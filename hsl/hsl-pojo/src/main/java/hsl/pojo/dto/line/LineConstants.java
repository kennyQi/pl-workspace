package hsl.pojo.dto.line;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LineConstants {
	
	/**
	 * 线路推荐指数
	 */
	public static final Map<String,String>	recommendationLevelMap = new TreeMap<String,String>();
	
	public final static Integer RLEVEL_1 = 1;
	public final static Integer RLEVEL_2 = 2;
	public final static Integer RLEVEL_3 = 3;
	public final static Integer RLEVEL_4 = 4;
	public final static Integer RLEVEL_5 = 5;
	
	static{
		recommendationLevelMap.put(RLEVEL_1+"", "1星");
		recommendationLevelMap.put(RLEVEL_2+"", "2星");
		recommendationLevelMap.put(RLEVEL_3+"", "3星");
		recommendationLevelMap.put(RLEVEL_4+"", "4星");
		recommendationLevelMap.put(RLEVEL_5+"", "5星");
	}
	
	
	/**
	 * 线路类型
	 */
	public static final Map<String,String>	typeMap = new TreeMap<String,String>();
	
	public final static Integer TYPE_WITH_GROUP = 1;
	public final static Integer TYPE_SELF_HELP = 2;
	
	static{
		typeMap.put(TYPE_WITH_GROUP + "", "跟团游");
		typeMap.put(TYPE_SELF_HELP + "", "自助游");
	}
	
	/**
	 * 交通工具
	 */
	public static final Map<String,String>	vehicleMap = new TreeMap<String,String>();
	public final static Integer VEHICLE_PLAIN = 1; // 飞机
	public final static Integer VEHICLE_BUS = 2; // 大巴
	public final static Integer VEHICLE_SHIP = 3; // 轮船
	public final static Integer VEHICLE_TRAIN = 4; // 火车
	public final static Integer SELF_GOING = 5; // 自行前往
	
	static{
		vehicleMap.put(VEHICLE_PLAIN + "", "飞机");
		vehicleMap.put(VEHICLE_BUS + "", "大巴");
		vehicleMap.put(VEHICLE_SHIP + "", "轮船");
		vehicleMap.put(VEHICLE_TRAIN + "", "火车");
		vehicleMap.put(SELF_GOING + "", "自行前往");
	}
	
	/**
	 * 住宿星级
	 */
	public static final Map<String,String> stayLevelMap = new TreeMap<String,String>();
	
	public final static Integer STAY_LEVEL_3 = 1; // 三星
	public final static Integer STAY_LEVEL_4 = 2; // 四星
	public final static Integer STAY_LEVEL_5 = 3; // 五星
	public final static Integer STAY_LEVEL_B3 = 4; // 标三
	public final static Integer STAY_LEVEL_B4 = 5; // 标四
	public final static Integer STAY_LEVEL_B5 = 6; // 标五
	public final static Integer STAY_NOTHING = 7;  //暂无
	
	static{
		stayLevelMap.put(STAY_LEVEL_5 + "","五星");
		stayLevelMap.put(STAY_LEVEL_4 + "","四星");
		stayLevelMap.put(STAY_LEVEL_3 + "","三星");
		stayLevelMap.put(STAY_LEVEL_B3 + "","标三");
		stayLevelMap.put(STAY_LEVEL_B4 + "","标四");
		stayLevelMap.put(STAY_LEVEL_B5 + "","标五");
		stayLevelMap.put(STAY_NOTHING + "","暂无");
	}
	
	/***
	 * 线路状态
	 */
	public static final Map<String,String> statusMap = new TreeMap<String,String>();
	public final static Map<String,String> AUDIT_STATUS_MAP = new HashMap<String,String>();//线路审核
	public final static Map<String,String> GROUNDING_STATUS_MAP = new HashMap<String,String>();//线路上架
	public final static Map<String,String> UNDERCARRIAGE_STATUS_MAP = new HashMap<String,String>();//线路下架
	public static Integer DO_NOT_AUDIT = 1;//未审核
	public static Integer AUDIT_FAIL = 2;//审核未通过
	public static Integer AUDIT_SUCCESS = 3;//已审核
	public static Integer AUDIT_UP = 4;//已上架
	public static Integer AUDIT_DOWN = 5;//已下架
	
	static{
		statusMap.put(DO_NOT_AUDIT + "", "未审核");
		statusMap.put(AUDIT_FAIL + "", "审核未通过");
		statusMap.put(AUDIT_SUCCESS + "", "已审核");
		statusMap.put(AUDIT_UP + "", "已上架");
		statusMap.put(AUDIT_DOWN + "", "已下架");
		
		AUDIT_STATUS_MAP.put(AUDIT_FAIL + "", "审核未通过");
		AUDIT_STATUS_MAP.put(AUDIT_SUCCESS + "", "审核通过");
		
		GROUNDING_STATUS_MAP.put(AUDIT_UP+"","上架");
		UNDERCARRIAGE_STATUS_MAP.put(AUDIT_DOWN+"","下架");
	}
}

