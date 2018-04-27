package hg.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.KeyValue;

public class JobConstants {
	public final static String JOB_1 = "政府机关干部";
	public final static String JOB_2 = "邮电通信";
	public final static String JOB_3 = "计算机/网络";
	public final static String JOB_4 = "商业贸易";
	public final static String JOB_5 = "银行/金融/证券/投资";
	public final static String JOB_6 = "咨询";
	public final static String JOB_7 = "社会服务";
	public final static String JOB_8 = "旅游/饭店";
	public final static String JOB_9 = "健康/医疗服务";
	public final static String JOB_10 = "房地产";
	public final static String JOB_11 = "交通运输";
	public final static String JOB_12 = "税务";
	public final static String JOB_13 = "法律/司法";
	public final static String JOB_14 = "文化/娱乐/体育";
	public final static String JOB_15 = "媒介/广告";
	public final static String JOB_16 = "科研/教育";
	public final static String JOB_17 = "农林/畜牧/渔业";
	public final static String JOB_18 = "矿业/制造业";
	public final static String JOB_19 = "学生";
	public final static String JOB_20 = "自由职业";
	public final static String JOB_OTHER = "其他";
	
	public static class KeyValueImpl implements KeyValue {

		private String key;
		private String value;
		
		public KeyValueImpl(String key, String value){
			this.key=key;
			this.value=value;
		}
		
		@Override
		public Object getKey() {
			return key;
		}

		@Override
		public Object getValue() {
			return value;
		}
		
	}
	
	public final static List<KeyValue> JOB_LIST = new ArrayList<KeyValue>();
	public final static Map<String, String> JOB_MAP = new HashMap<String, String>();
	
	static {
		JOB_LIST.add(new KeyValueImpl("1", JOB_1));
		JOB_LIST.add(new KeyValueImpl("2", JOB_2));
		JOB_LIST.add(new KeyValueImpl("3", JOB_3));
		JOB_LIST.add(new KeyValueImpl("4", JOB_4));
		JOB_LIST.add(new KeyValueImpl("5", JOB_5));
		JOB_LIST.add(new KeyValueImpl("6", JOB_6));
		JOB_LIST.add(new KeyValueImpl("7", JOB_7));
		JOB_LIST.add(new KeyValueImpl("8", JOB_8));
		JOB_LIST.add(new KeyValueImpl("9", JOB_9));
		JOB_LIST.add(new KeyValueImpl("10", JOB_10));
		JOB_LIST.add(new KeyValueImpl("11", JOB_11));
		JOB_LIST.add(new KeyValueImpl("12", JOB_12));
		JOB_LIST.add(new KeyValueImpl("13", JOB_13));
		JOB_LIST.add(new KeyValueImpl("14", JOB_14));
		JOB_LIST.add(new KeyValueImpl("15", JOB_15));
		JOB_LIST.add(new KeyValueImpl("16", JOB_16));
		JOB_LIST.add(new KeyValueImpl("17", JOB_17));
		JOB_LIST.add(new KeyValueImpl("18", JOB_18));
		JOB_LIST.add(new KeyValueImpl("19", JOB_19));
		JOB_LIST.add(new KeyValueImpl("20", JOB_20));
		JOB_LIST.add(new KeyValueImpl("0", JOB_OTHER));
		for(KeyValue kv:JOB_LIST){
			JOB_MAP.put(kv.getKey().toString(), kv.getValue().toString());
		}
	}
	
}
