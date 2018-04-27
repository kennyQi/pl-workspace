package pl.h5.control.constant;
import java.util.HashMap;
import java.util.Map;
public class Constants {
	public static  Map<Integer, String> articleChannelMap=new HashMap<Integer, String>();
	static {
		articleChannelMap.put(1, "时政要闻");
		articleChannelMap.put(2, "名人到访");
		articleChannelMap.put(3, "重要活动");
		articleChannelMap.put(4, "自然风光");
		articleChannelMap.put(5, "文化传承");
		articleChannelMap.put(6, "文物主题");
	}
}
