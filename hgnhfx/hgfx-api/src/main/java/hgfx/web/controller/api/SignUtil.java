package hgfx.web.controller.api;

import hg.framework.common.util.Md5FileUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class SignUtil {

	/**验证签名。
	 * @param request
	 * @param signKey 使用签名的key
	 * @param signValue 传来的sign
	 * @return 错误信息 or null
	 */
	public static String checkSign(HttpServletRequest request, String signKey) {
		//检查开关
		final String property = System.getProperty("checkSign", "true");
		boolean checkSign= property.equalsIgnoreCase("true");
		if(!checkSign){
//			System.out.println("system property checkSign is  "+property);
			return null;
		}
		
		// check sign
		String signError = null;
		final String signValue = request.getParameter("sign");
		if (signValue == null){
			signError = "缺少签名！";
		}else if(request.getParameter("time")==null){
			signError="缺少time参数！";
		}else{
			final Map<String, String[]> parameterMap = request.getParameterMap();
			final String md5 = SignUtil.computeSign(signKey, parameterMap);
			
			if (!signValue.equals(md5)) {
				signError = "签名验证错误！";
			}
		}
		return signError;
	}

	/**计算签名。参数名排序后的顺序，再加 接入方key，做md5
	 * @param signKey
	 * @param parameterMap
	 * @return
	 */
	public static String computeSign(String signKey,
			final Map<String, String[]> parameterMap) {
		List<String> keys = new ArrayList<>();
		// 参数名排序
		for (String k : parameterMap.keySet()) {
			if (!k.equalsIgnoreCase("sign"))
				keys.add(k);
		}
		Collections.sort(keys);
		
		StringBuffer signBuf = new StringBuffer();
		for (String k : keys)
			signBuf.append(parameterMap.get(k)[0]);
		final String md5 = Md5FileUtil.MD5(signBuf.toString() + signKey);
		return md5;
	}

	public static void main(String[] args) {
		if(args.length==0){
			System.out.println("生成签名，请传入签名用到的key 和 http请求的参数值。如\n"+
					"bcd1e53041a073993700539d38271e16 appId=2c24e68161c24c3690921520d6cccbd5&orderCode=20160607&productCode=haha_setProdCode&csairCard=415305153869&csairName=xlj&num=100&time=33333333");
			return;
		}
		
		final String signKey=args[0] ;//= "bcd1e53041a073993700539d38271e16";
		String request = args[1];//= "appId=2c24e68161c24c3690921520d6cccbd5&orderCode=20160607&productCode=haha_setProdCode&csairCard=415305153869&csairName=xlj&num=100&time=33333333&sign=27af89e6f18ebbec01f05619a6e05a23";
		String compu = computeSign(signKey, request);
//		System.out.println(request+"&sign="+ compu);
	}

	/**
	 * @param signKey
	 * @param request
	 * @return
	 */
	public static String computeSign(final String signKey, String request) {
		//String request = "appId=2c24e68161c24c3690921520d6cccbd5&orderCode=20160606&time=33333333&sign=ee1b468c2ef7ea4c3e2f2a747d012e01";
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		for(String entry:request.split("&")){
			String[] entry2=entry.split("=");
			parameterMap.put(entry2[0], new String[]{entry2[1]});
		}
		String compu=computeSign(signKey, parameterMap);
		return compu;
	}
}
